<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<meta name="keywords" content="table">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%@include file="/base/link.jsp"%>
<link rel="stylesheet" href="/admin/crop/css/jquery.Jcrop.css" type="text/css" />
<script type="text/javascript" src="/admin/crop/js/jquery.Jcrop.js"></script>
<style type="text/css">
.img {
    max-width: none;
}
</style>
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
				上传图片
			</div>
			<hr />

			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>

				<div class="am-u-sm-12 am-u-md-8 maxWidth">
					<form class="am-form am-form-horizontal" id="myform" method="post">
						<input type="hidden" id="x" name="x" /> <input type="hidden"
							id="y" name="y" /> <input type="hidden" id="w" name="w" /> <input
							type="hidden" id="h" name="h" /> <input type="hidden" id="id"
							name="id" value='${id}'>
							<input type="hidden" name="path" id="path" >
					</form>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label"></label>
							<div class="am-u-sm-9">
								<form id="form" method="post">
								<input type="file" id="up_img" name="file" accept="image/png, image/jpeg, image/jpg" onchange="uploadImg()" />
								</form>
							</div>
						</div>
						<div class="am-form-group">
				           <div class="am-u-sm-9 am-u-sm-push-3">
				              <a class="am-btn am-btn-primary" id="saveBtn">保存修改</a>
				              <a class="am-btn am-btn-primary" href="javascript:history.go(-1);" id="cancel">返回</a>
				            </div>
				        </div>
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>
	<div id="imgdiv">
		<img src="" id="target" alt="" class="img" />
	</div>
</body>
<script type="text/javascript">
	function uploadImg(){
		var options ={
			url:"/admin/course/uploadCover",
			success : function(data) {
				if(data.status == 200){
					$("#target").attr('src',data.data.cover);
					$("#path").val(data.data.path);
					$("#target").css("width",data.data.width);
					$("#target").css("height",data.data.height);
					
					$('#target').Jcrop({
						onChange : updatePreview,
						onSelect : updatePreview,
						aspectRatio : 414 / 200
					}, function() {
						var bounds = this.getBounds();
						boundx = bounds[0];
						boundy = bounds[1];
						jcrop_api = this;
					});
		        }else{
		            showInfo(data.msg,function(){},3000);
		        }
			}
		};
		$("#form").ajaxSubmit(options);
	}

	$(function() {
		$("#saveBtn").bind("click", function() {
			var options ={
					url:"/admin/article/saveCover",
					success : function(data) {
						if(data.status == 200){
			            	window.location.href = "/admin/article/edit";
			            }else{
			            	showInfo(data.msg,function(){},3000);
			            }
					}
				};
			$("#myform").ajaxSubmit(options);
		});
	});
	
	function updatePreview(c) {
		if (parseInt(c.w) > 0) {
			$("#x").val(c.x);
			$("#y").val(c.y);
			$("#w").val(c.w);
			$("#h").val(c.h);
		}
	}
</script>
</html>