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

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg"><c:if test="${type==1}">认可${member.nick_name}的人</c:if><c:if test="${type==0}">${member.nick_name}认可的人</c:if></strong> / <small>Table</small>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-g">
			<form method="post" id="xlsForm" action="/admin/member/saveMembers" enctype="multipart/form-data">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<!-- <button type="button" onclick="edit(0)" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button> -->
							</div>
						</div>
					</div>
				</div>
				</form>
				<div class="am-u-md-3 am-cf">
					<div class="am-fr">
						<div class="am-input-group am-input-group-sm">
							<!-- <input type="text" class="am-form-field" id="search" name="mobile"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-default" onclick="submitForm()" type="button">搜索</button>
							</span> -->
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
									<th class="table-title">手机号</th>
									<th class="table-type">昵称</th>
									<th class="table-author">性别</th>
									<th class="table-type">职位</th>
									<th class="table-type">登录次数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.memberId}</td>
									<td>${obj.mobile}</td>
									<td><a href="/admin/member/info?id=${obj.memberId}">${obj.nick_name}</a></td>
									<td>${obj.gender}</td>
									<td>${obj.job_name}</td>
									<td>${obj.login_number}</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<%-- <a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="edit('${obj.memberId}');">
													<span class="am-icon-pencil-square-o" ></span> 编辑
												</a>
												<a onclick="deleteMember('${obj.memberId}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
													<span class="am-icon-trash-o"></span> 删除
												</a> --%>
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
</html>