<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
.clear {
	clear: both;
}
</style>
</head>
<body>
	<form action="" class="formular" id="speedForm">
		<input type="hidden" id="id" name="id" value="${speed.id }" />

		<fieldset>
			<legend> 基础信息 </legend>
            <div style="width:auto;height:auto;float: left">
				<label>
                    <span>交通工具类型</span>
                    <select name="transportType" class="text-input">
                        <option value="0" <c:if test="${speed.transportType==0 }">selected</c:if>>地铁</option>
                        <option value="1" <c:if test="${speed.transportType==1 }">selected</c:if>>公交车</option>
                        <option value="2" <c:if test="${speed.transportType==2 }">selected</c:if>>出租车</option>
                        <option value="3" <c:if test="${speed.transportType==3 }">selected</c:if>>步行</option>
                    </select>
				</label>
				<label>
					<span>交通工具明细</span>
                    <input name="tranSportDes" id="tranSportDes" value="${speed.tranSportDes }" class="text-input"/>
                </label>
                <label>
					<span>时速（km/h）</span>
                    <input name="speed" id="speed" value="${speed.speed }" class="text-input"/>
                </label>
                <%--<label>--%>
					<%--<span>城市代码</span>--%>
                    <%--<input name="cityCode" id="cityCode" value="${speed.cityCode }" class="text-input"/>--%>
                <%--</label>--%>
                <%--<label>--%>
					<%--<span>城市名</span>--%>
                    <%--<input name="cityName" id="cityName" value="${speed.cityName }" class="text-input"/>--%>
                <%--</label>--%>
			</div>
		</fieldset>
        <a href="javascript:add()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
	</form>
</body>
<script type="text/javascript">
    //$('#tranSportDes').validatebox({
    //    required: true
    //});
    $('#speed').validatebox({
        required: true
    });
    //$('#cityCode').validatebox({
    //    required: true
    //});
    //$('#cityName').validatebox({
    //    required: true
    //});
	function add() {
        var isValid = $('#speedForm').form('validate');
        if (!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            $.post("${ctx}/speed/add", $("#speedForm").serializeArray(),
                    function (data) {
                        if (data.result == '0' || data.result == '2' || data.result == '7')
                            $.messager.alert('提示', data.alertInfo, 'info');
                    });
        }

	}
</script>
</html>
