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
				<li class="bgColor"><a href="/admin/coursetype" >课程一级目录</a></li>
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
					<%--<strong class="am-text-primary am-text-lg">课程列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf">
			<form class="am-form am-form-horizontal" method="post" id="myForm">
				<input type="hidden" id="catalogId" name="catalogId" value="${parentTypeId}">
				<div class="am-form-group">
	          		<label for="q-question" class="am-u-sm-3 am-form-label">添加课程到分类：</label>
	              	<div class="am-u-sm-9">
			              <input style="width: 50%" type="text" id="course-typetwo" readonly="readonly" name="parentName" value="${record.course_name}">
			              <input type="hidden" id="course-hidden-typetwo" name="parentId" readonly="readonly" value="${record.course_id}">
			              <form class="am-form am-form-horizontal" id="myform" method="post" >
			              	<input type="text" name="demo" id="demo" placeholder="输入名称搜索课程" style="float: left;width: 315px;"/>
			              </form>
			              <select id="typetwo" style="float: left;width: 315px;height: 43px;"></select>
			              <div style="float: none;">
			              	<a class="am-btn am-btn-primary" id="addTypetwo" >添加到分类</a>&nbsp;<a class="am-btn am-btn-primary" id="clear">清除</a>
			              </div>
		            </div>
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
									<th class="table-title" style="width: 10%"><center>课程分类id</center></th>
									<th class="table-title" style="width: 10%"><center>课程id</center></th>
									<th class="table-title" style="width: 50%"><center>课程名称</center></th>
									<th class="table-title" style="width: 20%"><center>权重值</center></th>
									<th class="table-set" style="width: 10%"><center>操作</center></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${list}">
								<tr>
									<td><center>${obj.parent_id}</td>
									<td><center>${obj.course_id}</center></td>
									<td><center>${obj.course_name}</center></td>
									<td><center>${obj.weight}</center></td>
									<td>
											<div class="am-btn-toolbar">
											<center>
												<div class="am-btn-group am-btn-group-xs">
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="edit('${obj.id}');">
														<span class="am-icon-pencil-square-o" ></span> 修改权重值
													</a>
													<a onclick="deleteA('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger" >
														<span class="am-icon-trash-o"></span> 删除
													</a>
												</div>
											</center>
										</div>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="am-cf">
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
$(function(){
	$("#saveBtn").bind("click", function(){  
		save();
	});
	$("#demo").bind("change",function(){
		$("#demo").empty();
		submit($("#demo").val());
	});
	$("#addTypetwo").bind("click",function(){
		var text = $("#course-typetwo").val();
		var ids = $("#id").val();
		if($("#typetwo option:selected").val() == ''){
			return;
		}
		$("#course-hidden-typetwo").val($("#typetwo option:selected").val());
		$("#course-typetwo").val($("#typetwo option:selected").text());
		save();
	});
	$("#clear").bind("click",function(){
		$("#course-hidden-typetwo").val('');
		$("#course-typetwo").val('');
	});
	add();
});
function submit(str){
	$('#myForm').ajaxSubmit({
		type:'post',
		url:"/admin/catalog/query",
	    success:function(result){
	        var html = "";
	    	for(var i=0;i<result.length;i++){
	    		html += "<option value='"+result[i].id+"'>";
	    		html += result[i].value;
	    		html += "</option>";
	    	$("#typetwo").html(html);
	        }
	    }
	});
}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/coursetype/deleteCourse?id=" + id, function(result) {
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
	function add(){
		$.getJSON("/admin/catalog/query",function(result){
			var html = "";
			for(var i=0;i<result.length;i++){
				html += "<option value='"+result[i].id+"'>";
				html += result[i].value;
				html += "</option>";
			}
			$("#typetwo").html(html);
		});
	}
	function save(str){
		var id = $("#catalogId").val();
		$('#myForm').ajaxSubmit({
			type:'post',
			url:"/admin/coursetype/saveCourse",
		    success:function(data){
		        if(data.status == 200){
		           	window.location.href = "/admin/coursetype/queryCourse?id="+id;
		        }else{
		        	$("#saveBtn").removeAttr('disabled');
		            showInfo(data.msg,function(){},3000);
		        }
		    }
		});
	}
	function edit(id){
		window.location.href="/admin/coursetype/editWeight?id="+id;
	}
</script>
</html>