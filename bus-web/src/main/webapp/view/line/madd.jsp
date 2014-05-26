<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CCfe935a7c589f7ca959bae20c503de4"></script>
</head>

<body>
	<form class="formular" id="userForm" method="post">
		<input type="hidden" name="id" value="${model.id }" />
		<fieldset>
			<legend>
				匹配关系
			</legend>
			<div style="float: left;width: 33%">
				<label>
					<span>唯一标识</span>
					<input class="text-input" type="text" name="lineId" value="${model.lineId }" />
				</label>
				<label>
					<span>线路名称</span>
					<input class="text-input" type="text" id="name" name="name" value="${model.name }" />
					<a href="#" onclick="busSearch(document.getElementById('name').value,'');">匹配</a>
				</label>
				<div style="margin-top: 10px;" id="r-result"></div> 
			</div>
			<div style="float: right;width: 65%;">
				<div
					style="width: 100%; height: 350px; border: 1px solid gray;margin-top: 5px; float: right"
					id="container"></div>
			</div>
		</fieldset>
		</br>
		<a href="#" id="btn-add" onclick="addOrUpdate();" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		<a href="#" id="btn-back" onclick="javascript:parent.closeTab();" class="easyui-linkbutton" iconCls="icon-back">返回</a>
	</form>
	
</body>
<script type="text/javascript">
	busSearch($('#name').val(),'${model.uid }');

	var map = new BMap.Map("container");
	var point = new BMap.Point(123.436815, 41.80972);
	map.centerAndZoom(point, 12);
	
	//鱼骨控件
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用

	function addOrUpdate() {
		$.post("${ctx}/bus/match/addOrUpdate", $("#userForm").serializeArray(), function(
				data) {
			$.messager.alert('提示', data.mes, 'info');
			$('#MyPopWindow').window('close');
			$('#userTable').datagrid('reload');
		});
	}
	
	//公交线路查询
	function busSearch(name,uid){
		if(name == '' || name == null){
			return;
		}
		
		jQuery(document).ready(function(){
			$.ajax({
				type : "get", //jquey是不支持post方式跨域的
				url : "<%=root%>/bus/match/line?name="+name+"&uid="+uid,
				contentType: "application/json",
				dataType: 'json',
				success : findLineResult
			});
		});
	}
	
	var lineMap = {};
	function findLineResult(data){
		if(data.rows.length > 0){
			var ids;
			var html = '';
			for(var i=0;i<data.rows.length;i++){
				var name = data.rows[i].name;
				var uid = data.rows[i].uid;
				var geoResult = data.rows[i].geoResult;
				
				lineMap[uid] = geoResult;
				if(i == 0){
					ids = uid;
					showLine(geoResult);
				}else{
					ids = ids+","+uid;
				}
				
				if('${model.uid}'.indexOf(uid) >= 0){
					html = html+'<input name="uid" style="display: inline-block;" checked=true value="'+uid+'" type="checkbox"/><a href="javascript:selectLine(\''+uid+'\')">'+name+'<br/>';
				}else{
					html = html+'<input name="uid" style="display: inline-block;" value="'+uid+'" type="checkbox"/><a href="javascript:selectLine(\''+uid+'\')">'+name+'<br/>';
				}
			}
			$("#r-result").html(html);
		}
	}
	
	function showLine(lonlats){
		map.clearOverlays();
		
		var lonlatArray = lonlats.split(";");
		var pointArrays = new Array();
		for(var i=0;i<lonlatArray.length;i++){
			var lonlat = lonlatArray[i];
			if(lonlat.split(",").length==2){
				pointArrays[i]= new BMap.Point(lonlat.split(",")[0],lonlat.split(",")[1]);
				point = pointArrays[i];
			}
		}
		
		map.centerAndZoom(point, 14);
		
		var polyline = new BMap.Polyline(pointArrays, {strokeColor:"green", strokeWeight:6, strokeOpacity:0.5});
		map.addOverlay(polyline);	
	}
	
	function selectLine(uid){
		var points = lineMap[uid];
		showLine(points);
	}
</script>
</html>
