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

			<div class="am-g">
				<div class="am-u-sm-12">
					<div id="chart_line" style="width: 100%; height: 400px">
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>
</body>
<script type="text/javascript">
var chart; 
$(function() {
	$.getJSON("/admin/orderreport/OrderReport",function(result){
		var cou = result['cou'];
		var ym = result['ym'];
	    chart = new Highcharts.Chart({ 
	        chart: { 
	            renderTo: 'chart_line', //图表放置的容器，DIV 
	            defaultSeriesType: 'line', //图表类型line(折线图) 
	            zoomType: 'x'   //x轴方向可以缩放 
	        }, 
	        credits: { 
	            enabled: false   //右下角不显示LOGO 
	        }, 
	        title: { 
	            text: '订单金额月报表'  
	        }, 
	        subtitle: { 
	            text: '2015-2016年'  //副标题 
	        }, 
	        xAxis: {  //x轴 
	        	//x轴标签名称 
	            categories: ym, 
	            gridLineWidth: 1, //设置网格宽度为1 
	            lineWidth: 2,  //基线宽度 
	            labels:{y:26}  //x轴标签位置：距X轴下方26像素 
	        }, 
	        yAxis: {  //y轴 
	            title: {text: '金额'}, //标题 
	            lineWidth: 2 //基线宽度 
	        }, 
	        plotOptions:{ //设置数据点 
	            line:{ 
	                dataLabels:{ 
	                    enabled:true  //在数据点上显示对应的数据值 
	                }, 
	                enableMouseTracking: false //取消鼠标滑向触发提示框 
	            } 
	        }, 
	        legend: {  //图例 
	            layout: 'horizontal',  //图例显示的样式：水平（horizontal）/垂直（vertical） 
	            backgroundColor: '#ffc', //图例背景色 
	            align: 'left',  //图例水平对齐方式 
	            verticalAlign: 'top',  //图例垂直对齐方式 
	            x: 100,  //相对X位移 
	            y: 70,   //相对Y位移 
	            floating: true, //设置可浮动 
	            shadow: true  //设置阴影 
	        }, 
	        exporting: { 
	            enabled: false  //设置导出按钮不可用 
	        }, 
	        series: [
	        {  //数据列 
	            name: '月销售额', 
	            data: cou 
	        }
	        ] 
	    });
	});
});
</script>
</html>