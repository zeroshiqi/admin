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
				<li><a href="/admin/member">用户管理</a></li>
				<li><a href="/admin/push">推送管理</a></li>
				<li><a href="/admin/image">图片列表</a></li>
				<li><a href="/admin/info">短信列表</a></li>
				<li class="bgColor"><a href="/admin/feedback">意见反馈</a></li>
				<li><a href="/admin/invoice">发票列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">企业版意见反馈</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf">
				<form method="post" id="xlsForm">
					<!-- <button type="button" onclick="edit(0)" class="am-btn am-btn-default">
						<span class="am-icon-plus"></span> 新增
					</button> -->
					<button type="button" onclick="query()" class="add">
						<span class="icon-zoom-in"></span> 查看其它意见反馈
					</button>
				</form>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id" style="width: 5%;">ID</th>
									<th class="table-title" style="width: 40%;">内容</th>
<!-- 									<th class="table-title" style="width: 5%;">用户ID</th> -->
									<th class="table-title" style="width: 10%;">手机号</th>
									<th class="table-type" style="width: 10%;">昵称</th>
									<th class="table-type" style="width: 10%;">发表时间</th>
									<th class="table-type" style="width: 10%;">设备型号</th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td style="width: 5%;">${obj.id}</td>
									<td style="width: 40%;">${obj.content}</td>
<%-- 									<td style="width: 5%;">${obj.employeeId}</td> --%>
									<td style="width: 10%;">${obj.mobile}</td>
									<td style="width: 10%;">${obj.name}</td>
									<td style="width: 10%;">${fn:substring(obj.create_at,0,19)}</td>
									<td style="width: 10%;">${obj.platform}</td>
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
	function query(){
		window.location.href = "/admin/feedback";
	}
</script>
</html>