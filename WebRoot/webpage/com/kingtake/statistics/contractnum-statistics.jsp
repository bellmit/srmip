<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="numContainer" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<script type="text/javascript">
$(function () {
    $.ajax({
        url:'ContractStatisticsController.do?getOutcomeContractNumLineData',
        cache:false,
        type:'POST',
        dataType:'json',
        success:function(data){
            $('#numContainer').highcharts({
                title: {
                    text: '近五年出账合同签订数量趋势图',
                    x: -20 //center
                },
                subtitle: {
                    text: '',
                    x: -20
                },
                xAxis: {
                    categories: data.xAxis
                },
                yAxis: {
                    title: {
                        text: '数量(个)'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: '个'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: '签订合同数量趋势',
                    data: data.series
                }]
            });
        }
    })
    
});
</script>
<script src="plug-in/Highcharts-4.2.5/js/highcharts.js"></script>
<script src="plug-in/Highcharts-4.2.5/js/modules/exporting.js"></script>