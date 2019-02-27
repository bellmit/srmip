var treeGrid = null, layer = null, path = uri.getBasePath(),
    FUNDS_TYPE = '',
    mapList = [], // 获取树列表所有数据
    balance = null, // 余额
    directTotal = 0, // 直接费总额
    directTotala = 0,// 设备、外协总额
    directFee = 0, // 直接费除设备&外协总额
    totalFee = 0, // 总经费除去校内协作&指定分承包
    calcType = false;
//点击按钮触发动作
var active = {
    save: function(){
        var data = treeGrid.getDataList('totalBudgetTable');
        var param = $.extend(parent.budgetTotalObj,{
	   		 "list" : JSON.stringify(data),
	   		 "FUNDS_TYPE": parent.budgetTotalObj.code,
	   		 "FUNDS_CATEGORY": 1
        },true);
        if(data.length > 0) {
            $.ajax({
                url : path + "ysController.do?saveOrUpdateFund",
                type : "post",
                data : param,
                success : function(r) {
                    if (r.code === 0) {
                        layer.msg(r.msg);
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }else{
                        layer.msg(r.msg);
                    }
                }
            });
        } else {
            layer.msg('请添加数据')
        }
    },// 保存
    cancel: function(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    } // 取消
};

layui.use(['treeGrid', 'layer'], function () {
    treeGrid = layui.treeGrid;
    layer = layui.layer;
    //获取初始化参数
    var params = getParam();
    if(params.pageType){
    	$(".budgetBtn").hide();
    }
    //渲染表格
    renderTable(params);
    //添加监控事件
    addTableEvent();
    
});

/**
 * 渲染树表格
 * @param params
 */
function renderTable(params) {
    layer.load(2);
    treeGrid.render({
        id: 'totalBudgetTable',
        elem: '#totalBudgetTable',
        idField:'ID', //必須字段
        url : path + "ysController.do?getTreeTemplate",
        treeId:'ID',//树形id字段名称
        treeUpId:'PID',//树形父id字段名称
        treeShowName:'DETAIL_NAME',//以树形式显示的字段
        height: 'auto',
        iconOpen:true, //是否显示图标【默认显示】
        isOpenDefault:true, //节点默认是展开还是折叠【默认展开】
        method:'post',
        where: params,
        cols: [[
            {field: 'CATEGORY_CODE_DTL', width: 100, align: 'left', title: '预算编码'},
            {field: 'DETAIL_NAME', minWidth: 200, title: '明细预算名称'},
            {field: 'UNIT',width: 70, align: 'right', title: '单位'},
            {field: 'PRICE',width: 70, align: 'right', title: '单价', edit:'text'},
            {field: 'AMOUNT', width: 70, align: 'right', title: '数量', edit:'text'},
            {field: 'HISTORY_MONEY', align: 'right', title: '已编制金额', width: 120},
            {field: 'XE', align: 'right', title: '限额', width: 120, templet: function(data) {
                if(!data.XE){
                	return '';
                }
            	return toMoneyStyle(data.XE);
        	}},
            {field: 'DETAIL_SYMBOL4', align: 'right', title: '已开支金额(含借款)', width: 160},
            {field: 'MONEY', align: 'right', title: '预算金额', width: 100, edit: 'text', templet: function(d) {
                    var html = "<span ></span>";//style=\"color: #fff\"
                    if(!d.MONEY){
                    	return html;
                    }
                    if(Number(d.MONEY) < 0) {
                        html = '<span style="color: red">'+ toMoneyStyle(Number(d.MONEY) * -1) + '</span>';
                    } else {//第一次默认的名称为XXX，#f7fbfa
                        html = '<span >'+ toMoneyStyle(Number(d.MONEY)) + '</span>';//style="color: #fff"
                    }
                    return html;
                }},
            {field: 'DETAIL_DESC', align: 'left',title: '备注', edit: 'text'}
        ]],
        isPage: false,
        size: 'sm', //小尺寸的表格
        done: function (res) {
            editColsBg(res.data);
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
        },
        parseData: function(res){
            FUNDS_TYPE = params.FUNDS_TYPE;
            if(res.code < 0) {
                parent.layer.alert(res.msg);
                active.cancel.call(this);
                return {data:[]}
            } else if (params.FUNDS_TYPE === "7" ){
                for(var i = 0 ; i < res.data.length ; i++){
                    var resData = res.data[i];
                    resData.HISTORY_MONEY = resData.MONEY;
                    resData.MONEY = null;
                }
            }
			/*else {
                for(var i = 0 ; i < res.data.length ; i++){
                    var resData = res.data[i];
                    resData.MONEY = null;
                }
            }*/

        }
    });
};
/**
 * 添加表格监控事件
 */
function addTableEvent(){
	//保存、取消
	$('#totalDudget .layui-btn').on('click', function(){
        var type = $(this).data('type');
        parent.budgetTotalId = null;
        active[type] ? active[type].call(this) : '';
    });
	//绑定操作
	treeGrid.on('tool(totalBudgetTable)',function (obj) {
		if(obj.event === 'del'){ // 删除行
			layer.confirm("你确定删除数据吗？如果存在下级节点则一并删除，此操作不能撤销!",
			{icon: 3, title:'提示'},
			function(index){ // 确定回调
			obj.del();
			layer.close(index);
			},function (index) { // 取消回调
			layer.close(index);
			}
			);
		}else if(obj.event==="add"){//添加行
			var param={};
			param.DETAIL_NAME = 'XXX';
			param.ID = String(++i);
			param.PID = obj.data ? obj.data.ID : "0";
			param.NODE = '3';
			treeGrid.addRow('totalBudgetTable', obj.data ? obj.data[treeGrid.config.indexName] + 1 : 0, param);
		}
	});
	// 编辑预算金额单元格
    treeGrid.on('edit(totalBudgetTable)', function(obj) {
        if(obj.data.ID === "0-0") {
            layer.msg('该行内容不允许编辑');
            return '';
        }
        switch (obj.field) {
            case 'MONEY': // 预算金额
            case 'PRICE':
            case 'AMOUNT':
               if(isNaN(toMoneyNumber(obj.value))) {
                   layer.msg('请输入正确数值');
                   if(obj.field === 'PRICE') {
                       obj.data.PRICE = 0;
                   }
                   if(obj.field === 'AMOUNT') {
                       obj.data.AMOUNT = 0;
                   }
                   if(obj.field === 'MONEY') {
                       obj.data.MONEY = 0;
                   }
                   treeGrid.updateRow("totalBudgetTable", obj.data);
                   return '';
               }
                // 处理输入的金额，更新表格输入的数据
                if(obj.data.PRICE !== null && obj.data.PRICE !== undefined && obj.data.PRICE !== "" &&
                    obj.data.AMOUNT !== null && obj.data.AMOUNT !== undefined && obj.data.AMOUNT !== "") {
                    obj.data.MONEY = keepTenDigits(toMoneyNumber(obj.data.PRICE * obj.data.AMOUNT));
                } else {
                   if(obj.field === 'MONEY') {
                       obj.data.MONEY = keepTenDigits(toMoneyNumber(obj.value));
                   }
                }

               // obj.data.MONEY = keepTenDigits(obj.value);
               treeGrid.updateRow("totalBudgetTable", obj.data);
               mapList = treeGrid.getDataMap("totalBudgetTable"); // 获取表格所有数据map格式
               var arrList = treeGrid.getDataList("totalBudgetTable"); // 获取表格所有数据[{}]格式
               var newMap = arrList.slice(1, arrList.length - 8); // 获取除总计、间接费及以下的其他费数据（直接费）[{}]
               var money1 = toMoneyNumber(mapList['19-13'].MONEY) || 0;
               var money2 = toMoneyNumber(mapList['19-11'].MONEY) || 0;
               totalFee = parent.budgetTotalObj.ALL_FEE - money1 - money2;  // 获取除校内协作&指定承包后的总经费
               for(var i = 0; i < newMap.length; i++) {
                   if(newMap[i].NODE === "1") {
                       directTotal += toMoneyNumber(newMap[i].MONEY) || 0; // 直接费总额
                       if(newMap[i].ID === "19-01" || newMap[i].ID === "19-03"){ // 设备、外协总额
                    	   directTotala += toMoneyNumber(newMap[i].MONEY) || 0;
                       }
                   }
               }
                // 计算间接费
                countIndirect(mapList);
               break;
           case 'DETAIL_DESC':
               break;
       }
        var arrList2 = treeGrid.getDataList("yearBudgetTable"); // 获取表格所有数据[{}]格式
        editColsBg(arrList2);
    });
}

/**
 * 根据比例计算间接费
 * @param mapList
 */
function countIndirect(mapList) {
    if(FUNDS_TYPE !== "7") {
        var indirect = 0;
        switch (parent.budgetTotalObj.JJFJSFS) {
            case '1':
            	directFee = parent.budgetTotalObj.ALL_FEE - directTotala;
                if (Number(parent.budgetTotalObj.ALL_FEE) < 5000000) {
                    indirect = directFee * 0.2;
                } else if (Number(parent.budgetTotalObj.ALL_FEE) < 10000000) {

                    indirect = 5000000 * 0.2 + (directFee - 5000000) * 0.15;
                } else {
                    indirect =  5000000 * 0.2 + 5000000 * 0.15 + (directFee - 10000000) * 0.13;
                }
                break;
            case '2':
                calcType = true;
                if (Number(parent.budgetTotalObj.ALL_FEE) < 500000) {
                    indirect = totalFee * 0.3;
                } else if (Number(parent.budgetTotalObj.ALL_FEE) < 5000000) {
                    indirect = 500000 * 0.3 + (totalFee - 500000) * 0.2;
                } else {
                    indirect =  500000 * 0.3 + 4500000 * 0.2 + (totalFee - 5000000) * 0.13;
                }
                break;
            case '3':
                indirect = 0;
                break;
            case '4':
                // 根据条件手动输入即可
                // disabled();
                break;
            case '5':
                // 根据条件手动输入即可
                // disabled();
                break;
            case '6':
                indirect = totalFee * (1/21);
                break;
        }
        var dxbl = null, zrdwbl = null, cydwbl = null, xmzbl = null;
        if (calcType) {
            dxbl = 0; // 学校管理费
            zrdwbl = 0; // 责任单位管理费
            cydwbl = 0; // 承研单位管理费
            xmzbl = indirect; // 项目组绩效津贴
        } else {
            dxbl = indirect * (parent.budgetTotalObj.XMZBL/100); // 学校管理费
            zrdwbl = indirect * (parent.budgetTotalObj.ZRDWBL/100); // 责任单位管理费
            cydwbl = indirect * (parent.budgetTotalObj.CYDWBL/100); // 承研单位管理费
            xmzbl = indirect * (parent.budgetTotalObj.XMZBL/100); // 项目组绩效津贴
        }

        // 分配间接经费
        var a = mapList['19-1001'],
            b = mapList['19-1002'],
            c = mapList['19-1003'],
            d = mapList['19-1004'],
            e = mapList['19-10'], // 间接费总额
            f = mapList['0-0']; // 余额
        var jjxj = xmzbl + cydwbl + zrdwbl + dxbl;// 计算间接费总额
        a.MONEY = keepTenDigits(dxbl);
        b.MONEY = keepTenDigits(zrdwbl);
        c.MONEY = keepTenDigits(cydwbl);
        d.MONEY = keepTenDigits(xmzbl);
        e.MONEY = keepTenDigits(jjxj);
        f.MONEY = keepTenDigits(parent.budgetTotalObj.ALL_FEE - jjxj - directTotal
            - (toMoneyNumber(mapList['19-11'].MONEY) || 0) - (toMoneyNumber(mapList['19-12'].MONEY) || 0)
            - (toMoneyNumber(mapList['19-13'].MONEY) || 0)); // 计算余额
        treeGrid.updateRow("totalBudgetTable", a);
        treeGrid.updateRow("totalBudgetTable", b);
        treeGrid.updateRow("totalBudgetTable", c);
        treeGrid.updateRow("totalBudgetTable", d);

        treeGrid.updateRow("totalBudgetTable", e); // 间接费总额
        treeGrid.updateRow("totalBudgetTable", f); // 总计列预算金额（余额）
    } else {
        var g = mapList['0-0']; // 余额
        g.MONEY = keepTenDigits(parent.budgetTotalObj.ALL_FEE - directTotal
            - (toMoneyNumber(mapList['19-11'].MONEY) || 0) - (toMoneyNumber(mapList['19-12'].MONEY) || 0)
            - (toMoneyNumber(mapList['19-13'].MONEY) || 0)); // 计算余额
        treeGrid.updateRow("totalBudgetTable", g); // 总计列预算金额（余额）
    }
    var arrList2 = treeGrid.getDataList("yearBudgetTable"); // 获取表格所有数据[{}]格式
    editColsBg(arrList2);

    balance = null; // 余额
    directTotal = 0; // 直接费总额
    directTotala = 0;
    directFee = 0; // 直接费除设备&外协总额
    totalFee = 0; // 总经费除去校内协作&指定分承包
    calcType = false;
}
/**
 * 获取查询参数
 * @returns {___anonymous5067_5190}
 */
function getParam(){
	var urlParam = BaseUtil.getUrlParams();
	var param = $.extend({
		YS_TYPE: 'ALL',		//预算种类
		FUNDS_CATEGORY: 1,	//预算类型
		layer: 1,
		PID: '19-10'
//		FUNDS_TYPE: parent.budgetTotalObj.code
	},urlParam,true);
	return param;
}

// 输入或计算金额保留到十位
function keepTenDigits (item) {
    var indirect = 0, str = item / 10 + "";
    if(str.indexOf(".") !== -1) indirect = Number(str.substring(0, str.indexOf("."))) * 10;
    else indirect = Number(str) * 10;
    return indirect;
}
// 转科学计数法
var toMoneyStyle = function(val){
    //金额转换每隔3位用逗号分开 1,234.56
    var str = keepTenDigits(Number(val)) + '';
    var intSum = str.replace( /\B(?=(?:\d{3})+$)/g, ',' );
    return intSum;
};
// 反解金额为number
var toMoneyNumber = function(val){ // '12,345'
    var indirect = 0, val = val + '';
    if(val !== null && val.indexOf(",") !== -1){
        indirect = Number(val.split(',').join(''));
    } else {
        indirect = Number(val);
    }
    return indirect; // 12345
};

// 计算规则
function completRule () {
    var index = layer.open({
        type : 2,
        title : '计算规则',
        maxmin : false,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '518px' ],
        content : path + 'webpage/budget/templates/budgetDashbord/completRule.html' // iframe的url
    });
}
// 禁止叶节点不可编辑、可编辑列颜色及隐藏ID栏
function editColsBg (arr) {
    // 名称和预算金额列背景
    $("td[data-field='MONEY']").css('background','rgb(251, 242, 202)');

    var id08 = '', id11 = '';
    if(arr.length !== 0) {
        for(var i = 0, len = arr.length; i < len; i++) {
            if(arr[i].ID === "19-08") {
                id08 = i;
            }
            if(arr[i].ID === "19-11") {
                id11 = i;
            }
            if(arr[i].NODE === "0" || arr[i].PID === "19-10") {
                var _this = $("tr[u_id='"+ arr[i].PID + "']");
                var AMOUNT =  _this.find("td[data-field='AMOUNT']");
                var PRICE =  _this.find("td[data-field='PRICE']");
                var MONEY =  _this.find("td[data-field='MONEY']");
                for(var y = 0; y < AMOUNT.length; y++) {
                    AMOUNT[y].removeAttribute('data-edit');
                }
                for(var z = 0; z < PRICE.length; z++) {
                    PRICE[z].removeAttribute('data-edit');
                }
                for(var j = 0; j < MONEY.length; j++) {
                    MONEY[j].removeAttribute('data-edit');
                    MONEY[j].setAttribute('style', 'background: #fff !important');
                }
            }
            if(arr[i].ID === '19-10') {
                var _that = $("tr[data-index='"+ i + "']");
                _that.find("td[data-field='AMOUNT']")[0].removeAttribute('data-edit');
                _that.find("td[data-field='PRICE']")[0].removeAttribute('data-edit');
                _that.find("td[data-field='MONEY']")[0].removeAttribute('data-edit');
                _that.find("td[data-field='MONEY']")[0].setAttribute('style', 'background: #fff !important');
            }
        }
        setTimeout(function() {
            var _this = $("tr[data-index='"+ id08 + "']"), _that = $("tr[data-index='"+ id11 + "']");
            _this.find("td[data-field='AMOUNT']")[0].setAttribute('data-edit', 'text');
            _this.find("td[data-field='PRICE']")[0].setAttribute('data-edit', 'text');
            _this.find("td[data-field='MONEY']")[0].setAttribute('data-edit', 'text');
            _this.find("td[data-field='MONEY']")[0].setAttribute('style', 'background: rgb(251, 242, 202) !important');

            _that.find("td[data-field='AMOUNT']")[0].setAttribute('data-edit', 'text');
            _that.find("td[data-field='PRICE']")[0].setAttribute('data-edit', 'text');
            _that.find("td[data-field='MONEY']")[0].setAttribute('data-edit', 'text');
            _that.find("td[data-field='MONEY']")[0].setAttribute('style', 'background: rgb(251, 242, 202) !important');
        }, 100);
    }
}
$(window).scroll(function() {
    // 当滚动到最底部以上40像素时，显示吸顶效果
    if ($(this).scrollTop() >= 10) {
        $(".layui-table-header").css({
            'position': 'fixed',
            'top': '0',
            'z-index': 1000
        });
    } else {
        $(".layui-table-header").css({
            'position': 'relative'
        });
    }
});