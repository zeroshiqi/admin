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

	<div class="">
		<!-- sidebar start -->
		<div class="admin-sidebar">
			<%@include file="/base/left.jsp"%>
		</div>
		<!-- sidebar end -->

		<!-- 辅助导航栏 -->
		<div class="nav-assist">
			<ul>
				<li><a href="/admin/ticket">成绩列表</a></li>
				<li class="bgColor"><a href="/admin/question">问题列表</a></li>
				<li><a href="/admin/typeone">问题一级目录列表</a></li>
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
					<button type="button" id="importBtn" onclick="$('#xls').click()" class="add">
						<span class="icon-plus"></span> 导入
					</button>

					<input type="file" name="XLSFile" id="xls" style="display: none;" onchange="uploadXLS()" />
					<input type="hidden" id="sign" value="0">
				</form>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title"><center>问题</center></th>
									<th class="table-title"><center>A</center></th>
									<th class="table-title"><center>B</center></th>
									<th class="table-type"><center>C</center></th>
									<th style="width: 150px;"><center>D</center></th>
									<th style="width: 50px;"><center>答案</center></th>
									<th class="table-type"><center>出题人</center></th>
									<th class="table-type"><center>试题类型</center></th>
									<th style="width: 150px;"><center>所属二级目录</center></th>
									<%--<th class="table-set"><center>操作</center></th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td><center>${obj.title}</center></td>
									<td><center>${obj.a}</center></td>
									<td><center>${obj.b}</center></td>
									<td><center>${obj.c}</center></td>
									<td><center>${obj.d}</center></td>
									<td><center>${obj.answer}</center></td>
									<td><center>${obj.user_name}</center></td>
									<td>
										<c:if test="${obj.type eq 1}">
											<center>基础</center>
										</c:if>
										<c:if  test="${obj.type eq 2}">
											<center>增强</center>
										</c:if>
									</td>
									<td><center><a onclick="editParent('${obj.parent_id}')">${obj.parent_name}</a></center></td>
								</tr>
								<tr>
									<td colspan=9>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span>
												</a>
												<a onclick="deleteA('${obj.id}')" class="glyphicon glyphicon-trash" >
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
	function edit(id){
		if(id == 0){
			window.location.href="/admin/question/edit";
		}else{
			window.location.href="/admin/question/edit?id="+id;
		}
	}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/question/delete?id=" + id, function(result) {
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
	function editParent(id){
		if(id == 0){
			window.location.href="/admin/typetwo/edit";
		}else{
			window.location.href="/admin/typetwo/edit?id="+id;
		}
	}
	//通过Excel导入问题
	function uploadXLS(){
		showConfirm("确认上传?",function(){
			if($('#sign').val()!="0"){
				alert("正在处理请求，请勿重复提交！");
				return;
			}
			$('#sign').val('1');
			$('#xlsForm').ajaxSubmit({
				type:'post',
				url:"/admin/question/saveQuestions",
			    success:function(data){
			        if(data.status == 200){
			        	showConfirm(data.msg,function(){
			        		$('#sign').val('0');
			        		window.location.href = "/admin/question";
			        	});
			        }else{
			            showInfo(data.msg,function(){},3000);
			        }
			    }
			});
		});
	}
</script>
</html>