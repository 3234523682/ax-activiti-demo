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
    <title>流程任务开启</title>
</head>
<body>
<form id="inputForm" action="${ctx}/workflow/task/start" method="post" class="form-horizontal" target="_blank"
      accept-charset="utf-8">
    <input type="hidden" name="procDefId" value="${startParam.procDefId}"/>
    <input type="hidden" name="procDefKey" value="${startParam.procDefKey}"/>
    <div class="control-group">
        <label class="control-label">流程分类：</label>
        <div class="controls">
            <b>${startParam.category}</b>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">流程名称：</label>
        <div class="controls">
            <b>${startParam.procDefName}</b>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">任务名称：</label>
        <div class="controls">
            <input id="processTitle" name="processTitle" type="text" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">发起人&nbsp;&nbsp;&nbsp;：</label>
        <div class="controls">
            <select name="processStartUserId">
                <option value="">请选择</option>
                <c:forEach items="${actUsers}" var="user">
                    <option value="${user.userId}">${user.userName}</option>
                </c:forEach>
            </select>
            <span class="help-inline"></span>
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
</body>
</html>
