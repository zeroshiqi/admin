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

		<!-- content start -->
		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg"><c:if
							test="${type == 0}">线下课程报名用户</c:if>
						<c:if test="${type == 1}">线上课程在线用户</c:if></strong> / <small>Table</small>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-md-3 am-cf">
					<div class="am-fr">
						<div class="am-input-group am-input-group-sm">
							<div class="am-u-md-6 am-cf">
								<div class="am-fl am-cf">
									<div class="am-btn-toolbar am-fl">
										<div class="am-btn-group am-btn-group-xs">
											<button type="button" id="exportFile" onclick="exportFile()"
												class="am-btn am-btn-default">
												<span class="am-icon-plus"></span> 导出
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="am-u-md-3 am-cf">
					<div class="am-fr">
						<div class="am-input-group am-input-group-sm">
							<input type="text" class="am-form-field" id="search"
								name="search"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-default" onclick="submitForm()"
									type="button">搜索</button>
							</span>
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
									<th class="table-type">头像</th>
									<th class="table-title">手机号</th>
									<th class="table-type">昵称</th>
									<th class="table-author">性别</th>
									<th class="table-type">职位</th>
										<th class="table-type">报名方式</th>
										<th class="table-type">支付方式</th>
										<th class="table-type">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
									<tr>
										<td>${obj.memberId}</td>
										<td><img src="${obj.avatar}" width="100px" height="100px"></td>
										<td>${obj.mobile}</td>
										<td><a href="/admin/member/info?id=${obj.memberId}">${obj.nick_name}</a></td>
										<td>${obj.gender}</td>
										<td>${obj.job_name}</td>
										<c:if test="${type == 1}">
											<c:if test="${obj.status == 1}">
												<td>在房间内</td>
											</c:if>
											<c:if test="${obj.status == 0}">
												<td>不在房间内</td>
											</c:if>
										</c:if>
										<c:if test="${type == 0}">
											<td><c:if test="${obj.type == 0}">线下报名</c:if> <c:if
													test="${obj.type == 1}">APP报名</c:if> <c:if
													test="${obj.type == 2}">WEB报名</c:if></td>
											<td><c:if test="${obj.from1 == -1}">旧数据</c:if> <c:if
													test="${obj.from1 == 0}">众筹</c:if> <c:if
													test="${obj.from1 == 1}">百度支付</c:if> <c:if
													test="${obj.from1 == 2}">微信支付</c:if> <c:if
													test="${obj.from1 == 3}">支付宝支付</c:if></td>
											<td>
												<div class="am-btn-toolbar">
													<div class="am-btn-group am-btn-group-xs">
														<a onclick="deleteJoin('${obj.joinId}')"
															class="am-btn am-btn-default am-btn-xs am-text-danger">
															<span class="am-icon-trash-o"></span> 删除
														</a>
													</div>
												</div>
											</td>
										</c:if>
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
	function exportFile() {
		window.location.href = "/admin/course/export?id=${courseId}";
	}
	function submitForm() {
		if ($("#search").val() != '') {
			'joinMembers?courseId=150&type=0';
			window.location.href = "/admin/course/joinMembers?courseId=${courseId}&type=${type}&name="
					+ $("#search").val();
		}
	}
	function deleteJoin(id) {
		showConfirm("确认删除?", function() {
			$.getJSON("/admin/course/deleteJoin?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
						//..
					}, 3000);
				}
			});
		});
	}
</script>
</html>