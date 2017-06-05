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
<link rel="stylesheet" href="/admin/crop/css/jquery.Jcrop.css"
	type="text/css" />
<script type="text/javascript" src="/admin/crop/js/jquery.Jcrop.js"></script>

</head>
<body>

	<header class="am-topbar admin-header">
		<%@include file="/base/top.jsp"%>
	</header>

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar">
			<%@include file="/base/left.jsp"%>
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">课程图片</strong> / <small>CourseImages</small>
					<input type="hidden" name="id" id="courseId" value="${id}" />
				</div>
			</div>
			
			<div class="am-g">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button" onclick="editImage(0)" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<ul class="am-avg-sm-2 am-avg-md-3 am-avg-lg-3 am-margin gallery-list">
				<c:forEach var="obj" items="${images}">
					<li>
			        <a href="#">
			          <img class="am-img-thumbnail am-img-bdrs" style="width: 414px;height: 212px" src="${obj.image_url}" alt=""/>
			        </a>
			        <div class="gallery-title" style="cursor: pointer;"><a onclick="editImage('${obj.id}')"> 修改 </a><a onclick="deleteImage('${obj.id}')"> 删除 </a></div>
			      </li>
				</c:forEach>
		    </ul>
		</div>
		<!-- content end -->
	</div>

</body>
<script type="text/javascript">
	//删除图片
	function deleteImage(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/course/deleteImage?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){},3000);
				}
			});
		});
	}
	
	//修改图片
	function editImage(id){
		var courseId = $("#courseId").val();
		
		if(id == 0){
			window.location.href="/admin/course/editImage?courseId="+courseId;
		}else{
			window.location.href="/admin/course/editImage?id="+id+'&courseId='+courseId;
		}
	}
</script>
</html>