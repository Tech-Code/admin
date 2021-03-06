<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/template.css" type="text/css"/>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
        initDataGrid("${ctx}/citystation/list","{}");
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


    function initDataGrid(getUrl,queryParams) {
        $('#csTable').datagrid({
            title: '城市交通站点列表', //标题
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
            pageNumber: 1,
            queryParams: queryParams,// 查询参数
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
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.cityName;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'transType',
                        title: '交通工具类型',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value == '飞机场') return '飞机';
                            if (value == '火车站') return '火车';
                            if (value == '长途汽车站') return '长途客车';
                            if (value == '港口码头') return '轮渡';
                            return row.transType;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'transdetail',
                        title: '交通工具小类',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null) {
                                var result = value.replace('飞机场','飞机').
                                                   replace('火车站','火车').
                                                   replace('长途汽车站','长途客车').
                                                   replace('港口码头','轮渡');
                            }
                            return result;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'cityStationName',
                        title: '站名',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.cityStationName;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'coordinate',
                        title: '站点坐标',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.coordinate;
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
                $('#csTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
            }
        });
    }
	//新增
	function addrow() {
		parent.addTab('tabId_cs_add','添加城市交通站点','<%=root%>/view/citystation/add.jsp');
	}
	//更新
	function updaterow() {
		var rows = $('#csTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择需要更新的城市交通站点", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个城市交通站点进行更新", 'info');
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

		parent.addTab('tabId_cs_update','更新城市交通站点信息',url);
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

    //搜索
    function select() {
        var cityCode = $('#cityCode').val();
        var cityName = $('#cityName').combobox('getText');
        var selectTransType = $('#selectTransType').combobox('getText');
        var stationName = $('#stationName').val();

        if(selectTransType == '全部') selectTransType = '';
        if (cityName == '全部') cityName = '';
        var queryParams = {cityCode: cityCode, cityName: cityName, selectTransType: selectTransType, stationName: stationName};

        initDataGrid("${ctx}/citystation/searchlist?", queryParams);
    }
</script>
</head>

<body>
    <form style="margin:20px 0 10px 0;" class="formular">
        <fieldset>
            <legend>查询条件</legend>
            城市代码:<input id="cityCode" style="width:100px;"/>
            城市名称:<input class="easyui-combobox" id="cityName"/>
            交通工具类型:<input class="easyui-combobox" id="selectTransType"/>
            站点名称:<input id="stationName" style="width:100px;"/>

            <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a>
        </fieldset>
    </form>
	<div style="padding: 10" id="tabdiv">
		<table id="csTable"></table>
	</div>
</body>
</html>
