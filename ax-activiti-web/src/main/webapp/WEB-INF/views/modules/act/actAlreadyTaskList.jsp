<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>待办任务列表</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/task/awaitClaimTaskList">待签任务列表</a></li>
    <li class="active"><a href="${ctx}/workflow/task/alreadyClaimTaskList">待办任务列表</a></li>
    <li><a href="${ctx}/workflow/task/historicTaskList">已办任务</a></li>
    <li><a href="${ctx}/workflow/task/process">新建任务</a></li>
</ul>
<form:form id="searchForm" modelAttribute="actTaskPageQuery" action="${ctx}/workflow/task/awaitClaimTaskList" method="get" class="breadcrumb form-search">
    <label>任务标题：</label><input type="text" name="taskProcessInstanceName" value="${queryParam.taskProcessInstanceName}"/>
    <label>事项：</label><input type="text" name="procDefName" value="${queryParam.procDefName}"/>
    <label>发起人：</label><input type="text" name="taskInitiator" value="${queryParam.taskInitiator}"/>
    <label>步骤：</label><input type="text" name="taskNodeName" value="${queryParam.taskNodeName}"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>流水号</th>
        <th>任务事项</th>
        <th>任务名称</th>
        <th>发起人</th>
        <th>经办步骤</th>
        <th>流程变量</th>
        <th>流程版本</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="actTask">
        <tr>
            <td>${actTask.taskProcInsId}</td>
            <td>${actTask.taskProcessName}</td>
            <td>${actTask.taskName}</td>
            <td>${actTask.taskInitiator}</td>
            <td>${actTask.currentNodeName}</td>
            <td>
                <c:forEach items="${actTask.processVariables}" var="mapEntity">
                    ${mapEntity.key}
                    ${mapEntity.value}
                </c:forEach>
            </td>
            <td><b title='流程版本号'>V: ${actTask.taskProcDefVersion}</b></td>
            <td><fmt:formatDate value="${actTask.createDate}" type="both"/></td>
            <td>
                <a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${actTask.taskProcDefId}&processInstanceId=${actTask.taskProcInsId}">进度</a>
                <c:if test="${empty actTask.taskExecutionId}">
                    <a href="${ctx}/workflow/task/deleteTask?taskId=${actTask.taskId}&reason=" onclick="return promptx('删除任务','删除原因',this.href);">删除任务</a>
                </c:if>
                <c:if test="${empty actTask.nodeAffiliationPerson}">
                    <a href="javascript:claim('${actTask.taskId}');">退签</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
