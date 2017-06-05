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
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑职位</strong></div>
      
       <div class="am-form-group">
            <div class="am-u-sm-9 am-u-sm-push-3">
             <a class="am-btn am-btn-primary" id="saveBtn2">保存修改</a>
            </div>
          </div>
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
         <div class="am-form-group">
            <label for="user-head" class="am-u-sm-3 am-form-label">封面</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
            </div>
          </div>
        	
        	<div class="am-form-group">
            <label  class="am-u-sm-3 am-form-label">头图</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive2" id="imglive2" accept="image/png, image/jpeg, image/jpg" />
            </div>
          </div>
        	
          <div class="am-form-group">
            <label for="job-name" id="teacherNameId" class="am-u-sm-3 am-form-label">职位名称</label>
            <div class="am-u-sm-9">
            	<input type="hidden" name="id" value="${record.id}">
              <input type="text" id="job-name" name="name" placeholder="" value="${record.job_name}">
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="address" class="am-u-sm-3 am-form-label">地址</label>
            <div class="am-u-sm-9">
              <input type="text" id="address" name="address" placeholder="" value="${record.address}">
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="email" class="am-u-sm-3 am-form-label">邮箱</label>
            <div class="am-u-sm-9">
              <input type="text" id="email" name="email" placeholder="" value="${record.email}">
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="company" class="am-u-sm-3 am-form-label">公司</label>
            <div class="am-u-sm-9">
              <input type="text" id="company" name="company" placeholder="" value="${record.company}">
            </div>
          </div>
          
		 <div class="am-form-group">
            <label for="job-pay" id="teacherJobId" class="am-u-sm-3 am-form-label">薪资</label>
            <div class="am-u-sm-9">
              <input type="text" id="job-pay" name="pay" placeholder="薪资" value="${record.pay}">
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="job-tag" id="teacherJobId" class="am-u-sm-3 am-form-label">标签</label>
            <div class="am-u-sm-9">
              <input type="text" id="job-tag" name="tag" placeholder="标签" value="${record.tag}">
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="job-type" class="am-u-sm-3 am-form-label">所属分类</label>
            <div class="am-u-sm-9">
              	<select id="job-type" name="type">
              		<c:forEach var="obj" items="${types}">
              			<option value="${obj.id}">${obj.value}</option>
              		</c:forEach>
              	</select>
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="job-tags" id="teacherJobId" class="am-u-sm-3 am-form-label">底部标签(使用 - 分割)</label>
            <div class="am-u-sm-9">
              <input type="text" id="job-tags" name="tags" placeholder="例:年底双薪-技能培训" value="${record.tags}">
            </div>
          </div>
          
          <div class="am-form-group">
		     <label for="editor" class="am-u-sm-3 am-form-label">文章内容</label>
		       <div class="am-u-sm-9">
		          <script id="editor" type="text/plain" style="width:515px;height:300px;">${record.content}</script>
		           <input type="hidden" name="content" id="content" />
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
var ue = UE.getEditor('editor');
$(function(){
	$("#saveBtn").bind("click", function(){  
		save();
	});
	$("#saveBtn2").bind("click", function(){  
		save();
	});
	$("#job-type").val('${record.type_id}');
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
	$(".preview-image-round").attr("src","${record.cover}");
	$("#imglive2").premage({
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
	$(".preview-image-square").attr("src","${record.avatar}");
});
function save(){
	var email = $("#email").val();
	if(email == ''){
		showInfo("请输入邮箱!",function(){},3000);
		return;
	}	
	var address = $("#address").val();
	if(address == ''){
		showInfo("请输入地址!",function(){},3000);
		return;
	}	
	var name = $("#job-name").val();
	if(name == ''){
		showInfo("请输入类别名称!",function(){},3000);
		return;
	}	
	var pay = $("#job-pay").val();
	if(pay == ''){
		showInfo("请输入薪资!",function(){},3000);
		return;
	}	
	var company = $("#company").val();
	if(company == ''){
		showInfo("请输入公司!",function(){},3000);
		return;
	}	
	var tag = $("#job-tag").val();
	if(tag == ''){
		showInfo("请输入标签!",function(){},3000);
		return;
	}	
	if(!ue.hasContents() ){
		showInfo("请输入简介!",function(){},3000);
		return;
	}
	$("#saveBtn").attr("disabled","disabled");
	$("#content").val(ue.getContent());
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/job/saveJob",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/job";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
	
}
</script>
</html>