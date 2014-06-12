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
	<form action="" class="formular" id="csForm">
		<%--<input type="hidden" id="lng" name="lng" value="${cs.lng }" />--%>
		<%--<input type="hidden" id="lat" name="lat" value="${cs.lat }" />--%>
		<input type="hidden" id="transdetail" name="transdetail" value="${cs.transdetail }" />
		<input type="hidden" id="id" name="id" value="${cs.id }" />

		<fieldset>
			<legend> 基础信息 </legend>
			<div style="width:39%;height:370px;float: left">
				<label>
					<span>城市代码</span>
					<input name="cityCode" value="${cs.cityCode }" class="text-input"/>
				</label>
				<label>
					<span>城市名称</span>
                    <input name="cityName" value="${cs.cityName }" class="text-input"/>
                </label>
                <label>
                    <span>交通工具类型</span>
                    <select name="transType" class="text-input" >
                    <option value="火车" <c:if test="${cs.transType=='火车' }" >selected</c:if>>火车</option>
                    <option value="飞机" <c:if test="${cs.transType=='飞机' }" >selected</c:if>>飞机</option>
                    <option value="轮渡" <c:if test="${cs.transType=='轮渡' }" >selected</c:if>>轮渡</option>
                    </select>
                    </label>
				<label>
					<span>站名</span>
					<input name="stationName" value="${cs.stationName }" class="text-input" />
				</label>
                <label>
                    <span>交通工具小类</span>
                    <label><input type="checkbox" class="checkbox" name="transdetailGroup" value="高铁" id="transdetail1" />高铁</label>
                    <label><input type="checkbox" class="checkbox" name="transdetailGroup" value="动车" id="transdetail2" />动车</label>
                    <label><input type="checkbox" class="checkbox" name="transdetailGroup" value="特快" id="transdetail3" />特快</label>
                    <label><input type="checkbox" class="checkbox" name="transdetailGroup" value="普快" id="transdetail4" />普快</label>
                    <label><input type="checkbox" class="checkbox" name="transdetailGroup" value="直达" id="transdetail5" />直达</label>
                </label>
                <label>
                    <span>站名坐标</span>
                    <input name="coordinate" value="${cs.coordinate }" class="text-input" />
                </label>
			</div>
			<div style="width:60%;height:300px;border: 1px solid gray;float: right;" id="container"> </div>
		</fieldset>
		<%--<fieldset>--%>
			<%--<legend> 详细信息 </legend>--%>
			<%--<div style="width:100%;">--%>
				<%--<script id="editor" name="adContent" type="text/plain"--%>
					<%--style="height: 200px;"></script>--%>
			<%--</div>--%>
		<%--</fieldset>--%>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
    // 处理checkbox
    var transdetail = '${cs.transdetail}';
    var transdetails = transdetail.split(";");
    for(var i = 0; i < transdetails.length;i++) {
        if(transdetails[i] == "高铁") {
            $("#transdetail1").attr("checked",true);
        }
        if(transdetails[i] == "动车") {
            $("#transdetail2").attr("checked",true);
        }
        if(transdetails[i] == "特快") {
            $("#transdetail3").attr("checked",true);
        }
        if(transdetails[i] == "普快") {
            $("#transdetail4").attr("checked",true);
        }
        if(transdetails[i] == "直达") {
            $("#transdetail5").attr("checked",true)
        }
    }


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
        var transdetails = "";
        $("input[name='transdetailGroup']").each(function(){
            if(true==$(this).attr("checked")) {
                transdetails = transdetails + $(this).attr('value') + ";";
            }
        })
        $("#transdetail").attr('value',transdetails);

		$.post("${ctx}/citystation/add", $("#csForm").serializeArray(),
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
		<%--editor.setContent('${cs.adContent }', false)--%>
	<%--}, 1000);--%>
	
</script>
</html>