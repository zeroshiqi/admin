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
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑文章</strong> / <small>Edit article</small></div>
      
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
            <label for="article-title" class="am-u-sm-3 am-form-label"></label>
            <div class="am-u-sm-9">
            	<c:choose>
            		<c:when test="${fn:length(imageUrl)>0}">
            			<img src="${imageUrl}" id="coverImg" width="200px" height="100px" >
            			<input type="hidden" id="cover" name="cover" value="${coverPath}">
            		</c:when>
            		<c:otherwise>
            			<img src="${article.cover}" id="coverImg" width="200px" height="100px" >
            			<input type="hidden" id="cover" name="cover" value="${coverStr}">
            		</c:otherwise>
            	</c:choose>
            		
              <a style="cursor: pointer;" id="coverBtn">选择封面</a>
            </div>
          </div>
        	
          <div class="am-form-group">
            <label for="article-title" class="am-u-sm-3 am-form-label">标题 / title</label>
            <div class="am-u-sm-9">
              <input type="text" name="title" id="article-title" placeholder="标题 / title"  <c:if test="${article.title != null}">value="${article.title}"</c:if> >
              <input type="hidden" id="id" name="id" value="${article.id}">
            </div>
          </div>

		 <div class="am-form-group">
            <label for="article-level" class="am-u-sm-3 am-form-label">级别 / Level</label>
            <div class="am-u-sm-9">
              	<select id="article-level" name="level">
              		<option value="1" selected="selected">初级</option>
              		<option value="2">中级</option>
              		<option value="3">高级</option>
              	</select>
            </div>
          </div>
          
            <div class="am-form-group">
            <label for="article-type" class="am-u-sm-3 am-form-label">文章类型</label>
            <div class="am-u-sm-9">
              	<select id="article-type" name="type">
              		<c:forEach var="obj" items="${typeList}">
              		<option value="${obj.id}">${obj.value}</option>
              		</c:forEach>
              	</select>
            </div>
          </div>
			
		<div class="am-form-group">
	            <label for="article-tag" class="am-u-sm-3 am-form-label">标签</label>
	            <div class="am-u-sm-9">
	              <input type="text" name="tag" id="article-tag" placeholder="标签" value="${article.tag}" />
	            </div>
	       </div> 
	       	
	       	<div class="am-form-group">
	            <label for="article-tag" class="am-u-sm-3 am-form-label">文章简介</label>
	            <div class="am-u-sm-9">
	            	<textarea name="synopsis" >${article.synopsis}</textarea>
	            </div>
	       </div> 
			
			<div class="am-form-group">
		            <label for="editor" class="am-u-sm-3 am-form-label">文章内容</label>
		            <div class="am-u-sm-9">
		             	<script id="editor" type="text/plain" style="width:515px;height:300px;">${article.content}</script>
		             	<input type="hidden" name="content" id="content" />
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
var type = '${article.type}';
var level = '${article.level}';
var ue = UE.getEditor('editor');
$(function(){
	$("#article-level").val(level);
	$("#article-type").val(type);
	$("#saveBtn").bind("click", function(){  
		save();
	});
	$("#saveBtn2").bind("click", function(){  
		save();
	});
	$("#coverBtn").bind("click", function(){  
		$("#content").val(ue.getContent());
		$('#myform').ajaxSubmit({
			type:'post',
			url:"/admin/article/saveTempArticle",
		    success:function(data){
		        if(data.status == 200){
		        	window.location.href = "/admin/article/addCover";
		        }else{
		        	$("#saveBtn").removeAttr('disabled');
		            showInfo(data.msg,function(){},3000);
		        }
		    }
		});
		
	});
});

function save(){
	var title = $("#article-title").val();
	if(title == ''){
		showInfo("请输入标题!",function(){},3000);
		return;
	}
	var level = $("#article-level").val();
	if(level == ''){
		showInfo("请选择级别!",function(){},3000);
		return;
	}
	var type = $("#article-type").val();
	if(type == ''){
		showInfo("请选择类别!",function(){},3000);
		return;
	}
	//var url = $("#article-URL").val();
	if(!ue.hasContents() ){
		showInfo("请输入文章内容",function(){},3000);
		return;
	}
	
	$("#content").val(ue.getContent());
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/article/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/article";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
}
</script>
</html>