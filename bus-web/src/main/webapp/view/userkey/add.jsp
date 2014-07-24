<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
.clear {
	clear: both;
}
</style>
<script type="text/javascript">
    jQuery(function ($) {
        $('#businessType').combobox({
            url: "${ctx}/userkey/businesstypes?notAll=1",
            valueField: 'id',
            textField: 'text'
        });
    });
</script>
</head>
<body>
	<form action="" class="formular" id="userKeyForm">
		<input type="hidden" id="id" name="id" value="${userKey.id }" />

        <c:if test="${userKey.source!=null}">
            <input type="hidden" id="source" name="source" value="${userKey.source}"/>
        </c:if>


        <input type="hidden" id="createDate" name="createDate" value="${userKey.createDate }"/>

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
                    <div style="margin-top: 5px">
                        <input class="easyui-combobox" id="businessType" name="businessType" value="${userKey.businessType }"
                           style="width:140px"/>
                    </div>
                </label>
                <label>
                    <span>key</span>
                    <div style="margin-top: 5px">
                        <input class="text-input" id="key" name="key" value="${userKey.key }" readonly style="width:700px"/>
                    </div>
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
		<a href="javascript:add()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
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
                        $.messager.alert('提示', '操作成功', 'info');
                        $('#key').attr('value',data.key);
                    });

        }
	}
</script>
</html>
