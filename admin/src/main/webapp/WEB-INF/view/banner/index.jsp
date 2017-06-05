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
				<li><a href="/admin/company" >企业管理</a></li>
				<li><a href="/admin/keywords" >搜索关键字管理</a></li>
				<li><a href="/admin/catalog" >一级分类管理</a></li>
				<li><a href="/admin/catalogtwo" >二分类级管理</a></li>
				<li><a href="/admin/coursetype" >课程一级目录</a></li>
				<li><a href="/admin/coursetype/typetwo" >课程二级目录</a></li>
				<li><a href="/admin/appcomment" >课程评论管理</a></li>
				<li><a href="/admin/appticket" >成绩列表</a></li>
				<li class="bgColor"><a href="/admin/banner">首页banner列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<!-- <div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">好多课首页Banner列表</strong>
				</div>
			</div> -->
			<div class="am-g">
					<form method="post" id="xlsForm">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button" onclick="edit(0,'${dictId}')" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button>
							</div>
						</div>
					</div>
				</div>
				</form>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-type">封面图片</th>
									<th class="table-type">描述</th>
									<th class="table-type">排序值</th>
									<th class="table-type">展示类型</th>
									<th class="table-type">关联数据</th>
									<th class="table-type">创建时间</th>
									<!-- <th class="table-type">操作</th> -->
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><img src="${obj.cover}" width="100px" height="40px" /></td>
									<td>${obj.name}</td>
									<td>${obj.weight}</td>
									<c:if test="${obj.show_type =='0'}">
										<td>课程</td>
									</c:if>
									<c:if test="${obj.show_type =='1'}">
										<td>讲师</td>
									</c:if>
									<c:if test="${obj.show_type =='2'}">
										<td>课程包</td>
									</c:if>
									<c:if test="${obj.show_type =='0'}">
										<td>${obj.course_name}</td>
									</c:if>
									<c:if test="${obj.show_type =='1'}">
										<td>${obj.teacher_name}</td>
									</c:if>
									<c:if test="${obj.show_type =='2'}">
										<td>${obj.type_name}</td>
									</c:if>
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
	function edit(id,dictId){
		window.location.href="/admin/banner/edit?id="+id;
	}
	function deleteType(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/banner/delete?id=" + id, function(result) {
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