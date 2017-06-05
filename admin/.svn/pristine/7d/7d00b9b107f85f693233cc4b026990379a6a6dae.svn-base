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
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">用户信息</strong> / <small>Edit user</small></div>
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
            <img src="${member.avatar}" width="200" height="200">
            </div>
          </div>
        	
          <div class="am-form-group">
            <label class="am-u-sm-3 am-form-label">手机号 / mobile</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	${member.mobile}
            </div>
          </div>

          <div class="am-form-group">
            <label for="user-nickName" class="am-u-sm-3 am-form-label">昵称 / NickName</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	${member.nick_name}
            </div>
          </div>

		 <div class="am-form-group">
            <label for="user-gender" class="am-u-sm-3 am-form-label">性别 / Gender</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	${info.gender}
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="user-birthday" class="am-u-sm-3 am-form-label">生日 / Birthday</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	${info.birthday}
            </div>
          </div>
		
		 <div class="am-form-group">
            <label for="user-company" class="am-u-sm-3 am-form-label">公司 / Company</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	${info.company_name}
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="user-job" class="am-u-sm-3 am-form-label">职位 / Job</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	${info.job_name}
            </div>
          </div>
          
          	<div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">线上课程</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	<a href="/admin/member/onlineList?memberId=${member.id}">${onlineCount}</a>
            </div>
          </div>
          
          <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">线下课程</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
            	<a href="/admin/member/offlineList?memberId=${member.id}">${offlineCount}</a>
            </div>
          </div>
          
          	<div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">认可TA</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	<a href="/admin/member/favour?memberId=${member.id}&type=1">${toCount}</a>
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">TA认可</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	<a href="/admin/member/favour?memberId=${member.id}&type=0">${toCount}</a>
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
            <div class="am-u-sm-9" style="margin-top: 10px">
              	${core_capacity}
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">微信号</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	${info.weixin}
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">付费方</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	<c:if test="${info.pay_type == 1}">
              		企业
              	</c:if>
              	<c:if test="${info.pay_type == 0}">
              		个人
              	</c:if>
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">是否跨界学习</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	<c:if test="${info.study == 1}">
              		是
              	</c:if>
              	<c:if test="${info.study == 0}">
              		否
              	</c:if>
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">是否有老学员推荐</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	<c:if test="${info.old_stu == 1}">
              		是
              	</c:if>
              	<c:if test="${info.old_stu == 0}">
              		否
              	</c:if>
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">是否运营公众号</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	<c:if test="${info.gongzhong == 1}">
              		是
              	</c:if>
              	<c:if test="${info.gongzhong == 0}">
              		否
              	</c:if>
            </div>
          </div>
          
          
           <div class="am-form-group">
            <label for="user-core" class="am-u-sm-3 am-form-label">个人简介</label>
            <div class="am-u-sm-9" style="margin-top: 10px">
              	${info.content}
            </div>
          </div>
          
          <div class="am-form-group">
            <div class="am-u-sm-9 am-u-sm-push-3">
              <a class="am-btn am-btn-primary" id="saveBtn" onclick="edit(${member.id});">编辑信息</a>
              <a class="am-btn am-btn-primary" id="cancel" onclick="history.go(-1)">返回</a>
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
function edit(id){
	window.location.href="/admin/member/edit?id="+id;
}
</script>
</html>