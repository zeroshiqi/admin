package cn.ichazuo.service;

import java.util.List;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;

import cn.ichazuo.common.Result;
import cn.ichazuo.model.PushLog;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * @ClassName: PushService 
 * @Description: (推送Service) 
 * @author ZhaoXu
 * @date 2015年8月19日 下午7:45:45 
 * @version V1.0
 */
public class PushService {
	
	/**
	 * @Title: findPushLog 
	 * @Description: (查询推送日志列表) 
	 * @param page
	 * @return
	 */
	public List<PushLog> findPushLog(int page){
		return PushLog.dao.paginate(page, Result.PAGE_COUNT, "select * ", "from l_push_log order by id desc").getList();
	}
	
	/**
	 * @Title: findPushLogCount 
	 * @Description: (查询推送日志总数) 
	 * @return
	 */
	public Long findPushLogCount(){
		return Db.queryLong("select count(*) from l_push_log");
	}
	
	/**
	 * @Title: savePushLog 
	 * @Description: (保存推送日志) 
	 * @param log
	 * @return
	 * @throws Exception
	 */
	public boolean savePushLog(PushLog log) throws Exception{
		if(log.save()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: pushIOSMessage 
	 * @Description: (推送IOS信息) 
	 * @param client 客户端
	 * @param alert 提示信息
	 * @param type 类别  0:课程信息 1:推送其他消息
	 * @param value 值
	 */
	public void pushIOSMessage(ClientEnum client, String alert, Integer type,Long value) {
		cn.jpush.api.push.model.notification.IosNotification.Builder build = null;
		if(type == 0){
			//推送课程详情
			build = IosNotification.newBuilder().setAlert(alert).setBadge(0)
					.setSound("happy").addExtra("which", "courseDetail").addExtra("courseId", value);
		}else if(type == 1){
			//推送其他信息(打开APP即可)
			build = IosNotification.newBuilder().setAlert(alert).setBadge(0).setSound("happy").addExtra("which", "notification");
		}
		
		JPushClient jpushClient = getPushClient(client);
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.all()).setNotification(Notification.newBuilder()
				.addPlatformNotification(build.build())
						.build()).setMessage(Message.content("")).setOptions(Options.newBuilder().setApnsProduction(!PropKit.getBoolean("project.dev", false)).build()).build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title: getPushClient 
	 * @Description: (选择推送APP) 
	 * @param type
	 * @return
	 */
	private JPushClient getPushClient(ClientEnum type) {
		if (type == ClientEnum.ChaZuo) {
			return new JPushClient(PropKit.get("jpush.ichazuo.secret"), PropKit.get("jpush.ichazuo.appkey"), 3);
		} else {
			return new JPushClient(PropKit.get("jpush.gugu.secret"), PropKit.get("jpush.gugu.appkey"), 3);
		}
	}

	// 客户端枚举
	public enum ClientEnum {
		ChaZuo, GuGu
	}
}
