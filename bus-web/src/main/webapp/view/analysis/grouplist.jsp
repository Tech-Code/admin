<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		var url = "${ctx}/analysis/grouplist?position=10&name=all&startTime="+getPreWeek()+"&endTime="+getToDay(); //数据来源
		initDataGrid(url,"{}");
		//业务名称
		$('#selectType').combobox({ 
			url:"${ctx}/analysis/typename",
			valueField:'id', 
			textField:'text' 
			}); 
		
		//分段类型
		$('#selectgroup').combobox({ 
			url:"${ctx}/analysis/timetype",
			valueField:'id', 
			textField:'text',
			onSelect : function(record) {
					if(record.id==7){
						document.getElementById("beginTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});}; 
						document.getElementById("endTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});}; 
					}else if(record.id==10){
						document.getElementById("beginTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'});}; 
						document.getElementById("endTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'});}; 
					}else if(record.id==13){
						document.getElementById("beginTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH'});}; 
						document.getElementById("endTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH'});}; 
					}else if(record.id==16){
						document.getElementById("beginTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'});}; 
						document.getElementById("endTime").onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'});}; 
					}
				} 
			});
		
		$('#selectType').combobox('setValue','全部');
		$('#selectgroup').combobox('setValue','日');
		
		$('#beginTime').val(getPreWeek());
		$('#endTime').val(getToDay());
	});
	//更新
	function select() {
		var btime =document.getElementById("beginTime").value;
		var etime = document.getElementById("endTime").value;
		var selectType = $('#selectType').combobox('getValue');
		if(selectType=="全部"||selectType==""){
			selectType="all";
		}
		var selectgroup = $('#selectgroup').combobox('getValue');
		if(selectgroup=="日"||selectgroup==""){
			selectgroup=10;
		}
		var url="${ctx}/analysis/grouplist?";
		var queryParams={startTime:btime,endTime:etime,name:selectType,position:selectgroup,page:1};
		initDataGrid(url,queryParams);
	}
	function initDataGrid(getUrl,queryParams) {
		$('#analysistypeTable').datagrid({
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : false, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠 
			url :getUrl,
			sortOrder : 'desc', //倒序
			idField:'id', //主键字段
			remoteSort : true, //服务器端排序
			pagination : true, //显示分页
			rownumbers : true, //显示行号
			pageNumber:1,
			queryParams: queryParams,// 查询参数
			columns : [ [ 
			{
				field : 'startTime',
				title : '开始时间段',
				width : 20,
				formatter : function(value, row, index) {
					return row.startTime;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'endTime',
				title : '结束时间段',
				width : 20,
				formatter : function(value, row, index) {
					return row.endTime;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'name',
				title : '业务名称',
				width : 20,
				formatter : function(value, row, index) {
					return row.name;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'keyName',
				title : '业务标识',
				width : 20,
				formatter : function(value, row, index) {
					return row.keyName;
				} //需要formatter一下才能显示正确的数据
			},{
				field : 'total',
				title : '业务总量',
				width : 20,
				formatter : function(value, row, index) {
					return row.total;
				} //需要formatter一下才能显示正确的数据
			}] ],
			
			onLoadSuccess : function() {
				$('#analysistypeTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});		
	}
	//更新
	function down() {
		var btime =document.getElementById("beginTime").value;
		var etime = document.getElementById("endTime").value;
		var selectType = $('#selectType').combobox('getValue');
		if(selectType=="全部"||selectType==""){
			selectType="all";
		}
		var selectgroup = $('#selectgroup').combobox('getValue');
		if(selectgroup=="日"||selectgroup==""){
			selectgroup=10;
		}
		var url =  "${ctx}/analysis/downloadgl?name="+selectType+"&position="+selectgroup+"&startTime="+btime+"&endTime="+etime; 
	    window.location.href = url;  
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
	 
	 function getPreWeek(){
	    	var beforeDate = new Date();
	    	beforeDate.setTime(beforeDate.getTime()-1000*60*60*24*7);
	    	var strYear2=beforeDate.getFullYear();
	    	var strMon2=beforeDate.getMonth()+1;
	    	var strDate2=beforeDate.getDate();
	    	if (strMon2 < 10) {
	    		  strMon2 = '0' + strMon2;
	        }
	    	if (strDate2 < 10) {
	    		strDate2 = '0' + strDate2;
	        }
	    	var std =strYear2+"-"+strMon2+"-"+strDate2;
	    	return std;
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
<form style="margin:20px 0 10px 0;" class="formular">
        <fieldset>
            <legend>查询条件</legend>
<input type="text" class="Wdate" id="beginTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
<input type="text" class="Wdate" id="endTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
<input class="easyui-combobox"  id="selectType" style="width:200px;" />
<input class="easyui-combobox"  id="selectgroup" style="width:80px;" />
 <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a> 
 <a href="#"  class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="down()">导出EXCEL</a>  
</fieldset>
    </form>
	<div style="padding: 10" id="tabdiv">
		<table id="analysistypeTable"></table>
	</div>
</body>
</html>
