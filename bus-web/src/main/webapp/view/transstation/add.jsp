<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=root%>/js/autoComplete.js"></script>
<script type="text/javascript"
            src="http://221.180.144.57:17095/gisability?ability=apiserver&abilityuri=webapi/auth.json&t=ajaxmap&v=3.0&key=1617610aa3f930281889eb824d81e3bcc67f4e9781c69212880f2e985e1dedf869c2483ece723d68"></script>
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
        <input type="hidden" id="coodinate" name="coodinate" value=""/>
		<fieldset>
			<legend> 基础信息 </legend>
			<div style="width:39%;height:600px;float: left">
				<label>
					<span>班次</span>
                    <input name="trips" id="trips" value="${ts.trips }" class="text-input"/>
                </label>
				<label>
					<span>站点</span>
                    <input name="cityStation.cityStationName" value="${ts.cityStation.cityStationName}" class="text-input" id="cityStationName"/>
                    <div id="divAutoList"></div>
                </label>
				<label>
					<span>站序</span>
                    <input name="stationOrder" id="stationOrder" value="${ts.stationOrder }" class="text-input"/>
                </label>
				<label>
					<span>到达时间</span>
                    <%--<input field="datetime" width="150" editor="datetimebox"/>--%>
                    <%--<input class="easyui-datetimebox" name="arriveTime" id="arriveTime" data-options="required:true,showSeconds:true"/>--%>
                    <input name="arriveTime" id="arriveTime" value="${ts.arriveTime }" class="text-input"/>格式：HH:mm
                </label>
				<label>
					<span>发车时间</span>
                    <%--<input class="easyui-datetimebox" name="departTime" id="departTime" data-options="required:true,showSeconds:true"/>--%>
                    <input name="departTime" id="departTime" value="${ts.departTime }" class="text-input"/>格式：HH:mm
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
            <div style="width:60%;height:300px;border: 1px solid gray;float: right;" id="iCenter"></div>
        </fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
    var mapObj, toolbar, overview, scale;
    var opt = {
        level: 10,//初始地图视野级别
        center: new MMap.LngLat(116.397428, 39.90923),//设置地图中心点
        doubleClickZoom: true,//双击放大地图
        rotateEnable: true,
        scrollwheel: true//鼠标滚轮缩放地图
    }

    mapObj = new MMap.Map("iCenter", opt);
    mapObj.plugin(["MMap.ToolBar", "MMap.OverView", "MMap.Scale"], function () {
        toolbar = new MMap.ToolBar();
        toolbar.autoPosition = false; //加载工具条
        mapObj.addControl(toolbar);
        overview = new MMap.OverView(); //加载鹰眼
        mapObj.addControl(overview);
        scale = new MMap.Scale(); //加载比例尺
        mapObj.addControl(scale);
    });

    $.extend($.fn.validatebox.defaults.rules, {
        time: {// 验证时间
            validator: function (value) {
                return /^([0-1][0-9]|[2][0-3]):([0-5][0-9])$/i.test(value);
            },
            message: '时间格式不正确'
        }
    });
    $('#trips').validatebox({
        required: true
    });
    $('#cityStationName').validatebox({
        required: true
    });
    $('#stationOrder').validatebox({
        required: true
    });
    $('#price').validatebox({
        required: true

    });
    $('#arriveTime').validatebox({
        required: true,
        validType: 'time'
    });
    $('#miles').validatebox({
        required: true
    });
    $('#departTime').validatebox({
        required: true,
        validType: 'time'
    });

    function addMarker(lonlat) {
        var LngLatX = lonlat.split(",")[0]; //获取Lng值
        var LngLatY = lonlat.split(",")[1]; //获取Lat值
        marker = new MMap.Marker({id: "m",
            position: new MMap.LngLat(LngLatX, LngLatY),
            icon: "http://webapi.amap.com/images/marker_sprite.png"}) //自定义构造MMap.Marker对象

        var arr = new Array();
        arr.push(marker);
        mapObj.addOverlays(arr);
    }
	function add() {
        var isValid = $('#tsForm').form('validate');
        if (!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            $.post("${ctx}/transstation/add", $("#tsForm").serializeArray(),
                    function (data) {
                        if (data.result == '0' || data.result == '2' || data.result == '3' || data.result == '1')
                            $.messager.alert('提示', data.alertInfo, 'info');
            });
	    }
    }
	
</script>
</html>
