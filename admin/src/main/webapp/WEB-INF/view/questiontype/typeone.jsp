<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
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
				<li><a href="/admin/ticket">成绩列表</a></li>
				<li><a href="/admin/question">问题列表</a></li>
				<li class="bgColor"><a href="/admin/typeone">问题一级目录列表</a></li>
				<li><a href="/admin/typetwo">问题二级目录列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<div class="am-u-md-3 am-cf">
				<form method="post" id="xlsForm">
					<button type="button" onclick="edit(0)" class="add">
						<span class="icon-plus"></span> 新增
					</button>
				</form>
			</div>

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title">Id</th>
									<th class="table-title">名称</th>
									<th class="table-title">创建时间</th>
									<th class="table-title"><center>参考人数</center></th>
									<%--<th class="table-set">操作</th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td>${obj.title}</td>
									<td>${fn:substring(obj.create_at,0,19)}</td>
									<td><center>
									<a class="aYes" href="/admin/ticket/queryTicketsByTopParentId?topParentId=${obj.id}">${obj.ticketCount}</a></center></td>
								</tr>
								<tr>
									<td colspan=4>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a onclick="querySon('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary" >
													<span class="am-icon-trash-o"></span> 查看子目录
												</a>
												<a	class="am-btn am-btn-default am-btn-xs am-text-secondary"
														onclick="copy('${obj.id}');"> <span
														class="am-icon-pencil-square-o"></span> 获得URL
													</a>
												<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
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
	function edit(id){
		if(id == 0){
			window.location.href="/admin/typeone/edit";
		}else{
			window.location.href="/admin/typeone/edit?id="+id;
		}
	}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/typeone/delete?id=" + id, function(result) {
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
			window.location.href="/admin/typetwo";
		}else{
			window.location.href="/admin/typetwo/queryByParentId?id="+id;
		}
	}
	function copy(id) {
		jPrompt('URL:', "http://www.chazuomba.com/files/webApp/test/testList.html?id="+id, '带输入框的提示框', function(r) {

		});
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