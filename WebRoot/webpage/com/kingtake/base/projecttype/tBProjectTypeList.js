function initModule(){
    $.ajax({
        url:'tBProjectTypeController.do?initModule',
        cache:false,
        type:'GET',
        dataType:'json',
        success:function(data){
            tip(data.msg);
        }
    });
}