/**
 * uri解析
 */
(function() {
	function parser(url, options) {
		var opts = {
			url : undefined != url ? url
					: options && options["url"] ? options["url"]
							: location.href,
			strictMode : options && options["strictMode"] ? options["strictMode"]
					: false,
			key : [ "source", "protocol", "authority", "userInfo", "user",
					"password", "host", "port", "relative", "path",
					"directory", "file", "query", "anchor" ],
			q : {
				name : "params",
				parser : /(?:^|&)([^&=]*)=?([^&]*)/g
			},
			parser : {
				strict : /^(?:([^:\/?#]+):)?(?:\/\/((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?))?((((?:[^?#\/]*\/)*)([^?#]*))(?:\?([^#]*))?(?:#(.*))?)/,
				loose : /^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/
			}
		};

		str = decodeURI(opts.url);

		var m = opts.parser[opts.strictMode ? "strict" : "loose"].exec(str);
		var uri = {};
		var i = 14;

		while (i--) {
			uri[opts.key[i]] = m[i] || "";
		}

		var params = null;
		uri[opts.key[12]].replace(opts.q.parser, function($0, $1, $2) {
			if ($1) {
				if (!params)
					params = {};
				params[$1] = $2;
			}
		});
		uri[opts.q.name] = params;

		return uri;
	}

	var uri = {
		/**
		 * 获取uri对象
		 * 
		 * @param url
		 *            {String} url地址
		 * @param options
		 *            {Object} 配置
		 * @returns {Object}
		 */
		parse : function(url, options) {
			return parser(url, options);
		},

		/**
		 * 获取root地址
		 * 
		 * @param url
		 * @param options
		 * @returns
		 */
		root : function(url, options) {
			var uri = parser(url, options);
			var directory = uri["directory"].substring(1);
			return [
					uri["protocol"] + ":",
					"",
					uri["authority"],
					uri["directory"].indexOf("/modules") == 0 ? "" : directory
							.substring(0, directory.indexOf("/")), "" ]
					.join("/");
		},
        /***
		 * huouq
         * @returns {string}
         */
		getBasePath : function(){
			var projectName = "";
            if(location.href.indexOf("srmip")>=0){
                projectName = "srmip";
                return window.location.origin+"/"+projectName+"/";
            }
            return window.location.origin+"/"+projectName;
		}
	};

	/**
	 * 获取指定uri字段
	 */
	var keys = [ "source", "protocol", "authority", "userInfo", "user",
			"password", "host", "port", "relative", "path", "directory",
			"file", "query", "anchor", "params" ];
	var i = 15;
	while (i--) {
		uri[keys[i]] = new Function(
				"url",
				"options",
				"var _hg=window[\"hg\"];var _uri=_hg&&_hg[\"uri\"]?_hg[\"uri\"]:uri;return _uri.parse(url,options)[\""
						+ keys[i] + "\"];");
	}

	window["uri"] = uri;
})();