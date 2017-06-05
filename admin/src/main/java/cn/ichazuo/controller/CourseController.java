package cn.ichazuo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.CodeUtils;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.ExportExcel;
import cn.ichazuo.common.utils.ExportModel;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.OperateImage;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Course;
import cn.ichazuo.model.OfflineCourse;
import cn.ichazuo.model.OfflineCourseImage;
import cn.ichazuo.model.OnlineCourse;
import cn.ichazuo.model.Spread;
import cn.ichazuo.service.BusinessService;
import cn.ichazuo.service.CourseService;
import cn.ichazuo.service.DictItemService;
import cn.ichazuo.service.LogService;
import cn.ichazuo.service.MemberService;
import cn.ichazuo.service.PlayAddressService;

/**
 * @ClassName: CourseController
 * @Description: (courseController)
 * @author ZhaoXu
 * @date 2015年8月2日 下午2:42:35
 * @version V1.0
 */
public class CourseController extends BaseController {

	private CourseService courseService = Duang.duang(CourseService.class);
	private DictItemService dictItemService = Duang.duang(DictItemService.class);
	private PlayAddressService playAddressService = Duang.duang(PlayAddressService.class);
	private MemberService memberService = Duang.duang(MemberService.class);
	private BusinessService businessService = Duang.duang(BusinessService.class);
	private LogService logService = Duang.duang(LogService.class);

	public void index() {
		offline();
	}

	// 编辑内容
	public void editContent() {
		Integer type = getParaToInt("type", 1);
		Long courseId = getParaToLong("id");
		if (NumberUtils.isNullOrZero(courseId)) {
			redirect("/course");
			return;
		}
		String title = courseService.findCourseById(courseId).getStr("course_name");
		String content = courseService.findCourseContent(courseId, type);
		setAttr("courseId", courseId);
		setAttr("content", content);
		setAttr("type", type);
		setAttr("title", title);

		render("content.jsp");
	}

	// 保存内容
	public void saveContent() {
		Integer type = getParaToInt("type", 1);
		Long courseId = getParaToLong("courseId");
		if (NumberUtils.isNullOrZero(courseId)) {
			renderJson(error("参数错误"));
			return;
		}
		String content = getPara("content");
		if (StringUtils.isNullOrEmpty(content)) {
			renderJson(error("参数错误"));
			return;
		}
		try {
			courseService.saveCourseContent(courseId, content, type);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}

	}

	// 修改线下课程播放状态
	public void updateOnlinePlayStatus() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数缺失"));
			return;
		}
		OnlineCourse online = courseService.findOnlineByCourseId(id);
		if (online == null) {
			renderJson(error("参数错误"));
			return;
		}
		try {
			online.set("end_status", online.getInt("end_status") == 0 ? 1 : 0);
			online.set("version", online.getLong("version") + 1);
			online.set("update_at", DateUtils.getNowDate());

			courseService.updateOnlineCourse(online);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统错误"));
		}

	}

	// 课程参加用户
	public void joinMembers() {
		Integer type = getParaToInt("type");
		Integer page = getParaToInt("page", 1);
		Long courseId = getParaToLong("courseId");

		String name = getPara("name", "");
		setAttr("courseId", courseId);
		setAttr("type", type);
		try {
			if(type == 0){
				if (NumberUtils.isNullOrZero(courseId)) {
					redirect("/offline");
					return;
				}
				Long count = memberService.findOfflineJoinMemberCount(courseId, name);
				List<Record> list = memberService.findOfflineJoinMember(courseId, page, name);
				list.forEach(record -> {
					record.set("avatar", super.appenUrl(record.getStr("avatar")));
				});
				setAttr("page", new Page(page, count,
						"/admin/course/joinMembers?type=" + type + "&courseId=" + courseId + "&name=" + name));
				setAttr("list", list);
			 }else{
				 if(NumberUtils.isNullOrZero(courseId)){
				 redirect("/online");
				 return;
				 }
				 Long count = memberService.findOnlineJoinMemberCount(courseId,name);
				 List<Record> list = memberService.findOnlineJoinMember(courseId,page,name);
				 list.forEach(record -> {
				 record.set("avatar", super.appenUrl(record.getStr("avatar")));
				 });
				 setAttr("page", new Page(page, count,
				 "/admin/course/joinMembers?type="+type+"&courseId="+courseId+ "&name=" + name));
				 setAttr("list",list);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			redirect("/offline");
			return;
		}

		render("coursejoin.jsp");
	}
	
	// 删除报名用户
	public void deleteJoin() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数缺失"));
			return;
		}
		try {
			courseService.updateOfflineJoinStatus(id);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	// 跳转上传课件
	public void uploadFile() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			render("online.jsp");
			return;
		}
		setAttr("id", id);
		OnlineCourse online = courseService.findOnlineByCourseId(id);
		setAttr("time", online.getInt("time_length"));
		setAttr("address", online.getStr("play_address"));
		setAttr("pdfAddress", online.getStr("pdf_address"));
		render("uploadvideo.jsp");
	}
	// 跳转上传课件
	public void uploadPDF() {
		Long id = getParaToLong("id", 0L);
		Long page = getParaToLong("page", 0L);
		String name = getPara("name");
		if (NumberUtils.isNullOrZero(id)) {
			render("online.jsp");
			return;
		}
		setAttr("id", id);
		setAttr("name",name);
		setAttr("page", page);
		OnlineCourse online = courseService.findOnlineByCourseId(id);
		setAttr("pdfAddress", online.getStr("pdf_address"));
		render("uploadpdf.jsp");
	}
	// 跳转裁剪图片
	public void cropImage() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			render("online.jsp");
			return;
		}
		setAttr("id", id);
		Course course = courseService.findCourseById(id);
		setAttr("course", course);
		setAttr("newtype", getParaToInt("newtype"));
		render("image.jsp");
	}

	// 跳转课程图片
	public void images() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			render("offline.jsp");
			return;
		}
		setAttr("id", id);
		List<OfflineCourseImage> images = courseService.findImagesByCourseId(id);
		images.forEach(info -> {
			info.set("image_url", super.appenUrl(info.getStr("image_url")));
		});
		setAttr("images", images);
		render("images.jsp");
	}

	// 跳转修改图片
	public void editImage() {
		Long id = getParaToLong("id", 0L);
		Long courseId = getParaToLong("courseId", 0L);
		if (NumberUtils.isNullOrZero(courseId)) {
			// error
			redirect("/admin/offline");
			return;
		}
		if (!NumberUtils.isNullOrZero(id)) {
			OfflineCourseImage image = courseService.findImageById(id);
			setAttr("image", image);
		}
		setAttr("id", id);
		setAttr("courseId", courseId);
		render("editimage.jsp");
	}

	// 修改播放地址
	public void updatePlayAddress() {
		Long id = getParaToLong("id", 0L);
		int time = getParaToInt("time", 0);
//		if (NumberUtils.isNullOrZero(id) || NumberUtils.isNullOrZero(time)) {
//			renderJson(error("参数缺失"));
//			return;
//		}
		OnlineCourse online = courseService.findOnlineByCourseId(id);
		online.set("update_at", DateUtils.getNowDate()).set("version", online.getLong("version") + 1);
		online.set("time_length", time);
		Object uploadFile= getSession().getAttribute("uploadFile");
		if (StringUtils.isNullOrEmpty(getPara("path")) && getSession().getAttribute("uploadFile") == null) {
			renderJson(error("参数缺失"));
			return;
		}
		if (StringUtils.isNullOrEmpty(getPara("path"))) {
			online.set("play_address", getSession().getAttribute("uploadFile").toString());
		} else {
			online.set("play_address", getPara("path"));
		}
		getSession().removeAttribute("uploadFile");
		try {
			courseService.updatePlayAddress(online);

			Course course = courseService.findCourseById(online.getLong("course_id"));
			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 0, online.getLong("id"),
					"用户:" + user.getRealName() + "->上传了课程:" + course.getStr("course_name") + "的课件");
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统错误"));
		}
	}
	
	// 修改课程PDF大纲地址
	public void updatePdfAddress(){
		String id = getPara("courseid");
//		if (NumberUtils.isNullOrZero(id) || NumberUtils.isNullOrZero(time)) {
//			renderJson(error("参数缺失"));
//			return;
//		}
		boolean isHave = false;
		if(getFile("pdf") != null){
			isHave=true;
		}else{
			isHave = false;
			renderJson(error("pdf文件未上传！"));
			return;
		}
		String path="";
		if(isHave){
			UploadFile file = getFile("pdf");
			path = new Upload().uploadPdf(file, "file");
		}
		OnlineCourse online = courseService.findOnlineByCourseId(Long.parseLong(id));
		online.set("update_at", DateUtils.getNowDate()).set("version", online.getLong("version") + 1);
		online.set("pdf_address", path);
		try {
			courseService.updatePlayAddress(online);
			Course course = courseService.findCourseById(online.getLong("course_id"));
			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 0, online.getLong("id"),
					"用户:" + user.getRealName() + "->上传了PDF课程大纲:" + course.getStr("course_name") + "的课件");
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统错误"));
		}
	}
	// 跳转线下课程列表
	public void offline() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		int newtype = 0;
		List<Record> list = courseService.findOfflineCourse(page, name, newtype);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.get("cover")));
			info.set("avatar", appenUrl(info.get("avatar")));
		});
		long count = courseService.findOfflineCourseCount(name, newtype);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/course/offline?s=1"));
		if (PropKit.getBoolean("project.dev")) {
			setAttr("url", PropKit.get("url.test"));
		} else {
			setAttr("url", PropKit.get("url.pro"));
		}
		setAttr("newtype", newtype);
		render("offline.jsp");
	}

	// 跳转线上课程列表
	public void onlineNew() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		int newtype = 1;
		List<Record> list = courseService.findOfflineCourse(page, name, newtype);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.get("cover")));
			info.set("avatar", appenUrl(info.get("avatar")));
		});
		long count = courseService.findOfflineCourseCount(name, newtype);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/course/onlineNew?s=1"));
		if (PropKit.getBoolean("project.dev")) {
			setAttr("url", PropKit.get("url.test"));
		} else {
			setAttr("url", PropKit.get("url.pro"));
		}
		setAttr("newtype", newtype);
		render("offline.jsp");
	}
	
	// 跳转线上课程列表
		public void publicClass() {
			int page = getParaToInt("page", 1);
			String name = getPara("name");
			int newtype = 5;
			List<Record> list = courseService.findOfflineCourse(page, name, newtype);
			list.forEach(info -> {
				info.set("cover", appenUrl(info.get("cover")));
				info.set("avatar", appenUrl(info.get("avatar")));
			});
			long count = courseService.findOfflineCourseCount(name, newtype);
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/course/onlineNew?s=1"));
			if (PropKit.getBoolean("project.dev")) {
				setAttr("url", PropKit.get("url.test"));
			} else {
				setAttr("url", PropKit.get("url.pro"));
			}
			setAttr("newtype", newtype);
			render("offline.jsp");
		}
	// 跳转直播课程列表
	public void living() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		int newtype = 2;
		List<Record> list = courseService.findOfflineCourse(page, name, newtype);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.get("cover")));
			info.set("avatar", appenUrl(info.get("avatar")));
		});
		long count = courseService.findOfflineCourseCount(name, newtype);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/course/offline?s=1"));
		if (PropKit.getBoolean("project.dev")) {
			setAttr("url", PropKit.get("url.test"));
		} else {
			setAttr("url", PropKit.get("url.pro"));
		}
		setAttr("newtype", newtype);
		render("living.jsp");
	}
	
	// 跳转推广信息列表
	public void spread() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		String from1 = getPara("from1");
		List<Record> list = courseService.findSpreadList(page, name,from1);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.get("cover")));
			info.set("avatar", appenUrl(info.get("avatar")));
		});
		long count = courseService.findSpreadCount(name,from1);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/course/spread?s=1"));
		render("spread.jsp");
	}
	// 跳转直播课程列表
	public void bookSale() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		int newtype = 3;
		List<Record> list = courseService.findBookList(page, name);
		list.forEach(info -> {
		info.set("cover", appenUrl(info.get("cover")));
			info.set("avatar", appenUrl(info.get("avatar")));
		});
		long count = courseService.findBookCount(name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/course/bookSale?s=1"));
		if (PropKit.getBoolean("project.dev")) {
			setAttr("url", PropKit.get("url.test"));
		} else {
			setAttr("url", PropKit.get("url.pro"));
		}
		setAttr("newtype", newtype);
		render("book.jsp");
	}
	
	// 跳转线上课程列表
	@Deprecated
	public void online() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		List<Record> list = courseService.findOnlineCourse(page, name);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.get("cover")));
			info.set("avatar", appenUrl(info.get("avatar")));
		});
		long count = courseService.findOnlineCourseCount(name);
		setAttr("list", list);
		setAttr("name", name);
		setAttr("pageNo",page);
		setAttr("page", new Page(page, count, "/admin/course/online?s=1"));
		render("online.jsp");
	}

	public void export() {
		long id = getParaToLong("id");
		try {
			if (NumberUtils.isNullOrZero(id)) {
				renderJson(error("参数缺失!"));
			}
			List<Record> list = courseService.findOrder(id);
			List<ExportModel> models = new ArrayList<>();

			list.forEach(info -> {
				ExportModel model = new ExportModel(info);

				models.add(model);
			});

			ExportExcel.exportExcel("courseExport.xls", models, getResponse());
			renderFile("");
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	// 删除线下课程
	public void deleteOffline() {
		long id = getParaToLong("id");
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数缺失"));
		}
		try {
			Course course = courseService.deleteOfflineCourse(id);

			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 1, course.getLong("id"),
					"用户:" + user.getRealName() + "->删除了线下课程:" + course.getStr("course_name"));
			renderJson(ok());
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}
	}
	// 删除推广渠道
	public void deleteSpread() {
		long id = getParaToLong("id");
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数缺失"));
		}
		try {
			courseService.deleteSpread(id);
			renderJson(ok());
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}
	}	
	// 删除线上课程
	public void deleteOnline() {
		long id = getParaToLong("id");
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数缺失"));
		}
		try {
			Course course = courseService.deleteOnlineCourse(id);

			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 0, course.getLong("id"),
					"用户:" + user.getRealName() + "->删除了线上课程:" + course.getStr("course_name"));
			renderJson(ok());
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}
	}

	// 编辑课程
	public void edit() {
		long id = getParaToLong("id", 0L);
		int newtype = getParaToInt("newtype");
		setAttr("newtype", newtype);
		String content = "";
		setAttr("cityList", courseService.findAllCity());
		setAttr("addressList", playAddressService.findAllAddress());

		if (!NumberUtils.isNullOrZero(id)) {
			Course course = courseService.findCourseById(id);

			course.set("avatar", super.appenUrl(course.getStr("avatar")));
			setAttr("course", course);
			if (course.getInt("type") == 0) {
				OfflineCourse offline = courseService.findOfflineByCourseId(id);
				setAttr("offlineType", dictItemService.findAll("OFFLINECOURSETYPE"));
				String ids = offline.getStr("teacher_id");
				StringBuffer sb = new StringBuffer();
				if(ids!="" && ids!=null && !"".equals(ids)){
					for (String s : ids.split(",")) {
						if (StringUtils.isNullOrEmpty(s)) {
							continue;
						}
						sb.append(memberService.findMemberById(Long.parseLong(s)).getStr("nick_name")).append(",");
					}
				}
				

				content = offline.getStr("course_content");
				setAttr("teacherNames", StringUtils.removeEndChar(sb.toString(), ','));
				setAttr("offline", offline);
				setAttr("content", content);

				render("offlineCourse.jsp");
			} else {
				OnlineCourse online = courseService.findOnlineByCourseId(id);
				setAttr("onlineType", dictItemService.findAll("ONLINECOURSETYPE"));
				LocalTime beginTime = LocalTime
						.parse(DateUtils.formatDate(online.getDate("play_begin_time"), DateUtils.HOUR_MINUTE_SECOND));
				setAttr("startHour", beginTime.getHour());
				setAttr("startMinute", beginTime.getMinute());
				setAttr("online", online);

				render("onlineCourse.jsp");
			}
		} else {
			if (getParaToInt("type") == 0) {
				setAttr("offlineType", dictItemService.findAll("OFFLINECOURSETYPE"));
				render("offlineCourse.jsp");
			} else {
				setAttr("onlineType", dictItemService.findAll("ONLINECOURSETYPE"));
				render("onlineCourse.jsp");
			}

		}
	}

	// 保存课程
	public void save() {
		boolean isHave = false;
		try {
			isHave = getFile("imglive") != null;
		} catch (Exception e) {
			isHave = false;
			if (NumberUtils.isNullOrZero(getParaToLong("courseId", 0L))) {
				renderJson(error("参数缺失!"));
				return;
			}
		}

		boolean qrcodeHave = false;
		try {
			qrcodeHave = getFile("qrcode") != null;
		} catch (Exception e) {
			qrcodeHave = false;
			if (NumberUtils.isNullOrZero(getParaToLong("courseId", 0L))) {
				renderJson(error("请上传二维码!"));
				return;
			}
		}
		// 验证参数
		if (StringUtils.isNullOrEmpty(getPara("courseName")) || getParaToInt("type", -1) == -1
				|| StringUtils.isNullOrEmpty(getPara("beginTime"))) {
			renderJson(error("参数缺失!"));
			return;
		}
		int type = getParaToInt("type");
		if (type == 0) {
//			if (NumberUtils.isNullOrZero(getParaToLong("offlineType", 0L))
//					|| NumberUtils.isNullOrZero(getParaToInt("discount", 0))
//					|| StringUtils.isNullOrEmpty(getPara("price")) || StringUtils.isNullOrEmpty(getPara("courseTime"))
//					|| NumberUtils.isNullOrZero(getParaToLong("city", 0L))
//					|| StringUtils.isNullOrEmpty(getPara("teacher"))
//					|| NumberUtils.isNullOrZero(getParaToInt("studenNumber", 0))
//					|| StringUtils.isNullOrEmpty(getPara("star"))
//					|| StringUtils.isNullOrEmpty(getPara("lon")) || StringUtils.isNullOrEmpty(getPara("lat"))) {
//				renderJson(error("参数缺失!"));
//				return;
//			}
		} else {
//			if (NumberUtils.isNullOrZero(getParaToLong("playAddress", 0L))
//					|| StringUtils.isNullOrEmpty(getPara("courseTeacher")) || getParaToInt("playStrtHour", -1) < 0
//					|| getParaToInt("playStartMinute", -1) < 0 || StringUtils.isNullOrEmpty(getPara("coursePrice"))) {
//				renderJson(error("参数缺失!"));
//				return;
//			}
		}
		Course course = null;
		Long courseId = getParaToLong("courseId", 0L);
		// 判断是否为修改
		if (NumberUtils.isNullOrZero(courseId)) {
			course = new Course();
			course.set("create_at", DateUtils.getNowDate());
			course.set("cover", "");
		} else {
			course = courseService.findCourseById(courseId);
			course.set("update_at", DateUtils.getNowDate()).set("version", course.getLong("version") + 1);
		}
		// 上传图片
		if (isHave) {
			UploadFile file = getFile("imglive");
			if (file.getFile().length() >= 300 * 1024) {
				renderJson(error("图片太大,请选择300k以内的小图!"));
				return;
			}
			String path = new Upload().upload(file, "avatar");
			course.set("avatar", path);
			// course.set("cover", "");
		}
		if (qrcodeHave) {
			UploadFile file = getFile("qrcode");
			String path = new Upload().upload(file, "qrcode");
			course.set("qrcode", path);
		}

		course.set("course_name", getPara("courseName")).set("type", getParaToInt("type"));
		course.set("synopsis", getPara("synopsis", ""));
//		course.set("begin_time", DateUtils.parseDate(getPara("beginTime") + " " + CodeUtils.getRandomInt(23) + ":"
//				+ CodeUtils.getRandomInt(59) + ":" + CodeUtils.getRandomInt(59)));
		course.set("begin_time", getPara("beginTime"));
		course.set("end_time", getPara("endTime"));

		if (type == 0) {
			OfflineCourse offline = null;
			if (NumberUtils.isNullOrZero(courseId)) {
				offline = new OfflineCourse();
				offline.set("create_at", DateUtils.getNowDate());
			} else {
				offline = courseService.findOfflineByCourseId(courseId);
				offline.set("update_at", DateUtils.getNowDate());
				offline.set("version", offline.getLong("version") + 1);
			}
			offline.set("tag", getPara("tag", ""));
			offline.set("offline_type_id", getParaToLong("offlineType")).set("course_content", getPara("courseContent"))
					.set("discount", getParaToInt("discount"));
			offline.set("price", Double.parseDouble(getPara("price"))).set("course_time", getPara("courseTime"))
					.set("city_id", getParaToLong("city"));
			offline.set("teacher_id", getPara("teacher")).set("student_num", getPara("studenNumber"))
					.set("address", getPara("address")).set("lon", Double.valueOf(getPara("lon")));
			offline.set("lat", Double.parseDouble(getPara("lat"))).set("star", Double.parseDouble(getPara("star")));
			offline.set("newtype", getParaToInt("newtype"));
			offline.set("show_address", getPara("showAddress"));
			offline.set("teacher_name", getPara("course-teacher"));
			if(getParaToInt("newtype")==5){
				//课程关联的课程包ID和名称
				offline.set("catalog_id", getParaToLong("typeId1",0L));
				offline.set("catalog_name", getPara("typeName1"));
				//线上课程购买后开通好多课会员期限
				offline.set("expiry_date", getPara("expiryDate1"));
			}else{
				//课程关联的课程包ID和名称
				offline.set("catalog_id", getParaToLong("typeId",0L));
				offline.set("catalog_name", getPara("typeName"));
				//线上课程购买后开通好多课会员期限
				offline.set("expiry_date", getPara("expiryDate"));
			}
			//讲师职位
			offline.set("teacher_job", getPara("teacherjob"));
			//线下课程举办站点
			offline.set("station", getPara("station"));
			
			//报名审核通过通知短信
			offline.set("message", getPara("message"));
			//报名审核未通过通知短信
			offline.set("message1", getPara("message1"));
			//报名线下课成功赠送短信
			offline.set("message2", getPara("message2"));
			//报名线下课成功赠送审核成功短信
			offline.set("message3", getPara("message3"));
			//报名线下课成功赠送审核不通过短信
			offline.set("message4", getPara("message4"));
			//报名审核通过通知邮件
			offline.set("email", getPara("email"));
			//报名审核未通过通知邮件
			offline.set("email1", getPara("email1"));
			//赠送报名审核通过通知邮件
			offline.set("email2", getPara("email2"));
			//赠送报名审核未通过通知邮件
			offline.set("email3", getPara("email3"));
			//线下课程结业证书邮件
			offline.set("email4", getPara("email4"));
			//直播课程直播流（用于查询直播状态）
			offline.set("stream", getPara("stream"));
			//直播房间号
			offline.set("live_room_no", getPara("liveRoomNo"));
			//直播账号Id
			offline.set("uid", getPara("uid"));
			//直播公告
			offline.set("live_notice", getPara("liveNotice"));
			
			try {
				if (NumberUtils.isNullOrZero(courseId)) {
					courseService.saveOfflineCourse(offline, course);
					if(getParaToInt("newtype")==5){
						if(!NumberUtils.isNullOrZero(getParaToLong("typeId1"))){
							//修改公开课关联的线上课
//							courseService.updateCatalogGroup(offline.get("id"), getParaToLong("typeId1"));
						}
					}
					
					LoginUser user = getSessionAttr(PropKit.get("project.session"));
					logService.saveLog(user, 1, course.getLong("id"),
							"用户:" + user.getRealName() + "->添加了线下课程:" + course.getStr("course_name"));
				} else {
					courseService.updateOfflineCourse(offline, course);
					if(getParaToInt("newtype")==5){
						if(!NumberUtils.isNullOrZero(getParaToLong("typeId1"))){
							//修改公开课关联的线上课
//							courseService.updateCatalogGroup(offline.get("id"), getParaToLong("typeId1"));
						}
					}

					LoginUser user = getSessionAttr(PropKit.get("project.session"));
					logService.saveLog(user, 1, course.getLong("id"),
							"用户:" + user.getRealName() + "->修改了线下课程:" + course.getStr("course_name"));
				}

				renderJson(ok());
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统异常"));
			}
		} else {
			OnlineCourse online = null;
			if (NumberUtils.isNullOrZero(courseId)) {
				online = new OnlineCourse();
				online.set("course_content", "");
				online.set("course_ppt", "");
				online.set("course_back", "");
				online.set("create_at", DateUtils.getNowDate());
			} else {
				online = courseService.findOnlineByCourseId(courseId);
				online.set("update_at", DateUtils.getNowDate());
				online.set("version", online.getLong("version") + 1);
			}
			online.set("tag", getPara("courseTag", ""));
			online.set("introduction", getPara("introduction", ""));
			online.set("price", Double.parseDouble(getPara("coursePrice")));
			online.set("play_address_id", getParaToLong("playAddress"));
			int startHour = getParaToInt("playStrtHour", 0);
			int endHour = getParaToInt("playEndHour", 0);
			int startMinute = getParaToInt("playStartMinute", 0);
			int endMinute = getParaToInt("playEndMinute", 0);

			String beginTime = getPara("beginTime") + " " + startHour + ":" + startMinute + ":00";
			String endTime = getPara("beginTime") + " " + endHour + ":" + endMinute + ":00";

			online.set("play_begin_time", DateUtils.parseDate(beginTime));
			online.set("play_end_time", DateUtils.parseDate(endTime));
			online.set("teacher", getPara("courseTeacher"));
			online.set("online_type_id", getParaToLong("onlineType"));
			try {
				if (NumberUtils.isNullOrZero(courseId)) {
					courseService.saveOnlineCourse(online, course);

					LoginUser user = getSessionAttr(PropKit.get("project.session"));
					logService.saveLog(user, 0, course.getLong("id"),
							"用户:" + user.getRealName() + "->添加了线上课程:" + course.getStr("course_name"));
				} else {
					courseService.updateOnlineCourse(online, course);

					LoginUser user = getSessionAttr(PropKit.get("project.session"));
					logService.saveLog(user, 0, course.getLong("id"),
							"用户:" + user.getRealName() + "->修改了线上课程:" + course.getStr("course_name"));
				}
				renderJson(ok());
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统异常"));
			}
		}
	}

	public void saveOnlineCourse() {
		boolean isHave = false;
		try {
			isHave = getFile("imglive") != null;
		} catch (Exception e) {
			isHave = false;
//			if (NumberUtils.isNullOrZero(getParaToLong("courseId", 0L))) {
//				renderJson(error("参数缺失!"));
//				return;
//			}
		}

		// 验证参数
		if (StringUtils.isNullOrEmpty(getPara("courseName")) || getParaToInt("type", -1) == -1
				|| StringUtils.isNullOrEmpty(getPara("beginTime"))) {
			renderJson(error("参数缺失!"));
			return;
		}
		if (NumberUtils.isNullOrZero(getParaToLong("playAddress", 0L))
				|| StringUtils.isNullOrEmpty(getPara("courseTeacher")) || getParaToInt("playStrtHour", -1) < 0
				|| getParaToInt("playStartMinute", -1) < 0 || StringUtils.isNullOrEmpty(getPara("coursePrice"))) {
			renderJson(error("参数缺失!"));
			return;
		}
		Course course = null;
		Long courseId = getParaToLong("courseId", 0L);
		String subtitle = getPara("subtitle");
		// 判断是否为修改
		if (NumberUtils.isNullOrZero(courseId)) {
			course = new Course();
			course.set("create_at", DateUtils.getNowDate());
			course.set("cover", "");
		} else {
			course = courseService.findCourseById(courseId);
			course.set("update_at", DateUtils.getNowDate()).set("version", course.getLong("version") + 1);
		}
		// 上传图片
		if (isHave) {
			UploadFile file = getFile("imglive");
			if (file.getFile().length() >= 300 * 1024) {
				renderJson(error("图片太大,请选择300k以内的小图!"));
				return;
			}
			String path = new Upload().upload(file, "avatar");
			course.set("avatar", path);
			// course.set("cover", "");
		}else{
			if (NumberUtils.isNullOrZero(getParaToLong("courseId", 0L))) {
				course.set("avatar", "");
			}
			
		}
		course.set("qrcode", "");
		course.set("course_name", getPara("courseName")).set("type", getParaToInt("type"));
		course.set("synopsis", getPara("synopsis", ""));
		course.set("begin_time", DateUtils.parseDate(getPara("beginTime") + " " + CodeUtils.getRandomInt(23) + ":"
				+ CodeUtils.getRandomInt(59) + ":" + CodeUtils.getRandomInt(59)));
		course.set("subtitle", subtitle);
		course.set("level", getPara("level"));

		OnlineCourse online = null;
		if (NumberUtils.isNullOrZero(courseId)) {
			online = new OnlineCourse();
			online.set("course_content", "");
			online.set("course_ppt", "");
			online.set("course_back", "");
			online.set("create_at", DateUtils.getNowDate());
		} else {
			online = courseService.findOnlineByCourseId(courseId);
			online.set("update_at", DateUtils.getNowDate());
			online.set("version", online.getLong("version") + 1);
		}
		online.set("tag", getPara("courseTag", ""));
		online.set("introduction", getPara("introduction", ""));
		online.set("price", Double.parseDouble(getPara("coursePrice")));
		online.set("play_address_id", getParaToLong("playAddress"));
		online.set("online_type_id", getParaToLong("onlineType"));
		int startHour = getParaToInt("playStrtHour", 0);
		int endHour = getParaToInt("playEndHour", 0);
		int startMinute = getParaToInt("playStartMinute", 0);
		int endMinute = getParaToInt("playEndMinute", 0);

		String beginTime = getPara("beginTime") + " " + startHour + ":" + startMinute + ":00";
		String endTime = getPara("beginTime") + " " + endHour + ":" + endMinute + ":00";

		online.set("play_begin_time", DateUtils.parseDate(beginTime));
		online.set("play_end_time", DateUtils.parseDate(endTime));
		online.set("teacher", getPara("courseTeacher"));
		try {
			if (NumberUtils.isNullOrZero(courseId)) {
				courseService.saveOnlineCourse(online, course);

				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user, 0, course.getLong("id"),
						"用户:" + user.getRealName() + "->添加了线上课程:" + course.getStr("course_name"));
			} else {
				courseService.updateOnlineCourse(online, course);

				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user, 0, course.getLong("id"),
						"用户:" + user.getRealName() + "->修改了线上课程:" + course.getStr("course_name"));
			}
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	// 保存课程封面
	public void saveCover() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数缺失"));
			return;
		}
		try {
			double x = Double.parseDouble(getPara("x"));
			double y = Double.parseDouble(getPara("y"));
			double w = Double.parseDouble(getPara("w"));
			double h = Double.parseDouble(getPara("h"));

			String path = getPara("path");
			// 获得后缀
			String suffix = path.substring(path.lastIndexOf(".") + 1);
			OperateImage o = new OperateImage((int) x, (int) y, (int) w, (int) h);
			o.setSrcpath(path);

			String coverPath = o.cut(suffix, "cover");

			Course course = courseService.findCourseById(id);
			course.set("cover", coverPath).set("version", course.getLong("version") + 1).set("update_at",
					DateUtils.getNowDate());
			courseService.updateCourse(course);

			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, course.getInt("type") == 0 ? 1 : 0, course.getLong("id"),
					"用户:" + user.getRealName() + "->修改了课程:" + course.getStr("course_name") + " 的封面");
			renderJson(ok());
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}
	}

	// swf上传文件
	public void upload() {
		try {
			UploadFile file = getFile("file");
			String path = new Upload().upload(file, "file");
			setSessionAttr("uploadFile", path);
			renderJson(ok(path));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("上传失败..."));
		}
	}

	// ajax上传封面
	public void uploadCover() {
		try {
			UploadFile file = getFile("file");
			String path = new Upload().upload(file, "cover");
			BufferedImage bufferedImage = ImageIO.read(new File(PropKit.get("upload.path") + path));
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			setSessionAttr("coverPath", path);

			JSONObject obj = new JSONObject();
			obj.put("path", path);
			obj.put("width", width);
			obj.put("height", height);
			obj.put("cover", appenUrl(path));
			// obj.put("cover",
			// "http://static.ichazuo.cn/course/index/id/5/n/big_2015070102554531691.jpg");
			renderJson(ok("上传成功...", obj));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("上传失败..."));
		}
	}

	// 删除图片
	public void deleteImage() {
		try {
			Long id = getParaToLong("id", 0L);
			if (NumberUtils.isNullOrZero(id)) {
				renderJson(error("参数缺失"));
				return;
			}
			OfflineCourseImage image = courseService.findImageById(id);
			image.set("status", 0);
			image.set("update_at", DateUtils.getNowDate()).set("version", image.getLong("version") + 1);
			courseService.updateImage(image);

			Course course = courseService.findCourseById(image.getLong("course_id"));
			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 1, course.getLong("id"),
					"用户:" + user.getRealName() + "->删除了课程:" + course.getStr("course_name") + " 的图片");

			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	// 保存课程图片
	public void saveOfflineImage() {
		Long id = getParaToLong("id", 0L);
		Long courseId = getParaToLong("courseId");
		if (NumberUtils.isNullOrZero(courseId)) {
			renderJson(error("参数缺失"));
			return;
		}
		try {
			double x = Double.parseDouble(getPara("x"));
			double y = Double.parseDouble(getPara("y"));
			double w = Double.parseDouble(getPara("w"));
			double h = Double.parseDouble(getPara("h"));

			String path = getPara("path");
			// 获得后缀
			String suffix = path.substring(path.lastIndexOf(".") + 1);
			OperateImage o = new OperateImage((int) x, (int) y, (int) w, (int) h);
			o.setSrcpath(path);

			String imagePath = o.cut(suffix, "cover");

			if (NumberUtils.isNullOrZero(id)) {
				OfflineCourseImage image = new OfflineCourseImage();
				image.set("course_id", courseId).set("create_at", DateUtils.getNowDate());
				image.set("image_url", imagePath);

				courseService.saveImage(image);

				Course course = courseService.findCourseById(image.getLong("course_id"));
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user, 1, image.getLong("course_id"),
						"用户:" + user.getRealName() + "->添加了课程:" + course.getStr("course_name") + " 的图片");

			} else {
				OfflineCourseImage image = courseService.findImageById(id);
				image.set("image_url", imagePath).set("update_at", DateUtils.getNowDate()).set("version",
						image.getLong("version") + 1);
				courseService.updateImage(image);

				Course course = courseService.findCourseById(image.getLong("course_id"));
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user, 1, image.getLong("course_id"),
						"用户:" + user.getRealName() + "->修改了课程:" + course.getStr("course_name") + " 的图片");
			}

			renderJson(ok());
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}
	}

	// 控制隐藏显示
	public void hiddenOrShow() {
		long courseId = getParaToLong("courseId", 0L);
		if (courseId == 0) {
			renderJson(ok());
			return;
		}
		try {
			Course course = courseService.findCourseById(courseId);
			if (course.getInt("is_hidden") == 0) {
				course.set("is_hidden", 1);
			} else {
				course.set("is_hidden", 0);
			}
			courseService.updateCourse(course);
			renderJson(ok());
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}

	}

	// 控制隐藏显示
	public void crow() {
		long courseId = getParaToLong("courseId", 0L);
		if (courseId == 0) {
			renderJson(ok());
			return;
		}
		try {
			OfflineCourse offline = courseService.findOfflineByCourseId(courseId);
			if (offline.getInt("is_crowd") == 0) {
				offline.set("is_crowd", 1);
			} else {
				offline.set("is_crowd", 0);
			}
			courseService.updateOfflineCourse(offline);
			renderJson(ok());
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}
	}

	public void webHidden() {
		long courseId = getParaToLong("courseId", 0L);
		if (courseId == 0) {
			renderJson(ok());
			return;
		}
		try {
			Course course = courseService.findCourseById(courseId);
			if (course.getInt("web_hidden") == 0) {
				course.set("web_hidden", 1);
			} else {
				course.set("web_hidden", 0);
			}
			courseService.updateCourse(course);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
	public void webShow() {
		long courseId = getParaToLong("courseId", 0L);
		if (courseId == 0) {
			renderJson(ok());
			return;
		}
		try {
			Course course = courseService.findCourseById(courseId);
			if (course.getInt("new_web_hidden") == 0) {
				course.set("new_web_hidden", 1);
			} else {
				course.set("new_web_hidden", 0);
			}
			courseService.updateCourse(course);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	public void isnew() {
		long courseId = getParaToLong("courseId", 0L);
		if (courseId == 0) {
			renderJson(ok());
			return;
		}
		try {
			OfflineCourse course = courseService.findOfflineByCourseId(courseId);
			if (course.getInt("isnew") == 0) {
				course.set("isnew", 1);
			} else {
				course.set("isnew", 0);
			}
			courseService.updateOfflineCourse(course);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	//录音课程标新/不标新
	public void isNew() {
		long courseId = getParaToLong("courseId", 0L);
		if (courseId == 0) {
			renderJson(ok());
			return;
		}
		try {
			OnlineCourse course = courseService.findOnlineByCourseId(courseId);
			if (course.getInt("isnew") == 0) {
				course.set("isnew", 1);
			} else {
				course.set("isnew", 0);
			}
			courseService.updateOnlineCourse(course);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
	public void updateFullStatus() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数异常"));
			return;
		}
		OfflineCourse r = courseService.findOfflineByCourseId(id);
		if (r == null) {
			renderJson(error("参数异常"));
			return;
		}
		r.set("isfull", r.getInt("isfull") == 0 ? 1 : 0);

		try {
			courseService.updateOfflineCourse(r);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}

	}
	
/**
 * 课程报名人数
 */
		public void joinAllMembers() {
			Integer type = getParaToInt("type");
			Integer page = getParaToInt("page", 1);
			Long courseId = getParaToLong("courseId");

			String name = getPara("name", "");
			setAttr("courseId", courseId);
			setAttr("type", type);
			try {
					 if(NumberUtils.isNullOrZero(courseId)){
					 redirect("/online");
					 return;
					 }
					 Long count = memberService.findOnlineJoinMemberAllCount(courseId,name);
					 List<Record> list = memberService.findOnlineJoinMemberAll(courseId,
					 page);
					 list.forEach(record -> {
					 record.set("avatar", super.appenUrl(record.getStr("avatar")));
					 });
					 setAttr("page", new Page(page, count,
					 "/admin/course/joinAllMembers?type="+type+"&courseId="+courseId+ "&name=" + name));
					 setAttr("list",list);
//				 }
			} catch (Exception e) {
				e.printStackTrace();
				redirect("/offline");
				return;
			}

			render("coursejoin.jsp");
		}
		
		// 控制录音课程是否售卖
		public void sellOrNot() {
			long courseId = getParaToLong("courseId", 0L);
			if (courseId == 0) {
				renderJson(ok());
				return;
			}
			try {
				Course course = courseService.findCourseById(courseId);
				if (course.getInt("is_sell") == 0) {
					course.set("is_sell", 1);
				} else {
					course.set("is_sell", 0);
				}
				courseService.updateCourse(course);
				renderJson(ok());
			} catch (Exception e) {
				renderJson(error("系统异常"));
			}

		}
		
		// 控制录音课程是否在企业版APP显示
		public void businessHidden() {
			long courseId = getParaToLong("courseId", 0L);
			if (courseId == 0) {
				renderJson(ok());
				return;
			}
			try {
				Course course = courseService.findCourseById(courseId);
				if (course.getInt("business_hidden") == 0) {
					course.set("business_hidden", 1);
				} else {
					course.set("business_hidden", 0);
				}
				courseService.updateCourse(course);
				renderJson(ok());
			} catch (Exception e) {
				renderJson(error("系统异常"));
			}

		}
		
		// 控制录音课程录音课件是否有片头
		public void ifHaveTitle() {
			long courseId = getParaToLong("courseId", 0L);
			if (courseId == 0) {
				renderJson(ok());
				return;
			}
			try {
				Course course = courseService.findCourseById(courseId);
				if (course.getInt("if_title") == 0) {
					course.set("if_title", 1);
				} else {
					course.set("if_title", 0);
				}
				courseService.updateCourse(course);
				renderJson(ok());
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统异常"));
			}

		}
	// 根据课程报名人员批量建企业账号APP
	public void BatchBuildBusinessAppAccount() {
		int page = getParaToInt("page", 1);
		Long courseId = getParaToLong("courseId",0L);
		String courseName = getPara("courseName");
		StringBuffer result = new StringBuffer();
		//查询是否已经存在课程名字相同的公司信息
		List<Record> list1 = Db.query("select id from t_business where business_name = ? and status = 1 limit 1",courseName);
		//建立已课程名称为名字的企业信息不建立企业信息
		//如果企业信息不存在，则直接建立企业信息，否则不新建
		if(list1.size()>0){
			renderJson(ok("已经存在名为："+courseName+"的企业，无法新建！"));
		}else{
			//①、建立已课程名字为名称、登录名和密码的企业信息
			Record Brecord = new Record();
			Date date = new Date();
			Brecord.set("status", 1);
			Brecord.set("business_name", courseName);
			Brecord.set("login_name", courseName);
			Brecord.set("business_level", 50);
			Brecord.set("update_at", date);
			Brecord.set("password", PasswdEncryption.generate(courseName.trim()));
			Brecord.set("create_at", date);
			businessService.save(Brecord);
			List<Record> list2 = Db.find("select id from t_business where business_name = ? and status = 1 limit 1",courseName);
			//①、查询此课程的全部报名人员信息
			List<Record> list = courseService.findOrderDetail(courseId);
			//②循环遍历报名人员信息，创建用户好多课App用户信息
//			list.forEach(info -> {
			for(int i=0;i<list.size();i++){
				//手机号码
				String mobile = list.get(i).get("mobile");
				//姓名
				String name = list.get(i).get("nick_name");
				//公司
				String work = list.get(i).get("company_name");
				//职位
				String job = list.get(i).get("job_name");
				//过滤手机号相同的用户
				List<Record> exist = Db.find("select id from t_business_employee where mobile = ? and status = 1 limit 1",mobile);
				if(exist.size()<1){
					Record record = new Record();
					Date date1 = new Date();
					record.set("avatar", (CodeUtils.getRandomInt(8) + 1) + ".png");
					record.set("status", 1);
					record.set("name", name);
					record.set("sex", "男");
					record.set("mobile", mobile);
					record.set("business_id",list2.get(0).get("id"));
					record.set("business_name", courseName);
					record.set("position", job);
					record.set("update_at", date);
					//默认创建会员期限为365天的学员账号
					record.set("expiry_date", 365);
					try{
						record.set("password",PasswdEncryption.generate(mobile.trim()));
						record.set("create_at", date);
						businessService.saveEmployee(record);
					}catch(Exception e){
						e.printStackTrace();					}
				}else{
	            	result.append(mobile).append(",");
				}
			};
			renderJson(ok("以下手机号码因为已经注册过创建App账号失败:"+result.toString()));
		}
	}
	
	// 编辑直播课程
		public void editLivingCourse() {
			long id = getParaToLong("id", 0L);
			int newtype = 2;
			setAttr("newtype", newtype);
			String content = "";
			setAttr("cityList", courseService.findAllCity());
			setAttr("addressList", playAddressService.findAllAddress());

			if (!NumberUtils.isNullOrZero(id)) {
				Course course = courseService.findCourseById(id);

				course.set("avatar", super.appenUrl(course.getStr("avatar")));
				setAttr("course", course);
				OfflineCourse offline = courseService.findOfflineByCourseId(id);
				setAttr("offlineType", dictItemService.findAll("OFFLINECOURSETYPE"));
				String ids = offline.getStr("teacher_id");
				StringBuffer sb = new StringBuffer();
				if(ids!="" && ids!=null && !"".equals(ids)){
					for (String s : ids.split(",")) {
						if (StringUtils.isNullOrEmpty(s)) {
							continue;
						}
						sb.append(memberService.findMemberById(Long.parseLong(s)).getStr("nick_name")).append(",");
					}
				}
				content = offline.getStr("course_content");
				setAttr("teacherNames", StringUtils.removeEndChar(sb.toString(), ','));
				setAttr("offline", offline);
				setAttr("content", content);
				render("livingCourse.jsp");
			} else {
				if (getParaToInt("type") == 0) {
					setAttr("offlineType", dictItemService.findAll("OFFLINECOURSETYPE"));
					render("livingCourse.jsp");
				} else {
					setAttr("onlineType", dictItemService.findAll("ONLINECOURSETYPE"));
					render("livingCourse.jsp");
				}

			}
		}
		//编辑书籍
		public void editBook() {
			long id = getParaToLong("id", 0L);
			int newtype = 3;
			setAttr("newtype", newtype);
			String content = "";
			setAttr("cityList", courseService.findAllCity());
			setAttr("addressList", playAddressService.findAllAddress());

			if (!NumberUtils.isNullOrZero(id)) {
				Course course = courseService.findCourseById(id);

				course.set("avatar", super.appenUrl(course.getStr("avatar")));
				setAttr("course", course);
				OfflineCourse offline = courseService.findOfflineByCourseId(id);
				setAttr("offlineType", dictItemService.findAll("OFFLINECOURSETYPE"));
				String ids = offline.getStr("teacher_id");
				StringBuffer sb = new StringBuffer();
				if(ids!="" && ids!=null && !"".equals(ids)){
					for (String s : ids.split(",")) {
						if (StringUtils.isNullOrEmpty(s)) {
							continue;
						}
						sb.append(memberService.findMemberById(Long.parseLong(s)).getStr("nick_name")).append(",");
					}
				}
				content = offline.getStr("course_content");
				setAttr("teacherNames", StringUtils.removeEndChar(sb.toString(), ','));
				setAttr("offline", offline);
				setAttr("content", content);
				render("bookEdit.jsp");
			} else {
				if (getParaToInt("type") == 0) {
					setAttr("offlineType", dictItemService.findAll("OFFLINECOURSETYPE"));
					render("bookEdit.jsp");
				} else {
					setAttr("onlineType", dictItemService.findAll("ONLINECOURSETYPE"));
					render("bookEdit.jsp");
				}

			}
		}
		// 编辑推广渠道
		public void editSpread() {
			long id = getParaToLong("id", 0L);
			if (!NumberUtils.isNullOrZero(id)) {
				Record spread = courseService.findSpreadById(id);
				setAttr("spread", spread);
				render("spreadEdit.jsp");
			} else {
				render("spreadEdit.jsp");
			}
		}
		// 保存内容
		public void saveSpread() {
			Long id = getParaToLong("id");
			//推广码
			String from1 = getPara("from1");
			//推广渠道
			String promotionParty = getPara("promotion_party");
			//合作方式
			String cooperationMode = getPara("cooperation_mode");
			//课程名称
			String courseName = getPara("course_name");
			Record spread = new Record();
			spread.set("from1", from1);
			spread.set("promotion_party", promotionParty);
			spread.set("cooperation_mode", cooperationMode);
			spread.set("course_name", courseName);
			try {
				if (NumberUtils.isNullOrZero(id)) {
					List<Record> spreadList =  courseService.findSpreadByFrom1(from1);
					if(spreadList.size()>0){
						renderJson(error("课程："+spreadList.get(0).getStr("course_name")+"--已经使用过此推广码！"));
						return;
					}
					spread.set("create_at", new Date());
					spread.set("status", "1");
					courseService.saveSpread(spread);
				}else{
					spread.set("id", id);
					courseService.updateSpread(spread);
				}
				renderJson(ok());
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统异常"));
			}

		}
}
