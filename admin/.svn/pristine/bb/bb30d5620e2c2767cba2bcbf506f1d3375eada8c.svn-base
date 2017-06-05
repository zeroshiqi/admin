package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: MemberInfo 
 * @Description: (映射会员信息表) 
 * @author ZhaoXu
 * @date 2015年8月1日 下午1:21:16 
 * @version V1.0
 */
public class MemberInfo extends Model<MemberInfo> {
	private static final long serialVersionUID = 1L;
	// DAO
	public static final MemberInfo dao = new MemberInfo();
	
	/**
	 * @Title: getMember 
	 * @Description: (获得Member) 
	 * @return
	 */
	public Member getMember(){
		return Member.dao.findById(getLong("member_id"));
	}

}
