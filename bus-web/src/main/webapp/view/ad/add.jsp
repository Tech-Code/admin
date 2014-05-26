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
	<form action="" class="formular" id="adForm">
		<input type="hidden" name="id" value="${ad.id }" />
		<input type="hidden" id="lng" name="lng" value="${ad.lng }" />
		<input type="hidden" id="lat" name="lat" value="${ad.lat }" />
		<fieldset>
			<legend> 基础信息 </legend>
			<div style="width:39%;height:300px;float: left">
				<label>
					<span>登录名称</span>
					<input name="title" value="${ad.title }" class="text-input"/>
				</label>
				<label>
					<span>开始日期</span>
					<input class="Wdate text-input" type="text" name="startTimeStr" value="${ad.startTimeStr }" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',readOnly:true})">
				</label>
				<label>
					<span>结束日期</span>
					<input class="Wdate text-input" type="text" name="overTimeStr" value="${ad.overTimeStr }" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',readOnly:true})">
				</label>
				<label>
					<span>链接地址</span>
					<input name="url" value="${ad.url }" class="text-input" />
				</label>
				<label>
					<span>广告类型</span>
					<select name="type" class="text-input" >
						<option value="0" <c:if test="${ad.type==0 }" >selected</c:if>>食品餐饮</option>
						<option value="1" <c:if test="${ad.type==1 }" >selected</c:if>>购物</option>
						<option value="2" <c:if test="${ad.type==2 }" >selected</c:if>>住宿</option>
						<option value="3" <c:if test="${ad.type==3 }" >selected</c:if>>出行</option>
					</select>
				</label>
			</div>
			<div style="width:60%;height:300px;border: 1px solid gray;float: right;" id="container"> </div>
		</fieldset>
		<fieldset>
			<legend> 详细信息 </legend>
			<div style="width:100%;">
				<script id="editor" name="adContent" type="text/plain"
					style="height: 200px;"></script>
			</div>
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
	
	var _lng = '${ad.lng }';
	var _lat = '${ad.lat }';
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

	function add() {
		$.post("${ctx}/ad/add", $("#adForm").serializeArray(),
			function(data) {
				$.messager.alert('提示', "操作成功", 'info');
				$('#MyPopWindow').window('close');
				$('#userTable').datagrid('reload');
			});
	}
	
	var editor;
	setTimeout(function(){
		//实例化编辑器
		editor = UE.getEditor('editor');
	}, 200);
	
	setTimeout(function(){
		editor.setContent('${ad.adContent }', false)
	}, 1000);
	
</script>
</html>
