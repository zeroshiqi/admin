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
<!-- <SCRIPT src="https://code.highcharts.com/mapdata/countries/cn/custom/cn-all-sar-taiwan.js" type="text/javascript"></SCRIPT> -->
<!-- <SCRIPT src="/admin/js/Highmaps-4.2.4/js/highmaps.js" type="text/javascript"></SCRIPT> -->
<script src="https://code.highcharts.com/maps/highmaps.js"></script>
<script src="https://code.highcharts.com/maps/modules/exporting.js"></script>
<script src="https://code.highcharts.com/mapdata/countries/cn/custom/cn-all-sar-taiwan.js"></script>s

</head>
<body>
<div id="container" style="width: 100%;height: 700px;"></div>

	<!-- Javascript -->
<script type="text/javascript">

        $(document).ready(function () {
        	 var data = [
        	             {
        	                 "hc-key": "tw-ph",
        	                 "value": 300
        	             }
						]
        	         // Initiate the chart
        	         $('#container').highcharts('Map', {

        	             title : {
        	                 text : 'Highmaps basic demo'
        	             },

        	             subtitle : {
        	                 text : 'Source map: <a href="https://code.highcharts.com/mapdata/countries/cn/custom/cn-all-sar-taiwan.js">China with Hong Kong, Macau, and Taiwan</a>'
        	             },

        	             mapNavigation: {
        	                 enabled: true,
        	                 buttonOptions: {
        	                     verticalAlign: 'bottom'
        	                 }
        	             },

        	             colorAxis: {
        	                 min: 0
        	             },

        	             series : [{
        	                 data : data,
        	                 mapData: Highcharts.maps['countries/cn/custom/cn-all-sar-taiwan'],
        	                 joinBy: 'hc-key',
        	                 name: 'Random data',
        	                 states: {
        	                     hover: {
        	                         color: '#BADA55'
        	                     }
        	                 },
        	                 dataLabels: {
        	                     enabled: false,
        	                     format: '{point.name}'
        	                 }
        	             }]
        	         });
        	     });

    </script>
	
</body>
</html>