package cn.ichazuo.common.utils;

import java.util.Date;

import com.jfinal.plugin.activerecord.Record;

public class ExportModel {

	private String code;// 订单号
	private int number; // 数量
	private double price; // 价格
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
	private String email;
	private String reviewStatus;//审核状态
	private String refundResult;//退款状态
	private String content;//提问
	private String isGift;//赠送
	
	public ExportModel(Record r){
		this.code = r.getStr("code");
		this.number = r.getInt("number");
		this.price = r.getBigDecimal("price").doubleValue();
		this.coursename = r.getStr("course_name");
		this.updateTime = r.getDate("create_at");
		this.type = r.getInt("type") == 1 ? "百度支付" : "微信支付";
		this.name = r.getStr("name");
		this.weixin = r.getStr("weixin");
		this.work = r.getStr("work");
		this.job = r.getStr("job");
		this.mobile = r.getStr("mobile");
		this.sex = r.getStr("sex");
		this.address = r.getStr("address");
		this.email = r.getStr("email");
		if(r.getInt("review_status") == 1){
			this.reviewStatus = "审核通过";		
		}else if(r.getInt("review_status") == 2){
			this.reviewStatus = "审核未通过";
		}else{
			this.reviewStatus = "审核中";
		}
		this.refundResult = r.getStr("refund_result");
		this.content = r.getStr("content");
		if(r.getInt("is_gift") == 1){
			this.isGift = "赠送";		
		}else{
			this.isGift = "非赠送";
		}
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
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


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getReviewStatus() {
		return reviewStatus;
	}


	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}


	public String getRefundResult() {
		return refundResult;
	}

	public void setRefundResult(String refundResult) {
		this.refundResult = refundResult;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getIsGift() {
		return isGift;
	}


	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}
	
}
