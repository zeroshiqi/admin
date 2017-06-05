package cn.ichazuo.service;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.model.Article;

/**
 * @ClassName: ArticleService 
 * @Description: (文章Service) 
 * @author ZhaoXu
 * @date 2015年8月4日 下午6:52:27 
 * @version V1.0
 */
public class ArticleService {
	
	/**
	 * @Title: findArticleList 
	 * @Description: (查询文章列表) 
	 * @param page
	 * @param title
	 * @return
	 */
	public List<Article> findArticleList(int page,String title,int type){
		String sql = " from t_article a left join s_dict_item i on i.id = a.type where a.status = 1 and a.show_type = ? ";
		if(!StringUtils.isNullOrEmpty(title)){
			sql += " and a.title like '%"+title+"%'";
		}
		sql += " order by a.id desc";
		return Article.dao.paginate(page, Result.PAGE_COUNT, "select a.*,(select count(*) from t_article_comment where status = 1 and article_id = a.id) as commentCount,i.value ", sql,type).getList();
	}
	
	/**
	 * @Title: findArticleCount 
	 * @Description: (查询文章总数) 
	 * @param title
	 * @return
	 */
	public Long findArticleCount(String title,int type){
		String sql = "select count(*) from t_article a where a.status = 1 and a.show_type = ?  ";
		if(!StringUtils.isNullOrEmpty(title)){
			sql += " and a.title like '%"+title+"%'";
		}
		return Db.queryLong(sql,type);
	}
	
	/**
	 * @Title: findArticleById 
	 * @Description: (根据id查询文章) 
	 * @param id
	 * @return
	 */
	public Article findArticleById(Long id){
		return Article.dao.findById(id);
	}
	
	/**
	 * @Title: updateArticle 
	 * @Description: (修改文章) 
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Article updateArticle(Article article) throws Exception{
		if(article.update()){
			return article;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: saveArticle 
	 * @Description: (保存文章) 
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Article saveArticle(Article article) throws Exception{
		if(article.save()){
			return article;
		}else{
			throw new Exception();
		}
	}
}
