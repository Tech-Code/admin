<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%> 
<% String root = request.getContextPath(); %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="<%=root%>/js/easyui/themes/dayun/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/css/tooltip-form-style.css">
<link rel="stylesheet" href="<%=root%>/css/validationEngine.jquery.css" type="text/css"/>
<link rel="stylesheet" href="<%=root%>/css/customMessages.css" type="text/css"/>
<link rel="stylesheet" href="<%=root%>/css/template.css" type="text/css"/>

<script type="text/javascript" src="<%=root%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=root%>/js/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=root%>/js/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=root%>/js/easyui/validate/easyui_validate.js"></script>
<script type="text/javascript" src="<%=root%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    function getContextPath() {
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
        return result;
    }
</script>