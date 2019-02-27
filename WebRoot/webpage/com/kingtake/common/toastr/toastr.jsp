<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <link href="webpage/com/kingtake/common/toastr/bootstrap-combined.min.css" rel="stylesheet"> -->
<link href="webpage/com/kingtake/common/toastr/toastr.css" rel="stylesheet" type="text/css" />
<script src="webpage/com/kingtake/common/toastr/toastr.js"></script>
<script src="webpage/com/kingtake/common/toastr/glimpse.js"></script>
<script src="webpage/com/kingtake/common/toastr/glimpse.toastr.js"></script>
<script type="text/javascript">
    function toastrMsg(businessCode) {
        $.ajax({
            url : 'tBFormTipController.do?getTipContent',
            data : {
                'businessCode' : businessCode
            },
            type : 'POST',
            dataType : 'html',
            cache : false,
            success : function(data) {
                toastr.options = {
                    closeButton : true,
                    debug : true,
                    positionClass : 'toast-top-full-width',
                    onclick : null,
                    showMethod : "fadeIn",
                    hideMethod : "fadeOut"
                };

                toastr.success(data);
            }
        });
    }
</script>