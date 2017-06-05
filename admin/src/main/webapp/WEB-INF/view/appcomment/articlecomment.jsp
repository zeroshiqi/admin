<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<meta name="keywords" content="table">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
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
				<li class="bgColor"><a href="/admin/coursetype/typetwo">课程二级目录</a></li>
				<li><a href="/admin/appcomment">企业课程评论管理</a></li>
				<li><a href="/admin/appticket">好多课课程试卷成绩列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<div class="am-g">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<!-- <button type="button" onclick="edit(0)" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button> -->
							</div>
						</div>
					</div>
				</div>
				<div class="am-u-md-3 am-cf">
					<div class="am-fr">
						<div class="am-input-group am-input-group-sm">
							<!-- <input type="text" class="am-form-field" id="search" name="mobile"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-default" onclick="submitForm()" type="button">搜索</button>
							</span> -->
						</div>
					</div>
				</div>
			</div>

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-title">内容</th>
									<!-- <th class="table-type">是否匿名</th> -->
									<th class="table-author">用户ID</th>
									<th class="table-author">姓名</th>
									<th class="table-type">评论时间</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>

							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td>${obj.content}</td>
									<%-- <td>
										<c:if test="${obj.anonymous == 0}">不匿名</c:if>
										<c:if test="${obj.anonymous == 1}">匿名</c:if>
									</td> --%>
									<td>${obj.member_id}</td>
									<td>${obj.nick_name}</td>
									<td>${obj.create_at}</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a onclick="deleteComment('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
													<span class="am-icon-trash-o"></span> 删除
												</a>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
							</c:forEach>
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
	function deleteComment(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/comment/deleteArticleComment?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){
						//..
					},3000);
				}
			});
		});
	}
</script>
</html>