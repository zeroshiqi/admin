/**
 * Created by Administrator on 2016/7/25.
 */

$(function() {
    // 导航hover效果
    $("#nav > li").hover(function(){
        maxH($(this).children(".navAuto"),$(this))
        $(this).children(".navAuto").show();
    },function(){
        $(this).children(".navAuto").hide();
    })
    $(".navAuto").hover(function(){
        $(this).show();
    },function(){
        $(this).hide();
    })

    // 如果高度大于窗口高度添加滚动条
    function maxH(index,max){
        var maxH = $(window).height()-($(max).offset().top - $(window).scrollTop());
        if($(index).height() > maxH){
            $(index).css({
                "height" : maxH+"px",
                "overflow-y" : "scroll"
            })
        };
    }

    //导航栏 点击、hover 切换背景颜色
    $(".navAuto > li").hover(function(){
        $(this).parent().siblings("a").addClass("navBgColor").siblings().removeClass("navBgColor")
    },function(){
        $(this).parent().siblings("a").removeClass("navBgColor").siblings().removeClass("navBgColor")
    })

    $(".navAuto > li").click(function(){
        $(this).parent().parent().addClass("navBgColor").siblings().removeClass("navBgColor")
    })


    // 辅助导航栏点击
    $(".nav-assist li").click(function(){
        $(this).addClass("baColor").siblings().removeClass("baColor");
    })


    // 解决左边导航栏字体冲突问题
    $(".nav li > a").wrapInner("<span></span>").children("span").css({"font-family":"微软雅黑"});
    $(".nav-assist li > a").wrapInner("<span></span>").children("span").addClass("icon-bookmark-empty");
    $(".nav-assist li > a > span").wrapInner("<span></span>").children("span").addClass("font");
    $(".icon-coffee").wrapInner("<span></span>").children("span").addClass("font");

    // 左边导航栏折叠/打开切换

    (function(){
        if(!localStorage.getItem("tog")){
            localStorage.setItem('tog','1');
        }

        var _i = localStorage.getItem("tog");
        if (_i == 0) {
            $("[navber]").css({'width':'60'});
            $(".nav li > a").height(50).children("span").hide();
            $(".navAuto li > a > span").show();
            $(".navAuto").css({'left' : '60px'});
            $(".admin-content").css({"padding":"105px 0 0 60px"});
            $(".nav-assist").css({"paddingLeft":"78px"});
        }else if(_i == 1){
            $("[navber]").css({'width':'175'});
            $(".nav li > a > span").show();
            $(".navAuto").css({'left' : '175px'});
            $(".admin-content").css({"padding":"105px 0 0 175px"});
            $(".nav-assist").css({"paddingLeft":"193px"})
        }
    })();

    $(".packUp").click(function(){

        var _i = localStorage.getItem("tog");

        if (_i == 1) {
            localStorage.setItem('tog','0');

            $("[navber]").animate({'width':'60'},300);
            $(".nav li > a").height(50).children("span").hide();
            $(".navAuto li > a > span").show();
            $(".navAuto").css({'left' : '60px'});
            $(".admin-content").css({"padding":"105px 0 0 60px"})
            $(".nav-assist").css({"paddingLeft":"78px"});
        }else {
            localStorage.setItem('tog','1');

            $("[navber]").animate({'width':'175'},300);
            $(".nav li > a > span").show();
            $(".navAuto").css({'left' : '175px'});
            $(".admin-content").css({"padding":"105px 0 0 175px"});
            $(".nav-assist").css({"paddingLeft":"193px"})
        }

    });


    //表格隔行变色
    function toggleColor(){
        var TbRow = document.getElementsByTagName("tbody");
        if (TbRow != null)
        {
            for (var i=0;i<TbRow.length ;i++ )
            {
                if (i%2==0)
                {
                    TbRow[i].style.backgroundColor="#fff";
                }
                else
                {
                    TbRow[i].style.backgroundColor="#E8EDFF";
                }
            }
        }
    }toggleColor();



//    function addCookie(name,value)
//    {
//        var Days = 60;
//        var exp = new Date();
//        exp.setDate(exp.getDate()+Days);
//        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
//    }
////获取Cookie的值
//    function getCookie(name)
//    {
//        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
//
//        if(arr=document.cookie.match(reg))
//
//            return unescape(arr[2]);
//        else
//            return null;
//    }



});

//页码跳转
function tiaoZhuan(index){
    var num = $('#skip').val();
    $('#skip_to').attr('href',index+'&page='+num);
}