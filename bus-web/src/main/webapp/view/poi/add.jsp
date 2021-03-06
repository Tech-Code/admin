<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">
        .clear {
            clear: both;
        }
    </style>
    <script type="text/javascript">
        jQuery(function ($) {
            $('#cityName').combobox({
                url: "${ctx}/analysis/cityname?notAll=1",
                valueField: 'text',
                textField: 'text',
                required:true,
                onSelect:function(rec) {
                    $('#cityCode').attr("value", rec.id);
                    $('#cityName').combobox("setValue", rec.text);
                }
            });

        });
    </script>
</head>
<body>
<form action="" class="formular" id="poiForm">
    <input type="hidden" id="poiId" name="poiId" value="${poi.poiId }"/>

    <input type="hidden" id="poiType1Temp" name="poiType1Temp" value="${poi.poiType1 }"/>
    <input type="hidden" id="poiType2Temp" name="poiType2Temp" value="${poi.poiType2 }"/>
    <input type="hidden" id="poiType3Temp" name="poiType3Temp" value="${poi.poiType3 }"/>
    <input type="hidden" id="cityCode" name="cityCode" value="${poi.cityCode }"/>
    <input type="hidden" id="gridId" name="gridId" value="${poi.gridId }"/>

    <fieldset>
        <legend> 基础信息</legend>
        <div style="width:auto;height:auto;float: left">
            <label>
                <span>城市名称</span>
                <div style="margin-top: 5px">
                    <input class="easyui-combobox" id="cityName" name="cityName"
                           value="${poi.cityName }"
                           style="width:140px"/>
                </div>
            </label>
            <label>
                <span>地标点名称</span>
                <input name="poiName" id="poiName" value="${poi.poiName }" class="text-input"/>
            </label>
            <label>
                <span>地标点大类型</span>
                <select id="poiType1" onchange="onPoiType1Changed()" name="poiType1" style="width: 150px">
                    <option value="0">===请选择===</option>
                </select>
            </label>
            <label>
                <span>地标点中类型</span>
                <select id="poiType2" onchange="onPoiType2Changed()" name="poiType2" style="width: 150px">
                    <option value="0">===请选择===</option>
                </select>
            </label>
            <label>
                <span>地标点小类型</span>
                <select id="poiType3" name="poiType3" style="width: 150px">
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
    <a href="javascript:add()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
</form>
</body>
<script type="text/javascript">
    $.extend($.fn.validatebox.defaults.rules, {
        intOrFloat: {// 验证整数或小数
            validator: function (value) {
                return /^\d+(\.\d+),\d+(\.\d+)?$/i.test(value);
            },
            message: '请输入正确的经纬度'
        }
    });

    $(function () {
        var poiType1 = $("#poiType1Temp").val();
        var poiType2 = $("#poiType2Temp").val();
        var poiType3 = $("#poiType3Temp").val();

        $.post("${ctx}/poi/getPoiType1", function (data) {
            for(var i = 0; i<data.poiType1List.length;i++) {
                var value = data.poiType1List[i];
                if (poiType1 == value) {
                    $("<option value='" + value + "' selected=true>" + value + "</option>").appendTo("#poiType1");
                } else {
                    $("<option value='" + value + "'>" + value + "</option>").appendTo("#poiType1");
                }
            }
        });

        if (poiType1 != "" && poiType2 != "" && poiType3 != "") {

            onPoiType1Changed(poiType1, poiType2);
            onPoiType2Changed(poiType1, poiType2, poiType3);
        }


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
        required: true,
        validType: 'intOrFloat'
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

            var local_selectedPoiType2;
            for (var i = 0; i < data.poiType2List.length; i++) {
                var value = data.poiType2List[i];
                if(i == 0) local_selectedPoiType2 = value;
                if (typeof(selectedPoiType2) != "undefined" && selectedPoiType2 == value) {
                    $("<option value='" + value + "' selected=true>" + value + "</option>").appendTo("#poiType2");
                    local_selectedPoiType2 = value;
                } else {
                    $("<option value='" + value + "'>" + value + "</option>").appendTo("#poiType2");
                }
            }

            $("#poiType3 option").each(function () {
                $(this).remove();   //移除原有项
            });
            $.post("${ctx}/poi/getPoiType3", {poiType1: selectPoiType1, poiType2: local_selectedPoiType2}, function (data) {

                for (var i = 0; i < data.poiType3List.length; i++) {
                    var value = data.poiType3List[i];
                    if (typeof(selectedPoiType3) != "undefined" && selectedPoiType3 == value) {
                        $("<option value='" + value + "' selected=true>" + value + "</option>").appendTo("#poiType3");
                    } else {
                        $("<option value='" + value + "'>" + value + "</option>").appendTo("#poiType3");
                    }
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

            for (var i = 0; i < data.poiType3List.length; i++) {
                var value = data.poiType3List[i];
                if (typeof(selectedPoiType3) != "undefined" && selectedPoiType3 == value) {
                    $("<option value='" + value + "' selected=true>" + value + "</option>").appendTo("#poiType3");
                } else {
                    $("<option value='" + value + "'>" + value + "</option>").appendTo("#poiType3");
                }
            }
        });
    }

</script>

</html>
