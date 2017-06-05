<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>

<!-- 登录 -->
<div topber>
	<div class="header"><img class="logo" src="/admin/images/logo-white.png" alt="插坐学院"></div>
    <%--<ul>--%>
        <%--<li class="dropdown">登录 <i></i></li>--%>
        <%--<!--<li class="admin-fullscreen">开启全屏</li>-->--%>
    <%--</ul>--%>
    <%--<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>--%>

    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
    <li class="am-dropdown" data-am-dropdown>
    <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
    <span class="am-icon-users"></span> <span class=" icon-coffee">${userName }</span> <span class="am-icon-caret-down"></span>
    </a>
    <ul class="am-dropdown-content">
    <li><a href="#"><span class="am-icon-power-off"></span>退出</a></li>
    </ul>
    </li>
    <%--<li><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>--%>
    </ul>
    </div>
</div>

<!-- 横向导航 -->
<%--<div class="nav-assist"></div>--%>