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
      课程内容
    </div>

    <hr/>

    <div class="am-g">
      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>
		
      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
          <input type="hidden" name="courseId" id="courseId" value="${courseId}">
          
            <div class="am-form-group">
		            <label for="course-studentNum" class="am-u-sm-3 am-form-label"></label>
		            <div class="am-u-sm-9">
		             ${title }
		            </div>
		        </div>
          
          		<div class="am-form-group">
		            <label for="editor" class="am-u-sm-3 am-form-label">
		            	<c:if test="${type==1}">预告内容:</c:if>
		            	<c:if test="${type==2}">直播内容:</c:if>
		            	<c:if test="${type==3}">往期课程内容:</c:if>
		            </label>
		            <div class="am-u-sm-9">
		            <script id="editor" type="text/plain" style="width:515px;height:300px;">${content}</script>
		             <input type="hidden" name="content" id="courseContent" />
		            </div>
		        </div>
		        <input  type="hidden" name="type" value='${type}' />
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

$(function(){
	$("#saveBtn").bind("click", function(){  
		save();
	});
});
function save(){
	if(!ue.hasContents()){
		showInfo("请输入课程简介",function(){},3000);
		return;
	}
	$("#courseContent").val(ue.getContent()); 
	var options ={
		url:"/admin/course/saveContent",
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
</script>
</html>