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
import cn.ichazuo.service.TicketService;

/**
 * @ClassName: AppCommentController 
 * @Description: (好多课课程评论Controller) 
 * @author LDY
 * @date 2016-7-4 14:49:30 
 * @version V1.0
 */
public class AppCourseExamController extends BaseController{
	private TicketService service  = Duang.duang(TicketService.class);
	
	public void index(){
		try{
			int page = getParaToInt("page", 1);
			long score = getParaToLong("score",0L);
			String topParentName = getPara("topParentName");
			String courseName = getPara("courseName");
			String title = getPara("title");
			long count = service.findAppCount(courseName,title);
			List<Record> list = service.findAppList(page,courseName,title);
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/appticket?s=1&courseName="+courseName+"&title="+title));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		render("appcourse.jsp");
	}
	
	public void queryTicketsByTopParentId(){
		Long topParentId = getParaToLong("topParentId");
		try{
			int page = getParaToInt("page", 1);
			long count = service.findCountByTopParentId(topParentId);
			List<Record> list = service.findListByTopParentId(page,topParentId);
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/ticket?s=1&topParentId="+topParentId));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		render("tickets.jsp");
	}
	
	public void queryTicketsByParentId(){
		Long parentId = getParaToLong("parentId");
		try{
			int page = getParaToInt("page", 1);
			long count = service.findCountByParentId(parentId);
			List<Record> list = service.findListByParentId(page,parentId);
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/ticket?s=1&parentId="+parentId));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		render("tickets.jsp");
	}
	//查询某个试卷下的答题名单
	public void queryEmployee(){
		Long id = getParaToLong("id");
		try{
			int page = getParaToInt("page", 1);
			long count = service.findAppEmployeeCountByExamId(id);
			List<Record> list = service.findAppEmployeeListByExamId(page,id);
			//如果答题人数大于0，查询每个人在该课程的最高分、最低分和考试次数
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Integer employeeId = list.get(i).get("employeeId");
					//最高分
					List<Record> list1 = service.findAppEmployeeMaxScoreByExamId(id,employeeId);
					//最低分
					List<Record> list2 = service.findAppEmployeeMinScoreByExamId(id,employeeId);
					//考试次数
					long examCount = service.findAppEmployeeExamCountByExamId(id,employeeId);
					Integer maxScore = list1.get(0).get("score");
					Integer minScore = list2.get(0).get("score");
					list.get(i).set("maxScore", maxScore);
					list.get(i).set("minScore", minScore);
					list.get(i).set("ecount", examCount);
				}
			}
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/appticket/queryEmployee?s=1&id="+id));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		render("appemployee.jsp");
	}
	
	//查询某个学员的答题明细
	public void queryEmployeeExamDetail(){
		Long id = getParaToLong("id");
//		Long employeeId = getParaToLong("employeeId");
		String courseName = getPara("courseName");
		try{
			int page = getParaToInt("page", 1);
			long count = service.findAppEmployeeExamDetailCount(id,courseName);
			List<Record> list = service.findAppEmployeeExamDetailList(page,id,courseName);
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/appticket/queryEmployeeExamDetail?s=1&id="+id+"&courseName="+courseName));
			setAttr("employeeId",id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		render("tickets.jsp");
	}
	
}
