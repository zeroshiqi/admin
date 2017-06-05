package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: OperaLog 
 * @Description: (操作日志) 
 * @author ZhaoXu
 * @date 2015年8月5日 下午4:08:06 
 * @version V1.0
 */
public class OperaLog extends Model<OperaLog>{
	private static final long serialVersionUID = 1L;
	//DAO
	public static OperaLog dao = new OperaLog();

}
