<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CCfe935a7c589f7ca959bae20c503de4"></script>
<script type="text/javascript" src="<%=root%>/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=root%>/js/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=root%>/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/autoComplete.js"></script>
<style type="text/css">
.clear {
	clear: both;
}
</style>
</head>
<body>
	<form action="" class="formular" id="tsForm">
        <input type="hidden" id="id" name="id" value="${ts.id }" />
        <input type="hidden" id="cityStationId" name="cityStation.id" value="${ts.cityStation.id}"/>

		<fieldset>
			<legend> 基础信息 </legend>
			<div style="width:39%;height:600px;float: left">
				<label>
					<span>班次</span>
                    <input name="trips" id="trips" value="${ts.trips }" class="text-input"/>
                </label>
				<label>
					<span>站点</span>
                    <input name="cityStation.stationName" value="${ts.cityStation.stationName}" class="text-input" id="stationName"/>
                    <div id="divAutoList"></div>
                </label>
				<label>
					<span>站序</span>
                    <input name="stationOrder" id="stationOrder" value="${ts.stationOrder }" class="text-input"/>
                </label>
				<label>
					<span>到达时间</span>
                    <input name="arriveTime" id="arriveTime" value="${ts.arriveTime }" class="text-input"/>
                </label>
				<label>
					<span>发车时间</span>
                    <input name="departTime" id="departTime" value="${ts.departTime }" class="text-input"/>
                </label>
				<label>
					<span>里程</span>
                    <input name="miles" id="miles" value="${ts.miles }" class="text-input"/>
                </label>
				<label>
					<span>票价</span>
                    <input name="price" id="price" value="${ts.price }" class="text-input"/>
                </label>
			</div>
		</fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
    $('#trips').validatebox({
        required: true
    });
    $('#stationName').validatebox({
        required: true
    });
    $('#stationOrder').validatebox({
        required: true
    });
    $('#arriveTime').validatebox({
        required: true
    });
    $('#departTime').validatebox({
        required: true
    });
    $('#miles').validatebox({
        required: true
    });
    $('#price').validatebox({
        required: true
    });
	function add() {
        var isValid = $('#tsForm').form('validate');
        if (!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            $.post("${ctx}/transstation/add", $("#tsForm").serializeArray(),
                    function (data) {
                        if (data.result == '0' || data.result == '2' || data.result == '3' || data.result == '1')
                            $.messager.alert('提示', data.alertInfo, 'info');
                        <%--if (data.result == '1') $.messager.confirm('提示', '确定要覆盖吗?', function (result) {--%>
                            <%--if (result) {--%>
                                <%--$('#id').val(data.id);--%>
                                <%--$.post("${ctx}/transstation/add", $("#tsForm").serializeArray(),--%>
                                        <%--function (data) {--%>
                                            <%--$.messager.alert('提示', data.alertInfo, 'info');--%>
                                            <%--$('#id').val('');--%>
                                        <%--});--%>
                            <%--}--%>
                        <%--});--%>
                    });
	    }
    }
	
</script>
</html>
