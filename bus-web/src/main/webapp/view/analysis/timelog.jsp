<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		
		$('#analysistypeTable').datagrid({
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/analysis/loglist?name=all&startTime="+getPreMonth(getToDay())+"&endTime="+getToDay(), //数据来源
			sortOrder : 'desc', //倒序
			idField:'id', //主键字段
			remoteSort : true, //服务器端排序
			pagination : true, //显示分页
			rownumbers : true, //显示行号
			detailFormatter: function(rowIndex, rowData){
                return '<table><tr>' +
                        '<td style="border:0;padding-right:10px">' +
                        '<p>Name: ' + 1111 + '</p>' +
                        '<p>Amount: ' + 22222+ '</p>' +
                        '</td>' +
                        '<td style="border:0">' +
                        '<p>Price: ' + 11111 + '</p>' +
                        '<p>Cost: ' + 22222+ '</p>' +
                        '</td>' +
                        '</tr></table>';
            },
			columns : [ [ 
			{
				field : 'name',
				title : '业务',
				width : 20,
				
				formatter : function(value, row, index) {
					return row.name;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'evertime',
				title : '时间',
				width : 20,
				formatter : function(value, row, index) {
					return row.evertime;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'url',
				title : '访问记录',
				width : 20,
				formatter : function(value, row, index) {
					return row.url;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'statusCode',
				title : '状态码',
				width : 20,
				formatter : function(value, row, index) {
					return row.statusCode;
				} //需要formatter一下才能显示正确的数据
			}] ],
			
			onLoadSuccess : function() {
				$('#analysistypeTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
		
		//业务名称
		$('#selectType').combobox({ 
			url:"${ctx}/analysis/typename",
			valueField:'id', 
			textField:'text' 
			}); 
		
		$('#selectType').combobox('setValue','全部');
		$('#beginTime').datebox('setValue',getPreMonth(getToDay()));
		$('#endTime').datebox('setValue',getToDay());
	});
	//更新
	function select() {
		var btime =$('#beginTime').datebox('getValue');
		var etime = $('#endTime').datebox('getValue');
		var selectType = $('#selectType').combobox('getValue');
		if(selectType=="全部"){
			selectType="all";
		}
		var keyword =$('#keyword').val();
		$('#analysistypeTable').datagrid({ url:"${ctx}/analysis/loglist?",queryParams:{startTime:btime,endTime:etime,name:selectType,keyword:keyword},method:"post"});
	}
	
	 function getToDay(){
         
         var now = new Date();
         var nowYear = now.getFullYear();
         var nowMonth = now.getMonth();
         var nowDate = now.getDate();
         newdate = new Date(nowYear,nowMonth,nowDate);
         nowMonth = doHandleMonth(nowMonth + 1);
         nowDate = doHandleMonth(nowDate);
         return nowYear+"-"+nowMonth+"-"+nowDate;
    }
   
    //----修改日期格式填充零
    function doHandleMonth(month){
         if(month.toString().length == 1){
          month = "0" + month;
         }
         return month;
    }
    
    function getPreMonth(date) {
        var arr = date.split('-');
        var year = arr[0]; //获取当前日期的年份
        var month = arr[1]; //获取当前日期的月份
        var day = arr[2]; //获取当前日期的日
        var days = new Date(year, month, 0);
        days = days.getDate(); //获取当前日期中月的天数
        var year2 = year;
        var month2 = parseInt(month) - 1;
        if (month2 == 0) {
            year2 = parseInt(year2) - 1;
            month2 = 12;
        }
        var day2 = day;
        var days2 = new Date(year2, month2, 0);
        days2 = days2.getDate();
        if (day2 > days2) {
            day2 = days2;
        }
        if (month2 < 10) {
            month2 = '0' + month2;
        }
        var t2 = year2 + '-' + month2 + '-' + day2;
        return t2;
    }

</script>
</head>

<body >
<div style="margin:20px 0 10px 0;">
<input class="easyui-datebox" id="beginTime" data-options="required:true,showSeconds:false"/>
<input class="easyui-datebox" id="endTime" data-options="required:true,showSeconds:false" />
 <input class="easyui-combobox"  id="selectType" style="width:200px;" />
<label>
<input name="keyword" id="keyword" value="查询关键字" class="text-input"/>
</label> 
 <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a>  
</div>
	<div style="padding: 10" id="tabdiv">
		<table id="analysistypeTable"></table>
	</div>
</body>
</html>
