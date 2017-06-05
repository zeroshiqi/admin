package cn.ichazuo.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.model.OperaLog;
import cn.ichazuo.model.UserLog;

/**
 * @ClassName: LogService 
 * @Description: (日志Service) 
 * @author ZhaoXu
 * @date 2015年8月5日 下午4:32:28 
 * @version V1.0
 */
public class LogService {
	
	/**
	 * @Title: saveLog 
	 * @Description: (保存操作日志) 
	 * @param userId 操作人
	 * @param type 类别 0:线上课程  1:线下课程   2:文章  3:用户
	 * @param operateId 目标ID
	 * @param operate 操作内容
	 */
	@Before(Tx.class)
	public void saveLog(LoginUser user,Integer type,Long operateId,String operate){
		OperaLog log = new OperaLog();
		log.set("user_id", user.getId());
		log.set("type", type);
		log.set("operate_id", operateId);
		log.set("operate", operate);
		log.set("create_at", DateUtils.getNowDate());
		log.save();
	}
	
	/**
	 * @Title: saveRequestLog 
	 * @Description: (保存请求日志) 
	 * @param user
	 * @param ip
	 * @param uri
	 * @param description
	 */
	public void saveRequestLog(LoginUser user,String ip,String uri,String description){
		UserLog log = new UserLog();
		log.set("ip_address", ip);
		log.set("uri", uri);
		log.set("user_id", user.getId());
		log.set("account", user.getAccount());
		log.set("user_name", user.getRealName());
		log.set("description", description);
		log.set("create_at", DateUtils.getNowDate());
		
		log.save();
	}
}
