package cn.ichazuo.common.utils;

import com.jfinal.kit.PropKit;

import cn.ichazuo.common.utils.sms.HttpSend;
import cn.ichazuo.common.utils.sms.MD5;

public class MobileUtils {
	/**
	 * @Title: send
	 * @Description: (发送短信)
	 * @param mobile
	 * @param content
	 * @throws Exception 
	 */
	public static void send(String mobile, String content) throws Exception {
		String username = PropKit.get("sms.duanxinbao.username");// 短信宝帐户名
		String pass = new MD5().Md5(PropKit.get("sms.duanxinbao.password"));// 短信宝帐户密码，md5加密后的字符串
		String phone = mobile;// 电话号码
		HttpSend send = new HttpSend(
				"http://www.smsbao.com/sms?u=" + username + "&p=" + pass + "&m=" + phone + "&c=" + content);
		send.send();
	}
	
	public static void sendPost(String mobile, String content) throws Exception {
		String username = PropKit.get("sms.duanxinbao.username");// 短信宝帐户名
		String pass = new MD5().Md5(PropKit.get("sms.duanxinbao.password"));// 短信宝帐户密码，md5加密后的字符串
		String phone = mobile;// 电话号码
		HttpSend send = new HttpSend(
				"http://www.smsbao.com/sms?u=" + username + "&p=" + pass + "&m=" + phone);
		send.sendPost(content);
	}
}
