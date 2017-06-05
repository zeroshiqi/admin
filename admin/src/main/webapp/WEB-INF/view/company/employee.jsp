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
		<div class="nav-assist" style="background:#0e90d2">
		<a  class="icon-reply" href="javascript:history.go(-1);" style="float:left;color:#fff;padding:12px 20px"></a>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="minTitle">--%>
				<%--企业用户列表--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf">
				<form method="post" id="xlsForm">
					<button type="button" onclick="add('${companyId}')" class="add">
						<span class="icon-plus"></span> 新增
					</button>

					<div class="Go">
						<input type="text" placeholder="手机号码" class="text"  style="width: 120px;" id="searchMobile" name="mobile">
						<button class="icon-search" onclick="submitForm('${companyId}')" type="button"></button>
<!-- 								<button class="am-btn am-btn-default" onclick="submitForm1()" -->
<!-- 									type="button">同步</button> -->
					</div>
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

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title" style="width: 5%"><center>id</center></th>
									<th class="table-title" style="width: 10%"><center>姓名</center></th>
									<th class="table-title" style="width: 10%"><center>性别</center></th>
									<th class="table-type" style="width: 10%"><center>职位</center></th>
									<th class="table-type" style="width: 20%"><center>电话</center></th>
									<th class="table-type" style="width: 20%"><center>邮箱</center></th>
									<th class="table-type" style="width: 5%"><center>状态</center></th>
									<%--<th class="table-set" style="width: 10%"><center>操作</center></th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td><center>${obj.id}</td>
									<td><center>${obj.name}</center></td>
									<td><center>${obj.sex}</center></td>
									<td><center>${obj.position}</center></td>
									<td><center>${obj.mobile}</center></td>
									<td><center>${obj.mailbox}</center></td>
									<td>
										<center>
											<c:if test="${obj.status == 2}">
												<font color="red" ><a style="font-weight: bold;font-size: 15px;color: red" href="" onclick="aliveEmployee('${obj.id}')">失效</a></font>
											</c:if>
											<c:if test="${obj.status == 1}">
												<a style="font-weight: bold;font-size: 15px;" href="" onclick="abateEmployee('${obj.id}')">有效</a>
											</c:if>
										</center>
									</td>
								</tr>
								<tr>
									<td colspan=7>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}','${companyId}');">
													<span class="am-icon-pencil-square-o" ></span>
												</a>
												<a onclick="deleteA('${obj.id}')" class="glyphicon glyphicon-trash" >
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
	function edit(id,companyId){
		if(id == 0){
			window.location.href="/admin/company/editEmployee";
		}else{
			window.location.href="/admin/company/editEmployee?id="+id+"&companyId="+companyId;
		}
	}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/company/deleteEmployee?id=" + id, function(result) {
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
	 function add(id){
			window.location.href="/admin/company/editEmployee?companyId="+id;
	 }
	 function submitForm(companyId) {
			window.location.href = "/admin/company/queryUser?mobile="+ $("#searchMobile").val()+"&id="+companyId;
	}
	 //使企业用户生效
	 function aliveEmployee(id){
		 $.getJSON("/admin/company/aliveEmployee?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){
						//..
					},3000);
				}
			});
	 }
	 //使企业用户失效
	 function abateEmployee(id){
		 $.getJSON("/admin/company/abateEmployee?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){
						//..
					},3000);
				}
			});
	 }
</script>
</html>