package cn.ichazuo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.ChuanglanSMS;
import cn.ichazuo.common.utils.StringUtils;

/**
 * @ClassName: OrderService 
 * @Description: (订单Service) 
 * @author ZhaoXu
 * @date 2015年9月6日 下午1:38:23 
 * @version V1.0
 */
public class OrderService {
	/**
	 * @Title: findWebOrder 
	 * @Description: (查询Web订单列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findWebOrder(int page,String name,String price,int newtype,String beginTime,String endTime,String mobile,String code){
		if(StringUtils.isNullOrEmpty(price) || "null".equals(price)){
			price="";
		}
		if(StringUtils.isNullOrEmpty(name) || "null".equals(name)){
			name="";
		}
		Double p = Double.valueOf(StringUtils.isNullOrEmpty(price) ? "-1" : price);
		String sql = "from v_offline_order where newtype = "+newtype+" and type >= 0 and review_status!=2";
		if(p != -1){
			sql += " and price = " + p;
		}
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql += " and update_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql+=" and course_name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and update_at <='"+endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and mobile = '"+mobile+"'";
		}
		if(!StringUtils.isNullOrEmpty(code)){
			sql += " and order_code like '%"+code+"%'";
		}
		sql +=" order by id desc ";
		return Db.paginate(page, Result.PAGE_COUNT, "select id,order_code as code,number,price,course_name,order_code,update_at,type,job,join_reason,buy_intentions,mobile,review_status,refund_result,email,web,name as names ", sql).getList();
	}
	public List<Record> findWebOrderExport(String name,String price,int newtype,String beginTime,String endTime,String mobile){
//		Double p = Double.valueOf(StringUtils.isNullOrEmpty(price) ? "-1" : price);
		String p = StringUtils.isNullOrEmpty(price) ? "-1" : price;
		String sql = " SELECT `b`.`order_code` AS `code`,'1' AS `number`,`b`.`price` AS `price`,`a`.`name` AS `course_name` ,`b`.`order_code` AS `order_code` ,"
			+"`b`.`create_at` AS `update_at` ,"
			+"case when `b`.`payment_name`='支付宝' then '2' else '0' end as type ,c.name as name,'' as weixin,'' as work,c.position as job,`c`.`mobile` AS `mobile` ,c.sex as sex ,'' AS `address`,a.teacher_name as teacherId,2 as web,'' as content,'0' as from1 FROM"
			+"("
			+"("
			+"`t_haoduoke_catalog_group` `a`"
					+"JOIN `t_haoduoke_app_order` `b` ON("
						+"("
						+"	`a`.`id` = `b`.`order_catalog_id`"
						+")"
					+")"
				+")"
				+"JOIN `t_business_employee` `c` ON((`c`.`id` = `b`.`employee_id`))"
			+")"
		+"WHERE `a`.`status` = 1 AND `b`.`order_status` = 1 and b.catalog_type=3 AND `c`.`status` = 1 and c.mobile not in (select mobile from t_business_employee where business_id = 3 and status=1)";
		if( !"-1".equals(p)){
			sql += " and b.price = " + p;
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and a.name like '%"+name+"%' ";
		}
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql += " and b.create_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and b.create_at <='"+endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and c.mobile ='"+mobile+"'";
		}
		String sql2 = " SELECT `b`.`order_code` AS `code`,'1' AS `number`,`b`.`price` AS `price`,`a`.`name` AS `course_name` ,`b`.`order_code` AS `order_code` ,"
				+"`b`.`create_at` AS `update_at` ,"
				+"case when `b`.`payment_name`='支付宝' then '2' else '0' end as type ,c.name as name,'' as weixin,'' as work,c.position as job,`c`.`mobile` AS `mobile` ,c.sex as sex ,'' AS `address`,a.teacher_id as teacherId,1 as web,'' as content,'0' as from1 FROM"
				+"("
				+"("
				+"`t_business_catalog` `a`"
						+"JOIN `t_haoduoke_app_order` `b` ON("
							+"("
							+"	`a`.`id` = `b`.`order_catalog_id`"
							+")"
						+")"
					+")"
					+"JOIN `t_business_employee` `c` ON((`c`.`id` = `b`.`employee_id`))"
				+")"
			+"WHERE `a`.`status` = 1 and b.catalog_type=2 AND `b`.`order_status` = 1 AND `c`.`status` = 1 and c.mobile not in (select mobile from t_business_employee where business_id = 3 and status=1)";
			if( !"-1".equals(p)){
				sql2 += " and b.price = " + p;
			}
			if(!StringUtils.isNullOrEmpty(name)){
				sql2 += " and a.name like '%"+name+"%' ";
			}
			if(!StringUtils.isNullOrEmpty(beginTime)){
				sql2 += " and b.create_at >= '"+beginTime+"'";
			}
			if(!StringUtils.isNullOrEmpty(endTime)){
				sql2 += " and b.create_at <='"+endTime+"'";
			}
			if(!StringUtils.isNullOrEmpty(mobile)){
				sql2 += " and c.mobile ='"+mobile+"'";
			}
		String sql1 = " select w.order_code as code,w.number,w.price,c.course_name,w.order_code,w.update_at,w.type,u.`name`,a.weixin,a.`work`,w.job,a.mobile,a.sex,w.address,o.teacher_id as teacherId,0 as web,u.content as content,w.from1 from t_course_web_order_user u left join t_course_web_order w on u.order_id = w.id left join t_course c on w.course_id = c.id left join t_course_offline o on o.course_id = c.id JOIN t_course_web_order_user a ON a.order_id = w.id where w.`status` = 1 and (o.newtype= 1 or o.newtype=2 or o.newtype=5) and a.mobile not in (select mobile from t_business_employee where business_id = 3 and status=1)";
		if( !"-1".equals(p)){
			sql1 += " and w.price = " + p;
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql1 += " and c.course_name like '%"+name+"%' ";
		}
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql1 += " and w.create_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql1 += " and w.create_at <='"+endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql1 += " and a.mobile ='"+mobile+"'";
		}
		sql =sql2+"union all"+sql + " union all " + sql1;
		return Db.find(sql);
	}
	/**
	 * @Title: findWebOrderCount 
	 * @Description: (查询意见反馈总数) 
	 * @return
	 */
	public Long findWebOrderCount(String name,String price,int newtype,String beginTime,String endTime,String mobile,String code){
		if(StringUtils.isNullOrEmpty(name) || "null".equals(name)){
			name="";
		}
		if(StringUtils.isNullOrEmpty(price) || "null".equals(price)){
			price="";
		}
		Double p = Double.valueOf(StringUtils.isNullOrEmpty(price) ? "-1" : price);
		String sql = "select count(*) from v_offline_order where `course_name` like ?  and newtype = ? ";
		if(p != -1){
			sql += " and price = " + p;
		}
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql += " and update_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and update_at <='"+endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and mobile = '"+mobile+"' or job ='"+mobile+"'";
		}
		if(!StringUtils.isNullOrEmpty(code)){
			sql += " and order_code like '%"+code+"%'";
		}
		return Db.queryLong(sql,"%"+name+"%",newtype);
	}
	
	
	
	/**
	 * @Title: findWebOrderCount 
	 * @Description: (查询意见反馈总数) 
	 * @return
	 */
	public Long findEmployeeRegisterCount(String beginTime,String endTime){
		String sql = "select count(*) from t_business_employee where status=1 ";
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql += " and create_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and create_at <='"+endTime+"'";
		}
		return Db.queryLong(sql);
	}
	
	/**
	 * @Title: findWebUserList 
	 * @Description: (查询订单用户信息列表) 
	 * @param page
	 * @param courseId
	 * @return
	 */
	public List<Record> findWebUserList(int page,Long orderId){
		return Db.paginate(page, Result.PAGE_COUNT, "select member_id as memberId,name,mobile,weixin,content,sex,work", "from t_course_web_order_user where order_id = ? and status = 1 ",orderId).getList();
	}
	
	public List<Record> findWebAllUserList(Long orderId){
		return Db.find("select member_id as memberId,name,mobile,weixin,content,sex,work from t_course_web_order_user where order_id = ? and status = 1 ", orderId);
//		return Db.paginate(page, Result.PAGE_COUNT, "select member_id as memberId,name,mobile,weixin,content,sex,work", "from t_course_web_order_user where order_id = ? and status = 1 ",orderId).getList();
	}
//	public List<Record> findWebAllUserList1(String  mobile){
//		return Db.find("select member_id as memberId,name,mobile,weixin,content,sex,work from t_course_web_order_user where order_id = ? and status = 1 ", orderId);
////		return Db.paginate(page, Result.PAGE_COUNT, "select member_id as memberId,name,mobile,weixin,content,sex,work", "from t_course_web_order_user where order_id = ? and status = 1 ",orderId).getList();
//	}
	/**
	 * @Title: findWebUserCount 
	 * @Description: (查询订单用户信息) 
	 * @param courseId
	 * @return
	 */
	public Long findWebUserCount(Long courseId){
		return Db.queryLong("select count(*) from t_course_web_order_user where order_id = ? and status = 1",courseId);
	}
	
	public List<Record> findWebOnlineCourseOrder(int page,String name){
		return Db.paginate(page, Result.PAGE_COUNT, "select o.code,o.price,o.course_id,e.course_name,o.weixin,o.update_at,o.at ", "from t_course_online_order o left join t_course e on e.id = o.course_id where o.status = 1 and e.course_name like ? order by o.create_at desc","%"+name+"%").getList();
	}
	
	public Long findWebOnlineCourseOrderCount(String name){
		return Db.queryLong("select count(*) from t_course_online_order o left join t_course_online_order_user u on u.order_id = o.id left join t_course e on e.id = o.course_id where o.status = 1 and e.course_name like ?","%"+name+"%");
	}
	public Long findJiNiuWebOnlineCourseOrderCount(String no){
		return Db.queryLong("select count(*) from t_course_jiniu_web_order o where o.status = 1 and course_id = ? ",no);
	}
	public List<Record> findCourseMember(String courseId){
		return Db.find("select a.mobile,a.name,b.course_id,b.email,b.order_code,b.price,b.is_gift,b.review_status from t_course_web_order_user a join t_course_web_order b on a.order_id = b.id where a.order_id=?",courseId);
	}
	//查询线下课程审核通过短信
	public List<Record> findMessageByCourseId(String courseId){
		return Db.find("select message,message3 from t_course_offline where course_id=?",courseId);
	}
	
	//查询线下课程审核通过短信
	public List<Record> findEmailReportList(String email,String courseId){
		String sql = "select * from t_course_email where 1=1  and to_days(create_at) = to_days(now())";
		if(!StringUtils.isNullOrEmpty(email)){
			sql += " and email = '"+email+"'";
		}
		if(!StringUtils.isNullOrEmpty(courseId)){
			sql += " and course_id ="+courseId;
		}
		return Db.find(sql);
	}
	//查询线下课程审核通过短信
	public List<Record> findMessageReportList(String mobile,String courseId){
		String sql = "select * from t_sms_report where 1=1 and to_days(create_at) = to_days(now())";
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and mobile = '"+mobile+"'";
		}
		if(!StringUtils.isNullOrEmpty(courseId)){
			sql += " and course_id ="+courseId;
		}
		return Db.find(sql);
	}
	//查询线下课程审核通过邮件
	public List<Record> findEmailByCourseId(String courseId){
		return Db.find("select email,email2,email4 from t_course_offline where course_id=?",courseId);
	}
	//查询线下课程审核未通过短信
		public List<Record> findMessageByCourseId1(String courseId){
			return Db.find("select message1,message4 from t_course_offline where course_id=?",courseId);
		}
		//查询线下课程未审核通过邮件
		public List<Record> findEmailByCourseId1(String courseId){
			return Db.find("select email1,email3 from t_course_offline where course_id=?",courseId);
		}
		//
		public List<Record> findCourseMember1(String courseId){
			return Db.find("select b.name,a.email from t_course_web_order a join t_course_web_order_user b on a.id = b.order_id where a.course_id = ? and a.status=1 and b.status=1 and a.review_status=1",courseId);
		}
	
	//*********************************************************************************//
	//******************************    以下为查询所有订单的接口        *****************************//
	//*********************************************************************************//
	/**
	 * @Description: (查询所有课程订单总数) 
	 * @return
	 */
	public Long findAllOrderCount(){
		String sql = "select count(*) from v_order";
		return Db.queryLong(sql);
	}
	/**
	 * @Title: findAllOrder 
	 * @Description: (查询全部订单列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findAllOrder(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select o.id,o.course_name,o.CODE,o.price,o.province,o.city,o.create_at,o.name,o.mobile ", "from v_order o").getList();
	}
	
	public Long findAllOrderCountByTime(String startTime,String endTime,String city){
		String sql = "from v_order where 1=1";
		if(!StringUtils.isNullOrEmpty(startTime)){
			sql += " and create_at >= '" + startTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and create_at <= '" + endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(city)){
			sql += " and city like '%" + city+"%'";
		}
		return Db.queryLong("select count(*) "+sql);
	}
	
	/**
	 * @Title: findAllOrder 
	 * @Description: (查询全部订单列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findAllOrderByTime(int page,String startTime,String endTime,String city){
		String sql = "from v_order o where 1=1";
		if(!StringUtils.isNullOrEmpty(startTime)){
			sql += " and create_at >= '" + startTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and create_at <= '" + endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(city)){
			sql += " and city like '%" + city+"%'";
		}
		return Db.paginate(page, Result.PAGE_COUNT, "select o.id,o.course_name,o.CODE,o.price,o.province,o.city,o.create_at,o.name,o.mobile ", sql).getList();
	}
	
	/**
	 * @Description: (查询所有订单课程总数) 
	 * @return
	 */
	public Long findAllOfficeCount(){
		String sql = "SELECT count(a.zong) FROM (select count(*) as zong from v_order group by id) a ";
		return Db.queryLong(sql);
	}
	/**
	 * @Description: (查询所有订单课程总数) 
	 * @return
	 */
	public Long findAllOfficeCount(String startTime,String endTime,String city){
		String sql = "from v_order where 1=1";
		if(!StringUtils.isNullOrEmpty(startTime)){
			sql += " and create_at >= '" + startTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and create_at <= '" + endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(city)){
			sql += " and city like '%" + city+"%'";
		}
		return Db.queryLong("SELECT count(a.zong) FROM (select count(*) as zong "+ sql +" group by id) as a");
	}
	/**
	 * @return 
	 * @Description: (查询所有订单总金额) 
	 * @return
	 */
	public Double findAllMoneyCount(){
		String sql = "select SUM(price) FROM v_order ";
		return Db.queryBigDecimal(sql).doubleValue();
	}
	/**
	 * @Description: (查询所有订单课程总金额) 
	 * @return
	 */
//	public Double findAllMoneyCount(String startTime,String endTime,String city){
//		Double result =  Db.queryBigDecimal("SELECT SUM(price) FROM v_order where create_at >= ? and create_at <= ? ",startTime,endTime).doubleValue();
//		return result;
//	}
	//查询所有订单
	public List<Record> findAllOrders(){
		return Db.find("select o.id,o.city,o.province,o.mobile from t_course_web_order_user o");
	}
	/**
	 * @Description: (查询所有订单课程总金额) 
	 * @return
	 */
	public Double findAllMoneyCount(String startTime,String endTime,String city){
		String sql = "from v_order where 1=1";
		if(!StringUtils.isNullOrEmpty(startTime)){
			sql += " and create_at >= '" + startTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and create_at <= '" + endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(city)){
			sql += " and city like '%" + city+"%'";
		}
		Double result =  Db.queryBigDecimal("SELECT SUM(price) "+sql).doubleValue();
		return result;
	}
	//更新订单
	public void update(Record record){
		Db.update("t_course_web_order_user", record);
	}
	
	//更新订单
	public void updateOrderReviewStatus(Record record){
		Db.update("t_course_web_order", record);
	}
	//更新订单
	public void updateOrderRefundResult(Record record){
		Db.update("t_course_web_order", record);
	}
	
	 /**
     * @param urlAll
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String request( String mobileNumber) {
    	String httpUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?phone=" +mobileNumber ;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "39e7dd9a339a9265683e25d070693ca5");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////               报表部分                                                                 //////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Record> findMonthList(){
    	return Db.find("select a.yue from (SELECT date_format(create_at, '%Y') AS nian,date_format(create_at, '%m') AS yue	FROM v_order WHERE date_format(create_at, '%Y') = '2016' GROUP BY nian,yue) as a");
    }
    
    public List<Record> findPriceByMonth(){
    	return Db.find("SELECT SUM(a.zong) FROM (SELECT date_format(create_at, '%Y') AS nian,date_format(create_at, '%m') AS yue,course_name,SUM(price) AS zong FROM v_order GROUP BY nian,yue) as a WHERE a.nian='2016' GROUP BY a.nian,a.yue");
    }
    
    public List<Record> findOrdersByMonth(){
    	return Db.find("SELECT COUNT(a.yue) FROM (SELECT date_format(create_at, '%Y') AS nian,date_format(create_at, '%m') AS yue,course_name FROM v_order WHERE date_format(create_at, '%Y') = '2016') as a GROUP BY a.nian,a.yue");
    }
    //复购率报表
    public List findComplexCCList(){
    	return Db.query("SELECT COUNT(a.cou) as cc FROM v_complexRate as a GROUP BY a.cou order by a.cou desc");
    }
    public List findComplexCouList(){
    	return Db.query("select a.cou from v_complexRate as a GROUP BY a.cou order by a.cou desc");
    }
    
    //复购率报表
    public List findPayNewCCList(String beginTime,String endTime){
    	String sql = "select DATE_FORMAT(z.create_at,'%m月%d') as days from ("+
				"select a.name,a.mobile,b.create_at from t_business_employee a join (select employee_id,`create_at` from t_business_member_record where status=1 and gain_way!=3 and gain_way!=1 group by employee_id) as b on "+
				"a.id = b.employee_id where a.status=1 and a.business_id not in (3,24,4,15,65,78,73,69,65,56,55,86,80)) as z where 1=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and z.create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and z.create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(z.create_at,'%Y年%m月%d日') order by create_at asc";
    	return Db.query(sql);
    }
    public List findPayNewCouList(String beginTime,String endTime){
    	String sql = "select count(*) from ("+
    				"select a.name,a.mobile,b.create_at from t_business_employee a join (select employee_id,`create_at` from t_business_member_record where status=1 and gain_way!=3 and gain_way!=1 group by employee_id) as b on "+
    				"a.id = b.employee_id where a.status=1 and a.business_id not in (3,24,4,15,65,78,73,69,65,56,55,86,80)) as z where 1=1 ";
    			if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
    				sql += " and z.create_at >= '"+beginTime+"' ";
    			}
    			if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
    				sql += " and z.create_at <= '"+endTime+"' ";
    			}
    			sql+=" group by DATE_FORMAT(z.create_at,'%Y年%m月%d日') order by create_at asc";
    	return Db.query(sql);
    }
    public BigDecimal findPayNewCouALl(String endTime){
    	String sql = "select sum(z.p) from (select count(*) as p from (select a.name,a.mobile,b.create_at from t_business_employee a join (select employee_id,`create_at` from t_business_member_record where status=1 and gain_way!=3 and gain_way!=1 group by employee_id) as b on a.id = b.employee_id where a.status=1 and a.business_id not in (3,24,4,15,65,78,73,69,65,56,55,86,80)) as z where 1=1 ";
    			if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
    			sql+= "group by DATE_FORMAT(z.create_at,'%Y年%m月%d日') order by create_at asc) as z";
    	return Db.queryBigDecimal(sql);
    }
    public BigDecimal findPayNewCouALl1(String beginTime,String endTime){
    	String sql = "select ifnull(sum(z.p),0) from (select count(*) as p from (select a.name,a.mobile,b.create_at from t_business_employee a join (select employee_id,`create_at` from t_business_member_record where status=1 and gain_way!=3 and gain_way!=1 group by employee_id) as b on a.id = b.employee_id where a.status=1 and a.business_id not in (3,24,4,15,65,78,73,69,65,56,55,86,80)) as z where 1=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
		    	if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
    			sql+= "group by DATE_FORMAT(z.create_at,'%Y年%m月%d日') order by create_at asc) as z";
    	return Db.queryBigDecimal(sql);
    }
    public List findBuyCouALl(String beginTime,String endTime){
    	String sql = "select * from v_offline_order where newtype=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and update_at >= '"+beginTime+"' ";
				}
		    	if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and update_at <= '"+endTime+"' ";
				}
    			sql+= "group by mobile";
    	return Db.find(sql);
    }
    //注册时段趋势报表
    public List findRegisterList(String beginTime,String endTime){
    	String sql = "select DATE_FORMAT(create_at,'%H') as hours,count(id) from t_business_employee where status=1 and create_at is not null ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(create_at,'%H')";
    	return Db.query(sql);
    }
    //注册时段趋势日期报表
    public List findRegisterDateList(String beginTime,String endTime){
    	String sql = "select DATE_FORMAT(create_at,'%m月%d') as days from t_business_employee where status=1 and create_at is not null  ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(create_at,'%Y%m%d') order by DATE_FORMAT(create_at,'%Y%m%d') asc";
    	return Db.query(sql);
    }
    //注册时段人数小时报表
    public List findRegisterHourList(String beginTime,String endTime){
    	String sql = "select DATE_FORMAT(create_at,'%H') as days from t_business_employee where status=1 and create_at is not null  ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(create_at,'%H') order by DATE_FORMAT(create_at,'%H') asc";
    	return Db.query(sql);
    }
    public List findRegisterHourCountList(String beginTime,String endTime){
    	String sql = "select count(DATE_FORMAT(create_at,'%H')) from t_business_employee where status=1 and create_at is not null  ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(create_at,'%H') order by DATE_FORMAT(create_at,'%H') asc";
    	return Db.query(sql);
    }
    //注册时段趋势数量报表
    public List findRegisterCountList(String beginTime,String endTime){
    	String sql = "select count(DATE_FORMAT(create_at,'%Y%m%d')) from t_business_employee where status=1 and create_at is not null  ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(create_at,'%Y%m%d') order by DATE_FORMAT(create_at,'%Y%m%d') asc";
    	return Db.query(sql);
    }
    //注册时段趋势数量报;
    public BigDecimal findRegisterCountAllList(String endTime){
    	String sql = "select ifnull(sum(z.k),0) from (select count(DATE_FORMAT(create_at,'%Y%m%d')) as k from t_business_employee where status=1 and create_at is not null ";
    			if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
    			sql+= " group by DATE_FORMAT(create_at,'%Y%m%d') order by DATE_FORMAT(create_at,'%Y%m%d') asc) as z";
    	return Db.queryBigDecimal(sql);
    }
  //注册时段趋势数量报;
    public BigDecimal findRegisterCountAllList1(String beginTime,String endTime){
    	String sql = "select ifnull(sum(z.k),0) from (select count(DATE_FORMAT(create_at,'%Y%m%d')) as k from t_business_employee where status=1 and create_at is not null ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
		    	if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
    			sql+= " group by DATE_FORMAT(create_at,'%Y%m%d') order by DATE_FORMAT(create_at,'%Y%m%d') asc) as z";
    	return Db.queryBigDecimal(sql);
    }
    //日活日期
    public List findActiveDateList(String beginTime,String endTime){
    	String sql = "select a.days from (select DATE_FORMAT(create_at, '%m月%d') as days,employee_id from t_business_study where employee_id !=33 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(create_at, '%Y年%m月%d日'),employee_id) a group by a.days";
    	return Db.query(sql);
    }
    //日活日活跃人数
    public List findActiveCountList(String beginTime,String endTime){
    	String sql = "select count(a.days) from (select DATE_FORMAT(create_at, '%Y年%m月%d日') as days,employee_id from t_business_study where employee_id !=33 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(create_at, '%Y年%m月%d日'),employee_id) a group by a.days";
    	return Db.query(sql);
    }
    //日活日活跃人数
    public List findActiveCountAllList(String beginTime,String endTime){
    	String sql = "select employee_id from t_business_study where employee_id !=33 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and create_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and create_at <= '"+endTime+"' ";
				}
				sql+=" group by employee_id ";
    	return Db.query(sql);
    }
    //app唤醒日期
    public List findAwakenDateList(String beginTime,String endTime){
    	String sql = "select a.days from (select DATE_FORMAT(login_time, '%m月%d') as days,employee_id from t_business_login_details where login_type=1 and employee_id !=33 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and login_time >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and login_time <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(login_time, '%Y年%m月%d日'),employee_id) a group by a.days";
    	return Db.query(sql);
    }
    //app唤醒人数
    public List findAwakenCountList(String beginTime,String endTime){
    	String sql = "select count(a.days) from (select DATE_FORMAT(login_time, '%Y年%m月%d日') as days,employee_id from t_business_login_details where login_type=1 and employee_id !=33 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and login_time >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and login_time <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(login_time, '%Y年%m月%d日'),employee_id) a group by a.days";
    	return Db.query(sql);
    }
    //app唤醒总人数
    public List findAwakenCountAllList(String beginTime,String endTime){
    	String sql = "select employee_id from t_business_login_details where login_type=1 and employee_id!=33 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and login_time >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and login_time <= '"+endTime+"' ";
				}
				sql+=" group by employee_id ";
    	return Db.query(sql);
    }
    
    //日付费日期
    public List findDailyPayDateList(String beginTime,String endTime){
    	String sql = "select a.days from (select DATE_FORMAT(update_at,'%m月%d') as days,mobile from v_offline_order where newtype=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and update_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and update_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(update_at,'%Y年%m月%d日'),mobile) as a group by a.days order by a.days";
    	return Db.query(sql);
    }
    //日付费日活跃人数
    public List findDailyPayCountList(String beginTime,String endTime){
    	String sql = "select a.days,count(a.days) from (select DATE_FORMAT(update_at,'%Y年%m月%d日') as days,mobile from v_offline_order where newtype=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and update_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and update_at <= '"+endTime+"' ";
				}
				sql+=" group by DATE_FORMAT(update_at,'%Y年%m月%d日'),mobile) as a group by a.days order by a.days";
    	return Db.query(sql);
    }
    //区间付费总人数
    public List findDailyPayCountAllList(String beginTime,String endTime){
    	String sql = "select count(mobile) from v_offline_order where newtype=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and update_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and update_at <= '"+endTime+"' ";
				}
				sql+=" group by mobile ";
    	return Db.query(sql);
    }
    
    public void sendMessage(String mobile,String message,Long courseId) throws ParseException, IOException{
    	ChuanglanSMS client = new ChuanglanSMS("N3606610","65e67e31");
		CloseableHttpResponse response = null;
		StringBuffer errStr = new StringBuffer();
		//发送短信
		response = client.sendMessage(mobile,message);
//		response = client.sendMessage("15910491294",str.replace("#xxx#", r.getStr("nick_name"))+text.replaceAll(" ",""));
			if(response != null && response.getStatusLine().getStatusCode()==200){
				Date date = new Date();
				String a = EntityUtils.toString(response.getEntity());
				JSONObject jsObject = new JSONObject(a);
				Record record = new Record();
				Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsObject.toString());
				record.set("mobile", mobile);
				record.set("content", message);
				record.set("create_at", date);
				record.set("success", jsonMap.get("success").toString());
				record.set("course_id", courseId);
				if(!(jsonMap.get("success").toString().equals("true") || jsonMap.get("success").toString()=="true")){
					errStr.append(mobile).append(",");
					record.set("recode",jsonMap.get("r"));
				}else{
					record.set("recode","0");
				}
				//把每一条短信的发送记录保存到数据库
				saveSms(record);
		}
    }
    
    //保存短信记录
    public void saveSms(Record record){
		Db.save("t_sms_report", record);
	}
  //保存短信记录
    public void saveEmail(Record record){
		Db.save("t_course_email", record);
	}
    public Long findComplexCou(){
    	return Db.queryLong("select count(*) from v_complexRate as a where a.cou >1 ");
    }
    public Long findComplex(){
    	return Db.queryLong("select count(*) from v_complexRate as a ");
    }
    //订单金额月报表
    //查询月份、年份集合
    public List findOrderNianList(){
    	return Db.query("SELECT b.nian FROM v_orderAmount AS b GROUP BY b.nian,b.yue ORDER BY b.nian,b.yue asc");
    }
    public List findOrderMonthList(){
    	return Db.query("SELECT b.yue FROM v_orderAmount AS b GROUP BY b.nian,b.yue ORDER BY b.nian,b.yue asc");
    }
    public List findOrderZongList(){
    	return Db.query("SELECT SUM(b.price) AS zong FROM v_orderAmount AS b GROUP BY b.nian,b.yue ORDER BY b.nian,b.yue asc");
    }
    
    /**
     * 线上课程复购率报表
     * @param beginTime
     * @param endTime
     * @return
     */
    public List findRateCountList(String beginTime,String endTime){
    	String sql = "select a.c from (select mobile,count(mobile) as c from v_offline_order where 1=1 and newtype=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and update_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and update_at <= '"+endTime+"' ";
				}
				sql+=" group by mobile order by count(mobile) desc) as a group by a.c";
    	return Db.query(sql);
    }
    //查询每个购买次数对应的人数
    public List findRateNumberList(String beginTime,String endTime){
    	String sql = "select count(a.c) from (select mobile,count(mobile) as c from v_offline_order where 1=1 and newtype=1 ";
		    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
					sql += " and update_at >= '"+beginTime+"' ";
				}
				if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
					sql += " and update_at <= '"+endTime+"' ";
				}
				sql+=" group by mobile order by count(mobile) desc) as a group by a.c";
    	return Db.query(sql);
    }
    
    //查询复购率的分子（复购人数）
    public List findRateCou(String beginTime,String endTime){
    	String sql = "select a.c from (select mobile,count(mobile) as c from v_offline_order where 1=1 and newtype=1 ";
		if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
			sql += " and update_at >= '"+beginTime+"' ";
		}
		if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
			sql += " and update_at <= '"+endTime+"' ";
		}
		sql+=" group by mobile order by count(mobile) desc) as a where a.c>=2";
    	return Db.query(sql);
    }
  //查询复购率的分母（订单人数）
    public List findRate(String beginTime,String endTime){
    	String sql = "select a.c from (select mobile,count(mobile) as c from v_offline_order where 1=1 and newtype=1 ";
    	if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
			sql += " and update_at >= '"+beginTime+"' ";
		}
		if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
			sql += " and update_at <= '"+endTime+"' ";
		}
		sql+=" group by mobile order by count(mobile) desc) as a where a.c<2";
		return Db.query(sql);
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////                     线上课程每日营业数据报表           /////////////////////////////////////////////// 
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
	 * @Title: findWebOnlineOrder 
	 * @Description: (查询Web渠道) 
	 * @param memberId
	 * @param type
	 * @return
	 */
	public List<Record> findWebOnlineOrder(String beginTime,String endTime){
		String sql = "select * from v_offline_order where newtype=1 and update_at>='"+beginTime+"' and update_at<='"+endTime+"' and web=0";
		return Db.find(sql);
	}
	public List<Record> findAppOnlineOrder(String beginTime,String endTime){
		String sql = "select * from v_offline_order where newtype=1 and update_at>='"+beginTime+"' and update_at<='"+endTime+"' and web=1";
		return Db.find(sql);
	}
	public List<Record> findAppOnlineOrderByFrom(String beginTime,String endTime){
		String sql = "select * from v_offline_order where newtype=1 and update_at>='"+beginTime+"' and update_at<='"+endTime+"' and from1!='0' group by from1";
		return Db.find(sql);
	}
	public List<Record> findOrderCountByFrom1(String beginTime,String endTime,String from1,String courseName){
		String sql = "select count(from1) as cou,course_name,sum(price) as sprice,price from v_offline_order where newtype=1 and update_at>='"+beginTime+"' and update_at<='"+endTime+"' and from1='"+from1+"' and course_name='"+courseName+"' group by from1,course_name";
		return Db.find(sql);
	}
	public List<Record> findOrderAllCountByFrom1(String from1,String courseName,String endTime){
		String sql = "select count(from1) as cou,course_name,sum(price) as sprice,price from v_offline_order where newtype=1 and from1='"+from1+"' and course_name='"+courseName+"' ";
		if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
			sql += " and update_at <= '"+endTime+"' ";
		}
		sql +=" group by from1";
		return Db.find(sql);
	}
	public List<Record> findSpreadByFrom1(String from1){
		String sql = "select promotion_party,cooperation_mode,course_name from t_course_spread where status=1 and from1='"+from1+"'";
		return Db.find(sql);
	}
	//唤醒App人数
	public List<Record> findAwakenList(String beginTime,String endTime){
		String sql = "select employee_id,count(employee_id) from t_business_login_details where login_type=1 and employee_id!=33";
		if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
			sql += " and login_time >= '"+beginTime+"' ";
		}
		if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
			sql += " and login_time <= '"+endTime+"' ";
		}
		sql+=" group by employee_id";
		return Db.find(sql);
	}
	//学习人数
	public List<Record> findStudyList(String beginTime,String endTime){
		String sql = "select employee_id,count(employee_id) from t_business_study where employee_id!=33 ";
		if(!StringUtils.isNullOrEmpty(beginTime) && !("null").equals(beginTime) && beginTime!=null){
			sql += " and create_at >= '"+beginTime+"' ";
		}
		if(!StringUtils.isNullOrEmpty(endTime) && !("null").equals(endTime) && endTime!=null){
			sql += " and create_at <= '"+endTime+"' ";
		}
		sql+=" group by employee_id";
		return Db.find(sql);
	}
	/**
	 * @Title: findBookSaleOrderCount 
	 * @Description: (查询书籍购买订单) 
	 * @return
	 */
	public Long findBookSaleOrderCount(String name,String price,String beginTime,String endTime,String mobile,String code){
		if(StringUtils.isNullOrEmpty(name) || "null".equals(name)){
			name="";
		}
		if(StringUtils.isNullOrEmpty(price) || "null".equals(price)){
			price="";
		}
		Double p = Double.valueOf(StringUtils.isNullOrEmpty(price) ? "-1" : price);
		String sql = "select count(*) from t_course_web_order a join t_course_web_order_user b on a.id=b.order_id join t_course c on c.id=a.course_id join t_course_offline d on d.course_id=c.id where a.status=1 and b.status=1 and c.course_name like ?  and d.newtype = 3 ";
		if(p != -1){
			sql += " and a.price = " + p;
		}
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql += " and a.create_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and a.create_at <='"+endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and b.mobile = '"+mobile+"'";
		}
		if(!StringUtils.isNullOrEmpty(code)){
			sql += " and a.order_code like '%"+code+"%'";
		}
		return Db.queryLong(sql,"%"+name+"%");
	}
	public List<Record> findBookSaleOrder(int page,String name,String price,String beginTime,String endTime,String mobile,String code){
		if(StringUtils.isNullOrEmpty(price) || "null".equals(price)){
			price="";
		}
		if(StringUtils.isNullOrEmpty(name) || "null".equals(name)){
			name="";
		}
		Double p = Double.valueOf(StringUtils.isNullOrEmpty(price) ? "-1" : price);
		String sql = "from t_course_web_order a join t_course_web_order_user b on a.id=b.order_id join t_course c on c.id=a.course_id join t_course_offline d on d.course_id=c.id where a.status=1 and b.status=1 and d.newtype = 3 ";
		if(p != -1){
			sql += " and a.price = " + p;
		}
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql += " and a.create_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql+=" and c.course_name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and a.create_at <='"+endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and b.mobile = '"+mobile+"'";
		}
		if(!StringUtils.isNullOrEmpty(code)){
			sql += " and a.order_code like '%"+code+"%'";
		}
		sql +=" order by a.id desc ";
		return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.order_code as code,a.number,a.price,c.course_name,a.create_at,a.job,b.work,b.mobile,b.name,b.province,b.city,a.address,b.content,a.memo,a.handle_status,a.courier_number ", sql).getList();
	}
	
	public List<Record> findBookSaleOrderForExport(String name,String price,String beginTime,String endTime,String mobile){
		if(StringUtils.isNullOrEmpty(price) || "null".equals(price)){
			price="";
		}
		if(StringUtils.isNullOrEmpty(name) || "null".equals(name)){
			name="";
		}
		Double p = Double.valueOf(StringUtils.isNullOrEmpty(price) ? "-1" : price);
		String sql = "from t_course_web_order a join t_course_web_order_user b on a.id=b.order_id join t_course c on c.id=a.course_id join t_course_offline d on d.course_id=c.id where a.status=1 and b.status=1 and d.newtype = 3 ";
		if(p != -1){
			sql += " and a.price = " + p;
		}
		if(!StringUtils.isNullOrEmpty(beginTime)){
			sql += " and a.create_at >= '"+beginTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql+=" and c.course_name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(endTime)){
			sql += " and a.create_at <='"+endTime+"'";
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and b.mobile = '"+mobile+"'";
		}
		sql +=" order by a.id desc ";
		return Db.find("select a.id,a.order_code as code,a.number,a.price,c.course_name,a.create_at,a.job,b.work,b.mobile,b.name,b.province,b.city,a.address,b.content,a.invoice_name,a.invoice_mobile,a.invoice_address,a.invoice_title,a.invoice_type,a.invoice_remarks,a.memo,a.handle_status,a.courier_number "+sql);
	}
	 /**
     * 根据订单号查询书籍销售订单
     * @param code
     * @return
     */
    public List<Record> findBookOrderByCode(String code){
    	String sql = "select order_code as code,memo,courier_number as courierNumber,handle_status as handleStatus from t_course_web_order where status=1 and order_code='"+code+"'";
    	return Db.find(sql);
    }
}
