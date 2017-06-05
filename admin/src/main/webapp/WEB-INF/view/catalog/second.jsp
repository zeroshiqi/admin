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

			<div class="minTitle">
				课程分类列表
			</div>
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

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-title" style="width: 5%"><center>id</center></th>
									<th class="table-title" style="width: 25%"><center>课程分类名称</center></th>
									<th class="table-title" style="width: 20%"><center>子标题名称</center></th>
									<th class="table-title" style="width: 10%"><center>权重值</center></th>
									<th class="table-title" style="width: 15%"><center>试卷名称</center></th>
									<th class="table-title" style="width: 10%"><center>包含的有效课程数量</center></th>
<!-- 									<th class="table-title" style="width: 15%"><center>创建时间</center></th> -->
									<%--<th class="table-set" style="width: 15%"><center>操作</center></th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td><center>${obj.id}</td>
									<td><center>${obj.name}</center></td>
									<td><center>${obj.subtitle}</center></td>
									<td><center>${obj.weight}</center></td>
									<td><center>${obj.exam_name}</center></td>
									<td><center><a href="/admin/catalog/queryCourse?id=${obj.id}">${obj.courseCount}</a></center></td>
								</tr>
								<tr>
									<td colspan=6>
										<center>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="locking('${obj.id}','${obj.name}');">
														<span class="am-icon-pencil-square-o" ></span> 添加考卷
													</a>

													<a class="glyphicon glyphicon-edit" onclick="edit('${obj.id}');">
														<span class="am-icon-pencil-square-o" ></span>
													</a>
													<a onclick="deleteA('${obj.id}')" class="glyphicon glyphicon-trash" >
														<span class="am-icon-trash-o"></span>
													</a>
<%-- 													<a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="query('${obj.id}');"> --%>
<!-- 														<span class="am-icon-pencil-square-o" ></span> 查看此目录下的课程 -->
<!-- 													</a> -->
												</div>
											</div>
										</center>
									</td>
								</tr>
							</tbody>
							</c:forEach>
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
	 <div id="ly" align="center" style="position: absolute; top: 0px; filter: alpha(opacity=60);
         z-index: 2; left: 30%; display: none;width: 30%;height: 50%;">
    </div>
	<!-- 填写备注的window -->
    <div id="memoWindow" align="center" title="备注信息" style="position: absolute; z-index: 3; left: 30%; top: 10%;
         background-color: #fff; display: none; width:50%; height: 300px;border: 1px solid;">
        <div style="width: 100%;height: 30px; background-color: #e6f5ee; border-bottom: 1px solid #009966; font-size: 17px;"><font face="微软雅黑" style="padding-left: 10px;">添加试卷到课程</font></div>
        <div id="memoDiv" style="width: 98%; padding-top: 10px;">
			<table>
			    <tr>
			    	<td>
			    		<div class="am-g">
							<form class="am-form am-form-horizontal" method="post" id="examForm">
								<input type="hidden" name="catalogId" id="catalogId"/>
								<div class="am-form-group">
								<label for="q-question" class="am-u-sm-3 am-form-label">目录名称</label>
					              	<div class="am-u-sm-9">
					              		<input style="width: 80%" type="text" disabled="disabled" id="courseName">
						            </div>
					          		<label for="q-question" class="am-u-sm-3 am-form-label">添加试题到课程</label>
					              	<div class="am-u-sm-9">
							              <input style="width: 50%" type="text" id="course-exam" readonly="readonly" name="examName" value="${record.title}">
							              <input type="hidden" id="course-hidden-exam" name="examId" readonly="readonly" value="${record.examId}">
							              <form class="am-form am-form-horizontal" id="myform" method="post" >
							              	<input type="text" name="examDemo" id="examDemo" placeholder="输入名称搜索试卷" style="float: left;width: 315px;"/>
							              </form>
							              <select id="examList" style="float: left;width: 315px;height: 43px;"></select>
							              <div style="float: right;">
							              	<a class="am-btn am-btn-primary" id="addExam" >添加到分类</a>&nbsp;<a class="am-btn am-btn-primary" onclick="Lock_CheckForm(this)">取消</a>
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
	function edit(id){
		if(id == 0){
			window.location.href="/admin/catalog/edit";
		}else{
			window.location.href="/admin/catalog/edit?id="+id;
		}
	}
	function deleteA(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/catalog/delete?id=" + id, function(result) {
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
	function query(id){
		if(id == 0){
			window.location.href="/admin/catalog/queryCourse";
		}else{
			window.location.href="/admin/catalog/queryCourse?id="+id;
		}
	}
	//添加备注按钮弹出框
	 function locking(id,name){
//		   document.getElementById('klID').value = id;
//		   document.getElementById('veriFY').value = source;
//		   document.getElementById("memo").value = memo;
		   document.all.ly.style.display="block";   
		   document.all.ly.style.width="50%";   
		   document.all.ly.style.height="50%"; 
		   document.all.memoWindow.style.display='block'; 
		   $("#catalogId").val(id);
		   $("#courseName").val(name);
			$("#addExam").bind("click",function(){
				var text = $("#course-exam").val();
				var ids = $("#id").val();
				if($("#examList option:selected").val() == ''){
					return;
				}
				$("#course-hidden-exam").val($("#examList option:selected").val());
				$("#course-exam").val($("#examList option:selected").text());
				saveExam($("#catalogId").val());
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
	//关闭备注弹出框
	 function Lock_CheckForm(theForm){   
		document.all.ly.style.display='none';
		document.all.memoWindow.style.display='none';
		return false;   
	}
	//保存试卷到课程
		function saveExam(){
			$('#examForm').ajaxSubmit({
				type:'post',
				url:"/admin/catalog/saveCatalogExam",
			    success:function(data){
			        if(data.status == 200){
			           	window.location.href = "/admin/catalog";
			        }else{
			        	$("#saveBtn").removeAttr('disabled');
			            showInfo(data.msg,function(){},3000);
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
		function submitExam(str){
			$('#examForm').ajaxSubmit({
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
</script>
</html>