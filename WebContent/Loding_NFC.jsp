<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.K.NFC_Library.bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



   
<title>NFC写入数据页面</title>


	<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/bootstrap-responsive.min.css">

 <link rel="stylesheet" type="text/css" media="screen" href="./css/messenger/messenger.css">
    <link rel="stylesheet" type="text/css" media="screen" href="./css/messenger/messenger-theme-flat.css">

</head>
<body onload="messageLoad('${sessionScope.message}');" >
	<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<h1 class="text-center">
				NFC写入数据
			</h1>
			<div class="row-fluid">
				<div class="span12 text-center">
					<div class="row-fluid">
							<div class="span12">
									<input class="input-medium search-query" type="text" name="NFCID" id="NFCID" /> <button onclick="search_NFCID()" class="btn" >识别NFC</button>
							</div>
			</div>
				</div>
			</div>
			<div class="tabbable" id="tabs-260582">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-668012" data-toggle="tab">添加学生NFC卡</a>
					</li>
					<li>
						<a href="#panel-423270" data-toggle="tab">添加书籍NFC卡</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-668012">
						<form class="form-horizontal" action="./NFC_Library/NFC_InsertUser" id="inser_user" onsubmit="inser_Submit(this)" method="post">
									<div class="control-group">
										 <label class="control-label"  for="U_studentID">学号</label>
										<div class="controls">
											<input id="U_studentID" type="text" name="U_studentID"/>
										</div>
									</div>
									<div class="control-group">
										 <label class="control-label" for="U_name">姓名</label>
										<div class="controls">
											<input id="U_name" type="text" name="U_name"/>
										</div>
									</div>
									<div class="control-group">
										<div class="controls">
											 <button type="submit" class="btn">登记</button>
										</div>
									</div>
						</form>
					</div>
					<div class="tab-pane" id="panel-423270">
						<form class="form-horizontal" action="./NFC_Library/NFC_InsertBook" id="insert_book" onsubmit="inser_Submit(this)" method="post">
										<div class="control-group">
											 <label class="control-label" for="B_name">书名</label>
											<div class="controls">
												<input id="B_name" type="text" name="B_name"/>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label" for="B_press">出版社</label>
											<div class="controls">
												<input id="B_press" type="text" name="B_press"/>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
												  <button type="submit" class="btn">登记</button>
											</div>
										</div>
										
						</form>
					</div>
					
				</div>
			</div>
			
		</div>
	</div>
</div>

</body>
 <script type="text/javascript" src="./js/jquery.min.js"></script>
 <script type="text/javascript" src="./js/bootstrap.min.js"></script>
    <script type="text/javascript" src="./js/messenger/messenger.min.js"></script>
	<script type="text/javascript" src="./js/messenger/message.js"></script>
	
	<script type="text/javascript">
	<!-- 用户提交添加表单前 先添加 NFCID-->
	function  inser_Submit(from_this){
		 var NFC_ID=document.createElement("input");
		 NFC_ID.type="hidden";
		 NFC_ID.name="NFC_ID";
		 NFC_ID.value=document.getElementById("NFCID").value;
		 from_this.appendChild(NFC_ID);
	}
	
	function search_NFCID(){
		$.getJSON('./NFC_Library/NFC_Search_NFCID',null).done(function(data){
			if(data.msg!=0){
				messageLoad(21);
				document.getElementById("NFCID").value=data.NFCID;
			}else{
				messageLoad(22);
			}
		}).fail(function(){
			alert('登录出错：网络连接出现问题，请稍后再试。');
		}).complete(function(){
		})
		return false;
	}
	
</script>
</html>