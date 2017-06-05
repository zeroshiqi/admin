
var pf = {
		
		
		getIndexElement:function(){
			return top;
		},
		/**
		 * 打开加载进度条
		 */
		openpageloading : function(){
			$.messager.progress({text:"loading....."});
		},
		
		/**
		 * 关闭加载进度跳
		 */
		closepageloading : function(){
			$.messager.progress("close");
		},
		
		/**
		 * 将url添加当前时间，解决缓存问题
		 */
		_updateUrl : function(ajaxUrl) {
			if (ajaxUrl.indexOf('?') >= 0) {
				return ajaxUrl + '&ajax_timestamp=' + new Date().getTime();
			} else {
				return ajaxUrl + '?ajax_timestamp=' + new Date().getTime();
			}
		},
		
		/**
		 * Ajax请求，只有请求，返回的内容请用回调函数处理
		 * 
		 * @pageUrl 访问页面的URL
		 * @callFunction 要回调的函数名称
		 */
		ajaxGet : function(pageUrl, callFunction) {
//			pf.openpageloading();
			pageUrl = this._updateUrl(pageUrl);
			 jQuery.ajax({
                 url: pageUrl,
                 type: "get",
                 success: function(data, textStatus) {
//                	pf.closepageloading();
     				if (callFunction !== undefined && typeof (callFunction) == "function") {
     					callFunction(data);
     				}
                 },
                 error: pf.ajaxError
             });
		},	
		
		ajaxJson : function(pageUrl, data ,callFunction,contentType){
			if(typeof(contentType) == 'undefined' || contentType === null){
				contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
			}
			$.ajax({
				   type: 'POST',
				   url : pageUrl,
				   data: data,
				   contentType:contentType,
				   dataType: 'json',
				   success: function(jsonData){
				      if (callFunction != undefined && typeof (callFunction) == 'function') {
   					       callFunction(jsonData);
   				      }
				   },
				   error : pf.ajaxError
			});
		},
		
		/**
		 * ajax提交一个form到某个url
		 * 
		 * @param formId 表单Id
		 * @param callFunction 回调函数
		 */
		ajaxSubmitFormToUrl : function( pageUrl ,formId, div ,callFunction){
			if(!pf.validateForm(formId)){
				return;
			}
			pf.openpageloading();
			pageUrl = this._updateUrl(pageUrl);
			 jQuery.ajax({
                url: pageUrl,
                type: "post",
                data:$('#'+formId).serialize(),
                success: function(data, textStatus) {
               	pf.closepageloading();
               		if(div != undefined && div != "" && div != null){
               			$("#"+div).html(data);
               			pf.parse($("#"+div));
               		}
    				if (callFunction != undefined && typeof (callFunction) == "function") {
    					callFunction(data);
    				}
                },
                error: pf.ajaxError
            });
		},
		
		/**
		 * 分页查询方法
		 * @param pageUrl 查选地址
		 * @param start 分页开始值
		 * @param formId 参数formId
		 * @param div 查询结果返回DIV
		 * @param callFunction 回调函数
		 */
		pagingToUrl : function(pageUrl ,start,formId, div ,callFunction,pageSize){
			if(!pf.validateForm(formId)){
				return;
			}
			if(pageSize === undefined){
		       pageSize = 10;
		    }
			pf.openpageloading();
			var dat = $('#'+formId).serialize();
			dat += '&start='+start+'&pageSize='+pageSize;
			pageUrl = this._updateUrl(pageUrl);
			 jQuery.ajax({
                url: pageUrl,
                type: "post",
                data:dat,
                success: function(data, textStatus) {
               	pf.closepageloading();
               		if(div != undefined && div != "" && div != null){
               			$("#"+div).html(data);
               			pf.parse($("#"+div));
               		}
    				if (callFunction != undefined && typeof (callFunction) == "function") {
    					callFunction(data);
    				}
                },
                error: pf.ajaxError
            });
		},
		/**
		 * 分页查询方法，查询地址为form的Action
		 * @param start 分页开始值
		 * @param formId 参数formId
		 * @param div 查询结果返回DIV
		 * @param callFunction 回调函数
		 */
		paging: function(start,formId, div ,callFunction,pageSize){
			var pageUrl = $('#' + formId).attr('action');
			pf.pagingToUrl(pageUrl, start, formId, div, callFunction,pageSize);
		},
		
		/**
		 * 验证form数据格式
		 * 
		 * @param formId
		 * @returns
		 */
		validateForm : function (formId){
			return $('#'+formId).form('validate');
		},
		
		/**
		 * 提交一个form，提交前验证
		 * 
		 * @param formId
		 */
		submitForm : function (formId){
			if(pf.validateForm(formId)){
				$('#'+formId).submit();
			}
		},
		
		
		/**
		 * ajax提交一个form
		 * 
		 * @param formId 表单Id
		 * @param callFunction 回调函数
		 */
		ajaxSubmitForm : function( formId, div,callFunction,params) {
			var pageUrl = $('#' + formId).attr('action');
			pf.ajaxSubmitFormToUrl(pageUrl, formId, div,callFunction,params);
		},
		
		
		/**
		 * ajax提交一个表单，并附带其他参数
		 * @param formId
		 * @param pas   如 "a=1&b=2&c=3"
		 * @param callFunction
		 */
		ajaxSumbitFormAddPas : function (formId,pas,callFunction){
			if(!pf.validateForm(formId)){
				return;
			}
//			pf.openpageloading();
			var pageUrl = $('#' + formId).attr('action');
			var data = $('#'+formId).serialize();
			data +="&"+pas;
			pf.ajaxPost(pageUrl, data, callFunction);
		},
		/**
		 * ajax提交一个表单到指定的URl，并附带其他参数
		 * @param pageUrl
		 * @param formId
		 * @param pas   如 "a=1&b=2&c=3"
		 * @param callFunction
		 */
		ajaxSumbitFormAddPasToUrl : function (pageUrl,formId,pas,callFunction){
			if(!pf.validateForm(formId)){
				return;
			}
			pf.openpageloading();
			var data = $('#'+formId).serialize();
			data +="&"+pas;
			pf.ajaxPost(pageUrl, data, callFunction);
		},
		
		
		/**
		 * post方式ajax
		 * @param pageUrl
		 * @param data
		 * @param callFunction
		 */
		ajaxPost : function(pageUrl, data ,callFunction){
//			pf.openpageloading();
			pageUrl = this._updateUrl(pageUrl);
			 jQuery.ajax({
               url: pageUrl,
               type: "post",
               data: data,
               success: function(data, textStatus) {
//              	pf.closepageloading();
   				if (callFunction != undefined && typeof (callFunction) == "function") {
   					callFunction(data);
   				}
               },
               error: pf.ajaxError
           });
		},
		/**
		 * post方式ajax  没有加载图片
		 * @param pageUrl
		 * @param data
		 * @param callFunction
		 */
		ajaxPostNotLoad : function(pageUrl, data ,callFunction){
			pageUrl = this._updateUrl(pageUrl);
			 jQuery.ajax({
               url: pageUrl,
               type: "post",
               data: data,
               success: function(data, textStatus) {
   				if (callFunction != undefined && typeof (callFunction) == "function") {
   					callFunction(data);
   				}
               },
               error: pf.ajaxError
           });
		},
		
		/**
		 * post方式ajax
		 * @param pageUrl
		 * @param data
		 * @param callFunction
		 */
		ajaxPostNoLoading : function(pageUrl, data ,callFunction){
			pageUrl = this._updateUrl(pageUrl);
			 jQuery.ajax({
               url: pageUrl,
               type: "post",
               data: data,
               success: function(data, textStatus) {
   				if (callFunction != undefined && typeof (callFunction) == "function") {
   					callFunction(data);
   				}
               },
               error: pf.ajaxError
           });
		},
		
		/**
		 * ajax请求异常处理
		 * 
		 * @param XMLHttpRequest
		 * @param textStatus
		 * @param errorThrown
		 */
		ajaxError : function(msg){
			pf.closepageloading();
			pf.removeLoading();
			//var info = '系统发生错误['+msg.status+'],<a class="ahref" href="javascript:void(0)">查看</a>详细信息!';
			//msg.responseText
			$.messager.alert('系统提示','系统发生错误，请联系管理员!','error');
		},
		pageStart : 0,
		/**
		 * 检查输入的分页数合法性
		 */
		checkPageNum : function(inputNum, i, maxNum) {
			var pageNum = inputNum.value;
			if (isNaN(pageNum) || (pageNum <= 0 && pageNum != '') || pageNum.indexOf('.') > 0) {
				alert(_msg._num);
				inputNum.focus();
			} else if (pageNum > maxNum) {
				alert(_msg._maxNum);
				inputNum.focus();
			}
			pf.pageStart = i;
		},

		/**
		 * 输入页码后的跳转
		 */
		pagingFun : function(pageFun) {
			pageFun(pf.pageStart);
		},
		
		
		/**
		 * 初始化一个省市联动选择框
		 * 
		 * @param proCodeId
		 * @param cityCodeId
		 * @param proNameId
		 * @param cityNameId
		 */
		initCityCode : function (proCodeId,cityCodeId,proNameId,cityNameId){
			
			var nowProCode = $("#"+proCodeId).val();
			var nowCityCode = $("#"+cityCodeId).val();
			
			$("#" + proCodeId).html("");
			$.each(province, function (k, p) { 
                var option = "<option value='" + p.ProID + "'>" + p.ProName + "</option>";    
                $("#"+proCodeId).append(option);
            });
             
            $("#"+proCodeId).change(function () {
            	$("#"+proNameId).val($(this).find("option:selected").text());
                var selValue = $(this).val(); 
                $("#" + cityCodeId).html("");
                 
                $.each(city, function (k, p) { 
                    if (p.ProID == selValue) {
                        var option = "<option value='" + p.CityID + "'>" + p.CityName + "</option>";
                        $("#"+cityCodeId).append(option);
                    }
                });
                $("#"+cityCodeId).change();
            });
            
            $("#"+cityCodeId).change(function () {
            	$("#"+cityNameId).val($(this).find("option:selected").text());
            }); 
            
            if(nowProCode != ""){
            	$("#"+proCodeId).attr("value",nowProCode+"");
            }
            $("#"+proCodeId).change();     
            
            
            if(nowCityCode != ""){
            	$("#"+cityCodeId).attr("value",nowCityCode);
            }
            $("#"+cityCodeId).change();       
		},
		
		alert : function (str){
			$.messager.alert(_msg._title,str);
		},
		
		
		/**
		 * 弹出一个确认框
		 * @param msg 确认框内的提示内容
		 * @param CallBackFunction 点确认和取消时调用的函数，有一个boolean标识参数
		 */
		confirm : function (msg,CallBackFunction){
			$.messager.confirm(_msg._title,msg,CallBackFunction);
		},
		
		/**
		 * 提交一个带附件的form
		 */
		submitFileForm : function (formId, CallBackFunction){
			var pageUrl = $("#"+formId).attr("action");
			pf.submitFileFormToUrl(formId, pageUrl, CallBackFunction);
		},
		
		/**
		 * 提交一个带附件的form到一个Url上
		 */
		submitFileFormToUrl : function (formId, pageUrl, CallBackFunction){
			
			if(!pf.validateForm(formId)){
				return ;
			}
			pf.fileUploadCallBack = CallBackFunction;
			pf.openpageloading();
			var form = $("#"+formId);
			form.attr("action",pageUrl);
			form.attr("method","post");
			form.attr("enctype","multipart/form-data");
			form.attr("target","fileIframe");
			form.submit();
		},
		/**
		 * 提交一个单独额带附件的form
		 */
		submitSingleFileForm : function (mainitemId,CallBackFunction,mainitemType){
			if(typeof(mainitemType) != 'undefined' && mainitemType != null && $.trim(mainitemType) != ''){
				$("#mainitemType").val(mainitemType);
		    }
		   if(typeof(mainitemId) != 'undefined' && mainitemId != null && $.trim(mainitemId) != ''){
				$("#mainitemId").val(mainitemId);
		    }
			var pageUrl = $("#"+"fileForm").attr("action");
			pf.submitFileFormToUrl("fileForm", pageUrl, CallBackFunction);
		},
		
		
		fileUploadCallBack:"",
		
		/**
		 * Ajax回调成功触发事件
		 */
		_ajaxSuccessEvent : function(elementDiv) {
			// 如果存在Ajax回调成功的事件，那么触发事件。
			if (elementDiv.successEvent) {
				elementDiv.fire(elementDiv.successEvent);
			}
		},
		
		/**
		 * 新建一个标签页
		 * @param url
		 * @param title
		 * @param data
		 * @param noSingle 是不是为非单立的
		 */
		newTabs : function (pageUrl , title, callFunction,single){
			pf.newTabsByPas(pageUrl, title, "", callFunction, single);
		},
		
		newTabsByPas : function (pageUrl , title,data, callFunction, single){
			pf.ajaxPost(pageUrl, data, 
					function (data){
						alert(data);
						if(single == undefined || single){
							if($('#jhtabs').tabs('exists',title)){
								$('#jhtabs').tabs('close',title);
							}
						}
						$('#jhtabs').tabs('add',{
						    title:title,
						    content:data,
						    closable:true,
						    selected:true
						});
						if (callFunction != undefined && typeof (callFunction) == "function") {
		   					callFunction(data);
		   				}
					}
				);
		},
		/**
		 * 关闭一个Tab页签
		 */
		colesTab : function(title){
			$('#mainDiv').tabs('close',title);
		},
		
		/**
		 * 关闭元素所属的tab页
		 * @param e
		 */
		closeNowTab : function (){
			var tab = $('#mainDiv').tabs('getSelected');
			var index = $('#mainDiv').tabs('getTabIndex',tab);
			$('#mainDiv',document).tabs('close',index);
		},
		
		closeNowFirstTab : function (){
			var tab = $('#jhtabs').tabs('getSelected');
			var index = $('#jhtabs').tabs('getTabIndex',tab);
			$("#jhtabs").tabs('close',index);
		},
		
		/**
		 * 新建一个IFRameTab
		 * @param url
		 * @param title
		 * @param single
		 */
		newIFrameTab : function (url,title,single){
			
			
			pf.openpageloading();
			if(single == undefined || single){
				if($('#jhtabs').tabs('exists',title)){
					$('#jhtabs').tabs('close',title);
				}
			}
			
			var frame = '<iframe class="tabIframe" frameborder="0" onload="pf.closepageloading();"  src="' + url + '" width="100%;" height="99.8%;" >';
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true
			});
			
		},
		/**
		 * voice
		 */
		newVoiceTab:function(url,title,id){
			//top.pf.openpageloading();
			var frame = '<iframe id="'+id+'" class="tabIframe" frameborder="0"   src="' + url + '" width="100%;" height="99.8%;" >';			
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true//,
//			    onBeforeDestroy:function(){
//			    	 var frame = $('iframe', this);  
//			         if (frame.length > 0) { 
////			        	 alert(frame[0].contentWindow.document.body.innerHTML);
//			        	 frame[0].src='about:blank';
//			             frame[0].contentWindow.document.write('');  
//			             frame[0].contentWindow.close();
//			             frame.remove();  
//			             if ($.browser.msie) {  
//			                 CollectGarbage();
//			             }
//			         }
//			    }
			    
			});
		},
		/**
		 * email iframe专用
		 */
		newemailTab:function(url,title,id){
			//pf.openpageloading();
			var frame = '<iframe id="'+id+'" name="email'+id+'" class="tabIframe" frameborder="0"  onload=top.emailCallback("'+id+'") src="' + url + '" width="100%;" height="99.8%;" >';			
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true//,
//			    onBeforeDestroy:function(){
//			    	 var frame = $('iframe', this);  
//			         if (frame.length > 0) { 
////			        	 alert(frame[0].contentWindow.document.body.innerHTML);
//			        	 frame[0].src='about:blank';
//			             frame[0].contentWindow.document.write('');  
//			             frame[0].contentWindow.close();
//			             frame.remove();  
//			             if ($.browser.msie) {  
//			                 CollectGarbage();
//			             }
//			         }
//			    }
			    
			});
		},
		/**
		 * chat  qq  weixin  webchat  iframe专用
		 */
		newChatTab:function(url,title,id){
			//top.pf.openpageloading();
			var frame = '<iframe id="'+id+'" class="tabIframe" frameborder="0"  onload=top.chatCallback("'+id+'") src="' + url + '" width="100%;" height="99.8%;" >';	
			/****************************************************************************************/
			/* 判断是否包含有title的tab页，并判断connId是否相等，如果不相等则前一个回话的回话id失效，
			 * 改变前一个会话的tab页名字，加上"结束"作为新名字 */
			if($('#jhtabs').tabs('exists',title)){	//判断名为title的tab页是否存在
				if($('iframe[id=connId]').length==0){	//判断原来的会话是否失效，若失效(length属性为0)，则在失效的tab的title加上“结束”两个字，避免和新开的tab页冲突
					var connId = id;	//connId
					var $tab = $('#jhtabs').tabs('getTab',title);	//根据title取出tab
					var newTi = title + "-结束";	//新标题
					$('#jhtabs').tabs('setTabTitle',{tab:$tab,title:newTi});	//不刷新页面改title名字！
					/*$tab.panel({title: newTi});	//改变标题
					$('#jhtabs').tabs('update', {
						tab: $tab,
						options: {
							title: newTitle
						}
					});*/
				}
			}
			/****************************************************************************************/
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true//,
//			    onBeforeDestroy:function(){
//			    	 var frame = $('iframe', this);  
//			         if (frame.length > 0) { 
////			        	 alert(frame[0].contentWindow.document.body.innerHTML);
//			        	 frame[0].src='about:blank';
//			             frame[0].contentWindow.document.write('');  
//			             frame[0].contentWindow.close();
//			             frame.remove();  
//			             if ($.browser.msie) {  
//			                 CollectGarbage();
//			             }
//			         }
//			    }
			    
			});
		},
		/**
		 * sms
		 */
		newSmsTab:function(url,title,id){
			//top.pf.openpageloading();
			var frame = '<iframe id="'+id+'" class="tabIframe" frameborder="0"  onload=top.smsCallback("'+id+'") src="' + url + '" width="100%;" height="99.8%;" >';			
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true
			});
		},
		/**
		 * 海量
		 */
		newHailiangTab : function(url,title,single,id){
			pf.openpageloading();
			/*if(single == undefined || single){
				if($('#jhtabs').tabs('exists',title)){
					$('#jhtabs').tabs('close',title);
				}
			}*/
			var frame = '<iframe id="'+id+'" class="tabIframe" frameborder="0"  onload=top.hailiangCallback("'+id+'") src="' + url + '" width="100%;" height="99.8%;" >';			
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true
			});
		},
		/**
		 * 微博   iframe专用
		 */
		newWeiboTab : function(url,title,single,id){
			pf.openpageloading();
			/*if(single == undefined || single){
				if($('#jhtabs').tabs('exists',title)){
					$('#jhtabs').tabs('close',title);
				}
			}*/
			var frame = '<iframe id="'+id+'" class="tabIframe" frameborder="0"  onload=top.weiboCallback("'+id+'") src="' + url + '" width="100%;" height="99.8%;" >';			
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true
			});
		},
		/**
		 * 新建一个IFRameTab 带id和回调函数
		 * @param url
		 * @param title
		 * @param single
		 * @param id  iframe 对象id
		 * @param func  回调的函数
		 */
		newIFrameTabId : function (url,title,single,id,message,func){
			
			
			pf.openpageloading();
			if(single == undefined || single){
				if($('#jhtabs').tabs('exists',title)){
					$('#jhtabs').tabs('close',title);
				}
			}
			var frame = '<iframe id="'+id+'" class="tabIframe" frameborder="0"  onload=top.pf.callBackFunc('+func+',"'+message+'") src="' + url + '" width="100%;" height="99.8%;" >';			
			$('#jhtabs').tabs('add',{
			    title:title,
			    content:frame,
			    closable:true,
			    selected:true
			});

		},
		//回调
		callBackFunc:function(callFunction,result){
				pf.closepageloading();
				if (callFunction != undefined && typeof (callFunction)== 'function') {
					callFunction(result);
			    }
		},
		addFileInput : function (tableId){
			var fileInput =$("#"+tableId).find("#fileTr").clone();
			fileInput.attr("id","");
			fileInput.find("input[type='file']").val("");
			fileInput.children().last().html(
					 '<a href="javascript:void(0)" onclick="pf.deleteFileInput(this)"  class="easyui-linkbutton" >删除</a>'	
			);
			pf.parse(fileInput.children().last());
			$("#"+tableId).prepend(fileInput);
			pf.setFileInputName(tableId);
		},
		
		deleteFileInput : function (but){
			var fileTr = $(but).parent().parent();
				fileTr.remove();
				pf.setFileInputName();
		},
		
		setFileInputName : function (tableId){
			var seq=0;
			if($("#fileSeq_"+tableId).val()!=undefined&&$("#fileSeq_"+tableId).val()!=null&&$("#fileSeq_"+tableId).val()!=""){
				seq=$("#fileSeq_"+tableId).val()*1;
			};
			$("#"+tableId).find('tr').each(function(i,n){
			     $(n).find("input[type='hidden']").attr("name","fileVo["+(seq*1+i*1)+"].mainItemType");
			     $(n).find("input[type='file']").attr("name","fileVo["+(seq*1+i*1)+"].file");
			     $(n).find("select").attr("name","fileVo["+(seq*1+i*1)+"].type");
			     
		    });
		},
		
		deleteFile : function (attachmentId,attachmentName,mainitemType,but){
			var pageUrl = contextRoot+"/file/deleteFile.do?attachmentId=" + attachmentId+"&mainitemType="+mainitemType ;
			
			var msg = "确认要删除"+attachmentName;
			var fileTr = $(but).parent().parent();
			pf.confirm(msg, function(r){
				if(r){
					pf.ajaxGet(pageUrl, function(data){
						if(data){
							fileTr.remove();
						}
					});
				}
			});
		},
		
		/**
		 * 页面的提交，首先将获得HTML内容，
		 */
		ajaxSubmit : function(pageUrl, params, func) {
			pf.openpageloading();
			pageUrl = this._updateUrl(pageUrl);
			new Ajax.Request(pageUrl, pf.ajaxRequestOptions({
				method : 'post',
				parameters : params,
				onSuccess : function(transport) {
					pf.closepageloading();
					try {
						func(transport);
					} catch (e) {
						pf.log(e.name + ':' + e.message);
					}
				},
				onFailure : this._ajaxOnFailure
			}));
		},
		
		/**
		 * jquery ui 编译一个元素
		 * @param obj
		 */
		parse : function (obj){
			$.parser.parse(obj);
		},
		
		/**
		 * 把一个div变成 模态对话框
		 * @param div
		 */
		openWindow : function(div){
			$('#'+div).window('move',{				
			  left:($(document).scrollLeft()+400),
			  top:$(document).scrollTop()+80
			});
			$('#'+div).window('open');
		},
		/**把一个页面的内容放在Div中，并变成模态对话框弹出
		 * @param url
		 * @param div
		 * @param callFunction
		 */
		openUrlWindow : function (url, div, callFunction){
			pf.ajaxGet(url, function(data){
				
				$("#"+div).html(data);
				
				pf.parse($("#"+div));
				
				pf.openWindow(div);
				
				if (callFunction != undefined && typeof (callFunction) == "function") {
   					callFunction(data);
   				}
			});
		},
		/**
		 * 关闭一个模态对话框
		 * @param div
		 */
		closeWindow : function(div){
			$('#'+div).window('close');
		},
		
		/**
		 * 显示一个日期框
		 */
		WdatePicker : function(){
			WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd'});
		},
		/**
		 *显示一个有最大日期限制的日期框
		 * @param min
		 */
		WdatePickerMax : function (max,hasMin){
			if(hasMin != undefined && hasMin == true){
				WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',minDate:curDate,maxDate:"#F{$dp.$D($('#"+max+"')[0])}"});
			} else {
				WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',maxDate:"#F{$dp.$D($('#"+max+"')[0])}"});
			}
		},
		
		/**
		 * 显示一个有最小日期限制的日期框
		 * @param min
		 * @param compareDate
		 */
		WdatePickerMin : function (min,compareMin){
			if(compareMin != undefined && compareMin == true){
				if($('#'+min).val() == ''){
					WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',minDate:curDate});
				} else {
					var min1 = dateUtil.getDate(curDate,'yyyy-MM-dd').getTime();
					var min2 = dateUtil.getDate($.trim($('#'+min).val()),'yyyy-MM-dd').getTime();
					if(min1 > min2){
						WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',minDate:curDate});
					} else {
						WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',minDate:"#F{$dp.$D($('#"+min+"')[0])}"});
					}
				}
			} else {
				WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',minDate:"#F{$dp.$D($('#"+min+"')[0])}"});
			}
		},
		
		/**
		 * 去除字符串前后的空格
		 * @param str
		 * @returns
		 */
		trim : function (str){
			return str.replace(/(^\s*)|(\s*$)/g, "");
		},
		/**
		 * 通过code获取code对应编码信息，返回json对象
		 * @param code
		 * @returns
		 */
		getParMap : function(code){
		   var _result = {};
		   if(typeof(code) != 'undefined' && code != null && $.trim(code) != ''){
		        code = $.trim(code);
		   		$.ajax({
                  type: 'POST',
                  async:false,
                  dataType: 'json',
                  url: contextRoot+'/parmap/'+code+'.do',
                  data:{'date':new Date()},
                  success: function(rs){
                    _result = rs;
                  },
                  error: pf.ajaxError
                 });
		   }
		   return _result; 
		},
		/**
		 * 通过codeId获取codeName，返回字符串
		 * @param parMap
		 * @param codeId
		 * @returns
		 */
		getCodeName : function(parMap,codeId){
		  var _result = '';
		  if(typeof(parMap) != 'undefined' && parMap != null && codeId != null){
		      
		      if(typeof(parMap[codeId]) != 'undefined'){
		         _result = parMap[codeId];
		      }
		  }
		  return _result;
		},
		/**
		 * 通过parMap返回下拉options
		 * @param parMap
		 * @param is 是否包含默认请选择选项 非必须参数
		 * @param selected 选中选项的值   非必须参数
		 * @returns
		 */
	    getOptions : function(parMap,is,selected){
	      
	      var _options = ''; 
	      if(typeof(is) != 'undefined' && is){
	         if(selected && selected != null && selected == ''){
	             _options += '<option value="" selected="true">请选择</option>';
	         } else {
	             _options += '<option value="">请选择</option>';
	         }
	      }
	      if(typeof(parMap) != 'undefined' && parMap != null){
	         
	         for(var codeId in parMap){
	             if(selected && selected != null){
	                if(codeId == selected){
	                    _options += '<option value="'+codeId+'" selected="true">'+parMap[codeId]+'</option>';
	                } else {
	                    _options += '<option value="'+codeId+'">'+parMap[codeId]+'</option>';
	                }
	             } else {
	                    _options += '<option value="'+codeId+'">'+parMap[codeId]+'</option>';
	             }
	         }
	      }
	      return _options;
	    },
	    
	    /**
		 * 将数值四舍五入(保留N位小数)后格式化成金额形式
		 * @param amount 数值(Number或者String)
		 * @param n      保留位数(默认为0)
		 * @return       金额格式的字符串,如'1,234,567.45'
		 * @type         String
		 */
		formatAmount : function (s,n) {
		   if($.trim(s) == '' || isNaN(s)){
		     return '';
		   }
		   n = n > 0 && n <= 20 ? n : 2;
		   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		   var l = s.split(".")[0].split("").reverse(),
		   r = s.split(".")[1];
		   t = "";
		   for(i = 0; i < l.length; i ++ )
		   {
		      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		   }
		   return t.split("").reverse().join("") + "." + r;
		},

		/**
		 * 字符转数字 保留两位小数
		 * @param str
		 * @returns
		 */
		parseDouble : function (str){
			var res = parseFloat(str).toFixed(3);
			if(isNaN(res) || res == undefined ){
				res = 0;
			}
			return res;
		},
		showLoading : function(){
		      $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
              $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 280) / 2,top:($(window).height() - 260) / 2});
		},
		removeLoading : function(){
		     $(".datagrid-mask").remove(); 
    		 $(".datagrid-mask-msg").remove();
		},
		onlyInputNum : function(obj) {
		  if(isNaN(obj.value)){
		      obj.value = obj.value.replace(/[^\d\.]/g,'').replace(/,/g,'');
		      var objVal = obj.value;
		      if(objVal.split('.').length >= 3){
		         obj.value = objVal.substr(0,objVal.length-1);
		      } 
		  }
	    }
	    ,
		onlyInputInt : function(obj) {
		  if(isNaN(obj.value)){
	         obj.value = obj.value.replace(/[^\d]/g,'').replace(/,/g,'');
	      }
	    },
	    formatInputAmount : function(obj,n){
	    	obj.value = this.formatAmount(obj.value,n);
	    },
	    clearFormat : function(obj){
	    	obj.value = obj.value.replace(/,/g,'');
	    },
	    getFieldValue : function (row,fieldName){
	        return eval('row.'+fieldName);
	    },
	    getJqFieldObj : function (trItem,fieldName){
	       return $(trItem).find('td[field="'+fieldName+'"] div.datagrid-cell');
	    },
	    getDgTrObjs : function(dgId){
	    	return $('#'+dgId).siblings(':eq(1)').find('.datagrid-body tr');
	    },
	   //打开窗口
	   showWindow : function (divId,title, url, width, height, modal, minimizable, maximizable) {
	        divId = divId == null ? 'win01':divId;
	        $('#'+divId).html('');
	        url = (url.indexOf('?')!=-1)?(url+'&winId='+divId):('?winId='+winId);
	        $('#'+divId).window({
		         title: title,
		         width: width === undefined ? 600 : width,
		         height: height === undefined ? 400 : height,
		         content: '<iframe scrolling="yes" frameborder="0"  src="' + url + '" style="width:100%;height:98%;"></iframe>',
		         modal: modal === undefined ? true : modal,
		         minimizable: minimizable === undefined ? false : minimizable,
		         maximizable: maximizable === undefined ? false : maximizable,
		         shadow: false,
		         cache: false,
		         closed: false,
		         collapsible: false,
		         resizable: false,
		         loadingMessage: '正在加载数据，请稍等片刻......'
	        });
	        if($('#'+divId) !== undefined && $('#'+divId) !== null){
	           try{
			       $('#'+divId).window('move',{
					  left:(($(window).width()-$('#'+div).width()) * 0.5),
					  top:$(document).scrollTop()+20
					});
				} catch(e){
				}
			}
	   },
	    
	   completeTask : function (){
		   
		   parent.task.listTask();
		   parent.pf.closeNowTab();
	   },
	   pagerFilter : function (page){
		   var resultData = {total:0,rows:[]};
		   if(page != undefined && page != null
				   && page.pageDataList && page.pageDataList.length >0){
			   resultData = {
	               total: page.totalSize,
	               rows : page.pageDataList
		       };
		   } else {
		      if(page.total && page.rows){
		          resultData = page;
		      }
		   }
	       return resultData;
      },
      /**
       * 检查登录人是否有某个资源的操作权限
       * @param resId
       * @param callFunction
       */
      checkRes : function (resId){
		  if(resId == '' || resId == undefined){
			  return false;
		  }
    	  
	    	var _result = '';
	    	var pageUrl = contextRoot+"/login/checkRes.do";
	    	pageUrl = this._updateUrl(pageUrl);
			jQuery.ajax({
	            url: pageUrl,
	            async:false,
	            type: "post",
	            data: {"resId":resId},
	            success: function(data, textStatus) {
	            	_result = data;
	            },
	            error: pf.ajaxError
	        });
			return _result;
      }
      ,
      changTabName : function (tabName){
        parent.$('ul.tabs li.tabs-selected span.tabs-title').html(tabName);
      },
      showTips : function(jqObj,showText){
         $(jqObj).attr('title',showText);
      },
      setTips : function(componentId,showText){
        var component    = document.getElementById(componentId);
        var componentVal = '';
        if(component!= null){
           var componentType = component.type;
           if(componentType == 'text'){
              if(showText == undefined || showText == null){
                  showText = $('#'+componentId).val();
              }
              $('#'+componentId).attr('title',showText);
           } else if(componentType == 'select-one'){
              if(showText == undefined || showText == null){
                  showText = $('#'+componentId).find("option:selected").text();
              }
              $('#'+componentId).attr('title',showText);
           }
        }
     },
     indexOf : function (ary,item){
      if(ary != null){
          for(var i = 0;i < ary.length;i=i+1) {
		     if(ary[i] == item){
		        return true;
		     }
	      }
	   }
	   return false;
     },
    //限制textarea长度1000
    formatTextarea : function(inId,outId,strLong){
    	if($("#"+inId).val().length > 0){
    		$("#"+outId).text(strLong-$("#"+inId).val().length);
    	}else{
    		$("#"+outId).text(strLong);
    	}
		$('#'+inId).keydown(function(){
			var curLength=$("#"+inId).val().length;  
			if(curLength>=strLong){
				var num=$("#"+inId).val().substr(0,strLong-1); 
				$("#"+inId).val(num); 
				alert("超过字数限制，多出的字将被截断!" ); 
			}else{
				$("#"+outId).text(1000-$("#"+inId).val().length);
			}
		});		
	},
	appendParam : function(objId,name,val){
	   if($('#'+objId).find('input[name="'+name+'"]').length<=0){
	      $('#'+objId).append('<input type="hidden" name="'+name+'" value="'+val+'">');
	   } else {
	      $('#'+objId).find('input[name="'+name+'"]').val(val);
	   }
	},
	sorterText : function(a,b){
	   if (a < b) {
	      return -1;
	   } else if (a > b) {
	      return 1;
	   } 
	   return 0;
	},
	sorterDate : function(a,b){
	    if($.trim(a) == '' || $.trim(b) == ''){
	      return -1;
	    } else {
	      a = $.trim(a).replace(/\//g,'').replace(/-/g,'').replace(/:/g,'')*1;
	      b = $.trim(b).replace(/\//g,'').replace(/-/g,'').replace(/:/g,'')*1;
	      return a > b ? 1 : -1;
	    }  
	},
	/*
	 * js小数加法计算
	 */
	floatAccAdd : function(arg1,arg2){ 
		var r1,r2,m; 
		try{
			r1=arg1.toString().split(".")[1].length;
		}catch(e){
			r1=0;
		} 
		try{
			r2=arg2.toString().split(".")[1].length;
		}catch(e){
			r2=0;
		} 
		m=Math.pow(10,Math.max(r1,r2));
		return (arg1*m+arg2*m)/m;
	},
	/*
	 * js小数减法计算
	 */
	floatSubtr : function(arg1,arg2){ 
		var r1,r2,m,n;
	    try{
	    	r1=arg1.toString().split(".")[1].length;
	    }catch(e){
	    	r1=0;
	    }
	    try{
	    	r2=arg2.toString().split(".")[1].length;
	    }catch(e){
	    	r2=0;
	    }
	    m=Math.pow(10,Math.max(r1,r2));
	    n=(r1>=r2)?r1:r2;
	    return ((arg1*m-arg2*m)/m).toFixed(n);
	},
	
	/**
	 * 新建一个IFRameTab
	 * @param url
	 * @param title
	 * @param single
	 */
	newIFrameTabs : function (url,title,single){
		if(single == undefined || single){
			if(window.top.$('#jhtabs').tabs('exists',title)){
				window.top.$('#jhtabs').tabs('close',title);
			}
		}
		var frame = '<iframe class="tabIframe" frameborder="0" src="' + url + '" width="100%;" height="99.8%;" >';
		window.top.$('#jhtabs').tabs('add',{
		    title:title,
		    content:frame,
		    closable:true,
		    selected:true//,
		    //onBeforeDestroy:function(){
//		    	 var frame = $('iframe', this);  
//		         if (frame.length > 0) { 
////		        	 alert(frame[0].contentWindow.document.body.innerHTML);
//		        	 frame[0].src='about:blank';
//		             frame[0].contentWindow.document.write('');  
////		             alert(frame[0].contentWindow.document.body.innerHTML);
//		             frame[0].contentWindow.close();
//		             frame.remove();  
//		             if ($.browser.msie) {  
//		                 CollectGarbage();
//		             }
//		         }
//		    }
		});
	},
	
	/**
	 * 获取服务器时间
	 */
	getServerTime : function(connId,callFunction){
		 var servertime = "";
		 $.ajax({
               type: 'GET',
               async:false,
               //dataType: 'json',
               url: this._updateUrl(contextRoot+'/base/common!getServerTime.json'),
               data:{"connId":connId},
               success: function(data){
            	   servertime = data;
            	   if (callFunction != undefined && typeof (callFunction) == "function") {
      					callFunction(data);
      				}
               },
               error: pf.ajaxError
          });
//		  return servertime;
	}
	
	
};



String.prototype.clearFormat = function(){
	return this.replace(/,/g,'');
}
String.prototype.trim = function(){
   return this.replace(/(^\s*)|(\s*$)/g,'');
}
$.extend($.fn.validatebox.defaults.rules,{
    select : {//验证下拉选项不能为空
        validator : function(value) { 
            if(value === '' || value == '-1'){
				return false;
			}
			return true;
        }, 
        message : '该选择项为必选项' 
    }
});