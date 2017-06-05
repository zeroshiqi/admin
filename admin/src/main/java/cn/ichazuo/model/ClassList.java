package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: Member
 * @Description: (映射会员表)
 * @author ZhaoXu
 * @date 2015年8月1日 下午1:20:21
 * @version V1.0
 */
public class ClassList extends Model<ClassList> {
	private static final long serialVersionUID = 1L;
	// DAO
	public static final ClassList dao = new ClassList();
}
