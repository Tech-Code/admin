<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
</head>

<body>
	<form class="formular" id="userForm" method="post">
		<input type="hidden" name="id" value="${user.id }" />
		<fieldset>
			<legend>
				用户信息
			</legend>
			<label>
				<span>登录名称</span>
				<input class="text-input" type="text" name="loginname" value="${user.loginname }" />
			</label>
			<label>
				<span>密&nbsp;&nbsp;&nbsp;&nbsp;码</span>
				<input class="text-input" type="text" name="password" value="${user.password }" />
			</label>
			<label>
				<span>用户名称</span>
				<input class="class_req text-input" type="text" name="username" value="${user.username }"  />
			</label>
			<label>
				<span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱</span>
				<input class="class_req text-input" type="text" name="email" value="${user.email }"/>
			</label>
		</fieldset>
		</br>
		<a href="#" id="btn-add" onclick="addOrUpdateUser();" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		<a href="#" id="btn-back" onclick="javascript:parent.closeTab();" class="easyui-linkbutton" iconCls="icon-back">返回</a>
	</form>
</body>
<script type="text/javascript">
	function addOrUpdateUser() {
		$.post("${ctx}/user/addOrUpdate", $("#userForm").serializeArray(), function(
				data) {
			$.messager.alert('提示', data.mes, 'info');
			$('#MyPopWindow').window('close');
			$('#userTable').datagrid('reload');
		});
	}
</script>
</html>
