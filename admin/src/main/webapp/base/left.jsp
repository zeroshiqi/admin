<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>


<!-- 横向导航 -->
<div class="nav-assist"></div>

<div navber>
	<ul id="nav" class="nav">
		<i class="packUp icon-reorder"></i>
		<li>
			<a href='###' class="glyphicon glyphicon-book">课程管理</a>
			<ul class="navAuto">
				<li><a href="/admin/course/offline" >线下课程管理</a></li>
				<li><a href="/admin/course/onlineNew" >线上课程管理</a></li>
				<li><a href="/admin/course/online" >录音课程管理</a></li>
				<li><a href="/admin/course/living" >直播课程管理</a></li>
				<li><a href="/admin/course/publicClass">公开课管理</a></li>
<!-- 				<li><a href="/admin/course/bookSale" >新书预售</a></li> -->
				<li><a href="/admin/online">录音课程分类管理</a></li>
				<li><a href="/admin/course/spread">推广信息管理</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-shopping-cart">订单管理</a>
			<ul class="navAuto">
				<li><a href="/admin/order/online" >录音课程订单</a></li>
				<li><a href="/admin/order" >Web线下课程订单</a></li>
				<li><a href="/admin/order/onlineNew" >Web线上课程订单</a></li>
				<li><a href="/admin/crowdfunding/crowd" >众筹列表</a></li>
<!-- 				<li><a href="/admin/ordercount" >订单统计</a></li> -->
				<li><a href="/admin/complex" >复购率报表</a></li>
<!-- 				<li><a href="/admin/paynew" >新增付费用户趋势报表</a></li> -->
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-shopping-cart">APP数据报表</a>
			<ul class="navAuto">
				<li><a href="/admin/paynew" >新增付费用户趋势报表</a></li>
				<li><a href="/admin/register" >注册用户数日报表</a></li>
				<li><a href="/admin/register/active" >App用户日活</a></li>
				<li><a href="/admin/register/daily" >线上订单日付费用户</a></li>
				<li><a href="/admin/register/purchaseRate" >线上课程订单复购率</a></li>
<!-- 				<li><a href="/admin/register/awaken" >App唤醒报表</a></li> -->
				<li><a href="/admin/register/yesSale" >销售数据报表</a></li>
<!-- 				<li><a href="/admin/crowdfunding/crowd" >众筹列表</a></li> -->
<!-- <!-- 				<li><a href="/admin/ordercount" >订单统计</a></li> -->
<!-- 				<li><a href="/admin/complex" >复购率报表</a></li> -->
<!-- 				<li><a href="/admin/paynew" >新增付费用户趋势报表</a></li> -->
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-book">新书预售</a>
			<ul class="navAuto">
				<li><a href="/admin/course/bookSale" >新书预售</a></li>
				<li><a href="/admin/order/bookOrder">书籍订单</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-th">好多课App模块</a>
			<ul class="navAuto">
				<li><a href="/admin/company" >企业管理</a></li>
				<li><a href="/admin/keywords" >搜索关键字管理</a></li>
				<li><a href="/admin/catalog?type=0" >课程系列课一级分类管理</a></li>
				<li><a href="/admin/catalog?type=1" >讲师系列课一级分类管理</a></li>
				<li><a href="/admin/catalogtwo" >课程二级分类管理</a></li>
				<li><a href="/admin/coursetype" >课程一级目录</a></li>
				<li><a href="/admin/coursetype/typetwo" >课程二级目录</a></li>
				<li><a href="/admin/appcomment" >企业课程评论管理</a></li>
				<li><a href="/admin/appticket" >好多课课程试卷成绩列表</a></li>
				<li><a href="/admin/banner">好多课首页banner列表</a></li>
				<li><a href="/admin/company/power" >会员权限管理</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-th-list">页面模块</a>
			<ul class="navAuto">
				<li><a href="/admin/member" >用户管理</a></li>
				<li><a href="/admin/push" >推送管理</a></li>
				<li><a href="/admin/image" >图片列表</a></li>
				<li><a href="/admin/info" >短信列表</a></li>
				<li><a href="/admin/feedback" >意见反馈</a></li>
				<li><a href="/admin/invoice" >发票列表</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-align-right">文章模块</a>
			<ul class="navAuto">
				<li><a href="/admin/article" >文章管理(APP)</a></li>
				<li><a href="/admin/article?type=1" >文章管理(网站)</a></li>
				<li><a href="/admin/type" >文章类型列表</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-eye-open">招聘模块</a>
			<ul class="navAuto">
				<li><a href="/admin/job/jobtype" >招聘类别列表</a></li>
				<li><a href="/admin/job" >招聘信息列表</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-user">老师模块</a>
			<ul class="navAuto">
				<li><a href="/admin/teacher" >老师列表</a></li>
				<li><a href="/admin/teacher/invite" >邀请列表</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-calendar">试题模块</a>
			<ul class="navAuto">
				<li><a href="/admin/ticket" >成绩列表</a></li>
				<li><a href="/admin/question" >问题列表</a></li>
				<li><a href="/admin/typeone" >问题一级目录列表</a></li>
				<li><a href="/admin/typetwo" >问题二级目录列表</a></li>
			</ul>
		</li>
		<li>
			<a href='###' class="glyphicon glyphicon-eye-open">网站管理</a>
			<ul class="navAuto">
				<li><a href="/admin/selfstudy" >自学列表</a></li>
<!-- 				<li><a href="/admin/article?type=2" >招聘信息列表</a></li> -->
				<li><a href="/admin/student" >同学录</a></li>
			</ul>
		</li>


	</ul>
	<!--<div class="bulletin">-->
	<!--公告-->
	<!--</div>-->
</div>
