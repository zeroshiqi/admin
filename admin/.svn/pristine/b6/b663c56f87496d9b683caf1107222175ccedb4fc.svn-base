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
				<c:if test="${newtype == 0}"><strong class="am-text-primary am-text-lg">线下课程订单信息</strong> / <small>Table</small></c:if>
				<c:if test="${newtype == 1}"><strong class="am-text-primary am-text-lg">线上课程订单信息</strong> / <small>Table</small></c:if>
					
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-md-3 am-cf">
					<div class="am-fr">
						<div class="am-input-group am-input-group-sm">
						
						<input type="text" placeholder="价格" value="${price}" class="am-form-field" id="searchPrice" name="price"> 
						<input type="text" placeholder="课程名称" value="${name}" class="am-form-field" id="search" name="search"> 
							
								<span class="am-input-group-btn">
								<button class="am-btn am-btn-default" onclick="submitForm()"
									type="button">搜索</button>
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
									<th class="table-title">订单号</th>
									<th class="table-title">姓名</th>
									<th class="table-type">购买数量</th>
									<th class="table-type">支付金额</th>
									<th class="table-type">课程名称</th>
									<th class="table-type">购买时间</th>
									<th class="table-type">支付类型</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.code}</td>
									<td><a href="/admin/order/users?courseId=${obj.id}">${obj.names}</a></td>
									<td>${obj.number}</td>
									<td>${obj.price}</td>
									<td>${obj.course_name}</td>
									<td>${obj.update_at}</td>
									<td>
										<c:if test="${obj.type == 0}">微信支付</c:if>
										<c:if test="${obj.type == 1}">百度支付</c:if>
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
function submitForm() {
	<c:if test="${newtype == 0}">
	window.location.href = "/admin/order?name="
		+ $("#search").val()+"&price="+$("#searchPrice").val();
	</c:if>
	<c:if test="${newtype == 1}">
	window.location.href = "/admin/order/onlineNew?name="
		+ $("#search").val()+"&price="+$("#searchPrice").val();
	</c:if>
}
</script>
</html>