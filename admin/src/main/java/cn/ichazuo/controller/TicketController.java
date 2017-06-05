package cn.ichazuo.controller;

import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.TicketService;

public class TicketController extends BaseController{
	
	private TicketService service  = Duang.duang(TicketService.class);
	
	public void index(){
		try{
			int page = getParaToInt("page", 1);
			long score = getParaToLong("score",0L);
			String topParentName = getPara("topParentName");
			String parentName = getPara("parentName");
			long count = service.findCount(score,topParentName,parentName);
			List<Record> list = service.findList(page,score,parentName,topParentName);
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/ticket?s=1&score="+score+"&parentName="+parentName+"&topParentName="+topParentName));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		render("tickets.jsp");
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
}
