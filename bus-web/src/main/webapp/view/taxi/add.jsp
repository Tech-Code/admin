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
	<form action="" class="formular" id="taxiForm">
		<input type="hidden" id="id" name="id" value="${taxi.id }" />

		<fieldset>
			<legend> 基础信息 </legend>
            <div style="width:auto;height:auto;float: left">
				<label>
					<span>城市代码</span>
					<input name="cityCode" id="cityCode" value="${taxi.cityCode }" class="text-input"/>
				</label>
				<label>
					<span>城市名称</span>
                    <input name="cityName" id="cityName" value="${taxi.cityName }" class="text-input"/>
                </label>
                <label>
					<span>日间时间区间</span>
                    <input name="d_timesection" id="d_timesection" value="${taxi.d_timesection }" class="text-input"/>
                </label>
                <label>
					<span>起步公里数</span>
                    <input name="d_s_miles" id="d_s_miles" value="${taxi.d_s_miles }" class="text-input"/>
                </label>
                <label>
					<span>起步价</span>
                    <input name="d_s_cost" id="d_s_cost" value="${taxi.d_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>超出起步单价</span>
                    <input name="d_exceed_s_cost" id="d_exceed_s_cost" value="${taxi.d_exceed_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>超出规定距离单价</span>
                    <input name="d_s_exceed_d_cost" id="d_s_exceed_d_cost" value="${taxi.d_s_exceed_d_cost }" class="text-input"/>
                </label>
                <label>
					<span>夜间时间区间</span>
                    <input name="n_timesection" id="n_timesection" value="${taxi.n_timesection }" class="text-input"/>
                </label>
                <label>
					<span>夜间起步公里数</span>
                    <input name="n_s_miles" id="n_s_miles" value="${taxi.n_s_miles }" class="text-input"/>
                </label>
                <label>
					<span>夜间起步价(元)</span>
                    <input name="n_s_cost" id="n_s_cost" value="${taxi.n_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>夜间超出起步单价</span>
                    <input name="n_exceed_s_cost" id="n_exceed_s_cost" value="${taxi.n_exceed_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>夜间超出规定距离单价</span>
                    <input name="n_s_exceed_d_cost" id="n_s_exceed_d_cost" value="${taxi.n_s_exceed_d_cost }" class="text-input"/>
                </label>
                <label>
					<span>日间燃油税</span>
                    <input name="d_bas" id="d_bas" value="${taxi.d_bas }" class="text-input"/>
                </label>
                <label>
					<span>夜间燃油税</span>
                    <input name="n_bas" id="n_bas" value="${taxi.n_bas }" class="text-input"/>
                </label>
                <label>
					<span>日间规定距离</span>
                    <input name="d_exceed_distance" id="d_exceed_distance" value="${taxi.d_exceed_distance }" class="text-input"/>
                </label>
                <label>
					<span>夜间规定距离</span>
                    <input name="n_exceed_distance" id="n_exceed_distance" value="${taxi.n_exceed_distance }" class="text-input"/>
                </label>

			</div>
		</fieldset>
        <a href="javascript:add()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
	</form>
</body>
<script type="text/javascript">
    $('#cityCode').validatebox({
        required: true
    });
    $('#cityName').validatebox({
        required: true
    });
    $('#d_timesection').validatebox({
        required: true
    });
    $('#d_s_miles').validatebox({
        required: true
    });
    $('#d_s_cost').validatebox({
        required: true
    });
    $('#d_exceed_s_cost').validatebox({
        required: true
    });
    $('#d_s_exceed_d_cost').validatebox({
        required: true
    });
    $('#n_timesection').validatebox({
        required: true
    });
    $('#n_s_miles').validatebox({
        required: true
    });
    $('#n_s_cost').validatebox({
        required: true
    });
    $('#n_exceed_s_cost').validatebox({
        required: true
    });
    $('#n_s_exceed_d_cost').validatebox({
        required: true
    });
    $('#d_bas').validatebox({
        required: true
    });
    $('#n_bas').validatebox({
        required: true
    });
    $('#d_exceed_distance').validatebox({
        required: true
    });
    $('#n_exceed_distance').validatebox({
        required: true
    });


	function add() {
        var isValid = $('#taxiForm').form('validate');
        if (!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            $.post("${ctx}/taxi/add", $("#taxiForm").serializeArray(),
                    function (data) {
                        if (data.result == '0' || data.result == '2' || data.result == '6')
                            $.messager.alert('提示', data.alertInfo, 'info');
                    });

        }
	}
</script>
</html>
