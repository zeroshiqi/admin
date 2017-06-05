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
				<li><a href="/admin/company" >企业管理</a></li>
				<li><a href="/admin/keywords" >搜索关键字管理</a></li>
				<li><a href="/admin/catalog" >一级分类管理</a></li>
				<li><a href="/admin/catalogtwo" >二分类级管理</a></li>
				<li><a href="/admin/coursetype" >课程一级目录</a></li>
				<li class="bgColor"><a href="/admin/coursetype/typetwo" >课程二级目录</a></li>
				<li><a href="/admin/appcomment" >课程评论管理</a></li>
				<li><a href="/admin/appticket" >成绩列表</a></li>
				<li><a href="/admin/banner">首页banner列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">
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
<!-- 				</div> -->
				</form>
			</div>

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title">id</th>
									<th class="table-title">二级目录名称</th>
									<th class="table-title">所属一级目录</th>
<!-- 									<th class="table-title">考试题数</th> -->
									<th class="table-type">创建时间</th>
									<th class="table-type"><center>权重值</center></th>
									<%--<th class="table-set">操作</th>--%>
								</tr>
							</thead>

							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td>${obj.name}</td>
									<td><a href="/admin/coursetype/queryByParentId?id=${obj.parent_id}">${obj.parent_name}</a></td>
<%-- 									<td>${obj.number}</td> --%>
									<td>${fn:substring(obj.create_at,0,19)}</td>
									<td><center>${obj.weight}</center></td>
								</tr>
								<tr>
									<td colspan=5>
										<div class="am-btn-toolbar">
											<a onclick="querySon('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary" >
												<span class="am-icon-trash-o"></span> 查看拥有的课程
											</a>
											<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editParent('${obj.parent_id}');">
													<span class="am-icon-pencil-square-o" ></span> 编辑上级目录
											</a>
											<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
												<span class="am-icon-pencil-square-o" ></span>
											</a>
											<a onclick="deleteA('${obj.id}')" class="glyphicon glyphicon-trash" >
												<span class="am-icon-trash-o"></span>
											</a>
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
	function edit(id){
		if(id == 0){
			window.location.href="/admin/coursetype/editTypeTwo";
		}else{
			window.location.href="/admin/coursetype/editTypeTwo?id="+id;
		}
	}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/coursetype/deleteTypeTwo?id=" + id, function(result) {
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
	function querySon(id){
		if(id == 0){
			window.location.href="/admin/coursetype/queryCourse";
		}else{
			window.location.href="/admin/coursetype/queryCourse?id="+id;
		}
	}
	function editParent(id){
		if(id == 0){
			window.location.href="/admin/coursetype/edit";
		}else{
			window.location.href="/admin/coursetype/edit?id="+id;
		}
	}
	//通过Excel导入问题
// 	function uploadXLS(){
// 		showConfirm("确认上传?",function(){
			
// 			$('#xlsForm').ajaxSubmit({
// 				type:'post',
// 				url:"/admin/question/saveQuestions",
// 			    success:function(data){
// 			        if(data.status == 200){
// 			        	showConfirm(data.msg,function(){
// 			        		window.location.href = "/admin/question";
// 			        	});
// 			        }else{
// 			            showInfo(data.msg,function(){},3000);
// 			        }
// 			    }
// 			});
// 		}
// 	}
</script>
</html>