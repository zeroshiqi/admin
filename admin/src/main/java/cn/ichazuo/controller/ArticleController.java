package cn.ichazuo.controller;

import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.OperateImage;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Article;
import cn.ichazuo.service.ArticleService;
import cn.ichazuo.service.DictItemService;
import cn.ichazuo.service.LogService;

/**
 * @ClassName: ArticleController
 * @Description: (文章Controller)
 * @author ZhaoXu
 * @date 2015年8月4日 下午6:50:59
 * @version V1.0
 */
public class ArticleController extends BaseController {
	private ArticleService articleService = Duang.duang(ArticleService.class);
	private DictItemService dictItemService = Duang.duang(DictItemService.class);
	private LogService logService = Duang.duang(LogService.class);
	
	// list
	public void index() {
		int type = getParaToInt("type",0);
		if(type != 0){
			type = 1;
		}
		setAttr("type", type);
		String title = getPara("title");
		int page = getParaToInt("page", 1);
		long count = articleService.findArticleCount(title,type);
		List<Article> list = articleService.findArticleList(page, title,type);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.getStr("cover")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/article?type="+type));
		render("articles.jsp");
	}

	//save
	public void save() {
		int show_type = getParaToInt("show_type",0);
		if(show_type != 0){
			show_type = 1;
		}
		Long id = getParaToLong("id", 0L);
		if (StringUtils.isNullOrEmpty(getPara("title")) 
				|| NumberUtils.isNullOrZero(getParaToInt("level", 0))
				|| NumberUtils.isNullOrZero(getParaToLong("type", 0L))) {
			renderJson(error("参数缺失"));
			return;
		}
		if(StringUtils.isNullOrEmpty(getPara("content")) && StringUtils.isNullOrEmpty(getPara("url"))){
			renderJson(error("参数缺失"));
			return;
		}
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		try {
			if (NumberUtils.isNullOrZero(id)) {
				Article article = new Article();
				article.set("synopsis", getPara("synopsis", ""));
				
				
				article.set("cover", getPara("cover","")).set("create_at", DateUtils.getNowDate()).set("title", getPara("title")).set("content", getPara("content"));
				article.set("type", getParaToLong("type", 0L)).set("url", getPara("url","")).set("level", getParaToInt("level")).set("click_number",0);
				article.set("tag", getPara("tag","")).set("show_type", show_type);
				article = articleService.saveArticle(article);
				logService.saveLog(user, 2, article.getLong("id"), "用户:"+user.getRealName()+"->添加了文章:" + getPara("title"));
			} else {
				Article article = articleService.findArticleById(id);
				if(!StringUtils.isNullOrEmpty(getPara("cover"))){
					article.set("cover", getPara("cover",""));
				}
				article.set("synopsis", getPara("synopsis", ""));
				article.set("tag", getPara("tag",""));
				article.set("title", getPara("title")).set("url", getPara("url","")).set("content", getPara("content")).set("update_at", DateUtils.getNowDate()).set("version", article.getLong("version") + 1);
				article.set("type", getParaToLong("type", 0L)).set("level", getParaToInt("level",1));
				
				article = articleService.updateArticle(article);
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

	// edit
	public void edit() {
		int type = getParaToInt("type",0);
		if(type != 0){
			type = 1;
		}
		Article article = new Article();
		Long id = getParaToLong("id",0L);
		if (!NumberUtils.isNullOrZero(id)) {
			getSession().removeAttribute("article");
			article = articleService.findArticleById(id);
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
			Article article = articleService.findArticleById(id);
			article.set("status", 0).set("version", article.getLong("version") + 1).set("update_at",DateUtils.getNowDate());
			articleService.updateArticle(article);
			
			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 2, article.getLong("id"), "用户:"+user.getRealName()+"->删除了文章:" + getPara("title"));
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
	// 临时保存文章信息
	public void saveTempArticle(){
		Article article = new Article();
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
		Article article = articleService.findArticleById(id);
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
				Article article = articleService.findArticleById(id);
				article.set("cover", imagePath).set("version", article.getLong("version") + 1).set("update_at", DateUtils.getNowDate());
				
				articleService.updateArticle(article);
				
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
	
	
}
