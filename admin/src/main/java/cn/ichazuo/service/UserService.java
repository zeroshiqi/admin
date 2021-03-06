package cn.ichazuo.service;

import cn.ichazuo.model.User;

/**
 * @ClassName: UserService 
 * @Description: (用户Service) 
 * @author ZhaoXu
 * @date 2015年8月1日 下午12:26:17 
 * @version V1.0
 */
public class UserService {
	
	/**
	 * @Title: findUserByAccount 
	 * @Description: (根据账号查询User) 
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account){
		return User.dao.findFirst("select * from s_user where account = ? and status=1", account);
	}
}
