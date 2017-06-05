package cn.ichazuo.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.ExportExcel;
import cn.ichazuo.common.utils.ExportModel2;
import cn.ichazuo.common.utils.InvoiceExportModel;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.FeedbackService;
import cn.ichazuo.service.InvoiceService;

/**
 * @ClassName: InvoiceController 
 * @Description: (发票Controller) 
 * @author ZhaoXu
 * @date 2015年8月12日 上午11:31:56 
 * @version V1.0
 */
public class InvoiceController extends BaseController{
	private InvoiceService invoiceService = Duang.duang(InvoiceService.class);
	
	public void index(){
		Integer page = getParaToInt("page",1);
		String invoiceTitle = getPara("invoiceTitle");
		String courseName = getPara("courseName");
		String name = getPara("nickName");
		if(StringUtils.isNullOrEmpty(courseName)){
			courseName="";
		}
		if(StringUtils.isNullOrEmpty(invoiceTitle)){
			invoiceTitle="";
		}
		if(StringUtils.isNullOrEmpty(name)){
			name="";
		}
		long count = invoiceService.findInvoiceCount(invoiceTitle,courseName,name);
		setAttr("list",invoiceService.findInvoiceList(page,invoiceTitle,courseName,name));
		setAttr("page", new Page(page,count,"/admin/invoice?s=1&courseName="+courseName+"&nickName="+name+"&invoiceTitle="+invoiceTitle));
		setAttr("courseName",courseName);
		setAttr("nickName",name);
		setAttr("invoiceTitle",invoiceTitle);
		render("invoice.jsp");
	}
	//切换状态为已处理
	public void dispose(){
		Long id = getParaToLong("id",0L);
		Integer page = getParaToInt("page",1);
		invoiceService.updateInvoice(id);
		String invoiceTitle = getPara("invoiceTitle");
		String courseName = getPara("courseName");
		String name = getPara("nickName");
		if(StringUtils.isNullOrEmpty(courseName)){
			courseName="";
		}
		if(StringUtils.isNullOrEmpty(invoiceTitle)){
			invoiceTitle="";
		}
		if(StringUtils.isNullOrEmpty(name)){
			name="";
		}
		long count = invoiceService.findInvoiceCount(invoiceTitle,courseName,name);
		setAttr("list",invoiceService.findInvoiceList(page,invoiceTitle,courseName,name));
		setAttr("page", new Page(page,count,"/admin/invoice?s=1&courseName="+courseName+"&nickName="+name+"&invoiceTitle="+invoiceTitle));
		setAttr("courseName",courseName);
		setAttr("nickName",name);
		setAttr("invoiceTitle",invoiceTitle);
		render("invoice.jsp");
	}
	public void export() {
		try {
			String invoiceTitle = getPara("invoiceTitle");
			String courseName = getPara("courseName");
			String name = getPara("name");
			List<Record> list = invoiceService.findInvoiceListExport(invoiceTitle,courseName,name);
//			List<Record> list = courseService.findOrder(id);
			List<InvoiceExportModel> models = new ArrayList<>();

			list.forEach(info -> {
				InvoiceExportModel model = new InvoiceExportModel(info);

				models.add(model);
			});
			ExportExcel.exportInvoiceExcel("发票列表.xls", models, getResponse());
			renderFile("");
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
}
