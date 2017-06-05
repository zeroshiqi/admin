package cn.ichazuo.service;

import java.util.List;

import cn.ichazuo.model.PlayAddress;

/**
 * @ClassName: PlayAddressService 
 * @Description: (播放地址Service) 
 * @author ZhaoXu
 * @date 2015年8月2日 下午10:31:06 
 * @version V1.0
 */
public class PlayAddressService {
	
	/**
	 * @Title: findAllAddress 
	 * @Description: (查询全部播放地址) 
	 * @return
	 */
	public List<PlayAddress> findAllAddress(){
		return PlayAddress.dao.find("select * from t_play_address where status = 1");
	}
}
