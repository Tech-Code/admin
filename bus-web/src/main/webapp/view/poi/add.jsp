<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=CCfe935a7c589f7ca959bae20c503de4"></script>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        .clear {
            clear: both;
        }
    </style>
</head>
<body>
<form action="" class="formular" id="poiForm">
    <input type="hidden" id="orientation" name="orientation" value="${poi.orientation }"/>
    <input type="hidden" id="poiId" name="poiId" value="${poi.poiId }"/>

    <input type="hidden" id="poiType1Temp" name="poiType1Temp" value="${poi.poiType1 }"/>
    <input type="hidden" id="poiType2Temp" name="poiType2Temp" value="${poi.poiType2 }"/>
    <input type="hidden" id="poiType3Temp" name="poiType3Temp" value="${poi.poiType3 }"/>

    <fieldset>
        <legend> 基础信息</legend>
        <div style="width:auto;height:auto;float: left">
            <label>
                <span>城市代码</span>
                <input name="cityCode" id="cityCode" value="${poi.cityCode }" class="text-input"/>
            </label>
            <label>
                <span>地标点名称</span>
                <input name="poiName" id="poiName" value="${poi.poiName }" class="text-input"/>
            </label>
            <label>
                <span>地标点大类型</span>
                <select id="poiType1" onchange="onPoiType1Changed()" name="poiType1">
                    <option value="0">===请选择===</option>
                </select>
            </label>
            <label>
                <span>地标点中类型</span>
                <select id="poiType2" onchange="onPoiType2Changed()" name="poiType2">
                    <option value="0">===请选择===</option>
                </select>
            </label>
            <label>
                <span>地标点小类型</span>
                <select id="poiType3" name="poiType3">
                    <option value="0">===请选择===</option>
                </select>
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
           onclick="add()" class="easyui-linkbutton"/>
</form>
</body>
<script type="text/javascript">

    $(function () {
        var poiType1 = $("#poiType1Temp").val();
        var poiType2 = $("#poiType2Temp").val();
        var poiType3 = $("#poiType3Temp").val();

        $.post("${ctx}/poi/getPoiType1", function (data) {
            data.poiType1List.forEach(function (value) {
                if (poiType1 == value) {
                    $("<option value='" + value + "' selected=true>" + value + "</option>").appendTo("#poiType1");
                } else {
                    $("<option value='" + value + "'>" + value + "</option>").appendTo("#poiType1");
                }
            });
        });

        if (poiType1 != "" && poiType2 != "" && poiType3 != "") {

            onPoiType1Changed(poiType1, poiType2);
            onPoiType2Changed(poiType1, poiType2, poiType3);
        }


    });


    $('#cityCode').validatebox({
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
            $.post("${ctx}/poi/add", $("#poiForm").serializeArray(),
                    function (data) {
                        if (data.result == '0' || data.result == '2' || data.result == '5' || data.result == '8')
                            $.messager.alert('提示', data.alertInfo, 'info');
                    });
        }

    }

    function onPoiType1Changed(selectPoiType1, selectedPoiType2) {

        $("#poiType2 option").each(function () {
            $(this).remove();   //移除原有项
        });

        if (typeof(selectPoiType1) == "undefined" || selectPoiType1 == "") {
            selectPoiType1 = $("#poiType1").find("option:selected").text();
        }

        $.post("${ctx}/poi/getPoiType2", {poiType1: selectPoiType1}, function (data) {
            data.poiType2List.forEach(function (value) {
                if (typeof(selectedPoiType2) != "undefined" && selectedPoiType2 == value) {
                    $("<option value='" + value + "' selected=true>" + value + "</option>").appendTo("#poiType2");
                } else {
                    $("<option value='" + value + "'>" + value + "</option>").appendTo("#poiType2");
                }
            });
        });
    }

    function onPoiType2Changed(selectPoiType1, selectPoiType2, selectPoiType3) {

        $("#poiType3 option").each(function () {
            $(this).remove();   //移除原有项
        });
        if (typeof(selectPoiType1) == "undefined" || selectPoiType1 == "") {
            selectPoiType1 = $("#poiType1").find("option:selected").text();
        }
        if (typeof(selectPoiType2) == "undefined" || selectPoiType2 == "") {
            selectPoiType2 = $("#poiType2").find("option:selected").text();
        }
        $.post("${ctx}/poi/getPoiType3", {poiType1: selectPoiType1, poiType2: selectPoiType2}, function (data) {
            data.poiType3List.forEach(function (value) {
                if (typeof(selectedPoiType3) != "undefined" && selectedPoiType3 == value) {
                    $("<option value='" + value + "' selected=true>" + value + "</option>").appendTo("#poiType3");
                } else {
                    $("<option value='" + value + "'>" + value + "</option>").appendTo("#poiType3");
                }
            });
        });
    }

</script>

</html>
