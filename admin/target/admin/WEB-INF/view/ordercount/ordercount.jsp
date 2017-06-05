<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<%@include file="/base/link.jsp"%>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
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
					<strong class="am-text-primary am-text-lg">课程订单信息</strong> / <small>Table</small>
				</div>
			</div>
			<form method="post" id="xlsForm">>
				<div class="am-g">
					<div class="am-u-md-3 am-cf">
						<div class="am-fr">
							<div class="am-input-group am-input-group-sm">
							
							<input type="text" placeholder="开始时间" class="am-form-field" id="startTime" name="startTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm'})"> 
							<input type="text" placeholder="结束时间" class="am-form-field" id="endTime" name="endTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm'})"> 
								
									<span class="am-input-group-btn">
									<button class="am-btn am-btn-default" onclick="submitForm()" type="button">搜索</button>
								</span> 
								
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title">课程id</th>
									<th class="table-title">课程名称</th>
									<th class="table-title">订单号</th>
									<th class="table-type">价格</th>
									<th class="table-type">购买时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td>${obj.id}</td>
									<td>${obj.course_name}</td>
									<td>${obj.CODE}</td>
									<td>${obj.price}</td>
									<td>${obj.create_at}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<table>
							<tbody>
								<tr>
									<td><strong>1</strong></td>
									<td><strong>1</strong></td>
									<td><strong>1</strong></td>
									<td><strong>1</strong></td>
									<td><strong>1</strong></td>
								</tr>
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
	$('#xlsForm').ajaxSubmit({
			type:'post',
			url:"/admin/ordercount/",
		});
}
</script>
</html>