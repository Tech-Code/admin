<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<link rel="stylesheet" href="<%=root%>/css/template.css" type="text/css"/>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
        var queryParams = {cityName: ''};
        initDataGrid("${ctx}/poi/list", queryParams);
        $('#cityName').combobox({
            url: "${ctx}/analysis/cityname?notAll=0",
            valueField: 'text',
            textField: 'text'
        });
        $('#cityName').combobox('setValue', '全部');
	});
    function initDataGrid(getUrl, queryParams) {
        $('#poiTable').datagrid({
            title: '地标点表', //标题
            method: 'post',
            iconCls: 'icon-edit', //图标
            singleSelect: false, //多选
            height: 360, //高度
            fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
            striped: true, //奇偶行颜色不同
            collapsible: true,//可折叠
            url: getUrl, //数据来源
            sortOrder: 'desc', //倒序
            idField: 'poiId', //主键字段
            remoteSort: true, //服务器端排序
            pagination: true, //显示分页
            rownumbers: true, //显示行号
            queryParams:queryParams,
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
                        field: 'poiName',
                        title: '地标名称',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.poiName;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'poiType1',
                        title: 'POI大类',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.poiType1;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'poiType2',
                        title: 'POI中类',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.poiType2;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'poiType3',
                        title: 'POI小类',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.poiType3;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'poiCoordinate',
                        title: 'POI坐标',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.poiCoordinate;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'address',
                        title: '地址',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.address;
                        } //需要formatter一下才能显示正确的数据
                    },
                    {
                        field: 'tel',
                        title: '电话',
                        width: 20,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return row.tel;
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
                $('#poiTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
            }
        });
    }


	//新增
	function addrow() {
		parent.addTab('tabId_poi_add','添加地标点','<%=root%>/view/poi/add.jsp');

	}
	//更新
	function updaterow() {
		var rows = $('#poiTable').datagrid('getSelections');
		//这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择需要更新的地标点", 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', "只能选择一个地标点进行更新", 'info');
			return;
		}

		var ps = "";
		$.each(rows, function(i, n) {
			if (i == 0)
				ps += "?poiId=" + n.poiId;
		});
		
		var url = '<%=root%>/poi/update' + ps;

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

		parent.addTab('tabId_poi_update','更新地标点信息',url);
	}

 	//删除
	function deleterow() {
		$.messager.confirm('提示', '确定要删除吗?', function(result) {
			if (result) {
				var rows = $('#poiTable').datagrid('getSelections');
				var ps = "";
				$.each(rows, function(i, n) {
					if (i == 0)
						ps += "?poiId=" + n.poiId;
					else
						ps += "&poiId=" + n.poiId;
				});
				$.post('<%=root%>/poi/delete' + ps, function(data) {
					$('#poiTable').datagrid('reload');
					$.messager.alert('删除', data.mes, 'info');
				});
			}
		});
	}

    //搜索
    function select() {
        var cityCode = $('#cityCode').val();
        var cityName = $('#cityName').combobox('getText');
        var poiName = $('#poiName').val();
        var centerLonLat = $('#centerLonLat').val();
        var range = $('#range').combobox('getText');

        if (cityName == '全部') cityName = '';
        var queryParams = {cityCode: cityCode, cityName: cityName, poiName: poiName, centerLonLat: centerLonLat, range: range};
        initDataGrid("${ctx}/poi/searchlist?", queryParams);


    }
</script>
</head>

<body>
<form style="margin:20px 0 10px 0;" class="formular">
    <fieldset>
        <legend>查询条件</legend>
        城市代码: <input id="cityCode" style="width:100px;"/>
        城市名称: <input class="easyui-combobox" id="cityName"/>
        地标名称: <input id="poiName" style="width:100px;"/>
        中心点: <input id="centerLonLat" style="width:200px;"/>
        范围:
        <select id="range" class="easyui-combobox" name="range" style="width:100px;">
            <option value="500">500</option>
            <option value="1000">1000</option>
            <option value="1500">1500</option>
            <option value="2000">2000</option>
        </select>

        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a>
    </fieldset>
</form>
	<div style="padding: 10" id="tabdiv">
		<table id="poiTable"></table>
	</div>
</body>
<script type="text/javascript">
    $.extend($.fn.validatebox.defaults.rules, {
        intOrFloat: {// 验证整数或小数
        validator: function (value) {
            return /^\d+(\.\d+),\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入正确的经纬度'
        }

    });

    $('#centerLonLat').validatebox({
        validType: 'intOrFloat'
    });

</script>
</html>
