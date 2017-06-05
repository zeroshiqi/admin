<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
<link rel="stylesheet" href="/admin/login/css/index.css">
<SCRIPT src="/admin/login/js/jquery-1.9.1.min.js" type="text/javascript"></SCRIPT>
<SCRIPT src="/admin/login/js/jquery.form.js" type="text/javascript"></SCRIPT>
<SCRIPT src="/admin/js/jquery-1.12.3.min.js" type="text/javascript"></SCRIPT>
<SCRIPT src="/admin/js/Highcharts-4.2.3/js/highcharts.js" type="text/javascript"></SCRIPT>

</head>
<body>
<div id="chart_line" style="width: 100%; height: 400px"></div>
<input type="button" value="查询" onclick="serach()"/>

	<!-- Javascript -->
<script type="text/javascript">
	var chart; 
	$(function() {
		$.getJSON("/admin/ordercount/month",function(result){
		    chart = new Highcharts.Chart({ 
		        chart: { 
		            renderTo: 'chart_line', //图表放置的容器，DIV 
		            defaultSeriesType: 'line', //图表类型line(折线图), 
		            zoomType: 'x'   //x轴方向可以缩放 
		        }, 
		        credits: { 
		            enabled: false   //右下角不显示LOGO 
		        }, 
		        title: { 
		            text: '购买金额报表'  
		        }, 
		        subtitle: { 
		            text: '2016年'  //副标题 
		        }, 
		        xAxis: {  //x轴 
		            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'], //x轴标签名称 
		            gridLineWidth: 1, //设置网格宽度为1 
		            lineWidth: 2,  //基线宽度 
		            labels:{y:26}  //x轴标签位置：距X轴下方26像素 
		        }, 
		        yAxis: {  //y轴 
		            title: {text: '平均气温(°C)'}, //标题 
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
		            name: '一楼1号', 
		            data: [ - 4.6, -2.2, 4.5, 13.1, 19.8, 24.0, 25.8, 24.4, 19.3] 
		        }, 
		        { 
		            name: '一楼2号', 
		            data: [13.3, 14.4, 17.7, 21.9, 24.6, 27.2, 30.8, 32.1, 27.2, 23.7, 21.3, 15.6] 
		        },{ 
		            name: '一楼3号', 
		            data: [10.3, 11.4, 13.7, 22.9, 24.6, 37.2, 35.8, 32.1, 29.2] 
		        },{ 
		            name: '一楼4号', 
		            data: [12.3, 15.4, 17.7, 22.9, 23.6, 27.2, 35.8, 32.1, 24.2, 21.7, 10.3, 9.6] 
		        },{ 
		            name: '一楼5号', 
		            data: [14.3, 16.4, 13.7, 12.9, 26.6, 33.2, 36.8, 38.1, 22.2, 22.7, 21.3, 12.6] 
		        }] 
		    }); 
		}); 
	});
	function serach(){
		$.getJSON("/admin/ordercount/month",function(result){
			alert(result);
		});
	}
// 	$(function() {
// 		$.getJSON("/admin/ordercount",function(result){
// 		    chart = new Highcharts.Chart({ 
// 		        chart: { 
// 		            renderTo: 'chart_line', //图表放置的容器，DIV 
// 		            defaultSeriesType: 'line', //图表类型line(折线图), 
// 		            zoomType: 'x'   //x轴方向可以缩放 
// 		        }, 
// 		        credits: { 
// 		            enabled: false   //右下角不显示LOGO 
// 		        }, 
// 		        title: { 
// 		            text: '购买金额报表'  
// 		        }, 
// 		        subtitle: { 
// 		            text: '2016年'  //副标题 
// 		        }, 
// 		        xAxis: {  //x轴 
// 		            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'], //x轴标签名称 
// 		            gridLineWidth: 1, //设置网格宽度为1 
// 		            lineWidth: 2,  //基线宽度 
// 		            labels:{y:26}  //x轴标签位置：距X轴下方26像素 
// 		        }, 
// 		        yAxis: {  //y轴 
// 		            title: {text: '平均气温(°C)'}, //标题 
// 		            lineWidth: 2 //基线宽度 
// 		        }, 
// 		        plotOptions:{ //设置数据点 
// 		            line:{ 
// 		                dataLabels:{ 
// 		                    enabled:true  //在数据点上显示对应的数据值 
// 		                }, 
// 		                enableMouseTracking: false //取消鼠标滑向触发提示框 
// 		            } 
// 		        }, 
// 		        legend: {  //图例 
// 		            layout: 'horizontal',  //图例显示的样式：水平（horizontal）/垂直（vertical） 
// 		            backgroundColor: '#ffc', //图例背景色 
// 		            align: 'left',  //图例水平对齐方式 
// 		            verticalAlign: 'top',  //图例垂直对齐方式 
// 		            x: 100,  //相对X位移 
// 		            y: 70,   //相对Y位移 
// 		            floating: true, //设置可浮动 
// 		            shadow: true  //设置阴影 
// 		        }, 
// 		        exporting: { 
// 		            enabled: false  //设置导出按钮不可用 
// 		        }, 
// 		        series: [
// 		        {  //数据列 
// 		            name: '一楼1号', 
// 		            data: [ - 4.6, -2.2, 4.5, 13.1, 19.8, 24.0, 25.8, 24.4, 19.3] 
// 		        }, 
// 		        { 
// 		            name: '一楼2号', 
// 		            data: [13.3, 14.4, 17.7, 21.9, 24.6, 27.2, 30.8, 32.1, 27.2, 23.7, 21.3, 15.6] 
// 		        },{ 
// 		            name: '一楼3号', 
// 		            data: [10.3, 11.4, 13.7, 22.9, 24.6, 37.2, 35.8, 32.1, 29.2] 
// 		        },{ 
// 		            name: '一楼4号', 
// 		            data: [12.3, 15.4, 17.7, 22.9, 23.6, 27.2, 35.8, 32.1, 24.2, 21.7, 10.3, 9.6] 
// 		        },{ 
// 		            name: '一楼5号', 
// 		            data: [14.3, 16.4, 13.7, 12.9, 26.6, 33.2, 36.8, 38.1, 22.2, 22.7, 21.3, 12.6] 
// 		        }] 
// 		    }); 
// 		}); 
// 	});
	</script>
	
</body>
</html>