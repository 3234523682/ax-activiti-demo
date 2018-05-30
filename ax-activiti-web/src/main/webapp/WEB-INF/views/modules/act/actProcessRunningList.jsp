<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>运行中的流程</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/process/">流程管理</a></li>
    <li><a href="${ctx}/workflow/process/deploy/">部署流程</a></li>
    <li class="active"><a href="${ctx}/workflow/process/running">运行中的流程</a></li>
</ul>
<form id="searchForm" action="${ctx}/workflow/process/running/" method="post" class="breadcrumb form-search">
    <label>流程实例ID：</label><input type="text" id="procInsId" name="procInsId" value="${procInsId}" class="input-medium"/>
    <label>流程定义Key：</label><input type="text" id="procDefKey" name="procDefKey" value="${procDefKey}" class="input-medium"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>执行ID</th>
        <th>流程实例ID</th>
        <th>流程实例名称</th>
        <th>流程定义标识</th>
        <th>流程定义名称</th>
        <th>流程定义版本</th>
        <th>当前环节标识</th>
        <th>当前环节名称</th>
        <th>当前环节受理人</th>
        <th>是否挂起</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="procIns">
        <tr>
            <td>${procIns.id}</td>
            <td>${procIns.procInsId}</td>
            <td>${procIns.procInsName}</td>
            <td>${procIns.procDefKey}</td>
            <td>${procIns.procDefName}</td>
            <td>${procIns.procDefVersion}</td>
            <td>${procIns.activityId}</td>
            <td>${procIns.activityName}</td>
            <td>${procIns.activityConductor}</td>
            <td>${procIns.suspended}</td>
            <td>
                <a href="${ctx}/workflow/process/deleteProcIns?procInsId=${procIns.id}&reason=" onclick="return promptx('删除流程','删除原因',this.href);">删除流程</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
