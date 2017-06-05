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
					<strong class="am-text-primary am-text-lg">老师列表</strong> / <small>Table</small>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-md-3 am-cf">
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
									<th class="table-type">服务类型</th>
									<th class="table-type">价格</th>
									<th class="table-type">课程定义</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.id}</td>
									<td><img src='${obj.avatar}' width="100px" height="40px"></td>
									<td>${obj.name}</td>
									<td>${obj.title}</td>
									<td>
										<c:if test="${obj.type == 1}">1对1</c:if>
										<c:if test="${obj.type == 2}">企业内训</c:if>
										<c:if test="${obj.type == 3}">解决方案</c:if>
									</td>
									<td>${obj.price}</td>
									<td>${obj.info}</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="edit('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span> 编辑
												</a>
												<a onclick="deleteTeacher('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
													<span class="am-icon-trash-o"></span> 删除
												</a>
											</div>
										</div>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="am-cf">
							共 ${page.count } 条记录
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
	function edit(id){
		if(id == 0){
			window.location.href="/admin/teacher/edit";
		}else{
			window.location.href="/admin/teacher/edit?id="+id;
		}
	}
	function deleteTeacher(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/teacher/delete?id=" + id, function(result) {
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