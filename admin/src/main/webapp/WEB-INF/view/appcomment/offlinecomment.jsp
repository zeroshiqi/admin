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
				<li><a href="/admin/company">企业管理</a></li>
				<li><a href="/admin/keywords">搜索关键字管理</a></li>
				<li><a href="/admin/catalog">课程一级分类管理</a></li>
				<li><a href="/admin/catalogtwo">课程二级分类管理</a></li>
				<li><a href="/admin/coursetype">课程一级目录</a></li>
				<li><a href="/admin/coursetype/typetwo">课程二级目录</a></li>
				<li class="bgColor"><a href="/admin/appcomment">企业课程评论管理</a></li>
				<li><a href="/admin/appticket">好多课课程试卷成绩列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

		<%--<div class="am-u-md-3 am-cf">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">评论列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="am-u-md-6 am-cf">--%>
				<%--<div class="am-btn-toolbar am-fl">--%>
					<%--<div class="am-btn-group am-btn-group-xs">--%>
						<%--<!-- <button type="button" onclick="edit(0)" class="am-btn am-btn-default">--%>
							<%--<span class="am-icon-plus"></span> 新增--%>
						<%--</button> -->--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div class="am-input-group am-input-group-sm">--%>
					<%--<!-- <input type="text" class="am-form-field" id="search" name="mobile"> <span class="am-input-group-btn">--%>
					<%--<button class="am-btn am-btn-default" onclick="submitForm()" type="button">搜索</button>--%>
					<%--</span> -->--%>
				<%--</div>--%>
			<div class="am-u-md-3 am-cf">
				<form method="post" id="xlsForm">
					<div class="Go">
						<input type="text" placeholder="课程名称" class="text" id="courseName" name="courseName">
						<input type="text" placeholder="评论内容" class="text" id="comment" name="comment" >
						<!-- 								<input type="text" placeholder="发票抬头" class="am-form-field" id="invoiceTitle" name="invoiceTitle">  -->
						<button class="icon-search" onclick="submitForm()" type="button"></button>
					</div>
				</form>
			</div>


			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-title">课程</th>
									<th class="table-type">评论内容</th>
									<th class="table-type">评论人姓名</th>
									<th class="table-author">评论人手机号</th>
									<th class="table-type">评论时间</th>
									<%--<th class="table-set">操作</th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td>${obj.course_name}</td>
									<td style="width:40%;">${obj.comment}</td>
									<td>${obj.name}</td>
									<td>${obj.mobile}</td>
									<td>${fn:substring(obj.create_at,0,19)}</td>
								</tr>
								<tr>
									<td colspan=6>
										<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
										<a onclick="deleteComment('${obj.id}')" class="glyphicon glyphicon-trash" >
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
	function deleteComment(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/appcomment/deleteOfflineComment?id=" + id, function(result) {
				if (result.status == 200) {
					window.location.href="/admin/appcomment?courseName="+$("#courseName").val()+"&comment="+$("#comment").val();
// 					location.reload();
				} else {
					showInfo("系统错误....",function(){
						//..
					},3000);
				}
			});
		});
	}
	function submitForm() {
		window.location.href="/admin/appcomment?courseName="+$("#courseName").val()+"&comment="+$("#comment").val();
	}
</script>
</html>