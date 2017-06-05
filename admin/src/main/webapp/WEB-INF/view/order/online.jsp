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
				<li class="bgColor"><a href="/admin/order/online">录音课程订单</a></li>
				<li><a href="/admin/order">Web线下课程订单</a></li>
				<li><a href="/admin/order/onlineNew">Web线上课程订单</a></li>
				<li><a href="/admin/crowdfunding/crowd">众筹列表</a></li>
				<li><a href="/admin/ordercount">订单统计</a></li>
				<li><a href="/admin/complex">复购率报表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">订单信息</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf">
				<div class="Go">
					<input type="text" class="text" id="search" name="search">
					<button class="icon-search" onclick="submitForm()" type="button"></button>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-type">课程名称</th>
									<th class="table-type">订单号</th>
									<th class="table-type">微信</th>
									<th class="table-type">支付金额</th>
									<th class="table-type">购买时间</th>
									<th class="table-type">订单来源</th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.course_name}</td>
									<td><input readonly="true" style="width:49px;;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;" title="${obj.code}" value="${obj.code}"/></td>
									<td>${obj.weixin}</td>
									<td>${obj.price}</td>
									<td>${fn:substring(obj.update_at,0,19)}</td>
									<td>
										<c:if test="${obj.at == 0}">
											WEB
										</c:if>
										<c:if test="${obj.at == 1}">
											APP
										</c:if>
									</td>
								</tr>
							</tbody>
							</c:forEach>
						</table>
						<div class="tfoot">
							共 ${page.count} 条记录
							<div class="am-fr  am-fr-page">
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
	if ($("#search").val() != '') {
		window.location.href = "/admin/order/online?name="
				+ $("#search").val();
	}
}
</script>
</html>