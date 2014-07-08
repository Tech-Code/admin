<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		$('#tsTable').datagrid({
			title : '城际交通站点表', //标题
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/transstation/list", //数据来源
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
				field : 'transType',
                title : '交通工具类型',
				width : 20,
                formatter : function(value, row, index) {
                    return row.cityStation.transType;
                } //需要formatter一下才能显示正确的数据
            },
			{
				field : 'trips',
				title : '班次',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.trips;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'transDetail',
				title : '交通工具小类',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.cityStation.transdetail;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'cityCode',
				title : '城市代码',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.cityStation.cityCode;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'station',
				title : '站点',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.cityStation.stationName;
				} //需要formatter一下才能显示正确的数据
			},{
                    field : 'stationOrder',
                    title : '站序',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.stationOrder;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'coordinate',
                    title : '站点坐标',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.cityStation.coordinate;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'arriveTime',
                    title : '到达时间',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.arriveTime;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'departTime',
                    title : '发车时间',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.departTime;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'miles',
                    title : '里程',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.miles;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'price',
                    title : '票价',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.price;
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
				$('#tsTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
	//新增
	function addrow() {
		parent.addTab('tabId_ts_add','添加城际交通站点','<%=root%>/view/transstation/add.jsp');

	}
	//更新
	function updaterow() {
		var rows = $('#tsTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择需要更新的城际交通站点", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个城际交通站点进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?id=" + n.id;
		});
		
		var url = '<%=root%>/transstation/update' + ps;

		parent.addTab('tabId_ts_update','更新城际交通站点信息',url);
	}

 	//删除
	function deleterow() {
		$.messager.confirm('提示', '确定要删除吗?', function(result) {
			if (result) {
				var rows = $('#tsTable').datagrid('getSelections');
				var ps = "";
				$.each(rows, function(i, n) {
					if (i == 0)
						ps += "?id=" + n.id;
					else
						ps += "&id=" + n.id;
				});
				$.post('<%=root%>/transstation/delete' + ps, function(data) {
					$('#tsTable').datagrid('reload');
					$.messager.alert('删除', data.mes, 'info');
				});
			}
		});
	}
</script>
</head>

<body>
	<div style="padding: 10" id="tabdiv">
		<table id="tsTable"></table>
	</div>
</body>
</html>
