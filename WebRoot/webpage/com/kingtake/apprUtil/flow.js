$(function(){
    var read = $("#read").val();
    if (read == '1') {
        //无效化所有表单元素，只能进行查看
        $(":input").attr("disabled", "true");
        //隐藏选择框和清空框
        $("a[icon='icon-search']").css("display", "none");
        $("a[icon='icon-redo']").css("display", "none");
        $("a[icon='icon-save']").css("display", "none");
        $("a[icon='icon-ok']").css("display", "none");
        //隐藏下拉框箭头
        $(".combo-arrow").css("display", "none");
        //隐藏添加附件
        $("#filediv").parent().css("display", "none");
        //隐藏附件的删除按钮
        $(".jeecgDetail").parent().css("display", "none");
        //隐藏easyui-linkbutton
        $(".easyui-linkbutton").css("display", "none");
    } 
});