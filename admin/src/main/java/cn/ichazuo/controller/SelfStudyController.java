package cn.ichazuo.controller;

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.OperateImage;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.SelfStudy;
import cn.ichazuo.service.SelfStudyService;
import cn.ichazuo.service.DictItemService;
import cn.ichazuo.service.LogService;

/**
 * @ClassName: SelfStudyController
 * @Description: (自学课程Controller)
 * @author ZhaoXu
 * @date 2015年8月4日 下午6:50:59
 * @version V1.0
 */
public class SelfStudyController extends BaseController {
	private SelfStudyService selfStudyService = Duang.duang(SelfStudyService.class);
	private DictItemService dictItemService = Duang.duang(DictItemService.class);
	private LogService logService = Duang.duang(LogService.class);
	
	// list
	public void index() {
		String title = getPara("title");
		int page = getParaToInt("page", 1);
		long count = selfStudyService.findSelfStudyCount(title);
		List<Record> list = selfStudyService.findSelfStudyList(page, title);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.getStr("cover")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/selfstudy?s=1"));
		render("articles.jsp");
	}

	//save
	public void save1() {
		Long id = getParaToLong("id", 0L);
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		try {
			if (NumberUtils.isNullOrZero(id)) {
				SelfStudy article = new SelfStudy();
				article.set("synopsis", getPara("synopsis", ""));
				article.set("cover", getPara("cover","")).set("create_at", DateUtils.getNowDate()).set("title", getPara("title")).set("content", getPara("content"));
				article.set("type", getParaToLong("type", 0L)).set("url", getPara("url","")).set("level", getParaToInt("level")).set("click_number",0);
				article.set("tag", getPara("tag","")).set("show_type", "0");
				article = selfStudyService.saveSelfStudy(article);
				logService.saveLog(user, 2, article.getLong("id"), "用户:"+user.getRealName()+"->添加了文章:" + getPara("title"));
			} else {
				SelfStudy article = selfStudyService.findSelfStudyById(id);
				if(!StringUtils.isNullOrEmpty(getPara("cover"))){
					article.set("cover", getPara("cover",""));
				}
				article.set("synopsis", getPara("synopsis", ""));
				article.set("tag", getPara("tag",""));
				article.set("title", getPara("title")).set("url", getPara("url","")).set("content", getPara("content")).set("update_at", DateUtils.getNowDate()).set("version", article.getLong("version") + 1);
				article.set("type", getParaToLong("type", 0L)).set("level", getParaToInt("level",1));
				
				article = selfStudyService.updateSelfStudy(article);
				logService.saveLog(user, 2, article.getLong("id"), "用户:"+user.getRealName()+"->修改了文章:" + getPara("title"));
			}
			renderJson(ok());

			getSession().removeAttribute("coverPath");
			getSession().removeAttribute("imageUrl");
			getSession().removeAttribute("article");
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	public void save(){
		boolean isHave = false;
		try{
			//封面图片
			isHave = getFile("imglive") != null;
		}catch(Exception e){
			isHave = false;
		}
		Long id = getParaToLong("id",0L);
		//子标题
		String subtitle = getPara("subtitle");
		//标题
		String title = getPara("title");
		//级别
		int level = getParaToInt("level");
		//文章类型
		Long type = getParaToLong("type", 0L);
		//标签
		String tag = getPara("tag");
		//简介
		String synopsis = getPara("synopsis");
		//文章内容
		String content = getPara("content");
		//文章内容
		String weight = getPara("weight");
//		if(StringUtils.isNullOrEmpty(title)){
//			renderJson(error("参数错误"));
//			return;
//		}
		Record record = new Record();
		Date date = new Date();
		if(isHave){
			String path = new Upload().upload(getFile("imglive"), "cover");
			record.set("cover", path);
		}
		record.set("status", 1);
		record.set("subtitle", subtitle);
		record.set("title", title);
		record.set("level", level);
		record.set("type", type);
		record.set("tag", tag);
		record.set("synopsis", synopsis);
		record.set("content", content);
		record.set("update_at", date);
		record.set("weight", weight);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("create_at", date);
				selfStudyService.saveSelfStudy(record);
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user,1,id,
						"用户:" + user.getRealName() + "->创建了自学文章:" + title);
			}else{
				record.set("id", id);
				selfStudyService.updateEmployee(record);
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user,1,id,
						"用户:" + user.getRealName() + "->修改了自学文章:" + title);
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	public void edit(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			Record record = selfStudyService.findSelfStudy(id);
//			record.set("avatarAddress", uploadFile.upload(file, "avatar"));
			setAttr("record", record);
		}
		setAttr("typeList", dictItemService.findAll("READTYPE"));
		render("article.jsp");
	}
	// edit
	public void edit1() {
		int type = getParaToInt("type",0);
		if(type != 0){
			type = 1;
		}
		SelfStudy article = new SelfStudy();
		Long id = getParaToLong("id",0L);
		if (!NumberUtils.isNullOrZero(id)) {
			getSession().removeAttribute("article");
			article = selfStudyService.findSelfStudyById(id);
			setAttr("coverStr",article.getStr("cover"));
			article.set("cover", super.appenUrl(article.getStr("cover")));
			setAttr("coverStr",article.getStr("cover"));
		}
		
		setAttr("show_type", type);
		setAttr("article", article);
		setAttr("typeList", dictItemService.findAll("READTYPE"));
		render("article.jsp");
	}

	// delete
	public void delete() {
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			renderJson(error("参数缺失"));
			return;
		}
		try {
//			SelfStudy article = selfStudyService.findSelfStudyById(id);
//			article.set("status", 0).set("version", article.getLong("version") + 1).set("update_at",DateUtils.getNowDate());
//			selfStudyService.updateSelfStudy(article);
			Record record = new Record();
			record.set("id", id);
			record.set("status", "0");
			
			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			selfStudyService.updateEmployee(record);
			logService.saveLog(user, 2, id, "用户:"+user.getRealName()+"->删除了文章:" + getPara("title"));
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
	// 临时保存文章信息
	public void saveTempSelfStudy(){
		SelfStudy article = new SelfStudy();
		article.set("id", getPara("id"));
		article.set("show_type", getPara("show_type"));
		article.set("cover", getPara("cover")).set("title", getPara("title")).set("content", getPara("content"));
		article.set("type", getParaToLong("type", 0L)).set("url", getPara("url","")).set("level", getParaToInt("level"));
		getSession().removeAttribute("article");
		getSession().setAttribute("article", article);
		
		renderJson(ok());
	}
	
	//跳转添加图片
	public void addCover(){
		render("addimage.jsp");
	}
	
	//编辑图片
	public void editCover(){
		Long id = getParaToLong("id", 0L);
		if (NumberUtils.isNullOrZero(id)) {
			redirect("/admin/article");
			return;
		}
		SelfStudy article = selfStudyService.findSelfStudyById(id);
		setAttr("newtype", article.getInt("show_type"));
		setAttr("id", id);
		render("image.jsp");
	}
	
	//保存图片
	public void saveCover() {
		Long id = getParaToLong("id", 0L);
		
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
			
			if (!NumberUtils.isNullOrZero(id)) {
				SelfStudy article = selfStudyService.findSelfStudyById(id);
				article.set("cover", imagePath).set("version", article.getLong("version") + 1).set("update_at", DateUtils.getNowDate());
				
				selfStudyService.updateSelfStudy(article);
				
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user, 2, article.getLong("id"), "用户:"+user.getRealName()+"->修改了文章:" + article.getStr("title")+"的封面");
			}
			getSession().setAttribute("coverPath", imagePath);
			getSession().setAttribute("imageUrl", super.appenUrl(imagePath));
			renderJson(ok("ok"));
		} catch (Exception e) {
			renderJson(error("系统异常"));
		}
	}
	// 控制隐藏显示
		public void hiddenOrShow() {
			Long courseId = getParaToLong("courseId", 0L);
			if (courseId == 0) {
				renderJson(ok());
				return;
			}
			try {
				Record record = selfStudyService.findSelfStudy(courseId);
				if (record.getInt("is_hidden") == 0) {
					record.set("is_hidden", 1);
				} else {
					record.set("is_hidden", 0);
				}
				selfStudyService.updateEmployee(record);
				renderJson(ok());
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(error("系统异常"));
			}

		}
	
}
