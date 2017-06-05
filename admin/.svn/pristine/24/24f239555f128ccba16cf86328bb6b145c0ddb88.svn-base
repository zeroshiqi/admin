package cn.ichazuo.service;

import java.util.List;

import cn.ichazuo.model.DictItem;

/**
 * @ClassName: DictItemService 
 * @Description: (数据字典项Service) 
 * @author ZhaoXu
 * @date 2015年8月1日 下午4:31:43 
 * @version V1.0
 */
public class DictItemService {
	
	/**
	 * @Title: findAll 
	 * @Description: (查询全部数据字典项) 
	 * @param code
	 * @return
	 */
	public List<DictItem> findAll(String code){
		return DictItem.dao.find("select s.* from s_dict_item s left join s_dict d on s.dict_id = d.id where s.`status` = 1 and d.`code` = ?",code);
	}
}
