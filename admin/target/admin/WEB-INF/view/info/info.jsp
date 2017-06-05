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

	<header class="am-topbar admin-header">
		<%@include file="/base/top.jsp"%>
	</header>

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar">
			<%@include file="/base/left.jsp"%>
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
    <div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">发送短信</strong> / <small>Edit article</small></div>
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        	
          <div class="am-form-group">
            <label for="article-type" class="am-u-sm-3 am-form-label">线下课程列表</label>
            <div class="am-u-sm-9">
              	<select id="courseId" name="courseId">
              		<c:forEach var="obj" items="${courseList}">
              		<option value="${obj.id}">${obj.value}</option>
              		</c:forEach>
              	</select>
            </div>
          </div>

	       	
	       	<div class="am-form-group">
	            <label for="article-tag" class="am-u-sm-3 am-form-label">短信内容</label>
	            <div class="am-u-sm-9">
	            	<textarea name="text" id="infoText" style="height: 200px"></textarea>
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
$(function(){
	$("#saveBtn").bind("click", function(){  
		save();
	});
});

function save(){
	var infoText = $("#infoText").val();
	if(infoText == ''){
		showInfo("请输入信息!",function(){},3000);
		return;
	}
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/info/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/info";
	        }else if(data.status == 888){
	        	showConfirm("有短信发送失败!", function() {
	        		alert(data.data);
	        	});
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
}
</script>
</html>