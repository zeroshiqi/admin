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
		编辑课程二级分类信息
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
           <div class="am-form-group">
            <label class="am-u-sm-3 am-form-label">头图：</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">课程分类名称：</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-name" name="name" placeholder="分类名称" value="${record.name}">
              <input type="hidden" name="id" id="q-id" value="${record.id}">
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">子标题名称：</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-subtitle" name="subtitle" placeholder="子标题名称" value="${record.subtitle}">
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">搜索权重值：</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-weight" name="weight" placeholder="权重值" value="${record.weight}">
            </div>
          </div>
      	<div class="am-form-group">
	          	<label for="q-question" class="am-u-sm-3 am-form-label">所属的一级分类：</label>
	              <div class="am-u-sm-9">
			              <input style="width: 50%" type="text" id="course-typetwo" readonly="readonly" name="parentName" value="${record.parent_name}">
			              <input type="hidden" id="course-hidden-typetwo" name="parentId" readonly="readonly" value="${record.parent_id}">
			              <form class="am-form am-form-horizontal" id="myform" method="post" >
			              	<input type="text" name="demo" id="demo" placeholder="输入名称搜索一级分类" style="float: left;width: 215px;"/>
			              </form>
			              <select id="typetwo" style="float: left;width: 315px;height: 43px;"></select>
			              <div style="float: none;">
			              	<a class="am-btn am-btn-primary" id="addTypetwo" >选为父类</a>&nbsp;<a class="am-btn am-btn-primary" id="clear">清除</a>
			              </div>
		           </div>
		   	</div>
		  	<div class="am-form-group">
	            <div class="am-u-sm-9 am-u-sm-push-3">
	            <center>
	              <a class="am-btn am-btn-primary" id="saveBtn">保存修改</a>
	              <a class="am-btn am-btn-primary" id="cancel" onclick="history.go(-1);">取消</a>
	              </center>
	            </div>
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
	$("#clear").bind("click",function(){
		$("#course-hidden-typetwo").val('');
		$("#course-typetwo").val('');
	});
	$("#demo").bind("change",function(){
		$("#demo").empty();
		submit($("#demo").val());
	});
	$("#addTypetwo").bind("click",function(){
		var text = $("#course-typetwo").val();
		var ids = $("#id").val();
		if($("#typetwo option:selected").val() == ''){
			return;
		}
		$("#course-hidden-typetwo").val($("#typetwo option:selected").val());
		$("#course-typetwo").val($("#typetwo option:selected").text());
	});
	add();
	$("#imglive").premage({
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
	
	$("#img-live-preview-id").attr("src","http://www.chazuomba.com/file"+"${record.avatar}");
});
function save(){
	var name = $("#q-name").val();
	if(name == ''){
		showInfo("请输入分类名称!",function(){},3000);
		return;
	}
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/catalogtwo/save?name="+$("#q-name").val()+"&id="+$("#q-id").val()+"&subtitle="+$("#q-subtitle").val()+"&weight="+$("#q-weight").val()+"&parentId="+$("#course-hidden-typetwo").val()+"&parentName="+$('#course-typetwo').val(),
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/catalogtwo";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
}
function add(){
	$.getJSON("/admin/catalogtwo/query",function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#typetwo").html(html);
	});
}
</script>
</html>