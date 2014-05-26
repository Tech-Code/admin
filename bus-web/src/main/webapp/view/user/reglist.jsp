<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		$('#userTable').datagrid({
			title : '用户列表', //标题
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/user/reg/list", //数据来源
			sortOrder : 'desc', //倒序
			idField:'id', //主键字段
			remoteSort : true, //服务器端排序
			pagination : true, //显示分页
			rownumbers : true, //显示行号
			columns : [ [ {
				field : 'ck',
				checkbox : true,
				width : 2
			}, //显示复选框
			{
				field : 'phone',
				title : '手机号码',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.phone;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'imei',
				title : 'IMEI码',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.imei;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'timeStr',
				title : '注册时间',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.timeStr;
				} //需要formatter一下才能显示正确的数据
			} ] ],
			onLoadSuccess : function() {
				$('#userTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
</script>
</head>

<body>
	<div style="padding: 10" id="tabdiv">
		<table id="userTable"></table>
	</div>
</body>
</html>
