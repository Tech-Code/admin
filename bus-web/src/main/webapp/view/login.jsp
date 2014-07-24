<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎您使用本系统</title>
</link>
<script type="text/javascript">
	$(function() {
		setDialog();
		// 进入页面，焦点在用户名文本框上
		$("#loginCode").focus();
	});

	/** --------------操作弹出框------------------* */
	// 设置弹出框的属性
	function setDialog() {
		$('#login').dialog({
			title : '用户登录',
			modal : true, // 模式窗口：窗口背景不可操作
			collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
			resizable : true, // 可拖动边框大小
			closable : false
		// 不提供关闭窗口按钮
		});
	}
	
	function submitForm(){
		$("#login").submit();
	}
	function clearForm(){
		$('#login').form('clear');
	}
</script>
</head>

<body>
	<div id="login" style="padding: 5px; width: 400px; height: 180px;">
		<form action="<%=root%>/user/login" method="post">
			<table id="loginTable" style="width: 100%; padding: 24px;">
				<tr>
					<td align="right"><b style="font: 12px">用户名：</b></td>
					<td><input type="text" class="easyui-validatebox"
						name="userName" id="userName" style="width: 200px;" maxlength="20" />
					</td>
				</tr>
				<tr>
					<td align="right"><b style="font: 12px">密&nbsp;&nbsp;码：</b></td>
					<td><input type="password" class="easyui-validatebox"
						name="password" id="password" style="width: 200px;" maxlength="20" /></td>
				</tr>
				<tr>
					<td></td>
					<td id="login_msg" style="color: red; font: 12px">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="登录"> 
						<input type="reset" value="重置">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
