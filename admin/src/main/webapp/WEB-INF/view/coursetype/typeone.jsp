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
				<li><a href="/admin/company">企业管理</a></li>
				<li><a href="/admin/keywords">搜索关键字管理</a></li>
				<li><a href="/admin/catalog">课程一级分类管理</a></li>
				<li><a href="/admin/catalogtwo">课程二级分类管理</a></li>
				<li class="bgColor"><a href="/admin/coursetype">课程一级目录</a></li>
				<li><a href="/admin/coursetype/typetwo">课程二级目录</a></li>
				<li><a href="/admin/appcomment">企业课程评论管理</a></li>
				<li><a href="/admin/appticket">好多课课程试卷成绩列表</a></li>
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
									<th class="table-title" style="width: 10%">Id</th>
									<th class="table-title" style="width: 20%"><center>封面图片</center></th>
									<th class="table-title" style="width: 20%"><center>名称</center></th>
									<th class="table-title" style="width: 10%"><center>权重值</center></th>
									<th class="table-title" style="width: 10%"><center>展示类型</center></th>
									<th class="table-title" style="width: 20%"><center>关联数据</center></th>
									<th class="table-title" style="width: 10%">创建时间</th>
									<%--<th class="table-set" style="width: 20%"><center>操作</center></th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><img src="${obj.cover}" width="100px" height="40px" /></td>
									<td><center>${obj.name}</center></td>
									<td><center>${obj.weight}</center></td>
									<td>
										<c:if test="${obj.show_type =='0'}">
											<center>不显示Banner</center>
										</c:if>
										<c:if test="${obj.show_type =='1'}">
											<center>课程</center>
										</c:if>
										<c:if test="${obj.show_type =='2'}">
											<center>讲师</center>
										</c:if>
										<c:if test="${obj.show_type =='3'}">
											<center>课程包</center>
										</c:if>
										
									</td>
									<td>
										<c:if test="${obj.show_type =='0'}">
											<center>不显示Banner</center>
										</c:if>
										<c:if test="${obj.show_type =='1'}">
											<center>${obj.course_name}</center>
										</c:if>
										<c:if test="${obj.show_type =='2'}">
											<center>${obj.teacher_name}</center>
										</c:if>
										<c:if test="${obj.show_type =='3'}">
											<center>${obj.type_name}</center>
										</c:if>
									</td>
									<td>${fn:substring(obj.create_at,0,19)}</td>
								</tr>
								<tr>
									<td colspan=7>
									<center>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a onclick="querySon('${obj.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary" >
													<span class="am-icon-trash-o"></span> 查看子目录
												</a>
												<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
													<span class="am-icon-pencil-square-o" ></span>
												</a>
												<a onclick="deleteA('${obj.id}')" class="glyphicon glyphicon-trash" >
													<span class="am-icon-trash-o"></span>
												</a>
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
	 <div id="kc" align="center" style="position: absolute; top: 0px; filter: alpha(opacity=60);
         z-index: 2; left: 30%; display: none;width: 30%;height: 50%;">
    </div>
	<!-- window -->
    <div id="courseWindow" align="center" title="封面课程" style="position: absolute; z-index: 3; left: 30%; top: 10%;
         background-color: #fff; display: none; width:50%; height: 300px;border: 1px solid;">
        <div style="width: 100%;height: 30px; background-color: #e6f5ee; border-bottom: 1px solid #009966; font-size: 17px;"><font face="微软雅黑" style="padding-left: 10px;">添加封面课程</font></div>
        <div id="courseDiv" style="width: 98%; padding-top: 10px;">
			<table>
			    <tr>
			    	<td>
			    		<div class="am-g">
							<form class="am-form am-form-horizontal" method="post" id="myForm">
								<input type="hidden" id="typeoneId" name="typeoneId"">
								<div class="am-form-group">
					          		<label for="q-question" class="am-u-sm-3 am-form-label">设置为封面课程</label>
					              	<div class="am-u-sm-9">
							              <input style="width: 50%" type="text" id="course-typetwo" readonly="readonly" name="parentName" value="${record.course_name}">
							              <input type="hidden" id="course-hidden-typetwo" name="parentId" readonly="readonly" value="${record.course_id}">
							              <form class="am-form am-form-horizontal" id="myform" method="post" >
							              	<input type="text" name="demo" id="demo" placeholder="输入名称搜索课程" style="float: left;width: 315px;"/>
							              </form>
							              <select id="typetwo" style="float: left;width: 315px;height: 43px;"></select>
							              <div style="float: none;">
							              	<a class="am-btn am-btn-primary" id="addTypetwo" >设置为封面课程</a>&nbsp;<a class="am-btn am-btn-primary" onclick="Lock_CourseForm(this)">取消</a>
							              </div>
						            </div>
						      	</div>
								</form>
						   </div>
			    	</td>
			    </tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	$("#demo").bind("change",function(){
		$("#demo").empty();
		submit($("#demo").val());
	});
});
	function edit(id){
		if(id == 0){
			window.location.href="/admin/coursetype/edit";
		}else{
			window.location.href="/admin/coursetype/edit?id="+id;
		}
	}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/coursetype/delete?id=" + id, function(result) {
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
			window.location.href="/admin/coursetype/typetwo";
		}else{
			window.location.href="/admin/coursetype/queryByParentId?id="+id;
		}
	}
	//添加备注按钮弹出框
	 function course(id,name){
		   document.all.kc.style.display="block";   
		   document.all.kc.style.width="50%";   
		   document.all.kc.style.height="50%"; 
		   document.all.courseWindow.style.display='block'; 
		   $("#typeoneId").val(id);
// 		   $("#courseName").val(name);
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
			addCourse();
	}
	 function addCourse(){
			$.getJSON("/admin/coursetype/bannerCourse",function(result){
				var html = "";
				for(var i=0;i<result.length;i++){
					html += "<option value='"+result[i].id+"'>";
					html += result[i].value;
					html += "</option>";
				}
				$("#typetwo").html(html);
			});
	}
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
	//关闭课程弹出框
	 function Lock_CourseForm(theForm){   
		document.all.kc.style.display='none';
		document.all.courseWindow.style.display='none';
		return false;   
	}
	//将选择的课程选作封面
	function save(str){
			var id = $("#catalogId").val();
			$('#myForm').ajaxSubmit({
				type:'post',
				url:"/admin/catalog/saveCourse",
			    success:function(data){
			        if(data.status == 200){
			           	window.location.href = "/admin/catalog/queryCourse?id="+id;
			        }else{
			        	$("#saveBtn").removeAttr('disabled');
			            showInfo(data.msg,function(){},3000);
			        }
			    }
			});
		}
</script>
</html>