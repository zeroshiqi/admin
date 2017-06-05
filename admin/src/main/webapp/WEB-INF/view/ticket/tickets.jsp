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
		<div class="nav-assist">
			<ul>
				<li class="bgColor"><a href="/admin/ticket">成绩列表</a></li>
				<li><a href="/admin/question">问题列表</a></li>
				<li><a href="/admin/typeone">问题一级目录列表</a></li>
				<li><a href="/admin/typetwo">问题二级目录列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<div class="am-u-md-3 am-cf">
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
					<input type="text" placeholder="一级目录名称" class="text" id="topParentName" name="topParentName">
					<input type="text" placeholder="二级目录名称" class="text" id="parentName" name="parentName">
					<input type="text" placeholder="分数大于等于" class="text" id="score" name="score">
					<button class="icon-search" onclick="serch()" type="button" ></button>
				</div>
			</div>
			

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-title"><center>一级目录</center></th>
									<th class="table-title"><center>二级目录</center></th>
									<th style="width: 20%;"><center>姓名</center></th>
									<th style="width: 20%"><center>分数</center></th>
<!-- 									<th class="table-type">状态</th> -->
						
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><center>${obj.typeone}</center></td>
									<td><center>${obj.typetwo}</center></td>
									<td><center>${obj.nick_name}</center></td>
									<td><center>${obj.score}</center></td>
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
// 	$(function(){
// 		$("#addTypeone").bind("click",function(){
// 			var text = $("#course-typeone").val();
// 			var ids = $("#id").val();
// 			if($("#typeone option:selected").val() == ''){
// 				return;
// 			}
// 			$("#course-hidden-typeone").val($("#typeone option:selected").val());
// 			$("#course-typeone").val($("#typeone option:selected").text());
// 		});
// 		$("#addTypeone").bind("click",function(){
// 			var text = $("#course-typeone").val();
// 			var ids = $("#id").val();
// 			if($("#typeone option:selected").val() == ''){
// 				return;
// 			}
// 			$("#course-hidden-typeone").val($("#typeone option:selected").val());
// 			$("#course-typeone").val($("#typeone option:selected").text());
// 		});
// 		add();
// 		add1();
// 	});

	function serch(){
		window.location.href="/admin/ticket?score="+$("#score").val()+"&topParentName="+$("#topParentName").val()+"&parentName="+$("#parentName").val();
	}
</script>
</html>