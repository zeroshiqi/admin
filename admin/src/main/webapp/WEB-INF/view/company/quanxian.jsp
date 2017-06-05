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
      企业下员工批量分配权限
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 maxwidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        	
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">企业名称：</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-business_name" name="bname" placeholder="企业名称" value="${record.business_name}">
              <input type="hidden" name="id" value="${record.id}">
            </div>
          </div>

          <div class="am-form-group">
            <label for="q-catalog-id" class="am-u-sm-3 am-form-label">课程包ID：</label>
            <div class="am-u-sm-9">
              	<input type="text" id="q-ctalog-id" name="catalogId" placeholder="课程包ID">
            </div>
          </div>
          <div class="am-form-group">
            <label for="q-optionB" class="am-u-sm-3 am-form-label">开通期限：</label>
            <div class="am-u-sm-9">
              	<input type="text" id="q-expiry-date" name="expiryDate" placeholder="开通期限">
            </div>
          </div>
          <div class="am-form-group">
            <div class="am-u-sm-9 am-u-sm-push-3">
              <a class="am-btn am-btn-primary" id="saveBtn">立即开通</a>
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
	if(answer != ''){
		$("#q-answer").val(answer);
	}
	
	$("#saveBtn").bind("click", function(){  
		save();
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
	$("#clear").bind("click",function(){
		$("#course-hidden-typetwo").val('');
		$("#course-typetwo").val('');
	});
// 	add();
});
function save(){
	var now = $("#").val();
	var businessName = $("#q-ctalog-id").val();
	alert(businessName);
	if(businessName == ''){
		showInfo("请输入课程包Id!",function(){},3000);
		return;
	}
	var a = $("#q-expiry-date").val();
	if(a == ''){
		showInfo("请输入开通期限!",function(){},3000);
		return;
	}
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/company/kaitong",
	    success:function(data){
	        if(data.status == 200){
	        	$("#saveBtn").removeAttr('disabled');
	        	showInfo(data.msg,function(){
	        	},3000);
	           	
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
	
}
function add(){
	$.getJSON("/admin/typetwo/query",function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#typetwo").html(html);
	});
}
function submit(str){
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/typetwo/query",
	    success:function(result){
	        var html = "";
	    	for(var i=0;i<result.length;i++){
	    		html += "<option value='"+result[i].id+"'>";
	    		html += result[i].value;
	    		html += "</option>";
	    	$("#typetwo").html(html);
	        }
	    }
	});
}
</script>
</html>