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
<style type="text/css">
.clear {
	clear: both;
}
</style>
</head>
<body>
	<form action="" class="formular" id="poiForm">
		<input type="hidden" id="orientation" name="orientation" value="${poi.orientation }" />
		<input type="hidden" id="id" name="id" value="${poi.id }" />

		<fieldset>
			<legend> 基础信息 </legend>
			<div style="width:39%;height:520px;float: left">
				<label>
					<span>城市代码</span>
					<input name="cityCode" id="cityCode" value="${poi.cityCode }" class="text-input"/>
				</label>
				<label>
					<span>站点ID</span>
                    <input name="poiPK.stationId" id="stationId" value="${poi.poiPK.stationId }" class="text-input"/>
                </label>
                <label>
					<span>POIID</span>
                    <input name="poiPK.poiId" id="poiId" value="${poi.poiPK.poiId }" class="text-input"/>
                </label>
                <label>
					<span>地标点名称</span>
                    <input name="poiName" id="poiName" value="${poi.poiName }" class="text-input"/>
                </label>
                <label>
					<span>地标点大类型</span>
                    <input name="poiType1" id="poiType1" value="${poi.poiType1 }" class="text-input"/>
                </label>
                <label>
					<span>地标点中类型</span>
                    <input name="poiType2" id="poiType2" value="${poi.poiType2 }" class="text-input"/>
                </label>
                <label>
					<span>地标点小类型</span>
                    <input name="poiType3" id="poiType3" value="${poi.poiType3 }" class="text-input"/>
                </label>
                <label>
					<span>地标点坐标</span>
                    <input name="poiCoordinate" id="poiCoordinate" value="${poi.poiCoordinate }" class="text-input"/>
                </label>
                <label>
					<span>地址</span>
                    <input name="address" id="address" value="${poi.address }" class="text-input"/>
                </label>
                <label>
					<span>电话</span>
                    <input name="tel" id="tel" value="${poi.tel }" class="text-input"/>
                </label>
			</div>
		</fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
    $('#cityCode').validatebox({
        required: true
    });
    $('#stationId').validatebox({
        required: true
    });
    $('#poiId').validatebox({
        required: true
    });
    $('#poiName').validatebox({
        required: true
    });
    $('#poiType1').validatebox({
        required: true
    });
    $('#poiType2').validatebox({
        required: true
    });
    $('#poiType3').validatebox({
        required: true
    });
    $('#poiCoordinate').validatebox({
        required: true
    });
	function add() {
        var isValid = $('#poiForm').form('validate');
        if (!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            var field = $("#poiForm").serializeArray();
            console.log(field);
            $.post("${ctx}/poi/add", $("#poiForm").serializeArray(),
                    function (data) {
                        $.messager.alert('提示', "操作成功", 'info');
                    });
        }

	}
</script>
</html>
