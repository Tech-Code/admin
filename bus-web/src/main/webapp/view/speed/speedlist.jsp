<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		$('#speedTable').datagrid({
			title : '时速配置表', //标题
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/speed/list", //数据来源
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
				field : 'tranSportation',
                title : '交通工具',
				width : 20,
                formatter : function(value, row, index) {
                    return row.tranSportation;
                } //需要formatter一下才能显示正确的数据
            },
			{
				field : 'tranSportDes',
				title : '交通工具明细',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.tranSportDes;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'speed',
				title : '时速',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.speed;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'cityCode',
				title : '城市代码',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.cityCode;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'cityName',
				title : '城市名',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.cityName;
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
				$('#speedTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
	//新增
	function addrow() {
		parent.addTab('tabId_speed_add','添加时速配置信息','<%=root%>/view/speed/add.jsp');

	}
	//更新
	function updaterow() {
		var rows = $('#speedTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择需要更新的时速配置信息", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个时速配置信息进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?id=" + n.id;
		});
		
		var url = '<%=root%>/speed/update' + ps;

		parent.addTab('tabId_speed_update','更新时速配置信息',url);
	}

 	//删除
	function deleterow() {
		$.messager.confirm('提示', '确定要删除吗?', function(result) {
			if (result) {
				var rows = $('#speedTable').datagrid('getSelections');
				var ps = "";
				$.each(rows, function(i, n) {
					if (i == 0)
						ps += "?id=" + n.id;
					else
						ps += "&id=" + n.id;
				});
				$.post('<%=root%>/speed/delete' + ps, function() {
					$('#speedTable').datagrid('reload');
					$.messager.alert('删除', '删除已成功', 'info');
				});
			}
		});
	}
</script>
</head>

<body>
	<div style="padding: 10" id="tabdiv">
		<table id="speedTable"></table>
	</div>
</body>
</html>
