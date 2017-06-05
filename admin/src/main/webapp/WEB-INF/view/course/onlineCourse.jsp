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

<link href="/admin/ue/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
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
		编辑录音课程
	</div>
    <hr/>
    <div class="am-g">
      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>
		
      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        
          <div class="am-form-group">
            <label class="am-u-sm-3 am-form-label">头图：</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
             	<input type="hidden" name="courseId" id="courseId" value="${course.id}">
             	<input type="hidden" name="type" value="1">
            </div>
          </div>
        
          <div class="am-form-group">
            <label for="course-name" class="am-u-sm-3 am-form-label">课程名称：</label>
            <div class="am-u-sm-9">
              <input type="text" name="courseName" id="course-name" value="${course.course_name}" placeholder="课程名称"  >
            </div>
          </div>
          <div class="am-form-group">
            <label for="subtitle" class="am-u-sm-3 am-form-label">课程子标题：</label>
            <div class="am-u-sm-9">
              <input type="text" name="subtitle" id="subtitle" value="${course.subtitle}" placeholder="课程子标题"  >
            </div>
          </div>

           <div class="am-form-group">
            <label for="course-coursePrice" class="am-u-sm-3 am-form-label">价格：</label>
            <div class="am-u-sm-9">
              <input type="text" name="coursePrice" id="course-coursePrice" value="${online.price}" placeholder="价格"  >
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="course-beginTime" class="am-u-sm-3 am-form-label">开课时间：</label>
            <div class="am-u-sm-9">
              <input type="text" name="beginTime" id="course-beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${course.begin_time}" pattern="yyyy-MM-dd HH:mm:ss" />" placeholder="例 : 2015-07-30">
            </div>
          </div>
          <div class="am-form-group">
            <label for="course-beginTime" class="am-u-sm-3 am-form-label">开课时间：</label>
            <div class="am-u-sm-9">
              <input type="text" name="beginTime" id="course-beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${course.begin_time}" pattern="yyyy-MM-dd HH:mm:ss" />" placeholder="例 : 2015-07-30">
            </div>
          </div>
		 
		 <div id="onlineDiv">
		 	  <div class="am-form-group">
		            <label for="online-playAddress" class="am-u-sm-3 am-form-label">播放地址：</label>
		            <div class="am-u-sm-9">
		              	<select id="online-playAddress" name="playAddress">
		              		<c:forEach var="obj" items="${addressList}">
		              		<option value="${obj.id}">${obj.address_name}</option>
		              		</c:forEach>
		              	</select>
		            </div>
		       </div>
		       
		       <div class="am-form-group">
		            <label class="am-u-sm-3 am-form-label">直播开始时间：</label>
		            <div class="am-u-sm-9">
		              	<select id="online-playStartHour" name="playStrtHour" style="width: 80px;float: left;">
		              		<c:forEach var="obj" begin="5" end="23">
		              		<option value="${obj}">${obj}</option>
		              		</c:forEach>
		              	</select>
		              	<span style="float: left;font-size: 1.6rem;line-height: 1.2;color: #555;vertical-align: middle;padding-top: 10px">&nbsp;&nbsp;点&nbsp;&nbsp;</span>
		              	<select id="online-playStartMinute" name="playStartMinute" style="width: 80px;float: left;">
		              		<c:forEach var="obj" begin="0" end="55" step="5">
		              		<option value="${obj}">${obj}</option>
		              		</c:forEach>
		              	</select>
		            </div>
		       </div>
		 </div>
		 
		  <div class="am-form-group">
            <label for="course-teacher" class="am-u-sm-3 am-form-label">讲师：</label>
            <div class="am-u-sm-9">
              <input type="text" name="courseTeacher" id="course-teacher" value="${online.teacher}" placeholder="讲师"  >
            </div>
          </div>
          
            <div class="am-form-group">
            <label for="course-tag" class="am-u-sm-3 am-form-label">课程标签：</label>
            <div class="am-u-sm-9">
              <input type="text" name="courseTag" id="course-tag" value="${online.tag}" placeholder="课程标签"  >
            </div>
          </div>
          <div class="am-form-group">
	            <label for="offline-type" class="am-u-sm-3 am-form-label">课程类型：</label>
	            <div class="am-u-sm-9">
	              	<select id="online-type" name="onlineType">
	              		<c:forEach var="obj" items="${onlineType}">
	              			<c:if test="${online.online_type_id eq obj.id}">
	              				<option value="${obj.id}" selected="selected">${obj.value}</option>
	              			</c:if>
	              			<c:if test="${online.online_type_id != obj.id}">
	              				<option value="${obj.id}">${obj.value}</option>
	              			</c:if>
	              			
	              		</c:forEach>
	              	</select>
	            </div>
	        </div>
	        <div class="am-form-group">
	            <label for="offline-type" class="am-u-sm-3 am-form-label">课程级别：</label>
	            <div class="am-u-sm-9">
	              	<select id="online-type" name="level">
	              	<c:if test="${course.level eq 0}">
	              		<option value="0" selected="selected">初级</option>
	              		<option value="1">中级</option>
	              		<option value="2">高级</option>
	              	</c:if>
	              	<c:if test="${course.level eq 1}">
	              		<option value="0">初级</option>
	              		<option value="1"  selected="selected">中级</option>
	              		<option value="2">高级</option>
	              	</c:if>
	              	<c:if test="${course.level eq 2}">
	              		<option value="0">初级</option>
	              		<option value="1">中级</option>
	              		<option value="2" selected="selected">高级</option>
	              	</c:if>
	              	<c:if test="${empty course.level}">
	              		<option value="0">初级</option>
	              		<option value="1">中级</option>
	              		<option value="2" selected="selected">高级</option>
	              	</c:if>
	              	</select>
	            </div>
	        </div>
          <div class="am-form-group">
            <label for="course-beginTime" class="am-u-sm-3 am-form-label">课程介绍：</label>
            <div class="am-u-sm-9">
             	<script id="editor" type="text/plain" style="width:515px;height:300px;">${online.introduction}</script>
		             <input type="hidden" name="introduction" id="introduction" />
            </div>
          </div>
          
          <div class="am-form-group">
		     <label for="article-tag" class="am-u-sm-3 am-form-label">课程简介：</label>
		      <div class="am-u-sm-9">
		           <textarea name="synopsis" >${course.synopsis}</textarea>
		       </div>
		   </div> 
          <div class="am-form-group">
            <div class="am-u-sm-9 am-u-sm-push-3">
              <a class="am-btn am-btn-primary" id="saveBtn">保存修改</a>
              
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
var ue = UE.getEditor('editor');

function save(){
	$("#courseContent").val($("#content").val());
	
	var teacher = $("#course-teacher").val();
	if(teacher == ''){
		showInfo("请输入讲师!",function(){},3000);
		return;
	}
	var courseName = $("#course-name").val();
	if(courseName == ''){
		showInfo("请输入课程名称!",function(){},3000);
		return;
	}
	var price = $("#course-coursePrice").val();
	if(price == ''){
		showInfo("请输入课程价格!",function(){},3000);
		return;
	}
	if(!/^[0-9]*$/.test(price) && !/^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/.test(price)){
		showInfo("价格格式不正确!",function(){},3000);
		return;
	}
	var type = $("#course-type option:selected").val();
	if(type == '' || type == -1){
		showInfo("请输选择课程类型!",function(){},3000);
		return;
	}
	/* var beginTime = $("#course-beginTime").val();
	if(beginTime == ''){
		showInfo("请输入课程开始时间!",function(){},3000);
		return;
	}
	if(!beginTime.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/) && !beginTime.match(/^((?:19|20)\d\d)-([1-9]|1[012])-([1-9]|[12][0-9]|3[01])$/)) { 
		showInfo("请输入正确的课程开始时间!",function(){},3000);
		return;
	} */
		var playAddress = $("#online-playAddress option:selected").val();
		if(playAddress == '' || playAddress == 0){
			showInfo("请选择播放地址!",function(){},3000);
			return;
		}
		
		var playStartHour = $("#online-playStartHour option:selected").val();
		var playEndHour = $("#online-playEndHour option:selected").val();
		var playStartMinute = $("#online-playStartMinute option:selected").val();
		var playEndMinute = $("#online-playEndMinute option:selected").val();
		if(parseInt(playStartHour) > parseInt(playEndHour)){
			showInfo("结束时间必须大于开始时间!",function(){},3000);
			return;
		}
		if(parseInt(playStartHour) == parseInt(playEndHour)){
			if(playStartMinute >= playEndMinute){
				showInfo("结束时间必须大于开始时间!",function(){},3000);
				return;
			}
		}
		if(!ue.hasContents()){
			showInfo("请输入课程简介",function(){},3000);
			return;
		}
		$("#introduction").val(ue.getContent()); 
		/* if(!ue.hasContents()){
			showInfo("请输入课程简介",function(){},3000);
			return;
		}
		$("#courseContent").val(ue.getContent()); */
		var options ={
			url:"/admin/course/saveOnlineCourse",
			success : function(data) {
				if(data.status == 200){
	            	window.location.href = "/admin/course/online";
	            }else{
	            	showInfo(data.msg,function(){},3000);
	            }
			}
		};
		$("#myform").ajaxSubmit(options);	
	}

$(function(){
	var id = $("#courseId").val();
	if(id != '' && id > 0){
		var type = $("#course-type option:selected").val();
				$("#online-playAddress").val('${online.play_address_id}');
				$("#online-playStartHour").val('${startHour}');
				$("#online-playStartMinute").val('${startMinute}');
		
	}
	
	$("#saveBtn").bind("click", function(){  
		save();
	});
	$("#imglive").premage({
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
	
	$("#img-live-preview-id").attr("src","${course.avatar}");
});
</script>
</html>