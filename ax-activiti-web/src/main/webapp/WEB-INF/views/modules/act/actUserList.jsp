<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="decorator" content="default"/>
    <title>用户列表</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/workflow/identity/actUserList">用户列表</a></li>
    <li><a href="${ctx}/workflow/identity/createActUser">新建用户</a></li>
</ul>
<form id="searchForm" action="${ctx}/workflow/identity/actUserList" method="get" class="breadcrumb form-search">
    <input id="pageNo" name="pageNum" type="hidden" value="1"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>用户ID：</label><input type="text" id="userId" name="userId" value="${queryParam.userId}" class="input-medium"/>
    <label>用户名称：</label><input type="text" id="userName" name="userName" value="${queryParam.userName}" class="input-medium"/>
    <label>所属分组：</label>
    <select id="groupId" name="groupId" class="input-medium">
        <option value="">请选择</option>
        <c:forEach items="${actGroups}" var="group">
            <option value="${group.groupId}" ${group.groupId==queryParam.groupId?'selected':''}>${group.groupName}</option>
        </c:forEach>
    </select>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<%--<div style="text-align: right"><a target="_blank" href="${ctx}/workflow/model/create">创建</a></div>--%>
<table width="100%" class="table table-striped table-bordered table-condensed table-nowrap">
    <thead>
    <tr>
        <th>用户ID</th>
        <th>用户名称</th>
        <th>用户邮箱</th>
        <th>所属分组</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td>${user.userId }</td>
            <td>${user.userName}</td>
            <td>${user.userEmail}</td>
            <td>
                <c:set var="groupNames" value=""/>
                <c:forEach items="${user.actGroupVOs}" var="actGroup">
                    <c:set var="groupNames" value="${groupNames}、${actGroup.groupName}"/>
                </c:forEach>
                    ${groupNames.length() > 0 ? groupNames.substring(1,groupNames.length()) : ''}
            </td>
            <td>
                <a href="${ctx}/workflow/identity/updateActUser?userId=${user.userId}">编辑</a>
                <a href="${ctx}/workflow/identity/deleteActUser?userId=${user.userId}">删除</a>
                <a href="${ctx}/workflow/identity/actUserPacketManaged?userId=${user.userId}">分组管理</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>