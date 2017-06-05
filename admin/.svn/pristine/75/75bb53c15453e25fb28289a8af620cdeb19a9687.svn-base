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
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑问题</strong> / <small>Edit Question</small></div>
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        	
          <div class="am-form-group">
            <label for="q-question" class="am-u-sm-3 am-form-label">问题</label>
            <div class="am-u-sm-9">
              <input type="text" id="q-question" name="question" placeholder="问题" value="${record.title}">
              <input type="hidden" name="id" value="${record.id }">
            </div>
          </div>

		 <div class="am-form-group">
            <label for="q-optionA" class="am-u-sm-3 am-form-label">A选项</label>
            <div class="am-u-sm-9">
              	<input type="text" id="q-optionA" name="optionA" placeholder="A选项" value="${record.a}">
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="q-optionB" class="am-u-sm-3 am-form-label">B选项</label>
            <div class="am-u-sm-9">
              	<input type="text" id="q-optionB" name="optionB" placeholder="B选项" value="${record.b}">
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="q-optionC" class="am-u-sm-3 am-form-label">C选项</label>
            <div class="am-u-sm-9">
              	<input type="text" id="q-optionC" name="optionC" placeholder="C选项" value="${record.c}">
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="q-optionD" class="am-u-sm-3 am-form-label">D选项</label>
            <div class="am-u-sm-9">
              	<input type="text" id="q-optionD" name="optionD" placeholder="D选项" value="${record.d}">
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="q-answer" class="am-u-sm-3 am-form-label">正确答案</label>
            <div class="am-u-sm-9">
             	<select id="q-answer" name="answer">
              		<option value="A" selected="selected">A</option>
              		<option value="B">B</option>
              		<option value="C">C</option>
              		<option value="D">D</option>
              	</select>
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="q-type" class="am-u-sm-3 am-form-label">试题类型</label>
            <div class="am-u-sm-9">
             	<select id="q-type" name="qType">
              		<option value="1" selected="selected">基础</option>
              		<option value="2">增强</option>
              	</select>
            </div>
          </div>
           <div class="am-form-group">
            <label for="q-userName" class="am-u-sm-3 am-form-label">出题人</label>
            <div class="am-u-sm-9">
              	<input type="text" id="q-userName" name="userName" placeholder="出题人" value="${record.user_name}">
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
	if(answer != ''){
		$("#q-answer").val(answer);
	}
	
	$("#saveBtn").bind("click", function(){  
		save();
	});
});
function save(){
	var question = $("#q-question").val();
	if(question == ''){
		showInfo("请输入问题!",function(){},3000);
		return;
	}
	var a = $("#q-optionA").val();
	if(a == ''){
		showInfo("请输入A选项!",function(){},3000);
		return;
	}
	var b = $("#q-optionB").val();
	if(b == ''){
		showInfo("请输入B选项!",function(){},3000);
		return;
	}
	var c = $("#q-optionC").val();
	if(c == ''){
		showInfo("请输入C选项!",function(){},3000);
		return;
	}
	var d = $("#q-optionD").val();
	if(d == ''){
		showInfo("请输入D选项!",function(){},3000);
		return;
	}
	var userName = $("#q-userName").val();
	if(userName == ''){
		showInfo("请输入出题人!",function(){},3000);
		return;
	}
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/question/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/question";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
	
}
</script>
</html>