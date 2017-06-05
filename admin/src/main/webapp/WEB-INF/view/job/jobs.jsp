<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
				<li><a href="/admin/job/jobtype">招聘类别列表</a></li>
				<li class="bgColor"><a href="/admin/job">招聘信息列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">招聘信息列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>

			<div class="am-u-md-6 am-cf">
					<button type="button" onclick="edit(0)" class="add">
						<span class="icon-plus"></span> 新增
					</button>

					<div class="Go">
						<input type="text" class="text" id="search" name="job">
						<button class="icon-search" onclick="submitForm()" type="button"></button>
					</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-type">招聘名称</th>
									<th class="table-type">图片</th>
									<th class="table-type">薪水</th>
									<th class="table-type">类别</th>
									<th class="table-type">公司</th>
									<th class="table-type">创建时间</th>
									<%--<th class="table-type">操作</th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td>${obj.job_name}</td>
									<td>
										<img src="${obj.cover}" width="150px" height="50px">
									</td>
									<td>${obj.pay}</td>
									<td>${obj.value}</td>
									<td>${obj.company}</td>
									<td>${fn:substring(obj.create_at,0,19)}</td>
								</tr>
								<tr>
									<td colspan=7>
										<div class="am-btn-toolbar">
											<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
												<span class="am-icon-pencil-square-o" ></span>
											</a>
											<a onclick="deleteType('${obj.id}')" class="glyphicon glyphicon-trash" >
												<span class="am-icon-trash-o"></span>
											</a>
										</div>
									</td>
								</tr>
							</tbody>
							</c:forEach>
						</table>
						<div class="tfoot">
							共 ${page.count} 条记录
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
function submitForm(){
	if($("#search").val() != ''){
		window.location.href="/admin/job?job="+$("#search").val();
	}
}
	function edit(id){
		if(id == 0){
			window.location.href="/admin/job/editjob";
		}else{
			window.location.href="/admin/job/editjob?id="+id;
		}
	}
	function deleteType(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/job/deleteJob?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo(result.msg,function(){
						//..
					},3000);
				}
			});
		});
	}
</script>
</html>