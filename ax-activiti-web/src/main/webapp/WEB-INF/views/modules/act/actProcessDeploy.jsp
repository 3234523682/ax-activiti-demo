<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="default"/>
    <title>部署流程</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/process/">流程管理</a></li>
    <li class="active"><a href="${ctx}/workflow/process/deploy/">部署流程</a></li>
    <li><a href="${ctx}/workflow/process/running">运行中的流程</a></li>
</ul>
<br/>
<form id="inputForm" action="${ctx}/workflow/process/deploy" method="post" enctype="multipart/form-data" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">模型分类：</label>
        <div class="controls">
            <input id="modelCategory" name="modelCategory" type="text" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">流程文件：</label>
        <div class="controls">
            <input type="file" id="file" name="file" class="required input-xxlarge"/>
            <span class="help-inline">支持文件格式：zip、bar、bpmn、bpmn20.xml</span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
    </div>
</form>
</body>
</html>
