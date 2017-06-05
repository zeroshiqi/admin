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
	<div class="nav-assist" style="background:#0e90d2">
	<a  class="icon-reply" href="javascript:history.go(-1);" style="float:left;color:#fff;padding:12px 20px"></a>
	</div>

		<!-- content start -->
		<div class="admin-content">
    <div class="minTitle">
      编辑招聘类别
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        	
          <div class="am-form-group">
            <label for="teacher-name" id="teacherNameId" class="am-u-sm-3 am-form-label">类别名称：</label>
            <div class="am-u-sm-9">
            	<input type="hidden" name="id" value="${record.id}">
              <input type="text" id="teacher-name" name="value" placeholder="" value="${record.value}">
            </div>
          </div>
          
		 <div class="am-form-group">
            <label for="teacher-weight" id="teacherJobId" class="am-u-sm-3 am-form-label">排序值：</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-weight" name="weight" placeholder="标题" value="${record.weight}">
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
});
function save(){
	var name = $("#teacher-name").val();
	if(name == ''){
		showInfo("请输入类别名称!",function(){},3000);
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
		url:"/admin/job/saveType",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/job/jobtype";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
	
}
</script>
</html>