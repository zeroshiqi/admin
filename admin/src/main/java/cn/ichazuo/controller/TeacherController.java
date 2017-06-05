package cn.ichazuo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.OperateImage;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Course;
import cn.ichazuo.service.TeacherService;

public class TeacherController extends BaseController {

	private TeacherService service = Duang.duang(TeacherService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		long count = service.findTeacherCount();
		List<Record> list = service.findTeacherList(page);
		list.forEach(info -> {
			info.set("avatar", appenUrl(info.getStr("avatar")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/teacher?s=1"));
		render("teachers.jsp");
	}

	public void save() {
		try {
			boolean isHave = false;
			boolean isHaveCover = false;
			try {
				isHave = getFile("imglive") != null;
			} catch (Exception e) {
				isHave = false;
			}
			try {
				isHaveCover = getFile("imglive1") != null;
			} catch (Exception e) {
				isHaveCover = false;
			}
			int type = getParaToInt("type", 0);
			if (type == 0) {
				renderJson(error("参数错误!"));
				return;
			}

			if (StringUtils.isNullOrEmpty(getPara("name"), getPara("title"),getPara("info"),getPara("price"))) {
				renderJson(error("参数错误!"));
				return;
			}

			Long id = getParaToLong("id", 0L);
			if (NumberUtils.isNullOrZero(id)) {
				if (!isHave) {
					renderJson(error("请选择背景图片"));
					return;
				}
				if (!isHaveCover) {
					renderJson(error("请选择讲师头像图片"));
					return;
				}
				Record record = new Record();
				record.set("name", getPara("name"));
				record.set("title", getPara("title"));
				record.set("type", type);
				record.set("price", Double.parseDouble(getPara("price")));
				record.set("info", getPara("info"));
				String path = new Upload().upload(getFile("imglive"), "avatar");
				record.set("avatar", path);
				record.set("create_at", DateUtils.getNowDate());
				record.set("content", getPara("content"));
				record.set("job", getPara("job"));
				record.set("weight", getPara("weight"));
				record.set("bewrite", getPara("bewrite"));
				record.set("status", 1);
				record.set("catalog_id", getPara("typeId"));
				record.set("catalog_name", getPara("typeName"));
				
				service.saveTeacher(record);
			} else {
				Record record = service.findTeacherById(id);
				if (isHave) {
					String path = new Upload().upload(getFile("imglive"), "avatar");
					record.set("avatar", path);
				}
				if(isHaveCover){
					String path = new Upload().upload(getFile("imglive1"), "cover");
					record.set("cover", path);
				}
				record.set("name", getPara("name"));
				record.set("title", getPara("title"));
				record.set("type", type);
				record.set("price", Double.parseDouble(getPara("price")));
				record.set("info", getPara("info"));
				record.set("content", getPara("content"));
				record.set("update_at", DateUtils.getNowDate());
				record.set("job", getPara("job"));
				record.set("weight", getPara("weight"));
				record.set("version", record.getLong("version") + 1);
				record.set("bewrite", getPara("bewrite"));
				record.set("catalog_id", getPara("typeId"));
				record.set("catalog_name", getPara("typeName"));
				service.updateTeacher(record);
			}
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统错误"));
		}
	}

	
	public void edit() {
		Long id = getParaToLong("id", 0L);
		if (!NumberUtils.isNullOrZero(id)) {
			Record record = service.findTeacherById(id);
			record.set("avatar", appenUrl(record.getStr("avatar")));
			record.set("cover", appenUrl(record.getStr("cover")));
			setAttr("record", record);
		}
		render("teacher.jsp");
	}

	public void delete() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数错误"));
			return;
		}
		try {
			service.deleteTeacher(id);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	public void invite() {
		int page = getParaToInt("page", 1);
		long count = service.findTeacherInviteCount();
		List<Record> list = service.findTeacherInvite(page);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/teacher/invite?s=1"));
		render("teachers.jsp");

		render("invite.jsp");
	}
	//查询可以关联到Banner的课程包
	public void query(){
		String title = getPara("name");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> examList = service.findCatalog(title);
		for (Record obj : examList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("name")));
			arr.add(json);
		}
		renderJson(arr);
	}
	// 控制录音课程是否售卖
			public void isShow() {
				long id = getParaToLong("id", 0L);
				if (id == 0) {
					renderJson(ok());
					return;
				}
				try {
					Record record = service.findTeacherById(id);
					if (record.getInt("app_hidden") == 0) {
						record.set("app_hidden", 1);
					} else {
						record.set("app_hidden", 0);
					}
					service.updateTeacher(record);
					renderJson(ok());
				} catch (Exception e) {
					renderJson(error("系统异常"));
				}

			}
			// 控制录音课程是否售卖
			public void webShow() {
				long id = getParaToLong("id", 0L);
				if (id == 0) {
					renderJson(ok());
					return;
				}
				try {
					Record record = service.findTeacherById(id);
					if (record.getInt("web_hidden") == 0) {
						record.set("web_hidden", 1);
					} else {
						record.set("web_hidden", 0);
					}
					service.updateTeacher(record);
					renderJson(ok());
				} catch (Exception e) {
					renderJson(error("系统异常"));
				}

			}
			// 控制录音课程是否售卖
						public void dakeShow() {
							long id = getParaToLong("id", 0L);
							if (id == 0) {
								renderJson(ok());
								return;
							}
							try {
								Record record = service.findTeacherById(id);
								if (record.getInt("is_hidden") == 0) {
									record.set("is_hidden", 1);
								} else {
									record.set("is_hidden", 0);
								}
								service.updateTeacher(record);
								renderJson(ok());
							} catch (Exception e) {
								renderJson(error("系统异常"));
							}

						}
			public void findAllTeacher(){
				String name = getPara("name","");
				JSONArray arr = new JSONArray();

				List<Record> memberList = service.findAllTeacher(name);
				for (Record obj : memberList) {
					JSONObject json = new JSONObject();
					json.put("id", new String(obj.getLong("id") + ""));
					json.put("value", new String(obj.getStr("name")));
					arr.add(json);
				}
				renderJson(arr);
			}
			// 跳转裁剪图片
			public void cropImage() {
				Long id = getParaToLong("id", 0L);
				setAttr("id", id);
				Record teacher = service.findTeacherById(id);
				setAttr("course", teacher);
				render("image.jsp");
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

					Record course = service.findTeacherById(id);
					course.set("cover1", coverPath);
					service.updateTeacher(course);
					renderJson(ok());

				} catch (Exception e) {
					renderJson(error("系统异常"));
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
}
