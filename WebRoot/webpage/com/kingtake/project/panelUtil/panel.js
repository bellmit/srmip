function apprOrBasic(div){
	var div = $(div);
	if(div.attr("state") == "live"){
		//隐藏
		div.attr("state", "dead");
		div.children("a").removeClass("expand");
		div.children("span").html("展开");
		div.parent().next().hide();
		
	}else if(div.attr("state") == "dead"){
		//展开，并隐藏其他展开
		$(".tool").each(function(index, ele){
			var ele = $(ele);
			if(ele.attr("state") == "live"){
				//隐藏
				ele.attr("state", "dead");
				ele.children("a").removeClass("expand");
				ele.children("span").html("展开");
				ele.parent().next().hide();
			}
		});
		
		div.attr("state", "live");
		div.children("a").addClass("expand");
		div.children("span").html("隐藏");
		div.parent().next().show();
	}
}

//点击整条消息框
function apprOrBasic2(parentDiv){
	var div = $(parentDiv).find("div[class='tool']");
	if(div.attr("state") == "live"){
		//隐藏
		div.attr("state", "dead");
		div.children("a").removeClass("expand");
		div.children("span").html("展开");
		div.parent().next().hide();
		
	}else if(div.attr("state") == "dead"){
		//展开，并隐藏其他展开
		$(".tool").each(function(index, ele){
			var ele = $(ele);
			if(ele.attr("state") == "live"){
				//隐藏
				ele.attr("state", "dead");
				ele.children("a").removeClass("expand");
				ele.children("span").html("展开");
				ele.parent().next().hide();
			}
		});
		
		div.attr("state", "live");
		div.children("a").addClass("expand");
		div.children("span").html("隐藏");
		div.parent().next().show();
	}
}