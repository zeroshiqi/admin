package cn.ichazuo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.im.EasemobIMUsers;
import cn.ichazuo.model.Member;
import cn.ichazuo.model.MemberInfo;

/**
 * @ClassName: MemberService 
 * @Description: (会员Service) 
 * @author ZhaoXu
 * @date 2015年8月1日 下午1:27:47 
 * @version V1.0
 */
public class MemberService {
	
	/**
	 * @Title: findAllMember 
	 * @Description: (查询所有注册用户) 
	 * @return
	 */
	public List<Record> findAllMember(String name){
		return Db.find("select id,nick_name as nickName from t_member where status = 1 and nick_name like ? limit 0,50","%"+name+"%");
	}
	
	/**
	 * @Title: findAllTeachers 
	 * @Description: (查询所有注册用户) 
	 * @return
	 */
	public List<Record> findAllTeachers(String name){
		return Db.find("select id,name as nickName from t_teacher where status = 1 and name like ?","%"+name+"%");
	}
	
	/**
	 * @Title: findMemberList 
	 * @Description: (查询分页) 
	 * @param page
	 * @return
	 */
	public List<Member> findMemberList(int page,String mobile,String nickName,String sex,String work){
		String sql = " from t_member m left join t_member_info i on m.id = i.member_id where m.status = 1 ";
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and m.mobile like '%"+mobile.trim()+"%'";
		}
		if(!StringUtils.isNullOrEmpty(nickName)){
			sql += " and m.nick_name like '%"+nickName.trim()+"%'";
		}
		if(!StringUtils.isNullOrEmpty(sex) && !"-1".equals(sex)){
			if("1".equals(sex)){
				sql += " and i.gender = '男'";
			}else{
				sql += " and i.gender = '女'";
			}
		}
		if(!StringUtils.isNullOrEmpty(work)){
			sql += " and i.company_name like '%"+work+"%'";
		}
		sql += " order by m.id desc ";
		return Member.dao.paginate(page, Result.PAGE_COUNT, "select i.weixin,m.login_number,m.create_at,m.mobile,m.nick_name,i.gender,i.company_name,m.id as memberId", sql).getList();
	}
	
	/**
	 * @Title: findMemberCount 
	 * @Description: (查询总条数) 
	 * @return
	 */
	public long findMemberCount(String mobile,String nickName,String sex,String work){
		String sql = "select count(*) from t_member m inner join t_member_info i on m.id = i.member_id where m.status = 1";
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and m.mobile like '%"+mobile.trim()+"%'";
		}
		if(!StringUtils.isNullOrEmpty(nickName)){
			sql += " and m.nick_name like '%"+nickName.trim()+"%'";
		}
		if(!StringUtils.isNullOrEmpty(sex) && !"-1".equals(sex)){
			if("1".equals(sex)){
				sql += " and i.gender = '男'";
			}else{
				sql += " and i.gender = '女'";
			}
		}
		if(!StringUtils.isNullOrEmpty(work)){
			sql += " and i.company_name like '%"+work+"%'";
		}
		return Db.queryLong(sql);
	}
	
	/**
	 * @Title: findMemberFavourCount 
	 * @Description: (查询用户赞的人数) 
	 * @param memberId
	 * @param type
	 * @return
	 */
	public Long findMemberFavourCount(Long memberId,boolean type){
		if(type){
			return Db.queryLong("select count(*) from t_member_favour f  where f.from_member_id = ?",memberId);
		}else{
			return Db.queryLong("select count(*) from t_member_favour f  where f.to_member_id = ?",memberId);
		}
	}
	
	/**
	 * @Title: findMemberJoinCourseCount 
	 * @Description: (查询用户报名参加课程数量) 
	 * @param memberId
	 * @param type
	 * @return
	 */
	public Long findMemberJoinCourseCount(Long memberId,boolean type){
		if(type){
			return Db.queryLong("select count(*) from t_course_offline_join a join t_course b on a.course_id = b.id join t_course_offline c on c.course_id = b.id join t_member d on d.id = a.member_id where a.status=1 and b.status=1 and c.status=1 and d.status=1 and c.newtype=0 and a.member_id=?",memberId);
		}else{
			return Db.queryLong("select count(*) from v_offline_order where mobile = ? and `newtype` = 1 ",memberId);
		}
	}
	/**
	 * @Title: findMemberJoinCourseCount1 
	 * @Description: (查询用户报名参加课程数量) 
	 * @param memberId
	 * @param type
	 * @return
	 */
	public List<Record> findMemberJoinCourseCount1(String memberId,boolean type){
		if(type){
			String sql = "select c.id,c.course_name,a.number,a.price,a.create_at,b.name,0 as web from t_course_web_order a join t_course_web_order_user b on a.id=b.order_id join t_course c on c.id=a.course_id join t_course_offline d on d.course_id=c.id where a.status=1 and b.status=1 and c.status=1 and d.status=1 and d.newtype=0 and b.mobile='"+memberId;
			sql += "' union all (select b.id,b.name,1 as number,a.price,a.create_at,c.name,1 as web from t_haoduoke_app_order a join t_business_catalog b on a.order_catalog_id = b.id join t_business_employee c on c.id=a.employee_id where a.order_status=1 and c.mobile='"+memberId+"')";
			return Db.find(sql);
		}else{
			String sql = "select c.id,c.course_name,a.number,a.price,a.create_at,b.name,0 as web from t_course_web_order a join t_course_web_order_user b on a.id=b.order_id join t_course c on c.id=a.course_id join t_course_offline d on d.course_id=c.id where a.status=1 and b.status=1 and c.status=1 and d.status=1 and d.newtype=1 and b.mobile='"+memberId;
			sql += "' union all (select b.id,b.name,1 as number,a.price,a.create_at,c.name,1 as web from t_haoduoke_app_order a join t_business_catalog b on a.order_catalog_id = b.id join t_business_employee c on c.id=a.employee_id where a.order_status=1 and c.mobile='"+memberId+"')";
			return Db.find(sql);
		}
	}
	
	/**
	 * @Title: findOfflineCourse 
	 * @Description: (查询参加的线下课程) 
	 * @param page
	 * @param memberId
	 * @return
	 */
	public List<Record> findOfflineCourse(int page,Long memberId){
		String sql = "from t_course_offline_join j left join t_course_offline o on o.course_id = j.course_id inner join t_course c on c.id = o.course_id where o.`status` = 1 and c.`status` = 1 and o.newtype=0 and j.member_id="+memberId;
		sql += " order by o.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select c.id,c.course_name,j.number,o.course_time,o.price,j.create_at as update_at", sql).getList();
//		String sql = "from v_offline_order where newtype=0 and mobile="+memberId;
//		sql += " order by update_at desc";
//		return Db.paginate(page, Result.PAGE_COUNT, "select id,course_name,price,web,update_at,name,number", sql).getList();
	}
	
	/**
	 * @Title: findOnlineCourse 
	 * @Description: (查询参加的线上课程) 
	 * @param page
	 * @param memberId
	 * @return
	 */
	public List<Record> findOnlineCourse(int page,String memberId){
//		String sql = "from t_course_online_join j left join t_course_online o on o.course_id = j.course_id inner join t_course c on c.id = o.course_id where o.`status` = 1 and c.`status` = 1 and j.member_id="+memberId;
//		sql += " order by o.id desc ";
//		return Db.paginate(page, Result.PAGE_COUNT, "select *,c.id as courseId,(select count(*) from t_course_online_join o where o.course_id = c.id) as count ,j.create_at as joinTime", sql).getList();
//		String sql = "from v_offline_order where newtype=1 and mobile="+memberId;
//		sql += " order by update_at desc";
//		return Db.paginate(page, Result.PAGE_COUNT, "select id,course_name,price,web,update_at,name,number", sql).getList();
		String sql = "select c.id,c.course_name,a.number,a.price,a.create_at,b.name,0 as web from t_course_web_order a join t_course_web_order_user b on a.id=b.order_id join t_course c on c.id=a.course_id join t_course_offline d on d.course_id=c.id where a.status=1 and b.status=1 and c.status=1 and d.status=1 and d.newtype=1 and b.mobile='"+memberId;
		sql += "' union all (select b.id,b.name,1 as number,a.price,a.create_at,c.name,1 as web from t_haoduoke_app_order a join t_business_catalog b on a.order_catalog_id = b.id join t_business_employee c on c.id=a.employee_id where a.order_status=1 and c.mobile='"+memberId+"')";
		return Db.find(sql);
	}
	public List<Record> findOnlineCourseAll(String memberId){
//		String sql = "from t_course_online_join j left join t_course_online o on o.course_id = j.course_id inner join t_course c on c.id = o.course_id where o.`status` = 1 and c.`status` = 1 and j.member_id="+memberId;
//		sql += " order by o.id desc ";
//		return Db.paginate(page, Result.PAGE_COUNT, "select *,c.id as courseId,(select count(*) from t_course_online_join o where o.course_id = c.id) as count ,j.create_at as joinTime", sql).getList();
		String sql = "select id,course_name,price,web,update_at,name,number from v_offline_order where newtype=1 and mobile="+memberId;
		sql += " order by update_at desc";
		return Db.find(sql);
	}
	public List<Record> findComment(String memberId){
//		String sql = "from t_course_online_join j left join t_course_online o on o.course_id = j.course_id inner join t_course c on c.id = o.course_id where o.`status` = 1 and c.`status` = 1 and j.member_id="+memberId;
//		sql += " order by o.id desc ";
//		return Db.paginate(page, Result.PAGE_COUNT, "select *,c.id as courseId,(select count(*) from t_course_online_join o where o.course_id = c.id) as count ,j.create_at as joinTime", sql).getList();
		String sql = "select a.course_name,DATE_FORMAT(a.begin_time,'%Y年%m月%d日') create_at,b.address,b.teacher_id from t_course a join t_course_offline b on a.id = b.course_id where a.status=1 and b.status=1 and b.newtype=0";
		return Db.find(sql);
	}
	
	
	/**
	 * @Title: findMemberById 
	 * @Description: (根据ID查询注册用户) 
	 * @param id
	 * @return
	 */
	public Member findMemberById(Long id){
		return Member.dao.findById(id);
	}
	
	/**
	 * @Title: findMemberInfoByMemberId 
	 * @Description: (根据用户ID查询用户信息) 
	 * @param memberId
	 * @return
	 */
	public MemberInfo findMemberInfoByMemberId(Long memberId){
		return MemberInfo.dao.findFirst("select * from t_member_info where member_id = ? ",memberId);
	}
	
	/**
	 * @Title: findMemberMobileCount 
	 * @Description: (查询手机号数量) 
	 * @param mobile
	 * @return
	 */
	public long findMemberMobileCount(String mobile){
		return Db.queryLong("select count(*) from t_member where status = 1 and mobile = ?",mobile);
	}
	
	/**
	 * @Title: findCoreCapacity 
	 * @Description: (查询核心能力) 
	 * @param coreId
	 * @return
	 */
	public String findCoreCapacity(Long coreId){
		return Db.queryStr("select `value` from s_dict_item where id = ?",coreId);
	}
	
	/**
	 * @Title: deleteMember 
	 * @Description: (删除用户) 
	 * @param id
	 * @throws Exception 
	 */
	@Before(Tx.class)
	public Member deleteMember(Long id) throws Exception{
		Member member = Member.dao.findById(id);
		if(member.set("status", 0).update() &&
				MemberInfo.dao.findFirst("select * from t_member_info where member_id = ?",id).set("status", 0).update()){
			return member;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: saveMember 
	 * @Description: (保存用户信息) 
	 * @param member
	 * @param memberInfo
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Member saveMember(Member member,MemberInfo memberInfo) throws Exception{
		if(member.save() && memberInfo.set("member_id", member.getLong("id")).save()){
			Long id = member.getLong("id");
			if(PropKit.getBoolean("project.dev", true)){
				saveTestImUser(id);
			}else{
				saveIMUser(id);
			}
			return member;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: updateMember 
	 * @Description: (修改用户信息) 
	 * @param member
	 * @param memberInfo
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Member updateMember(Member member,MemberInfo memberInfo) throws Exception{
		if(member.update() && memberInfo.update()){
			return member;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: saveMembers 
	 * @Description: (保存用户) 
	 * @param members
	 */
	@Before(Tx.class)
	public List<String> saveMembers(Map<Member,MemberInfo> members,Map<Member,Long> courseIds,Map<Member,Long> sourseFrom,Map<Member,Long> numbers,Map<Member,String> emails,Map<Member,Long> reviews,Map<Member,String> contents,Map<Member,Double> prices){
		Set<Member> set = members.keySet();
		List<String> result = new ArrayList<>();
		for(Member member : set){
			MemberInfo info = members.get(member);
			Long courseId = courseIds.get(member);
			Long sourse = sourseFrom.get(member);
			//购买数量
			Long number = numbers.get(member);
			//邮箱
			String email = emails.get(member);
			//审核结果
			Long reviewStatus = reviews.get(member);
			//提问
			String content = contents.get(member);
			//提问
			Double price = prices.get(member);
			//过滤手机号相同的用户
			Long id = Db.queryLong("select id from t_member where mobile = ? and status = 1 limit 1",member.getStr("mobile"));
			if(NumberUtils.isNullOrZero(id)){
				member.save();
				info.set("member_id", member.getLong("id"));
				
				if(PropKit.getBoolean("project.dev", true)){
					saveTestImUser( member.getLong("id"));
				}else{
					saveIMUser( member.getLong("id"));
				}
				info.save();
				
				id = member.getLong("id");
			}else{
				result.add(String.valueOf(id));
			}

			if(!NumberUtils.isNullOrZero(courseId)){
				//保存订单信息到t_course_web_order表
				Record record = new Record();
				record.set("course_id", courseId);
				record.set("member_id",id);
				if(sourse==4){
					record.set("order_code","罗辑思维订单");
				}else if(sourse==5){
					record.set("order_code","直接转账");
				}else if(sourse==8){
					record.set("order_code","线下课程赠送");
				}else if(sourse==9){
					record.set("order_code","企业用户赠送");
				}
				else{
					record.set("order_code","其他渠道订单");
				}
				record.set("status","1");
				record.set("openid","Excel导入");
				record.set("unionid","Excel导入");
				record.set("email",email);
				record.set("invoice_name",member.get("nick_name"));
				record.set("invoice_mobile",member.get("mobile"));
				record.set("invoice_status",1);
				record.set("type",2);
				record.set("create_at", DateUtils.getNowDate());
				record.set("update_at", DateUtils.getNowDate());
				record.set("review_status", reviewStatus);
				record.set("price", price);
				record.set("is_gift", 0);
				record.set("job",info.get("job_name"));
				record.set("ip", "127.0.0.1");
				Db.save("t_course_web_order",record);
				//保存订单信息到t_course_web_order_user表
				Record record1 = new Record();
				record1.set("order_id", record.getLong("id"));
				record1.set("member_id",id);
				record1.set("name",member.get("nick_name"));
				record1.set("mobile",member.get("mobile"));
				record1.set("status","1");
				record1.set("content",content);
				record1.set("sex","man");
				record1.set("version",1);
				record1.set("weixin","(默认)");
				record1.set("work",info.get("company_name"));
				record1.set("create_at", DateUtils.getNowDate());
				Db.save("t_course_web_order_user",record1);
				Long count = Db.queryLong("select count(*) from t_course_offline_join where course_id = ? and member_id = ? and status = 1",courseId,id);
				if(NumberUtils.isNullOrZero(count)){
					Record record2 = new Record();
					record2.set("course_id", courseId).set("member_id", id);
					record2.set("create_at", DateUtils.getNowDate());
					if(!NumberUtils.isNullOrZero(sourse)){
						record2.set("from1", sourse);
						record2.set("type", sourse);
					}
					if(!NumberUtils.isNullOrZero(number)){
						record2.set("number", number);
					}else{
						record2.set("number", "1");
					}
					record2.set("order_id", record.getLong("id"));
					Db.save("t_course_offline_join",record2);
				}
			}
		}
		return result;
	}
	
	/**
	 * @Title: findOfflineJoinMember 
	 * @Description: (查询线下课程报名用户) 
	 * @param courseId
	 * @param page
	 * @return
	 */
	public List<Record> findOfflineJoinMember(Long courseId,int page,String name){
		return Db.paginate(page, Result.PAGE_COUNT, "select t.id as memberId,t.mobile,t.nick_name,i.gender,i.job_name,t.avatar,j.type,j.from1,j.id as joinId,j.number ", "from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = ? and t.nick_name like ? order by j.type desc",courseId,"%"+name+"%").getList();
	}
	
	/**
	 * @Title: findOfflineJoinMemberCount 
	 * @Description: (查询线下课程报名用户数量) 
	 * @param courseId
	 * @return
	 */
	public Long findOfflineJoinMemberCount(Long courseId,String name){
		return Db.queryLong("select count(*) from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = ? and t.nick_name like ?", courseId,"%"+name+"%");
	}
	
	/**
	 * @Title: findOnlinecJoinMember 
	 * @Description: (查询线上课程在线用户列表) 
	 * @param courseId
	 * @param page
	 * @return
	 */
	public List<Record> findOnlineJoinMember(Long courseId,int page,String name){
		return Db.paginate(page, Result.PAGE_COUNT, "select t.id as memberId,t.mobile,t.nick_name,i.gender,i.job_name,t.avatar,j.type,j.from1,j.id as joinId,j.number ", "from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = ? and t.nick_name like ? order by j.type desc",courseId,"%"+name+"%").getList();
	}
	
	/**
	 * @Title: findOnlineJoinMemberAllCount 
	 * @Description: (查询线上课程报名用户) 
	 * @param courseId
	 * @return
	 */
	public Long findOnlineJoinMemberAllCount(Long courseId,String name){
		return Db.queryLong("select sum(j.number) from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = ? and t.nick_name like ?", courseId,"%"+name+"%");
	}
	
	/**
	 * @Title: findOnlinecJoinMemberAll 
	 * @Description: (查询线上课程报名用户列表) 
	 * @param courseId
	 * @param page
	 * @return
	 */
	public List<Record> findOnlineJoinMemberAll(Long courseId,int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select t.id as memberId,t.mobile,t.nick_name,i.gender,i.job_name,t.avatar,j.status", "from t_course_online_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where j.status=1 and t.status=1 and j.course_id = ?",courseId).getList();
	}
	
	/**
	 * @Title: findOnlineJoinMemberCount 
	 * @Description: (查询线上课程在线用户) 
	 * @param courseId
	 * @return
	 */
	public Long findOnlineJoinMemberCount(Long courseId,String name){
		return Db.queryLong("select count(*) from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = ? and t.nick_name like ?", courseId,"%"+name+"%");
	}
	
	/**
	 * @Title: findFavourMember 
	 * @Description: (查询认可的用户) 
	 * @param type
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<Member> findFavourMember(int type,Long memberId,int page){
		String sql = "";
		if(type == 0){
			sql = "from t_member_favour f left join t_member m on m.id = f.to_member_id left join t_member_info i on i.member_id = m.id where f.status = 1 and m.status = 1 and f.from_member_id = ?";
		}else{
			sql = "from t_member_favour f left join t_member m on m.id = f.from_member_id left join t_member_info i on i.member_id = m.id where f.status = 1 and m.status = 1 and f.to_member_id = ?";
		}
		return Member.dao.paginate(page, Result.PAGE_COUNT, "select m.id as memberId,m.avatar,m.nick_name,i.gender,i.job_name,m.login_number", sql,memberId).getList();
	}
	
	/**
	 * @Title: findFavourMemberCount 
	 * @Description: (查询认可的用户数量) 
	 * @param type
	 * @param memberId
	 * @return
	 */
	public Long findFavourMemberCount(int type,Long memberId){
		if(type==0){
			return Db.queryLong("select count(*) from t_member_favour f  where f.status = 1 and f.from_member_id = ?",memberId);
		}else{
			return Db.queryLong("select count(*) from t_member_favour f  where f.status = 1 and f.to_member_id = ?",memberId);
		}
	}
	
	private void saveIMUser(Long userId){
		 ObjectNode datanode = JsonNodeFactory.instance.objectNode();
	     datanode.put("username",String.valueOf(userId));
	     datanode.put("password",PasswdEncryption.MD5(String.valueOf(userId)));
	     ObjectNode createChazuoIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode,PropKit.get("im.chazuo.appkey"),PropKit.get("im.chazuo.clientid"),PropKit.get("im.chazuo.clientsecret"));
	     ObjectNode createGuGuIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode,PropKit.get("im.gugu.appkey"),PropKit.get("im.gugu.clientid"),PropKit.get("im.gugu.clientsecret"));
	     if (null != createGuGuIMUserSingleNode) {
	        System.out.println(createGuGuIMUserSingleNode.toString());
	     }
	     if(null != createChazuoIMUserSingleNode){
	        System.out.println(createChazuoIMUserSingleNode.toString());
	     }
	}
	
	private void saveTestImUser(Long userId){
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
	     datanode.put("username",String.valueOf(userId));
	     datanode.put("password",PasswdEncryption.MD5(String.valueOf(userId)));
	     ObjectNode createChazuoIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode,PropKit.get("im.test.appkey"),PropKit.get("im.test.clientid"),PropKit.get("im.test.clientsecret"));
	     if(null != createChazuoIMUserSingleNode){
	        System.out.println(createChazuoIMUserSingleNode.toString());
	     }
	}
}
