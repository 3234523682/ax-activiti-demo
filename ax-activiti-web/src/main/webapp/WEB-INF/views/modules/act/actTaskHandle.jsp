<%--
  Created by IntelliJ IDEA.
  User: Mr_lin
  Date: 2018/3/16
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="default"/>
    <title>Act任务办理</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/task/alreadyClaimTaskList">待办任务列表</a></li>
    <li class="active"><a href="${ctx}/workflow/model/create">任务办理</a></li>
</ul>
<form id="inputForm" action="${ctx}/workflow/task/complete" method="post" class="form-horizontal" target="_blank"
      accept-charset="utf-8">
    <input type="hidden" name="taskId" value="${actTask.taskId}">
    <input type="hidden" name="procInsId" value="${actTask.taskProcInsId}">
    <div class="control-group">
        <label class="control-label">任务事项：</label>
        <div class="controls">
            <label class="control-label">${actTask.taskProcessName}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">任务名称：</label>
        <div class="controls">
            <label class="control-label">${actTask.taskName}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">当前环节：</label>
        <div class="controls">
            <label class="control-label">${actTask.currentNodeName}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">即将流向：</label>
        <div class="controls">
            <c:forEach items="${actModelNodes}" var="nodeInfo">
                ${nodeInfo.nodeName}、
            </c:forEach>
        </div>
    </div>
    <%--<div class="control-group">--%>
    <%--<label class="control-label">处理人：</label>--%>
    <%--<div class="controls">--%>
    <%--<input id="modelName" name="modelName" type="text" class="required"/>--%>
    <%--<span class="help-inline"></span>--%>
    <%--</div>--%>
    <%--</div>--%>
    <div class="control-group">
        <label class="control-label">审批操作：</label>
        <div class="controls">
            <input name="commentType" value="同意" type="radio" class="required"/> 同意&nbsp;&nbsp;
            <input name="commentType" value="驳回" type="radio" class="required"/> 驳回
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">处理意见：</label>
        <div class="controls">
            <textarea id="comment" name="comment" class="required"></textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">流程参数：</label>
        <div class="controls">
            <textarea id="flowParamJsonStr" name="flowParamJsonStr" class="required"></textarea>
            <span class="help-inline">json字符串</span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
    </div>
</form>
<act:histoicFlow procInsId="${actTask.taskProcInsId}"/>
</body>
</html>
