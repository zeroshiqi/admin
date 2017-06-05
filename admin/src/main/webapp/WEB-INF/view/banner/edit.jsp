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
<link rel="stylesheet" href="/admin/premage/premage.min.css">
<script src="/admin/premage/premage.min.js"></script>

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
      编辑好多课首页Banner
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
          <div class="am-form-group">
            <label class="am-u-sm-3 am-form-label">封面：</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
            </div>
          </div>
          <div class="am-form-group">
            <label for="teacher-name" id="teacherNameId" class="am-u-sm-3 am-form-label">类别名称：</label>
            <div class="am-u-sm-9">
            	<input type="hidden" name="id" value="${record.id}">
              	<input type="text" id="teacher-name" name="name" placeholder="描述" value="${record.name}">
            </div>
          </div>
          
		 <div class="am-form-group">
            <label for="teacher-weight" id="teacherJobId" class="am-u-sm-3 am-form-label">排序值：</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-weight" name="weight" placeholder="排序值" value="${record.weight}">
            </div>
          </div>
           <div class="am-form-group">
            <label for="teacher-weight" id="teacherJobId" class="am-u-sm-3 am-form-label">展示类型：</label>
            <div class="am-u-sm-9">
              <input type="hidden" id="st" name="st" value="${record.show_type}">
				<select id="showType" name="showType">
					<c:if test="${empty record.id}">
						<option value="0" selected="selected">课程</option>
						<option value="1">讲师</option>
						<option value="2">课程包</option>
					</c:if>
					<c:if test="${not empty record.id}">
						<c:if test="${record.show_type == '0'}">
							<option value="0" selected="selected">课程</option>
							<option value="1">讲师</option>
							<option value="2">课程包</option>
						</c:if>
						<c:if test="${record.show_type == '1'}">
							<option value="0">课程</option>
							<option value="1" selected="selected">讲师</option>
							<option value="2">课程包</option>
						</c:if>
						<c:if test="${record.show_type == '2'}">
							<option value="0">课程</option>
							<option value="1">讲师</option>
							<option value="2" selected="selected">课程包</option>
						</c:if>
					</c:if>
				</select>
            </div>
          </div>
          <div class="am-form-group" id="jiangshi">
	            <label for="course-teacher" class="am-u-sm-3 am-form-label">讲师</label>
	            <div class="am-u-sm-9">
	              <input type="text" id="course-teacher" name="teacherName" readonly="readonly" value="${record.teacher_name}">
	              <input type="hidden" id="course-hidden-teacher" name="teacher" value="${record.teacher_id}" readonly="readonly">
	              <br>
	              <input type="text" id="teacher" style="float: left;width: 200px">
	              <select id="teachers" style="float: left;width: 315px">
	              </select>
	              <br>
	              <div style="float: none;">
	              <a class="am-btn am-btn-primary" id="addTeacher" >选为封面讲师</a>&nbsp;<a class="am-btn am-btn-primary" id="clearteacher">清除</a>
	              </div>
	            </div>
	          </div>
          <div class="am-form-group" id="kecheng">
	            <label for="course-teacher" class="am-u-sm-3 am-form-label">课程</label>
	            <div class="am-u-sm-9">
		                <input style="width: 50%" type="text" id="course-exam" readonly="readonly" name="examName" value="${record.course_name}">
						<input type="hidden" id="course-hidden-exam" name="examId" readonly="readonly" value="${record.course_id}">
						<input type="text" name="examDemo" id="examDemo" placeholder="输入名称搜索课程" style="float: left;width: 315px;"/>
						<select id="examList" style="float: left;width: 315px;height: 43px;"></select>
						<div style="float: right;">
							<a class="am-btn am-btn-primary" id="addExam" >选为封面课程</a>&nbsp;<a class="am-btn am-btn-primary" id="clear">清除</a>
						</div>
		          </div>
          </div>
           <div class="am-form-group" id="fenlei">
	            <label for="course-teacher" class="am-u-sm-3 am-form-label">分类课程：</label>
	            <div class="am-u-sm-9">
		                <input style="width: 50%" type="text" id="course-type" readonly="readonly" name="typeName" value="${record.type_name}">
						<input type="hidden" id="course-hidden-type" name="typeId" readonly="readonly" value="${record.type_id}">
						<input type="text" name="typeDemo" id="typeDemo" placeholder="输入名称搜索课程分类" style="float: left;width: 315px;"/>
						<select id="typeList" style="float: left;width: 315px;height: 43px;"></select>
						<div style="float: right;">
							<a class="am-btn am-btn-primary" id="addType" >选为封面课程分类</a>&nbsp;<a class="am-btn am-btn-primary" id="clearType">清除</a>
						</div>
		          </div>
          </div>
           <div class="am-form-group">
		            <div class="am-u-sm-9 am-u-sm-push-3">
		              <a class="am-btn am-btn-primary" id="saveBtn">保存修改</a>
		              <a class="am-btn am-btn-primary" id="cancel" onclick="history.go(-1);">取消</a>
		              
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
	$("#examDemo").bind("change",function(){
		$("#examDemo").empty();
		change($("#examDemo").val());
	});
	$("#article-type").val('${record.article_id}');
	$("#imglive").premage({
		width:'200px',
	    height:'200px',
	    shape: {
	        type:'round'
	    },
	    image_container:{ 
	        shape: {
	            type:'round'
	        }
	    },
	    animation: {
	        enabled: true
	    }
	});
	$("#img-live-preview-id").attr("src","${record.cover}");
	$("#demo").bind("change",function(){
		$("#demo").empty();
		submit($("#demo").val());
	});
	$("#teacher").bind("change",function(){
		$("#teacher").empty();
		addTeachers($("#teacher").val());
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
	add();
	addTeachers('');
	addType('');
	var st = $("#st").val();
	if(st=="1"){
		$("#jiangshi").show();
		$("#kecheng").hide();
		$("#fenlei").hide();
	}else if(st=="0"){
		$("#kecheng").show();
		$("#jiangshi").hide();
		$("#fenlei").hide();
	}else{
		$("#kecheng").hide();
		$("#jiangshi").hide();
		$("#fenlei").show();
	}
	//选择课程
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
	$("#typeDemo").bind("change",function(){
		$("#typeDemo").empty();
		addType($("#typeDemo").val());
	});
	$('#showType').change(function(){
		var st1 = $(this).children('option:selected').val();
		if(st1=="1"){
			$("#jiangshi").show();
			$("#kecheng").hide();
			$("#fenlei").hide();
		}else if(st1=="0"){
			$("#kecheng").show();
			$("#jiangshi").hide();
			$("#fenlei").hide();
		}else{
			$("#kecheng").hide();
			$("#jiangshi").hide();
			$("#fenlei").show();
		}
    });
    //选择讲师事件
    $("#addTeacher").bind("click",function(){
		var text = $("#course-teacher").val();
		var ids = $("#course-hidden-teacher").val();
		if($("#teachers option:selected").val() == ''){
			return;
		}
		$("#course-hidden-teacher").val($("#teachers option:selected").val());
		$("#course-teacher").val($("#teachers option:selected").text());
	});
//     课程清除按钮事件
    $("#clear").bind("click",function(){
		$("#course-hidden-typetwo").val('');
		$("#course-typetwo").val('');
	});
    $("#clearteacher").bind("click",function(){
		$("#course-hidden-teacher").val('');
		$("#course-teacher").val('');
	});
  //清除课程分类
    $("#clearType").bind("click",function(){
		$("#course-hidden-type").val('');
		$("#course-type").val('');
	});
  //选择分类课程
	$("#addType").bind("click",function(){
		var text = $("#course-type").val();
		var ids = $("#id").val();
		if($("#typeList option:selected").val() == ''){
			return;
		}
		$("#course-hidden-type").val($("#typeList option:selected").val());
		$("#course-type").val($("#typeList option:selected").text());
	});
  
});
function save(){
	var name = $("#teacher-name").val();
	if(name == ''){
		showInfo("请输入Banner描述!",function(){},3000);
		return;
	}	
	var weight = $("#teacher-weight").val();
	if(weight == ''){
		showInfo("请输入类别排序值!",function(){},3000);
		return;
	}	
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/banner/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/banner";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
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
		$("#examList").html(html);
	});
}
function change(str){
	$.getJSON("/admin/catalog/query?demo="+str,function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#examList").html(html);
	});
}
function addTeachers(str){
	$.getJSON("/admin/member/findAllTeachers?name="+str,function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#teachers").html(html);
	});
	
}
function addType(str){
	$.getJSON("/admin/banner/query?name="+str,function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#typeList").html(html);
	});
}
</script>
</html>