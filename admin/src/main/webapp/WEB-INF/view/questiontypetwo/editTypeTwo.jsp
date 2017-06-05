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
<link rel="stylesheet" href="/admin/premage/premage.min.css">
<script src="/admin/premage/premage.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/pf/pf.js?a=33"></script>

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
      编辑问题二级目录
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        	
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">二级目录名称：</label>
            <div class="am-u-sm-9">
            	<input type="hidden" name="id" value="${record.id }">
              	<input type="text" id="q-title" name="title" placeholder="名称" value="${record.title}">
             </div>
             </br>
<%--               	<input type="text" id="q-parent-title" name="parent" placeholder="所属一级目录名称" value="${record.parent_name}"> --%>
				<label for="q-question" class="am-u-sm-3 am-form-label">所属一级目录：</label>
              	<div class="am-u-sm-9">
	              <input type="text" id="course-typeone" readonly="readonly" name="parentName" value="${record.parent_name}">
	              <input type="hidden" id="course-hidden-typeone" name="parentId" readonly="readonly" value="${record.parent_id}">
	              <br>
	              <form class="am-form am-form-horizontal" id="myformq" method="post" >
	              	<input type="text" name="demo" id="demo" placeholder="输入名称搜索二级目录" style="float: left;width: 315px;"/>
	              </form>
	              <select id="typeone" style="float: left;width: 315px;height: 43px;"></select>
	              <div style="float: none;">
	              	<a class="am-btn am-btn-primary" id="addTypeone" >添加</a>&nbsp;<a class="am-btn am-btn-primary" id="clear">清除</a>
	              </div>
	            </div>
	            <label for="q-question" class="am-u-sm-3 am-form-label">考试题数：</label>
	            <div class="am-u-sm-9">
	              	<input type="text" id="q-number" name="number" placeholder="名称" value="${record.number}">
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
var answer = '${record.answer}';
$(function(){
	$("#saveBtn").bind("click", function(){ 
		save();
	});
	
	$("#demo").bind("change",function(){
		$("#demo").empty();
		submit($("#demo").val());
	});
	$("#addTypeone").bind("click",function(){
		var text = $("#course-typeone").val();
		var ids = $("#id").val();
		if($("#typeone option:selected").val() == ''){
			return;
		}
		$("#course-hidden-typeone").val($("#typeone option:selected").val());
		$("#course-typeone").val($("#typeone option:selected").text());
	});
	$("#clear").bind("click",function(){
		$("#course-hidden-typeone").val('');
		$("#course-typeone").val('');
	});
	add();
// 	$("#img-live-preview-id").attr("src","${course.avatar}");
});
function save(){
	var title = $("#q-title").val();
	var number = $("#q-number").val();
	if(title == ''){
		showInfo("请输入二级目录标题!",function(){},3000);
		return;
	}
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/typetwo/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/typetwo";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
}
function add(){
	$.getJSON("/admin/typeone/query",function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#typeone").html(html);
	});
}
function submit(str){
	$('#myformq').ajaxSubmit({
		type:'post',
		url:"/admin/typeone/query",
	    success:function(result){
	        var html = "";
	    	for(var i=0;i<result.length;i++){
	    		html += "<option value='"+result[i].id+"'>";
	    		html += result[i].value;
	    		html += "</option>";
	    	$("#typeone").html(html);
	        }
	    }
	});
}
</script>
</html>