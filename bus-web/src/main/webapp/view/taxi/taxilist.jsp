<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/template.css" type="text/css"/>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
        initDataGrid("${ctx}/taxi/list","{}");
        $('#cityName').combobox({
            url: "${ctx}/analysis/cityname",
            valueField: 'id',
            textField: 'text'
        });
        $('#cityName').combobox('setValue', '全部');
	});

    function initDataGrid(getUrl, queryParams) {
        $('#taxiTable').datagrid({
            title: '出租车费用配置表', //标题
            method: 'post',
            iconCls: 'icon-edit', //图标
            singleSelect: false, //多选
            height: 360, //高度
            fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
            striped: true, //奇偶行颜色不同
            collapsible: true,//可折叠
            url: getUrl, //数据来源
            sortOrder: 'desc', //倒序
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
                        field: 'cityCode',
                        title: '城市代码',
                        width: 20,
                        formatter: function (value, row, index) {
                            return row.cityCode;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'cityName',
                        title: '城市名称',
                        width: 20,
                        formatter: function (value, row, index) {
                            return row.cityName;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'd_timesection',
                        title: '日间时间区间',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.d_timesection;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'd_s_miles',
                        title: '起步公里数',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.d_s_miles;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'd_s_cost',
                        title: '起步价',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.d_s_cost;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'd_exceed_s_cost',
                        title: '超出起步单价(元/公里)',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.d_exceed_s_cost;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'd_s_exceed_d_cost',
                        title: '超出规定距离单价',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.d_s_exceed_d_cost;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'n_timesection',
                        title: '夜间时间区间',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.n_timesection;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'n_s_miles',
                        title: '夜间起步公里数',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.n_s_miles;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'n_s_cost',
                        title: '夜间起步价(元)',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.n_s_cost;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'n_exceed_s_cost',
                        title: '夜间超出起步单价',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.n_exceed_s_cost;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'n_s_exceed_d_cost',
                        title: '夜间超出规定距离单价',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.n_s_exceed_d_cost;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'd_bas',
                        title: '日间燃油税',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.d_bas;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'n_bas',
                        title: '夜间燃油税',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.n_bas;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'd_exceed_distance',
                        title: '日间规定距离',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.d_exceed_distance;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'n_exceed_distance',
                        title: '夜间规定距离',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.n_exceed_distance;
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
                $('#taxiTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
            }
        });
    }

	//新增
	function addrow() {
		parent.addTab('tabId_taxi_add','添加出租车费用','<%=root%>/view/taxi/add.jsp');

	}
	//更新
	function updaterow() {
		var rows = $('#taxiTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择需要更新的出租车费用", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个出租车费用进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?id=" + n.id;
		});
		
		var url = '<%=root%>/taxi/update' + ps;

		parent.addTab('tabId_bc_update','更新出租车费用',url);
	}

 	//删除
	function deleterow() {
		$.messager.confirm('提示', '确定要删除吗?', function(result) {
			if (result) {
				var rows = $('#taxiTable').datagrid('getSelections');
				var ps = "";
				$.each(rows, function(i, n) {
					if (i == 0)
						ps += "?id=" + n.id;
					else
						ps += "&id=" + n.id;
				});
				$.post('<%=root%>/taxi/delete' + ps, function() {
					$('#taxiTable').datagrid('reload');
					$.messager.alert('删除', '删除已成功', 'info');
				});
			}
		});
	}

    //搜索
    function select() {
        var cityCode = $('#cityCode').val();
        var cityName = $('#cityName').combobox('getText');
        if (cityName == '全部') cityName = '';
        var queryParams = {cityCode: cityCode, cityName: cityName};

        initDataGrid("${ctx}/taxi/searchlist?", queryParams);
    }
</script>
</head>

<body>
<form style="margin:20px 0 10px 0;" class="formular">
    <fieldset>
        <legend>查询条件</legend>
        城市代码:<input id="cityCode" style="width:100px;"/>
        城市名称:<input class="easyui-combobox" id="cityName"/>

        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a>
    </fieldset>
</form>
	<div style="padding: 10" id="tabdiv">
		<table id="taxiTable"></table>
	</div>
</body>
</html>
