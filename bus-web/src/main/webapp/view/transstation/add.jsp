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
			<div style="width:auto;height:auto;float: left">
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
                    <span>发车方式</span>
                    <input type="radio" name="departType" value="0" id="fixDepartType"
                           onclick="departTypeChange()"/>固定发车
                    <input type="radio" name="departType" value="1" id="repeatDepartType"
                           onclick="departTypeChange()"/>循环发车
                </label>
                <label>
                    <span>首班车时间（HH:mm）</span>
                    <input name="firstDepartTime" id="firstDepartTime" value="${ts.firstDepartTime }" class="text-input"/>
                </label>
                <label>
                    <span>末班车时间（HH:mm）</span>
                    <input name="lastDepartTime" id="lastDepartTime" value="${ts.lastDepartTime }" class="text-input"/>
                </label>
                <label>
                    <div id="divDepartInterval"></div>
                </label>
				<label>
					<span>站序</span>
                    <input name="stationOrder" id="stationOrder" value="${ts.stationOrder }" class="text-input"/>
                </label>
				<label>
					<span>到达时间（HH:mm）</span>
                    <%--<input field="datetime" width="150" editor="datetimebox"/>--%>
                    <%--<input class="easyui-datetimebox" name="arriveTime" id="arriveTime" data-options="required:true,showSeconds:true"/>--%>
                    <input name="arriveTime" id="arriveTime" value="${ts.arriveTime }" class="text-input"/>
                </label>
				<label>
					<span>发车时间（HH:mm）</span>
                    <%--<input class="easyui-datetimebox" name="departTime" id="departTime" data-options="required:true,showSeconds:true"/>--%>
                    <input name="departTime" id="departTime" value="${ts.departTime }" class="text-input"/>
                </label>
                <label>
                    <span>天数</span>
                    <select name="dayCount" class="text-input" id="dayCount">
                        <option value="1" <c:if test="${cs.dayCount==1 }">selected</c:if>>1天</option>
                        <option value="2" <c:if test="${cs.dayCount==2 }">selected</c:if>>2天</option>
                        <option value="3" <c:if test="${cs.dayCount==3 }">selected</c:if>>3天</option>
                        <option value="4" <c:if test="${cs.dayCount==4 }">selected</c:if>>4天</option>
                        <option value="5" <c:if test="${cs.dayCount==5 }">selected</c:if>>5天</option>
                    </select>
                </label>
				<label>
					<span>里程（公里）</span>
                    <input name="miles" id="miles" value="${ts.miles }" class="text-input"/>
                </label>
				<label>
					<span>票价（元）</span>
                    <input name="price" id="price" value="${ts.price }" class="text-input"/>
                </label>
			</div>
            <div style="width:60%;height:300px;border: 1px solid gray;float: right;" id="iCenter"></div>
        </fieldset>
        <a href="javascript:add()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
	</form>
</body>
<script type="text/javascript">
    <c:choose>
    <c:when test="${ts.departType=='0' }">
    $('#fixDepartType').attr("checked", "checked");
    </c:when>
    <c:when test="${ts.departType=='1' }">
    $('#repeatDepartType').attr("checked", "checked");
    </c:when>
    <c:otherwise>
    $('#fixDepartType').attr("checked", "checked");
    </c:otherwise>
    </c:choose>

    var mapObj, toolbar, overview, scale;


    departTypeChange();
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
        validType: 'time'
    });
    $('#miles').validatebox({
        required: true
    });
    $('#departTime').validatebox({
        validType: 'time'
    });
    $('#firstDepartTime').validatebox({
        validType: 'time'
    });
    $('#lastDepartTime').validatebox({
        validType: 'time'
    });

    $('#departInterval').validatebox({
        validType: 'number'
    });

    $(document).ready(function () {
        var cityStationCoordinate = "${ts.cityStation.coordinate}";

        if (cityStationCoordinate != null && cityStationCoordinate != "") {
            loadMap(cityStationCoordinate);
            addMarker(cityStationCoordinate);
        } else {
            loadMap("116.397428, 39.90923");
        }
    });

    function loadMap(lonlat) {
        var LngLatX = lonlat.split(",")[0]; //获取Lng值
        var LngLatY = lonlat.split(",")[1]; //获取Lat值
        var opt = {
            level: 10,//初始地图视野级别
            center: new MMap.LngLat(LngLatX, LngLatY),//设置地图中心点
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
    }

    function stationOrderChanged() {
        // 发车时间与到达时间根据站序做限制
        var stationOrder = $('#stationOrder').val();
        if (stationOrder < 2) {
            $('#arriveTime').validatebox({
                required: true
            });
        }
    }

    function departTypeChange() {
        var selected = $('input[name="departType"]:checked').val();
        var divDepartInterval = $("#divDepartInterval"); //可供选择的项
        divDepartInterval.empty();
        if(selected == 1) {
            divDepartInterval.append("<span > 发车间隔（分钟） </span >");
            var item1 = "<input name='departInterval' id='departInterval' value='${ts.departInterval}' class='text-input'/>";
            divDepartInterval.append(item1);
        }
    }

    function addMarker(lonlat) {
        var LngLatX = lonlat.split(",")[0]; //获取Lng值
        var LngLatY = lonlat.split(",")[1]; //获取Lat值
        marker = new MMap.Marker({id: "m",
            position: new MMap.LngLat(LngLatX, LngLatY),
            icon: "<%=root%>/img/marker_sprite.png"})//自定义构造MMap.Marker对象

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
