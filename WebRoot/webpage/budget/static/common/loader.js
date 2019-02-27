/**
 * 整站通用JS
 */
(function() {
	// 根路径
	var basePath = (function getRootPath() {
		var pathName = location.pathname.substring(1);
		var webName = pathName == "" ? "" : pathName.substring(0, pathName
				.indexOf("/"));
		return [ location.protocol, "", location.host, webName, "" ].join("/");
	})();

	// 核心模块
	var coreModules = [ {
		name : "公共库",
		// 路径
		path : basePath + "admin/common/",
		css : {
			path : "css/",
			files : [ "common.css", "icon.css", "formBasic.css" ]
		},
		// JS
		js : {
			path : "js/",
			files : [ "uri.js","theme.js"]
		},
		// 加载后函数
		after : function() {
			// 加载模块默认入口文件
			// 如：模块文件为index.html，则加载文件为css/index.css、js/index.js
			var file = uri.file();
			var file = file != "" ? file.replace(/\.html$/, "").replace(/\.jsp$/, "") : "login";
			coreModules.push({
				name : "子模块默认入口",
				js : [ "js/" + file + ".js" ],
				// 加载前函数
				before : function() {
					if (!window.hg && window.top.hg) {
						window.hg = window.top.hg;
					}
				},
				// 加载后函数
				after : function() {
					// 显示BODY
					setTimeout(function() {
						$("body").fadeIn();
					}, 100);
				}
			});
		}
	}, {
		// 名称
		name : "外部库及插件",
		// 路径
		path : basePath + "js/",
		// CSS
		css : [ "easyui/themes/default/tooltip.css", "easyui/themes/icon.css" ],
		// JS
		js : [ "jquery/jquery1.9.0.js", "easyui/easyloader.js" ],
		// 加载后函数
		after : function() {
			// 设置EasyUI默认为中文
			easyloader.locale = "zh_CN";
			// 切换EasyUI皮肤
			easyloader.theme = "default";
			// 加载EasyUI解析器
			easyloader.load("parser", function() {
			});
		}
	}, {
		name : "自定义扩展库",
		// 路径
		path : basePath + "js/",
		js : {
			path : "appPlugs/",
			files : [ "jquery-ext.js", "easyui-ext.js","ajax-request.js" ]
		}
	} ];

	var loader = {
		/**
		 * 调试模式
		 */
		debugMode : false,

		/**
		 * 加载CSS
		 * 
		 * @param url
		 * @param callback
		 */
		loadCss : function(url, callback) {
			var link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.media = "screen";
			link.href = [ url, "?t=", Date.now() ].join("");
			document.getElementsByTagName("head")[0].appendChild(link);
			if (callback) {
				callback(link);
			}
		},

		/**
		 * 加载JS
		 * 
		 * @param url
		 * @param callback
		 */
		loadJs : function(url, callback) {
			var f = arguments.callee;
			if (!("queue" in f))
				f.queue = {};
			var queue = f.queue;
			if (url in queue) {// script is already in the document
				if (callback) {
					if (queue[url])// still loading
						queue[url].push(callback);
					else
						// loaded
						callback();
				}
				return;
			}
			queue[url] = callback ? [ callback ] : [];

			var done = false;
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.language = "javascript";
			script.src = url;
			script.onload = script.onreadystatechange = function() {
				if (!done
						&& (!script.readyState || script.readyState == "loaded" || script.readyState == "complete")) {
					done = true;
					script.onload = script.onreadystatechange = null;
					_callback();
				}
			};
			script.onerror = function() {
				if (loader.debugMode && window.console) {
					console.error("Loading js file \"" + url + "\" error.");
				}
				_callback();
			};
			document.getElementsByTagName("head")[0].appendChild(script);

			function _callback() {
				while (queue[url].length)
					queue[url].shift()();
				queue[url] = null;
			}
		},

		/**
		 * 加载单个模块
		 * 
		 * @param module
		 * @param callback
		 */
		loadModule : function(module, callback) {
			if (!module) {
				if (callback)
					callback();
			}

			var basePath = module["path"] ? module["path"] : "";

			// 加载前函数
			if (module["before"]
					&& "[object Function]" === Object.prototype.toString
							.call(module["before"])) {
				module["before"]();
			}

			var css = module["css"] ? module["css"] : [];
			var cssPath = "";
			if ("[object Object]" === Object.prototype.toString.call(css)) {
				cssPath = css["path"];
				if ("[object Array]" === Object.prototype.toString
						.call(css["files"])) {
					css = css["files"];
				} else {
					css = [];
				}
			}

			var js = module["js"] ? module["js"] : [];
			var jsPath = "";
			if ("[object Object]" === Object.prototype.toString.call(js)) {
				jsPath = js["path"];
				if ("[object Array]" === Object.prototype.toString
						.call(js["files"])) {
					js = js["files"];
				} else {
					js = [];
				}
			}
			var loadFiles = css.concat(js);

			(function _load() {
				if (loadFiles.length > 0) {
					var file = loadFiles.shift();
					if (/\.css$/i.test(file)) {
						loader.loadCss([ basePath, cssPath, file ].join(""),
								_load);
					} else if (/\.js$/i.test(file)) {
						loader.loadJs([ basePath, jsPath, file ].join(""),
								_load);
					} else {
						_load();
					}
				} else {
					// 加载后函数
					if ("[object Function]" === Object.prototype.toString
							.call(module["after"])) {
						module["after"]();
					}
					// 函数回调
					if (callback)
						callback();
				}
			})();
		},

		/**
		 * 加载多个模块
		 * 
		 * @param name
		 * @param callback
		 */
		load : function(modules, callback) {
			if (!modules) {
				if (callback)
					callback();
			}

			(function _load() {
				if (modules.length) {
					loader.loadModule(modules.shift(), _load);
				} else {
					if (callback) {
						callback();
					}
				}
			})();
		}
	};

	window.loader = loader;

	// 加载核心模块
	loader.load(coreModules, function() {
		return false;
		// 除login模块外强制包含HG框架
		if (!window.hg && !/login\/$/.test(uri.directory())) {
			var _uri = uri.parse();
			var _root = uri.root();
			var _mUrl = _uri["source"].replace(_root + "admin/", "");
			var _mId = _uri["params"] ? _uri["params"]["mId"] : _mUrl
					.substring(0, _mUrl.lastIndexOf("/")).replace("/", "-");
			var _mName = document.title;
			location.replace([ _root, "admin/base/index.html?mId=", _mId,
					"&mName=", encodeURI(_mName), "&mUrl=", _mUrl ].join(""));
		}
	});
})();