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
				<li><a href="/admin/order/online">录音课程订单</a></li>
				<li <c:if test="${newtype == 0}">class="bgColor"</c:if>><a href="/admin/order">Web线下课程订单</a></li>
				<li <c:if test="${newtype == 1}">class="bgColor"</c:if>><a href="/admin/order/onlineNew">Web线上课程订单</a></li>
				<li><a href="/admin/crowdfunding/crowd">众筹列表</a></li>
				<li><a href="/admin/ordercount">订单统计</a></li>
				<li><a href="/admin/complex">复购率报表</a></li>
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
					<input type="text" placeholder="课程名称" value="${name}" class="text" id="search" name="search">
					<input type="text" placeholder="手机号" value="${mobile}" class="text" id="searchMobile" name="searchMobile">
					<input type="text" placeholder="订单号" value="${code}" class="text" id="searchCode" name="searchCode">
                 	<input type="text" placeholder="开始时间" name="beginTime"  class="text" value="${beginTime} " id="beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 00:00">
              		</br>
              		<input type="text" placeholder="结束时间" name="endTime" class="text" value="${endTime}" id="endTime" onFocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 00:00">
						<button class="icon-search" onclick="submitForm()" type="button"></button>
						<!-- 								<button class="am-btn am-btn-default" onclick="submitForm1()" -->
						<!-- 									type="button">同步</button> -->
						<c:if test="${newtype == 1}">
						<button type="button" id="exportFile" onclick="exportFile()"
							class="add">
							<span class="icon-plus"></span> 导出
						</button>
					</c:if>
				</div>
			</div>
			<c:if test="${newtype == 1}">
				<div class="am-g" >
					<div class="am-u-md-3 am-cf">
						<div class="Go">
<!-- 							<div class="am-input-group am-input-group-sm"> -->
							
							<input type="text" placeholder="直播房间号" class="text" id="jiniusearch" name="jiniusearch"> 
							<span>
								<button class="am-btn am-btn-default" style="padding:4px 12px;border-radius:6px;margin:6px;border:none;color:#fff;background-color:#337ab7;" onclick="searchJiniu()"type="button">查询极牛直播报名数量</button>
							</span> 
							好多课App注册用户数量：<font size="30px" color="blue">${employeeRegister}</font>
<%-- 								WEB订单数量：<font size="30px" color="blue">${employeeRegister}</font> --%>
<%-- 								WEB订单金额：<font size="30px" color="blue">${employeeRegister}</font> --%>
<%-- 								App订单数量：<font size="30px" color="blue">${employeeRegister}</font> --%>
<%-- 								App订单金额：<font size="30px" color="blue">${employeeRegister}</font> --%>
<!-- 							</div> -->
						</div>
					</div>
				</div>
<!-- 				<div class="am-g" > -->
<!-- 					<div class="am-u-md-3 am-cf"> -->
<!-- 						<div class="Go"> -->
<%--  								WEB订单数量：<font size="30px" color="blue" style="font-weight: bold;">${employeeRegister}</font>&nbsp;&nbsp;&nbsp; --%>
<%-- 								WEB订单金额：<font size="30px" color="blue" style="font-weight: bold;">${employeeRegister}</font>&nbsp;&nbsp;&nbsp; --%>
<%-- 								App订单数量：<font size="30px" color="blue" style="font-weight: bold;">${employeeRegister}</font>&nbsp;&nbsp;&nbsp; --%>
<%-- 								App订单金额：<font size="30px" color="blue" style="font-weight: bold;">${employeeRegister}</font>&nbsp;&nbsp;&nbsp; --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
			</c:if>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<c:if test="${newtype == 0}">
									<tr>
										<th style="width: 5%">订单号</th>
										<th style="width: 6%"><center>姓名</center></th>
										<th style="width: 10%"><center>手机号</center></th>
										<th style="width: 15%"><center>邮箱</center></th>
										<th style="width: 5%"><center>支付金额</center></th>
										<th style="width: 20%"><center>课程名称</center></th>
										<th style="width: 10%"><center>购买时间</center></th>
										<th style="width: 9%"><center>职位</center></th>
	<!-- 									<th style="width: 10%"><center>购买意向</center></th> -->
	<!-- 									<th style="width: 10%"><center>参课原因</center></th> -->
										<th style="width: 5%">退款状态</th>
										<th style="width: 10%"><center>审核</center></th>
									</tr>
								</c:if>
								<c:if test="${newtype == 1}">
									<tr>
										<th style="width: 5%">订单号</th>
										<th style="width: 10%"><center>姓名</center></th>
										<th style="width: 10%"><center>手机号</center></th>
										<th style="width: 5%">购买数量</th>
										<th style="width: 5%"><center>支付金额</center></th>
										<th style="width: 35%"><center>课程名称</center></th>
										<th style="width: 10%"><center>购买时间</center></th>
										<th style="width: 10%"><center>职位</center></th>
	<!-- 									<th style="width: 10%"><center>购买意向</center></th> -->
	<!-- 									<th style="width: 10%"><center>参课原因</center></th> -->
										<th style="width: 5%">支付类型</th>
									</tr>
								</c:if>
							</thead>
							<c:forEach var="obj" items="${list}">
								<c:if test="${newtype == 0}">
									<tbody>
										<tr>
											<td><input readonly="true" style="width:50px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;" title="${obj.code}" value="${obj.code}"/></td>
											<td><center><a href="/admin/order/users?courseId=${obj.id}">${obj.names}</a></center></td>
											<td><center>${obj.mobile}</center></td>
											<td><center>${obj.email}</center></td>
											<td><center>${obj.price}</center></td>
											<td><center>${obj.course_name}</center></td>
											<td><center>${fn:substring(obj.update_at,0,19)}</center></td>
											<td><center>${obj.job}</center></td>
											<%-- <td><center>${obj.buy_intentions}</center></td>
											<td><center>${obj.join_reason}</center></td> --%>
											<td>
<%-- 												<c:if test="${obj.type == 0}">微信支付</c:if> --%>
<%-- 												<c:if test="${obj.type == 1}">百度支付</c:if> --%>
<%-- 												<c:if test="${obj.type == 2}">支付宝</c:if> --%>
												<center>${obj.refund_result}</center>
											</td>
											<td>
												<c:if test="${obj.review_status == 0}">
													<a style="font-weight: bold;font-size: 15px;cursor:pointer;" onclick="shenhe('${obj.id }','1')" ><font style="color: green;font-size: 15px;"><center>通过</center></font></a>
													<a style="font-weight: bold;font-size: 15px;cursor:pointer;" onclick="shenhe('${obj.id }','2')" ><font style="color: red;font-size: 15px;"><center>未通过</center></font></a>
												</c:if>
												<c:if test="${obj.review_status != 0}">
													<c:if test="${obj.review_status == 1}">
														<center>通过</center>
													</c:if>
													<c:if test="${obj.review_status == 2}">
														<center>未通过</center>
													</c:if>
												</c:if>
											</td>
										</tr>
									</tbody>
								</c:if>
								<c:if test="${newtype == 1}">
									<tbody>
										<tr>
											<td><input readonly="true" style="width:50px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;" title="${obj.code}" value="${obj.code}"/></td>
											<td><center>${obj.names}</center></td>
											<td><center>${obj.mobile}</center></td>
											<td><center>${obj.number}</center></td>
											<td><center>${obj.price}</center></td>
											<td><center>${obj.course_name}</center></td>
											<td><center>${fn:substring(obj.update_at,0,19)}</center></td>
											<td><center>${obj.job}</center></td>
											<%-- <td><center>${obj.buy_intentions}</center></td>
											<td><center>${obj.join_reason}</center></td> --%>
											<td>
												<c:if test="${obj.type == 0}">微信支付</c:if>
												<c:if test="${obj.type == 1}">百度支付</c:if>
												<c:if test="${obj.type == 2}">支付宝</c:if>
											</td>
										</tr>
									</tbody>
								</c:if>
							</c:forEach>
							<input type="hidden" id="flag" name="flag" value="0"/>
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
</body>
<script type="text/javascript">
function submitForm() {
	<c:if test="${newtype == 0}">
	window.location.href = "/admin/order?name="+ $("#search").val()+"&price="+$("#searchPrice").val()+"&beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val()+"&mobile="+$('#searchMobile').val()+"&code="+$('#searchCode').val();;
	</c:if>
	<c:if test="${newtype == 1}">
	window.location.href = "/admin/order/onlineNew?name="
		+ $("#search").val()+"&price="+$("#searchPrice").val()+"&beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val()+"&mobile="+$('#searchMobile').val()+"&code="+$('#searchCode').val();
	</c:if>
}
function exportFile() {
	window.location.href = "/admin/order/export?name="+ $("#search").val()+"&price="+$("#searchPrice").val()+"&beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val()+"&mobile="+$('#searchMobile').val()+"&code="+$('#searchCode').val();
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
function searchJiniu(){
	$.getJSON("/admin/order/jiniu?no="+$('#jiniusearch').val(), function(result) {
        showInfo(result.msg,function(){},3000);
	});
}
function shenhe(code,status){
	var msg = "";
	if(status==1){
		msg="确认通过审核吗？";
	}else{
		msg="确认审核不通过吗？";
	}
	showConfirm(msg, function() {
		if($("#flag").val()!="0"){
			alert("正在处理请求，请勿重复提交！");
			return;
		}
		$("#flag").val("1");
		$.getJSON("/admin/order/shenhe?code=" + code+"&status="+status, function(
				result) {
			if (result.status == 200) {
				$("#flag").val("0");
				location.reload();
			} else {
				showInfo(result.msg, function() {
				}, 3000);
				$("#flag").val("0");
			}
		});
	});
}
</script>
</html>