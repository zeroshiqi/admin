package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: Course 
 * @Description: (映射课程表) 
 * @author ZhaoXu
 * @date 2015年8月2日 下午3:05:46 
 * @version V1.0
 */
public class Spread extends Model<Spread>{
	private static final long serialVersionUID = 1L;
	//DAO
	public static final Spread dao = new Spread();
}
