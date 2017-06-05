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
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<%@include file="/base/link.jsp"%>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/ZeroClipboard.min.js"></script>
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
				<li><a href="/admin/order">Web线下课程订单</a></li>
				<li><a href="/admin/order/onlineNew">Web线上课程订单</a></li>
				<li><a href="/admin/crowdfunding/crowd">众筹列表</a></li>
				<li class="bgColor"><a href="/admin/ordercount">订单统计</a></li>
				<li><a href="/admin/complex">复购率报表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">课程订单信息</strong>--%>
				<%--</div>--%>
			<%--</div>--%>
			<form method="post" id="xlsForm">
				<div class="am-u-md-3 am-cf">
					<div class="search">
						<div class="Go">
							<input type="text" placeholder="开始时间" class="text" id="startTime" name="startTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm'})">
							<input type="text" placeholder="结束时间" class="text" id="endTime" name="endTime" onFocus="WdatePicker({startDate:'%y-%M-%d 23:59:00',dateFmt:'yyyy-MM-dd HH:mm'})">
							<input type="text" placeholder="城市" class="text" id="city" name="city">
							<button class="icon-search"  onclick="submitForm()" type="button"></button>
						</div>
					</div>
				</div>
			</form>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th style="width: 5%;">ID</th>
									<th style="width: 35%;"><center>课程名称</center></th>
									<th style="width: 10%;"><center>价格</center></th>
									<th style="width: 5%;"><center>姓名</center></th>
									<th style="width: 10%;"><center>手机</center></th>
									<th style="width: 8%;"><center>省份</center></th>
									<th style="width: 10%;"><center>城市</center></th>
									<th style="width: 10%;"><center>购买时间</center></th>
<!-- 									<th style="width: 7%;"><center>订单号</center></th> -->
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.id}</td>
									<td><center>${obj.course_name}</center></td>
<%-- 									<td><center><a id="abc" title="${obj.CODE}" onclick="tanchu('${obj.CODE}')">${fn:substring(obj.CODE,0,10)}...</a></center></td> --%>
									<td><center>${obj.price}</center></td>
									<td><center>${obj.name}</center></td>
									<td><center>${obj.mobile}</center></td>
									<td><center>${obj.province}</center></td>
									<td><center>${obj.city}</center></td>
									<td><center>${fn:substring(obj.create_at,0,19)}</center></td>
<%-- 									<td><input readonly="true" style="width:100%;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;" title="${obj.CODE}" value="${obj.CODE}"/></td> --%>
								</tr>
							</tbody>
							</c:forEach>
						</table>
						<table class="am-table am-table-striped am-table-hover table-main">
							<tfoot>
									<tr>
										<th style="width: 5%;"><strong>合计：</strong></th>
										<th style="width: 35%;"><center><strong style="color: blue">${officeCount}</strong>个课程</center></th>
										<th style="width: 10%;"><center><strong style="color: blue">${moneyCount}</strong>总金额</center></th>
										<th style="width: 5%;"></th>
										<th style="width: 10%;"><center><strong style="color: blue">${page.count}</strong>个订单</center></th>
										<th style="width: 8%;"></th>
										<th style="width: 10%;"></th>
										<th style="width: 10%;"></th>
<%-- 										<th style="width: 7%;"><center><strong style="color: blue">${page.count}</strong>个订单</center></th> --%>
									</tr>
								</tfoot>
<!-- 							<tbody> -->
<!-- 								<tr> -->
<!-- 									<td style="width: 10%"><center><strong>合计：</strong></center></td> -->
<%-- 									<td style="width: 40%"><center><strong style="color: blue">${officeCount}</strong>个课程</center></td> --%>
<%-- 									<td style="width: 20%"><center><strong style="color: blue">${page.count}</strong>个订单</center></td> --%>
<%-- 									<td style="width: 30%"><center><strong style="color: blue">${moneyCount}</strong>总金额</center></td> --%>
<!-- 								</tr> -->
<!-- 							</tbody> -->
						</table>
						<div class="tfoot">
							共 ${page.count}个记录
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
		<input type="hidden" value="0" id="total">
		<!-- content end -->
	</div>
</body>
<script type="text/javascript">
$(function(){
// 	clip = new ZeroClipboard.Client(); //初始化对象  
//     ZeroClipboard.setMoviePath("js/ZeroClipboard.swf");    
//     clip.setHandCursor( true ); //设置手型  
//     clip.setText(str); // 设置要复制的文本。
//     clip.addEventListener('mouseDown', function (client) {  //创建监听  
//           copyUrl(); //设置需要复制的代码  
//     });  
//     clip.glue('copycardid'); //将flash覆盖至指定ID的DOM上  
});
function copyUrl(){  
	clip.setText(window.location.href);  
}  
function submitForm() {
	window.location.href="/admin/ordercount?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&city="+$("#city").val();
}

function tanchu(str){
		alert(str);
		clip = new ZeroClipboard.Client(); // 新建一个对象
		ZeroClipboard.setMoviePath("js/ZeroClipboard.swf");  
	    clip.setHandCursor(true);
	    clip.setText(str); // 设置要复制的文本。
	    clip.addEventListener( "mouseDown", function(client) {
	        alert("复制卡号成功！");
	    });
	    // 注册一个 button，参数为 id。点击这个 button 就会复制。
	    //这个 button 不一定要求是一个 input 按钮，也可以是其他 DOM 元素。
	    clip.glue("abc");
}

</script>
</html>