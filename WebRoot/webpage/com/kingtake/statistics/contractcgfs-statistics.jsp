<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="numContainer" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<script type="text/javascript">
    $(function() {
        $.ajax({
            url : 'ContractStatisticsController.do?getOutcomeContractCgfsLineData',
            cache : false,
            type : 'POST',
            dataType : 'json',
            success : function(data) {
                $('#numContainer').highcharts(
                        {
                            chart : {
                                type : 'column'
                            },
                            title : {
                                text : '近五年各采购方式合同签订数量柱状图'
                            },
                            subtitle : {
                                text : ''
                            },
                            xAxis : {
                                categories : data.xAxis,
                                crosshair : true
                            },
                            yAxis : {
                                min : 0,
                                title : {
                                    text : '数量 (个)'
                                }
                            },
                            tooltip : {
                                headerFormat : '<span style="font-size:10px">{point.key}</span><table style="width:150px;">',
                                pointFormat : '<tr><td style="color:{series.color};padding:0;">{series.name}: </td>'
                                        + '<td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
                                footerFormat : '</table>',
                                shared : true,
                                useHTML : true
                            },
                            plotOptions : {
                                column : {
                                    pointPadding : 0.2,
                                    borderWidth : 0
                                }
                            },
                            series : data.series
                        });
            }
        });
    });
</script>
<script src="plug-in/Highcharts-4.2.5/js/highcharts.js"></script>
<script src="plug-in/Highcharts-4.2.5/js/modules/exporting.js"></script>