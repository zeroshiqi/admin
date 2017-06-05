package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: PlayAddress 
 * @Description: (映射播放地址) 
 * @author ZhaoXu
 * @date 2015年8月2日 下午10:27:46 
 * @version V1.0
 */
public class PlayAddress extends Model<PlayAddress>{
	private static final long serialVersionUID = 1L;
	
	//DAO
	public static final PlayAddress dao = new PlayAddress();
}
