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
	<form action="" class="formular" id="taxiForm">
		<input type="hidden" id="id" name="id" value="${taxi.id }" />

		<fieldset>
			<legend> 基础信息 </legend>
			<div style="width:39%;height:620px;float: left">
				<label>
					<span>城市代码</span>
					<input name="cityCode" value="${taxi.cityCode }" class="text-input"/>
				</label>
				<label>
					<span>城市名称</span>
                    <input name="cityName" value="${taxi.cityName }" class="text-input"/>
                </label>
                <label>
					<span>日间时间区间</span>
                    <input name="d_timesection" value="${taxi.d_timesection }" class="text-input"/>
                </label>
                <label>
					<span>起步公里数</span>
                    <input name="d_s_miles" value="${taxi.d_s_miles }" class="text-input"/>
                </label>
                <label>
					<span>起步价</span>
                    <input name="d_s_cost" value="${taxi.d_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>超出起步单价</span>
                    <input name="d_exceed_s_cost" value="${taxi.d_exceed_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>超出规定距离单价</span>
                    <input name="d_s_exceed_d_cost" value="${taxi.d_s_exceed_d_cost }" class="text-input"/>
                </label>
                <label>
					<span>夜间时间区间</span>
                    <input name="n_timesection" value="${taxi.n_timesection }" class="text-input"/>
                </label>
                <label>
					<span>夜间起步公里数</span>
                    <input name="n_s_miles" value="${taxi.n_s_miles }" class="text-input"/>
                </label>
                <label>
					<span>夜间起步价(元)</span>
                    <input name="n_s_cost" value="${taxi.n_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>夜间超出起步单价</span>
                    <input name="n_exceed_s_cost" value="${taxi.n_exceed_s_cost }" class="text-input"/>
                </label>
                <label>
					<span>夜间超出规定距离单价</span>
                    <input name="n_s_exceed_d_cost" value="${taxi.n_s_exceed_d_cost }" class="text-input"/>
                </label>
			</div>
			<div style="width:60%;height:300px;border: 1px solid gray;float: right;" id="container"> </div>
		</fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">

	//以下两句话为创建地图
	var map = new BMap.Map("container");
	var lng = 123.438973;
	var lat = 41.811334;
	
	var _lng = 123.458973;
	var _lat = 41.83334;
	if (_lng != '' && _lat != '') {
		lng = _lng;
		lat = _lat;
	}else{
		$('#lng').val(lng);
		$('#lat').val(lat);
	}

	map.centerAndZoom(new BMap.Point(lng, lat), 14);
	var marker = new BMap.Marker(new BMap.Point(lng, lat)); // 创建标注
	map.addOverlay(marker);
	//鱼骨控件
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用
	//点击地图进行地址解析
	var gc = new BMap.Geocoder();
	map.addEventListener("click", function(e) {
		var pt = e.point;
		map.clearOverlays();
		var marker1 = new BMap.Marker(new BMap.Point(pt.lng, pt.lat)); // 创建标注
		map.addOverlay(marker1);

		$('#lng').val(pt.lng);
		$('#lat').val(pt.lat);
	});

    function getTransdetail(transdetails) {


    }

	function add() {

		$.post("${ctx}/taxi/add", $("#taxiForm").serializeArray(),
			function(data) {
				$.messager.alert('提示', "操作成功", 'info');
				//$('#MyPopWindow').window('close');
				//$('#userTable').datagrid('reload');

			});

	}




	<%--var editor;--%>
	<%--setTimeout(function(){--%>
		<%--//实例化编辑器--%>
		<%--editor = UE.getEditor('editor');--%>
	<%--}, 200);--%>
	<%----%>
	<%--setTimeout(function(){--%>
		<%--editor.setContent('${taxi.adContent }', false)--%>
	<%--}, 1000);--%>
	
</script>
</html>
