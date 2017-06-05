package cn.ichazuo.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.glassfish.jersey.model.internal.RankedComparator.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.BookExportModel;
import cn.ichazuo.common.utils.ChuanglanSMS;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.ExportExcel;
import cn.ichazuo.common.utils.ExportModel;
import cn.ichazuo.common.utils.ExportModel2;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Member;
import cn.ichazuo.model.OnlineCourse;
import cn.ichazuo.service.InfoService;
import cn.ichazuo.service.MemberService;
import cn.ichazuo.service.OrderService;
import cn.ichazuo.service.TeacherService;
import cn.jpush.api.utils.Base64;

/**
 * @ClassName: OrderController
 * @Description: (订单Controller)
 * @author ZhaoXu
 * @date 2015年9月6日 下午1:36:13
 * @version V1.0
 */
public class OrderController extends BaseController {
	private OrderService orderService = Duang.duang(OrderService.class);
	private MemberService memberService = Duang.duang(MemberService.class);
	private TeacherService teacherService = Duang.duang(TeacherService.class);
	public void index() {
		int newtype = 0;
		setAttr("newtype", newtype);
		String price = getPara("price");
		String name = getPara("name","");
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		String code = getPara("code");
		String mobile = getPara("mobile");
		Integer page = getParaToInt("page", 1);
		if("null".equals(price) || price==null){
			price = "";
		}
		if("null".equals(name) || name==null){
			name = "";
		}
		if("null".equals(endTime) || endTime==null){
			endTime = "";
		}
		if("null".equals(code) || code==null){
			code = "";
		}
		if("null".equals(beginTime) || beginTime==null){
			beginTime = "";
		}
		if("null".equals(mobile) || mobile==null){
			mobile = "";
		}
		long count = orderService.findWebOrderCount(name,price,newtype,beginTime,endTime,mobile,code);

		List<Record> list = orderService.findWebOrder(page,name,price,newtype,beginTime,endTime,mobile,code);
		list.forEach(info -> {
			List<Record> l = orderService.findWebAllUserList(info.getLong("id"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < l.size() && i < 3; i++) {
				sb.append(l.get(i).getStr("name")).append(",");
			}
			String ab = StringUtils.removeEndChar(sb.toString(), ',');
			if (l.size() > 3) {
				ab += "等...";
			}
			info.set("names", ab);
		});
		setAttr("list", list);
		setAttr("price", price).setAttr("name", name).setAttr("beginTime", beginTime).setAttr("endTime", endTime);
		setAttr("page", new Page(page, count, "/admin/order?name="+name));
		render("order.jsp");
	}
	
	public void onlineNew(){
		int newtype = 1;
		setAttr("newtype", newtype);
		String price = getPara("price");
		String name = getPara("name");
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		String mobile = getPara("mobile");
		String code = getPara("code");
		if("null".equals(price) || price==null){
			price = "";
		}
		if("null".equals(name) || name==null){
			name = "";
		}
		if("null".equals(endTime) || endTime==null){
			endTime = "";
		}
		if("null".equals(code) || code==null){
			code = "";
		}
		if("null".equals(beginTime) || beginTime==null){
			beginTime = "";
		}
		if("null".equals(mobile) || mobile==null){
			mobile = "";
		}
		Integer page = getParaToInt("page", 1);
		long count = orderService.findWebOrderCount(name,price,newtype,beginTime,endTime,mobile,code);
		//好多课App注册用户数
		long employeeRegister = orderService.findEmployeeRegisterCount(beginTime,endTime);

		List<Record> list = orderService.findWebOrder(page,name,price,newtype,beginTime,endTime,mobile,code);
//		list.forEach(info -> {
//			List<Record> l = orderService.findWebAllUserList(info.getLong("id"));
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < l.size() && i < 3; i++) {
//				sb.append(l.get(i).getStr("name")).append(",");
//			}
//			String ab = StringUtils.removeEndChar(sb.toString(), ',');
//			if (l.size() > 3) {
//				ab += "等...";
//			}
//			info.set("names", ab);
//		});
		setAttr("list", list);
		setAttr("price", price);
		setAttr("employeeRegister", employeeRegister);
		setAttr("name", name);
		setAttr("beginTime", beginTime);
		setAttr("endTime", endTime);
		setAttr("page", new Page(page, count, "/admin/order/onlineNew?s=1&price="+price+"&name="+name+"&beginTime="+beginTime+"&endTime="+endTime+"&code="+code+"&mobile="+mobile));
		render("order.jsp");
	}
	public void export() {
		try {
			String price = getPara("price");
			String name = getPara("name");
			String beginTime = getPara("beginTime");
			String endTime = getPara("endTime");
			String mobile = getPara("mobile");
			List<Record> list = orderService.findWebOrderExport(name,price,1,beginTime,endTime,mobile);
//			List<Record> list = courseService.findOrder(id);
			List<ExportModel2> models = new ArrayList<>();

			list.forEach(info -> {
				if(info.getLong("web")==0){
					//查询课程讲师
					String teachers = "";
					String id = info.getStr("teacherId");
					String arr[] = id.split(",");
					for(int j=0;j<arr.length;j++){
						Member m = memberService.findMemberById(Long.valueOf(arr[j]));
						if(m != null){
							teachers += m.getStr("nick_name");
						}
						if(j == 1){
							teachers = StringUtils.removeEndChar(teachers, ',');
							if(arr.length > 1){
								teachers = "众老师";
							}
							break;
						}
					}
					//在每条记录上添加讲师姓名
					info.set("teacherId", teachers);
				}else if(info.getLong("web")==1){
					//查询课程讲师
					String teachers = "";
					String id = info.getStr("teacherId");
					String arr[] = id.split(",");
					for(int j=0;j<arr.length;j++){
						Record m = teacherService.findTeacherById(Long.valueOf(arr[j]));
						if(m != null){
							teachers += m.getStr("name");
						}
						if(j == 1){
							teachers = StringUtils.removeEndChar(teachers, ',');
							if(arr.length > 1){
								teachers = "众老师";
							}
							break;
						}
					}
					//在每条记录上添加讲师姓名
					info.set("teacherId", teachers);
				}else{
					//在每条记录上添加讲师姓名
					info.set("teacherId", info.getStr("teacherId"));
				}
				
				ExportModel2 model = new ExportModel2(info);

				models.add(model);
			});

			ExportExcel.exportExcel2("courseExport.xls", models, getResponse());
			renderFile("");
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	public void users() {
		Integer page = getParaToInt("page", 1);
		Long courseId = getParaToLong("courseId", 0L);
		if (NumberUtils.isNullOrZero(courseId)) {
			redirect("/order");
			return;
		}
		List<Record> list = orderService.findWebUserList(page, courseId);
//		list.forEach(info -> {
//			info.set("content1", StringUtils.subString(info.getStr("content")));
//		});
		long count = orderService.findWebUserCount(courseId);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/order/users?courseId=" + courseId));
		render("orderuser.jsp");
	}
	
	@Deprecated
	public void online(){
		String name = getPara("name","");
		int page = getParaToInt("page", 1);
		long count = orderService.findWebOnlineCourseOrderCount(name);

		List<Record> list = orderService.findWebOnlineCourseOrder(page,name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/order/online?name="+name));
		render("online.jsp");
	}
	//更新历史订单加入省份和城市
	public void update() {
		List<Record> list = orderService.findAllOrders();
		list.forEach(info -> {
//			List<Record> l = orderService.request(info.getStr("mobile"));
			//根据手机号获取归属地
			String str = null;
			String province="";
			String city="";
			JSONArray jsonArray = null;
			str = "[" +orderService.request(info.getStr("mobile")) + "]";
			jsonArray = new JSONArray(str);
			int errNumResult = (int) jsonArray.getJSONObject(0).get("errNum");
			if(errNumResult == 0){
				org.json.JSONObject jsonresult = (org.json.JSONObject) jsonArray.getJSONObject(0).get("retData");
				province = jsonresult.getString("province");
				city = jsonresult.getString("city");
			}
			Long id = info.getLong("id");
			//将查询出来的省份和城市更新到数据库
			Record record = new Record();
			record.set("city", city);
			record.set("province", province);
			record.set("id", id);
			orderService.update(record);
		});
	}
	
	//备注书籍订单
	public void memoBookOrder(){
		//书籍订单号
		String code = getPara("code");
		com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
		if(StringUtils.isNullOrEmpty(code)){
			renderJson(error("订单号错误"));
			return;
		}
		try{
			//根据订单号查询书籍信息
			List<Record> orderList =orderService.findBookOrderByCode(code);
			if(orderList.size()<1){
				renderJson(error("无效的订单"));
				return;
			}
			//将结果放入Json对象 返回到页面
			obj.put("order", orderList);
			renderJson(obj);
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常，请联系管理员"));
		}
	}
	public void jiniu(){
		String no = getPara("no");
		long count = orderService.findJiNiuWebOnlineCourseOrderCount(no);
        renderJson(ok("房间号为"+no+"的直播课程当前报名人数为："+String.valueOf(count)));
	}
	// 修改线下课程播放状态
	public void shenhe() {
		String code = getPara("code");
		String status = getPara("status");
//		OnlineCourse online = courseService.findOnlineByCourseId(id);
		if (code == null) {
			renderJson(error("参数错误"));
			return;
		}
		try {
			Record record = new Record();
			record.set("id", code);
			record.set("review_status", status);
			Record record1 = new Record();
			Record record2 = new Record();
			//根据订单Id查询报名时填写的手机号和姓名、邮箱
			Record r = orderService.findCourseMember(code).get(0);
			int isGift = r.getInt("is_gift");
			int reviewStatus =  r.getInt("review_status");
			if(reviewStatus!=0){
				renderJson(error("已执行审核，请勿重复操作！"));
				return;
			}
			//判断是否为审核通过
			if(("1").equals(status)){
				//修改审核状态
				orderService.updateOrderReviewStatus(record);
				Record r1 = orderService.findMessageByCourseId(r.getLong("course_id").toString()).get(0);
				Record r2 = orderService.findEmailByCourseId(r.getLong("course_id").toString()).get(0);
				String str = r1.getStr("message");
				String str3 = r1.getStr("message3");
				String email = r2.getStr("email");
				String email2 = r2.getStr("email2");
				//如果赠送的发送赠送审核成功短信
				if(isGift==1){
					if(str3!="" && !("").equals(str3) && !StringUtils.isNullOrEmpty(r.getStr("mobile")) && r.getStr("mobile")!="" && !("").equals(r.getStr("mobile"))){
						//发送短信
						//判断该学员是否发送了该课程的短信
//						List<Record> list = orderService.findMessageReportList(r.getStr("mobile"),r.getLong("course_id").toString()); 
//						if(list.size()<1){
							orderService.sendMessage(r.getStr("mobile"),str3.replace("#name#", r.getStr("name")).replace("#mobile#", r.getStr("mobile")),r.getLong("course_id"));
//						}
					}
					if(email2!="" && !("").equals(email2) && !StringUtils.isNullOrEmpty(r.getStr("email")) && r.getStr("email")!="" && !("").equals(r.getStr("email"))){
						//判断该邮件是否发送了该课程的邮件
//						List<Record> list = orderService.findEmailReportList(r.getStr("email"),r.getLong("course_id").toString()); 
//						if(list.size()<1){
							sendEmail(r.getStr("email"),"审核通知",email2.replace("#name#", r.getStr("name")));
//							Record emailR = new Record();
//							emailR.set("email", r.getStr("email"));
//							emailR.set("content", email2.replace("#name#", r.getStr("name")));
//							emailR.set("create_at", new Date());
//							emailR.set("is_gift", 1);
//							emailR.set("course_id", r.getLong("course_id").toString());
//							//保存邮件发送记录到数据库
//							orderService.saveEmail(emailR);
//						}
					}
				}else{
					if(str!="" && !("").equals(str) && !StringUtils.isNullOrEmpty(r.getStr("mobile")) && r.getStr("mobile")!="" && !("").equals(r.getStr("mobile"))){
						//发送短信
//						List<Record> list = orderService.findMessageReportList(r.getStr("mobile"),r.getLong("course_id").toString()); 
//						if(list.size()<1){
							orderService.sendMessage(r.getStr("mobile"),str.replace("#name#", r.getStr("name")).replace("#mobile#", r.getStr("mobile")),r.getLong("course_id"));
//						}
					}
					if(email!="" && !("").equals(email) && !StringUtils.isNullOrEmpty(r.getStr("email")) && r.getStr("email")!="" && !("").equals(r.getStr("email"))){
//						//判断该邮件是否发送了该课程的邮件
//						List<Record> list = orderService.findEmailReportList(r.getStr("email"),r.getLong("course_id").toString()); 
//						if(list.size()<1){
							sendEmail(r.getStr("email"),"审核通知",email.replace("#name#", r.getStr("name")));
//							Record emailR = new Record();
//							emailR.set("email", r.getStr("email"));
//							emailR.set("content", email.replace("#name#", r.getStr("name")));
//							emailR.set("create_at", new Date());
//							emailR.set("is_gift", 1);
//							emailR.set("course_id", r.getLong("course_id").toString());
//							//保存邮件发送记录到数据库
//							orderService.saveEmail(emailR);
//						}
					}
				}
				
				//发送邮件
//				sendEmail("");
				renderJson(ok());
			}else if(("2").equals(status)){
				Record r1 = orderService.findMessageByCourseId1(r.getLong("course_id").toString()).get(0);
				Record r2 = orderService.findEmailByCourseId1(r.getLong("course_id").toString()).get(0);
				String str = r1.getStr("message1");
				String str4 = r1.getStr("message4");
				String email = r2.getStr("email1");
				String email3 = r2.getStr("email3");
				String refund= "";
				Record newr = new Record();
				Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
				//判断订单号中是否包含中文，如果包含中文不做退款操作
		        Matcher m = p.matcher(r.getStr("order_code"));
		        if (m.find()) {
		        	newr.set("id", code);
					newr.set("refund_result", "非自平台订单，无法退款");
					refund = "退款成功";
		        }else{
		        	//审核不通过自动退费
					refund = wechatRefund1(r.getStr("order_code"),r.getBigDecimal("price"),r.getBigDecimal("price"));
					newr.set("id", code);
					newr.set("refund_result", refund);
		        }
				//退款
				if(!"退款成功".equals(refund)){
					//更新退款状态
					orderService.updateOrderRefundResult(newr);
					renderJson(error("退款失败，审核操作不成功，原因："+refund));
				}else{
					//退款成功后修改订单状态
//					newr.set("status", 2);
					newr.set("review_status", 2);
					orderService.updateOrderRefundResult(newr);
					//发送短信
					//如果赠送的发送赠送审核成功短信
					if(isGift==1){
						if(str4!="" && !("").equals(str4) && !StringUtils.isNullOrEmpty(r.getStr("mobile")) && r.getStr("mobile")!="" && !("").equals(r.getStr("mobile"))){
							orderService.sendMessage(r.getStr("mobile"),str4.replace("#name#", r.getStr("name")).replace("#mobile#", r.getStr("mobile")),r.getLong("course_id"));
						}
						//发送邮件
						if(email3!="" && !("").equals(email3) && !StringUtils.isNullOrEmpty(r.getStr("email")) && r.getStr("email")!="" && !("").equals(r.getStr("email"))){
							//判断该邮件是否发送了该课程的邮件
//							List<Record> list = orderService.findMessageReportList(r.getStr("email"),r.getLong("course_id").toString()); 
//							if(list.size()<1){
								sendEmail(r.getStr("email"),"审核通知",email3.replace("#name#", r.getStr("name")));
//								Record emailR = new Record();
//								emailR.set("email", r.getStr("email"));
//								emailR.set("content", email3.replace("#name#", r.getStr("name")));
//								emailR.set("create_at", new Date());
//								emailR.set("is_gift", 1);
//								emailR.set("course_id", r.getLong("course_id").toString());
//								//保存邮件发送记录到数据库
//								orderService.saveEmail(emailR);
//							}
						}
					}else{
						if(str!="" && !("").equals(str) && !StringUtils.isNullOrEmpty(r.getStr("mobile")) && r.getStr("mobile")!="" && !("").equals(r.getStr("mobile"))){
							orderService.sendMessage(r.getStr("mobile"),str.replace("#name#", r.getStr("name")).replace("#mobile#", r.getStr("mobile")),r.getLong("course_id"));
						}
						//发送邮件
						if(email!="" && !("").equals(email) && !StringUtils.isNullOrEmpty(r.getStr("email")) && r.getStr("email")!="" && !("").equals(r.getStr("email"))){
							sendEmail(r.getStr("email"),"审核通知",email);
						}
					}
					
//					sendEmail("");
					renderJson(ok());
				}
			}else{
				renderJson(ok());
			}
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统错误"));
			}

		}
	/**
     * 发送邮件
     */
	public void sendEmail(String tomail,String title,String content){
		try {
			PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        String param="tomail="+tomail+"&title="+title+"&content="+URLEncoder.encode(content, "utf-8");
	        try {
	            URL realUrl = new URL("http://sslapi.chazuomba.com/sendmail.php");
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 退款函数，该方法可以对曾经部分退款的订单进行再次退款
	 * @param out_trade_no 商户订单号
	 * @param total_fee1 退款对应的订单的总金额（以“元”为单位）
	 * @param refund_fee1 计划退款的金额（以“元”为单位）
	 * @return
	 */
	public static String wechatRefund1(String out_trade_no,BigDecimal total_fee1,BigDecimal refund_fee1){
		String out_refund_no = UUID.randomUUID().toString().substring(0, 32);// 退款单号，随机生成 ，但长度应该跟文档一样（32位）(卖家信息校验不一致，请核实后再试)
		int total_fee =  (int)(total_fee1.doubleValue()*100);//订单的总金额,以分为单位（填错了貌似提示：同一个out_refund_no退款金额要一致）
		int refund_fee = (int)(total_fee1.doubleValue()*100);;// 退款金额，以分为单位（填错了貌似提示：同一个out_refund_no退款金额要一致）
		String nonce_str = CodeUtils.getUUID();// 随机字符串// 随机字符串
		//微信公众平台文档：“基本配置”--》“开发者ID”
		String appid ="wxa3069b403f0a23af";
		//微信公众平台文档：“基本配置”--》“开发者ID”
		String appsecret = "80bc66a37943bad3ae60aa33f2bec2fe";
		//商户号
		//微信公众平台文档：“微信支付”--》“商户信息”--》“商户号”，将该值赋值给partner
		String mch_id = "1401360402";
		String op_user_id = mch_id;//就是MCHID
		//微信公众平台："微信支付"--》“商户信息”--》“微信支付商户平台”（登录）--》“API安全”--》“API密钥”--“设置密钥”（设置之后的那个值就是partnerkey，32位）
		String partnerkey = "5df41cd6bbd63cf58da9bab43c86610d";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee+"");
		packageParams.put("refund_fee", refund_fee+"");
		packageParams.put("op_user_id", op_user_id);
	
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + 
				"<appid>" + appid + "</appid>" + 
				"<mch_id>" + mch_id + "</mch_id>" + 
				"<nonce_str>" + nonce_str + "</nonce_str>" + 
				"<sign><![CDATA[" + sign + "]]></sign>"	+ 
				"<out_trade_no>" + out_trade_no + "</out_trade_no>"	+ 
				"<out_refund_no>" + out_refund_no + "</out_refund_no>" + 
				"<total_fee>" + total_fee + "</total_fee>" + 
				"<refund_fee>" + refund_fee + "</refund_fee>" + 
				"<op_user_id>" + op_user_id + "</op_user_id>" + 
				"</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			String refundResult= ClientCustomSSL.doRefund(createOrderURL, xml);
			System.out.println("退款返回的结果："+ refundResult);
			return refundResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "退款失败";
	}
	
	/**
	 * 该方法默认全额退款，但如果该订单曾经退款一部分，那么就不可使用该方法
	 * @param out_trade_no 商户订单号
	 * @param total_fee1 总的退款金额（以“元”为单位）
	 */
	public static void wechatRefund(String out_trade_no,double total_fee1){
		
//		wechatRefund1(out_trade_no,total_fee1,total_fee1);
	}
	/**
	 * @Title: writeJSONString 
	 * @Description: (输出json字符串) 
	 * @param json
	 */
	protected void writeJSONString(JSON json){
		StringUtils.writeJSONString(json);
	}
	
	public void CreateImage() throws IOException {  
	        int width = 100;     
	        int height = 100;     
	        String s = "你好";     
	             
	        File file = new File("/Users/tengxin/Pictures/image.jpg");     
	             
	        Font font = new Font("Serif", Font.BOLD, 10);     
	        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     
	        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
	        g2.setBackground(Color.WHITE);     
	        g2.clearRect(0, 0, width, height);     
	        g2.setPaint(Color.RED);     
	             
	        FontRenderContext context = g2.getFontRenderContext();     
	        Rectangle2D bounds = font.getStringBounds(s, context);     
	        double x = (width - bounds.getWidth()) / 2;     
	        double y = (height - bounds.getHeight()) / 2;     
	        double ascent = -bounds.getY();     
	        double baseY = y + ascent;     
	             
	        g2.drawString(s, (int)x, (int)baseY);     
	             
	        ImageIO.write(bi, "jpg", file);      
	}  
	
	
	// 修改线下课程播放状态
		public void toEmail() {
			String courseId = getPara("courseId");
			if (courseId == null) {
				renderJson(error("参数错误"));
				return;
			}
			List<Record> list = orderService.findCourseMember1(courseId);
			Record r1 = orderService.findEmailByCourseId(courseId).get(0);
			StringBuffer errStr = new StringBuffer();
			for(Record r : list){
				String email = r1.getStr("email4");
//				//发送邮件
				if(email!="" && !("").equals(email) && !StringUtils.isNullOrEmpty(r.getStr("email")) && r.getStr("email")!="" && !("").equals(r.getStr("email"))){
					sendEmail(r.getStr("email"),"结业证书",email.replace("#name#", r.getStr("name")));
//					sendEmail("2242336488@qq.com","结业证书",email.replace("#name#", "李东阳"));
				}
			}
			renderJson(ok());
		}
		/**
		 * 书籍预售订单
		 */
		public void bookOrder(){
			int newtype = 3;
			setAttr("newtype", newtype);
			String price = getPara("price");
			String name = getPara("name");
			String beginTime = getPara("beginTime");
			String endTime = getPara("endTime");
			String mobile = getPara("mobile");
			String code = getPara("code");
			if("null".equals(price) || price==null){
				price = "";
			}
			if("null".equals(name) || name==null){
				name = "";
			}
			if("null".equals(endTime) || endTime==null){
				endTime = "";
			}
			if("null".equals(code) || code==null){
				code = "";
			}
			if("null".equals(beginTime) || beginTime==null){
				beginTime = "";
			}
			if("null".equals(mobile) || mobile==null){
				mobile = "";
			}
			Integer page = getParaToInt("page", 1);
			long count = orderService.findBookSaleOrderCount(name,price,beginTime,endTime,mobile,code);
			List<Record> list = orderService.findBookSaleOrder(page,name,price,beginTime,endTime,mobile,code);
			setAttr("list", list);
			setAttr("price", price);
			setAttr("name", name);
			setAttr("beginTime", beginTime);
			setAttr("endTime", endTime);
			setAttr("page", new Page(page, count, "/admin/order/bookOrder?s=1&price="+price+"&name="+name+"&beginTime="+beginTime+"&endTime="+endTime+"&code="+code+"&mobile="+mobile));
			render("bookOrder.jsp");
		}
		public void exportBookOrder() {
			try {
				String price = getPara("price");
				String name = getPara("name");
				String beginTime = getPara("beginTime");
				String endTime = getPara("endTime");
				String mobile = getPara("mobile");
				List<Record> list = orderService.findBookSaleOrderForExport(name,price,beginTime,endTime,mobile);
//				List<Record> list = courseService.findOrder(id);
				List<BookExportModel> models = new ArrayList<>();

				list.forEach(info -> {
					BookExportModel model = new BookExportModel(info);

					models.add(model);
				});

				ExportExcel.exportBookOrderExcel("书籍订单.xls", models, getResponse());
				renderFile("");
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统异常"));
			}
		}
		
		public void saveMemo() {
			try {
				//老的备注
				String memo = getPara("memo");
				//新添加的备注
				String memo2 = getPara("memo2");
				//订单号
				String id = getPara("code");
				//快递单号
				String fastMail = getPara("fastMail");
				//发货状态
				String handleStatus = getPara("handleStatus");
				String memoResult="";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(!StringUtils.isNullOrEmpty(memo)){
					memoResult =df.format(new Date())+"\r"+memo2+"\n"+memo;
				}else{
					memoResult ="\n"+df.format(new Date())+"\r"+memo2;
				}
				Record record = new Record();
				record.set("id", id);
				record.set("memo", memoResult.replaceAll("&", ""));
				record.set("handle_status", handleStatus);
				record.set("courier_number", fastMail);
				orderService.updateOrderRefundResult(record);
				renderJson(ok("保存成功"));
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统异常"));
			}
		}
}
