<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="procInsId" type="java.lang.String" required="true" description="流程实例ID" %>
<%@ attribute name="startActId" type="java.lang.String" required="false" description="开始活动节点名称" %>
<%@ attribute name="endActId" type="java.lang.String" required="false" description="结束活动节点名称" %>
<fieldset>
    <legend>流转信息</legend>
    <div id="histoicFlowList">
        正在加载流转信息...
    </div>
</fieldset>
<script type="text/javascript">
    $.get("${ctx}/workflow/task/histoicFlow?procInsId=${procInsId}&startAct=${startActId}&endAct=${endActId}&t=" + new Date().getTime(), function (data) {
        $("#histoicFlowList").html(data);
    });
</script>