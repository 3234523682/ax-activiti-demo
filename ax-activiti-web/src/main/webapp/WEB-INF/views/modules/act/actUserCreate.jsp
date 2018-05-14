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
    <title>新建Act用户</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/identity/actUserList">用户列表</a></li>
    <li class="active"><a href="${ctx}/workflow/identity/createActUser">新建用户</a></li>
</ul>
<form id="inputForm" action="${ctx}/workflow/identity/saveActUser" method="post" class="form-horizontal" accept-charset="utf-8">
    <div class="control-group">
        <label class="control-label">用户标识：</label>
        <div class="controls">
            <input id="userId" name="userId" type="text" value="${actUser.userId}" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户名称：</label>
        <div class="controls">
            <input id="userName" name="userName" type="text" value="${actUser.userName}" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户邮箱：</label>
        <div class="controls">
            <input id="userEmail" name="userEmail" type="text" value="${actUser.userEmail}" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
    </div>
</form>
</body>
</html>
