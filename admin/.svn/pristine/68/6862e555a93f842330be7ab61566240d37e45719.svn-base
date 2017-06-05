package cn.ichazuo.controller;

import com.jfinal.aop.Clear;
import com.jfinal.ext.render.CaptchaRender;

import cn.ichazuo.common.base.BaseController;

/**
 * @ClassName: IndexController 
 * @Description: (indexController) 
 * @author ZhaoXu
 * @date 2015年7月31日 下午9:00:31 
 * @version V1.0
 */
public class IndexController extends BaseController{
	
	@Clear
	public void index(){
		render("/index.jsp");		//跳转首页
	}
	
	@Clear
	public void captcha(){
		//生成验证码
		render(new CaptchaRender("myCaptcha"));
	}
	
}
