package cn.ichazuo.common.utils;

import java.util.Date;

import com.jfinal.plugin.activerecord.Record;

public class BookExportModel {

	private String code;// 订单号
	private String number; // 数量
	private String price; // 价格
	private String coursename; // 名称
	private String province;	//省份
	private String city;//城市
	private String name;	//姓名
	private String work;	//公司
	private Date updateTime;	//购买时间
	private String mobile;// 手机号
//	private String sex;//性别
	private String address;//书籍(发票)收货地址
//	private String invoiceName;//发票收件人
	private String content;//购买留言
//	private String invoiceAddress;//发票售票地址
	private String invoiceType;//发票类型
	private String invoiceTitle;//发票抬头
	private String from1;//来源
	private String memo;//备注
	private String courierNumber;//快递单号
	private String handleStatus;//发货状态
	
	public BookExportModel(Record r){
		this.code = r.getStr("code");
		this.number = String.valueOf(r.getInt("number"));
		this.price = r.getBigDecimal("price").toString();
		this.coursename = r.getStr("course_name");
		this.updateTime = r.getDate("create_at");
		this.name = r.getStr("name");
		this.address = r.getStr("address");
		this.work = r.getStr("work");
		this.invoiceTitle = r.getStr("invoice_title");
		this.mobile = r.getStr("mobile");
//		this.sex = r.getStr("sex");
		this.address = r.getStr("address");
		this.invoiceType = r.getStr("invoice_type");
		this.content = r.getStr("content");
		this.from1 = r.getStr("from1");
		this.province = r.getStr("province");
		this.city = r.getStr("city");
		this.memo = r.getStr("memo");
		this.courierNumber = r.getStr("courier_number");
		this.handleStatus = String.valueOf(r.getInt("handle_status"));
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


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getWork() {
		return work;
	}


	public void setWork(String work) {
		this.work = work;
	}


	public String getUpdateTime() {
		return DateUtils.formatDate(updateTime);
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}



	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getInvoiceType() {
		return invoiceType;
	}


	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}


	public String getInvoiceTitle() {
		if(StringUtils.isNullOrEmpty(invoiceTitle)){
			invoiceTitle = "";
		}
		return invoiceTitle;
	}


	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}


	public String getFrom1() {
		return from1;
	}


	public void setFrom1(String from1) {
		this.from1 = from1;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getCourierNumber() {
		return courierNumber;
	}


	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}


	public String getHandleStatus() {
		if(handleStatus.equals("1")){
			return "已发货";
		}else{
			return "未发货";
		}
	}


	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
}
