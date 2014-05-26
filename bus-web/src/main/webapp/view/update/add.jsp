<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form action="" class="formular" id="adForm">
		<input type="hidden" name="id" value="${model.id }" />
		<fieldset>
			<legend> 基础信息 </legend>
			<div>
				<span>版本号</span> 
				<input name="ver" value="${model.ver }" style="width: 200px"/>
				<span>下载地址</span> 
				<input name="url" value="${model.url }" style="width: 200px"/>
				<span>更新内容</span>
				<textarea rows="5" name="memo" cols="20">${model.memo }</textarea>
			</div>
		</fieldset>
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
	function add() {
		$.post("${ctx}/update/add", $("#adForm").serializeArray(),
			function(data) {
				$.messager.alert('提示', "操作成功", 'info');
				$('#MyPopWindow').window('close');
				$('#userTable').datagrid('reload');
			});
	}
</script>
</html>
