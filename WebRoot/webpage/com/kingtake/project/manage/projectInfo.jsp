<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目基本信息</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<link rel="stylesheet" type="text/css" href="webpage/com/kingtake/project/manage/sideCatalog/css/side-toolbar.css">
<link rel="stylesheet" type="text/css" href="webpage/com/kingtake/project/manage/sideCatalog/css/side-catalog.css">
<style type="text/css">
#content-wrap {
	border: 1px solid #e5e5e5;
	background: #fff;
}

#content-wrap:after {
	clear: both;
	content: "";
	display: block;
	height: 0;
}

.para {
	text-indent: 1em;
	margin-top: 10px;
	text-align: center;
}

.side-info {
	height: 480px;
	background: #fafafa;
}

.col-main {
	float: center;
	width: 98%;
	min-height: 800px;
}

.col-sub {
	float: left;
	width: 290px;
}

#footer {
	width: 100%;
	height: 200px;
}

.title1 {
	width: 120px;
	height: 30px;
	line-height: 30px;
	TEXT-DECORATION: none;
	color: #FE4E13;
	font-family: "Microsoft YaHei", "微软雅黑", "Microsoft JhengHei";
	border: #FE4E13 solid;
	border-width: 0px 0px 3px 0px;
	font-weight: bold
}

.title2 {
	width: 120px;
	height: 30px;
	line-height: 30px;
	TEXT-DECORATION: none;
	color: #009900;
	font-family: "Microsoft YaHei", "微软雅黑", "Microsoft JhengHei";
	border: #009900 solid;
	border-width: 0px 0px 3px 0px;
	font-weight: bold
}

.title3 {
	width: 120px;
	height: 30px;
	line-height: 30px;
	TEXT-DECORATION: none;
	color: #1C83EC;
	font-family: "Microsoft YaHei", "微软雅黑", "Microsoft JhengHei";
	border: #1C83EC solid;
	border-width: 0px 0px 3px 0px;
	font-weight: bold
}

.hr {
	border: #E6E7E9 solid;
	border-width: 0px 0px 1px 0px
}

.headline-1 .headline-2 {
	display: block;
	padding: 0px;
	border: none;
	margin: 0px;
}
</style>
</head>
<body id="container" class="easyui-layout" data-options="border:false">
  <div id="content-wrap" data-options="region:'center'">
    <input id="projectId" type="hidden" value="${projectId }">
    <input id="projectName" type="hidden" value="${projectName }">
    <div id="content" class="col-main"></div>

    <div id="side" class="col-sub"></div>
    <div id="catalog-showtag"></div>
  </div>

  <!-- <div id="footer"></div> -->
  <div id="sideContainer" data-options="region:'west'" style="width: 220px;">
    <div id="sideToolbar">
      <div id="sideCatalog"></div>
    </div>
  </div>
  <script type="text/javascript" src="webpage/com/kingtake/project/manage/sideCatalog/js/side-toolbar.js"></script>
  <script type="text/javascript" src="webpage/com/kingtake/project/manage/sideCatalog/js/side-catalog.js"></script>
  <script type="text/javascript">
            $('#sideToolbar').sideToolbar({
                width : 200,
                height : 500,
                showHeight : 500
            });

            var baikeViewInfo = {
                title : "项目信息",
                cataList : []
            };
            baikeViewInfo.cataList = [
            /* {"title":"历史缘由","level":1,"index":"1","url":"tPmProjectController.do?goUpdateForResearchGroup&id=40288aec516192ec015161a97a240003"},
            {"title":"流派分类","level":1,"index":"2","url":"tPmProjectApprovalController.do?approvalUpdateForResearchGroup&projectId=40288aec516192ec015161a97a240003"},
            //{"title":"流派分类","level":1,"index":2,"url":"啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦"},
            //{"title":"三大美味","level":1,"index":3,"url":"tPmProjectMemberController.do?tPmProjectMember&projectId=40288aec516192ec015161a97a240003"},
            {"title":"三大美味","level":1,"index":"3","url":"tPmProjectMemberController.do?tPmProjectMember&projectId=40288aec516192ec015161a97a240003&planContractFlag=1"},
            {"title":"全聚德","level":2,"index":"3_1","url":"tPmProjectMemberController.do?tPmProjectMember&projectId=40288aec516192ec015161a97a240003&planContractFlag=1"},
            //{"title":"全聚德","level":2,"index":"3_1","url":"啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦"},
            {"title":"便宜坊","level":2,"index":"3_2","url":"tPmProjectMemberController.do?tPmProjectMember&projectId=40288aec516192ec015161a97a240003&planContractFlag=1"},
            {"title":"大董","level":2,"index":"3_3","url":"tPmProjectMemberController.do?tPmProjectMember&projectId=40288aec516192ec015161a97a240003&planContractFlag=1"},
            {"title":"吃法三则","level":1,"index":"4","url":"tPmIncomeContractApprController.do?tPmIncomeContractAppr&projectId=40288aec516192ec015161a97a240003"},
            {"title":"烹制方法","level":1,"index":"5","url":"tPmTaskController.do?taskUpdateForResearchGroup&projectId=40288aec516192ec015161a97a240003"},
            {"title":"传统做法","level":1,"index":"6","url":"tPmIncomeNodeController.do?tPmIncomeNode&projectId=40288aec516192ec015161a97a240003"},
            {"title":"选材","level":2,"index":"6_1","url":"tPmPayNodeController.do?tPmPayNode&projectId=40288aec516192ec015161a97a240003"},
            {"title":"烤制","level":2,"index":"6_2","url":"tPmOutcomeContractApprController.do?tPmOutcomeContractAppr&projectId=40288aec516192ec015161a97a240003"},
            {"title":"制作程序","level":1,"index":"7","url":"tBPmChangeProjectnameController.do?tBPmChangeProjectname&projectId=40288aec516192ec015161a97a240003"} */
            ];

            var openMap = {};

            $(function() {
                $.ajax({
                    url : "tPmSidecatalogController.do?getSideCatalogList",
                    type : "POST",
                    dataType : "json",
                    data : {
                        "projectId" : "${projectId}"
                    },
                    cache : false,
                    success : function(data) {
                        baikeViewInfo.cataList = data;
                        initContentView(baikeViewInfo);
                        $('#sideCatalog').sideCatalog({
                            width : 180,
                            height: $(window).height()-50,
                            content : '#content'
                        });
                        
                        if($('#incomeContract') && '${lxyj}' =='2' && '${needContract}' =='1'){
                        	$("[href$='#incomeContract']").css("color",'red');
                        	$("[href$='#incomeContract']").attr("title",'请录入项目合同');
                        }
                    }
                });
                //初始化页面内容显示

            });

            $(document).scroll(function() {
                fixSideToolbarPosition();
            });

            //sideToolbar应为fixed定位，根据页面布局计算fixed定位时的top和left
            function fixSideToolbarPosition() {

                var windowBottom = $(document).scrollTop() + $(window).height();
                var contentBottom = $("#content-wrap").offset().top + $("#content-wrap").height();

                var bottom = (windowBottom <= contentBottom) ? 0 : windowBottom - contentBottom;
                /*var top = $(window).height() - bottom - $("#sideToolbar").height() - 10;*/

                $('#sideToolbar').css({
                    bottom : bottom
                });
            }

            //初始化页面内容显示
            function initContentView(baikeViewInfo) {
                var html = '', headlineLevel, headlineClass;
                for (var i = 0, l = baikeViewInfo.cataList.length; i < l; i++) {
                    var cata = baikeViewInfo.cataList[i];
                    //未立项的只能查看‘项目申报信息’
                    if( (cata.businessCode !='projectInfo' && cata.businessCode !='incomeContract') && '${lxStatus}' =='0'){
                    	continue;
                    }
                    //计划类项目，不显示‘项目合同’
                    if(cata.businessCode =='incomeContract' && '${lxyj}' =='1'){
                    	continue;
                    }
                  
                    if (cata.level == 1) {
                        headlineLevel = 'div', headlineClass = 'headline-1';
                    } else if (cata.level == 2) {
                        headlineLevel = 'div', headlineClass = 'headline-2';
                    }
                    if (i % 3 == 1) {
                        headlineClass = headlineClass + ' title1';
                    } else if (i % 3 == 2) {
                        headlineClass = headlineClass + ' title2';
                    } else if (i % 3 == 0) {
                        headlineClass = headlineClass + ' title3';
                    }
                    var frameStr = "";
                    if (cata.url != null) {
                        frameStr = createIframe(cata.businessCode);
                    }
                    html += '<' + headlineLevel + ' id="' + cata.businessCode + '" class="' + headlineClass + '">' +
                    /* '<span class="headline-index">' + cata.index + '</span>' + */
                    '<span class="headline-title">' +  cata.title + '</span>' + '</' + headlineLevel +'>';
                    html += "<div class=\"hr\"></div>";
                    html += '<div id="'+ cata.businessCode +'_content" class="para">' + frameStr + '</div>';
                }
                $('#content').html(html);

                getContent(baikeViewInfo.cataList[0].businessCode);
            }

            function createIframe(idx) {
                var iframe = '<iframe id="iframe_'
                        + idx
                        + '" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0" frameborder="0" style="border:0;width:100%;" onload=iFrameHeight("iframe_'
                        + idx + '")></iframe>';
                return iframe;
            }

            //获取内容
            function getContent(idx) {
                var showArray = getShowArray(idx);
                for (var i = 0; i < showArray.length; i++) {
                    var url = showArray[i].url;
                    if (!openMap[showArray[i].businessCode] && url != "") {
                        $("#iframe_" + showArray[i].businessCode).attr("src", url);
                    }
                    openMap[showArray[i].businessCode] = '1';
                }
            }

            //根据index获取元素

            function getShowArray(idx) {
                var showArray = [];
                for (var i = 0; i < baikeViewInfo.cataList.length; i++) {
                    if (baikeViewInfo.cataList[i].businessCode == idx) {
                        showArray.push(baikeViewInfo.cataList[i]);
                        if (i < baikeViewInfo.cataList.length - 1) {
                            var catai1 = baikeViewInfo.cataList[i + 1];
                            showArray.push(catai1);
                            if (catai1.url == null) {
                                showArray.push(baikeViewInfo.cataList[i + 2]);
                            }
                        }
                        //临近最后一个界面，最后一个也加载出来
                        if (i + 2 == baikeViewInfo.cataList.length - 1) {
                            showArray.push(baikeViewInfo.cataList[baikeViewInfo.cataList.length - 1]);
                        }
                    }
                }
                return showArray;
            }

            function iFrameHeight(id) {
                var ifm = document.getElementById(id);
                if(id == "iframe_incomeNode"){
                	ifm.height = 350;
                }else{
	                var subWeb = document.frames ? document.frames[id].document : ifm.contentDocument;
	                if (ifm != null && subWeb != null) {
	                    ifm.height = subWeb.body.scrollHeight;
	                }
                }
            }
            
            function getParameter(){
                var projectName = $("#projectName").val();
                return projectName;
            }
        </script>
</body>
</html>