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
    <title>新建Act分组</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/identity/actGroupList">分组列表</a></li>
    <li class="active"><a href="${ctx}/workflow/identity/createActGroup">新建分组</a></li>
</ul>
<form id="inputForm" action="${ctx}/workflow/identity/saveActGroup" method="post" class="form-horizontal"
      accept-charset="utf-8">
    <div class="control-group">
        <label class="control-label">分组标识：</label>
        <div class="controls">
            <input id="groupId" name="groupId" type="text" value="${actGroup.groupId}" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分组名称：</label>
        <div class="controls">
            <input id="groupName" name="groupName" type="text" value="${actGroup.groupName}" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分组类型：</label>
        <div class="controls">
            <input id="groupType" name="groupType" type="text" value="${actGroup.groupType}" class="required"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
    </div>
</form>
</body>
</html>
