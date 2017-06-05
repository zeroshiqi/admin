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
<!-- <input type="button" value="查询" onclick="serach()"/> -->

	<!-- Javascript -->
<script type="text/javascript">
	var chart; 
	$(function() {
		$.getJSON("/admin/orderreport/OrderReport",function(result){
			var cou = result['cou'];
			alert(cou);
			var ym = result['ym'];
			alert(ym);
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
	
</body>
</html>