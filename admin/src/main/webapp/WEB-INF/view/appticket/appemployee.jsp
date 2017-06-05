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

		<!-- 辅助导航栏 -->
		<div class="nav-assist" style="background:#0e90d2">
		<a  class="icon-reply" href="javascript:history.go(-1);" style="float:left;color:#fff;padding:12px 20px"></a>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">好多课课程答题人列表</strong> / <small>Table</small>--%>
				<%--</div>--%>
			<%--</div>--%>
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
<!-- 						<div class="am-input-group am-input-group-sm"> -->
<!-- 							<input type="text" placeholder="课程名称" style="width:20%;float: left;margin-left: 5px;" class="am-form-field" id="topParentName" name="topParentName">  -->
<!-- 		 					<span class="am-input-group-btn"> -->
<!-- 								<button class="am-btn am-btn-default" onclick="serch()" type="button" >搜索</button> -->
<!-- 							</span>	 -->
<!-- 						</div> -->
					</div>
				</div>
			</div>
			
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th style="width: 10%; class="table-id">学员id</th>
									<th style="width: 30%; class="table-title"><center>学员昵称</center></th>
									<th style="width: 30%; class="table-title"><center>学员手机号</center></th>
									<th style="width: 10%;"><center>答题次数</center></th>
									<th style="width: 10%;"><center>最高分</center></th>
									<th style="width: 10%"><center>最低分</center></th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.employeeId}</td>
									<td><center><a class="aYes" href="/admin/appticket/queryEmployeeExamDetail?id=${obj.employeeId}");">${obj.name}</a></center></td>
									<td><center>${obj.mobile}</center></td>
									<td><center>${obj.ecount}</center></td>
									<td><center>${obj.maxScore}</center></td>
									<td><center>${obj.minScore}</center></td>
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
		window.location.href="/admin/appticket/queryEmployee?courseName="+$("#courseName").val();
	}
</script>
</html>