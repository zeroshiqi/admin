package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: User
 * @Description: (映射用户表)
 * @author ZhaoXu
 * @date 2015年7月31日 下午5:58:41
 * @version V1.0
 */
public class User extends Model<User> {
	private static final long serialVersionUID = 1L;
	
	//DAO
	public static final User dao = new User();
}
