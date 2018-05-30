<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:title/></title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>
    <style>
        html, body, .parent {
            height: 100%;
            overflow: hidden;
        }

        .top {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 50px;
            text-align: center;
            line-height: 50px;
            font-size: 20px;
            background: #c12c42;
            color: #ebf4f8;
        }

        .left {
            position: absolute;
            left: 0;
            top: 50px;
            bottom: 50px;
            width: 260px;
        }

        .right {
            position: absolute;
            left: 270px;
            right: 0;
            top: 50px;
            bottom: 50px;
        }

        .bottom {
            position: absoulte;
            left: 0;
            right: 0;
            bottom: 0;
            height: 50px;
        }
    </style>
    <meta charset="UTF-8"/>
    <sitemesh:head/>
</head>
<body>
<div class="parent" id="parent">
    <div class="top">
        <span style="float: left">当前登陆用户：
            <c:if test="${null != actUser.userName}">
                ${actUser.userName},
                <span id="userOutSpan" style="cursor:pointer;font-size: 15px;" onclick="userIdEmptyFun()">退出登录</span>
            </c:if>
        </span>
        activiti demo
        <span id="setUserSpan" style="float: right;cursor:pointer;">设置登陆用户</span>
    </div>
    <div class="left">
        <%@include file="/WEB-INF/views/include/menuleft.jsp" %>
    </div>
    <div class="right">
        <p><sitemesh:body/></p>
    </div>
    <div class="bottom" style="background-color: lightgreen;">
        <p>bottom</p>
    </div>
</div>
<script type="text/template" id="userIdSetBox">
    <div id="userIdSetDiv" action="" disabled="disabled">
        <br/>
        <label>用户登录名：</label><input id="userId" type="text" name="userId"/>
        <br/><br/>
        <input style="float: right;" id="userIdSetSubmit" class="btn btn-primary" type="button" value="   保    存   "
               onclick="userIdSetSubmitFun()"/>
    </div>
</script>
<script>

    function page(pageNo, pageSize, param) {
        var form = $("#searchForm");
        var maxPageNo = $("#pageSelect").attr("max");
        var minPageNo = $("#pageSelect").attr("min");
        if (pageNo > maxPageNo) {
            pageNo = maxPageNo;
        } else if (pageNo < minPageNo) {
            pageNo = minPageNo;
        }
        pageSize = pageSize < 1 ? $(form).find("#pageSize").val() : pageSize;
        $(form).find("#pageNo").val(pageNo);
        $(form).find("#pageSize").val(pageSize);
        $(form).submit();
    }

    function userIdSetSubmitFun() {
        var userId = $("#userIdSetDiv").find("#userId").val();
        $.post("${ctx}/workflow/identity/userIdSet", {userId: userId}, function (data) {
            if (data === 'true') {
                operationSucceedNotice('设置成功！！', function () {
                    location.reload();
                });
            } else {
                operationFailureNotice('设置失败，用户不存在！！');
            }
        });
    }

    function userIdEmptyFun() {
        $.post("${ctx}/workflow/identity/userIdEmpty", function (data) {
            if (data === 'true') {
                operationSucceedNotice('设置成功！！', function () {
                    location.reload();
                });
            }
        });
    }

    $(document).ready(function () {

        new jBox('Modal', {
            attach: '#setUserSpan',
            blockScroll: false,
            animation: 'zoomIn',
            draggable: 'title',
            closeButton: true,
            overlay: false,
            reposition: false,
            repositionOnOpen: false,
            title: '设置登陆用户',
            closeOnClick: false,
            content: $('#userIdSetBox').html()
        });

    });

</script>
</body>
</html>