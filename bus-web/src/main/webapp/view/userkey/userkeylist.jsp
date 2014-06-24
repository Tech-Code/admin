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
				field : 'createDate',
                title : '时间',
				width : 20,
                formatter : function(value, row, index) {
                    return row.createDate;
                } //需要formatter一下才能显示正确的数据
            },{
				field : 'businessName',
                title : '业务名称',
				width : 20,
                formatter : function(value, row, index) {
                    return row.businessName;
                } //需要formatter一下才能显示正确的数据
            },{
				field : 'businessSubName',
				title : '业务子名称',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.businessSubName;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'businessFlag',
				title : '业务标识',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.businessFlag;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'businessType',
				title : '业务分类',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.businessType;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'usedApi',
				title : '使用API',
				width : 20,
				sortable : true,
				formatter : function(value, row, index) {
					return row.usedApi;
				} //需要formatter一下才能显示正确的数据
			},{
                    field : 'province',
                    title : '省份',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.province;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'status',
                    title : '状态',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.status;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'firm',
                    title : '厂商',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.firm;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'businessUrl',
                    title : '业务地址',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.businessUrl;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'key',
                    title : 'key',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.key;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'contact',
                    title : '联系人',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.contact;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'businessResource',
                    title : '资源',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {
                        return row.businessResource;
                    } //需要formatter一下才能显示正确的数据
            },{
                    field : 'source',
                    title : '来源',
                    width : 20,
                    sortable : true,
                    formatter : function(value, row, index) {

                        return row.source;
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
		parent.addTab('tabId_key_add','添加用户key','<%=root%>/view/userkey/add.jsp');

	}
	//更新
	function updaterow() {
		var rows = $('#userKeyTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要更新的用户key", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个用户key进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?id=" + n.id;
		});
		
		var url = '<%=root%>/taxi/update' + ps;

		parent.addTab('tabId_key_update','更新用户key',url);
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
				$.post('<%=root%>/usekey/delete' + ps, function() {
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
