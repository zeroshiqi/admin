<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<%@include file="/base/link.jsp"%>
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
		<div class="nav-assist">
			<ul>
				<li class="bgColor"><a href="/admin/student">同学录列表</a></li>
<!-- 				<li><a href="/admin/teacher/invite">邀请列表</a></li> -->
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<!-- <div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">老师列表</strong>
				</div>
			</div> -->
			<div class="am-u-md-3 am-cf">				
				<button type="button" onclick="edit(0)" class="add">
					<span class="icon-plus"></span> 新增
				</button>
			</div>
			
			<div class="am-g" style="margin-top: 20px">
				<div class="am-u-sm-11">
					<div class="am-fr">
						<div class="am-input-group am-input-group-sm">
						</div>
					</div>
				</div>
				<div class="am-u-sm-1"></div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-type">头像</th>
									<th class="table-type">姓名</th>
									<th class="table-type">标题</th>
									<th class="table-type">城市</th>
									<th class="table-type">行业</th>
									<th class="table-type">职位</th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><img src='${obj.cover}' width="100px" height="40px"></td>
									<td>${obj.name}</td>
									<td>${obj.title}</td>
									<td>${obj.city}</td>
									<td>${obj.industry}</td>
									<td>${obj.job}</td>
								</tr>
								<tr>
									<td colspan=7>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span>
												</a>
												<a onclick="deleteTeacher('${obj.id}')" class="glyphicon glyphicon-trash" >
													<span class="am-icon-trash-o"></span>
												</a>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
							</c:forEach>
						</table>
						<div class="tfoot">
							共 ${page.count } 条记录
							<div class="am-fr am-fr-page">
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
	function edit(id){
		if(id == 0){
			window.location.href="/admin/student/edit";
		}else{
			window.location.href="/admin/student/edit?id="+id;
		}
	}
	function deleteTeacher(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/student/delete?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){
						//..
					},3000);
				}
			});
		});
	}
</script>
</html>