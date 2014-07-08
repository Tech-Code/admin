<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CCfe935a7c589f7ca959bae20c503de4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/CityList/1.2/src/CityList_min.js"></script>
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
					<input name="cityCode" id="cityCode" value="${cs.cityCode }" class="text-input" data-options="required:true"/>
				</label>
				<label>
					<span>城市名称</span>
                    <input name="cityName" id="cityName" value="${cs.cityName }" class="text-input"/>
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
					<input name="stationName" id="stationName" value="${cs.stationName }" class="text-input"/>
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
                    <input name="coordinate" id="coordinate" value="${cs.coordinate }" class="text-input" />
                </label>
			</div>
		</fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
    $('#cityCode').validatebox({
        required: true
    });
    $('#cityName').validatebox({
        required: true
    });
    $('#stationName').validatebox({
        required: true
    });
    $('#coordinate').validatebox({
        required: true
    });

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
            if(transdetails == "") {
                $.messager.alert('提示', "请选择交通工具小类", 'info');
            } else {
                $.post("${ctx}/citystation/add", $("#csForm").serializeArray(),
                        function (data) {
                            if (data.result == '0' || data.result == '2' || data.result == '4') $.messager.alert('提示', data.alertInfo, 'info');

                            <%--if (data.result == '1') $.messager.confirm('提示', '确定要覆盖吗?', function (result) {--%>
                                <%--if (result) {--%>
                                    <%--$('#id').val(data.id);--%>
                                    <%--$.post("${ctx}/citystation/add", $("#csForm").serializeArray(),--%>
                                            <%--function (data) {--%>
                                                <%--$.messager.alert('提示', data.alertInfo, 'info');--%>
                                                <%--$('#id').val('');--%>
                                            <%--});--%>
                                <%--}--%>
                            <%--});--%>

                        });

            }

        }
	}
</script>
</html>
