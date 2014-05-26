<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/template.css" type="text/css" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CCfe935a7c589f7ca959bae20c503de4"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<style type="text/css">
	body,html,#allmap {
		width: 100%;
		height: 100%;
		overflow: hidden;
		margin: 0;
	}
</style>
<title>动态公交监控工具</title>
</head>
<body>
	<div style="margin: 10px; width: 100%; height: 100%;font-size:10pt;">
		<div style="width: 80%; float: left;">
			公交线路查询：<input type="text" id="lineName" value=""
						style="width: 100px; border: 1px solid #C0C0C0"> 
			<input type="button" value="查看" style="border: 1px solid #C0C0C0"
						onclick="busSearch(document.getElementById('lineName').value);">
			刷新时间(s)：
			<select id="timer" onchange="selectChange();">
				<option value="5000">5</option>
				<option selected="selected" value="10000">10</option>
				<option value="20000">20</option>
			</select>
			<input type="button" value="停止" style="border: 1px solid #C0C0C0"
						onclick="stopRefresh();">
			<input type="button" value="继续" style="border: 1px solid #C0C0C0"
						onclick="selectChange();">
			<input type="text" id="busId" value=""
						style="width: 50px; border: 1px solid #C0C0C0">
			<input type="button" value="锁定" style="border: 1px solid #C0C0C0"
						onclick="trace(document.getElementById('busId').value);">
			<input type="text" id="fixPoint" value=""
						style="width: 150px; border: 1px solid #C0C0C0">
		</div>
		<div
			style="width: 70%; height: 89%; border: 1px solid gray;margin-top: 5px; float: left"
			id="container"></div>
		<div
			style="width: 28%; height: 89%; overflow: scroll; margin-top: 5px;border: 1px solid gray; float: left;">
			<ul id="r-result">
				
			</ul>	
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var map = new BMap.Map("container");
	var point = new BMap.Point(123.436815, 41.80972);
	map.centerAndZoom(point, 13);

	//鱼骨控件
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用

	/*
	//公交线路对象
	var busline = new BMap.BusLineSearch(map, {
		renderOptions : {
			map : map,
			panel : "r-result"
		},
		onGetBusListComplete : function(result) {
			if (result) {
				var line = result.getBusListItem(0);//获取第一个公交列表显示到map上
				busline.getBusLine(line);
			}
		}
	});
	*/

	//公交线路查询
	function busSearch(name) {
		//首先要引入jquery的js包
		jQuery(document).ready(function(){
			$.ajax({
				type : "get", //jquey是不支持post方式跨域的
				url : '<%=root%>/bus/gps?lid='+name,
				contentType: "application/json",
				dataType: 'json',
				success : gpsResult
			});
		});
	}
	
	//画线方法
	function showLineByLatLon(lonlats){
		var lonlatArray = lonlats.split(";");
		var pointArrays = new Array();
		for(var i=0;i<lonlatArray.length;i++){
			var lonlat = lonlatArray[i];
			if(lonlat.split(",").length==2){
				pointArrays[i]= new BMap.Point(lonlat.split(",")[0],lonlat.split(",")[1]);
				point = pointArrays[i];
			}
		}
		
		var polyline = new BMap.Polyline(pointArrays, {strokeColor:"green", strokeWeight:6, strokeOpacity:0.5});
		map.addOverlay(polyline);	
	}
	
	var bid = null;
	function trace(busId){
		bid = busId;
	}
	
	//坐标转换完之后的回调函数
	translateCallback = function (point){
		var iconPath = '<%=root%>/img/xx1.png';
		var myIcon = new BMap.Icon(iconPath, new BMap.Size(19,25));
		var marker = new BMap.Marker(point,{icon:myIcon});  // 创建标注
		map.addOverlay(marker);              // 将标注添加到地图中
	}
	
	
	var dataMap;
	function gpsResult(result){
		dataMap = result;
		
		var data = null;
		var html = '';
		for (var uid in result) { 
			var bus = result[uid];
			html = html+'<li style="margin-top: 5px"><a href="javascript:selectLine(\''+bus.uid+'\')">'+bus.name+'</li>';
			data = bus;
		}
		
		$("#r-result").html(html);
		
		if(data == null){
			return;
		}
		
		showResult(data);
	}
	
	function selectLine(uid){
		var data = dataMap[uid];
		showResult(data);
	}
	
	function showResult(data){
		//清楚覆盖物
		map.clearOverlays();
		//画线
		showLineByLatLon(data.geo);
		
		setTimeout(function(){
			if(data.gps.length > 0){
				for(var i=0;i<data.gps.length;i++){
					var busId = data.gps[i].split("#")[0];
					//修正后经纬度
					var lonlat = data.gps[i].split("#")[2];
					var pCenter = new BMap.Point(lonlat.split(",")[0],lonlat.split(",")[1]);
					var marker1 = new BMap.Marker(pCenter);  // 创建标注
					var label = new BMap.Label(busId,{offset:new BMap.Size(20,-10)});
					marker1.setLabel(label);
					map.addOverlay(marker1);
					
					//原始84经纬度
					//var _lonlat = data.gps[i].split("#")[3];
					//var gps = new BMap.Point(_lonlat.split(",")[0],_lonlat.split(",")[1]);
					//BMap.Convertor.translate(gps,0,translateCallback);
					
					if(bid ==  busId){
						map.centerAndZoom(pCenter, map.getZoom());
						//fixPoint
						$("#fixPoint").val(lonlat);
					}
				}
			}
		}, 1000);
	}
	
	var time = $("#timer").val();
	var t = window.setInterval('loadGps()',time);
	
	function selectChange(){
		time = $("#timer").val();
		window.clearInterval(t);
		t = window.setInterval('loadGps()',time);
	}
	
	function stopRefresh(){
		window.clearInterval(t);
	}
</script>
