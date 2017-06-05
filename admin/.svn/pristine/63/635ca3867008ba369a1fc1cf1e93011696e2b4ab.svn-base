package cn.ichazuo.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import cn.ichazuo.common.interceptor.ActionInterceptor;
import cn.ichazuo.common.route.AdminRoute;
import cn.ichazuo.model.Article;
import cn.ichazuo.model.ArticleComment;
import cn.ichazuo.model.Course;
import cn.ichazuo.model.CourseOfflineComment;
import cn.ichazuo.model.DictItem;
import cn.ichazuo.model.Member;
import cn.ichazuo.model.MemberInfo;
import cn.ichazuo.model.OfflineCourse;
import cn.ichazuo.model.OfflineCourseImage;
import cn.ichazuo.model.OnlineCourse;
import cn.ichazuo.model.OperaLog;
import cn.ichazuo.model.PlayAddress;
import cn.ichazuo.model.PushLog;
import cn.ichazuo.model.User;
import cn.ichazuo.model.UserLog;
import cn.ichazuo.model.WebOrder;

/**
 * @ClassName: ServerConfig
 * @Description: (系统配置)
 * @author ZhaoXu
 * @date 2015年7月31日 下午7:45:55
 * @version V1.0
 */
public class ServerConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setMaxPostSize(100 * Const.DEFAULT_MAX_POST_SIZE);
		//加载配置文件
		PropKit.use("configInfo.properties");
		//设置开发模式
		me.setDevMode(PropKit.getBoolean("project.dev", true));
		//设置页面路径
		me.setBaseViewPath("/WEB-INF/view");
		//设置页面类型
		me.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes me) {
		//加载路由
		me.add(new AdminRoute());
	}

	@Override
	public void configPlugin(Plugins me) {
		//添加C3p0插件
		C3p0Plugin cp = new C3p0Plugin(PropKit.get("mysql.url"), PropKit.get("mysql.username"), PropKit.get("mysql.password"));
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		
		//数据库Model映射
		arp.addMapping("s_user", User.class);
		arp.addMapping("t_member", Member.class);
		arp.addMapping("t_member_info", MemberInfo.class);
		arp.addMapping("s_dict_item", DictItem.class);
		arp.addMapping("t_course", Course.class);
		arp.addMapping("t_course_offline", OfflineCourse.class);
		arp.addMapping("t_course_online", OnlineCourse.class);
		arp.addMapping("t_play_address", PlayAddress.class);
		arp.addMapping("t_course_offline_image", OfflineCourseImage.class);
		arp.addMapping("t_article", Article.class);
		arp.addMapping("l_opera_log", OperaLog.class);
		arp.addMapping("l_user_log", UserLog.class);
		arp.addMapping("t_course_offline_comment", CourseOfflineComment.class);
		arp.addMapping("t_article_comment", ArticleComment.class);
		arp.addMapping("l_push_log", PushLog.class);
		arp.addMapping("t_course_web_order", WebOrder.class);
	}

	@Override
	public void configHandler(Handlers me) {

	}

	@Override
	public void configInterceptor(Interceptors me) {
		//控制层拦截器
		me.addGlobalActionInterceptor(new ActionInterceptor());
	}
}
