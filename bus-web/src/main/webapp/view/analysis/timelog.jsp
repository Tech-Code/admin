<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	jQuery(function($) {
		var url="${ctx}/analysis/loglist?name=all&type=all&startTime="+getPreWeek()+"&endTime="+getToDay(); //数据来源
		initDataGrid(url,"{}");
		//业务名称
		$('#selectType').combobox({ 
			url:"${ctx}/analysis/typename",
			valueField:'id', 
			textField:'text' 
			}); 
		//服务类型
		$('#selectName').combobox({ 
			url:"${ctx}/analysis/servicename",
			valueField:'id', 
			textField:'text' 
			});
		$('#selectName').combobox('setValue','全部');
		$('#selectType').combobox('setValue','全部');
		$('#beginTime').datebox('setValue',getPreWeek());
		$('#endTime').datebox('setValue',getToDay());
	});
	//更新
	function select() {
		var btime =$('#beginTime').datebox('getValue');
		var etime = $('#endTime').datebox('getValue');
		var selectType = $('#selectType').combobox('getValue');
		if(selectType=="全部"||selectType==""){
			selectType="all";
		}
		var keyword =$('#keyword').val();
		if(keyword=="模糊关键字"||keyword==""){
			keyword="";
		}
		var selectName = $('#selectName').combobox('getValue');
		if(selectName=="全部"||selectName==""){
			selectName="all";
		}
		var getUrl="${ctx}/analysis/loglist?";
		var queryParams={startTime:btime,endTime:etime,name:selectType,type:selectName,keyword:keyword};
		initDataGrid(getUrl,queryParams);
	}
	
	function initDataGrid(getUrl,queryParams){
		$('#analysistypeTable').datagrid({
			method : 'post',
			iconCls : 'icon-edit', //图标
			singleSelect : true, //多选
			height : 360, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : getUrl,
			sortOrder : 'desc', //倒序
			idField:'id', //主键字段
			remoteSort : true, //服务器端排序
			pagination : true, //显示分页
			rownumbers : true, //显示行号
			pageNumber:1,
			queryParams: queryParams,// 查询参数
			columns : [ [ 
			{
				field : 'name',
				title : '业务',
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
				field : 'type',
				title : '服务类型',
				width : 20,
				formatter : function(value, row, index) {
					return row.type;
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
			},
			onClickRow:function(rowIndex, rowData){
				//var win=$("<div class=\"messager-body\"></div>").html(rowData.url).appendTo("textarea");
				$("textarea").css("display","block");
				$("textarea").html(rowData.url);
				$("textarea").select();
			}
		});
					
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
<input class="easyui-datebox" id="beginTime" data-options="required:true,showSeconds:false"/>
<input class="easyui-datebox" id="endTime" data-options="required:true,showSeconds:false" />
 <input class="easyui-combobox"  id="selectType" style="width:200px;" />
 <input class="easyui-combobox"  id="selectName" style="width:100px;" />
<label>
<input name="keyword" id="keyword"  value="模糊关键字" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#999"/>
</label> 
 <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="select()">查询</a>  
</fieldset>
    </form>
	<div style="padding: 10" id="tabdiv">
		<table id="analysistypeTable"></table>
	</div>
	 <textarea style="width:100%;height:60px;margin-top: 10px;border: 1px solid #B5B8C8;font-size: 12px;display:none;overflow-y:hidden">
	 </textarea>
</body>
</html>
