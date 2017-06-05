package cn.ichazuo.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * @ClassName: ServletExcludeHadler 
 * @Description: (Servlet处理器) 
 * @author ZhaoXu
 * @date 2015年8月3日 下午3:29:19 
 * @version V1.0
 */
public class ServletExcludeHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		nextHandler.handle(target, request, response, isHandled);

	}

}
