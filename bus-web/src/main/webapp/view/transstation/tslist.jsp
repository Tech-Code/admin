<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/template.css" type="text/css"/>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
        initDataGrid("${ctx}/transstation/list","{}");
        $('#selectTransType').combobox({
            url: "${ctx}/citystation/transtypes",
            valueField: 'id',
            textField: 'text'
        });
        $('#cityName').combobox({
            url: "${ctx}/analysis/cityname?notAll=0",
            valueField: 'text',
            textField: 'text'
        });
        $('#cityName').combobox('setValue', '全部');
        $('#selectTransType').combobox('setValue', '全部');
	});
    function initDataGrid(getUrl, queryParams) {
        $('#tsTable').datagrid({
            title: '城际交通站点表', //标题
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
                        field: 'transType',
                        title: '交通工具类型',
                        width: 20,
                        formatter: function (value, row, index) {
                            return row.cityStation.transType;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'trips',
                        title: '班次',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.trips;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'cityCode',
                        title: '城市代码',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.cityStation.cityCode;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'station',
                        title: '站点',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.cityStation.cityStationName;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'stationOrder',
                        title: '站序',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.stationOrder;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'departType',
                        title: '发车方式',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(row.departType == 0) return "固定发车";
                            if (row.departType == 1) return "循环发车";
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'firstDepartTime',
                        title: '首班车时间',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.firstDepartTime;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'lastDepartTime',
                        title: '末班车时间',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.lastDepartTime;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'departInterval',
                        title: '发车间隔',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.departInterval;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'coordinate',
                        title: '站点坐标',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.cityStation.coordinate;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'arriveTime',
                        title: '到达时间',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            var hour = parseInt(row.arriveTime / 60);
                            var minute = row.arriveTime % 60;

                            return hour + ":" + ((minute < 10)?('0'+minute):minute);
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'departTime',
                        title: '发车时间',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            var hour = parseInt(row.departTime / 60);
                            var minute = row.departTime % 60;
                            return hour + ":" + ((minute < 10) ? ('0' + minute) : minute);
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'dayCount',
                        title: '天数',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.dayCount + '天';
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'miles',
                        title: '里程',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.miles;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'price',
                        title: '票价',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.price;
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
                $('#tsTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
            }
        });
    }

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

    //搜索
    function select() {
        var cityCode = $('#cityCode').val();
        var cityName = $('#cityName').combobox('getText');
        var selectTransType = $('#selectTransType').combobox('getText');
        var stationName = $('#stationName').val();
        var trips = $('#trips').val();

        if (selectTransType == '全部') selectTransType = '';
        if (cityName == '全部') cityName = '';
        var queryParams = {cityCode: cityCode, cityName: cityName, selectTransType: selectTransType, stationName: stationName, trips: trips};

        initDataGrid("${ctx}/transstation/searchlist?", queryParams);
    }
</script>
</head>

<body>
    <form style="margin:20px 0 10px 0;" class="formular">
        <fieldset>
            <legend>查询条件</legend>
            城市代码:<input id="cityCode" style="width:100px;"/>
            城市名称:<input class="easyui-combobox" id="cityName"/>
            交通工具类型:<input class="easyui-combobox" id="selectTransType" style="width:70px;"/>
            班次:<input id="trips" style="width:100px;"/>
            站点名称:<input id="stationName" style="width:100px;"/>

            <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a>
        </fieldset>
    </form>
	<div style="padding: 10" id="tabdiv">
		<table id="tsTable"></table>
	</div>
</body>
</html>
