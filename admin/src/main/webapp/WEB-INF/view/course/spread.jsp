<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<meta name="keywords" content="table">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%@include file="/base/link.jsp"%>
<SCRIPT src="/admin/alert/m.js" type=text/javascript></SCRIPT>
<SCRIPT src="/admin/alert/jquery.ui.draggable.js"
type=text/javascript></SCRIPT>
<!-- 对话框核心JS文件和对应的CSS文件-->
<SCRIPT src="/admin/alert/jquery.alerts.js"
type=text/javascript></SCRIPT>
<SCRIPT src="/admin/alert/dialog-min.js"
type=text/javascript></SCRIPT>
<SCRIPT src="/admin/alert/dialog-plus.js"
type=text/javascript></SCRIPT>
<LINK media=screen href="/admin/alert/jquery.alerts.css"
type=text/css rel=stylesheet><!-- 示例代码 -->


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
				<li><a href="/admin/course/offline">线下课程管理</a></li>
				<li><a href="/admin/course/onlineNew">线上课程管理</a></li>
				<li><a href="/admin/course/living">直播课程管理</a></li>
				<li class="now"><a href="/admin/course/online">录音课程管理</a></li>
				<li class="bgColor"><a href="/admin/course/spread">推广渠道管理</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<c:if test="${newtype == 0}"><strong class="am-text-primary am-text-lg">线下课程列表</strong></c:if>--%>
					<%--<c:if test="${newtype == 1}"><strong class="am-text-primary am-text-lg">线上课程列表</strong></c:if>--%>

				<%--</div>--%>
			<%--</div>--%>

			<div class="am-u-md-3 am-cf">
					<button type="button" onclick="edit(0)"	class="add">
						<span class="icon-plus"></span>  新增
					</button>

					<div class="Go">
						<input type="text" class="text" id="search" name="search" placeholder="课程名称"/ >
						<input type="text" class="text" id="from1" name="from1" placeholder="推广码" />
						<button class="icon-search" onclick="submitForm()" type="button"></button>
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
									<th class="table-author">推广码</th>
<!-- 									<th class="table-type">创建时间</th> -->
									<th class="table-type">推广方</th>
									<th class="table-set">合作方式</th>
								</tr>
							</thead>

							<c:forEach var="obj" items="${list}">
								<tbody>
									<tr>
										<td>${obj.id}</td>
										<td>${obj.course_name}</td>
										<td>${obj.from1}</td>
										<td>${obj.promotion_party}</td>
										<td>${obj.cooperation_mode}</td>
<%-- 										<td>${obj.star}</td> --%>
<%-- 										<td><a  class="aYes" href="/admin/comment/offlineComment?courseId=${obj.courseId}">${obj.comment_count}</a></td> --%>
<%-- 										<td><a  class="aYes" href="/admin/course/joinMembers?courseId=${obj.courseId}&type=${newtype}">${obj.joinNumber}</a></td> --%>
									</tr>

									<tr>
										<td colspan=8>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<a	class="glyphicon glyphicon-edit"
														onclick="edit('${obj.id}');"> <span
														class="am-icon-pencil-square-o"></span>
													</a>
													<a onclick="deleteSpread('${obj.id}')"
														class="glyphicon glyphicon-trash">
													<span class="am-icon-trash-o"></span>
													</a>
												</div>
											</div>
										</td>
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
	function bgColor(i){
		$(".nav-assist li").eq(i).addClass("bgColor").siblings().removeAttr("class");
	}
	function submitForm() {
		if ($("#search").val() != ''|| $("#from1").val() != '') {
			window.location.href = "/admin/course/spread?name="+ $("#search").val()+"&from1="+$("#from1").val();
		}
	}
	function edit(id) {
		if (id == 0) {
			window.location.href = "/admin/course/editSpread";
		} else {
			window.location.href = "/admin/course/editSpread?id="+id;
		}
	}
	function deleteSpread(id) {
		showConfirm("确认删除?", function() {
			$.getJSON("/admin/course/deleteSpread?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
</script>
</html>