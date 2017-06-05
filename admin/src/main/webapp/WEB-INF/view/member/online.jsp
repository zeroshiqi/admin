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
					<strong class="am-text-primary am-text-lg">${member.nick_name}参加的线上课程</strong> / <small>Table</small>
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
<!-- 									<th class="table-author">封面</th> -->
<!-- 									<th class="table-type">上课时间</th> -->
									<th class="table-type">姓名</th>
									<th class="table-type">价格</th>
									<th class="table-type">购买数量</th>
									<th class="table-type">购买渠道</th>
									<th class="table-type">购买时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.id}</td>
									<td>${obj.course_name}</td>
<%-- 									<td>${obj.address}</td> --%>
<%-- 									<td><img src="${obj.cover}" width="100px"  height="40px"/></td> --%>
<%-- 									<td>${obj.course_time}</td> --%>
									<td>${obj.name}</td>
									<td>${obj.price}</td>
									<td>${obj.number}</td>
									<c:if test="${obj.web eq 0}">
										<td>Web购买</td>
									</c:if>
									<c:if test="${obj.web eq 1}">
										<td>App购买</td>
									</c:if>
<%-- 									<td><a href="/admin/comment/offlineComment?courseId=${obj.courseId}">${obj.comment_count}</a></td> --%>
									<td>${fn:substring(obj.update_at,0,19)}</td>
								</tr>
								</c:forEach>
							</tbody>
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