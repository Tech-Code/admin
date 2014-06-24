<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		$('#userKeyTable').datagrid({
			title : '权限配置表', //标题
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/userkey/list", //数据来源
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
				field : 'cityCode',
                title : '城市代码',
				width : 20,
                formatter : function(value, row, index) {
                    return row.cityCode;
                } //需要formatter一下才能显示正确的数据
            },{
				field : 'cityName',
                title : '城市名称',
				width : 20,
                formatter : function(value, row, index) {
                    return row.cityName;
                } //需要formatter一下才能显示正确的数据
            },{
				field : 'd_timesection',
				title : '日间时间区间',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.d_timesection;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'd_s_miles',
				title : '起步公里数',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.d_s_miles;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'd_s_cost',
				title : '起步价',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.d_s_cost;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'd_exceed_s_cost',
				title : '超出起步单价(元/公里)',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.d_exceed_s_cost;
				} //需要formatter一下才能显示正确的数据
			},{
                    field : 'd_s_exceed_d_cost',
                    title : '超出规定距离单价',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.d_s_exceed_d_cost;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'n_timesection',
                    title : '夜间时间区间',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.n_timesection;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'n_s_miles',
                    title : '夜间起步公里数',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.n_s_miles;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'n_s_cost',
                    title : '夜间起步价(元)',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.n_s_cost;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'n_exceed_s_cost',
                    title : '夜间超出起步单价',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.n_exceed_s_cost;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'n_s_exceed_d_cost',
                    title : '夜间超出规定距离单价',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.n_s_exceed_d_cost;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'd_bas',
                    title : '日间燃油税',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.d_bas;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'n_bas',
                    title : '夜间燃油税',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.n_bas;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'd_exceed_distance',
                    title : '日间规定距离',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.d_exceed_distance;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'n_exceed_distance',
                    title : '夜间规定距离',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.n_exceed_distance;
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
				$('#userKeyTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
	//新增
	function addrow() {
		parent.addTab('tabId_taxi_add','添加公交车费用','<%=root%>/view/taxi/add.jsp');

	}
	//更新
	function updaterow() {
		var rows = $('#userKeyTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要更新的公交车费用", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个公交车费用进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?id=" + n.id;
		});
		
		var url = '<%=root%>/taxi/update' + ps;

		parent.addTab('tabId_bc_update','更新公交车费用',url);
	}

 	//删除
	function deleterow() {
		$.messager.confirm('提示', '确定要删除吗?', function(result) {
			if (result) {
				var rows = $('#userKeyTable').datagrid('getSelections');
				var ps = "";
				$.each(rows, function(i, n) {
					if (i == 0)
						ps += "?id=" + n.id;
					else
						ps += "&id=" + n.id;
				});
				$.post('<%=root%>/taxi/delete' + ps, function() {
					$('#userKeyTable').datagrid('reload');
					$.messager.alert('删除', '删除已成功', 'info');
				});
			}
		});
	}
</script>
</head>

<body>
	<div style="padding: 10" id="tabdiv">
		<table id="userKeyTable"></table>
	</div>
</body>
</html>
