package cn.ichazuo.common.utils.model;

import java.io.Serializable;

import cn.ichazuo.common.Result;

/**
 * @ClassName: Page
 * @Description: (分页)
 * @author ZhaoXu
 * @date 2015年8月1日 下午2:23:16
 * @version V1.0
 */
public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
	private int page; // 当前页数
	private Long count; // 总条数
	private String url; // url

	public Page(int page, long count,String url) {
		this.page = page;
		this.count = count;
		this.url = url;
	}

	// 获得总页数
	public long getPageCount() {
		long temp = count % Result.PAGE_COUNT;
		if (temp == 0) {
			return count / Result.PAGE_COUNT;
		} else {
			return (count / Result.PAGE_COUNT) + 1;
		}
	}
	
	//获得总数
	public Long getCount(){
		return count;
	}

	// 获得当前页数
	public int getPage() {
		return page;
	}
	
	// 获得Url
	public String getUrl() {
		return url;
	}

}
