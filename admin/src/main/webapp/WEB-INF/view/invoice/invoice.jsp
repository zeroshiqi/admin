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
				<li><a href="/admin/feedback">意见反馈</a></li>
				<li class="bgColor"><a href="/admin/invoice">发票列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">发票信息列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<form method="post" id="xlsForm">
				<div class="am-u-md-3 am-cf">
					<div class="Go">
						<input type="text" placeholder="课程名称" class="text" id="courseName" name="courseName" value="${courseName }"/>
						<input type="text" placeholder="学员姓名" class="text" id="nickName" name="nickName"  value="${nickName }"/>
						<input type="text" placeholder="发票抬头" class="text" id="invoiceTitle" name="invoiceTitle"  value="${invoiceTitle }"/>
						<button class="icon-search" onclick="submitForm()" type="button"></button>
						<button type="button" id="exportFile" onclick="exportFile3()"
							class="add">
							<span class="icon-plus"></span> 导出
						</button>
					</div>
				</div>
			</form>

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id" style="width: 8%;"><center>发票收货人</center></th>
									<th class="table-title" style="width: 5%;"><center>收货人手机号</center></th>
									<th class="table-title" style="width: 5%;"><center>金额</center></th>
									<th class="table-title" style="width: 10%;"><center>发票类型</center></th>
									<th class="table-title" style="width: 15%;"><center>发票抬头</center></th>
									<th class="table-title" style="width: 20%;"><center>发票寄送地址</center></th>
									<th class="table-type"style="width: 15%;"><center>备注</center></th>
									<th class="table-type"style="width: 17%;"><center>开课时间</center></th>
									<th class="table-type"style="width: 5%;"><center>发票状态</center></th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td><center>${obj.invoice_name}</center></td>
									<td><center>${obj.invoice_mobile}</center></td>
									<td><center>${obj.price}</center></td>
									<td><center>${obj.invoice_type}</center></td>
									<td><center>${obj.invoice_title}</center></td>
									<td><center>${obj.invoice_address}</center></td>
									<td><center>${obj.invoice_remarks}</center></td>
									<td><center>${fn:substring(obj.begin_time,0,19)}</center></td>
									<td>
										<c:if test="${obj.invoice_status eq 1}"><a style="font-weight: bold;font-size: 20px;" href="/admin/invoice/dispose?id=${obj.id}&courseName=${courseName }&nickName=${nickName}&invoiceTitle=${invoiceTitle }"><font style="color: red;font-size: 15px;"><center>未处理</center></font></a></c:if>
										<c:if test="${obj.invoice_status eq 2}"><center>已处理</center></c:if>
									</td>
<%-- 									<td>${fn:substring(obj.create_at,0,19)}</td> --%>
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
	function submitForm() {
		window.location.href="/admin/invoice?courseName="+$("#courseName").val()+"&nickName="+$("#nickName").val()+"&invoiceTitle="+$("#invoiceTitle").val();
	}
	function exportFile3() {
		window.location.href = "/admin/invoice/export?courseName="+$("#courseName").val()+"&nickName="+$("#nickName").val()+"&invoiceTitle="+$("#invoiceTitle").val();
	}
</script>
</html>