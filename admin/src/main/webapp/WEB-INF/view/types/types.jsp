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
				<li><a href="/admin/article">文章管理(APP)</a></li>
				<li><a href="/admin/article?type=1">文章管理(网站)</a></li>
				<li class="bgColor"><a href="/admin/type">文章类型列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">文章类别列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf">
				<form method="post" id="xlsForm">
					<button type="button" onclick="edit(0)" class="add">
						<span class="icon-plus"></span> 新增
					</button>
				</form>
			</div>

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-title">封面</th>
									<th class="table-type">类别名称</th>
									<th class="table-type">排序值</th>
									<th class="table-type">创建时间</th>
									<%--<th class="table-type">操作</th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><img src="${obj.cover}" width="100px" height="40px" /></td>
									<td>${obj.value}</td>
									<td>${obj.weight}</td>
									<td>${fn:substring(obj.create_at,0,19)}</td>
								</tr>
								<tr>
									<td colspan=5>
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
	function edit(id){
		window.location.href="/admin/type/edit?id="+id;
	}
	function deleteType(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/type/delete?id=" + id, function(result) {
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