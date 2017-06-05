<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
	.wrap {
   position: absolute;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   margin: auto;
   background-color: #fff;
   border-radius: 6px;
   padding: 10px;
   padding: 36px;
   padding-top: 60px;
   width: 447px;
   height: 325px;
   overflow: hidden;
   -webkit-box-shadow: 0px 0px 4px 0px rgba(0,0,0,0.3);
   -moz-box-shadow: 0px 0px 4px 0px rgba(0,0,0,0.3);
   box-shadow: 0px 0px 4px 0px rgba(0,0,0,0.3);
  }
  .wrap-head {
   text-align: right;
   position: absolute;
   top: 0;
   left: 0;
   height: 40px;
   width: 100%;
   background-color: #337ab7;
  }
  .submit {
   display: block;
      color: #fff;
      background-color: #0e90d2;
         padding: 6px 24px;
      border-radius: 15px;
      border: none;
      position: absolute;
      bottom: 30px;
      right: 30px;
      cursor: pointer;
  }
  .wrap-head .handle-close{
      position: absolute;
      color: #fff;
      top: 9px;
      right: 17px;
      cursor: pointer;
      font-size: 14PX;
  }
</style>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<%@include file="/base/link.jsp"%>
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
		<div class="nav-assist">
			<ul>
				<li><a href="/admin/course/bookSale">新书预售</a></li>
				<li class="bgColor"><a href="/admin/order/bookOrder">书籍订单</a></li>
			</ul>
		</div>
		 
		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
				<%--<c:if test="${newtype == 0}"><strong class="am-text-primary am-text-lg">线下课程订单信息</strong></c:if>--%>
				<%--<c:if test="${newtype == 1}"><strong class="am-text-primary am-text-lg">线上课程订单信息</strong></c:if>--%>
					<%----%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="am-u-md-3 am-cf" style="height: 90px;">
				<div class="Go">
					<input type="text" placeholder="价格" value="${price}" class="text" id="searchPrice" name="price">
					<input type="text" placeholder="书籍名称" value="${name}" class="text" id="search" name="search">
					<input type="text" placeholder="手机号" value="${mobile}" class="text" id="searchMobile" name="searchMobile">
					<input type="text" placeholder="订单号" value="${code}" class="text" id="searchCode" name="searchCode">
                 	<input type="text" placeholder="开始时间" name="beginTime"  class="text" value="${beginTime} " id="beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 00:00">
              		</br>
              		<input type="text" placeholder="结束时间" name="endTime" class="text" value="${endTime}" id="endTime" onFocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 00:00">
						<button class="icon-search" onclick="submitForm()" type="button"></button>
						<!-- 								<button class="am-btn am-btn-default" onclick="submitForm1()" -->
						<!-- 									type="button">同步</button> -->
						<button type="button" id="exportFile" onclick="exportFile()"
							class="add">
							<span class="icon-plus"></span> 导出
						</button>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th style="width: 5%">订单号</th>
									<th style="width: 10%"><center>姓名</center></th>
									<th style="width: 10%"><center>手机号</center></th>
									<th style="width: 5%">数量</th>
									<th style="width: 10%"><center>金额</center></th>
									<th style="width: 10%"><center>书籍</center></th>
									<th style="width: 10%"><center>购买时间</center></th>
									<th style="width: 15%"><center>公司</center></th>
									<th style="width: 15%"><center>快递单号</center></th>
									<th style="width: 5%"><center>状态</center></th>
									<th style="width: 5%"><center>操作</center></th>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
								<tbody>
									<tr>
										<td><input readonly="true" style="width:50px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;" title="${obj.code}" value="${obj.code}"/></td>
										<td><center>${obj.name}</center></td>
										<td><center>${obj.mobile}</center></td>
										<td><center>${obj.number}</center></td>
										<td><center>${obj.price}</center></td>
										<td><center>${obj.course_name}</center></td>
										<td><center>${fn:substring(obj.create_at,0,19)}</center></td>
										<td><center>${obj.work}</center></td>
										<td><center>${obj.courier_number}</center></td>
										<td><center>
											<c:if test="${obj.handle_status eq 0}">
												<font style="color: red;font-weight: bold;">未发</font>
											</c:if>
											<c:if test="${obj.handle_status eq 1}">
												已发
											</c:if>
										</center></td>
										<td><a onclick="handel('${obj.code}','${obj.id}')" href="#" style="font-size: 15px;color: blue;font-weight: bold;"><center>修改</center></a></td>
									</tr>
								</tbody>
							</c:forEach>
						</table>
						<div class="tfoot">
							共 ${page.count} 条记录
							<div class="am-fr am-fr-page">
								<jsp:include page="/base/page.jsp">
									<jsp:param value="${page}" name="page" />
								</jsp:include>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>
	<div id="handle" class="wrap" style="display:none">
		  <div class="wrap-head">
		   <span class="handle-close" onclick="hideHandel()">X</span> 
		  </div>
			  <div style="margin-bottom:20px;">
			   <label style="cursor:pointer">
			    快递单号：
			    <input type="text" style="width:278px;height: 32px;border: 1px solid #908e8e" id="fastMail">
			    <input type="hidden" id="code"/>
			   </label>
			  </div>
			  <div>
			   <label style="cursor:pointer">
			    备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：
	<!-- 		   	<textarea name="memo" id="memo" cols="32" rows="6" style="vertical-align:middle;"></textarea> --> 	
				<div style="width:277px;height:132px;display:inline-block;vertical-align:middle;overflow-y:auto;">
					<textarea name="memo" id="memo" style="width:100%;height:0%;display:none;" placeholder=""></textarea>
			   		<textarea name="memo2" id="memo2" style="width:100%;height:100%;vertical-align:middle;"></textarea>
				</div>		
				</label>
			  </div>
			  <label style="cursor:pointer;margin: 24px 74px;">
				 <input type="checkbox" id="handleStatus" name="handleStatus">
				 已发货
			</label>
			  <button class="submit" onclick="saveMemo()" id="submit">保存</button>
		  </div>
	
</body>
<script type="text/javascript">
function submitForm() {
	window.location.href = "/admin/order/bookOrder?name="
		+ $("#search").val()+"&price="+$("#searchPrice").val()+"&beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val()+"&mobile="+$('#searchMobile').val()+"&code="+$('#searchCode').val();
}
function exportFile() {
	window.location.href = "/admin/order/exportBookOrder?name="+ $("#search").val()+"&price="+$("#searchPrice").val()+"&beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val()+"&mobile="+$('#searchMobile').val()+"&code="+$('#searchCode').val();
}
function submitForm1() {
	$.getJSON("/admin/order/update", function(
			result) {
		if (result.status == 200) {
			location.reload();
		} else {
			showInfo("系统错误....", function() {
			}, 3000);
		}
	});
}
$(function(){
	$('#handle').hide();
})
function handel(code,id){
	$.getJSON("/admin/order/memoBookOrder?code="+code, function(result) {
		var order = result['order'];
		var order_code = order[0].code;
		var memo = order[0].memo;
		var courierNumber = order[0].courierNumber;
		var handleStatus = order[0].handleStatus;
 		$('#code').val(id);
 		if(memo!=null && memo!=""){
 			$('#memo').attr({
 				'disabled':true,
 				'placeholder':memo
 			}).css('height','50%').show();
 			
 			$('#memo2').css('height','50%');
 		}
 		if(handleStatus == 1){
 			$('#handleStatus').prop('checked',true).attr('disabled','true');
 		}else{
 			$('#handleStatus').prop('checked',false);
 		}
		$('#fastMail').val(courierNumber);
		$('#handle').show();
	});
// 	$('#handle').show();
}
function saveMemo(){
	var memo = $("#memo").attr('placeholder');
	var memo2 = $('#memo2').val();
	var code = $("#code").val();
	var handleStatus= $('#handleStatus').prop('checked');
	var fastMail = $('#fastMail').val();
	if(handleStatus==true){
		handleStatus = 1;
	}else{
		handleStatus = 0;
	}
	$.getJSON("/admin/order/saveMemo?code="+code+"&memo="+memo+"&memo2="+memo2+"&handleStatus="+handleStatus+"&fastMail="+fastMail, function(
			result) {
		if (result.status == 200) {
			showInfo("保存成功", function() {
				location.reload();
			}, 3000);
		} else {
			showInfo(result.msg, function() {
			}, 3000);
		}
	});
// 	$.ajax(function(){
// 		url:
// 	})
}
function hideHandel(){
	$('#handle').hide();
	$('#fastMail').val('');
	$('#memo').val('');
	location.reload();
}
</script>
</html>