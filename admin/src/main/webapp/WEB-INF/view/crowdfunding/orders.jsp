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
					<strong class="am-text-primary am-text-lg">众筹失败订单列表</strong> / <small>Table</small>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<!-- <div class="am-btn-group am-btn-group-xs">
								<button type="button" onclick="edit(0)" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button>
							</div> -->
						</div>
					</div>
				</div>
				<div class="am-u-md-3 am-cf">
					<div class="am-fr">
						<!-- <div class="am-input-group am-input-group-sm">
							<input type="text" class="am-form-field" id="search" name="mobile"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-default" onclick="submitForm()" type="button">搜索</button>
							</span>
						</div> -->
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
									<th class="table-type">金额</th>
									<th class="table-author">时间</th>
									<th class="table-author">支付类型</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.code}</td>
									<td>${obj.price}</td>
									<td>${obj.createAt}</td>
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
</script>
</html>