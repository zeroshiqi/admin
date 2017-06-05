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
				<li <c:if test="${newtype == 0}">class="bgColor"</c:if>><a href="/admin/course/offline">线下课程管理</a></li>
				<li <c:if test="${newtype == 1}">class="bgColor"</c:if>><a href="/admin/course/onlineNew">线上课程管理</a></li>
				<li class="now"><a href="/admin/course/online">录音课程管理</a></li>
				<li><a href="/admin/online">录音课程分类管理</a></li>
			</ul>
		</div>
	
		<!-- content start -->
		<div class="admin-content">

			<!-- <div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">录音课程分类列表</strong>
				</div>
			</div> -->
			<div class="am-u-md-3 am-cf">
			<form method="post" id="xlsForm">
					<button type="button" onclick="edit(0,'${dictId}')" class="add">
						<span class="icon-plus"></span> 新增
					</button>
				</form>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-type">类别名称</th>
									<th class="table-type">排序值</th>
									<!-- <th class="table-type">操作</th> -->
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
<%-- 									<td><img src="${obj.cover}" width="100px" height="40px" /></td> --%>
									<td>${obj.value}</td>
									<td>${obj.weight}</td>
<%-- 									<td>${fn:substring(obj.create_at,0,19)}</td> --%>
								</tr>
								<tr>
									<td colspan="3">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
											<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}','${dictId}');">
													<span class="am-icon-pencil-square-o" ></span>
											</a>
											<a onclick="deleteType('${obj.id}')" class="glyphicon glyphicon-trash" >
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
	function edit(id,dictId){
		window.location.href="/admin/online/edit?id="+id+"&dictId="+dictId;
	}
	function deleteType(id){
		showConfirm("课程分类删除后此分类下的课程会全部失去关联,确认删除吗?",function(){
			$.getJSON("/admin/online/delete?id=" + id, function(result) {
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