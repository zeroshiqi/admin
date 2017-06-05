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
      	编辑课程分类信息  <!-- 标题 -->
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
           <div class="am-form-group">
            <label class="am-u-sm-3 am-form-label">背景：</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
            </div>
          </div>
        <div class="am-form-group">
            <label for="user-head" class="am-u-sm-3 am-form-label">封面：</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive1" id="imglive1" accept="image/png, image/jpeg, image/jpg" />
              	<img style="width: 200px;height: 200px;" class="preview-image" id="img-live-preview-id1">
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">课程分类名称：</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-name" name="name" placeholder="分类名称" value="${record.name}">
              <input type="hidden" name="id" id="q-id" value="${record.id}">
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">子标题名称：</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-subtitle" name="subtitle" placeholder="子标题名称" value="${record.subtitle}">
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">分类属性：</label>
            <div class="am-u-sm-9">
              	<select id="type" name="type2">
              		<option value="0" selected="selected">课程</option>
              		<option value="1">讲师</option>
              	</select>
            </div>
          </div>
          <div class="am-form-group">
            <label for="total" class="am-u-sm-3 am-form-label">课程数量：</label>
            <div class="am-u-sm-9">
				<input type="text" id="total" name="total" placeholder="分类课程包含的课程总数" value="${record.total}">
            </div>
          </div>
           <div class="am-form-group">
	            <label for="course-teacher" class="am-u-sm-3 am-form-label">讲师：</label>
	            <div class="am-u-sm-9">
	              <input type="text" id="course-teacher" name="teacherNames" readonly="readonly" value="${teacherNames}">
	              <input type="hidden" id="course-hidden-teacher" name="teacher" value="${record.teacher_id}" readonly="readonly">
	              <br>
	              <input type="text" id="teacher" value="" style="float: left;width: 200px">
	              <select id="teachers" style="float: left;width: 315px">
	              </select>
	              <br>
	              <div style="float: none;">
	              <a class="am-btn am-btn-primary" id="addTeacher" >添加</a>&nbsp;<a class="am-btn am-btn-primary" id="clear">清除</a>
	              </div>
	            </div>
	          </div>
           <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">展示形式：</label>
            <div class="am-u-sm-9">
              	<select id="representation" name="type" >
              		<c:if test="${record.flag == 0}">
	              		<option value="0" selected="selected">一级目录形式</option>
              			<option value="1">二级目录形式</option>
	              	</c:if>
              		<c:if test="${record.flag == 1}">
	              		<option value="0">一级目录形式</option>
              			<option value="1" selected="selected">二级目录形式</option>
	              	</c:if>
	              	<c:if test="${record.flag eq null}">
	              		<option value="0">一级目录形式</option>
              			<option value="1" selected="selected">二级目录形式</option>
	              	</c:if>
              	</select>
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">搜索权重值：</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-weight" name="weight" placeholder="权重值" value="${record.weight}">
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
	$("#clear").bind("click",function(){
		$("#course-hidden-typetwo").val('');
		$("#course-typetwo").val('');
	});
	$("#imglive").premage({
		width:'500px',
	    height:'200px',
	    shape: {
	        type:'square'
	    },
	    image_container:{ 
	        shape: {
	            type:'square'
	        }
	    },
	    animation: {
	        enabled: true
	    }
	});
	$("#imglive1").premage({
		width:'200px',
	    height:'200px',
	    shape: {
	        type:'square'
	    },
	    image_container:{ 
	        shape: {
	            type:'square'
	        }
	    },
	    animation: {
	        enabled: true
	    }
	});
	$("#img-live-preview-id1").attr("src","${record.cover}");
	$("#type").val('${type}');
	$("#teacher").bind("change",function(){
		$("#teachers").empty();
		add($("#teacher").val());
	});
	add("");
	$("#addTeacher").bind("click",function(){
		var text = $("#course-teacher").val();
		var ids = $("#course-hidden-teacher").val();
		if($("#teachers option:selected").val() == ''|| $("#teachers").val()==null || $("#teachers option:selected").val() == 'undefined'){
			alert("选择的讲师无效！");
			return;
		}
		if(text == ''){
			$("#course-hidden-teacher").val($("#teachers option:selected").val());
			$("#course-teacher").val($("#teachers option:selected").text());
		}else{
			$("#course-hidden-teacher").val(ids + "," +$("#teachers option:selected").val());
			$("#course-teacher").val(text + "," + $("#teachers option:selected").text());
		}
	});
	$("#clear").bind("click",function(){
		$("#course-hidden-teacher").val('');
		$("#course-teacher").val('');
	});
	$("#img-live-preview-id").attr("src","${record.avatar}");
});
function save(){
	var name = $("#q-name").val();
	if(name == ''){
		showInfo("请输入分类名称!",function(){},3000);
		return;
	}
	var teacher = $("#course-hidden-teacher").val(); 
	if(teacher == ''){
		showInfo("请选择讲师!",function(){},3000);
		return;
	}
	var total = $("#total").val();
	if(total == ''){
		showInfo("输入课程包包含的课程数量!",function(){},3000);
		return;
	}
	var flag = $("#representation option:selected").val();
	var type = $("#type").val();
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/catalog/save?name="+$("#q-name").val()+"&id="+$("#q-id").val()+"&subtitle="+$("#q-subtitle").val()+"&weight="+$("#q-weight").val()+"&flag="+flag+"&type="+type+"&teacher="+$("#course-hidden-teacher").val()+"&teacherNames="+$("#course-teacher").val()+"&total="+$("#total").val(),
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
function add(str){
	$.getJSON("/admin/teacher/findAllTeacher?name="+str,function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#teachers").html(html);
	});
	
}
</script>
</html>