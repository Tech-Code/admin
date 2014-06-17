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
	<form action="" class="formular" id="tsForm">
        <input type="hidden" id="id" name="id" value="${ts.id }" />

		<fieldset>
			<legend> 基础信息 </legend>
			<div style="width:39%;height:600px;float: left">
                <label>
                    <span>交通工具类型</span>
                    <select name="transType" class="text-input" >
                    <option value="火车" <c:if test="${ts.transType=='火车' }" >selected</c:if>>火车</option>
                    <option value="飞机" <c:if test="${ts.transType=='飞机' }" >selected</c:if>>飞机</option>
                    <option value="轮渡" <c:if test="${ts.transType=='轮渡' }" >selected</c:if>>轮渡</option>
                    </select>
                </label>
				<label>
					<span>班次</span>
                    <input name="trips" value="${ts.trips }" class="text-input"/>
                </label>
                <label>
                    <span>交通工具小类</span>
                    <select name="transDetail" class="text-input">
                        <option value="高铁" <c:if test="${ts.transDetail=='高铁' }">selected</c:if>>高铁</option>
                        <option value="动车" <c:if test="${ts.transDetail=='动车' }">selected</c:if>>动车</option>
                        <option value="特快" <c:if test="${ts.transDetail=='特快' }">selected</c:if>>特快</option>
                        <option value="普快" <c:if test="${ts.transDetail=='普快' }">selected</c:if>>普快</option>
                        <option value="直达" <c:if test="${ts.transDetail=='直达' }">selected</c:if>>直达</option>
                    </select>
                </label>
				<label>
					<span>城市代码</span>
                    <input name="cityCode" value="${ts.cityCode }" class="text-input"/>
                </label>
				<label>
					<span>站点</span>
                    <input name="station" value="${ts.station }" class="text-input"/>
                </label>
				<label>
					<span>站序</span>
                    <input name="stationOrder" value="${ts.stationOrder }" class="text-input"/>
                </label>
				<label>
					<span>站点坐标</span>
                    <input name="coordinate" value="${ts.coordinate }" class="text-input"/>
                </label>
				<label>
					<span>到达时间</span>
                    <input name="arriveTime" value="${ts.arriveTime }" class="text-input"/>
                </label>
				<label>
					<span>发车时间</span>
                    <input name="departTime" value="${ts.departTime }" class="text-input"/>
                </label>
				<label>
					<span>里程</span>
                    <input name="miles" value="${ts.miles }" class="text-input"/>
                </label>
				<label>
					<span>票价</span>
                    <input name="price" value="${ts.price }" class="text-input"/>
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

		$.post("${ctx}/transstation/add", $("#tsForm").serializeArray(),
			function(data) {
				$.messager.alert('提示', "操作成功", 'info');
				//$('#MyPopWindow').window('close');
				//$('#userTable').datagrid('reload');

			});

	}
	
</script>
</html>
