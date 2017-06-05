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
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑用户</strong> / <small>Edit user</small></div>
    </div>

    <hr/>

    <div class="am-g">

      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>

      <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
         <div class="am-form-group">
            <label for="user-head" class="am-u-sm-3 am-form-label">头像</label>
            <div class="am-u-sm-9">
             	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
            </div>
          </div>
        	
          <div class="am-form-group">
            <label for="user-mobile" class="am-u-sm-3 am-form-label">手机号 / mobile</label>
            <div class="am-u-sm-9">
              <input type="text" name="mobile" id="user-mobile" placeholder="手机号 / mobile"  <c:if test="${member != null}">value="${member.mobile}" readonly="readonly"</c:if> >
              <input type="hidden" id="memberId" name="memberId" value="${member.id}">
            </div>
          </div>

          <div class="am-form-group">
            <label for="user-password" class="am-u-sm-3 am-form-label">密码 / Password</label>
            <div class="am-u-sm-9">
              <input type="password" name="password" id="user-password" <c:if test="${member != null}">readonly="readonly"</c:if> placeholder="密码 / Password">
            </div>
          </div>

          <div class="am-form-group">
            <label for="user-nickName" class="am-u-sm-3 am-form-label">昵称 / NickName</label>
            <div class="am-u-sm-9">
              <input type="text" id="user-nickName" name="nickName" placeholder="昵称 / NickName" value="${member.nick_name}">
            </div>
          </div>

		 <div class="am-form-group">
            <label for="user-gender" class="am-u-sm-3 am-form-label">性别 / Gender</label>
            <div class="am-u-sm-9">
              	<select id="user-gender" name="gender">
              		<option value="男" selected="selected">男</option>
              		<option value="女">女</option>
              	</select>
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="user-birthday" class="am-u-sm-3 am-form-label">生日 / Birthday</label>
            <div class="am-u-sm-9">
              <input type="text" name="birthday" id="user-birthday" value="${info.birthday}" placeholder="2015-07-30">
            </div>
          </div>
		
		 <div class="am-form-group">
            <label for="user-company" class="am-u-sm-3 am-form-label">公司 / Company</label>
            <div class="am-u-sm-9">
              <input type="text" id="user-company" value="${info.company_name}" name="company" placeholder="公司 / Company">
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="user-job" class="am-u-sm-3 am-form-label">职位 / Job</label>
            <div class="am-u-sm-9">
              <input type="text" id="user-job" value="${info.job_name}" name="job" placeholder="职位 / Job">
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-weixin" class="am-u-sm-3 am-form-label">微信号</label>
            <div class="am-u-sm-9">
              <input type="text" id="user-weixin" value="${info.weixin}" name="weixin" placeholder="微信号">
            </div>
          </div>
          
          
         <%--  <div class="am-form-group">
            <label for="user-jobYear" class="am-u-sm-3 am-form-label">入职时间 / JobYear</label>
            <div class="am-u-sm-9">
              <input type="number" id="user-jobYear" name="jobYear" value="${info.job_year }" placeholder="入职时间 / JobYear">
            </div>
          </div> --%>
          
            <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">核心能力</label>
            <div class="am-u-sm-9">
              	<select id="user-core" name="core">
              		<c:forEach var="obj" items="${coreCapacity}">
              		<option value="${obj.id}">${obj.value}</option>
              		</c:forEach>
              	</select>
            </div>
          </div>
          
          
		 <div class="am-form-group">
            <label for="user-study" class="am-u-sm-3 am-form-label">是否跨界学习</label>
            <div class="am-u-sm-9">
              	<select id="user-study" name="study">
              		<option value="1">是</option>
              		<option value="0"  selected="selected">否</option>
              	</select>
            </div>
          </div>
          
          
		 <div class="am-form-group">
            <label for="user-payType" class="am-u-sm-3 am-form-label">付费方</label>
            <div class="am-u-sm-9">
              	<select id="user-payType" name="payType">
              		<option value="1">企业</option>
              		<option value="0"  selected="selected">个人</option>
              	</select>
            </div>
          </div>
          
          
		 <div class="am-form-group">
            <label for="user-oldStu" class="am-u-sm-3 am-form-label">是否有老学员推荐</label>
            <div class="am-u-sm-9">
              	<select id="user-oldStu" name="oldStu">
              		<option value="1" >是</option>
              		<option value="0" selected="selected">否</option>
              	</select>
            </div>
          </div>
          
          
          
		 <div class="am-form-group">
            <label for="user-gongzhong" class="am-u-sm-3 am-form-label">是否运营了公众号</label>
            <div class="am-u-sm-9">
              	<select id="user-gongzhong" name="gongzhong">
              		<option value="1" >是</option>
              		<option value="0" selected="selected">否</option>
              	</select>
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-gongzhong" class="am-u-sm-3 am-form-label">个人信息</label>
            <div class="am-u-sm-9">
              	<textarea name="content" id="content">${info.content}</textarea>
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
var gender = '${info.gender}';
var core = '${info.core_capacity_id}';
$(function(){
	$("#saveBtn").bind("click", function(){  
		saveMember();
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
	if(gender != '' && core != ''){
		$("#user-gender").val(gender);
		$("#user-core").val(core);
		$("#user-payType").val('${info.pay_type}');
		$("#user-study").val('${info.study}');
		$("#user-gongzhong").val('${info.gongzhong}');
		$("#user-oldStu").val('${info.old_stu}');
	}
	$("#img-live-preview-id").attr("src","${member.avatar}");
});
function saveMember(){
	var id = '${member.id}';
	var mobile = $("#user-mobile").val();
	if(mobile == '' && id == ''){
		showInfo("请输入手机号!",function(){},3000);
		return;
	}
	var partten = /^1[3,5,7,8]\d{9}$/;
	if(!partten.test(mobile) && id == ''){
		showInfo("请输入正确手机号!",function(){},3000);
		return;
	} 
	var password = $("#user-password").val();
	if(password == '' && $("#memberId").val() == ''){
		showInfo("请输入密码!",function(){},3000);
		return;
	}
	var nickName = $("#user-nickName").val();
	if(nickName == ''){
		showInfo("请输入昵称!",function(){},3000);
		return;
	}
	var birthday = $("#user-birthday").val();
	if(birthday != ''){
		var temp = birthday.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
		var temp1 = birthday.match(/^((?:19|20)\d\d)-([1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
		var temp2 = birthday.match(/^((?:19|20)\d\d)-([1-9]|1[012])-([1-9]|[12][0-9]|3[01])$/);
		var temp3 = birthday.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-([1-9]|[12][0-9]|3[01])$/);
		
		if(temp || temp1 || temp2 || temp3) { 
			//..
		}else{
			showInfo("请输入正确的日期!",function(){},3000);
			return;
		}
	}
	
	
	/* var jobYear = $("#user-jobYear").val();
	if(jobYear == ''){
		showInfo("请输入入职时间!",function(){},3000);
		return;
	}
	if(jobYear <= 1900){
		showInfo("请输入正确的入职时间!",function(){},3000);
		return;
	}
	 */
	$("#saveBtn").attr("disabled","disabled");
	$('#myform').ajaxSubmit({
		type:'post',
		url:"/admin/member/save",
	    success:function(data){
	        if(data.status == 200){
	           	window.location.href = "/admin/member";
	        }else{
	        	$("#saveBtn").removeAttr('disabled');
	            showInfo(data.msg,function(){},3000);
	        }
	    }
	});
	
}
</script>
</html>