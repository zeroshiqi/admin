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
					<strong class="am-text-primary am-text-lg">招聘信息列表</strong> / <small>Table</small>
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
							<input type="text" class="am-form-field" id="search" name="job"> <span class="am-input-group-btn">
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
									<th class="table-type">招聘名称</th>
									<th class="table-type">图片</th>
									<th class="table-type">薪水</th>
									<th class="table-type">类别</th>
									<th class="table-type">公司</th>
									<th class="table-type">创建时间</th>
									<th class="table-type">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.id}</td>
									<td>${obj.job_name}</td>
									<td>
										<img src="${obj.cover}" width="150px" height="50px">
									</td>
									<td>${obj.pay}</td>
									<td>${obj.value}</td>
									<td>${obj.company}</td>
									<td>${obj.create_at}</td>
									<td>
											<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="edit('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span> 编辑
												</a>
												<a onclick="deleteType('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
													<span class="am-icon-trash-o"></span> 删除
												</a>
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