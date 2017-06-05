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
import cn.ichazuo.service.TypeService;

/**
 * @ClassName: TypeController
 * @Description: (文章类别)
 * @author Zhaoxu
 * @date 2015年12月7日 下午6:24:54
 */
public class TypeController extends BaseController {

	private TypeService service = Duang.duang(TypeService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		long count = service.findTypeCount();
		List<Record> list = service.findTypeList(page);
		list.forEach(info -> {
			info.set("cover", appenUrl(info.getStr("cover")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/type?s=1"));
		render("types.jsp");
	}

	public void edit() {
		Long id = getParaToLong("id", 0L);
		if (!NumberUtils.isNullOrZero(id)) {
			Record record = service.findType(id);
			record.set("cover", appenUrl(record.getStr("cover")));
			setAttr("record", record);
		}
		
		setAttr("articles", service.findAllArticle());
		render("edit.jsp");
	}

	public void save() {
		boolean isHave = false;
		try {
			isHave = getFile("imglive") != null;
		} catch (Exception e) {
			isHave = false;
		}
		if (StringUtils.isNullOrEmpty(getPara("value"), getPara("weight"))) {
			renderJson(error("参数错误!"));
			return;
		}
		try {
			Long id = getParaToLong("id", 0L);
			if (NumberUtils.isNullOrZero(id)) {
				if (!isHave) {
					renderJson(error("请选择封面"));
					return;
				}
				Record record = new Record();
				record.set("value", getPara("value"));
				record.set("dict_id", service.findDictId());
				record.set("weight", getParaToInt("weight", 0));
				record.set("remark", getPara("value"));
				record.set("status", 1);
				String path = new Upload().upload(getFile("imglive"), "cover");
				record.set("cover", path);
				record.set("create_at", DateUtils.getNowDate());
				record.set("version", 1);
				
				record.set("article_id", getParaToLong("articleId"));

				service.saveType(record);
			} else {
				Record record = service.findType(id);
				if (isHave) {
					String path = new Upload().upload(getFile("imglive"), "cover");
					record.set("cover", path);
				}
				record.set("value", getPara("value"));
				record.set("dict_id", service.findDictId());
				record.set("weight", getParaToInt("weight", 0));
				record.set("remark", getPara("value"));
				record.set("update_at", DateUtils.getNowDate());
				record.set("version", record.getLong("version") + 1);
				record.set("article_id", getParaToLong("articleId"));

				service.updateType(record);
			}
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统错误"));
		}
	}

	public void delete() {
		Long id = getParaToLong("id");
		try {
			if (NumberUtils.isNullOrZero(id)) {
				renderJson(error("参数缺失"));
				return;
			}
			Long count = service.findArticleCount(id);
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
