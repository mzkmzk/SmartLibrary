<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.K.NFC_Library.bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="5">
<title>还书页面</title>
</head>
<body onload="messageLoad('${requestScope.message}')" style="text-align: center;">

	 <h1>还书状况</h1>
	<s:if test="#request.LB_return.size()!=0">
	<table border="3" style="margin: 0 auto;">
	<tr><td>借书人</td><td>书名</td><td>出版社</td></tr>
	<s:iterator value="#request.LB_return" var="list">
	<tr><td><s:property value="#list.U_Name"/></td>
		<td><s:property value="#list.book_return.B_name"/></td>
		<td><s:property value="#list.book_return.B_press"/></td>
	</tr>
	</s:iterator>
	</table>
	</s:if>
	<s:else>
	暂无记录
	</s:else>
</body>
<script type="text/javascript">
function Clean_WebVisitTemporary_values_return(){
	 window.location.href="./NFC_Library/Clean_WebVisitTemporary_values_return"; 
}

</script>
 <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/messenger/messenger.min.js"></script>
	<script type="text/javascript" src="../js/messenger/message.js"></script>
</html>