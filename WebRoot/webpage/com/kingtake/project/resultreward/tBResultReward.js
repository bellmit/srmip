function chooseDepart(inputId,inputName) {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:departController.do?selectDepartTree&scientificInstitutionFlag=0', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, width: 500, height: 350, opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: function (){
                	iframe = this.iframe.contentWindow;
                    var departname = iframe.getdepartListSelections('text');
                    if ($('#'+inputName).length >= 1) {
                        $('#'+inputName).val(departname);
                        $('#'+inputName).blur();
                    }
                    if ($("input[name='"+inputName+"']").length >= 1) {
                        $("input[name='"+inputName+"']").val(departname);
                        $("input[name='"+inputName+"']").blur();
                    }
                    var id = iframe.getdepartListSelections('id');
                    if (id !== undefined && id != "") {
                        $('#'+inputId).val(id);
                        $("input[name='"+inputId+"']").val(id);
                    }
                }, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:departController.do?selectDepartTree&scientificInstitutionFlag=0', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, parent: windowapi, width: 500, height: 350, opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: function(){
                	iframe = this.iframe.contentWindow;
                    var departname = iframe.getdepartListSelections('text');
                    if ($('#'+inputName).length >= 1) {
                        $('#'+inputName).val(departname);
                        $('#'+inputName).blur();
                    }
                    if ($("input[name='"+inputName+"']").length >= 1) {
                        $("input[name='"+inputName+"']").val(departname);
                        $("input[name='"+inputName+"']").blur();
                    }
                    var id = iframe.getdepartListSelections('id');
                    if (id !== undefined && id != "") {
                        $('#'+inputId).val(id);
                        $("input[name='"+inputId+"']").val(id);
                    }
                }, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }

//???????????????????????????
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"????????????",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '??????',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// ???????????????????????????????????????
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//????????????????????????????????? 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// ??????ckfinder????????????????????????
		finder.selectActionData = inputId; //???????????????input ID
		finder.removePlugins = 'help';// ????????????(????????????)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// ???????????????????????????????????????
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//????????????????????????????????? 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// ??????ckfinder????????????????????????
	finder.selectActionData = inputId; //???????????????input ID
	finder.removePlugins = 'help';// ????????????(????????????)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value?????????,id?????????
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}