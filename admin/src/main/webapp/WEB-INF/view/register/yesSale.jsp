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
<style>
  #loading{
 	transform: rotate(0deg);
     -webkit-animation: mymove 1.2s infinite linear;
     position: absolute;
     top: 0;
     bottom: 0;
     margin: auto;
     left: 40%;
     width: 60px;
  }
  #loadingBox{
   width: 100%;
   height: 130%;
   background: rgba(0,0,0,.6);
   top:0;
      position: absolute;
  }
  @keyframes mymove
  {
   from {transform:rotate(360deg)}
   to {transform:rotate(0deg)}
  }
  
  
 </style>
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
    <li><a href=" ">付费新增日报表</a ></li>
    <li><a href="/admin/register">注册用户数日报表</a ></li>
    <li><a href="/admin/register/active">日活跃用户数报表</a ></li>
    <li><a href="/admin/register/daily">线上课程日购买人数报表</a ></li>
    <li><a href="/admin/register/purchaseRate">线上课程复购率报表</a ></li>
    <li class="bgColor"><a href="/admin/register/yesSale">线上课程销售报表</a ></li>
   </ul>
  </div>

  <!-- content start -->
  <div class="admin-content">
   <div class="am-g">
    <div class="am-u-md-3 am-cf">
    <div class="Go">
                  <input type="text"  style="background:whight;alpha(opacity=50)" placeholder="开始时间" name="beginTime" class="text" value="${beginTime}" id="beginTime" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 00:00">
                <input type="text"  style="background:whight;alpha(opacity=50)" placeholder="结束时间" name="endTime" class="text" value="${endTime}" id="endTime" onFocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  placeholder="例 : 2015-07-30 00:00">
     <button class="icon-search" onclick="submitForm()" type="button"></button>
     <a class="am-btn am-btn-primary" id="reset">重置</a >
     <button type="button" id="exportFile" onclick="exportFile()"
       class="add">
       <span class="icon-plus"></span> 导出订单
      </button>
    </div>
   </div>
  <!--  <div class="am-g"> -->
    <div class="am-u-sm-12">
      <table style="width: 100%;height:100%;">
      <thead style="text-align: left;text-indent: 2rem;color: #fff;line-height: 2.2;">
        <tr><td>订单统计</td></tr>
       </thead>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         1、搜索时段内系列课销售订单：
         <span id="orderCount" style="color:red;font-weight:bold;font-size:18px;"></span>
         <font style="size:14px;">(web订单：<span id="webOrderCount"></span>个，app订单：<span id="appOrderCount"></span>个)</font>
        </td>
       </tr>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         2、搜索时段内系列课销售金额：
         <span id="orderPriceSum" style="color:red;font-weight:bold;font-size:18px;"></span>
         <font style="size:14px;">(web销售额：<span id="webOrderPriceSum"></span>元，app销售额：<span id="appOrderPriceSum"></span>元)</font>
        </td>
       </tr>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         3、截止<span id="endD" style="font-size:16px;color:green;font-weight: bold;"></span>累计注册人数：
         <span id="count" style="color:red;font-weight:bold;font-size:18px;"></span>，
        </td>
       </tr>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;搜索时段内新增注册人数：<span id="countTime" style="color:black;font-weight:bold;font-size:18px;"></span>
        </td>
       </tr>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         4、截止<span id="endD1" style="font-size:16px;color:green;font-weight: bold;"></span>累计付费用户数：
         <span id="couAll" style="color:red;font-weight:bold;font-size:18px;"></span>，
        </td>
       </tr>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;搜索时段内新增付费用户数：<span id="couTime" style="color:black;font-weight:bold;font-size:18px;"></span>
        </td>
       </tr>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;搜索时段内付费人数：<span id="buyCount" style="color:black;font-weight:bold;font-size:18px;"></span>
        </td>
       </tr>
       <tr style="width:100%;height: 50px;">
        <td style="width:100%;font-size:16px;color:black;padding-left: 10%;" align="left">
         5、搜索时段内唤醒App用户数：
         <span id="awakenCount" style="color:red;font-weight:bold;font-size:18px;"></span>，
         其中有学习记录的人数为：<span id="studyCount" style="color:blue;font-weight:bold;font-size:18px;"></span>，
         学习人数占唤醒人数比例为：<span id="studyPercent" style="color:blue;font-weight:bold;font-size:18px;"></span>
        </td>
       </tr>
      </table>
     </div>
   <!-- </div> -->
   <div id='spread'>
    <div class="am-u-sm-12">
     <form class="am-form">
      <table  id="myTable" class="am-table am-table-striped am-table-hover table-main">
       <thead>
        <tr>
          <th style="width: 20%">课程名称</th>
          <th style="width: 10%"><center>推广码</center></th>
          <th style="width: 10%"><center>推广渠道</center></th>
          <th style="width: 5%">单价</th>
          <th style="width: 7%">单量</th>
          <th style="width: 8%"><center>金额</center></th>
          <th style="width: 10%"><center>总订单数</center></th>
          <th style="width: 10%"><center>总金额</center></th>
          <th style="width: 10%"><center>合作方式</center></th>
 <!--          <th style="width: 10%"><center>购买意向</center></th> -->
 <!--          <th style="width: 10%"><center>参课原因</center></th> -->
         </tr>
       </thead>
       <!-- <tbody id="mytable">

       </tbody> -->
      </table>
     </form>
    </div>
   </div>
   <div id="loadingBox">
    <img id="loading" src="http://file.ichazuo.cn/1494993090937432.png" alt="">
   </div>
  </div>
  <!-- content end -->
 </div>
</body>
<script type="text/javascript">
function submitForm() {
 $('#loadingBox').show();
 $('#endD').text($('#endTime').val());
 $('#endD1').text($('#endTime').val());
 $('#spread').hide();
 $('#myTable tr:gt(0)').remove();
 $.getJSON("/admin/register/yesSaleRate?beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val(),function(result){
  $('#loadingBox').hide();
  var orderCount = result['orderCount'];
  var webOrderCount = result['webOrderCount'];
  var appOrderCount = result['appOrderCount'];
  var orderPriceSum = result['orderPriceSum'];
  var webOrderPriceSum = result['webOrderPriceSum'];
  var appOrderPriceSum = result['appOrderPriceSum'];
  var buyCount = result['buyCount'];
  var orderFromList = result['orderFromList'];
  $('#orderCount').text(orderCount+"个");
  $('#webOrderCount').text(webOrderCount);
  $('#appOrderCount').text(appOrderCount);
  $('#orderPriceSum').text(orderPriceSum+"元");
  $('#webOrderPriceSum').text(webOrderPriceSum);
  $('#appOrderPriceSum').text(appOrderPriceSum);
  $('#count').text(result['registerCount']+"人");
  $('#countTime').text(result['countTime']+"人");
  $('#couAll').text(result['couAll']+"人");
  $('#couTime').text(result['couTime']+"人");
  $('#awakenCount').text(result['awakenCount']);
  $('#studyCount').text(result['studyCount']);
  $('#buyCount').text(buyCount);
  if(orderFromList.length>0){
   $('#spread').show();
   $(".mytable").remove();
   for(var i=0;i<orderFromList.length;i++){
    $("#myTable").append("<tbody><tr>"+
      "<td><center>"+orderFromList[i].course_name+"</center></td>"+
      "<td><center>"+orderFromList[i].from1+"</center></td>"+
      "<td><center>"+orderFromList[i].promotionParty+"</center></td>"+
      "<td><center>"+orderFromList[i].price+"</center></td>"+
      "<td><center>"+orderFromList[i].cou+"</center></td>"+
      "<td><center>"+orderFromList[i].sprice+"</center></td>"+
      "<td><center>"+orderFromList[i].couALl+"</center></td>"+
      "<td><center>"+orderFromList[i].spriceAll+"</center></td>"+
      "<td><center>"+orderFromList[i].cooperationMode+"</center></td>"+
     "</tr></tbody>")
   }
  }
 });
}
$(function(){
 var endDate = getNowFormatDate();
 var beginDate = GetDateStr(0) +" 00:00:00";
 $('#beginTime').val(beginDate);
 $('#endTime').val(endDate);
 $('#endD').text(endDate);
 $('#endD1').text(endDate);
 $('#spread').hide();
 $.getJSON("/admin/register/yesSaleRate?beginTime="+beginDate+"&endTime="+endDate,function(result){
  $('#loadingBox').hide();
  var orderCount = result['orderCount'];
  var webOrderCount = result['webOrderCount'];
  var appOrderCount = result['appOrderCount'];
  var orderPriceSum = result['orderPriceSum'];
  var webOrderPriceSum = result['webOrderPriceSum'];
  var appOrderPriceSum = result['appOrderPriceSum'];
  var buyCount = result['buyCount'];
  var orderFromList = result['orderFromList'];
  $('#orderCount').text(orderCount+"个");
  $('#webOrderCount').text(webOrderCount);
  $('#appOrderCount').text(appOrderCount);
  $('#orderPriceSum').text(orderPriceSum+"元");
  $('#webOrderPriceSum').text(webOrderPriceSum);
  $('#appOrderPriceSum').text(appOrderPriceSum);
  $('#count').text(result['registerCount']+"人");
  $('#countTime').text(result['countTime']+"人");
  $('#couAll').text(result['couAll']+"人");
  $('#couTime').text(result['couTime']+"人");
  $('#awakenCount').text(result['awakenCount']);
  $('#studyCount').text(result['studyCount']);
  $('#buyCount').text(buyCount);
  $('#studyPercent').text(Percentage(result['studyCount'],result['awakenCount']));
  if(orderFromList.length>0){
   $('#spread').show();
   $(".mytable").remove();
   for(var i=0;i<orderFromList.length;i++){
    $("#myTable").append("<tbody><tr>"+
      "<td><center>"+orderFromList[i].course_name+"</center></td>"+
      "<td><center>"+orderFromList[i].from1+"</center></td>"+
      "<td><center>"+orderFromList[i].promotionParty+"</center></td>"+
      "<td><center>"+orderFromList[i].price+"</center></td>"+
      "<td><center>"+orderFromList[i].cou+"</center></td>"+
      "<td><center>"+orderFromList[i].sprice+"</center></td>"+
      "<td><center>"+orderFromList[i].couALl+"</center></td>"+
      "<td><center>"+orderFromList[i].spriceAll+"</center></td>"+
      "<td><center>"+orderFromList[i].cooperationMode+"</center></td>"+
     "</tr></tbody>")
   }
  }
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
 //求两个数的百分比
function Percentage(num, total) { 
    return (Math.round(num / total * 10000) / 100.00 + "%");// 小数点后两位百分比
}
 //导出订单
function exportFile() {
 window.location.href = "/admin/order/export?beginTime="+$('#beginTime').val()+"&endTime="+$('#endTime').val();
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