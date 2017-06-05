package cn.ichazuo.controller;

import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.StudentService;
import cn.ichazuo.service.TeacherService;

public class StudentController extends BaseController {

	private StudentService service = Duang.duang(StudentService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		long count = service.findStudentCount();
		List<Record> list = service.findStudentList(page);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.getStr("cover")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/student?s=1"));
		render("teachers.jsp");
	}

	public void save() {
		try {
			boolean isHave = false;
			try {
				isHave = getFile("imglive") != null;
			} catch (Exception e) {
				isHave = false;
			}
//			}

//			if (StringUtils.isNullOrEmpty(getPara("name"), getPara("title"),getPara("info"),getPara("price"))) {
//				renderJson(error("参数错误!"));
//				return;
//			}

			Long id = getParaToLong("id", 0L);
			if (NumberUtils.isNullOrZero(id)) {
				if (!isHave) {
					renderJson(error("请选择学员头像"));
					return;
				}
				Record record = new Record();
				record.set("name", getPara("name"));
				record.set("title", getPara("title"));
				String path = new Upload().upload(getFile("imglive"), "cover");
				record.set("cover", path);
				record.set("create_at", DateUtils.getNowDate());
				record.set("content", getPara("content"));
				record.set("job", getPara("job"));
				record.set("bewrite", getPara("bewrite"));
				//城市
				record.set("city", getPara("city"));
				//职位
				record.set("industry", getPara("industry"));
				record.set("weight", getPara("weight"));
				record.set("status", 1);
				
				service.saveStudent(record);
			} else {
				Record record = service.findStudentById(id);
				if (isHave) {
					String path = new Upload().upload(getFile("imglive"), "cover");
					record.set("cover", path);
				}
//				if(isHaveCover){
//					String path = new Upload().upload(getFile("imglive1"), "cover");
//					record.set("cover", path);
//				}
				record.set("name", getPara("name"));
				record.set("title", getPara("title"));
//				record.set("type", type);
//				record.set("price", Double.parseDouble(getPara("price")));
//				record.set("info", getPara("info"));
				record.set("content", getPara("content"));
				record.set("update_at", DateUtils.getNowDate());
				record.set("job", getPara("job"));
				//城市
				record.set("city", getPara("city"));
				//职位
				record.set("industry", getPara("industry"));
				record.set("weight", getPara("weight"));
				//描述
				record.set("bewrite", getPara("bewrite"));
//				record.set("version", record.getLong("version") + 1);

				service.updateStudent(record);
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
			Record record = service.findStudentById(id);
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
			service.deleteStudent(id);
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
}
