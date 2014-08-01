<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
</head>

<body>
	<form class="formular" id="userForm" method="post">
		<input type="hidden" name="id" value="${user.id }" />
        <input type="hidden" name="role" value="${user.role }"/>
		<fieldset>
			<legend>
				用户信息
			</legend>
			<label>
				<span>登录名称</span>
				<input class="text-input" type="text" id = "loginName" name="loginName" value="${user.loginName }"/>
			</label>
			<label>
				<span>密&nbsp;&nbsp;&nbsp;&nbsp;码</span>
				<input class="text-input" type="text" name="password" id="password" value="${user.password }" />
			</label>
			<label>
				<span>用户名称</span>
				<input class="text-input" type="text" name="userName" id="userName" value="${user.userName }"  />
			</label>
            <label>
                <span>用户角色</span>
                <select name="role" class="text-input" id="role" <c:if test="${updatetype=='2' }">disabled</c:if>>
                    <option value="1" <c:if test="${user.role=='1' }">selected</c:if>>普通用户</option>
                    <option value="0" <c:if test="${user.role=='0' }">selected</c:if>>管理员</option>
                </select>
            </label>
			<label>
				<span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱</span>
				<input class="text-input" type="text" name="email" id="email" value="${user.email }"/>
			</label>
		</fieldset>
		</br>
		<a href="javascript:add()" id="btn-add" class="easyui-linkbutton" iconCls="icon-save">保存</a>
	</form>
</body>
<script type="text/javascript">
    $('#loginName').validatebox({
        required: true
    });

    $('#password').validatebox({
        required: true
    });

    $('#userName').validatebox({
        required: true
    });
	function add() {
        var isValid = $('#userForm').form('validate');
        if (!isValid) {
            $.messager.alert('提示', "信息不完整，请补全！", 'info');
        } else {
            var role = $("#role").val();
            $.post("${ctx}/user/add", $("#userForm").serializeArray(),
                    function (data) {
                        if (data.result == '0' || data.result == '9' || data.result == '10' || data.result == '11') $.messager.alert('提示', data.alertInfo, 'info');
            });
        }
	}
</script>
</html>
