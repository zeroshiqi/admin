package cn.ichazuo.common.utils;

import java.util.Date;

import com.jfinal.plugin.activerecord.Record;

public class ExportModel2 {

	private String code;// 订单号
	private String number; // 数量
	private String price; // 价格
	private String coursename; // 名称
	private String type;	//购买类别
	private String name;// 姓名
	private String weixin;	//微信
	private String work;	//公司
	private String job;	//职位
	private Date updateTime;	//购买时间
	private String mobile;// 手机号
	private String sex;//性别
	private String address;//大课收票地址
	private String teacherId;//讲师
	private String content;//提问
	private String from1;//推广码

	
	public ExportModel2(Record r){
		this.code = r.getStr("code");
		this.number = r.getStr("number");
		this.price = r.getBigDecimal("price").toString();
		this.coursename = r.getStr("course_name");
		this.updateTime = r.getDate("update_at");
		if("1".equals(r.getStr("type"))){
			this.type = "百度支付";
		}else if("0".equals(r.getStr("type"))){
			this.type = "微信支付";
		}else{
			this.type = "支付宝支付";
		}
		this.name = r.getStr("name");
		this.weixin = r.getStr("weixin");
		this.work = r.getStr("work");
		this.job = r.getStr("job");
		this.mobile = r.getStr("mobile");
		this.sex = r.getStr("sex");
		this.address = r.getStr("address");
		this.teacherId = r.getStr("teacherId");
		this.content = r.getStr("content");
		this.from1 = r.getStr("from1");
	}
	
	
	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getWeixin() {
		return weixin;
	}


	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}


	public String getWork() {
		return work;
	}


	public void setWork(String work) {
		this.work = work;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdateTime() {
		return DateUtils.formatDate(updateTime);
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getFrom1() {
		return from1;
	}


	public void setFrom1(String from1) {
		this.from1 = from1;
	}
	
}
