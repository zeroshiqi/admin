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
      编辑同学录
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
          <div class="am-form-group">
            <label for="user-head" class="am-u-sm-3 am-form-label">头像：</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
            </div>
          </div>
<!--         <div class="am-form-group"> -->
<!--             <label for="user-head" class="am-u-sm-3 am-form-label">头像：</label> -->
<!--             <div class="am-u-sm-9"> -->
<!--              	<input type="file" name="imglive1" id="imglive1" accept="image/png, image/jpeg, image/jpg" /> -->
<!--              	<img style="width: 200px;height: 200px;" class="preview-image" id="img-live-preview-id1"> -->
<!--             </div> -->
<!--           </div> -->
          <div class="am-form-group">
            <label for="teacher-name" id="teacherNameId" class="am-u-sm-3 am-form-label">姓名：</label>
            <div class="am-u-sm-9">
            	<input type="hidden" name="id" value="${record.id}">
              <input type="text" id="teacher-name" name="name" placeholder="" value="${record.name}">
            </div>
          </div>
           <div class="am-form-group">
            <label for="teacher-job" id="teacherJobId" class="am-u-sm-3 am-form-label">标题：</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-job" name="title" placeholder="标题" value="${record.title}">
            </div>
          </div>
           <div class="am-form-group">
            <label for="teacher-job" id="teacherCityId" class="am-u-sm-3 am-form-label">城市：</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-city" name="city" placeholder="城市" value="${record.city}">
            </div>
          </div>
           <div class="am-form-group">
            <label for="teacher-job" id="teacherTndustryId" class="am-u-sm-3 am-form-label">行业：</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-industry" name="industry" placeholder="行业" value="${record.industry}">
            </div>
          </div>
           <div class="am-form-group">
            <label for="job" id="job" class="am-u-sm-3 am-form-label">职位：</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-job" name="job" placeholder="职位" value="${record.job}">
            </div>
          </div>
           <div class="am-form-group">
            <label for="weight" id="weight" class="am-u-sm-3 am-form-label">显示权重值：</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-weight" name="weight" placeholder="" value="${record.weight}">
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">描述：</label>
            <div class="am-u-sm-9">
<%--               <input type="text" id="q-question" name="question" placeholder="问题" value="${record.title}"> --%>
              <textarea rows="5" cols="" id="q-bewrite" name="bewrite" placeholder="描述" value="">${record.bewrite}</textarea>
            </div>
          </div>
           <div class="am-form-group">
            <label for="user-gongzhong" class="am-u-sm-3 am-form-label">简介：</label>
            <div class="am-u-sm-9">
            	<script id="editor" type="text/plain" style="width:515px;height:300px;">${record.content}</script>
		             <input type="hidden" name="content" id="teacherInfo" />
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
var type = '${record.type}';
var ue = UE.getEditor('editor');

$(function(){
	$("#saveBtn").bind("click", function(){  
		save();
	});
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
	if(type != ''){
		$("#teacher-type").val(type);
	}
	$("#img-live-preview-id").attr("src","${record.cover}");
});
function save(){
	var teacherType = $("#teacher-type").val();
	var nickName = $("#teacher-name").val();
	if(nickName == ''){
		showInfo("请输入姓名!",function(){},3000);
		return;
	}	
	var job = $("#teacher-job").val();
	if(job == ''){
		showInfo("请输入职位!",function(){},3000);
		return;
	}	
	$("#teacherInfo").val(ue.getContent()); 
	
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/student/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/student";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
	
}
</script>
</html>