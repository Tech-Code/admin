<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/template.css" type="text/css"/>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
        initDataGrid("${ctx}/userkey/list","{}");
        $('#selectBusinessType').combobox({
            url: "${ctx}/userkey/businesstypes?notAll=0",
            valueField: 'id',
            textField: 'text'
        });
        $('#selectBusinessType').combobox('setValue', '全部');
	});

    function initDataGrid(getUrl, queryParams) {
        $('#userKeyTable').datagrid({
            title: '权限配置表', //标题
            method: 'post',
            iconCls: 'icon-edit', //图标
            singleSelect: false, //多选
            height: 360, //高度
            fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
            striped: true, //奇偶行颜色不同
            collapsible: true,//可折叠
            url: getUrl, //数据来源
            sortOrder: 'asc', //倒序
            idField: 'id', //主键字段
            remoteSort: true, //服务器端排序
            pagination: true, //显示分页
            rownumbers: true, //显示行号
            queryParams: queryParams,
            pageNumber: 1,
            columns: [
                [
                    {
                        field: 'ck',
                        checkbox: true,
                        width: 2
                    }, //显示复选框
                    {
                        field: 'createDate',
                        title: '时间',
                        width: 20,
                        formatter: function (value, row, index) {
                            return row.createDate;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'businessName',
                        title: '业务名称',
                        width: 20,
                        formatter: function (value, row, index) {
                            return row.businessName;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'businessSubName',
                        title: '业务子名称',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.businessSubName;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'businessFlag',
                        title: '业务标识',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.businessFlag;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'businessType',
                        title: '业务分类',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.businessType;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'usedApi',
                        title: '使用API',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.usedApi;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'province',
                        title: '省份',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.province;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.status;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'firm',
                        title: '厂商',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.firm;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'businessUrl',
                        title: '业务地址',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.businessUrl;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'key',
                        title: 'key',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.key;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'contact',
                        title: '联系人',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.contact;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'businessResource',
                        title: '资源',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.businessResource;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'source',
                        title: '来源',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) return "原始";
                            if (value == 1) return "新增";
                            //return row.source;
                        } //需要formatter一下才能显示正确的数据
                    }
                ]
            ],
            toolbar: [
                {
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        addrow();
                    }
                },
                '-',
                {
                    text: '修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        updaterow();
                    }
                },
                '-',
                {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        deleterow();
                    }
                }
            ],
            onLoadSuccess: function () {
                $('#userKeyTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
            },
            onClickRow: function (rowIndex, rowData) {
                $.messager.show({
                    width: 500,
                    height: 35,
                    msg: rowData.key,
                    timeout: 30000
                })
            }
        });
    }

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
		
		var url = '<%=root%>/userkey/update' + ps;

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
				$.post('<%=root%>/userkey/delete' + ps, function(data) {
					$('#userKeyTable').datagrid('reload');
					$.messager.alert('删除', data.mes, 'info');
				});
			}
		});
	}

    //搜索
    function select() {
        var businessName = $('#businessName').val();
        var businessFlag = $('#businessFlag').val();
        var selectBusinessType = $('#selectBusinessType').combobox('getText');
        var province = $('#province').val();
        var businessUrl = $('#businessUrl').val();
        var key = $('#key').val();

        if (selectBusinessType == '全部') selectBusinessType = '';
        var queryParams = {businessName: businessName, businessFlag: businessFlag, selectBusinessType: selectBusinessType, province: province, businessUrl: businessUrl, key: key};

        initDataGrid("${ctx}/userkey/searchlist?", queryParams);
    }

    // 导出key
    function exportUserKey() {
        var businessName = $('#businessName').val();
        var businessFlag = $('#businessFlag').val();
        var selectBusinessType = $('#selectBusinessType').combobox('getText');
        var province = $('#province').val();
        var businessUrl = $('#businessUrl').val();
        var key = $('#key').val();

        if (selectBusinessType == '全部') selectBusinessType = '';
        var url = "${ctx}/userkey/downloaddl?businessName=" + businessName + "&businessFlag=" + businessFlag + "&selectBusinessType=" + selectBusinessType + "&province=" + province + "&businessUrl=" + businessUrl + "&key=" + key;
        window.location.href = url;
    }
</script>
</head>

<body>
<form style="margin:20px 0 10px 0;" class="formular">
    <fieldset>
        <legend>查询条件</legend>
        业务名称:<input id="businessName" style="width:100px;"/>
        业务标识:<input id="businessFlag" style="width:100px;"/>
        业务分类:<input class="easyui-combobox" id="selectBusinessType" style="width:70px;"/>
        省份:<input id="province" style="width:100px;"/>
        业务地址:<input id="businessUrl" style="width:100px;"/>
        KEY:<input id="key" style="width:100px;"/>

        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a>
        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="exportUserKey()">导出EXCEL</a>
    </fieldset>
</form>
	<div style="padding: 10" id="tabdiv">
		<table id="userKeyTable"></table>
	</div>
</body>
</html>
