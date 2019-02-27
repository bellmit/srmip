<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .gdbutton {
        margin-left: 938px;
        margin-top: 90;
        border-radius: 3px;
        border: solid 1px #999;
        background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#FAFAFA), to(#E4E4E4));
        font: 12px/1.333 tahoma, arial, \5b8b\4f53, sans-serif;
        height: 23px;
        letter-spacing: 3px;
        cursor: pointer;
    }
</style>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="projectId" value="${projectId}" type="hidden"/>
<input id="paySubjectcode" value="${paySubjectcode}" type="hidden"/>
<input id="projectNo" value="${projectNo}" type="hidden"/>
<input id="payYear" value="${payYear}" type="hidden"/>


<div class="easyui-layout" fit="true">
    <div region="north" style="height:200px;">
        <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
                     action="tPmPayfirstController.do?doUpdate" tiptype="1" beforeSubmit="checkAmount"
                     callback="@Override saveCallback">
            <table cellpadding="0" cellspacing="1" class="formtable">
                <tr>
                    <td align="right"><label class="Validform_label"> 归垫金额： </label> <font color="red">*</font></td>
                    <td class="value"><input id="GD_MONDY" name="GD_MONDY" type="text"
                                             style="width: 150px; text-align: right;" class="easyui-numberbox"
                                             datatype="*"
                                             data-options="min:0,max:9999999999,precision:2,groupSeparator:','"
                                             value=''><span class="Validform_checktip">元</span></td>
                    <th>
                        <label class="Validform_label"> 年度： </label> <font color="red">*</font>
                    </th>
                    <td>
                        <input value="${payYear}" readonly disabled/>
                    </td>
                </tr>
                <tr>
                    <th align="right">
                        <label class="Validform_label"> 垫支科目代码： </label> <font color="red">*</font>
                    </th>
                    <td>
                        <input class="" value="${paySubjectcode}" readonly disabled style="margin-left: 5px;"/>
                    </td>

                    <td align="right">
                        <label class="Validform_label"> 备注： </label>
                    </td>
                    <td>
                        <input class="" id="gdReason" name="gdReason"/>
                    </td>
                </tr>

            </table>
        </t:formvalid>
        <div>
            <input type="button" value="归 垫" class="gdbutton" style="" onclick="gdProcess()"/>

        </div>

    </div>
    <div region="center" style="padding:1px;">
        <t:datagrid name="tPmPayfirstList" checkbox="false" fitColumns="true"
                    actionUrl="tPmPayfirstController.do?tPmPayfirstListGdSearch&projectId=${projectId}"
                    idField="ID" fit="true" queryMode="group" onDblClick="">
            <t:dgCol title="主键" field="ID" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="项目_主键" field="PROJECT_ID" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="项目_编号" field="PROJECT_NO" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="归垫金额(元)" field="GD_MONDY" queryMode="group" width="100" align="right"
                     extendParams="formatter:formatCurrency,"></t:dgCol>
            <t:dgCol title="垫支科目代码" field="PAY_SUBJECTCODE" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="归垫理由" field="REASON" queryMode="group" width="200"></t:dgCol>
            <t:dgCol title="归垫状态" field="GD_STATUS" replace="未归垫_0,归垫中_1,已归垫_2" queryMode="group" width="100"></t:dgCol>
        </t:datagrid>

    </div>
</div>

<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        //设置datagrid的title
        var title = "";
        title = '${projectName}-归垫信息表';
        $("#tPmPayfirstList").datagrid({
            title: title
        });
    });


    /**
     * 处理归垫申请
     */
    function gdProcess(id) {
        W.$.dialog.confirm('您确定要申请归垫吗?', function () {
            if(!$("#GD_MONDY").val()){
                W.$.dialog.alert("归垫金额不能为空！");
                return;
            }
            //获取参数
            var params = {
                GD_MONDY: $("#GD_MONDY").val(),
                REASON: $("#gdReason").val(),
                paySubjectcode: $("#paySubjectcode").val(),
                projectNo: $("#projectNo").val(),
                projectId: $("#projectId").val(),
                payYear: $("#payYear").val()
            }
            //归垫申请
            $.ajax({
                url: "tPmPayfirstController.do?tPmPayfirstListGdAdd",
                type: "POST",
                dataType: "json",
                data: params,
                async: true,
                cache: false,
                success: function (data) {
                    W.$.dialog.alert(data.msg);
                    if (data.code == 0) {
                        $("#GD_MONDY").val('');
                        $("#gdReason").val('')

                        $('#tPmPayfirstList').datagrid('reload');
                    }
                },
                error: function () {
                    W.$.dialog.alert("系统故障，执行归垫申请失败，请稍后再试");
                }
            });
        }, function () {
        }, windowapi);
    }



</script>