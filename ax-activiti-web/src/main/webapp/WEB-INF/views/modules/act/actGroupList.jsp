<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="decorator" content="default"/>
    <title>分组列表</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/workflow/identity/actGroupList">分组列表</a></li>
    <li><a href="${ctx}/workflow/identity/createActGroup">新建分组</a></li>
</ul>
<form id="searchForm" action="${ctx}/workflow/identity/actGroupList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNum" type="hidden" value="1"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>分组ID：</label><input type="text" id="groupId" name="groupId" value="${queryParam.groupId}" class="input-medium"/>
    <label>分组名称：</label><input type="text" id="groupName" name="groupName" value="${queryParam.groupName}" class="input-medium"/>
    <label>分组类型：</label><input type="text" id="groupType" name="groupType" value="${queryParam.groupType}" class="input-medium"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<%--<div style="text-align: right"><a target="_blank" href="${ctx}/workflow/model/create">创建</a></div>--%>
<table width="100%" class="table table-striped table-bordered table-condensed table-nowrap">
    <thead>
    <tr>
        <th>分组ID</th>
        <th>分组名称</th>
        <th>分组类型</th>
        <th>分组成员</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="group">
        <tr>
            <td>${group.groupId }</td>
            <td>${group.groupName}</td>
            <td>${group.groupType}</td>
            <td>
                <c:set var="userNames" value=""/>
                <c:forEach items="${group.actUserVOs}" var="actUser">
                    <c:set var="userNames" value="${userNames}、${actUser.userName }"/>
                </c:forEach>
                    ${userNames.length() > 0 ? userNames.substring(1,userNames.length()) : ''}
            </td>
            <td>
                <a href="${ctx}/workflow/identity/updateActGroup?groupId=${group.groupId}">编辑</a>
                <a href="${ctx}/workflow/identity/deleteActGroup?groupId=${group.groupId}">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>