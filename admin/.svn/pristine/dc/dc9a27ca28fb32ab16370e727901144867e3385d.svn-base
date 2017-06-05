package cn.ichazuo.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.service.LogService;

/**
 * @ClassName: ActionInterceptor 
 * @Description: (控制层拦截器) 
 * @author ZhaoXu
 * @date 2015年8月1日 上午10:57:29 
 * @version V1.0
 */
public class ActionInterceptor implements Interceptor{
	private LogService logService = Duang.duang(LogService.class);
	
	private static final Map<String,String> urls = new HashMap<>();
	static {
		urls.put("/admin/member", "访问用户列表");
		urls.put("/admin/course/offline", "访问线下课程列表");
		urls.put("/admin/course/online", "访问线上课程列表");
		urls.put("/admin/article", "访问文章列表");
		urls.put("/admin/course/uploadFile", "访问上传课件");
	}
	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Object obj = controller.getSessionAttr(PropKit.get("project.session"));
		if(obj == null){
			controller.redirect("/index");
			return;
		}
		String uri =  controller.getRequest().getRequestURI();
		if(urls.containsKey(uri)){
			logService.saveRequestLog((LoginUser)obj, controller.getRequest().getRemoteAddr(), uri, urls.get(uri));
		}
		
		inv.invoke();
	}

}
