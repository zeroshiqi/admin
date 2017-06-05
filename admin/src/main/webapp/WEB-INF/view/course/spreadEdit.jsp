<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<link rel="stylesheet" href="/admin/premage/premage.min.css">
<script src="/admin/premage/premage.min.js"></script>
<link href="/admin/ue/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>

	<header>
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
    	编辑课程
    </div>

    <hr/>

    <div class="am-g">
      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>
		
      <div class="am-u-sm-12 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        <div class="am-form-group">
            <label for="course-name" class="am-u-sm-3 am-form-label">课程名称：</label>
            <div class="am-u-sm-9">
              <input type="text" name="course_name" id="course_name" value="${spread.course_name}" placeholder="课程名称">
              <input type="hidden" name="id" value="${spread.id }"/>
            </div>
          </div>
          <div class="am-form-group">
            <label for="course-name" class="am-u-sm-3 am-form-label">推广码：</label>
            <div class="am-u-sm-9">
              <input type="text" name="from1" id="from1" value="${spread.from1}" placeholder="推广码"  >
            </div>
          </div>
          <div class="am-form-group">
            <label for="promotion_party" class="am-u-sm-3 am-form-label">推广方：</label>
            <div class="am-u-sm-9">
				 <input type="text" name="promotion_party" id="promotion_party" value="${spread.promotion_party}" placeholder="推广方"  >
            </div>
          </div>
          <div class="am-form-group">
            <label for="cooperation_mode" class="am-u-sm-3 am-form-label">合作方式：</label>
            <div class="am-u-sm-9">
             	 <input type="text" name="cooperation_mode" id="cooperation_mode" value="${spread.cooperation_mode}" placeholder="合作方式"  >
            </div>
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
function save(){
	$("#courseContent").val($("#content").val());
	var from1 = $("#from1").val();
	if(from1 == ''){
		showInfo("请输入推广码!",function(){},3000);
		return;
	}
	var options ={
			url:"/admin/course/saveSpread",
			success : function(data) {
				if(data.status == 200){
						window.location.href = "/admin/course/spread";
	            }else{
	            	showInfo(data.msg,function(){},3000);
	        }
		}
	};
		$("#myform").ajaxSubmit(options);		
}
$(function(){
	$("#saveBtn").bind("click", function(){  
		save();
	});
});

</script>
</html>