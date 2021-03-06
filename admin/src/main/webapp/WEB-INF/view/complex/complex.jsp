<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<%@include file="/base/link.jsp"%>
<SCRIPT src="/admin/js/Highcharts-4.2.3/js/highcharts.js" type="text/javascript"></SCRIPT>
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
				<li><a href="/admin/ordercount">订单统计</a></li>
				<li class="bgColor"><a href="/admin/complex">复购率报表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<div class="am-g">
				<div class="am-u-sm-12">
					<div id="container" style="width: 100%; height: 500px"></div>
					<div style="width: 100%; height: 50px">
						<div style="width: 30%;float: left;">
							<center>
								<font style="font-size: 15px">复购人数：</font>
								<input id="cou" readonly="true" style="width:100px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;font-size: 20px;color: blue;font-weight:bold;"/>
							</center>
						</div>
						<div style="width: 30%;float:left;">
							<center>
								<font style="font-size: 15px">总人数：</font>
								<input id="zong" readonly="true" style="width:100px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;font-size: 20px;color: blue;font-weight:bold;"/>
							</center>
						</div>
						<div style="width: 30%;float: right;">
							<font style="font-size: 15px;">复购率：</font>
								<input id="complex" readonly="true" style="width:40px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;font-size: 30px;color: red;font-weight:bold;"/>
							<font style="font-size: 17px;font-weight: bold;">%</font>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!-- content end -->
	</div>
</body>
<script type="text/javascript">
var chart1; 
$(document).ready(function() {
	$.getJSON("/admin/complex/Complex",function(result){
		var couList = result['couList'];
		var ccList = result['ccList'];
		var complexRate = result['complexRate'];
		var cou = result['cou'];
		var zong = result['zong'];
		$('#complex').val(complexRate);
		$('#cou').val(cou);
		$('#zong').val(zong);
		chart1 = new Highcharts.Chart({
		chart : {
		renderTo : 'container',
		type : 'bar'
		},
		title : {
		text : '复购率报表'
		},
		credits: { 
	        enabled: false   //右下角不显示LOGO 
	    }, 
		xAxis : {
		categories :couList
		},
		yAxis : {
		title : {
		text : '人数'
		}
		},
		series : [ {
		name : '人数',
		data : ccList
		} ]
		});
	});
});
</script>
</html>