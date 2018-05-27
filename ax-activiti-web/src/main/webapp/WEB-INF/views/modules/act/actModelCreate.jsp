<%--
  Created by IntelliJ IDEA.
  User: Mr_lin
  Date: 2018/3/16
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="utf-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="default"/>
    <title>新建Act模型</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/model/list">流程列表</a></li>
    <li class="active"><a href="${ctx}/workflow/model/create">新建模型</a></li>
</ul>
<form id="inputForm" action="${ctx}/workflow/model/create" method="post" class="form-horizontal" target="_blank" accept-charset="utf-8">
    <div class="control-group">
        <label class="control-label">模型分类：</label>
        <div class="controls">
            <input id="modelCategory" name="modelCategory" type="text" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">模块名称：</label>
        <div class="controls">
            <input id="modelName" name="modelName" type="text" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">模块标识：</label>
        <div class="controls">
            <input id="modelKey" name="modelKey" type="text" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">模块描述：</label>
        <div class="controls">
            <textarea id="modelDescription" name="modelDescription" class="required"></textarea>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
    </div>
</form>
</body>
</html>
