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
			编辑文章
		<a class="am-btn am-btn-primary" id="saveBtn2">保存修改</a>
		</div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        	<div class="am-form-group">
	            <label class="am-u-sm-3 am-form-label">封面：</label>
	            <div class="am-u-sm-9">
	             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
	            </div>
          	</div>
          <div class="am-form-group">
            <label for="article-title" class="am-u-sm-3 am-form-label">标题：</label>
            <div class="am-u-sm-9">
              <input type="text" name="title" id="article-title" placeholder="标题"  <c:if test="${record.title != null}">value="${record.title}"</c:if> >
              <input type="hidden" id="id" name="id" value="${record.id}">
<%--               <input type="hidden" id="show_type" name="show_type" value="${show_type}"> --%>
            </div>
          </div>
            <div class="am-form-group">
            <label for="article-title" class="am-u-sm-3 am-form-label">子标题：</label>
            <div class="am-u-sm-9">
              <input type="text" name="subtitle" id="article-title" placeholder="子标题"  <c:if test="${record.subtitle != null}">value="${record.subtitle}"</c:if> >
            </div>
          </div>
		  <div class="am-form-group">
            <label for="article-type" class="am-u-sm-3 am-form-label">权重：</label>
            <div class="am-u-sm-9">
                 <input type="text" name="weight" id="article-weight" placeholder="权重"  value="${record.weight }" />
            </div>
          </div>
		 <div class="am-form-group">
            <label for="article-level" class="am-u-sm-3 am-form-label">级别：</label>
            <div class="am-u-sm-9">
              	<select id="article-level" name="level">
              		<option value="1" selected="selected">初级</option>
              		<option value="2">中级</option>
              		<option value="3">高级</option>
              	</select>
            </div>
          </div>
          
            <div class="am-form-group">
            <label for="article-type" class="am-u-sm-3 am-form-label">文章类型：</label>
            <div class="am-u-sm-9">
              	<select id="article-type" name="type">
              		<c:forEach var="obj1" items="${typeList}">
              		<option value="${obj1.id}">${obj1.value}</option>
              		</c:forEach>
              	</select>
            </div>
          </div>
			
		<div class="am-form-group">
	            <label for="article-tag" class="am-u-sm-3 am-form-label">标签：</label>
	            <div class="am-u-sm-9">
	              <input type="text" name="tag" id="article-tag" placeholder="标签" value="${record.tag}" />
	            </div>
	       </div> 
	       	
	       	<div class="am-form-group">
	            <label for="article-tag" class="am-u-sm-3 am-form-label">文章简介：</label>
	            <div class="am-u-sm-9">
	            	<textarea name="synopsis" >${record.synopsis}</textarea>
	            </div>
	       </div> 
			
			<div class="am-form-group">
		            <label for="editor" class="am-u-sm-3 am-form-label">文章内容：</label>
		            <div class="am-u-sm-9">
		             	<script id="editor" type="text/plain" style="width:515px;height:300px;">${record.content}</script>
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
var type = '${record.type}';
var level = '${record.level}';
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
	/* $("#coverBtn").bind("click", function(){  
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
		
	}); */
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
		url:"/admin/selfstudy/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/selfstudy?type="+$("#show_type").val();
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
}
</script>
</html>