package cn.ichazuo.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @ClassName: QuestionInfo 
 * @Description: (映射题目信息表) 
 * @author ZhaoXu
 * @date 2015年8月1日 下午1:21:16 
 * @version V1.0
 */
public class QuestionInfo extends Model<QuestionInfo> {
	private static final long serialVersionUID = 1L;
	// DAO
	public static final QuestionInfo dao = new QuestionInfo();
	
	/**
	 * @Title: getQuestion 
	 * @Description: (获得Question) 
	 * @return
	 */
	public Question getQuestion(){
		return Question.dao.findById(getLong("parent_id"));
	}

}
