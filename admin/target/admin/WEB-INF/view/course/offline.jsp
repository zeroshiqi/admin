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
<LINK media=screen href="/admin/alert/jquery.alerts.css" 
type=text/css rel=stylesheet><!-- 示例代码 -->


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
					<c:if test="${newtype == 0}"><strong class="am-text-primary am-text-lg">线下课程列表</strong> / <small>Table</small></c:if>
					<c:if test="${newtype == 1}"><strong class="am-text-primary am-text-lg">线上课程列表</strong> / <small>Table</small></c:if>
					
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button" onclick="edit(0)"
									class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button>
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
									<th class="table-title">课程名称</th>
									<th class="table-author">封面</th>
									<th class="table-type">上课时间</th>
									<th class="table-type">价格</th>
									<th class="table-type">评分</th>
									<th class="table-type">评论</th>
									<th class="table-type">报名</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
									<tr>
										<td>${obj.courseId}</td>
										<td>${obj.course_name}</td>
										<td><img src="${obj.cover}" width="100px" height="40px" /></td>
										<td>${obj.course_time}</td>
										<td>${obj.price}</td>
										<td>${obj.star}</td>
										<td><a
											href="/admin/comment/offlineComment?courseId=${obj.courseId}">${obj.comment_count}</a></td>
										<td><a
											href="/admin/course/joinMembers?courseId=${obj.courseId}&type=${newtype}">${obj.joinNumber}</a></td>
										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<a onclick="isfull('${obj.courseId}')"
														class="am-btn am-btn-default am-btn-xs am-text-secondary">
														<span class="am-icon-trash-o"></span><c:if test="${obj.isfull == 0}">招满</c:if>
														<c:if test="${obj.isfull == 1}">未招满</c:if>
													</a>
													<a onclick="isnew('${obj.courseId}')"
														class="am-btn am-btn-default am-btn-xs am-text-secondary">
														<span class="am-icon-trash-o"></span><c:if test="${obj.isnew == 0}">标新</c:if>
														<c:if test="${obj.isnew == 1}">不标新</c:if>
													</a>
													<a
														class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="showOrHidden('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> <c:if
															test="${obj.is_hidden == 0}">APP隐藏</c:if>
														<c:if test="${obj.is_hidden == 1}">APP显示</c:if>
													</a>
													<a
														class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="showOrHidden2('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> <c:if
															test="${obj.web_hidden == 0}">WEB隐藏</c:if>
														<c:if test="${obj.web_hidden == 1}">WEB显示</c:if>
													</a> 
													 <a
														class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="crowd('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> <c:if
															test="${obj.is_crowd==1}">关闭众筹</c:if>
														<c:if test="${obj.is_crowd==0}">开启众筹</c:if>
													</a> <a
														class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="copy('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 获得URL
													</a> <a
														class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="cover('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 封面
													</a> <a
														class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="toImages('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 图片
													</a> <a
														class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="edit('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 编辑
													</a> <a onclick="deleteOffline('${obj.courseId}')"
														class="am-btn am-btn-default am-btn-xs am-text-danger">
														<span class="am-icon-trash-o"></span> 删除
													</a>
												</div>
											</div>
										</td>
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
	function submitForm() {
		if ($("#search").val() != '') {
			<c:if test="${newtype == 0}">
			window.location.href = "/admin/course/offline?name="
				+ $("#search").val();
			</c:if>
			<c:if test="${newtype == 1}">
			window.location.href = "/admin/course/onlineNew?name="
				+ $("#search").val();
			</c:if>
		}
	}
	function edit(id) {
		if (id == 0) {
			window.location.href = "/admin/course/edit?newtype=${newtype}&type=0";
		} else {
			window.location.href = "/admin/course/edit?newtype=${newtype}&id=" + id;
		}
	}
	function deleteOffline(id) {
		showConfirm("确认删除?", function() {
			$.getJSON("/admin/course/deleteOffline?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function showOrHidden(courseId) {
		$.getJSON("/admin/course/hiddenOrShow?courseId=" + courseId, function(
				result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....", function() {
				}, 3000);
			}
		});
	}
	function showOrHidden2(courseId) {
		$.getJSON("/admin/course/webHidden?courseId=" + courseId, function(
				result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....", function() {
				}, 3000);
			}
		});
	}
	function crowd(courseId) {
		$.getJSON("/admin/course/crow?courseId=" + courseId, function(result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....", function() {
				}, 3000);
			}
		});
	}
	function isnew(id){
		$.getJSON("/admin/course/isnew?courseId=" + id, function(
				result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....", function() {
				}, 3000);
			}
		});
	}
	function isfull(id){
		$.getJSON("/admin/course/updateFullStatus?id=" + id, function(
				result) {
			if (result.status == 200) {
				location.reload();
			} else {
				showInfo("系统错误....", function() {
				}, 3000);
			}
		});
	}
	function cover(id) {
		window.location.href = "/admin/course/cropImage?newtype=${newtype}&id=" + id;
	}
	function toImages(id) {
		window.location.href = "/admin/course/images?newtype=${newtype}&id=" + id;
	}
	function copy(id) {
		var temp = "${url}";
		jPrompt('URL:', temp.replace("#courseId#",id), '带输入框的提示框', function(r) {
		   
		});
	}
</script>
</html>