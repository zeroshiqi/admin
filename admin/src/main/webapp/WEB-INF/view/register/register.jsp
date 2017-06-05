<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<%@include file="/base/link.jsp"%>
<SCRIPT src="/admin/js/Highcharts-5.0.6/code/highcharts.js" type="text/javascript"></SCRIPT>
<SCRIPT src="/admin/js/Highcharts-5.0.6/code/modules/exporting.js" type="text/javascript"></SCRIPT>
</head>
<body style="width: 100%; height: 100%">

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
				<li><a href="/admin/paynew">付费新增日报表</a></li>
				<li class="bgColor"><a href="/admin/register">注册用户数日报表</a></li>
				<li><a href="/admin/register/active">日活跃用户数报表</a></li>
				<li><a href="/admin/register/daily">线上课程日购买人数报表</a></li>
				<li><a href="/admin/register/purchaseRate">线上课程复购率报表</a></li>
				<li><a href="/admin/register/yesSale">线上课程销售报表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">
			<div class="am-g">
				<div class="am-u-md-3 am-cf">
				<div class="Go">
                 	<input type="text"  style="background:whight;alpha(opacity=50)" placeholder="开始时间" name="beginTime" class="text" value="${beginTime}" id="beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 23:59:59">
              		<input type="text"  style="background:whight;alpha(opacity=50)" placeholder="结束时间" name="endTime" class="text" value="${endTime}" id="endTime" onFocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 23:59:59">
					<button class="icon-search" onclick="submitForm()" type="button"></button>
					<a class="am-btn am-btn-primary" id="reset">重置</a>
					注册用户总量：<font size="30px" color="red" style="font-weight: bold;">
<!-- 					<input type="text" disabled="disabled" id="countAll" style="width:80px;font-size:20px;font-weight:bold;"/> -->
						<span id="countAll"></span>
					</font>
					时段内注册用户数量：<font size="30px" color="blue" style="font-weight: bold;">
<!-- 					<input type="text" disabled="disabled" id="countTime" style="width:100px;font-size:20px;font-weight:bold;"/> -->
					<span id="countTime"></span>
					</font>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-sm-12">
					<div id="container" style="min-width: 100%; height: 500px;"></div>
					<div id="container1" style="min-width: 100%; height: 500px;"></div>
<!-- 					<div id="container" style="min-width:400px;height:400px"></div> -->
				</div>

			</div>
		</div>
		<!-- content end -->
	</div>
</body>
<script type="text/javascript">
function submitForm() {
	$.getJSON("/admin/register/Report?beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val(),function(result){
		$('#countAll').text(result['countAll']);
		$('#countTime').text(result['countTime']);
		var couList = result['couList'];
		var ccList = result['list'];
// 		var complexRate = result['complexRate'];
// 		var cou = result['cou'];
// 		var zong = result['zong'];
// 		$('#complex').val(complexRate);
// 		$('#cou').val(cou);
// 		$('#zong').val(zong);
		$('#container').highcharts({
	        chart: {
	            type: 'line'
	        },
	        credits:{
	            enabled:false // 禁用版权信息
	       },
	        title: {
	            text: '注册用户数量趋势'
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	            categories: ccList
	        },
	        yAxis: {
	            title: {
	                text: '人数（个）'
	            }
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true
	                },
	                enableMouseTracking: false
	            }
	        },
	        series: [{
	            name: '日注册用户',
	            data: couList
	        }]
	    });
	});
	$.getJSON("/admin/register/ReportHour?beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val(),function(result){
		var couList = result['couList'];
		var ccList = result['list'];
		$('#container1').highcharts({
	        chart: {
	            type: 'line'
	        },
	        credits:{
	            enabled:false // 禁用版权信息
	       },
	        title: {
	            text: '注册用户数量趋势小时报表'
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	            categories: ccList
	        },
	        yAxis: {
	            title: {
	                text: '人数（个）'
	            }
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true
	                },
	                enableMouseTracking: false
	            }
	        },
	        series: [{
	            name: '小时内注册用户',
	            data: couList
	        }]
	    });
	});
}
$(function(){
	var endDate = getNowFormatDate();
	var beginDate = GetDateStr(-7) +" 00:00:00";
	$('#beginTime').val(beginDate);
	$('#endTime').val(endDate);
	$.getJSON("/admin/register/Report?beginTime="+beginDate+"&endTime="+endDate,function(result){
		$('#countAll').text(result['countAll']);
		$('#countTime').text(result['countTime']);
		var couList = result['couList'];
		var ccList = result['list'];
		$('#container').highcharts({
	        chart: {
	            type: 'line'
	        },
	        credits:{
	            enabled:false // 禁用版权信息
	       },
	        title: {
	            text: '注册用户数量趋势'
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	            categories: ccList
	        },
	        yAxis: {
	            title: {
	                text: '人数（个）'
	            }
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true
	                },
	                enableMouseTracking: false
	            }
	        },
	        series: [{
	            name: '日注册用户',
	            data: couList
	        }]
	    });
	});
	$.getJSON("/admin/register/ReportHour?beginTime="+beginDate+"&endTime="+endDate,function(result){
		var couList = result['couList'];
		var ccList = result['list'];
		$('#container1').highcharts({
	        chart: {
	            type: 'line'
	        },
	        credits:{
	            enabled:false // 禁用版权信息
	       },
	        title: {
	            text: '注册用户数量趋势小时报表'
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	            categories: ccList
	        },
	        yAxis: {
	            title: {
	                text: '人数（个）'
	            }
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true
	                },
	                enableMouseTracking: false
	            }
	        },
	        series: [{
	            name: '小时内注册用户',
	            data: couList
	        }]
	    });
	});
	$("#reset").bind("click", function(){  
		$('#beginTime').val('');
		$('#endTime').val('');
	});
})
//格式化日期
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

function GetDateStr(AddDayCount) { 
   var dd = new Date(); 
   dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
   var y = dd.getFullYear(); 
   var m = dd.getMonth()+1;//获取当前月份的日期 
   var d = dd.getDate(); 
   return y+"-"+m+"-"+d; 
 } 
var chart1; 
$(document).ready(function() {
	Highcharts.setOptions({
	    lang:{
	       contextButtonTitle:"图表导出菜单",
	       decimalPoint:".",
	       downloadJPEG:"下载JPEG图片",
	       downloadPDF:"下载PDF文件",
	       downloadPNG:"下载PNG文件",
	       downloadSVG:"下载SVG文件",
	       drillUpText:"返回 {series.name}",
	       loading:"加载中",
	       months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
	       noData:"没有数据",
	       numericSymbols: [ "千" , "兆" , "G" , "T" , "P" , "E"],
	       printChart:"打印图表",
	       resetZoom:"恢复缩放",
	       resetZoomTitle:"恢复图表",
	       shortMonths: [ "Jan" , "Feb" , "Mar" , "Apr" , "May" , "Jun" , "Jul" , "Aug" , "Sep" , "Oct" , "Nov" , "Dec"],
	       thousandsSep:",",
	       weekdays: ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期天"]
	    }
	}); 
});
</script>
</html>