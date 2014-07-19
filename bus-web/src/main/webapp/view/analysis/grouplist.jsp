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
			url : "${ctx}/analysis/grouplist?position=10&name=all&startTime="+getPreMonth(getToDay())+"&endTime="+getToDay(), //数据来源
			sortOrder : 'desc', //倒序
			idField:'id', //主键字段
			remoteSort : true, //服务器端排序
			pagination : true, //显示分页
			rownumbers : true, //显示行号
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
			textField:'text'
			});
		
		
		$('#selectType').combobox('setValue','全部');
		$('#selectgroup').combobox('setValue','日');
		
		$('#beginTime').datetimebox({   
			 showSeconds:false,  
             formatter: formatDateText,  
             parser: parseDate
		  });
		
		$('#endTime').datetimebox({   
			 showSeconds:false,  
            formatter: formatDateText,  
            parser: parseDate
		  });  
		
		$('#beginTime').datetimebox('setValue',getPreMonth(getToDay()));
		$('#endTime').datetimebox('setValue',getToDay());
	});
	//更新
	function select() {
		var btime =$('#beginTime').datebox('getValue');
		var etime = $('#endTime').datebox('getValue');
		var selectType = $('#selectType').combobox('getValue');
		if(selectType=="全部"||selectType==""){
			selectType="all";
		}
		var selectgroup = $('#selectgroup').combobox('getValue');
		if(selectgroup=="日"||selectgroup==""){
			selectgroup=10;
		}
		$('#analysistypeTable').datagrid({ url:"${ctx}/analysis/grouplist?",queryParams:{startTime:btime,endTime:etime,name:selectType,position:selectgroup},method:"post"});
	}
	
	//更新
	function down() {
		var btime =$('#beginTime').datebox('getValue');
		var etime = $('#endTime').datebox('getValue');
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
    
    
    function formatDateText(date) { 
    	var test = $('#selectgroup').combobox('getValue');
    	var rainType =test;
        if(rainType=='日'){
        	rainType=10;
        }
        if(rainType==16){
       	 return date.formatDate("yyyy-MM-dd hh:mm");  
       }else if(rainType==13){
       	 return date.formatDate("yyyy-MM-dd hh");  
       }else if(rainType==10){
       	return date.formatDate("yyyy-MM-dd");
       }else{
       	 return date.formatDate("yyyy-MM"); 
       }
    }
    
  //把时间格式字符串转化为时间  
  //如下格式  
  //2006  
  //2006-01  
  //2006-01-01  
  //2006-01-01 12  
  //2006-01-01 12:12  
  //2006-01-01 12:12:12  
  function parseDate(dateStr) {  
      var regexDT = /(\d{4})-?(\d{2})?-?(\d{2})?\s?(\d{2})?:?(\d{2})?:?(\d{2})?/g;  
      var matchs = regexDT.exec(dateStr);  
      var date = new Array();  
      for (var i = 1; i < matchs.length; i++) {  
          if (matchs[i]!=undefined) {  
              date[i] = matchs[i];  
          } else {  
              if (i<=3) {  
                  date[i] = '01';  
              } else {  
                  date[i] = '00';  
              }  
          }  
      }  
      return new Date(date[1], date[2]-1, date[3], date[4], date[5],date[6]);  
  }  
    
//为date类添加一个format方法  
//yyyy 年  
//MM 月  
//dd 日  
//hh 小时  
//mm 分  
//ss 秒  
//qq 季度  
//S  毫秒  
Date.prototype.formatDate = function (format) //author: meizz  
{  
    var o = {  
        "M+": this.getMonth() + 1, //month  
        "d+": this.getDate(),    //day  
        "h+": this.getHours(),   //hour  
        "m+": this.getMinutes(), //minute  
        "s+": this.getSeconds(), //second  
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter  
        "S": this.getMilliseconds() //millisecond  
    }  
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,  
    (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var k in o) if (new RegExp("(" + k + ")").test(format))  
        format = format.replace(RegExp.$1,  
      RegExp.$1.length == 1 ? o[k] :  
        ("00" + o[k]).substr(("" + o[k]).length));  
    return format;  
} 

</script>
</head>

<body >
<div style="margin:1px 0 10px 0;">
<input class="easyui-datetimebox" id="beginTime" />
<input class="easyui-datetimebox" id="endTime" />
<input class="easyui-combobox"  id="selectType" style="width:200px;" />
<input class="easyui-combobox"  id="selectgroup" style="width:80px;" />
 <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a> 
 <a href="#"  class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="down()">导出EXCEL</a>  
</div>
	<div style="padding: 10" id="tabdiv">
		<table id="analysistypeTable"></table>
	</div>
</body>
</html>
