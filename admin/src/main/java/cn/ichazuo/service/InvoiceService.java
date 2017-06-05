package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.StringUtils;

/**
 * @ClassName: FeedbackService 
 * @Description: (意见反馈Service) 
 * @author ZhaoXu
 * @date 2015年8月12日 上午11:35:29 
 * @version V1.0
 */
public class InvoiceService {
	
	/**
	 * @Title: findInvoiceList 
	 * @Description: (查询订单列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findInvoiceList(int page,String invoiceTitle,String courseName,String name){
//		String sql = " FROM	t_course_offline_join j JOIN t_course_web_order a ON j.order_id = a.id JOIN t_member b ON j.member_id = b.id JOIN t_course c ON a.course_id = c.id WHERE j.`status` = 1 AND a.`status` = 1 AND b.`status` =1 AND a.invoice_type is not null AND LENGTH(trim(a.invoice_type))>1 ";
		String sql = " From v_offline_invoice where 1=1";
		if(!StringUtils.isNullOrEmpty(courseName)){
			sql += " and course_name like '%" + courseName+"%'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and nick_name like '%" + name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(invoiceTitle)){
			sql += " and invoice_title like '%" + invoiceTitle+"%'";
		}
		sql +=" order by invoice_status,invoice_title,create_at asc";
//		System.out.println(sql);
		return Db.paginate(page, Result.PAGE_COUNT, "SELECT create_at,course_name,id,nick_name,mobile,invoice_name,invoice_mobile,invoice_type,invoice_title,invoice_address,invoice_remarks,invoice_status,price,begin_time ",sql).getList();
	}
	
	/**
	 * @Title: findFeedBackCount 
	 * @Description: (查询意见反馈总数) 
	 * @return
	 */
	public Long findInvoiceCount(String invoiceTitle,String courseName,String name){
		String sql = "FROM	t_course_offline_join j JOIN t_course_web_order a ON j.order_id = a.id JOIN t_member b ON j.member_id = b.id JOIN t_course c ON a.course_id = c.id WHERE j.`status` = 1 AND a.`status` = 1 AND b.`status` =1 AND a.invoice_type is not null AND LENGTH(trim(a.invoice_type))>1";
		if(!StringUtils.isNullOrEmpty(courseName)){
			sql += " and c.course_name like '%" + courseName+"%'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and b.nick_name like '%" + name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(invoiceTitle)){
			sql += " and a.invoice_title like '%" + invoiceTitle+"%'";
		}
//		System.out.println(sql);
		return Db.queryLong("select count(*) "+sql);
	}
	/**
	 * @Title: findBusinessFeedBackCount 
	 * @Description: (查询企业版APP意见反馈总数) 
	 * @return
	 */
	public void updateInvoice(Long id){
		Db.update("update t_course_web_order set invoice_status = 2 where id = ?", id);
	}
	/**
	 * 导出Excel
	 * @param name
	 * @param price
	 * @param newtype
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<Record> findInvoiceListExport(String invoiceTitle,String courseName,String name){
//		Double p = Double.valueOf(StringUtils.isNullOrEmpty(price) ? "-1" : price);
		String sql = "select * From v_offline_invoice where 1=1";
		if(!StringUtils.isNullOrEmpty(courseName)){
			sql += " and course_name like '%" + courseName+"%'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and nick_name like '%" + name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(invoiceTitle)){
			sql += " and invoice_title like '%" + invoiceTitle+"%'";
		}
		sql +=" order by invoice_status,invoice_title,create_at asc";
		return Db.find(sql);
	}
}
