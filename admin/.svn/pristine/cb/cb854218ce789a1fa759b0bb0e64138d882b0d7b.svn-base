package cn.ichazuo.controller;

import com.jfinal.aop.Duang;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.FeedbackService;

/**
 * @ClassName: FeedBackController 
 * @Description: (意见反馈Controller) 
 * @author ZhaoXu
 * @date 2015年8月12日 上午11:31:56 
 * @version V1.0
 */
public class FeedBackController extends BaseController{
	private FeedbackService feedbackService = Duang.duang(FeedbackService.class);
	
	public void index(){
		Integer page = getParaToInt("page",1);
		long count = feedbackService.findFeedBackCount();
		
		setAttr("list",feedbackService.findFeedBackList(page));
		setAttr("page", new Page(page,count,"/admin/feedback?s=1"));
		render("feedback.jsp");
	}
	public void business(){
		Integer page = getParaToInt("page",1);
		long count = feedbackService.findBusinessFeedBackCount();
		setAttr("list",feedbackService.findBusinessFeedBackList(page));
		setAttr("page", new Page(page,count,"/admin/feedback/business?s=1"));
		render("businessfeedback.jsp");
	}
}
