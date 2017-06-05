package cn.ichazuo.common.utils;

import java.util.Date;

import com.jfinal.plugin.activerecord.Record;

public class InvoiceExportModel {

	private Date createAt;// 购买时间
	private String courseName; // 课程名称
	private String id; // 价格
	private String nickName; // 下单人昵称
	private String mobile;	//下单人手机
	private String invoiceName;// 发票收件人姓名
	private String invoiceMobile;	//发票收件人电话
	private String invoiceType;	//发票类型（咨询费、服务费）
	private String invoiceTitle;	//发票抬头
	private String invoiceAddress;	//发票收货地址
	private String invoiceRemarks;//发票备注
	private String invoiceStatus;//发票状态：1、待处理，2、已处理
	private double price;
	private Date beginTime;
	
	public InvoiceExportModel(Record r){
		this.createAt = r.getDate("create_at");
		this.courseName = r.getStr("course_name");
		this.price = r.getBigDecimal("price").doubleValue();
//		this.id = r.getStr("id");
		this.nickName = r.getStr("nick_name");
		if(1==r.getInt("invoice_status")){
			this.invoiceStatus = "待处理";
		}else if(2==r.getInt("invoice_status")){
			this.invoiceStatus = "已处理";
		}
		this.invoiceName = r.getStr("invoice_name");
		this.invoiceMobile = r.getStr("invoice_mobile");
		this.invoiceType = r.getStr("invoice_type");
		this.invoiceTitle = r.getStr("invoice_title");
		this.mobile = r.getStr("mobile");
		this.invoiceRemarks = r.getStr("invoice_remarks");
		this.invoiceAddress = r.getStr("invoice_address");
		this.beginTime = r.getDate("begin_time");
	}


	public String getCreateAt() {
		return DateUtils.formatDate(createAt);
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getBeginTime() {
		return DateUtils.formatDate(beginTime);
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getInvoiceName() {
		return invoiceName;
	}


	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}


	public String getInvoiceMobile() {
		return invoiceMobile;
	}


	public void setInvoiceMobile(String invoiceMobile) {
		this.invoiceMobile = invoiceMobile;
	}


	public String getInvoiceType() {
		return invoiceType;
	}


	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}


	public String getInvoiceTitle() {
		return invoiceTitle;
	}


	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}


	public String getInvoiceAddress() {
		return invoiceAddress;
	}


	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}


	public String getInvoiceRemarks() {
		return invoiceRemarks;
	}


	public void setInvoiceRemarks(String invoiceRemarks) {
		this.invoiceRemarks = invoiceRemarks;
	}


	public String getInvoiceStatus() {
		return invoiceStatus;
	}


	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
