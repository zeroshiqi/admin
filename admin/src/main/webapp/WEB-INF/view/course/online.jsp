
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
				<li><a href="/admin/course/offline">线下课程管理</a></li>
				<li><a href="/admin/course/onlineNew">线上课程管理</a></li>
				<li class="bgColor"><a href="/admin/course/online">录音课程管理</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">线上课程列表(录音)</strong>--%>
				<%--</div>--%>
			<%--</div>--%>

			<div class="am-u-md-3 am-cf">
				<button type="button" onclick="edit(0)" class="add">
					<span class="icon-plus"></span> 新增
				</button>
				<div class="Go">
					<input type="text" class="text" id="search" name="search" value="${name }">
					<button class="icon-search" onclick="submitForm()" type="button"></button>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id" style="width: 5%;">ID</th>
									<th class="table-title" style="width: 20%;">课程名称</th>
									<th class="table-title" style="width: 5%;">价格</th>
									<th class="table-author" style="width: 5%">封面</th>
									<th class="table-type" style="width: 5%">时长</th>
									<th class="table-type" style="width: 5%">观看人数</th>
									<th class="table-type" style="width: 5%">报名人数</th>
									<th class="table-type" style="width: 10%">试卷名称</th>
									<%--<th class="table-set" style="width: 40%">操作</th>--%>
								</tr>
							</thead>

							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.courseId}</td>
									<td>${obj.course_name}</td>
									<td>${obj.price}</td>
									<td><img src="${obj.cover}" width="100px"  height="40px"/></td>
									<td>${obj.time_length}</td>
<%-- 									<td><a href="/admin/course/joinMembers?courseId=${obj.courseId}&type=1">${obj.count}</a></td> --%>
										<td>${obj.count}</td>
									<td>
<%-- 										<a href="/admin/course/joinAllMembers?courseId=${obj.courseId}&type=3">${obj.joinNumber}</a> --%>
										${obj.joinNumber}
									</td>
									<td>${obj.examName}</td>
								</tr>
								<tr>
									<td colspan=8>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<a onclick="isnew('${obj.courseId}')"
													   class="am-btn am-btn-default am-btn-xs am-text-secondary">
														<span class="am-icon-trash-o"></span><c:if test="${obj.isnew == 0}">标新</c:if>
														<c:if test="${obj.isnew == 1}"><img style="width: 35px;height: auto;" src="../images/new.png"></c:if>
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-second11ary" onclick="showOrHidden('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> <c:if test="${obj.is_hidden == 0}">APP隐藏</c:if><c:if test="${obj.is_hidden == 1}">APP显示</c:if>
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="showOrHidden2('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> <c:if test="${obj.web_hidden == 0}">WEB隐藏</c:if><c:if test="${obj.web_hidden == 1}">WEB显示</c:if>
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="upload('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> 上传课件
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="uploadPDF('${obj.courseId}','${pageNo}');">
													<span class="am-icon-pencil-square-o" ></span> 上传PDF课程大纲
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="cover('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> 设置封面
												</a>
												<%-- <a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',1);">
													<span class="am-icon-pencil-square-o" ></span> 设置预告
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',2);">
													<span class="am-icon-pencil-square-o" ></span> 设置直播
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',3);">
													<span class="am-icon-pencil-square-o" ></span> 设置历史
												</a> --%>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="editContent('${obj.courseId}',2);">
													<span class="am-icon-pencil-square-o" ></span> 设置大纲
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="sellOrNot('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> <c:if test="${obj.is_sell == 0}">关闭报名</c:if><c:if test="${obj.is_sell == 1}">开启报名</c:if>
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="businessHidden('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> <c:if test="${obj.business_hidden == 0}">企业APP隐藏</c:if><c:if test="${obj.business_hidden == 1}">企业APP显示</c:if>
												</a>
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="locking('${obj.id}','${obj.course_name}');">
														<span class="am-icon-pencil-square-o" ></span> 添加考卷
												</a>
												<c:if test="${obj.if_title == 0}">
												<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="ifHaveTitle('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> 设为有片头<c:if test="${obj.if_title == 1}">设为无片头</c:if>
												</a>
												</c:if>
												<c:if test="${obj.if_title == 1}">
													<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="ifHaveTitle('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span> 设为无片头
													</a>
												</c:if>
												<a class="glyphicon glyphicon-edit" onclick="edit('${obj.courseId}');">
													<span class="am-icon-pencil-square-o" ></span>
												</a>
												<a onclick="deleteOnline('${obj.courseId}')" class="glyphicon glyphicon-trash" >
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
	 <div id="ly" align="center" style="position: absolute; top: 0px; filter: alpha(opacity=60);
         z-index: 2; left: 30%; display: none;width: 30%;height: 50%;">
    </div>
	<!-- 填写备注的window -->
    <div id="memoWindow" align="center" title="备注信息" style="position: fixed; z-index: 3; left: 30%; top: 28%;
         background-color: #fff; display: none; width:50%; height: auto;box-shadow: 0 0 2px 2px #ddd ; border-radius:6px;overflow:hidden;">
        <div style="width: 100%; height: 50px; background-color: #337ab7; color:#fff; font-weight:800; line-height: 50px; "><font face="微软雅黑" style="padding-left: 10px; font-size:18px; ">添加试卷到课程</font></div>
        <div id="memoDiv" style="width: 98%;">
			<table style="margin: 8px 0 6px; border:0; box-shadow:none;">
			    <tr>
			    	<td style="padding-top:5px;">
			    		<div class="am-g">
							<form class="am-form am-form-horizontal" method="post" id="xlsForm">
								<input type="hidden" name="catalogCourseId" id="catalogCourseId"/>
								<div class="am-form-group">
									<label for="q-question" class="am-u-sm-3 am-form-label">课程名称：</label>
					              	<div class="am-u-sm-9">
					              		<input style="width: 80%" type="text" disabled="disabled" id="courseName">
						            </div>
					          		<label for="q-question" class="am-u-sm-3 am-form-label">添加试题到课程：</label>
					              	<div class="am-u-sm-9">
							              <input style="width: 50%" type="text" id="course-exam" readonly="readonly" name="examName" value="${record.title}">
							              <input type="hidden" id="course-hidden-exam" name="examId" readonly="readonly" value="${record.examId}">
							              <form class="am-form am-form-horizontal" id="myform" method="post" >
							              	<input type="text" name="examDemo" id="examDemo" placeholder="输入名称搜索试卷" style="float: left;width: 315px;"/>
							              </form>
							              <select id="examList" style="float: left;width: 315px;height: 43px;"></select>
							              <div style="float: right;margin-top:10px;">
							              	<a class="am-btn am-btn-primary" id="addExam" >添加到课程</a>&nbsp;<a class="am-btn am-btn-primary" onclick="Lock_CheckForm(this)">取消</a>
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
	function submitForm(){
		if($("#search").val() != ''){
			window.location.href="/admin/course/online?name="+$("#search").val();
		}
	}
	function upload(id){
		window.location.href="/admin/course/uploadFile?id="+id;
	}
	function uploadPDF(id,page){
		window.location.href="/admin/course/uploadPDF?id="+id+"&name="+$("#search").val()+"&page="+page;
	}
	function edit(id){
		if(id == 0){
			window.location.href="/admin/course/edit?type=1&newtype=0";
		}else{
			window.location.href="/admin/course/edit?id="+id+"&newtype=0";
		}
	}
	function cover(id){
		window.location.href="/admin/course/cropImage?id="+id;
	}
	function editContent(id,type){
		window.location.href="/admin/course/editContent?id="+id+"&type="+type;
	}
	function end(id,type){
		if(type == 0){
			showConfirm("结束课程?",function(){
				$.getJSON("/admin/course/updateOnlinePlayStatus?id=" + id, function(result) {
					if (result.status == 200) {
						location.reload();
					} else {
						showInfo("系统错误....",function(){},3000);
					}
				});
			});
		}else{
			showConfirm("恢复课程?",function(){
				$.getJSON("/admin/course/updateOnlinePlayStatus?id=" + id, function(result) {
					if (result.status == 200) {
						location.reload();
					} else {
						showInfo("系统错误....",function(){},3000);
					}
				});
			});
		}
		
	}
	function showOrHidden(courseId){
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/hiddenOrShow?courseId=" + courseId, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){},3000);
				}
			});
		});
	}
	function sellOrNot(courseId){
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/sellOrNot?courseId=" + courseId, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){},3000);
				}
			});
		});
	} 
	//企业APP显示或隐藏
	function businessHidden(courseId){
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/businessHidden?courseId=" + courseId, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
						showInfo("系统错误....",function(){},3000);
				}
			});
		});
	}
	function showOrHidden2(courseId) {
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/webHidden?courseId=" + courseId, function(
					result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function ifHaveTitle(courseId) {
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/ifHaveTitle?courseId=" + courseId, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	function deleteOnline(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/course/deleteOnline?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){},3000);
				}
			});
		});
	}
	function submitExam(str){
		$('#xlsForm').ajaxSubmit({
			type:'post',
			url:"/admin/catalog/queryExam",
		    success:function(result){
		        var html = "";
		    	for(var i=0;i<result.length;i++){
		    		html += "<option value='"+result[i].id+"'>";
		    		html += result[i].value;
		    		html += "</option>";
		    	$("#examList").html(html);
		        }
		    }
		});
	}
	function addExam(){
		$.getJSON("/admin/catalog/queryExam",function(result){
			var html = "";
			for(var i=0;i<result.length;i++){
				html += "<option value='"+result[i].id+"'>";
				html += result[i].value;
				html += "</option>";
			}
			$("#examList").html(html);
		});
	}
	//保存试卷到课程
	function saveExam(){
		var id = $("#catalogId").val();
		$('#xlsForm').ajaxSubmit({
			type:'post',
			url:"/admin/catalog/saveCourseExam",
		    success:function(data){
		        if(data.status == 200){
		           	window.location.href = "/admin/course/online";
		        }else{
		        	$("#saveBtn").removeAttr('disabled');
		            showInfo(data.msg,function(){},3000);
		        }
		    }
		});
	}
	function isnew(id){
		showConfirm("确认操作?", function() {
			$.getJSON("/admin/course/isNew?courseId=" + id, function(
					result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....", function() {
					}, 3000);
				}
			});
		});
	}
	//添加试卷按钮弹出框
	 function locking(id,courseName){
//		   document.getElementById('klID').value = id;
//		   document.getElementById('veriFY').value = source;
//		   document.getElementById("memo").value = memo;
		   document.all.ly.style.display="block";   
		   document.all.ly.style.width="50%";   
		   document.all.ly.style.height="50%"; 
		   document.all.memoWindow.style.display='block'; 
		   $("#catalogCourseId").val(id);
		   $("#courseName").val(courseName);
			$("#addExam").bind("click",function(){
				var text = $("#course-exam").val();
				var ids = $("#id").val();
				if($("#examList option:selected").val() == ''){
					return;
				}
				$("#course-hidden-exam").val($("#examList option:selected").val());
				$("#course-exam").val($("#examList option:selected").text());
				saveExam($("#catalogCourseId").val());
			});
			$("#clearExam").bind("click",function(){
				$("#course-hidden-exam").val('');
				$("#course-exam").val('');
			});
			$("#examDemo").bind("change",function(){
				$("#examDemo").empty();
				submitExam($("#examDemo").val());
			});
		   addExam();
	}
	//关闭试卷选择弹出框
	 function Lock_CheckForm(theForm){   
		document.all.ly.style.display='none';
		document.all.memoWindow.style.display='none';
		return false;   
	}
</script>
</html>