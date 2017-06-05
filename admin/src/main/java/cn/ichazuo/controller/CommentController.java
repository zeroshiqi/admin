package cn.ichazuo.controller;

import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.ArticleComment;
import cn.ichazuo.model.CourseOfflineComment;
import cn.ichazuo.service.CommentService;
import cn.ichazuo.service.LogService;

/**
 * @ClassName: CommentController 
 * @Description: (评论Controller) 
 * @author ZhaoXu
 * @date 2015年8月7日 上午10:35:48 
 * @version V1.0
 */
public class CommentController extends BaseController{
	private CommentService commentService = Duang.duang(CommentService.class);
	private LogService logService = Duang.duang(LogService.class);
	
	//线下课程评论列表
	public void offlineComment(){
		Long courseId = getParaToLong("courseId",0L);
		if(NumberUtils.isNullOrZero(courseId)){
			redirect("/offline");
			return;
		}
		int page = getParaToInt("page", 1);
		long count = commentService.findCourseOfflineCommentCount(courseId);
		List<CourseOfflineComment> list = commentService.findCourseOfflineCommentList(page, courseId);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/comment/offlineComment?s=1&courseId="+courseId));
		render("offlinecomment.jsp");
	}
	
	//删除线下课程评论
	public void deleteOfflineComment(){
		try{
			Long id = getParaToLong("id",0L);
			if(NumberUtils.isNullOrZero(id)){
				renderJson("参数缺失");
			}
			
			CourseOfflineComment comment = commentService.findCourseOfflineCommentById(id);
			comment.set("status", 0).set("version", comment.getLong("version") + 1).set("update_at", DateUtils.getNowDate());
			commentService.updateCourseOfflineComment(comment);
			
			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 1, comment.getLong("id"), "用户:"+user.getRealName()+"->删除了线下课程评论:" + comment.getStr("content"));
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
	//文章评论列表
	public void articleComment(){
		Long articleId = getParaToLong("articleId",0L);
		if(NumberUtils.isNullOrZero(articleId)){
			redirect("/article");
			return;
		}
		int page = getParaToInt("page", 1);
		long count = commentService.findArticleCommentCount(articleId);
		List<Record> list = commentService.findArticleCommentList(page, articleId);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/comment/articleComment?s=1&articleId="+articleId));
		render("articlecomment.jsp");
	}

	//删除评论
	public void deleteArticleComment(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("参数错误"));
			return;
		}
		try{
			ArticleComment comment = commentService.findArticleCommentById(id);
			comment.set("status", 0).set("update_at", DateUtils.getNowDate()).set("version", comment.getLong("version") + 1);
			commentService.updateArticleComment(comment);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
}
