<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CCfe935a7c589f7ca959bae20c503de4"></script>
<script type="text/javascript" src="<%=root%>/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.clear {
	clear: both;
}
</style>
</head>
<body>
	<form action="" class="formular" id="userKeyForm">
		<input type="hidden" id="id" name="id" value="${userKey.id }" />

		<fieldset>
			<legend> 基础信息 </legend>
            <div style="width:auto;height:auto;float: left">
				<label>
					<span>业务名称</span>
					<input name="businessName" id="businessName" value="${userKey.businessName }" class="text-input"/>
				</label>
				<label>
					<span>业务子名称</span>
                    <input name="businessSubName" id="businessSubName" value="${userKey.businessSubName }" class="text-input"/>
                </label>
                <label>
					<span>业务标识</span>
                    <input name="businessFlag" id="businessFlag" value="${userKey.businessFlag }" class="text-input"/>
                </label>
                <label>
					<span>业务分类</span>
                    <input name="businessType" id="businessType" value="${userKey.businessType }" class="text-input"/>
                </label>
                <label>
					<span>使用API</span>
                    <input name="usedApi" id="usedApi" value="${userKey.usedApi }" class="text-input"/>
                </label>
                <label>
					<span>省份</span>
                    <input name="province" id="province" value="${userKey.province }" class="text-input"/>
                </label>
                <label>
					<span>状态</span>
                    <input name="status" id="status" value="${userKey.status }" class="text-input"/>
                </label>
                <label>
					<span>厂商</span>
                    <input name="firm" id="firm" value="${userKey.firm }" class="text-input"/>
                </label>
                <label>
					<span>业务地址</span>
                    <input name="businessUrl" id="businessUrl" value="${userKey.businessUrl }" class="text-input"/>
                </label>
                <label>
					<span>联系人</span>
                    <input name="contact" id="contact" value="${userKey.contact }" class="text-input"/>
                </label>
                <label>
					<span>资源</span>
                    <input name="businessResource" id="businessResource" value="${userKey.businessResource }" class="text-input"/>
                </label>
			</div>
		</fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
    $('#businessUrl').validatebox({
        required: true
    });



	function add() {
        var isValid = $('#userKeyForm').form('validate');
        if (!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            $.post("${ctx}/userkey/add", $("#userKeyForm").serializeArray(),
                    function (data) {
                        //$.messager.alert('提示', '操作成功 <br> key: ' + data.key, 'info');
                        $.messager.show({
                            title: "操作成功",
                            msg: 'key:' + data.key,
                            showType: 'show',
                            width: 500,
                            height: 90,
                            timeout: 5000
                        });
                    });
        }
	}
</script>
</html>
