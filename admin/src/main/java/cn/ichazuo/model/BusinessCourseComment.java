package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: CourseOfflineComment 
 * @Description: (映射线下课程评论表) 
 * @author ZhaoXu
 * @date 2015年8月7日 上午10:25:37 
 * @version V1.0
 */
public class BusinessCourseComment extends Model<BusinessCourseComment>{
	private static final long serialVersionUID = 1L;
	//DAO
	public static final BusinessCourseComment dao = new BusinessCourseComment();
}
