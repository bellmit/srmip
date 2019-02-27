$(document).ready(function(){
	
	$("#password").bind({focus: function() {
				var obj = $(this);
				if (obj.val() == "密码 Password") {
					//obj.attr("type", "password");
					document.getElementById("password").type = "password";
					obj.val("");
					obj.css({color : "#fff"});
				}
		},blur: function() {
			var obj = $(this);
			if (obj.val() == "") {
				//obj.attr("type", "text");
				document.getElementById("password").type = "text";
				obj.val("密码 Password");
				obj.css({color : "#96bae8"});
			}
		}
	});
	
	$(".fn-rememberaccout").click(function(){
		$(this).toggleClass("rememberaccout-active");
		
		$("#on_off").val($(this).hasClass("rememberaccout-active")?"1":"0");
	})
	
	
	setpageSize();
    $(window).bind("resize",resizebind);
	
});


/*浏览器窗口重置事*/
function resizebind(){
     setpageSize();
}
/*浏览器窗口重置事-END*/


function setpageSize(){
    $("body").width($(window).width());
	$("body").height($(window).height());
	$(".minPart").css("top",($(window).height()-$(".minPart").height())/2);
}


