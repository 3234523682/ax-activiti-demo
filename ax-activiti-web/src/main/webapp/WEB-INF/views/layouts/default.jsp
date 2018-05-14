<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="utf-8"  %>
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
        activiti demo
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
</script>
</body>
</html>