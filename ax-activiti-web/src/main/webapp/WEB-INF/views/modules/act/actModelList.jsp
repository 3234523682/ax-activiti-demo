<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="decorator" content="default"/>
    <title>流程列表</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/workflow/model/list">流程列表</a></li>
    <li><a href="${ctx}/workflow/model/create">新建模型</a></li>
</ul>
<form id="searchForm" action="${ctx}/workflow/model/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNum" type="hidden" value="1"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>模型ID：</label><input type="text" id="modelId" name="modelId" value="${queryParam.modelId}" class="input-medium"/>
    <label>模型标识：</label><input type="text" id="modelKey" name="modelKey" value="${queryParam.modelKey}" class="input-medium"/>
    <label>模型名称：</label><input type="text" id="modelName" name="modelName" value="${queryParam.modelName}" class="input-medium"/>
    <label>模型类型：</label><input type="text" id="modelCategory" name="modelCategory" value="${queryParam.modelCategory}" class="input-medium"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<%--<div style="text-align: right"><a target="_blank" href="${ctx}/workflow/model/create">创建</a></div>--%>
<table width="100%" class="table table-striped table-bordered table-condensed table-nowrap">
    <thead>
    <tr>
        <th>ID</th>
        <th>标识</th>
        <th>分类</th>
        <th>名称</th>
        <th>版本</th>
        <th>创建时间</th>
        <th>最后更新时间</th>
        <th>元数据</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="model">
        <tr>
            <td>${model.actModelId }</td>
            <td>${model.actModelKey}</td>
            <td>${model.actModelCategory}</td>
            <td>${model.actModelName}</td>
            <td>${model.actModelVersion}</td>
            <td><fmt:formatDate value="${model.actModelCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${model.actModelLastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${model.actModelMetaInfo}</td>
            <td>
                <a href="${ctx}/act/process-editor/modeler.jsp?modelId=${model.actModelId}" target="_blank">编辑</a>
                <a href="${ctx}/workflow/model/deploy/${model.actModelId}">部署</a>
                <a href="${ctx}/workflow/model/export?modelId=${model.actModelId}" target="_blank">导出</a>
                <a href="${ctx}/workflow/model/showModelPicture?modelId=${model.actModelId}" target="_blank">导出图片</a>
                <a href="${ctx}/workflow/model/delete/${model.actModelId}">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>