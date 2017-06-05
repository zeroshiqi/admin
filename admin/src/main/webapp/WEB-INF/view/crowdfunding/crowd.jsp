<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
				<li><a href="/admin/order/online">录音课程订单</a></li>
				<li><a href="/admin/order">Web线下课程订单</a></li>
				<li><a href="/admin/order/onlineNew">Web线上课程订单</a></li>
				<li class="bgColor"><a href="/admin/crowdfunding/crowd">众筹列表</a></li>
				<li><a href="/admin/ordercount">订单统计</a></li>
				<li><a href="/admin/complex">复购率报表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">众筹列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>

			<div class="am-u-md-3 am-cf">
				<div class="search">
					<!-- <div class="am-btn-group am-btn-group-xs">
					<button type="button" onclick="edit(0)" class="am-btn am-btn-default">
					<span class="am-icon-plus"></span> 新增
					</button>
					</div> -->
					<div class="Go">
						<input type="text" class="text" id="search" name="search" placeholder="查询姓名" value="${name}">
						<select name="select" id="searchSelect" class="am-form-field">
							<option value="0" <c:if test="${select eq '0'}">selected="selected"</c:if>>查询全部</option>
							<option value="1" <c:if test="${select eq '1'}">selected="selected"</c:if>>未退款</option>
							<option value="2" <c:if test="${select eq '2'}">selected="selected"</c:if>>众筹成功</option>
						</select>
						<button class="icon-search" onclick="submitForm()" type="button"></button>
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
									<th class="table-title">课程名称</th>
									<th class="table-type">众筹金额</th>
									<th class="table-author">发起人</th>
									<th class="table-author">众筹时间</th>
									<th class="table-author">筹集金额</th>
									<th class="table-author">支付金额</th>
									<th class="table-author">微信号</th>
									<th class="table-author">手机号</th>
									<th class="table-author">状态</th>
									<th class="table-author">操作</th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td>${obj.course_name}</td>
									<td>${obj.price}</td>
									<td><a href="/admin/crowdfunding/info?cid=${obj.id}">${obj.nickname}</a></td>
									<td>${fn:substring(obj.create_at,0,19)}</td>
									<td>${obj.sumPrice}</td>
									<td>
										<c:if test="${obj.status == 1}">0</c:if>
										<c:if test="${obj.status == 2}">${obj.price - obj.sumPrice}</c:if>
										<c:if test="${obj.status == 3}">0</c:if>
									</td>	
									<td>
										${obj.weixin}
									</td>
									<td>${obj.phone}</td>
									<td>
										<c:if test="${obj.status == 1}">正在众筹</c:if>
										<c:if test="${obj.status == 2}">众筹成功</c:if>
										<c:if test="${obj.status == 3}">众筹失败</c:if>
									</td>	
									
									<td>
										<c:if test="${obj.status == 3 && obj.refund == 1}">已退款</c:if>
										<c:if test="${obj.status == 1 || obj.status == 2}">无法退款</c:if>
										<c:if test="${obj.status == 3 && obj.refund == 0}">
										<a onclick="updateStatus('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
											<span class="am-icon-trash-o"></span> 退款
										</a>
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
function updateStatus(id){
	showConfirm("确认已退款?",function(){
		$.getJSON("/admin/crowdfunding/updateStatus?id=" + id, function(result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....",function(){},3000);
			}
		});
	});
}
function submitForm(){
	window.location.href="/admin/crowdfunding/crowd?select="+$("#searchSelect").val()+"&name="+$("#search").val();
}
</script>
</html>