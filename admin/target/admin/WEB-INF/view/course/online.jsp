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
					<strong class="am-text-primary am-text-lg">线上课程列表</strong> / <small>Table</small>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button" onclick="edit(0)" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="am-u-md-3 am-cf">
					<div class="am-fr">
						<div class="am-input-group am-input-group-sm">
							<input type="text" class="am-form-field" id="search" name="search"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-default" onclick="submitForm()" type="button">搜索</button>
							</span>
						</div>
					</div>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-title">课程名称</th>
									<th class="table-title">价格</th>
									<th class="table-author">封面</th>
									<th class="table-type">时长</th>
									<th class="table-type">观看人数</th>
									<th class="table-type">报名人数</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.courseId}</td>
									<td>${obj.course_name}</td>
									<td>${obj.price}</td>
									<td><img src="${obj.cover}" width="100px"  height="40px"/></td>
									<td>${obj.time_length}</td>
									<td><a href="/admin/course/joinMembers?courseId=${obj.courseId}&type=1">${obj.count}</a></td>
									<td><a href="/admin/course/joinAllMembers?courseId=${obj.courseId}&type=1">${obj.joinMember}</a></td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="showOrHidden('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> <c:if test="${obj.is_hidden == 0}">APP隐藏</c:if><c:if test="${obj.is_hidden == 1}">APP显示</c:if>
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="showOrHidden2('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> <c:if test="${obj.web_hidden == 0}">WEB隐藏</c:if><c:if test="${obj.web_hidden == 1}">WEB显示</c:if>
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="upload('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> 上传课件
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="cover('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> 设置封面
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="edit('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> 编辑课程
												</a>
												<a onclick="deleteOnline('${obj.courseId}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
													<span class="am-icon-trash-o"></span> 删除课程
												</a>
												<%-- <a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',1);">
													<span class="am-icon-pencil-square-o" ></span> 设置预告
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',2);">
													<span class="am-icon-pencil-square-o" ></span> 设置直播
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',3);">
													<span class="am-icon-pencil-square-o" ></span> 设置历史
												</a> --%>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',2);">
													<span class="am-icon-pencil-square-o" ></span> 设置大纲
												</a>
											</div>
										</div>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="am-cf">
							共 ${page.count} 条记录
							<div class="am-fr">
								<jsp:include page="/base/page.jsp">
									<jsp:param value="${page}" name="page" />
								</jsp:include>
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
	function submitForm(){
		if($("#search").val() != ''){
			window.location.href="/admin/course/online?name="+$("#search").val();
		}
	}
	function upload(id){
		window.location.href="/admin/course/uploadFile?id="+id;
	}
	function edit(id){
		if(id == 0){
			window.location.href="/admin/course/edit?type=1&newtype=0";
		}else{
			window.location.href="/admin/course/edit?id="+id+"&newtype=0";
		}
	}
	function cover(id){
		window.location.href="/admin/course/cropImage?id="+id;
	}
	function editContent(id,type){
		window.location.href="/admin/course/editContent?id="+id+"&type="+type;
	}
	function end(id,type){
		if(type == 0){
			showConfirm("结束课程?",function(){
				$.getJSON("/admin/course/updateOnlinePlayStatus?id=" + id, function(result) {
					if (result.status == 200) {
						location.reload();
					} else {
						showInfo("系统错误....",function(){},3000);
					}
				});
			});
		}else{
			showConfirm("恢复课程?",function(){
				$.getJSON("/admin/course/updateOnlinePlayStatus?id=" + id, function(result) {
					if (result.status == 200) {
						location.reload();
					} else {
						showInfo("系统错误....",function(){},3000);
					}
				});
			});
		}
		
	}
	function showOrHidden(courseId){
		$.getJSON("/admin/course/hiddenOrShow?courseId=" + courseId, function(result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....",function(){},3000);
			}
		});
	}
	function showOrHidden2(courseId) {
		$.getJSON("/admin/course/webHidden?courseId=" + courseId, function(
				result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....", function() {
				}, 3000);
			}
		});
	}
	function deleteOnline(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/course/deleteOnline?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){},3000);
				}
			});
		});
	}
</script>
</html>