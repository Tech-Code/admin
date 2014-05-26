<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		$('#userTable').datagrid({
			title : '更新数据列表', //标题
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/update/list", //数据来源
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
				field : 'memo',
				title : '版本描述',
				width : 30,
				sortable : true,
				formatter : function(value, row, index) {
					return row.memo;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'ver',
				title : '版本号',
				width : 15,
				sortable : true,
				formatter : function(value, row, index) {
					return row.ver;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'timeStr',
				title : '发布时间',
				width : 15,
				sortable : true,
				formatter : function(value, row, index) {
					return row.timeStr;
				} //需要formatter一下才能显示正确的数据
			}] ],
			toolbar : [ {
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					addrow();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					updaterow();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					deleterow();
				}
			}],
			onLoadSuccess : function() {
				$('#userTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
	//新增
	function addrow() {
		parent.addTab('tabId_user_add','更新信息维护','<%=root%>/view/update/add.jsp');
	}
	//更新
	function updaterow() {
		var rows = $('#userTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要更新的对象", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一位对象进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?id=" + n.id;
		});
		var url = '<%=root%>/update/info' + ps;
		parent.addTab('tabId_update_update','更新信息维护',url);
	}

	//删除
	function deleterow() {
		$.messager.confirm('提示', '确定要删除吗?', function(result) {
			if (result) {
				var rows = $('#userTable').datagrid('getSelections');
				var ps = "";
				$.each(rows, function(i, n) {
					if (i == 0)
						ps += "?id=" + n.id;
					else
						ps += "&id=" + n.id;
				});
				$.post('<%=root%>/update/delete' + ps, function(data) {
					$('#userTable').datagrid('reload');
					$.messager.alert('提示', data.mes, 'info');
				});
			}
		});
	}
</script>
</head>

<body>
	<div style="padding: 10" id="tabdiv">
		<table id="userTable"></table>
	</div>
</body>
</html>
