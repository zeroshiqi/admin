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
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑文章类别</strong></div>
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
            <label for="teacher-name" id="teacherNameId" class="am-u-sm-3 am-form-label">类别名称</label>
            <div class="am-u-sm-9">
            	<input type="hidden" name="id" value="${record.id}">
              <input type="text" id="teacher-name" name="value" placeholder="" value="${record.value}">
            </div>
          </div>
          
		 <div class="am-form-group">
            <label for="teacher-weight" id="teacherJobId" class="am-u-sm-3 am-form-label">排序值</label>
            <div class="am-u-sm-9">
              <input type="text" id="teacher-weight" name="weight" placeholder="标题" value="${record.weight}">
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="article-type" class="am-u-sm-3 am-form-label">显示文章</label>
            <div class="am-u-sm-9">
              	<select id="article-type" name="articleId">
              		<c:forEach var="article" items="${articles}">
              			<option value="${article.id}">${article.title}</option>
              		</c:forEach>
              	</select>
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
		url:"/admin/type/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/type";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
	
}
</script>
</html>