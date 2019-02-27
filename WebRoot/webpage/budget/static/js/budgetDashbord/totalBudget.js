var layer = null, balance = null, directTotal = 0, directFee = 0, unBudgetTotal = 0, totalFee = 0,categoryCode=null,path = uri.getBasePath();

layui.use(['form', 'layer'], function () {
    var form = layui.form;
    layer = layui.layer;
    // 保存提交
    form.on('submit(save)', function(data){
        /*layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
        });*/
    	data = data.field;
    	var KBJ = 0;
    	for(var key in data){
    		if(key.indexOf("KBJ") > 0 && !$.isEmptyObject(data[key])){
    			KBJ += parseFloat(data[key]);
    		}
    	}
    	data['19-10'] = data['19-JJXJ'] ;
    	if(KBJ != 0){
    		layer.msg("可编辑金额不为 0");
    		return false;
    	}
        var dateTemp = new Date();

        var extendParam = {
            categoryCode:categoryCode,
            START_YEAR:dateTemp.getFullYear(),
            END_YEAR: dateTemp.getFullYear(),
            FUNDS_CATEGORY:"1" //总预算
        };

        $.ajax({
            url : window.location.origin+"/" + 'ysController.do?addZysFundMap',
            type : "post",
            data : $.extend(parent.budgetTotalObj,data,extendParam,true),
            success : function(r) {
                if (r.code === 0) {
                    layer.msg(r.msg);
                    parent.renderMainTable();
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    },500)
                }else{
                    layer.msg(r.msg);
                }
            }
        });
        return false;
    });

    getPmTemplate();
});
// 反解金额为number
var toMoneyNumber = function(val){ // '123,45.89'
    return Number(val.split(',').join('')); // 12345.89
};


var detailNames=['间接费','指定分承包费','不可预见费','校内协作费'];
function getPmTemplate(){
	var projectId = parent.budgetTotalObj.ID;
	var budgetTotalId = parent.budgetTotalId;
	if(projectId !== ''){
		  $.ajax({
	          url : path+"ysController.do?getZysTemplate",
	          type : "post",
	          data : {
	        	  T_P_ID: projectId,
	        	  FUNDS_TYPE: budgetTotalId==''?parent.budgetTotalObj.code:-1,
	        	  ysId:budgetTotalId,
	        	  layer:1
	          },
	          success : function(res) {
	        	  parent.budgetTotalId = '';
	              if (res.code === 0) {
	            	  var data = res.data;
            		  for(var i=1;i < data.length;i++){
            			  var id = data[i].CATEGORY_CODE;
            			  if(i==1){
            				  categoryCode =id;
            				  if(id=='09'){
                				  $('.kxfzjjTr').show();
                				  $('.zbxmTr').hide();
                				  $('.kyxmTr').hide();
                			  }else if(id=='20'){
                				  $('.zbxmTr').show();
                				  $('.kxfzjjTr').hide();
                				  $('.kyxmTr').hide();
                			  }else{
                				  $('.kyxmTr').show();
                				  $('.zbxmTr').hide();
                				  $('.kxfzjjTr').hide();
                			  }
            				  $("#" + id + "-ZJE").val(res.ZJF_FUNDS);
            				  $("#" + id + "-KBJ").val(res.FP_FUNDS);
            			  }
            			  var $id = $("#" + data[i].ID)
            			  if($id.length == 1){
            				  $id.val(data[i].MONEY);
            			  }
            		  }  
					} else {
	                  layer.msg(res.msg);
	              }
	          }
	          });
	  }
}
// 直接经费输入框失去焦点后的动作
$(".inputBlur").blur(function(){
    if(isNaN(Number($(this).val()))) { // 判断正常数值输入，不要出现逗号等其他特殊字符，否则清空
        layer.msg('请输入正确数值');
        $(this).val('0.00');
        return '';
    } else {
        $(this).val(parent.toMoneyStyle(Number($(this).val())));
        
        // 获取直接经费的分配，并合计金额
        if(categoryCode=='09'){
        	var ywf = toMoneyNumber($("#09-01").val());
        	var kyhdf = toMoneyNumber($("#09-02").val());
        	var lwf = toMoneyNumber($("#09-03").val());
        	var kjrypyf = toMoneyNumber($("#09-04").val());
        	var sbgzwhf = toMoneyNumber($("#09-05").val());
        	var cdjswhf = toMoneyNumber($("#09-06").val());
        	var zjlxkyf = toMoneyNumber($("#09-07").val());
        	var cxcgjlf = toMoneyNumber($("#09-08").val());
        	var zscqf = toMoneyNumber($("#09-09").val());
        	directTotal = ywf + kyhdf + lwf + kjrypyf + sbgzwhf + cdjswhf + zjlxkyf + cxcgjlf + zscqf;
        	balance = toMoneyNumber($("#09-ZJE").val()) - directTotal;
        	$('#09-KBJ').val(balance);
        	if(balance <= 0) {
                layer.msg('直接经费超过或已达到经费预算总额，请重新分配');
                $("#09-XJ").val(parent.toMoneyStyle(directTotal)).css('color', 'red'); // 直接经费小计
            } else{
           	 $("#09-XJ").val(parent.toMoneyStyle(directTotal)).css('color', '#666'); // 直接经费小计
            }
        	
        }else if(categoryCode == '20'){
        	var sbf = toMoneyNumber($("#20-01").val());
	       	 var clf = toMoneyNumber($("#20-02").val());
	       	 var wxf = toMoneyNumber($("#20-03").val());
	       	 var rlf = toMoneyNumber($("#20-04").val());
	       	 var hyjlf = toMoneyNumber($("#20-05").val());
	       	 var cbswf = toMoneyNumber($("#20-06").val());
	       	 var lwf = toMoneyNumber($("#20-07").val());
	       	 var zjzx = toMoneyNumber($("#20-08").val());
	       	 var qtzc = toMoneyNumber($("#20-09").val());
	       	directTotal = sbf + clf + wxf + rlf + hyjlf + cbswf + lwf + zjzx + qtzc;
            directFee = clf + rlf + hyjlf + cbswf + lwf + zjzx + qtzc;
            // 差额 = 合计经费 - 直接经费
            balance = toMoneyNumber($("#20-ZJE").val()) - directTotal;
            $('#20-KBJ').val(balance);
            if(balance <= 0) {
                layer.msg('直接经费超过或已达到经费预算总额，请重新分配');
                $("#20-XJ").val(parent.toMoneyStyle(directTotal)).css('color', 'red'); // 直接经费小计
            } else{
            	$("#20-XJ").val(parent.toMoneyStyle(directTotal)).css('color', '#666'); // 直接经费小计
            }
        }else{
        	 var sbf = toMoneyNumber($("#19-01").val());
        	 var clf = toMoneyNumber($("#19-02").val());
        	 var wxf = toMoneyNumber($("#19-03").val());
        	 var rlf = toMoneyNumber($("#19-04").val());
        	 var hyjlf = toMoneyNumber($("#19-05").val());
        	 var cbswf = toMoneyNumber($("#19-06").val());
        	 var lwf = toMoneyNumber($("#19-07").val());
        	 var zjzx = toMoneyNumber($("#19-08").val());
        	 var qtzc = toMoneyNumber($("#19-09").val());
        	 directTotal = sbf + clf + wxf + rlf + hyjlf + cbswf + lwf + zjzx + qtzc;
             directFee = clf + rlf + hyjlf + cbswf + lwf + zjzx + qtzc;
             // 差额 = 合计经费 - 直接经费
             balance = toMoneyNumber($("#19-ZJE").val()) - directTotal;
             $('#19-KBJ').val(balance);
             if(balance <= 0) {
                 layer.msg('直接经费超过或已达到经费预算总额，请重新分配');
                 $("#19-XJ").val(parent.toMoneyStyle(directTotal)).css('color', 'red'); // 直接经费小计
             } else{
            	 $("#19-XJ").val(parent.toMoneyStyle(directTotal)).css('color', '#666'); // 直接经费小计
             }
             // 计算间接费
             countIndirect ();
        }
        
    }
});
// 间接经费输入框失去焦点后的动作
$(".inputBlurJJ").blur(function(){
    if(isNaN(Number($(this).val()))) { // 判断正常数值输入，不要出现逗号等其他特殊字符，否则清空
        layer.msg('请输入正确数值');
        $(this).val('0.00');
        return '';
    } else {
        $(this).val(parent.toMoneyStyle(Number($(this).val())));
        // 获取直接经费的分配，并合计金额
        var xxglf = toMoneyNumber($("#19-1001").val());
        var zrdwglf = toMoneyNumber($("#19-1002").val());
        var cydwglf = toMoneyNumber($("#19-1003").val());
        var xmzjxjt = toMoneyNumber($("#19-1004").val());
        var total = xxglf + zrdwglf + cydwglf + xmzjxjt;
        $("#19-JJXJ").val(parent.toMoneyStyle(total)); // 间接经费小计
    }
});
// 不可预见费输入框失去焦点后的动作
$(".inputBlurUnBudget").blur(function(){
    if(isNaN(Number($(this).val()))) { // 判断正常数值输入，不要出现逗号等其他特殊字符，否则清空
        layer.msg('请输入正确数值');
        $(this).val('0.00');
        return '';
    } else {
        $(this).val(parent.toMoneyStyle(Number($(this).val())));
        // 获取直接经费的分配，并合计金额
        if(categoryCode == '19'){
        	var xnxzf = toMoneyNumber($("#19-13").val());
            var zdfcbjf = toMoneyNumber($("#19-11").val());
            var bkyjf = toMoneyNumber($("#19-12").val());
            unBudgetTotal = xnxzf + zdfcbjf+bkyjf;
//            totalFee = parent.budgetTotalObj.HJ - unBudgetTotal;
//            $("#19-12").val(parent.toMoneyStyle(unBudgetTotal)); // 不可预见费小计
            // 计算间接费
            countIndirect ();
            var balanceSum = toMoneyNumber($('#19-KBJ').val());
            $('#19-KBJ').val(parent.toMoneyStyle(balanceSum-unBudgetTotal));
        }else{
        	var xnxzf = toMoneyNumber($("#20-10").val());
        	$('#20-KBJ').val(parent.toMoneyStyle(balance-xnxzf));
        }
        
    }
});

// 根据比例计算间接费
function countIndirect () {
    var indirect = 0, str = '', intSum = '';
    switch (parent.budgetTotalObj.JJFJSFS) {
        case '1':
            if (directTotal < 5000000) {
                str = directFee * 0.2 / 10 + '';
            } else if (directTotal < 10000000) {
                str = directFee * 0.15 / 10 + '';
            } else {
                str = directFee * 0.13 / 10 + '';
            }
            if(str.indexOf(".")>=0){
              intSum = str.substring(0, str.indexOf("."));
            }else{
            	intSum = str;
            }
            indirect = Number(intSum) * 10;
            break;
        case '2':
            if (toMoneyNumber(budgetTotalObj.HJ) < 500000) {
                str = totalFee * 0.3 / 10 + '';
            } else if (toMoneyNumber(budgetTotalObj.HJ) < 5000000) {
                str = totalFee * 0.2 / 10 + '';
            } else {
                str = totalFee * 0.13 / 10 + '';
            }
            if(str.indexOf(".")>=0){
                intSum = str.substring(0, str.indexOf("."));
              }else{
              	intSum = str;
              }
            indirect = Number(intSum) * 10;
            break;
        case '3':
        	indirect = 0;
            break;
        case '4':
            // 根据条件手动输入即可
            disabled();
            break;
        case '5':
            // 根据条件手动输入即可
            disabled();
            break;
        case '6':
            str = totalFee * (1/21) / 10 + '';
            if(str.indexOf(".")>=0){
                intSum = str.substring(0, str.indexOf("."));
              }else{
              	intSum = str;
              }
            indirect = Number(intSum) * 10;
            break;
    }
    var dxbl =(indirect * parent.budgetTotalObj.XMZBL)/100;
    var zrdwbl = (indirect * parent.budgetTotalObj.ZRDWBL)/100;
    var cydwbl = (indirect * parent.budgetTotalObj.CYDWBL)/100;
    var xmzbl = (indirect * parent.budgetTotalObj.XMZBL)/100;
    // 分配间接经费
    $("#19-1001").val(parent.toMoneyStyle(dxbl));
    $("#19-1002").val(parent.toMoneyStyle(zrdwbl));
    $("#19-1003").val(parent.toMoneyStyle(cydwbl));
    $("#19-1004").val(parent.toMoneyStyle(xmzbl));
    var jjxj = xmzbl+cydwbl+zrdwbl+dxbl;
    $("#19-JJXJ").val(parent.toMoneyStyle(jjxj));
    $('#19-KBJ').val(parent.toMoneyStyle(balance-jjxj));
    
}

// 间接费可编辑
function disabled () {
    $("#19-1001").attr('disabled', false);
    $("#19-1002").attr('disabled', false);
    $("#19-1003").attr('disabled', false);
    $("#19-1004").attr('disabled', false);
    $("#19-JJXJ").attr('disabled', false);
}

// 取消
function cancel() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}