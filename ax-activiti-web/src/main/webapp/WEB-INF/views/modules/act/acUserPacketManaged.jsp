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
    <title>Act用户分组管理</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/workflow/identity/actUserList">用户列表</a></li>
    <li class="active"><a href="#">用户分组管理</a></li>
</ul>
<form id="inputForm" action="${ctx}/workflow/identity/updateMembership" method="post" class="form-horizontal"
      accept-charset="utf-8">
    <div class="control-group">
        <label class="control-label">用户标识：</label>
        <div class="controls">
            ${actUser.userId}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户名称：</label>
        <div class="controls">
            ${actUser.userName}
        </div>
    </div>
    <input type="hidden" name="userId" value="${actUser.userId}">
    <div class="control-group">
        <label class="control-label">分组名称：</label>
        <div class="controls">
            <c:forEach items="${allActGroup}" var="actGroup">
                <c:set var="isSelect" value="false"/>
                <c:forEach items="${actUserPackets}" var="userGroup">
                    <c:if test="${actGroup.groupId == userGroup.groupId}">
                        <c:set var="isSelect" value="true"/>
                    </c:if>
                </c:forEach>
                <input name="groupIds" type="checkbox" <c:if test="${isSelect}">checked</c:if>  value="${actGroup.groupId}"/>&nbsp;${actGroup.groupName}&nbsp;&nbsp;
            </c:forEach>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
    </div>
</form>
</body>
</html>
