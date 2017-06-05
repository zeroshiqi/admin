package cn.ichazuo.controller;

import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.ImageService;

/**
 * @ClassName: ImageController
 * @Description: (头图Controller)
 * @author ZhaoXu
 * @date 2015年10月15日 下午4:48:31
 * @version V1.0
 */
public class ImageController extends BaseController {
	private ImageService imageService = Duang.duang(ImageService.class);

	public void index() {
		try{
			int page = getParaToInt("page", 1);
			long count = imageService.findImageCount();
			
			List<Record> list = imageService.findImage(page);
			list.forEach(info -> {
				info.set("image_url", appenUrl(info.get("image_url")));
			});
			setAttr("list", list);
			setAttr("page", new Page(page, count, "/admin/image?s=1"));

		}catch(Exception e){
			e.printStackTrace();
		}
		render("image.jsp");
	}

	public void edit() {
		Long id = getParaToLong("id", 0L);
		if (!NumberUtils.isNullOrZero(id)) {
			setAttr("record", imageService.findImageById(id));
		}
		render("edit.jsp");
	}

	// 保存课程图片
	public void saveImage() {
		Long id = getParaToLong("id", 0L);
		String url = getPara("url","");
		String title = getPara("title","");
		int type = getParaToInt("type",0);

		try {
			String path = getPara("path");

			if (NumberUtils.isNullOrZero(id)) {
				Record record = new Record();
				record.set("url", url);
				record.set("create_at", DateUtils.getNowDate());
				if(StringUtils.isNullOrEmpty(path)){
					renderJson(error("参数缺失"));
					return;
				}
				record.set("image_url", path);
				record.set("title", title);
				record.set("type", type);
				imageService.saveImage(record);
			} else {
				
				Record record = imageService.findImageById(id);
				record.set("url", url);
				record.set("update_at", DateUtils.getNowDate());
				if(!StringUtils.isNullOrEmpty(path)){
					record.set("image_url", path);
				}
				record.set("type", type);
				record.set("version", record.getLong("version") + 1);
				record.set("title", title);
				imageService.updateImageInfo(record);
			}

			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}

	public void delete() {
		Long id = getParaToLong("id");
		try {
			if (NumberUtils.isNullOrZero(id)) {
				renderJson(error("参数缺失"));
				return;
			}
			imageService.updateImage(id);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("msg"));
		}
	}
}
