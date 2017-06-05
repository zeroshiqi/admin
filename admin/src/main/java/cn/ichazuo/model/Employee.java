package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: Employee 
 * @Description: (映射企业用户表) 
 * @author LiDongYang
 * @date 2016-5-9 14:00:15 
 * @version V1.0
 */
public class Employee extends Model<Employee>{
	private static final long serialVersionUID = 1L;
	//DAO
	public static final Employee dao = new Employee();
}
