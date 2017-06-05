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
<link href="/admin/ue/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/lang/zh-cn/zh-cn.js"></script>
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
      添加推送
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        	
          <div class="am-form-group">
            <label for="push-content" class="am-u-sm-3 am-form-label">推送内容：</label>
            <div class="am-u-sm-9">
              <input type="text" name="content" id="push-content" placeholder="推送内容" >
            </div>
          </div>

		 <div class="am-form-group">
            <label for="push-type" class="am-u-sm-3 am-form-label">推送类型：</label>
            <div class="am-u-sm-9">
              	<select id="push-type" name="type" onchange="changeType()">
              		<option value="0" selected="selected">推送线上课程</option>
              		<option value="1">推送其他课程</option>
              	</select>
            </div>
          </div>
          
          <div id="onlineDiv" class="am-form-group">
            <label for="push-courseId" class="am-u-sm-3 am-form-label">线上课程：</label>
            <div class="am-u-sm-9">
            	<select id="push-courseId" name="courseId" >
            	<c:forEach items="${list}" var="obj">
            	<option value="${obj.id}">${obj.course_name }</option>
            	</c:forEach>
              		
              	</select>
            </div>
          </div>

          <div class="am-form-group">
            <div class="am-u-sm-9 am-u-sm-push-3">
              <a class="am-btn am-btn-primary" id="saveBtn">提交推送</a>
              
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
});
function save(){
	var content = $("#push-content").val();
	if(content == ''){
		showInfo("请输入内容!",function(){},3000);
		return;
	}
	var type = $("#push-type").val();
	if(type == 0){
		var courseId = $("#push-courseId").val();
		if(courseId == ''){
			showInfo("请输入课程ID!",function(){},3000);
			return; 
		}
	}
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/push/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/push";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
}

function changeType(){
	var type = $("#push-type").val();
	if(type == 1){
		$("#onlineDiv").hide();
	}else{
		$("#onlineDiv").show();
	}
	
}
</script>
</html>