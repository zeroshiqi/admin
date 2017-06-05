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
				<li <c:if test="${newtype == 0}">class="bgColor"</c:if>><a href="/admin/course/offline">线下课程管理</a></li>
				<li <c:if test="${newtype == 1}">class="bgColor"</c:if>><a href="/admin/course/onlineNew">线上课程管理</a></li>
				<li <c:if test="${newtype == 5}">class="bgColor"</c:if>><a href="/admin/course/publicClass">公开课管理</a></li>
				<li class="now"><a href="/admin/course/online">录音课程管理</a></li>
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
						<input type="text" class="text" id="search"name="search">
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
									<th class="table-author">封面</th>
									<th class="table-type">上课时间</th>
									<th class="table-type">价格</th>
									<th class="table-type">评分</th>
									<th class="table-type">评论</th>
									<th class="table-type">报名</th>
									<!--<th class="table-set">操作</th>-->
								</tr>
							</thead>

							<c:forEach var="obj" items="${list}">
								<tbody>
									<tr>
										<td>${obj.courseId}</td>
										<td>${obj.course_name}</td>
										<td><img src="${obj.cover}" width="100px" height="40px" /></td>
										<td>${obj.course_time}</td>
										<td>${obj.price}</td>
										<td>${obj.star}</td>
										<td><a  class="aYes" href="/admin/comment/offlineComment?courseId=${obj.courseId}">${obj.comment_count}</a></td>
										<td><a  class="aYes" href="/admin/course/joinMembers?courseId=${obj.courseId}&type=${newtype}">${obj.joinNumber}</a></td>
									</tr>

									<tr>
										<td colspan=8>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="toEmail('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 发送结业证书邮件
													</a>
													<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="sellOrNot('${obj.courseId}');">
														<span class="am-icon-pencil-square-o" ></span> <c:if test="${obj.is_sell == 0}">关闭报名</c:if><c:if test="${obj.is_sell == 1}">开启报名</c:if>
													</a>
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
													<a class="am-btn am-btn-default am-btn-xs am-text-secondary"
															onclick="showOrHidden('${obj.courseId}');"> <span
															class="am-icon-pencil-square-o"></span> <c:if
															test="${obj.is_hidden == 0}">APP隐藏</c:if>
														<c:if test="${obj.is_hidden == 1}">APP显示</c:if>
													</a>
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
															onclick="showOrHidden2('${obj.courseId}');"> <span
															class="am-icon-pencil-square-o"></span> <c:if
															test="${obj.web_hidden == 0}">WEB隐藏</c:if>
														<c:if test="${obj.web_hidden == 1}">WEB显示</c:if>
													</a>
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
															onclick="showOrHidden3('${obj.courseId}');"> <span
															class="am-icon-pencil-square-o"></span> <c:if
															test="${obj.new_web_hidden == 0}">官网隐藏</c:if>
														<c:if test="${obj.new_web_hidden == 1}">官网显示</c:if>
													</a>
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
															onclick="crowd('${obj.courseId}');"> <span
															class="am-icon-pencil-square-o"></span> <c:if
															test="${obj.is_crowd==1}">关闭众筹</c:if>
														<c:if test="${obj.is_crowd==0}">开启众筹</c:if>
													</a>
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="copy('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 获得URL
													</a>
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="cover('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 封面
													</a>
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="toImages('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span> 图片
													</a>
													<a onclick="createAccount('${obj.courseId}','${obj.course_name}')"
													   class="am-btn am-btn-default am-btn-xs am-text-secondary">
														<span class="am-icon-pencil-square-o"></span> 批量创建好多课账号
													</a>
													<a	class="glyphicon glyphicon-edit"
														onclick="edit('${obj.courseId}');"> <span
														class="am-icon-pencil-square-o"></span>
													</a>
													<a onclick="deleteOffline('${obj.courseId}')"
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
		if ($("#search").val() != '') {
			<c:if test="${newtype == 0}">
			window.location.href = "/admin/course/offline?name="
				+ $("#search").val();
			</c:if>
			<c:if test="${newtype == 1}">
			window.location.href = "/admin/course/onlineNew?name="
				+ $("#search").val();
			</c:if>
			<c:if test="${newtype == 5}">
			window.location.href = "/admin/course/publicClass?name="
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
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/hiddenOrShow?courseId=" + courseId, function(
					result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function showOrHidden2(courseId) {
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/webHidden?courseId=" + courseId, function(
					result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function showOrHidden3(courseId) {
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/webShow?courseId=" + courseId, function(
					result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function sellOrNot(courseId){
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/sellOrNot?courseId=" + courseId, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){},3000);
				}
			});
		});
	} 
	function crowd(courseId) {
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/crow?courseId=" + courseId, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function sendEmail(courseId) {
		showConfirm("确认发送结业证书邮件吗?", function() {
			$.getJSON("/admin/course/crow?courseId=" + courseId, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function isnew(id){
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/isnew?courseId=" + id, function(
					result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function isfull(id){
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/updateFullStatus?id=" + id, function(
					result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
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
// 		var d = dialog({
// 			    title: '警告⚠️',
// 			    content: '确定审核吗？',
// 			    okValue: '确定',
// 			    ok: function () {
// 			    //删除操作
// 			        return false;//阻止dialog关闭
// 			    },
// 			    background:'#000000',
// 			    cancelValue: '取消',
// 			    cancel: function () {}
// 			});
// 			d.show();
	}
	function createAccount(id,name){
		$.getJSON("/admin/course/BatchBuildBusinessAppAccount?courseId="+id+"&courseName="+name, function(
				result) {
			if (result.status == 200) {
	            showInfo(result.msg,function(){},3000);
			} else {
				showInfo("系统错误....", function() {
				}, 3000);
			}
		});
	}
	function toEmail(id){
		showConfirm("确认发送结业证书邮件吗?", function() {
			$.getJSON("/admin/order/toEmail?courseId="+id, function(
					result) {
				if (result.status == 200) {
		            showInfo(result.msg,function(){},3000);
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		})
	}
</script>
</html>