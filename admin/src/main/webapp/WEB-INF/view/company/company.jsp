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
				<li class="bgColor"><a href="/admin/company" >企业管理</a></li>
				<li><a href="/admin/keywords" >搜索关键字管理</a></li>
				<li><a href="/admin/catalog" >一级分类管理</a></li>
				<li><a href="/admin/catalogtwo" >二分类级管理</a></li>
				<li><a href="/admin/coursetype" >课程一级目录</a></li>
				<li><a href="/admin/coursetype/typetwo" >课程二级目录</a></li>
				<li><a href="/admin/appcomment" >课程评论管理</a></li>
				<li><a href="/admin/appticket" >成绩列表</a></li>
				<li><a href="/admin/banner">首页banner列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">企业列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf">
				<form method="post" id="xlsForm">
					<button type="button" onclick="edit(0)" class="add">
						<span class="icon-plus"></span> 新增
					</button>
<!-- 					<div class="am-btn-toolbar am-fl"> -->
<!-- 							<div class="am-btn-group am-btn-group-xs"> -->
<!-- 								<button type="button" id="importBtn" onclick="$('#xls').click()" class="am-btn am-btn-default"> -->
<!-- 									<span class="am-icon-plus"></span> 导入 -->
<!-- 								</button> -->
<!-- 									<input type="file" name="XLSFile" id="xls" style="display: none;" onchange="uploadXLS()" /> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<input type="hidden" id="sign" value="0"> -->
<!-- 				</div> -->
				</form>
			</div>

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title" style="width: 10%"><center>id</center></th>
									<th class="table-title" style="width: 10%"><center>企业名称</center></th>
									<th class="table-title" style="width: 10%"><center>企业性质</center></th>
									<th class="table-type" style="width: 10%"><center>企业规模</center></th>
									<th class="table-type" style="width: 10%"><center>企业地址</center></th>
									<th class="table-type" style="width: 10%"><center>企业等级</center></th>
									<th class="table-type" style="width: 20%"><center>企业当前用户数</center></th>
									<th class="table-type" style="width: 10%"><center>企业状态</center></th>
									<%--<th class="table-set" style="width: 10%"><center>操作</center></th>--%>
								</tr>
							</thead>

							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td><center>${obj.id}</td>
									<td><center>${obj.business_name}</center></td>
									<td><center>${obj.business_nature}</center></td>
									<td><center>${obj.business_scale}</center></td>
									<td><center>${obj.business_address}</center></td>
									<td><center>${obj.business_level}</center></td>
									<td><center><a  class="aYes"  href="/admin/company/queryUser?id=${obj.id}">${obj.employeeCount}</a>/${obj.business_level*10}</center></td>
									<td>
										<center>
											<c:if test="${obj.status == 2}">
												<font color="red" ><a style="font-weight: bold;font-size: 15px;color: red" href="/admin/company/alive?id=${obj.id}">失效</a></font>
											</c:if>
											<c:if test="${obj.status == 1}">
												<a style="font-weight: bold;font-size: 15px;" href="/admin/company/abate?id=${obj.id}">有效</a>
											</c:if>
										</center>
									</td>
								</tr>
								<tr>
									<td colspan=8>
										<center>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="quanxian('${obj.id}');"> <span
														class="am-icon-pencil-square-o"></span> 批量开通权限
													</a>
													<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
														<span class="am-icon-pencil-square-o" ></span>
													</a>
													<a onclick="deleteA('${obj.id}')" class="glyphicon glyphicon-trash" >
														<span class="am-icon-trash-o"></span>
													</a>
<%-- 													<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="queryUser('${obj.id}')"> --%>
<!-- 														<span class="am-icon-pencil-square-o" ></span> 查看企业用户 -->
<!-- 													</a> -->
												</div>
											</div>
										</center>
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
	function queryUser(id){
		alert(id);
		if(id == 0){
			window.location.href="/admin/company/queryUser";
		}else{
			window.location.href="/admin/company/queryUser?id="+id;
		}
	}
	function quanxian(id){
		window.location.href="/admin/company/quanxian?id="+id;
	}
	
</script>
</html>