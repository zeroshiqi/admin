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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<link rel="stylesheet" href="/admin/premage/premage.min.css">
<script src="/admin/premage/premage.min.js"></script>
<link href="/admin/ue/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="/admin/ue/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>

	<header>
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
    	编辑课程
		 <a class="am-btn am-btn-primary" id="saveBtn2">保存修改</a>
    </div>

    <hr/>

    <div class="am-g">
      <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
      </div>
		
      <div class="am-u-sm-12 maxWidth">
        <form class="am-form am-form-horizontal" id="myform" method="post" >
        
		<div class="am-form-group">
			<label class="am-u-sm-3 am-form-label">头图：</label>
			<div class="am-u-sm-9">
			 	<input type="file" name="imglive" id="imglive" accept="image/png, image/jpeg, image/jpg" />
			 	<input type="hidden" name="courseId" id="courseId" value="${course.id}">
			 	<input type="hidden" name="type" value="0">
			</div>
		</div>
        	
        	<div class="am-form-group">
            <label class="am-u-sm-3 am-form-label">二维码：</label>
            <div class="am-u-sm-9">
             	<input type="file" name="qrcode" id="qrcode" accept="image/png, image/jpeg, image/jpg" />
             	<c:choose>
             		<c:when test="${empty  course.qrcode}">(未上传)</c:when>
             		<c:otherwise>(已上传)</c:otherwise>
             	</c:choose>
             		
            </div>
          </div>
        
          <div class="am-form-group">
            <label for="course-name" class="am-u-sm-3 am-form-label">课程名称：</label>
            <div class="am-u-sm-9">
              <input type="text" name="courseName" id="course-name" value="${course.course_name}" placeholder="课程名称"  >
            </div>
          </div>
          
           <div class="am-form-group">
            <label for="course-newtype" class="am-u-sm-3 am-form-label">类别：</label>
            <div class="am-u-sm-9">
              	<select id="course-newtype" name="newtype">
              		<option value="2" selected="selected">直播课程</option>
              		<option value="1">线上课程</option>
              		<option value="0">线下课程</option>
              	</select>
            </div>
          </div>
          <div class="am-form-group">
	            <label for="course-teacher" class="am-u-sm-3 am-form-label">系列课程包：</label>
	            <div class="am-u-sm-9">
	            
		                <input style="width: 50%" type="text" id="course-type-name" readonly="readonly" name="typeName" value="${offline.catalog_name}">
						<input type="hidden" id="course-hidden-type" name="typeId" readonly="readonly" value="${offline.catalog_id}">
						<input type="text" name="typeDemo" id="typeDemo" placeholder="输入名称搜索课程包" style="float: left;width: 315px;"/>
						<select id="typeList" style="float: left;width: 315px;height: 43px;"></select>
						<div style="float: right;">
							<a class="am-btn am-btn-primary" id="addType" >选为关联课程包</a>&nbsp;<a class="am-btn am-btn-primary" id="clearType">清除</a>
						</div>
		          </div>
		          <label for="course-newtype" class="am-u-sm-3 am-form-label">好多课会员期限：</label>
		            <div class="am-u-sm-9">
		              	<input type="text" name="expiryDate" id="expiry-date" value="${offline.expiry_date}" placeholder="会员期限" >
		            </div>
          </div>
          <div class="am-form-group">
            <label for="course-beginTime" class="am-u-sm-3 am-form-label">开课时间：</label>
            <div class="am-u-sm-9">
             
                 <input type="text" name="beginTime" id="course-beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 09:30:00',dateFmt:'yyyy-MM-dd HH:mm'})" value="<fmt:formatDate value="${course.begin_time}" pattern="yyyy-MM-dd HH:mm" />" placeholder="例 : 2015-07-30 00:00">
<%--               <input type="text" name="beginTime" id="course-beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 23:59:00',dateFmt:'yyyy-MM-dd HH:mm'})" value="${course.begin_time}"/> --%>
            </div>
          </div>
          <div class="am-form-group">
            <label for="course-beginTime" class="am-u-sm-3 am-form-label">课程结束时间：</label>
            <div class="am-u-sm-9">
             
              <input type="text" name="endTime" id="course-endTime" onFocus="WdatePicker({startDate:'%y-%M-%d 17:00:00',dateFmt:'yyyy-MM-dd HH:mm'})" value="<fmt:formatDate value="${course.end_time}" pattern="yyyy-MM-dd HH:mm" />" placeholder="例 : 2015-07-30 00:00">
            </div>
          </div>
			
		 <div>
		 	<div class="am-form-group">
	            <label for="offline-type" class="am-u-sm-3 am-form-label">课程类型：</label>
	            <div class="am-u-sm-9">
	              	<select id="offline-type" name="offlineType">
	              		<c:forEach var="obj" items="${offlineType}">
	              		<option value="${obj.id}">${obj.value}</option>
	              		</c:forEach>
	              	</select>
	            </div>
	        </div>

	         <div class="am-form-group">
	            <label for="course-price" class="am-u-sm-3 am-form-label">价格：</label>
	            <div class="am-u-sm-9">
	              <input type="text" id="course-price" name="price" value="${offline.price}" placeholder="价格">
	            </div>
	         </div>
	         
	          <div class="am-form-group">
	            <label for="course-time" class="am-u-sm-3 am-form-label">课程时间：</label>
	            <div class="am-u-sm-9">
	              <input type="text" id="course-time" name="courseTime" value="${offline.course_time}" placeholder="课程时间">

	            </div>
	          </div>
	          
	          <div class="am-form-group">
	            <label for="job-tags" id="teacherJobId" class="am-u-sm-3 am-form-label">底部标签(使用 - 分割)：</label>
	            <div class="am-u-sm-9">
	              <input type="text" id="job-tags" name="tag" placeholder="例:插坐学院-技能培训" value="${offline.tag}">
	            </div>
	          </div>
	          
	          <div class="am-form-group">
		            <label for="offline-city" class="am-u-sm-3 am-form-label">城市：</label>
		            <div class="am-u-sm-9">
		              	<select id="offline-city" name="city">
		              		<c:forEach var="obj" items="${cityList}">
		              		<option value="${obj.id}">${obj.city_name}</option>
		              		</c:forEach>
		              	</select>
		            </div>
		       </div>
<!-- 		         <div class="am-form-group"> -->
<!-- 		            <label for="offline-city" class="am-u-sm-3 am-form-label">地址</label> -->
<!-- 		            <div class="am-u-sm-9"> -->
<!-- 		              	<select id="offline-city" name="city"> -->
<%-- 		              		<c:forEach var="obj" items="${cityList}"> --%>
<%-- 		              		<option value="${obj.id}">${obj.city_name}</option> --%>
<%-- 		              		</c:forEach> --%>
<!-- 		              	</select> -->
<!-- 		            </div> -->
<!-- 		       </div> -->
		       <div class="am-form-group">
	            <label for="course-teacher" class="am-u-sm-3 am-form-label">讲师：</label>
	            <div class="am-u-sm-9">
	              <input type="text" id="course-teacher" name="course-teacher" readonly="readonly" value="${teacherNames}">
	              <input type="hidden" id="course-hidden-teacher" name="teacher" value="${offline.teacher_id}" readonly="readonly">
	              <br>
	              <input type="text" id="teacher" value="" style="float: left;width: 200px">
	              <select id="teachers" style="float: left;width: 315px">
	              </select>
	              <br>
	              <div style="float: none;">
	              <a class="am-btn am-btn-primary" id="addTeacher" >添加</a>&nbsp;<a class="am-btn am-btn-primary" id="clear">清除</a>
	              </div>
	            </div>
	          </div>
		        <div class="am-form-group">
		            <label for="course-studentNum" class="am-u-sm-3 am-form-label">讲师职位：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-teacherjob" value="${offline.teacher_job}" name="teacherjob" placeholder="讲师职位">
		            </div>
		        </div>
		        <div class="am-form-group">
<%-- 		        	<c:if test="${offline.newtype==0 }"> --%>
		            	<label for="q-message" class="am-u-sm-3 am-form-label">报名短信：</label>
<%-- 		            </c:if> --%>
<%-- 		            <c:if test="${offline.newtype!=1 }"> --%>
<!-- 		            	<label for="q-message" class="am-u-sm-3 am-form-label">报名短信：</label> -->
<%-- 		            </c:if> --%>
		            <div class="am-u-sm-9">
		<%--               <input type="text" id="q-question" name="question" placeholder="问题" value="${record.title}"> --%>
		              <textarea rows="6" cols="" id="q-message" name="message" placeholder="报名短信" value="">${offline.message}</textarea>
		              点击按钮添加关键字：
		              	<a class="am-btn am-btn-primary" id="msgQianMing" >短信签名</a>
		              	<a class="am-btn am-btn-primary" id="msgName" >学员姓名</a>
		            	<a class="am-btn am-btn-primary" id="msgCourseName" >课程名称</a>
		            	<a class="am-btn am-btn-primary" id="msgTime" >开课时间</a>
		            	<a class="am-btn am-btn-primary" id="msgMobile" >手机号</a>
		            </div>
		          </div>
		        <div class="am-form-group">
		            <label for="course-studentNum" class="am-u-sm-3 am-form-label">招生总数：</label>
		            <div class="am-u-sm-9">
		              <input type="number" id="course-studentNum" value="${offline.student_num}" name="studenNumber" placeholder="招生总数">
		            </div>
		        </div>
		        <div class="am-form-group">
		            <label for="course-stream" class="am-u-sm-3 am-form-label">直播流：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-stream" value="${offline.stream}" name="stream">
		            </div>
		        </div>
		        <div class="am-form-group">
		            <label for="offline-city" class="am-u-sm-3 am-form-label">直播房间号：</label>
		            <div class="am-u-sm-9">
		              	 <input type="text" id="live-room-no" value="${offline.live_room_no}" name="liveRoomNo">
		            </div>
		       </div>
		        <div class="am-form-group">
		            <label for="offline-city" class="am-u-sm-3 am-form-label">直播账号ID：</label>
		            <div class="am-u-sm-9">
		              	 <input type="text" id="uid" value="${offline.uid}" name="uid">
		            </div>
		       </div>
		         <div class="am-form-group">
		            <label for="course-discount" class="am-u-sm-3 am-form-label">折扣：(不打折填100,如果打88折请填88)</label>
		            <div class="am-u-sm-9">
		              <input type="number" id="course-discount" value="${offline.discount}" name="discount" placeholder="100">
		            </div>
		        </div>
		        
		         <div class="am-form-group">
		            <label for="course-star" class="am-u-sm-3 am-form-label">评分：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-star" name="star" value="${offline.star}" placeholder="评分">
		            </div>
		        </div>
		        <div class="am-form-group">
		            <label for="course-studentNum" class="am-u-sm-3 am-form-label">举办站点：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-station" value="${offline.station}" name="station" placeholder="例：上海站">
		            </div>
		        </div>
		        <div class="am-form-group">
		            <label for="course-address" class="am-u-sm-3 am-form-label">地址：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-address" value="${offline.address}" onchange="searchByStationName()" name="address" placeholder="地址">
		            </div>
		        </div>
		        
		        <div class="am-form-group">
		            <label for="course-address" class="am-u-sm-3 am-form-label">线上直播地址：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-show-address" value="${offline.show_address}" name="showAddress" placeholder="线上直播地址">
		            </div>
		        </div>
		        
		        <div class="am-form-group">
		            <label for="course-lon" class="am-u-sm-3 am-form-label">经度：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-lon" value="${offline.lon}" name="lon" placeholder="经度">
		            </div>
		        </div>
		        
		        <div class="am-form-group">
		            <label for="course-lat" class="am-u-sm-3 am-form-label">纬度：</label>
		            <div class="am-u-sm-9">
		              <input type="text" id="course-lat" name="lat" value="${offline.lat}" placeholder="地址">
		            </div>
		        </div>
		        
		        <div class="am-form-group">
		        	<label for="course-lat" class="am-u-sm-3 am-form-label"> </label>
		        	<div class="am-u-sm-9">
		            <div id="container" style="width: 515px;height: 515px">
					</div>
					</div>
		        </div>
		        
		        <div class="am-form-group">
		            <label for="editor" class="am-u-sm-3 am-form-label">课程介绍：</label>
		            <div class="am-u-sm-9">
		            <script id="editor" type="text/plain" style="width:515px;height:300px;">${content}</script>
		             <input type="hidden" name="courseContent" id="courseContent" />
		            </div>
		        </div>
		        <div class="am-form-group">
		            <label for="article-tag" class="am-u-sm-3 am-form-label">课程分享语：</label>
		            <div class="am-u-sm-9">
		            	<textarea name="synopsis" >${course.synopsis}</textarea>
		            </div>
		       </div> 
		       <div class="am-form-group">
		            <label for="article-tag" class="am-u-sm-3 am-form-label">直播公告：</label>
		            <div class="am-u-sm-9">
		            	<textarea name="liveNotice">${offline.live_notice}</textarea>
		            </div>
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
var ue = UE.getEditor('editor');
var ue1 = UE.getEditor('editor1');
var ue2 = UE.getEditor('editor2');
var ue3 = UE.getEditor('editor3');
var ue4 = UE.getEditor('editor4');
function insertText(obj,str) {
    if (document.selection) {
        var sel = document.selection.createRange();
        sel.text = str;
    } else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
        var startPos = obj.selectionStart,
            endPos = obj.selectionEnd,
            cursorPos = startPos,
            tmpStr = obj.value;
        obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
        cursorPos += str.length;
        obj.selectionStart = obj.selectionEnd = cursorPos;
    } else {
        obj.value += str;
    }
}
function save(){
	$("#courseContent").val($("#content").val());
	var courseName = $("#course-name").val();
	if(courseName == ''){
		showInfo("请输入课程名称!",function(){},3000);
		return;
	}
	var type = $("#course-type option:selected").val();
	if(type == '' || type == -1){
		showInfo("请输选择课程类型!",function(){},3000);
		return;
	}
	var beginTime = $("#course-beginTime").val();
	if(beginTime == ''){
		showInfo("请输入课程开始时间!",function(){},3000);
		return;
	}
	var endTime = $("#course-endTime").val();
	if(endTime == ''){
		showInfo("请输入课程结束时间!",function(){},3000);
		return;
	}
	var courseType = $('#course-hidden-type').val();
	if(courseType=="0"){
		showInfo("请选择关联的系列课!",function(){},3000);
		return;
	}
// 	if(!beginTime.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/) && !beginTime.match(/^((?:19|20)\d\d)-([1-9]|1[012])-([1-9]|[12][0-9]|3[01])$/)) { 
// 		showInfo("请输入正确的课程开始时间!",function(){},3000);
// 		return;
// 	}
	//course-discount
	var discount = $("#course-discount").val();
	if(discount == ''){
		showInfo("请填写折扣!",function(){},3000);
		return;
	}
		var offlineType = $("#offline-type option:selected").val();
		if(offlineType == ''){
			showInfo("请选择课程类别!",function(){},3000);
			return;
		}
		var tag = $("#job-tags").val();
		if(tag == ''){
			showInfo("请输入标签!",function(){},3000);
			return;
		}
		var price = $("#course-price").val();
		if(price == ''){
			showInfo("请输入价格!",function(){},3000);
			return;
		}
		if(!price.match(/^[0-9]+.?[0-9]*$/)){
			showInfo("请输入正确的价格!",function(){},3000);
			return;
		}
		var time = $("#course-time").val();
		if(time == ''){
			showInfo("请输入课程时间!",function(){},3000);
			return;
		}
		var city = $("#offline-city option:selected").val();
		if(city == '' || city == 0){
			showInfo("请选择城市!",function(){},3000);
			return;
		}
		var teacher = $("#course-hidden-teacher").val();
		if(teacher == ''){
			showInfo("请选择讲师!",function(){},3000);
			return;
		}
		var studentNum = $("#course-studentNum").val();
		if(studentNum == '' || studentNum < 1){
			showInfo("请输入招生数量!",function(){},3000);
			return;
		}
		var stream = $("#course-stream").val();
		if(stream == ''){
			showInfo("请填写直播流!",function(){},3000);
			return;
		}
		var star = $("#course-star").val();
		if(star == ''){
			showInfo("请输入评分!",function(){},3000);
			return;
		}
		if(!star.match(/^[0-9]+.?[0-9]*$/)){
			showInfo("请输入正确的评分!",function(){},3000);
			return;
		}
		var address = $("#course-address").val();
// 		if(address == ''){
// 			showInfo("请输入地址!",function(){},3000);
// 			return;
// 		}
		var lon = $("#course-lon").val();
		if(lon == ''){
			showInfo("请输入经度!",function(){},3000);
			return;
		}
		var lat = $("#course-lat").val();
		if(lat == ''){
			showInfo("请输入纬度!",function(){},3000);
			return;
		}
		if(!ue.hasContents()){
			showInfo("请输入课程简介",function(){},3000);
			return;
		}
		$("#courseContent").val(ue.getContent()); 
		var types = $("#course-newtype").val();
		var options ={
			url:"/admin/course/save",
			success : function(data) {
				if(data.status == 200){
						window.location.href = "/admin/course/living";
	            }else{
	            	showInfo(data.msg,function(){},3000);
	            }
			}
		};
		$("#myform").ajaxSubmit(options);		
}
$(function(){
	var id = $("#courseId").val();
	if(id != '' && id > 0){
		$("#course-type").val('${course.type}');
		changeType();
		var type = $("#course-type option:selected").val();
		
		ue.addListener("ready", function () {
		$("#offline-city").val('${offline.city_id}');
		searchByStationName();
		$("#offline-type").val("${offline.offline_type_id}");
		});
	}
	$("#course-newtype").val('${newtype}');
	$("#saveBtn").bind("click", function(){  
		save();
	});
	$("#msgQianMing").bind("click", function(){  
		insertText(document.getElementById('q-message'),'【插坐学院】');
	});
	$("#msgName").bind("click", function(){
		insertText(document.getElementById('q-message'),'#name#');
	});
	$("#msgCourseName").bind("click", function(){  
		insertText(document.getElementById('q-message'),'#courseName#');
	});
	$("#msgTime").bind("click", function(){  
		insertText(document.getElementById('q-message'),'#time#')
	});
	$("#msgMobile").bind("click", function(){  
		insertText(document.getElementById('q-message'),'#mobile#')
	});
	
	$("#msgQianMing1").bind("click", function(){  
		insertText(document.getElementById('q-message1'),'【插坐学院】');
	});
	$("#msgName1").bind("click", function(){
		insertText(document.getElementById('q-message1'),'#name#');
	});
	$("#msgCourseName1").bind("click", function(){  
		insertText(document.getElementById('q-message1'),'#courseName#');
	});
	$("#msgTime1").bind("click", function(){  
		insertText(document.getElementById('q-message1'),'#time#')
	});
	$("#msgMobile1").bind("click", function(){  
		insertText(document.getElementById('q-message1'),'#mobile#')
	});
	
	$("#msgQianMing2").bind("click", function(){  
		insertText(document.getElementById('q-message2'),'【插坐学院】');
	});
	$("#msgName2").bind("click", function(){
		insertText(document.getElementById('q-message2'),'#name#');
	});
	$("#msgCourseName2").bind("click", function(){  
		insertText(document.getElementById('q-message2'),'#courseName#');
	});
	$("#msgTime2").bind("click", function(){  
		insertText(document.getElementById('q-message2'),'#time#')
	});
	$("#msgMobile2").bind("click", function(){  
		insertText(document.getElementById('q-message2'),'#mobile#')
	});
	$("#saveBtn2").bind("click", function(){  
		save();
		
	});
	$("#teacher").bind("change",function(){
		$("#teachers").empty();
		add($("#teacher").val());
	});
	if('${offline.expiry_date}'=="" || '${offline.expiry_date}'=="0"){
		$("#expiry_date").val("365");
	}else{
		$("#expiry_date").val('${offline.expiry_date}');
	}
	addType('');
	$("#typeDemo").bind("change",function(){
		$("#typeDemo").empty();
		addType($("#typeDemo").val());
	});
	 //清除课程分类
    $("#clearType").bind("click",function(){
		$("#course-hidden-type").val('');
		$("#course-type-name").val('');
	});
  //选择分类课程
	$("#addType").bind("click",function(){
		var text = $("#course-type").val();
		var ids = $("#id").val();
		if($("#typeList option:selected").val() == ''){
			return;
		}
		$("#course-hidden-type").val($("#typeList option:selected").val());
		$("#course-type-name").val($("#typeList option:selected").text());
	});
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
	add("");
	$("#addTeacher").bind("click",function(){
		var text = $("#course-teacher").val();
		var ids = $("#course-hidden-teacher").val();
		if($("#teachers option:selected").val() == ''|| $("#teachers").val()==null || $("#teachers option:selected").val() == 'undefined'){
			alert("选择的讲师无效！");
			return;
		}
		if(text == ''){
			$("#course-hidden-teacher").val($("#teachers option:selected").val());
			$("#course-teacher").val($("#teachers option:selected").text());
		}else{
			$("#course-hidden-teacher").val(ids + "," +$("#teachers option:selected").val());
			$("#course-teacher").val(text + "," + $("#teachers option:selected").text());
		}
	});
	$("#clear").bind("click",function(){
		$("#course-hidden-teacher").val('');
		$("#course-teacher").val('');
	});
	
	$("#img-live-preview-id").attr("src","${course.avatar}");
});

function add(str){
	$.getJSON("/admin/member/findAllMember?name="+str,function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#teachers").html(html);
	});
	
}

function changeType(){
	var value = $("#course-type").val();
	if(value == '-1'){
		$("#offlineDiv").hide();
		$("#onlineDiv").hide();
	}else if(value == '0'){
		$("#offlineDiv").show();
		$("#onlineDiv").hide();
	}else{
		$("#offlineDiv").hide();
		$("#onlineDiv").show();
	}
}
function addType(str){
	$.getJSON("/admin/banner/query?name="+str,function(result){
		var html = "";
		for(var i=0;i<result.length;i++){
			html += "<option value='"+result[i].id+"'>";
			html += result[i].value;
			html += "</option>";
		}
		$("#typeList").html(html);
	});
}
//百度地图
var map = new BMap.Map("container");
map.centerAndZoom("北京", 14);
map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
var localSearch = new BMap.LocalSearch(map);
localSearch.enableAutoViewport(); //允许自动调节窗体大小

//查询坐标 
function searchByStationName(){
	map.clearOverlays();//清空原来的标注
	var keyword =$("#course-address").val();
	localSearch.setSearchCompleteCallback(function (searchResult) {
    var poi = searchResult.getPoi(0);
    $("#course-lon").val(poi.point.lng);
    $("#course-lat").val(poi.point.lat);
    map.centerAndZoom(poi.point, 15);
    var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地址对应的经纬度
    map.addOverlay(marker);
});
localSearch.search(keyword);
}

</script>
</html>