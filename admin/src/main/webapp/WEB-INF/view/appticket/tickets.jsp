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

		<!-- 辅助导航栏 -->
		<div class="nav-assist" style="background:#0e90d2">
		<a  class="icon-reply" href="javascript:history.go(-1);" style="float:left;color:#fff;padding:12px 20px"></a>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<div class="minTitle">
				学员考试明细列表
			</div>

			<div class="am-g" style="margin-top: 20px">
				<div class="am-u-sm-11">
					<div class="am-fr">
<!-- 						一级目录： -->
<!-- 						<div class="am-input-group am-input-group-sm"> -->
<!-- 	             			<select id="typeone" style="float: left;width: 315px;height: 43px;"></select> -->
<!-- 					    </div> -->
<!-- 					            二级目录： -->
<!-- 					    <div class="am-input-group am-input-group-sm"> -->
<!-- 					    	<select id="typetwo" style="float: left;width: 315px;height: 43px;"></select> -->
<!-- 					    </div> -->
<!-- 					            分数： -->
						<div class="Go">
							<input type="hidden" id="employeeId" class="text" value="${employeeId }"/>
							<input type="text" placeholder="课程名称" class="text" id="courseName" name="courseName">
							<button class="icon-search" onclick="serch()" type="button" ></button>
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
									<th class="table-title"><center>课程名称</center></th>
									<th class="table-title"><center>试卷名称</center></th>
									<th style="width: 20%;"><center>分数</center></th>
									<th style="width: 20%"><center>考试时间</center></th>
<!-- 									<th class="table-type">状态</th> -->
						
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><center>${obj.course_name}</center></td>
									<td><center>${obj.title}</center></td>
									<td><center>${obj.score}</center></td>
									<td><center>${fn:substring(obj.create_at,0,19)}</center></td>
<!-- 									<td> -->
<%-- 										<c:if test="${obj.status == 2}">已领取</c:if> --%>
<%-- 										<c:if test="${obj.status == 3}">已使用</c:if> --%>
<!-- 									</td> -->
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
	function serch(){
		window.location.href="/admin/appticket/queryEmployeeExamDetail?courseName="+$('#courseName').val()+"&id="+$('#employeeId').val();
	}
</script>
</html>