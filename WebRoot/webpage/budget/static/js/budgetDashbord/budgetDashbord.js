var projectSchoolList,renderMainTable, budgetLeftTable,yearBudgetLeftTable, budgetType = '', mainId = '', budgetTotalObj = null, budgetTotalId = '', budgetYearId = '', table = null, treeGrid = null, layer = null;
var path = "\\";
var toMoneyStyle = function(val){
//金额转换 分->元 保留2位小数 并每隔3位用逗号分开 1,234.56
	val = val ? val : 0;
    var str = Number(val).toFixed(2) + '';
    var intSum = str.substring(0,str.indexOf(".")).replace( /\B(?=(?:\d{3})+$)/g, ',' );//取到整数部分
    var dot = str.substring(str.length, str.indexOf(".")); //取到小数部分搜索
    var ret = intSum + dot;
    return intSum;
};

function load(){
	layui.use(['table', 'treeGrid', 'layer', 'element'], function () {
		path = uri.getBasePath();
	    table = layui.table;
	    layer = layui.layer;
	    treeGrid = layui.treeGrid;
	    // 渲染主项目表格
	    renderMainTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        table.render({
	            elem: '#budgetDashbordTable',
	            url: path + 'TpmBudgetController.do?getlxProjectList',
	            cols: [[
	                {type:  'numbers', align: 'center', title: '序号', width: 40, fixed: 'left'},
	                {field: 'PROJECT_NO', align: 'left', title: '项目编码', width: 150,sort: true, fixed: 'left'},
	                {field: 'PROJECT_NAME', align: 'left', title: '项目名称', width: 160, fixed: 'left'},
	                {field: 'DEVELOPER_DEPART_EN', align: 'left', title: '承建单位', width: 160},
	                {field: 'MEMBERS_NAME', align: 'left', title: '负责人', width: 80},
	                {field: 'XMML', align: 'left', title: '项目类型', width: 120},
	                {field: 'XMLBEN', align: 'left', title: '项目类别', width: 100},
	                {field: 'BEGIN_DATE_EN', align: 'left', title: '起始年度', width: 120,sort: true},
	                {field: 'END_DATE_EN', align: 'left', title: '终止年度', width: 120,sort: true},
	                {field: 'ALL_FEE', align: 'right', title: '总经费', width: 120,sort: true, templet: function(data) {
	                	return toMoneyStyle(data.ALL_FEE);
	            	}},
	                {field: 'ACCOUNTING_CODE', align: 'left', title: '会计科目', width: 100},
	                {field: 'SUBJECT_CODE', align: 'left', title: '会计项目代码', width: 120},
	                {field: 'ZMYE', align: 'right', title: '科目余额', width: 120,sort: true, templet: function(data) {
	                	return toMoneyStyle(data.ZMYE);
	            	}},
	                {field: 'LX_STATUS', align: 'left', title: '项目状态', width: 100,
	                	templet: function(d){
	                		if(d.LX_STATUS =="1"){
	                			return "立项"
	                		}else if(d.LX_STATUS =="0"){
	                			return "未立项"
	                		}
		                    return d
		                }},
	                {field: 'SOURCE_UNIT', align: 'left', title: '项目类来源', width: 120},
	                {field: 'PLAN_CONTRACT_REF_NO', align: 'left', title: '合同编号', width: 120},
	                {field: 'PLAN_CONTRACT_NAME', align: 'left', title: '合同名称', width: 160},
	                {field: 'BZ', align: 'left', title: '备注'},
	                {field: 'xxxxxxxx', align: 'left', title: '研制内容', width: 120},
	                {field: 'xxxxxxxx', align: 'left', title: '结题日期', width: 120},
	                {field: 'FUNDS_NAME', align: 'left', title: '经费类型', width: 120,sort: true},
	                {field: 'YSLX_EN', align: 'left', title: '预算类别', width: 120},
	                {field: 'JJFJSFS', align: 'left', title: '间接费计算方式', width: 160,
                        templet: function(d){
                            if(d.JJFJSFS =="1"){
                                return "根据直接费计算间接费"
                            }else if(d.JJFJSFS =="2"){
                                return "根据总经费计算绩效"
                            }else if(d.JJFJSFS =="3"){
                                return "无间接费"
                            }else if(d.JJFJSFS =="4"){
                                return "合同直接约定间接费"
                            }else if(d.JJFJSFS =="5"){
                                return "科研管理部门下拨"
                            }else if(d.JJFJSFS =="6"){
                                return "总经费的1/21"
                            }
                            return d.JJFJSFS
                        }},
	                {field: 'DXBL', align: 'left', title: '大学比例', width: 120},
	                {field: 'ZRDWBL', align: 'left', title: '责任单位比例', width: 120},
	                {field: 'CYDWBL', align: 'left', title: '承研单位比例', width: 120},
	                {field: 'XMZBL', align: 'left', title: '项目组比例', width: 120},
	                {field: 'MEMBERS_ID', align: 'left', title: '项目成员ID', width: 160, hide: true},
	                {field: 'PROJECT_MANAGER', align: 'center', title: '负责人', width: 80, hide: true},
	                {field: 'ID', align: 'center', title: 'ID', width: 100,sort: true, hide: true},
	                {field: 'FEE_TYPE', align: 'center', title: '经费类型ID', width: 100,sort: true, hide: true},
	            ]],
	            id: 'budgetDashbordTable',
	            page: true,
	            toolbar: '#toolbarDemo',
	            //skin: 'line', //行边框风格
	            //even: true, //开启隔行背景
	            size: 'sm', //小尺寸的表格
	            //defaultToolbar: ['filter', 'print', 'exports'],
	            where: params,
	            //loading:false,
	            text: {
	                none: '暂无相关数据'
	            },
	            done: function (res, curr, count) {
	                layer.closeAll('loading');
	                //选中第一行
	                if(res.data && res.data.length>0){
	                    $($("#budgetDashbordTable").next().find("tr")[1]).click();
	                }
	            },
	            height: 350
	        });
	    };

	    // 渲染总预算左侧表格
	    budgetLeftTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        table.render({
	            elem: '#budgetLeftTable',
	            url: path + 'ysController.do?getZysFundsList',
	            cols: [[
	                {field: 'edit', width: 160, align: 'center', title: '操作', templet: function(data) {
	                	var FINISH_FLAG = Number(data.FINISH_FLAG || 0);
	                	var CW_STATUS = Number(data.CW_STATUS || 0);
	                    var a = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="editTotal(\''
	                        + data.ID
	                        + '\',1)">编辑</a> ';
	                    var b = '<a class="layui-btn layui-btn-xs layui-btn-danger" onClick="delTotal(\''
	                        + data.ID
	                        + '\',1)">删除</a> ';
	                    var c = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="printTotal(\''
	                        + data.ID +'\',\''+data.CREATE_DATE+'\',\''+data.FUNDS_TYPE_NAME+'\')">打印</a> ';
	                    var d = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="auditTotal(\''
	                        + data.ID
	                        + '\')">审核</a> '
	                    if(FINISH_FLAG > 0 || CW_STATUS > 0){
	                    	return c;
	                    }
	                    return a + b;
	                }},
	                {field:'CREATE_DATE', align: 'center', title: '编制日期', width: 120},
	                {field:'FUNDS_TYPE_NAME', align: 'center', title: '预算类型', width: 120},
	                {field: 'TOTAL_FUNDS', align: 'center', title: '预算金额',width:120, templet: function(data) {
	                	return toMoneyStyle(data.TOTAL_FUNDS);
	            	}},
	                {field: 'FINISH_FLAG_NAME', align: 'center', title: '科研审核',width:100},
					{field: 'CW_STATUS_NAME', align: 'center', title: '财务审核',width:100}
	            ]],
	            id: 'budgetLeftTable',
	            page: false,
	            where: params,
	            totalRow:false,
	            loading:false,
	            size: 'sm', //小尺寸的表格
	            text: {
	                none: '暂无相关数据'
	            },
	            done: function (res, curr, count) {
	                layer.closeAll('loading');
	                if(res.data && res.data.length>0) {
	                    $($("#budgetLeftTable").next().find("tr")[1]).dblclick()
	                }
	            }
	        });
	    };

	    // 渲染总预算右侧表格
	    var BudgetRightTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        treeGrid.render({
	            id: 'BudgetRightTable',
	            elem: '#BudgetRightTable',
	            idField:'ID', //必須字段
	            url: path + 'ysController.do?getZysDetailList',
	            treeId:'ID',//树形id字段名称
	            treeUpId:'PID',//树形父id字段名称
	            treeShowName:'DETAIL_NAME',//以树形式显示的字段
	            height: 'auto',
	            iconOpen:true, //是否显示图标【默认显示】
	            isOpenDefault:true, //节点默认是展开还是折叠【默认展开】
	            size: 'sm', //小尺寸的表格
	            method:'post',
	            cols: [[
	                {field: 'CATEGORY_CODE_DTL', width: 100, align: 'left', title: '预算编码'},
	                {field: 'DETAIL_NAME', width: 240, title: '明细预算名称'},
	                {field: 'UNIT',width: 60, align: 'center', title: '单位'},
	                {field: 'PRICE',width: 60, align: 'center', title: '单价'},
	                {field: 'AMOUNT', width: 60, align: 'center', title: '数量'},
	                {field: 'MONEY', align: 'right', title: '金额', width: 100, templet: function(data) {
	                	return toMoneyStyle(data.MONEY);
	            	}},
	                {field: 'REMARK', title: '备注'}
	            ]],
	            isPage: false,
	            where: params,
	            loading:false,
	            done: function () {
	                layer.closeAll('loading');
	            },
	            text: {
	                none: '暂无相关数据'
	            },
	            onClickRow:function (index, o) {
	                console.log(index,o,"单击！");
	            },
	            onDblClickRow:function (index, o) {
	                console.log(index,o,"双击");
	            }
	        });
	    };

	    // 渲染年度预算左侧表格
	    yearBudgetLeftTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        table.render({
	            elem: '#yearBudgetLeftTable',
	            url: path + 'ysController.do?getNdysFundsList',
	            cols: [[
					{field: 'edit', width: 160, align: 'center', title: '操作', templet: function(data) {
						var FINISH_FLAG = Number(data.FINISH_FLAG || 0);
	                	var CW_STATUS = Number(data.CW_STATUS || 0);
					    var a = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="editTotal(\''
					        + data.ID
					        + '\',2)">编辑</a> ';
					    var b = '<a class="layui-btn layui-btn-xs layui-btn-danger" onClick="delTotal(\''
					        + data.ID
					        + '\',2)">删除</a> ';
					    var c = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="printNd(\''
					        + data.ID +'\',\''+data.CREATE_DATE+'\')">打印</a> ';
					    var d = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="auditTotal(\''
	                        + data.ID
	                        + '\')">审核</a> '
	                    if(FINISH_FLAG > 0 || CW_STATUS > 0){
	                    	return c;
	                    }
					    return a + b;
					}},
					{field:'CREATE_DATE', align: 'center', title: '编制日期', width: 120},
					{field:'FUNDS_TYPE_NAME', align: 'center', title: '预算类型', width: 120},
					{field: 'TOTAL_FUNDS', align: 'center', title: '预算金额',width:120, templet: function(data) {
	                	return toMoneyStyle(data.TOTAL_FUNDS);
	            	}},
					{field: 'FINISH_FLAG_NAME', align: 'center', title: '科研审核',width:100},
					{field: 'CW_STATUS_NAME', align: 'center', title: '财务审核',width:100}
	            ]],
	            id: 'yearBudgetLeftTable',
	            page: false,
	            where: params,
	            loading:false,
	            size: 'sm', //小尺寸的表格
	            text: {
	                none: '暂无相关数据'
	            },
	            done: function (res, curr, count) {
	                layer.closeAll('loading');
	                if(res.data && res.data.length>0) {
	                    $($("#yearBudgetLeftTable").next().find("tr")[1]).dblclick()
	                }
	            }
	        });
	    };

	    // 渲染年度预算右侧表格
	    var yearBudgetRightTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        treeGrid.render({
	            id: 'yearBudgetRightTable',
	            elem: '#yearBudgetRightTable',
	            idField:'ID', //必須字段
	            url: path + 'ysController.do?getNdysDetailList',
	            treeId:'ID',//树形id字段名称
	            treeUpId:'PID',//树形父id字段名称
	            treeShowName:'DETAIL_NAME',//以树形式显示的字段
	            height: 'auto',
	            iconOpen:true, //是否显示图标【默认显示】
	            isOpenDefault:true, //节点默认是展开还是折叠【默认展开】
	            method:'post',
	            size: 'sm', //小尺寸的表格
	            cols: [[
	                {field: 'CATEGORY_CODE_DTL', width: 100, align: 'left', title: '预算编码'},
	                {field: 'DETAIL_NAME', width: 240, title: '明细预算名称'},
	                {field: 'UNIT',width: 60, align: 'center', title: '单位'},
	                {field: 'PRICE',width: 60, align: 'center', title: '单价'},
	                {field: 'AMOUNT', width: 60, align: 'center', title: '数量'},
	                {field: 'MONEY', align: 'right', title: '金额', width: 100, templet: function(data) {
	                	return toMoneyStyle(data.MONEY);
	            	}},
	                {field: 'REMARK', title: '备注'}
	            ]],
	            isPage: false,
	            where: params,
	            loading:false,
	            done: function () {
	                layer.closeAll('loading');
	            },
	            text: {
	                none: '暂无相关数据'
	            },
	            onClickRow:function (index, o) {
	                console.log(index,o,"单击！");
	            },
	            onDblClickRow:function (index, o) {
	                console.log(index,o,"双击");
	            }
	        });
	    };

	    // 渲染总预算合计表格
	    var totalBudgetTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        treeGrid.render({
	            elem: '#totalBudgetTable',
	            url: path + 'ysController.do?getZysTotalList',
	            method:'post',
	            id: 'totalBudgetTable',
	            page: false,
	            idField:'ID', //必須字段
	            treeId:'ID',//树形id字段名称
	            treeUpId:'PID',//树形父id字段名称
	            treeShowName:'DETAIL_NAME',//以树形式显示的字段
	            height: 'auto',
	            size: 'sm', //小尺寸的表格
	            iconOpen:true, //是否显示图标【默认显示】
	            isOpenDefault:true, //节点默认是展开还是折叠【默认展开】
	            cols: [[
	                {field: 'ID', width: 100, align: 'center', title: '预算编码'},
	                {field: 'DETAIL_NAME', minWidth: 160, title: '明细预算名称'},
	                {field: 'CATEGORY',width: 70, align: 'center', title: '单位'},
	                {field: 'CATEGORY',width: 70, align: 'center', title: '单价'},
	                {field: 'DETAIL', width: 70, align: 'center', title: '数量'},
	                {field: 'MONEY', align: 'right', title: '金额', width: 170, templet: function(data) {
	                	return toMoneyStyle(data.MONEY);
	            	}},
	                {field: 'REMARK', title: '备注',hidden:true}
	            ]],
	            where: params,
	            loading:false,
	            text: {
	                none: '暂无相关数据'
	            },
	            done: function () {
	                layer.closeAll('loading');
	            }
	        });
	    };

	    // 渲染年度预算合计表格
	    var yearBudgetTotalTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        tableIns = treeGrid.render({
	            elem: '#yearBudgetTotalTable',
	            url: path + 'ysController.do?getNdysTotalList',
	            cols: [[
	                {field: 'ID', width: 100, align: 'center', title: '预算编码'},
	                {field: 'DETAIL_NAME', minWidth: 160, title: '明细预算名称'},
	                {field: 'CATEGORY',width: 70, align: 'center', title: '单位'},
	                {field: 'CATEGORY',width: 70, align: 'center', title: '单价'},
	                {field: 'DETAIL', width: 70, align: 'center', title: '数量'},
	                {field: 'MONEY', align: 'right', title: '金额', width: 170},
	                {field: 'REMARK', title: '备注',hidden:true}
	            ]],
	            id: 'yearBudgetTotalTable',
	            idField:'ID', //必須字段
	            treeId:'ID',//树形id字段名称
	            treeUpId:'PID',//树形父id字段名称
	            treeShowName:'DETAIL_NAME',//以树形式显示的字段
	            height: 'auto',
	            iconOpen:true, //是否显示图标【默认显示】
	            isOpenDefault:true, //节点默认是展开还是折叠【默认展开】
	            page: false,
	            where: params,
	            loading:false,
	            size: 'sm', //小尺寸的表格
	            text: {
	                none: '暂无相关数据'
	            },
	            done: function () {
	                layer.closeAll('loading');
	            }
	        });
	    };

	    // 渲染分配金额表格
	    var distributionMoneyTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        table.render({
	            elem: '#distributionMoneyTable',
	            url: path + 'ysController.do?getFpTotalList',
	            cols: [[
	                {field: 'edit', width: 220, align: 'center', title: '操作', templet: function(data) {
	                        var a = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="printJefp(\''
	                            + data.ID + '\',\''+data.FPID + '\',\''+data.FUNDS_TYPE+'\')">打印</a> ';
	                        return a;
	                 }},
	                {field:'FUNDS_TYPE_TEXT', align: 'center', title: '到款来源', width: 120},
	                {field: 'APPLY_USER', align: 'center', title: '申请人', width: 80},
	                {field: 'SUBMIT_TIME', align: 'center', title: '申请时间', width: 180},
	                {field: 'APPLY_MOUNT', align: 'center', title: '金额', width: 140},
	                {field: 'APPLY_YEAR', align: 'center', title: '年限', width: 80},
	                {field: 'AUDIT_USERNAME', align: 'center', title: '审核人员', width: 100},
	                {field: 'AUDIT_TIME', align: 'center', title: '审核时间', width: 180},
	                {field: 'YS_STATUS', align: 'center', title: '是否预算', width: 100,templet: function(d) {
	                	var ysStatus = d.YS_STATUS || 0;
	                	if(ysStatus > 0){
	                		return '是';
	                	}
	                	return '否';
	                }},
	                {field: 'ADUIT_STATUS_TEXT', align: 'center', title: '科研审核状态', width: 140},
	                {field: 'CW_STATUS_TEXT', align: 'center', title: '财务审核状态', width: 140}

	            ]],
	            id: 'distributionMoneyTable',
	            page: false,
	            where: params,
	            loading:false,
	            size: 'sm', //小尺寸的表格
	            text: {
	                none: '暂无相关数据'
	            },
	            done: function () {
	                layer.closeAll('loading');
	            }
	        });
	    };

	    // 渲染项目执行情况表格
	    var projectExecTable = function (params) {
	        layer.load(2);
	        treeGrid.render({
	            id: 'projectExecTable',
	            elem: '#projectExecTable',
	            idField:'ID', //必須字段
	            url : path + "ysController.do?getProjectExecList",
	            // url: "../../json/treeData.json",
	            treeId:'ID',//树形id字段名称
	            treeUpId:'PID',//树形父id字段名称
	            treeShowName:'DETAIL_NAME',//以树形式显示的字段
	            height: 'auto',
	            iconOpen:true, //是否显示图标【默认显示】
	            isOpenDefault:true, //节点默认是展开还是折叠【默认展开】
	            method:'post',
	            where: params,
	            cols: [[
	                {field: 'CATEGORY_CODE_DTL', width: 100, align: 'left', title: '明细编码'},
	                {field: 'DETAIL_NAME', minWidth: 180, title: '项目名称', templet: function(d) {
	                        var html = "<span></span>";
	                        if(!d.KZ){
	                            if (d.DETAIL_NAME) {
                                    html = '<span>'+ d.DETAIL_NAME + '</span>';
                                } else {
                                    html = '<span>开支</span>';
                                }

	                        }
	                        if(d.KZ == 1) {
	                            if (d.DETAIL_NAME) {
                                    html = '<span style="font-family: SimHei;color:darkgreen;font-weight: bold">'+ d.DETAIL_NAME + '</span>';
                                } else {
                                    html = '<span style="font-family: SimHei;color:darkgreen;font-weight: bold">开支</span>';
	                            }
	                        }
	                        return html;
	                    }},
	                {field: 'JZRQ',width: 120, align: 'right', title: '记账日期'},
	                {field: 'MONEY',width: 150, align: 'right', title: '预算金额'},
	                {field: 'KZJE', width: 150, align: 'right', title: '执行金额'},
	                {field: 'JDJE', width: 150, align: 'right', title: '借款金额'},
	                {field: 'YE', width: 150, align: 'right', title: '余额'},
	                {field: 'ZXH', width: 120, align: 'right', title: '审核序号'},
	                {field: 'KJPZH', width: 120, align: 'left',title: '会计凭证号'}
	            ]],
	            isPage: false,
	            size: 'sm', //小尺寸的表格
	            done: function () {
	                layer.closeAll('loading');
	            },
	            onClickRow:function (index, o) {
	                console.log(index,o,"单击！");
	                if(o.ID === "0-0") {
	                    layer.msg('该行内容不允许编辑');
	                    return '';
	                }
	            },
	            onDblClickRow:function (index, o) {
	                console.log(index,o,"双击");
	                if(o.ID === "0-0") {
	                    layer.msg('该行内容不允许编辑');
	                    return '';
	                }
	            }

	        });
	    };
	    // 渲染校内协作
	    var renderSchoolTable = function (params) {
	        layer.load(2);
	        //方法级渲染
	        table.render({
	            elem: '#projectSchoolTable',
	            url: path + 'ysController.do?getXzProjectList',
	            cols: [[
	                    {field: 'edit', width:160, align: 'center', title: '操作', templet: function(data) {
	                    	var r = '';
	                        var a = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="editSchool(\''+data.ID+'\' )">金额分配</a> ';
	                        var b = '<a class="layui-btn layui-btn-xs layui-btn-normal" onClick="submitAudit(\''+data.INCOME_ID+'\' )">发送审核</a> ';
	                        var ALL_FEE = data.ALL_FEE || 0;
	                        var FP_MONEY = data.FP_MONEY || 0;	//已审核
	                        var ZT_MONEY = data.ZT_MONEY || 0;	//在途
	                        var D_SH_MONEY = data.D_SH_MONEY || 0;	//待审核
	                        var kfp = ALL_FEE - FP_MONEY - ZT_MONEY;
	                        if(kfp > 0 && D_SH_MONEY > 0){
	                        	r = a + b;
	                        }else if(kfp > 0 && D_SH_MONEY <= 0){
	                        	r = a;
	                        }
	                        return r;
	                 }},
	                {field: 'PROJECT_NO', align: 'left', title: '项目编码', width: 150},
	                {field: 'PROJECT_NAME', align: 'left', title: '项目名称', width: 240},
	                {field: 'PROJECT_MANAGER', align: 'left', title: '负责人', width: 160},
	                {field: 'BEGIN_DATE', align: 'left', title: '起始年度', width: 140,sort: true},
	                {field: 'END_DATE', align: 'left', title: '终止年度', width: 140,sort: true},
	                {field: 'ALL_FEE', align: 'right', title: '总经费', width: 120,sort: true},
	                {field: 'FP_MONEY', align: 'right', title: '已分配金额', width: 120,sort: true},
	                {field: 'ZT_MONEY', align: 'right', title: '在途金额', width: 120,sort: true},
	                {field: 'D_SH_MONEY', align: 'right', title: '待审核金额', width: 120,sort: true}

	            ]],
	            id: 'projectSchoolTable',
	            treeId:'ID',//树形id字段名称
	            treeUpId:'PID',//树形父id字段名称
	            height: 'auto',
	            page: true,
	            method:'post',
	            where: params,
	            text: {
	                none: '暂无相关数据'
	            },
	            done: function (res, curr, count) {
	            	projectSchoolList = res.data;
	                layer.closeAll('loading');
	            }

	        });
	    };

	    function requestAllTableData (params) {
	        budgetLeftTable(params);
	        BudgetRightTable(params);

	        yearBudgetLeftTable(params);
	        yearBudgetRightTable(params);

	        totalBudgetTable(params);
	        yearBudgetTotalTable(params);

	        distributionMoneyTable(params);
	        projectExecTable(params);
	        renderSchoolTable(params);

	    }

	    // 初始时加载主表
	    renderMainTable();

	    // 默认请求一次，未带项目id应无数据无数据
	    //requestAllTableData();

	    //监听主表格行单击事件
	    table.on('row(budgetDashbordTable)', function(obj){
	    	obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
	        budgetType = obj.data.CATEGORY_CODE;
	        budgetTotalObj = obj.data;

	        mainId = budgetTotalObj.id;
	        let params = {
	            T_P_ID : budgetTotalObj.ID,
	            FEE_TYPE: budgetTotalObj.FEE_TYPE
	        };
	        requestAllTableData(params);
	    });

	    // 点击按钮触发动作
	    var active = {
			// 搜索
	        reload: function(){
	            var qryKey = $('#condition').val();
	            var qryValue = $('#queryValue').val();
	            //执行重载
	            table.reload('budgetDashbordTable', {
	                page: {
	                    curr: 1 //重新从第 1 页开始
	                },
	                where: {
	                	key:qryKey,
	                	value:qryValue
	                }
	            });
	        }, // 新增总预算
	        addTotal: function(){
	            if(budgetTotalObj === null) {
	                layer.msg('请先选择对应项目');
	                return '';
	            }
	            $.ajax({
	                url : path + "ysController.do?getZysType",
	                type : "post",
	                data : {T_P_ID: budgetTotalObj.ID},
	                success : function(backData) {
                        if(backData.code == 8 || backData.code == 9 || backData.code == 10){
                            layer.alert(backData.value);
                            return;
                        }
	                	budgetTotalObj = $.extend({}, budgetTotalObj, backData);
	                	var index = layer.open({
	                        type : 2,
	                        title : '新增' + backData.value,
	                        maxmin : false,
	                        shadeClose : false, // 点击遮罩关闭层
	                        area : [ '900px', '600px' ],
	                        content :path + 'webpage/budget/templates/budgetDashbord/totalBudget2.html?T_P_ID=' +
	                    		budgetTotalObj.ID + "&FUNDS_TYPE=" + budgetTotalObj.code
	                    });
	                	layer.full(index);
	                }
	            });

	        },// 新增年度预算
	        addYear: function(){
	        	if(budgetTotalObj === null) {
	                layer.msg('请先选择对应项目');
	                return '';
	            }
	        	$.ajax({
	                url : path + "ysController.do?getNdysType",
	                type : "post",
	                data : {T_P_ID: budgetTotalObj.ID},
	                success : function(backData) {
	                	if(backData.code < 0){
	                		layer.alert(backData.value);
	                		return;
	                	}
	                	budgetTotalObj = $.extend({}, budgetTotalObj, backData);
	                	var index = layer.open({
	                        type : 2,
	                        title : '新增' + backData.value,
	                        maxmin : false,
	                        shadeClose : false, // 点击遮罩关闭层
	                        area : [ '900px', '600px' ],
	                        content : path + 'webpage/budget/templates/budgetDashbord/yearBudget.html?T_P_ID=' +
	                        	budgetTotalObj.ID + "&FUNDS_TYPE=" + budgetTotalObj.code
	                    });
	                    layer.full(index);
	                }
	            });

	        },
			//调整预算申请
            adjustYsApply: function(){
                if(budgetTotalObj === null) {
                    layer.msg('请先选择对应项目');
                    return '';
                }
                $.ajax({
                    url : path + "ysController.do?adjustYsApply",
                    type : "post",
                    data : {tpId: budgetTotalObj.ID},
                    success : function(backData) {
                    	alert(backData.msg);
                    }
                });
			},
	        checkSubmit:subimtAudit //审核
	    };
	    $('#dashbord .layui-btn').on('click', function(){
	        var type = $(this).data('type');
	        active[type] ? active[type].call(this) : '';
	    });


	    //监听总预算左侧表格行双击事件
	    table.on('rowDouble(budgetLeftTable)', function(obj){
	        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
	        let params = {
	    		T_APPR_ID : obj.data.ID,
	    		NODE: 1,
	    		FEE_TYPE: budgetTotalObj.FEE_TYPE
	        };
	        BudgetRightTable(params);
	    });
	    //监听年度预算左侧表行双击事件
	    table.on('rowDouble(yearBudgetLeftTable)', function(obj){
	         obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
	    	 let params = {
	    		T_APPR_ID : obj.data.ID,
	    		FEE_TYPE: budgetTotalObj.FEE_TYPE
	        };
	        yearBudgetRightTable(params);
	    });

	});
}

// 编辑总预算
var editTotal = function(id,type){
    budgetTotalId = id;
    if(type == 1){
    	 budgetTotalObj = $.extend({}, budgetTotalObj,{code: -1,value:'编辑总预算'});
		var index = layer.open({
		    type : 2,
		    title : '编辑总预算',
			maxmin : false,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '1000px', '600px' ],
			content : path + 'webpage/budget/templates/budgetDashbord/totalBudget2.html?T_P_ID=' +
				budgetTotalObj.ID + "&FUNDS_TYPE=" + budgetTotalObj.code + "&ysId=" + id
		});
    }else if(type == 2){
    	 budgetTotalObj = $.extend({}, budgetTotalObj,{code: -1,value:'编辑年度预算'});
    	    var index = layer.open({
    	        type : 2,
    	        title : '编辑年度预算',
    	        maxmin : false,
    	        shadeClose : false, // 点击遮罩关闭层
    	        area : [ '1000px', '600px' ],
    	        content : path + 'webpage/budget/templates/budgetDashbord/yearBudget.html?T_P_ID=' +
            	budgetTotalObj.ID + "&FUNDS_TYPE=" + budgetTotalObj.code  + "&ysId=" + id
    	    });
    }

    layer.full(index);
};
var xzFundData;
var editSchool = function(id){
	for(var i = 0 ; i < projectSchoolList.length ;i++){
		var data = projectSchoolList[i];
		if(id == data.ID){
			xzFundData = data;
		}
	}
	xzFundData.KFP = xzFundData.ALL_FEE - xzFundData.FP_MONEY - xzFundData.ZT_MONEY;
	$.ajax({
        url : path + "ysController.do?getXZFunds",
        type : "post",
        data : {T_P_ID:budgetTotalObj.ID,NUM:'13',FUNDS_CATEGORY:'2'},
        success : function(r) {
        	xzFundData.FPJE = r.FPJE;
        	xzFundData.APPLY_YEAR = r.APPLY_YEAR;
        	xzFundData.NUM = r.NUM;
        	openLaye();

        }
    });
	function openLaye(){

		var html =
		'<div class="layui-form-item"> '+
		'	<label class="layui-form-label">协作费:</label>'+
		'	<div class="layui-input-block">'+
		'	  <input type="text" id="schoolMenoy" value="' + (xzFundData.FPJE - xzFundData.D_SH_MONEY) + '" lay-verify="title" disabled="disabled" autocomplete="off"  class="layui-input">'+
		'	</div>'+
		'</div>'+
		'<div class="layui-form-item"> '+
		'	<label class="layui-form-label">项目总额:</label>'+
		'	<div class="layui-input-block">'+
		'	  <input type="text" id="allMenoy" value="' + xzFundData.ALL_FEE + '"  lay-verify="title" disabled="disabled" autocomplete="off"  class="layui-input">'+
		'	</div>'+
		'</div>'+
		'<div class="layui-form-item"> '+
		'	<label class="layui-form-label">可分配金额:</label>'+
		'	<div class="layui-input-block">'+
		'	  <input type="text" id="kfpMenoy" value="' + xzFundData.KFP + '"  lay-verify="title" disabled="disabled" autocomplete="off"  class="layui-input">'+
		'	</div>'+
		'</div>'+
		'<div class="layui-form-item">'+
		'	<label class="layui-form-label">分配金额:</label>'+
		'	<div class="layui-input-block">'+
		'	  <input type="text" id="allotMenoy" value="' + (xzFundData.D_SH_MONEY || 0) + '"  lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">'+
		'	</div>'+
		'</div>';
		layer.open({
			type: 1
			,title: '分配金额'
			,id: 'layerDemo' //防止重复弹出
			,content: html
			,area: ['500px', '320px']
			,btn: '提交'
			,btnAlign: 'r' //按钮居中
			,yes: function(a,b){
				var schoolMenoy = Number(b.find("#schoolMenoy").val());
				var allotMenoy = Number(b.find("#allotMenoy").val());

				if(schoolMenoy < 0 ){
					layer.msg("分配金额大于协作费");
				}else if(allotMenoy > xzFundData.KFP){
					layer.msg("分配金额大于可分配金额");
				}else{
					xzFundData.APPLY_AMOUNT = allotMenoy;
					saveXZFunds();
				    layer.close(a);
				}
			}
			,success: function( layero , index){
				var $schoolMenoy = layero.find("#schoolMenoy");
				var $allMenoy = layero.find("#allMenoy");
				layero.find("#allotMenoy").on('input',function(){
					var FPJE = xzFundData.FPJE || 0;
					var D_SH_MONEY = xzFundData.D_SH_FEE || 0;
					var value = FPJE + (D_SH_MONEY - this.value);
					$("#schoolMenoy").val(value);
					if(value < 0){
						layer.msg("分配金额大于协作费");
					}else if(this.value > xzFundData.KFP){
						layer.msg("分配金额大于可分配金额");
					}
	            })
			}
		});
	}
	//保存分配金额
	function saveXZFunds(){
		var param = {};
		if(xzFundData.INCOME_ID){//编辑
			param = {
				ID: xzFundData.INCOME_ID,
	        	APPLY_AMOUNT: xzFundData.APPLY_AMOUNT,
	        	isExdit: true
    		};
		}else{//新增
			param = {
	        	T_P_ID: xzFundData.ID,
	        	NUM: xzFundData.NUM,
	        	APPLY_AMOUNT: xzFundData.APPLY_AMOUNT,
	        	APPLY_YEAR: xzFundData.APPLY_YEAR,
	        	isExdit: false
    		};
		}

		$.ajax({
	        url : path + "ysController.do?saveOrUpdateXZFunds",
	        type : "post",
	        data : param,
	        success : function(r) {
	        	table.reload("projectSchoolTable", {
                    T_P_ID : budgetTotalObj.ID,
                    FEE_TYPE: budgetTotalObj.FEE_TYPE
                });
	        	xzFundData = null;
	        }
	    });
	}

}

// 删除总预算
var delTotal = function(id,ysType){
    layer.confirm('确定要删除该条记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : path + "ysController.do?delZysContractFunds",
            type : "post",
            data : {
                ID: id,
                FUNDS_CATEGORY:ysType
            },
            success : function(r) {
                if (r.code === 0) {
                    layer.msg(r.msg);
                    budgetLeftTable({T_P_ID:budgetTotalObj.ID,FEE_TYPE:budgetTotalObj.FEE_TYPE});
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    })
};
//打印总预算
var printTotal = function(id,syDate,ysLx){
    budgetTotalObj.ysDate = syDate;
    budgetTotalObj.ysLx = ysLx;
    layer.load(2);
    $.ajax({
        url : path + 'budgetExportController.do?createWordXmzysspb',
        type : 'post',
        data : budgetTotalObj,
        dataType : 'json',
        success : function(data) {
            layer.closeAll("loading")
            window.location.href= path + "tPmIncomeApplyController.do?downloadWord&FileName=" + data.attributes.FileName;
        }
    });
}

//打印年度预算
var printNd = function(id,syDate){
    budgetTotalObj.ysDate = syDate;
    layer.load(2);
    $.ajax({
        url : path + 'budgetExportController.do?createWordXmndysmxb',
        type : 'post',
        data : budgetTotalObj,
        dataType : 'json',
        success : function(data) {
            layer.closeAll("loading")
            window.location.href= path + "tPmIncomeApplyController.do?downloadWord&FileName=" + data.attributes.FileName;
        }
    });
}

// 编辑年度预算
var editYear = function(id){
    budgetYearId = id;
    var index = layer.open({
        type : 2,
        title : '年度预算',
        maxmin : false,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '900px', '400px' ],
        content : path + 'webpage/budget/templates/budgetDashbord/yearBudget.html?T_P_ID=' +
        	budgetTotalObj.ID + "&ysId=" + id
    });
    layer.full(index);
};

// 删除年度预算
var delYear = function(id){
    layer.confirm('确定要删除该条记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : path + "ysController.do?delZysContractFunds",
            type : "post",
            data : {
                ID: id,
                FUNDS_CATEGORY:1
            },
            success : function(r) {
                if (r.code === 0) {
                    layer.msg(r.msg);
                    yearBudgetLeftTable({T_P_ID:budgetTotalObj.ID,FEE_TYPE:budgetTotalObj.FEE_TYPE});
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    })
};

//审核
var subimtAudit = function(){
    var data = {};
    //总预算
    var  rows = layui.table.cache.budgetLeftTable;
    if(rows.length>0){
        for(var i=0; i<rows.length; i++){
            var r = rows[i];
            if(r.FINISH_FLAG=="0"){
                data.total_funds_id = r.ID;//总预算
                data.total_funds = r.TOTAL_FUNDS;//预算金额
                data.t_p_id = budgetTotalObj.ID;//项目ID
                break;
            }
        }
    }
    //年度预算
    rows = layui.table.cache.yearBudgetLeftTable;
    if(rows.length>0){
        for(var i=0; i<rows.length; i++){
            var r = rows[i];
            if(r.FINISH_FLAG == "0"){
                data.year_funds_id = r.ID;//年度预算
                data.year_funds = r.TOTAL_FUNDS;//预算金额
                data.t_p_id = budgetTotalObj.ID;//项目ID
                break;
            }
        }
    }

    if(!data.total_funds_id && !data.year_funds_id){
        layer.alert("没有未发送审核的记录！")
        return false;
    }
    var url = path+"tPmApprLogsController.do?goApprSend&apprId=1&apprType=24";

    var that = this;
   /* layer.open({
        type: 2,
        area: ['500px', '300px'],
        content:path + url
    })*/
    windowapi= window;
    W=window;
    createwindow("发送审核",url,520,260,function(iframe){
        data.recive_userIds = iframe.$("#receiveUseIds").val();
        data.recive_userNames = iframe.$("#receiveUseNames").val();
        data.submit_msg = iframe.$("#senderTip").val();
        try{
            $.ajax({
                url : 'tPmProjectFundsApprController.do?submitFundsAppr',
                data : data,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                   layer.alert(data.msg);
                    //tPmProjectFundsApprListsearch();
                    //tPmProjectYearFundsApprListsearch();
                }
            });
        }catch(e){}
        //tPmProjectFundsApprListsearch();
        //tPmProjectYearFundsApprListsearch();
        try{
            var apprInfo = window.$.dialog.list['apprSend'];
            apprInfo.close();
        }catch(e){}

       // return false;
    });
}

/**
 *经费分配打印
 */
var printJefp = function (id,fpId,type) {
    var url;
    var param = {};
    param.projectId = id;
    if(type == "1"){
        //计划下达
        url = path+"budgetExportController.do?createWordJhjfptzs";
        param.jhffId = fpId;

    }else if(type == "2"){
    	//校内协作
    }else if(type == "3"){
        //垫支
        url = path+"budgetExportController.do?createWordJfdzsqs";
        param.jfdzId = fpId;
    }else{
    	//认领
        url = path+"budgetExportController.do?createWord";
        param.dkmxId = fpId;
    }

    $.ajax({
        url : url,
        type : "post",
        data : param,
        success : function(result) {
            var obj = eval('(' + result + ')');
            var fileName = obj.attributes.FileName;
            window.location.href="http://"+window.location.host+"/tPmIncomeApplyController.do?downloadWord&FileName="+fileName;
        }
    });
}

