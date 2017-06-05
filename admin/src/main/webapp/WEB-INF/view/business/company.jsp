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
				<li><a href="/admin/member">用户管理</a></li>
				<li><a href="/admin/push">推送管理</a></li>
				<li><a href="/admin/image">图片列表</a></li>
				<li><a href="/admin/info">短信列表</a></li>
				<li class="bgColor"><a href="/admin/feedback">意见反馈</a></li>
				<li><a href="/admin/invoice">发票列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">企业列表</strong>
				</div>
			</div>
			<div class="am-g">
			<form method="post" id="xlsForm">
				<div class="am-u-md-6 am-cf">
					<div class="am-fl am-cf">
						<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button" onclick="edit(0)" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 新增
								</button>
							</div>
						</div>
					</div>
					<div class="am-btn-toolbar am-fl">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button" id="importBtn" onclick="$('#xls').click()" class="am-btn am-btn-default">
									<span class="am-icon-plus"></span> 导入
								</button>
									<input type="file" name="XLSFile" id="xls" style="display: none;" onchange="uploadXLS()" />
							</div>
						</div>
						<input type="hidden" id="sign" value="0">
				</div>
				</form>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title"><center>id</center></th>
									<th class="table-title"><center>企业名称</center></th>
									<th class="table-title"><center>企业性质</center></th>
									<th class="table-type"><center>企业规模</center></th>
									<th class="table-type"><center>企业地址</center></th>
									<th class="table-type"><center>企业等级</center></th>
									<th class="table-set"><center>操作</center></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td><center>${obj.id}</td>
									<td><center>${obj.business_name}</center></td>
									<td><center>${obj.business_nature}</center></td>
									<td><center>${obj.business_scale}</center></td>
									<td><center>${obj.business_address}</center></td>
									<td><center>${obj.business_level}</center></td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="edit('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span> 编辑
												</a>
												<a onclick="deleteA('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
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
	function edit(id){
		if(id == 0){
			window.location.href="/admin/company/edit";
		}else{
			window.location.href="/admin/company/edit?id="+id;
		}
	}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/company/delete?id=" + id, function(result) {
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