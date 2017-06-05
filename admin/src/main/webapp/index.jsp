<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
<link rel="stylesheet" href="/admin/login/css/index.css">
<SCRIPT src="/admin/login/js/jquery-1.9.1.min.js" type="text/javascript"></SCRIPT>
<SCRIPT src="/admin/login/js/jquery.form.js" type="text/javascript"></SCRIPT>

</head>
<body>
	<DIV class="top_div"></DIV>
	<DIV
		style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
		<DIV style="width: 165px; height: 96px; position: absolute;">
			<DIV class="tou"></DIV>
			<DIV class="initial_left_hand" id="left_hand"></DIV>
			<DIV class="initial_right_hand" id="right_hand"></DIV>
		</DIV>
		<form id="myForm"   method="post">
		<P style="padding: 30px 0px 10px; position: relative;">
			<SPAN class="u_logo"></SPAN> <INPUT class="ipt" type="text"
				placeholder="请输入用户名" id="account" name="account" value="">
		</P>
		<P style="position: relative;">
			<SPAN class="p_logo"></SPAN> <INPUT class="ipt" id="password"
				type="password" placeholder="请输入密码" name="password" value="">
		</P>
		</form>
		<DIV
			style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
			<P style="margin: 0px 35px 20px 45px;">
				<SPAN style="float: left;"><A
					style="color: rgb(204, 204, 204);" href="#"></A></SPAN> <SPAN
					style="float: right;"><A
					style="color: rgb(204, 204, 204); margin-right: 10px;" href="#"></A>
					<A
					style="cursor :pointer; ;background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"
					onclick="subForm()" >登录</A> </SPAN>
			</P>
		</DIV>
	</DIV>

	<!-- Javascript -->
	<script type="text/javascript">
		function subForm(){
			var account = $("#account").val();
			if(account == ''){
				alert("请输入账号!");
				return;
			}
			var password = $("#password").val();
			if(password == ''){
				alert("请输入密码");
				return;
			}
			var ajaxOption={
					url:"/admin/login/login",
					success:function(data){
						if(data.status == 200){
							window.location.href = "/admin/course/offline";
						}else{
							alert(data.msg);
						}
					}
				};
			$('#myForm').ajaxSubmit(ajaxOption);
		}
		$(function() {
			//得到焦点
			$("#password").focus(function() {
				$("#left_hand").animate({
					left : "150",
					top : " -38"
				}, {
					step : function() {
						if (parseInt($("#left_hand").css("left")) > 140) {
							$("#left_hand").attr("class", "left_hand");
						}
					}
				}, 2000);
				$("#right_hand").animate({
					right : "-64",
					top : "-38px"
				}, {
					step : function() {
						if (parseInt($("#right_hand").css("right")) > -70) {
							$("#right_hand").attr("class", "right_hand");
						}
					}
				}, 2000);
			});
			//失去焦点
			$("#password").blur(function() {
				$("#left_hand").attr("class", "initial_left_hand");
				$("#left_hand").attr("style", "left:100px;top:-12px;");
				$("#right_hand").attr("class", "initial_right_hand");
				$("#right_hand").attr("style", "right:-112px;top:-12px");
			});
		});
	</script>
</body>
</html>