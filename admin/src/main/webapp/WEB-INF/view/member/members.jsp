<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>插坐学院-后台管理</title>
<%@include file="/base/link.jsp"%>
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
				<li class="bgColor"><a href="/admin/member">用户管理</a></li>
				<li><a href="/admin/push">推送管理</a></li>
				<li><a href="/admin/image">图片列表</a></li>
				<li><a href="/admin/info">短信列表</a></li>
				<li><a href="/admin/feedback">意见反馈</a></li>
				<li><a href="/admin/invoice">发票列表</a></li>
			</ul>
		</div>

		<!-- content start -->
		<div class="admin-content">

			<%--<div class="am-cf am-padding">--%>
				<%--<div class="am-fl am-cf">--%>
					<%--<strong class="am-text-primary am-text-lg">用户列表</strong>--%>
				<%--</div>--%>
			<%--</div>--%>



			<div class="am-u-md-3 am-cf">

				<form method="post" id="xlsForm" action="/admin/member/saveMembers" enctype="multipart/form-data">
					<button type="button" onclick="edit(0)" class="add">
						<span class="icon-plus"></span> 新增
					</button>
					<button type="button" id="importBtn" onclick="$('#xls').click()" class="add">
						<span class="icon-plus"></span> 导入
					</button>
					<input type="file" name="XLSFile" id="xls" style="display: none;" onchange="uploadXLS()" />
				</form>
				<div class="search">
					<%--<div class="am-input-group am-input-group-sm" style="width:190%">--%>
						<input type="text" placeholder="手机号" value="${mobile}" class="am-form-field" id="searchMobile" name="mobile">
						<input type="text" placeholder="工作单位" value="${work}" class="am-form-field" id="searchWork" name="work">
						<input type="text" placeholder="姓名" value="${nickName}" class="am-form-field" id="searchNickName" name="nickName">
						<input type="text" placeholder="微信号" value="${weixin}" class="am-form-field" id="searchWeixin" name="weixin">

						<select name="sex" id="searchSex" class="am-form-field" >
							<option value="-1" <c:if test="${sex eq '-1'}">selected="selected"</c:if>>请选择性别</option>
							<option value="1" <c:if test="${sex eq '1'}">selected="selected"</c:if>>男</option>
							<option value="0" <c:if test="${sex eq '0'}">selected="selected"</c:if>>女</option>
						</select>
						<%-- <select name="study" id="searchStudy" class="am-form-field"  style="width:17%" >
							<option value="-1" <c:if test="${study eq '-1'}">selected="selected"</c:if>>请选择是否跨界学习</option>
							<option value="1" <c:if test="${study eq '1'}">selected="selected"</c:if>>是跨界学习</option>
							<option value="0" <c:if test="${study eq '0'}">selected="selected"</c:if>>不是跨界学习</option>
						</select>
						<select name="pay" id="searchPay" class="am-form-field"  style="width:15%" >
							<option value="-1" <c:if test="${pay eq '-1'}">selected="selected"</c:if>>请选择付费方</option>
							<option value="1" <c:if test="${pay eq '1'}">selected="selected"</c:if>>企业</option>
							<option value="0" <c:if test="${pay eq '0'}">selected="selected"</c:if>>个人</option>
						</select>
						<select name="oldStu" id="searchOldStu" class="am-form-field"  style="width:20%" >
							<option value="-1" <c:if test="${oldStu eq '-1'}">selected="selected"</c:if>>请选择是否有老学员推荐</option>
							<option value="1" <c:if test="${oldStu eq '1'}">selected="selected"</c:if>>有老学员推荐</option>
							<option value="0" <c:if test="${oldStu eq '0'}">selected="selected"</c:if>>没有老学员推荐</option>
						</select>
						<select name="gongzhong" id="searchGongzhong" class="am-form-field"  style="width:20%" >
							<option value="-1" <c:if test="${gongzhong eq '-1'}">selected="selected"</c:if>>请选择是否运营了公众号</option>
							<option value="1" <c:if test="${gongzhong eq '1'}">selected="selected"</c:if>>运营了</option>
							<option value="0" <c:if test="${gongzhong eq '0'}">selected="selected"</c:if>>没有运营</option>
						</select> --%>
						<div class="Go">
							<button class="icon-search" onclick="submitForm()" type="button" style="bottom:-20px"></button>
						</div>
					<%--</div>--%>
				</div>
			</div>
			<div class="am-u-sm-1"></div>

			<div class="">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">ID</th>
									<th class="table-title">手机号</th>
									<th class="table-type">昵称</th>
									<th class="table-type">性别</th>
									<th class="table-type"><center>公司</center></th>
									<!-- <th class="table-type">认可TA</th>
									<th class="table-type">TA认可</th> -->
									<th class="table-type">微信号</th>
									<th class="table-type">登录次数</th>
									<th class="table-type">注册时间</th>
									<%--<th class="table-set">操作</th>--%>
								</tr>
							</thead>
							<c:forEach var="obj" items="${list}">
							<tbody>
								<tr>
									<td>${obj.memberId}</td>
									<td>${obj.mobile}</td>
									<td><a href="/admin/member/info?id=${obj.memberId}">${obj.nick_name}</a></td>
									<td>${obj.gender}</td>
									<td><center>${obj.company_name}</center></td>
									<%-- <td><a href="/admin/member/favour?memberId=${obj.memberId}&type=1">${obj.toCount}</a></td>
									<td><a href="/admin/member/favour?memberId=${obj.memberId}&type=0">${obj.fromCount}</a></td> --%>
									<td>${obj.weixin}</td>
									<td>${obj.login_number}</td>
									<td>${fn:substring(obj.create_at,0,19)}</td>
								</tr>
								<tr>
									<td colspan=8>
									<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
									<%-- <a class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="edit('${obj.memberId}');">
										<span class="am-icon-pencil-square-o" ></span> 编辑
									</a> --%>
									<a onclick="deleteMember('${obj.memberId}')" class="glyphicon glyphicon-trash" >
									<span class="am-icon-trash-o"></span>
									</a>
									</div>
									</div>
									</td>
								</tr>
							</tbody>
							</c:forEach>
						</table>
						<div class="tfoot">
							共 ${page.count } 条记录
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
		<input type="hidden" value="0" id="judge">
		<!-- content end -->
	</div>
</body>
<script type="text/javascript">
	function submitForm(){
		window.location.href="/admin/member?work="+$("#searchWork").val()+"&weixin="+$("#searchWeixin").val()+"&mobile="+$("#searchMobile").val()+"&nickName="+$("#searchNickName").val()+"&sex="+$("#searchSex").val();
	}
	function edit(id){
		if(id == 0){
			window.location.href="/admin/member/edit";
		}else{
			window.location.href="/admin/member/edit?id="+id;
		}
	}
	function uploadXLS(){
		showConfirm("确认上传?",function(){
			$("#importBtn").attr("disabled","disabled");
			if($("#judge").val()== 0){
				$("#judge").val('1');
			}else{
				alert("请勿重复提交！");
				return;
			}
			
			$('#xlsForm').ajaxSubmit({
				type:'post',
				url:"/admin/member/saveMembers",
			    success:function(data){
			        if(data.status == 200){
			        	showConfirm(data.msg,function(){
			        		window.location.href = "/admin/member";
			        		$("#importBtn").removeAttr("disabled");
			        	});
			        }else{
			            showInfo(data.msg,function(){},3000);
			            $("#importBtn").removeAttr("disabled");
			        }
			    }
			});
// 			$("#importBtn").removeAttr("disabled");
			
			/* $('#xlsForm').ajaxSubmit({
				type:'post',
				url:"/admin/member/saveMembers",
			    success:function(data){
			        if(data.status == 200){
			           	window.location.href = "/admin/member";
			        }else{
			        	$("#xls").removeAttr('disabled');
			            showInfo(data.msg,function(){},3000);
			        }
			    }
			}); */
			//$('#xlsForm').submit();
		});
	}
	function deleteMember(id){
		showConfirm("确认删除?",function(){
			$.getJSON("/admin/member/delete?id=" + id, function(result) {
				if (result.status == 200) {
					location.reload();
				} else {
					showInfo("系统错误....",function(){
						//..
					},3000);
				}
			});
		});
	}
</script>
</html>