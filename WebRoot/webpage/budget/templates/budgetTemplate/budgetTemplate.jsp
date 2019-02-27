<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link rel="stylesheet" href="webpage/budget/static/common/layui/css/layui.css">
<link rel="stylesheet" href="webpage/budget/static/css/index.css">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <div style="overflow:hidden" id="left">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-header">模板主表</div>
                    <div class="layui-card-body budgetTemplateTable">
                        <table class="layui-hide" id="budgetTemplateTable" lay-filter="budgetTemplateTable"></table>
                    </div>
                </div>
            </div>
            <div class="layui-col-md8">
                <div class="layui-card">
                    <div class="layui-card-header" style="position: relative">模板附属表
                        <div style="position: absolute;top: -1px; right: 16px;">
                            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="add(null)" type="button">新增</button>
                            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="save()" type="button">保存操作</button>
                        </div>
                    </div>
                    <div class="layui-card-body budgetInfoTable">
                        <table class="layui-hide" id="budgetInfoTable" lay-filter="budgetInfoTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
<!-- 全局js -->
<script type="text/javascript" src="webpage/budget/static/common/layui/layui.js"></script>
<script type="text/javascript" src="webpage/budget/static/common/jquery.min.js"></script>
<script type="text/javascript" src="webpage/budget/static/common/uri.js"></script>

<!-- 自定义js -->
<script type="text/javascript" src="webpage/budget/static/js/budgetTemplate/budgetTemplate.js"></script>