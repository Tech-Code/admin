<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css"/>
</head>

<body>
	<form class="formular" method="post">
		<fieldset>
			<legend>
				反馈信息
			</legend>
			<label>
				<span>唯一标识</span>
				<input class="text-input" type="text" disabled="disabled" value="${model.imei }" />
			</label>
			<label>
				<span>反馈时间</span>
				<input class="text-input" type="text" disabled="disabled" value="${model.timeStr }" />
			</label>
			<label>
				<span>用户内容</span>
				<textarea rows="5" cols="20" class="class_req text-input" disabled="disabled">${model.content }</textarea>
			</label>
		</fieldset>
		</br>
		<a href="#" id="btn-back" onclick="javascript:parent.closeTab();" class="easyui-linkbutton" iconCls="icon-back">返回</a>
	</form>
</body>
</html>
