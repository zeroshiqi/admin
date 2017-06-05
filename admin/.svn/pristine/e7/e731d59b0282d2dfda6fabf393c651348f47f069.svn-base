<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<meta name="keywords" content="table">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%@include file="/base/link.jsp"%>
<link href="/admin/upload/css/default.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="/admin/upload/swfupload/swfupload.js"></script>
<script type="text/javascript" src="/admin/upload/swfupload.queue.js"></script>
<script type="text/javascript" src="/admin/upload/fileprogress.js"></script>
<script type="text/javascript" src="/admin/upload/handlers.js"></script>
<!-- 初始化swfupload 对象-->
<script type="text/javascript">
	var swfu;

	window.onload = function() {
		var settings = {
			flash_url : "/admin/upload/swfupload/swfupload.swf",
			upload_url : "/admin/course/upload",
			post_params : {
				"id" : $("#courseId").val()
			},
			file_post_name: "file",  
			file_size_limit : "10000 MB",
			file_types : "*.*",
			file_types_description : "All Files",
			file_upload_limit : 100,
			file_queue_limit : 0,
			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel"
			},
			debug : false,

			// Button settings
			button_image_url : "/admin/upload/images/TestImageNoText_65x29.png",
			button_width : "65",
			button_height : "29",
			button_placeholder_id : "spanButtonPlaceHolder",
			button_text : '点此上传',
			button_text_style : ".theFont { font-size: 16; }",
			button_text_left_padding : 12,
			button_text_top_padding : 3,

			// The event handler functions are defined in handlers.js
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : success,
			upload_complete_handler : uploadComplete,
			queue_complete_handler : queueComplete
		// Queue plugin event
		};

		swfu = new SWFUpload(settings);
	};
</script>
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
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">上传课件</strong> / <small>UploadFile</small>
				</div>
			</div>

			<hr />

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form class="am-form am-form-horizontal" id="myform" method="post">
						<div class="am-form-group">
							<label for="time-length" class="am-u-sm-3 am-form-label"></label>
							<div class="am-u-sm-9">
								<input type="number" id="time-length" value="${time}" name="time"
									placeholder="时长"> <input type="hidden" id="courseId" name="id"
									value="${id}">
							</div>
						</div>
						<div class="am-form-group">
							<label for="video-path" class="am-u-sm-3 am-form-label"></label>
							<div class="am-u-sm-9">
								<input type="text" id="video-path" value="${address}" name="path"
									placeholder="播放地址">
							</div>
						</div>

						<div class="am-form-group">
							<label for="time-length" class="am-u-sm-3 am-form-label"></label>
							<div class="am-u-sm-9 fieldset flash" id="fsUploadProgress">
								<span class="legend">Upload Queue</span>
							</div>
							<div id="divStatus">0 Files Uploaded</div>
							<div>
								<span id="spanButtonPlaceHolder"></span>
								<input id="btnCancel" type="button" value="取消上传"
									onclick="swfu.cancelQueue();" disabled="disabled"
									style="margin-left: 2px; font-size: 8pt; height: 29px;cursor: pointer;" />
							</div>
						</div>
						
						<div class="am-form-group">
				            <div class="am-u-sm-9 am-u-sm-push-3">
				              <a class="am-btn am-btn-primary" id="saveBtn">保存修改</a>
				              
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
	var flag = false;
	function success(file,data){
		flag = true;
		showInfo("上传成功",function(){},3000);
	}
	
	function uploadError(){
		showInfo("上传失败....",function(){},3000);
	}
	
	$(function(){
		$("#saveBtn").bind("click", function(){  
			var time = $("#time-length").val();
			if(time == ''){
				showInfo("请输入时长....",function(){},3000);
				return;
			}
			var path = $("#video-path").val();
			if(path == '' && flag == false){
				showInfo("请输入播放地址....",function(){},3000);
				return;
			}
			var options ={
					url:"/admin/course/updatePlayAddress",
					success : function(data) {
						if(data.status == 200){
			            	window.location.href = "/admin/course/online";
			            }else{
			            	showInfo(data.msg,function(){},3000);
			            }
					}
				};
				$("#myform").ajaxSubmit(options);	
		});
	});
</script>
</html>