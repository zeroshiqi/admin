<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<meta name="keywords" content="table">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%@include file="/base/link.jsp"%>
<%--<link href="/admin/upload/css/default.css" rel="stylesheet"--%>
	<%--type="text/css" />--%>
<script type="text/javascript"
	src="/admin/upload/swfupload/swfupload.js"></script>
<script type="text/javascript" src="/admin/upload/swfupload.queue.js"></script>
<script type="text/javascript" src="/admin/upload/fileprogress.js"></script>
<script type="text/javascript" src="/admin/upload/handlers.js"></script>
<!-- 初始化swfupload 对象-->
</head>
<body>

	<header class="">
		<%@include file="/base/top.jsp"%>
	</header>

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar">
			<%@include file="/base/left.jsp"%>
		</div>
		<!-- sidebar end -->

		<!-- 辅助导航栏 -->
		<div class="nav-assist" style="background:#0e90d2">
		<a  class="icon-reply" href="javascript:history.go(-1);" style="float:left;color:#fff;padding:12px 20px"></a>
		</div>

		<!-- content start -->
		<div class="admin-content">
			<div class="minTitle">
				上传PDF大纲
			</div>

			<hr />

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>

				<div class="am-u-sm-12 am-u-md-8">
				
					<form class="am-form am-form-horizontal" id="myform" enctype="multipart/form-data" method="post">
						<div class="am-form-group">
<!-- 						 	<div class="am-u-sm-9 am-u-sm-push-3"> -->
<%-- 				            	<input type="text" name="courseid" id="courseid" value="${id}"/> --%>
<!-- 				              <a class="am-btn am-btn-primary" id="saveBtn">保存修改</a> -->
<!-- 				            </div> -->
				            <div class="am-u-sm-9 am-u-sm-push-3">
				          		<input type="hidden" name="courseid" id="courseid" value="${id}"/>
				          		<input type="hidden" name="search" id="search" value="${name}"/>
				          		<input type="hidden" name="page" id="page" value="${page}"/>
				          		<input type="text" name="pdfAddress" id="pdfAddress" value="${pdfAddress}" placeholder="pdf文件地址"/>
				            	<input type="file" name="pdf">
				              <a class="am-btn am-btn-primary" id="saveBtn">保存修改</a>
				            </div>
				          </div>
					</form>
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>

</body>
<script type="text/javascript">
	$(function(){
		$("#saveBtn").bind("click", function(){  
			var options ={
					url:"/admin/course/updatePdfAddress?courseid="+$("#courseid").val(),
					success : function(data) {
						if(data.status == 200){
			            	window.location.href = "/admin/course/online?name="+$("#search").val()+"&page="+$("#page").val();
			            }else{
			            	showInfo(data.msg,function(){},3000);
			            }
					}
				};
				$("#myform").ajaxSubmit(options);	
		});
	});
</script>
</html>