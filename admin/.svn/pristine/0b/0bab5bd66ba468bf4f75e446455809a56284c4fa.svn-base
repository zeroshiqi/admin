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
import cn.ichazuo.service.JobService;

public class JobController extends BaseController {
	private JobService service = Duang.duang(JobService.class);
	
	public void index(){
		String job = getPara("job","");
		int page = getParaToInt("page", 1);
		long count = service.findJobCount(job);
		List<Record> list = service.findJobList(page,job);
		list.forEach(info -> {
			info.set("cover", super.appenUrl(info.getStr("cover")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/job?s=1"));
		render("jobs.jsp");
	}
	
	public void editjob(){
		Long id = getParaToLong("id", 0L);
		if (!NumberUtils.isNullOrZero(id)) {
			Record record = service.findJob(id);
			record.set("cover", super.appenUrl(record.getStr("cover")));
			record.set("avatar", super.appenUrl(record.getStr("avatar")));
			setAttr("record", record);
		}
		setAttr("types", service.findAllType());
		render("editjob.jsp");
	}
	
	public void saveJob(){
		boolean isHave = false;
		boolean isHave2 = false;

		try {
			isHave2 = getFile("imglive2") != null;
		} catch (Exception e) {
			isHave2 = false;
		}
		
		try {
			isHave = getFile("imglive") != null;
		} catch (Exception e) {
			isHave = false;
		}
		if (StringUtils.isNullOrEmpty(getPara("name"), getPara("pay"),getPara("address"),getPara("email"),getPara("tag")) || NumberUtils.isNullOrZero(getParaToLong("type",0L))) {
			renderJson(error("参数错误!"));
			return;
		}
		String content = getPara("content","");
		String tags = getPara("tags","");
		try {
			Long id = getParaToLong("id", 0L);
			if (NumberUtils.isNullOrZero(id)) {
				if (!isHave || !isHave2) {
					renderJson(error("请上传图片!"));
					return;
				}
				Record record = new Record();
				record.set("company", getPara("company"));
				record.set("job_name", getPara("name"));
				record.set("pay", getPara("pay"));
				record.set("email", getPara("email"));
				record.set("tag", getPara("tag"));
				record.set("tags", tags);
				record.set("content", content);
				record.set("address", getPara("address"));
				String path = new Upload().upload(getFile("imglive"), "cover");
				String avatar = new Upload().upload(getFile("imglive2"), "avatar");
				record.set("cover", path);
				record.set("avatar", avatar);
				record.set("type_id", getParaToLong("type"));
				record.set("create_at", DateUtils.getNowDate());
				record.set("version", 1);
				record.set("status", 1);
				
				service.saveJob(record);
			} else {
				Record record = service.findJob(id);
				if (isHave) {
					String path = new Upload().upload(getFile("imglive"), "cover");
					record.set("cover", path);
				}
				if(isHave2){
					String path = new Upload().upload(getFile("imglive2"), "avatar");
					record.set("avatar", path);
				}
				record.set("company", getPara("company"));
				record.set("email", getPara("email"));
				record.set("address", getPara("address"));
				record.set("job_name", getPara("name"));
				record.set("pay", getPara("pay"));
				record.set("tag", getPara("tag"));
				record.set("tags", tags);
				record.set("content", content);
				record.set("update_at", DateUtils.getNowDate());
				record.set("version", record.getLong("version") + 1);
				record.set("type_id", getParaToLong("type"));
				
				service.updateJob(record);
			}
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统错误"));
		}
	}
	
	public void deleteJob(){
		Long id = getParaToLong("id");
		try {
			if (NumberUtils.isNullOrZero(id)) {
				renderJson(error("参数缺失"));
				return;
			}
			service.deleteJob(id);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("msg"));
		}
	}
	
	public void jobtype() {
		int page = getParaToInt("page", 1);
		long count = service.findTypeCount();
		List<Record> list = service.findTypeList(page);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/job/jobtype?s=1"));
		render("types.jsp");
	}
	
	public void editType(){
		Long id = getParaToLong("id", 0L);
		if (!NumberUtils.isNullOrZero(id)) {
			Record record = service.findType(id);
			setAttr("record", record);
		}
		render("edittype.jsp");
	}
	
	public void saveType(){
		if (StringUtils.isNullOrEmpty(getPara("value"), getPara("weight"))) {
			renderJson(error("参数错误!"));
			return;
		}
		try {
			Long id = getParaToLong("id", 0L);
			if (NumberUtils.isNullOrZero(id)) {
				Record record = new Record();
				record.set("value", getPara("value"));
				record.set("dict_id", service.findDictId());
				record.set("weight", getParaToInt("weight", 0));
				record.set("remark", getPara("value"));
				record.set("status", 1);
				record.set("create_at", DateUtils.getNowDate());
				record.set("version", 1);

				service.saveType(record);
			} else {
				Record record = service.findType(id);
				record.set("value", getPara("value"));
				record.set("dict_id", service.findDictId());
				record.set("weight", getParaToInt("weight", 0));
				record.set("remark", getPara("value"));
				record.set("update_at", DateUtils.getNowDate());
				record.set("version", record.getLong("version") + 1);

				service.updateType(record);
			}
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统错误"));
		}
	}
	
	public void deleteType() {
		Long id = getParaToLong("id");
		try {
			if (NumberUtils.isNullOrZero(id)) {
				renderJson(error("参数缺失"));
				return;
			}
			Long count = service.findUseCount(id);
			if (count > 0) {
				renderJson(error("类型已被使用,请勿删除"));
				return;
			}
			service.deleteType(id);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("msg"));
		}
	}
}
