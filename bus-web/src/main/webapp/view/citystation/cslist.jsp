<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		$('#csTable').datagrid({
			title : '城市站点列表', //标题
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/citystation/list", //数据来源
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
            },
			{
				field : 'cityName',
				title : '城市名称',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.cityName;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'transType',
				title : '交通工具类型',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.transType;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'stationName',
				title : '站名',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.stationName;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'transdetail',
				title : '交通工具小类',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.transdetail;
				} //需要formatter一下才能显示正确的数据
			},{
                    field : 'coordinate',
                    title : '站点坐标',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.coordinate;
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
				$('#csTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
	//新增
	function addrow() {
		parent.addTab('tabId_cs_add','添加城际站点','<%=root%>/view/citystation/add.jsp');

	}
	//更新
	function updaterow() {
		var rows = $('#csTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要更新的城市站点", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个城市站点进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?id=" + n.id;
		});
		
		var url = '<%=root%>/citystation/update' + ps;

//        $('#dd').dialog({
//            title: 'My Dialog',
//            width: 400,
//            height: 200,
//            close: false,
//            cache: false,
//            href: url,
//            modal: true
//        });
        //$('#dd').dialog('refresh', 'new_content.php');

		parent.addTab('tabId_cs_update','更新城市站点信息',url);
	}

 	//删除
	function deleterow() {
		$.messager.confirm('提示', '确定要删除吗?', function(result) {
			if (result) {
				var rows = $('#csTable').datagrid('getSelections');
				var ps = "";
				$.each(rows, function(i, n) {
					if (i == 0)
						ps += "?id=" + n.id;
					else
						ps += "&id=" + n.id;
				});
				$.post('<%=root%>/citystation/delete' + ps, function(data) {
					$('#csTable').datagrid('reload');
					$.messager.alert('删除', data.mes, 'info');
				});
			}
		});
	}
</script>
</head>

<body>
	<div style="padding: 10" id="tabdiv">
		<table id="csTable"></table>
	</div>
</body>
</html>
