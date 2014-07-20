<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
            src="http://221.180.144.57:17095/gisability?ability=apiserver&abilityuri=webapi/auth.json&t=ajaxmap&v=3.0&key=1617610aa3f930281889eb824d81e3bcc67f4e9781c69212880f2e985e1dedf869c2483ece723d68"></script>
<style type="text/css">
.clear {
	clear: both;
}
</style>
</head>
<body>
	<form action="" class="formular" id="csForm">
		<input type="hidden" id="transdetail" name="transdetail" value="${cs.transdetail }" />
		<input type="hidden" id="id" name="id" value="${cs.id }" />
		<input type="hidden" id="poiStationId" name="poiStationId"/>
        <input type="hidden" id="cityCode" name="cityCode" value="${cs.cityCode }"/>
		<fieldset>
			<legend> 基础信息 </legend>
            <div style="width:auto;height:auto;float: left">
				<label>
					<span>城市名称</span>
                    <input name="cityName" id="cityName" value="${cs.cityName }" class="text-input"/>
                </label>
                <label>
                    <span>交通工具类型</span>
                    <select name="transType" class="text-input" id="transType" onchange="changeTransDetail()">
                    <option value="150100" <c:if test="${cs.transType=='飞机场' }" >selected</c:if>>飞机场</option>
                    <option value="150200" <c:if test="${cs.transType=='火车站' }" >selected</c:if>>火车站</option>
                    <option value="150300;150301;150302;150303" <c:if test="${cs.transType=='港口码头' }" >selected</c:if>>港口码头</option>
                    <option value="150400" <c:if test="${cs.transType=='长途汽车站' }" >selected</c:if>>长途汽车站</option>
                    </select>
                </label>
				<label>
					<span>站名</span>
					<input name="cityStationName" id="cityStationName" value="${cs.cityStationName }" class="text-input"/>
                    <div id="divAutoList"></div>
				</label>
                <label>
                    <div id="divTransDetailList"></div>
                </label>
                <label>
                    <span>站名坐标</span>
                    <input name="coordinate" id="coordinate" value="${cs.coordinate }" class="text-input" />
                </label>
			</div>
            <div style="width:60%;height:300px;border: 1px solid gray;float: right;" id="iCenter"></div>
		</fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
    // 显示高德地图
    var mapObj, toolbar, overview, scale;

    var cityStationCoordinate = "${cs.coordinate}";

    if (cityStationCoordinate != null && cityStationCoordinate != "") {
        loadMap(cityStationCoordinate);
        addMarker(cityStationCoordinate);
    } else {
        loadMap("116.397428, 39.90923");
    }
    mapObj.bind(mapObj, "click", function (e) {
        var lonlat = e.lnglat.lng + "," + e.lnglat.lat;
        document.getElementById("coordinate").value = lonlat;
        addMarker(lonlat);
    });

    var highlightindex = -1; //定义高亮显示索引,-1代表不高亮任何行
    var timeOutId = null; //定义延迟时间Id
    var delayTime = 200; //默认延迟0.5秒
    var minPrefix = 0; //定义最小几个字符开始搜索
    var mouseOverCss = { background: "white", cursor: " pointer" }; //mouseover时样式
    var mouseOutCss = { background: "white" }; //mouseout时样式
    var grayCss = { background: "#cef", cursor: " pointer" };
    var upDownGrayCss = { background: "#cef" };
    var upDownWhiteCss = { background: "white" };

    var ajaxProcessUrl = getContextPath() + "/citystation/suggestlist"; //发送ajax请求调用处理url

    $(document).ready(function () {
        var wordInput = $("#cityStationName");

        $("#divAutoList").hide().css({
            "width": "250px",
            "color": "#555",
            "padding": "5px",
            "border": "1px solid #B5B8C8",
            "font - size": "14px",
            "margin - top": "5px",
            "background": "#FFFrepeat - x"
        });

        //给文本框添加键盘按下并弹起的事件
        wordInput.bind("keyup", function (event) {
            autoComplite(event)
        });
        wordInput.bind("focus", function (event) {
            autoComplite(event)
        });

        //给查询框添加blur事件，隐藏提示层
        $("#cityStationName").blur(function () {
            $("#divAutoList").hide();
        });
    });

    $('#cityName').validatebox({
        required: true
    });
    $('#cityStationName').validatebox({
        required: true
    });
    $('#coordinate').validatebox({
        required: true
    });

    changeTransDetail();

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

    function changeTransDetail() {
        var selectTransType = $("#transType").val();
        var divTransDetailList = $("#divTransDetailList"); //可供选择的项
        divTransDetailList.empty();
        divTransDetailList.append("<span > 交通工具小类 </span >");

        if(selectTransType == "150100") {

            var item1 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='飞机场' id='transdetail1' checked='true'/>飞机场</span>";
            divTransDetailList.append(item1);
        }
        if (selectTransType == "150200") {
            var item1 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='高铁' id='transdetail1' />高铁</span>";
            var item2 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='动车' id='transdetail2' />动车</span>";
            var item3 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='特快' id='transdetail3' />特快</span>";
            var item4 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='普快' id='transdetail4' />普快</span>";
            var item5 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='直达' id='transdetail5' />直达</span>";
            divTransDetailList.append(item1);
            divTransDetailList.append(item2);
            divTransDetailList.append(item3);
            divTransDetailList.append(item4);
            divTransDetailList.append(item5);
        }
        if (selectTransType == "150300;150301;150302;150303") {
            var item1 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='港口码头' id='transdetail1' checked='true' />港口码头</span>";
            divTransDetailList.append(item1);

        }
        if (selectTransType == "150400") {
            var item1 = "<span><input type='checkbox' class='checkbox' name='transdetailGroup' value='长途汽车站' id='transdetail1' checked='true'/>长途汽车站</span>";
            divTransDetailList.append(item1);

        }
    }

    function addMarker(lonlat) {
        //alert(lonlat);
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
        var isValid = $('#csForm').form('validate');
        if(!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            var transdetails = "";
            $("input[name='transdetailGroup']").each(function(){
                if(true==$(this).attr("checked")) {
                    transdetails = transdetails + $(this).attr('value') + ";";
                }
            })
            $("#transdetail").attr('value',transdetails);

            $.post("${ctx}/citystation/add", $("#csForm").serializeArray(),
                    function (data) {
                        if (data.result == '0' || data.result == '2' || data.result == '4') $.messager.alert('提示', data.alertInfo, 'info');
                    });



        }
	}

    function autoComplite(event) {

        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        var autoNode = $("#divAutoList"); //可供选择的项
        var poiType = $("#transType").val();
        var adName = $("#cityName").val();

        //        if (keyCode >= 65 && keyCode <= 90 || keyCode == 8 || keyCode == 46) { //输入字母,退格或删除,显示最新的内容
        if (keyCode != 13 && keyCode != 38 && keyCode != 40) { //不是三个特殊键，可以搜索
            var wordText = $("#cityStationName").val();
            if (wordText.length < minPrefix) return;
            //取消上次提交
            //window.clearTimeout(timeOutId);
            timeOutId = setTimeout(function () {
                $.ajax({
                    type: "GET",
                    url: ajaxProcessUrl,
                    data: 'cityStationName=' + wordText + '&poiType=' + poiType + '&adName=' + adName,
                    success: function (data) {
                        var wordNodes = $(data.result);
                        autoNode.html("");

                        for (var i = 0; i < data.result.length; i++) {
                            var newDivNode = $("<div>").attr("id", data.result[i].id);
                            newDivNode.attr("adName", data.result[i].adName);
                            newDivNode.attr("poiCoordinate", data.result[i].poiCoordinate);
                            newDivNode.attr("adCodeForSolr", data.result[i].adCodeForSolr);
                            newDivNode.html(data.result[i].poiStationName).appendTo(autoNode);
                            //添加光标进入事件, 高亮节点
                            newDivNode.mouseover(function () {
                                if (highlightindex != -1) {
                                    $("#divAutoList").children("div")
                                            .eq(highlightindex)
                                            .css(mouseOverCss);
                                }
                                highlightindex = $(this).attr("id");
                                $(this).css(grayCss);
                            });
                            //添加光标移出事件,取消高亮
                            newDivNode.mouseout(function () {
                                $(this).css(mouseOutCss);
                            });
                            //添加光标mousedown事件  点击事件newDivNode.click可能不起作用?
                            newDivNode.mousedown(function () {
                                var comText = $(this).text();
                                $("#divAutoList").hide();
                                highlightindex = -1;
                                $("#cityStationName").val(comText);
                                $("#poiStationId").val($(this).attr("id"));
                                $("#cityName").val($(this).attr("adName"));
                                $("#coordinate").val($(this).attr("poiCoordinate"));
                                $("#cityCode").val($(this).attr("adCodeForSolr"));
                                loadMap($(this).attr("poiCoordinate"));
                                addMarker($(this).attr("poiCoordinate"));
                            });
                        }
                        if (wordNodes.length > 0) {
                            autoNode.show();
                        }
                        else {
                            autoNode.hide();
                            highlightindex = -1;
                        }
                    },
                    dataType: "json"
                });
            }, delayTime);
        }
        else if (keyCode == 38) {//输入向上,选中文字高亮
            var autoNodes = $("#divAutoList").children("div")
            if (highlightindex != -1) {
                autoNodes.eq(highlightindex).css(upDownWhiteCss);
                highlightindex--;
            }
            else {
                highlightindex = autoNodes.length - 1;
            }

            if (highlightindex == -1) {
                highlightindex = autoNodes.length - 1;
            }
            autoNodes.eq(highlightindex).css(upDownGrayCss);
        }
        else if (keyCode == 40) {//输入向下,选中文字高亮
            var autoNodes = $("#divAutoList").children("div")
            if (highlightindex != -1) {
                autoNodes.eq(highlightindex).css(upDownWhiteCss);
            }
            highlightindex++;
            if (highlightindex == autoNodes.length) {
                highlightindex = 0;
            }
            autoNodes.eq(highlightindex).css(upDownGrayCss);

        }
        else if (keyCode == 13) {//输入回车
            if (highlightindex != -1) {
                var comText = $("#divAutoList").hide().children("div").eq(highlightindex).text();
                highlightindex = -1;
                $("#cityStationName").val(comText);
                return false;
            }
            else {
                alert("文本框中的[" + $("#cityStationName").val() + "]被提交了！");
                $("#divAutoList").hide();
                $("#cityStationName").get(0).blur();
                return true;
            }
        }
    }
</script>
</html>
