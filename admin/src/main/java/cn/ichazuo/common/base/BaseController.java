package cn.ichazuo.common.base;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.StringUtils;

/**
 * @ClassName: BaseController
 * @Description: (基本Controller)
 * @author ZhaoXu
 * @date 2015年7月31日 下午8:59:54
 * @version V1.0
 */
public class BaseController extends Controller {

	/**
	 * @Title: ok
	 * @Description: (返回成功)
	 * @return
	 */
	protected JSONObject ok() {
		JSONObject obj = new JSONObject();
		obj.put("status", Result.SUCCESS_STATUS);
		return obj;
	}

	/**
	 * @Title: ok
	 * @Description: (返回成功信息)
	 * @param msg
	 *            消息
	 * @return
	 */
	protected JSONObject ok(String msg) {
		JSONObject obj = ok();
		obj.put("msg", msg);
		obj.put("status", Result.SUCCESS_STATUS);
		return obj;
	}

	/**
	 * @Title: ok
	 * @Description: (返回成功)
	 * @param msg
	 * @param data
	 * @return
	 */
	protected JSONObject ok(String msg, Object data) {
		JSONObject obj = ok();
		obj.put("data", data);
		return obj;
	}

	protected JSONObject status(int status, Object data) {
		JSONObject obj = new JSONObject();
		obj.put("status", status);
		obj.put("data", data);
		return obj;
	}

	/**
	 * @Title: error
	 * @Description: (返回错误信息)
	 * @param msg
	 *            消息
	 * @return
	 */
	protected JSONObject error(String msg) {
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		obj.put("status", Result.ERROR_STATUS);
		return obj;
	}

	/**
	 * @Title: appenUrl
	 * @Description: (拼接URL)
	 * @param path
	 * @return
	 */
	protected String appenUrl(String path) {
		if (StringUtils.isNullOrEmpty(path)) {
			return "";
		}
		if (path.trim().startsWith("http:") || path.trim().startsWith("https:")) {
			return path;
		}

		String url = PropKit.get("project.image.url");
		if (url.endsWith("/") && path.startsWith("/")) {
			return url + path.substring(1);
		}
		if (!url.endsWith("/") && !path.startsWith("/")) {
			return url + "/" + path;
		}
		return url + path;
	}
	protected double addDouble(double a,double b){
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(b));
        double result = (bigDecimal1.add(bigDecimal2)).doubleValue();
        return result;
    }
}
