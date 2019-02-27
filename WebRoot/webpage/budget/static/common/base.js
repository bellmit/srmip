(function (window) {
    var $ = window.$;

    //控制台处理，避免低版本IE浏览器JS错误
    window.console = window.console || {
        log: function (str) {
        }, debug: function (str) {
        }, error: function (str) {
        }
    };
    window.console.debug = window.console.debug || function (str) {
    };

    var Base = {};

    Base.table = function(options){
        var that = this;
        var defaultOptions = {
            method: 'post',
            contentType: 'application/json',
            parseData: function(res){
                var code = (res.code==200 || res.code==204) ? 0 : res.code;
                return {
                    "code": code, //解析接口状态
                    "msg": res.description, //解析提示文本
                    "data": res.data.rows, //解析数据列表
                    "rowCount":res.data.rowCount
                };
            },
            response: {countName: 'rowCount'},
            page: true,
            text: {none: '暂无相关数据' },
            request: {
                pageName: 'curr', //页码的参数名称，默认：page
                limitName: 'nums' //每页数据量的参数名，默认：limit
            }
        };
        return layui.table.render($.extend({}, defaultOptions, options));
    };
    /**
     * 基于jQuery Ajax二次封装，统一处理失败，会话失效问题
     * @param options 主要参数如下：
     * type 请求类型【POST,GET】，默认POST
     * dataType 请求数据类型，默认json，如请求文本，xml格式数据需要调整
     * success 成功回调函数，参数（1、返回数据，2、请求返回状态，3、请求参数）
     * showException 是否提示后台成功返回的异常信息，默认true
     */
    Base.ajax = function (options) {
        if(!$.isEmptyObject(options.data)){
            options.data = JSON.stringify(options.data);
        }else{
            options.data = "{}";
        }
        var defaultOptions = {
            method: 'post',
            contentType: 'application/json',
            timeout: 100000,//100秒后超时
            success: function (data, textStatus) {
                //统一处理后台返回的异常信息
                if (options.showException != false && typeof(data) == "object" && data && data.code != 0) {
                    layer.alert(data.description);
                    return;
                }
                if ($.isFunction(options.callback)) {
                    options.callback(data, textStatus, options.data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

                if (textStatus == 'timeout') {
                    layer.alert("请求超时，请稍候重试！");
                    return;
                }
                //session超时，开迷你登录窗
                if (typeof(XMLHttpRequest.status) == 'number' && 9999 == XMLHttpRequest.status) {
                    //开窗登录
                    layer.alert("session超时");
                    /*layer.openMinLoginWin(function () {
                        Base.ajax(options);
                    });*/
                    return;
                }
                layer.alert("服务器繁忙，请稍候重试！");
            }
        };
        $.ajax($.extend({}, defaultOptions, options));
    }
    /**
     * 获取url 参数
     * @param key
     */
    Base.getUrlParams = function(key) {
        var psraStr = window.location.search.substr(1);
        var params = {};
        if($.isEmptyObject(psraStr)){
            return params;
        }
        if(!$.isEmptyObject(key)){
            var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
            params = unescape(psraStr.match(reg)[2]);
        }else{
            var paramStr = psraStr.split("&");
            var params = {};
            for(var i = 0 ; i < paramStr.length ; i++){
                var str = paramStr[i].split("=");
                if(!$.isEmptyObject(str[0]) && !$.isEmptyObject(str[1])){
                    params[str[0]] = str[1];
                }
            }
        }
        return params;
    };
    /**
     * 渲染模板中的占位符
     * @param template
     * @param json
     * @param handleFn
     * @returns {string | * | void}
     */
    Base.renderTemplate = function(template, json, handleFn) {

        return template.replace(/([{]{1}\w+[}]{1})/g, function(tmpWord){
            var tmpKey = tmpWord.substring(1,tmpWord.length -1);
            var tmpVal = json[tmpKey];
            if ($.isFunction(handleFn)) {
                tmpVal = handleFn.call(this, tmpVal, tmpKey, json);
            }
            if (typeof(tmpVal) == "string" || typeof(tmpVal) == "number") {
                return tmpVal;
            }

            return '';
        });
    };
    window.BaseUtil = Base;
})(window);
