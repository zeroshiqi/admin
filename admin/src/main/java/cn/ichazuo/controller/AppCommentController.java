package cn.ichazuo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.ExportModel2;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.ArticleComment;
import cn.ichazuo.model.CourseOfflineComment;
import cn.ichazuo.service.CommentService;
import cn.ichazuo.service.LogService;

/**
 * @ClassName: AppCommentController 
 * @Description: (好多课课程评论Controller) 
 * @author LDY
 * @date 2016-7-4 14:49:30 
 * @version V1.0
 */
public class AppCommentController extends BaseController{
	private CommentService commentService = Duang.duang(CommentService.class);
	
	//插坐学院好多课APP课程评论列表
	public void index() throws UnsupportedEncodingException{
		String courseName = getPara("courseName");
		String comment = getPara("comment");
		int page = getParaToInt("page", 1);
		long count = commentService.findAppCommentCount(comment,courseName);
		List<Record> list = commentService.findAppCommentList(page,comment,courseName);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				list.get(i).set("comment", java.net.URLDecoder.decode(list.get(i).getStr("comment"),"utf-8"));
			}
		}
		setAttr("list", list);
//		setAttr("page", new Page(page,count,"/admin/invoice?s=1"));
		setAttr("page", new Page(page, count, "/admin/appcomment?s=1"));
		render("offlinecomment.jsp");
	}
	
	//删除线下课程评论
	public void deleteOfflineComment(){
		try{
			Long id = getParaToLong("id",0L);
			if(NumberUtils.isNullOrZero(id)){
				renderJson("参数缺失");
			}
			commentService.deleteAppCourseComment(id);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
}
