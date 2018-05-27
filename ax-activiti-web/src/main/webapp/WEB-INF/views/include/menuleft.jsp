<%@ page contentType="text/html;charset=UTF-8" %>

<div class="accordion">
    <ul id="accordion">
        <li>
            <div class="link"><i class="fa fa-paint-brush"></i>模型管理<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="${ctx}/workflow/model/list">模型列表</a></li>
                <li><a href="${ctx}/workflow/model/create">新建模型</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-code"></i>流程管理<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="${ctx}/workflow/process/list">流程列表</a></li>
                <li><a href="${ctx}/workflow/process/running">运行流程</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-mobile"></i>任务管理<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <%--<li><a href="${ctx}/workflow/task/awaitClaimTaskList">待签任务</a></li>--%>
                <li><a href="${ctx}/workflow/task/alreadyClaimTaskList">待办任务</a></li>
                <li><a href="${ctx}/workflow/task/historicTaskList">已办任务</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-paint-brush"></i>身份管理<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="${ctx}/workflow/identity/actUserList">用户列表</a></li>
                <li><a href="${ctx}/workflow/identity/actGroupList">分组列表</a></li>
            </ul>
        </li>
    </ul>
</div>
