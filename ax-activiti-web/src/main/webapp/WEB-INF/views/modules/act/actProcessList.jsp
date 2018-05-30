<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>流程管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/workflow/process/">流程管理</a></li>
    <li><a href="${ctx}/workflow/process/deploy/">部署流程</a></li>
    <li><a href="${ctx}/workflow/process/running">运行中的流程</a></li>
</ul>
<sys:message content="${message}"/>
<form:form id="searchForm" modelAttribute="queryParam" action="${ctx}/workflow/process/list" method="post" class="breadcrumb form-search">
    <label>流程分类：</label><input type="text" name="category" value="${queryParam.category}"/>
    <label>流程标识：</label><input type="text" name="procDefKey" value="${queryParam.procDefKey}"/>
    <label>流程名称：</label><input type="text" name="procDefName" value="${queryParam.procDefName}"/>
    <label>流程版本：</label><input type="text" name="procDefVersion" value="${queryParam.procDefVersion}"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table class="table table-striped table-bordered table-condensed table-nowrap">
    <thead>
    <tr>
        <th>流程ID</th>
        <th>流程分类</th>
        <th>流程标识</th>
        <th>流程名称</th>
        <th>流程版本</th>
        <th>部署时间</th>
        <th>流程XML</th>
        <th>流程图片</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="process">
        <tr>
            <td>${process.procDefId}</td>
            <td>${process.category}</td>
            <td>${process.procDefKey}</td>
            <td>${process.procDefName}</td>
            <td><b title='流程版本号'>V: ${process.procDefVersion}</b></td>
            <td><fmt:formatDate value="${process.procDefDeployTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><a target="_blank" href="${ctx}/workflow/process/actProcessResourceRead?procDefId=${process.procDefId}&resourceType=xml">${process.procDefResourceXml}</a></td>
            <td><a target="_blank" href="${ctx}/workflow/process/actProcessResourceRead?procDefId=${process.procDefId}&resourceType=image">${process.procDefResourceImg}</a></td>
            <td>
                <c:if test="${process.suspended}">
                    <a href="${ctx}/workflow/process/update/true?procDefId=${process.procDefId}" onclick="return confirmx('确认要激活吗？', this.href)">激活</a>
                </c:if>
                <c:if test="${!process.suspended}">
                    <a href="${ctx}/workflow/process/update/false?procDefId=${process.procDefId}" onclick="return confirmx('确认挂起除吗？', this.href)">挂起</a>
                </c:if>
                <a href='${ctx}/workflow/process/delete?deploymentId=${process.deploymentId}' onclick="return confirmx('确认要删除该流程吗？', this.href)">删除</a>
                <a href='${ctx}/workflow/process/convert/toModel?procDefId=${process.procDefId}' onclick="return confirmx('确认要转换为模型吗？', this.href)">转换为模型</a>
                <a href="${ctx}/workflow/task/start?procDefKey=${process.procDefKey}&category=${process.category}&procDefName=${process.procDefName}">开启任务</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
