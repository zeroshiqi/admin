package cn.ichazuo.controller;

import java.math.BigDecimal;
import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.MobileUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.CrowdfundingService;

/**
 * @ClassName: CrowdfundingController
 * @Description: (众筹Controller)
 * @author ZhaoXu
 * @date 2015年9月16日 下午1:15:41
 * @version V1.0
 */
public class CrowdfundingController extends BaseController {
	
	// 退款后发送的短信
	private static final String sendMSG = "【插坐学院】#name#，很遗憾，由于众酬时间截止，学费没有全部筹集，插坐菌含泪将筹集到的#price#元学费，全额原路退还。有这么多小伙伴支持你学习，真幸福，加油提升自己吧！";
	
	private CrowdfundingService crowdfundingService = Duang.duang(CrowdfundingService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		long count = crowdfundingService.findAllFailOrderCount();

		setAttr("list", crowdfundingService.findAllFailOrders(page));
		setAttr("page", new Page(page, count, "/admin/crowdfunding?s=1"));

		render("orders.jsp");
	}

	public void crowd() {
		int select = getParaToInt("select",-1);
		int page = getParaToInt("page", 1);
		String name = getPara("name","");
		long count = crowdfundingService.findAllCrowdCount(select,name);
		setAttr("list", crowdfundingService.findAllCrowd(page,select,name));
		setAttr("page", new Page(page, count, "/admin/crowdfunding/crowd?select="+select));
		
		setAttr("name", name);
		setAttr("select", select);
		render("crowd.jsp");
	}

	public void updateStatus() {
		long id = getParaToLong("id", 0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		try{
			List<Record> users = crowdfundingService.findRefundList(id);
			users.forEach(info -> {
				String mobile = info.getStr("mobile");
				
				BigDecimal sum = crowdfundingService.findSumPrice(id);
				if(sum.compareTo(new BigDecimal(0)) > 0){
					try {
						MobileUtils.send(mobile, sendMSG.replaceAll("#name#", info.getStr("name")).replaceAll("#price#", String.valueOf(sum)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			crowdfundingService.updateRefund(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		renderJson(ok());
	}

	public void info() {
		int page = getParaToInt("page", 1);
		long cid = getParaToLong("cid", 0L);
		if (NumberUtils.isNullOrZero(cid)) {
			redirect("/crowdfunding/crowd");
			return;
		}
		long count = crowdfundingService.findInfoCount(cid);
		setAttr("list", crowdfundingService.findInfoList(page, cid));
		setAttr("page", new Page(page, count, "/admin/crowdfunding/info?cid=" + cid));

		render("info.jsp");
	}
}
