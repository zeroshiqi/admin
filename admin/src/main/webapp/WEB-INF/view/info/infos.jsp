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
				<li class="bgColor"><a href="/admin/info">短信列表</a></li>
				<li><a href="/admin/feedback">意见反馈</a></li>
				<li><a href="/admin/invoice">发票列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">短信列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf">
				<form method="post" id="xlsForm">
					<button type="button" onclick="add()" class="add">
						<span class="icon-plus"></span>  新增
					</button>
				</form>
				<div class="Go">
					<input type="text" placeholder="手机号码" class="text" id="mobile" name="mobile">
					<input type="text" placeholder="课程名称" class="text" id="coursename" name="coursename">
					<span class="Go">
						<button type="button" onclick="search()" class="icon-search"></button>
					</span>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id" style="width: 5%;">ID</th>
									<th class="table-title" style="width: 10%;">手机号码</th>
									<th class="table-title" style="width: 30%;"><center>短信内容</center></th>
									<th class="table-title" style="width: 20%;"><center>课程</center></th>
									<th class="table-title" style="width: 10%;"><center>接口调用是否成功</center></th>
									<th class="table-title" style="width: 5%;">错误码</th>
									<th class="table-type" style="width: 10%;">发送时间</th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
<%-- 									<td alt="${obj.alltext}"><span >${obj.text}</span></td> --%>
									<td>${obj.mobile}</td>
									<td style="width: 450px;"><input readonly="true" style="width:450px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;" title="${obj.content}" value="${obj.content}"/></td>
									<td><center>${obj.course_name}</center></td>
									<td>
										<center>
											<c:if test="${obj.success == true}">成功</c:if>
											<c:if test="${obj.success == false}"><font style="color: red;font-weight: bold;">失败</font></c:if>
										</center>
									</td>
									<td>${obj.recode}</td>
									<td>${fn:substring(obj.createAt,0,19)}</td>
<%-- 									<td>${obj.id}</td> --%>
<%-- 									<td alt="${obj.alltext}"><span >${obj.text}</span></td> --%>
<%-- 									<td>${obj.course_name}</td> --%>
<%-- 									<td>${fn:substring(obj.createAt,0,19)}</td> --%>
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
function search(){
	window.location.href="/admin/info?mobile="+$("#mobile").val()+"&coursename="+$("#coursename").val();
}
function add(){
	window.location.href="/admin/info/add";
}
</script>
</html>