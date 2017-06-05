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
				<li><a href="/admin/company">企业管理</a></li>
				<li><a href="/admin/keywords">搜索关键字管理</a></li>
				<li><a href="/admin/catalog">课程一级分类管理</a></li>
				<li><a href="/admin/catalogtwo">课程二级分类管理</a></li>
				<li><a href="/admin/coursetype">课程一级目录</a></li>
				<li><a href="/admin/coursetype/typetwo">课程二级目录</a></li>
				<li><a href="/admin/appcomment">企业课程评论管理</a></li>
				<li class="bgColor"><a href="/admin/appticket">好多课课程试卷成绩列表</a></li>
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
					<input type="text" placeholder="课程名称" class="text" id="courseName" name="courseName">
					<input type="text" placeholder="试卷名称" class="text" id="titleName" name="titleName">
					<button class="icon-search" onclick="serch()" type="button" ></button>
				</div>
			</div>
			

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th style="width: 10%; class="table-id">ID</th>
									<th style="width: 30%; class="table-title"><center>课程名称</center></th>
									<th style="width: 30%; class="table-title"><center>试卷名称</center></th>
									<th style="width: 10%;"><center>考试题数</center></th>
									<th style="width: 10%;"><center>参考人数</center></th>
									<th style="width: 10%"><center>参考次数</center></th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><center>${obj.course_name}</center></td>
									<td><center>${obj.title}</center></td>
									<td><center>${obj.number}</center></td>
									<td><center><a  class="aYes"  href="/admin/appticket/queryEmployee?id=${obj.id}">${obj.employee_count}</a></center></td>
									<td><center>${obj.exam_count}</center></td>
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
		window.location.href="/admin/appticket?courseName="+$("#courseName").val()+"&title="+$('#titleName').val();
	}
</script>
</html>