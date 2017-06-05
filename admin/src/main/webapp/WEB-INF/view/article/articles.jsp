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
				<li <c:if test="${type == 0}">class="bgColor"</c:if>><a href="/admin/article">文章管理(APP)</a></li>
				<li <c:if test="${type == 1}">class="bgColor"</c:if>><a href="/admin/article?type=1">文章管理(网站)</a></li>
				<li><a href="/admin/type">文章类型列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<div class="am-u-md-3 am-cf">
				<button type="button" onclick="edit(0)" class="add">
					<span class="icon-plus"></span> 新增
				</button>
				<div class="Go">
					<input type="text" class="text" id="search" name="mobile">
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
									<th class="table-title">标题</th>
									<th class="table-type">封面</th>
									<th class="table-type">级别</th>
									<th class="table-author">类别</th>
									<th class="table-author">评论数量</th>
									<th class="table-type">点击数</th>
									<%--<th class="table-set">操作</th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td>${obj.title}</td>
									<td><img src="${obj.cover}" width="100px" height="40px"></td>
									<td>
										<c:if test="${obj.level == 1}">初级</c:if>
										<c:if test="${obj.level == 2}">中级</c:if>
										<c:if test="${obj.level == 3}">高级</c:if>
									</td>
									<td>${obj.value}</td>
									<td><a href="/admin/comment/articleComment?articleId=${obj.id}">${obj.commentCount}</a></td>
									<td>${obj.click_number}</td>
								</tr>
								<tr>
									<td colspan=7>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="cover('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span> 封面
												</a>
												<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span>
												</a>
												<a onclick="deleteMember('${obj.id}')" class="glyphicon glyphicon-trash" >
													<span class="am-icon-trash-o"></span>
												</a>
											</div>
										</div>
									</tdcolspan>
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
	function submitForm(){
		if($("#search").val() != ''){
			window.location.href="/admin/article?type=${type}&title="+$("#search").val();
		}
	}
	function cover(id){
		window.location.href="/admin/article/editCover?id="+id;
	}
	function edit(id){
		if(id == 0){
			window.location.href="/admin/article/edit?type=${type}";
		}else{
			window.location.href="/admin/article/edit?type=${type}&id="+id;
		}
	}
	function deleteMember(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/article/delete?type=${type}&id=" + id, function(result) {
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