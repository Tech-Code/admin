<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/common.css" type="text/css" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	<form action="" class="formular" id="adForm">
		<input type="hidden" name="id" value="${model.id }" />
		<fieldset>
			<legend> 基础信息 </legend>
			<div>
				<label>
					<span>公告标题</span> 
					<input name="title" value="${model.title }" class="text-input"/>
				</label>
				<label>
					<span>公告类型</span> 
					<select name="type" class="text-input">
							<option value="0" <c:if test="${model.type==0 }" >selected</c:if>>出行提示</option>
							<option value="1" <c:if test="${model.type==1 }" >selected</c:if>>优惠信息</option>
					</select>
				</label>
				<label>
					<span>链接地址</span>
					<input name="url" value="${model.url }" class="text-input" />
				</label>
			</div>
		</fieldset>
		<fieldset>
			<legend> 详细内容 </legend>
			<div>
				<script id="editor" name="content" type="text/plain"
					style="height: 200px;"></script>
			</div>
		</fieldset>
		
		<input id="save" type="button" value="保存"
					onclick="add()" class="easyui-linkbutton" />
	</form>
</body>
<script type="text/javascript">
	var editor = UE.getEditor('editor');
	
	function add() {
		$.post("${ctx}/notice/add", $("#adForm").serializeArray(),
			function(data) {
				$.messager.alert('提示', "操作成功", 'info');
				$('#MyPopWindow').window('close');
				$('#userTable').datagrid('reload');
			});
	}
	
	setTimeout(function(){
		editor.setContent('${model.content }', false)
	}, 1000);
	
</script>
</html>
