package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: Article 
 * @Description: (映射文章表) 
 * @author ZhaoXu
 * @date 2015年8月4日 下午6:46:34 
 * @version V1.0
 */
public class SelfStudy extends Model<SelfStudy>{
	private static final long serialVersionUID = 1L;
	//DAO
	public static final SelfStudy dao = new SelfStudy();
	
}
