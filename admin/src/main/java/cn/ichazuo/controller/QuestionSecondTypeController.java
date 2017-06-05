package cn.ichazuo.controller;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.CodeUtils;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Member;
import cn.ichazuo.model.MemberInfo;
import cn.ichazuo.model.Question;
import cn.ichazuo.model.QuestionInfo;
import cn.ichazuo.service.QuestionService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class QuestionSecondTypeController extends BaseController{
	private QuestionService service = Duang.duang(QuestionService.class);
	
	public void index(){
		Integer page = getParaToInt("page", 1);
		String name = getPara("name");
		long count = service.findQuestionTypeTwoCount(name);
		setAttr("list",service.findQuestionTypeTwoList(page,name));
		setAttr("page", new Page(page,count,"/admin/typetwo?s=1"));
		render("typetwo.jsp");
	}
	
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		service.deleteSecondTypeById(id);
		renderJson(ok());
	}
	
	public void edit(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", service.findSecondTypeById(id));
		}
		render("editTypeTwo.jsp");
	}
	//复制一份考卷的内容重新生成一套试卷
	public void copy(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("试卷无效"));
			return;
		}
		try{
			JSONArray arr = new JSONArray();
			Record secondRecord = service.findSecondTypeById(id);
			if(secondRecord==null){
				renderJson(error("试卷无效"));
				return;
			}else{
				//先复制一份二级目录
				Record record = new Record();
				Date date = new Date();
				record.set("status", 1);
				record.set("title", secondRecord.getStr("title")+"-副本");
				record.set("parent_id", secondRecord.getInt("parent_id"));
				record.set("parent_name", secondRecord.getStr("parent_name"));
				record.set("update_at", date);
				record.set("number", secondRecord.getInt("number"));
				record.set("create_at", date);
				service.saveSecondType(record);
				//查询此二级目录下的所有试题
				List<Record> list =service.findAllQuestionByParentId(id);
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						Record record1 = new Record();
						Date date1 = new Date();
						record1.set("status", 1);
						record1.set("a", list.get(i).getStr("a"));
						record1.set("b", list.get(i).getStr("b"));
						record1.set("c", list.get(i).getStr("c"));
						record1.set("d", list.get(i).getStr("d"));
						record1.set("answer", list.get(i).getStr("answer"));
						record1.set("title", list.get(i).getStr("title"));
						record1.set("user_name", list.get(i).getStr("user_name"));
						record1.set("type", list.get(i).getInt("type"));
						record1.set("parent_id", record.getLong("id"));
						record1.set("parent_name", record.getStr("title"));
						record1.set("question_type", list.get(i).getInt("question_type"));
						record1.set("judge", list.get(i).getInt("judge"));
						record1.set("multiple_choice", list.get(i).getStr("multiple_choice"));
						service.save(record1);
					}
				}
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常，请联系管理员"));
		}
	}
	public void save(){
		Long id = getParaToLong("id",0L);
		String title = getPara("title");
		Long parentId = getParaToLong("parentId",0L);
		Long number = getParaToLong("number",0L);
		String parentName = getPara("parentName");
		if(StringUtils.isNullOrEmpty(title)){
			renderJson(error("参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("status", 1);
		record.set("title", title);
		record.set("parent_id", parentId);
		record.set("parent_name", parentName);
		record.set("update_at", date);
		record.set("number", number);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("create_at", date);
				service.saveSecondType(record);
			}else{
				record.set("id", id);
				service.updateSecondType(record);
				service.updateQuestionByParentId(id, title);
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	
	public void query(){
		String title = getPara("demo");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> memberList = service.findSecondType(title);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("title")));
			arr.add(json);
		}
		renderJson(arr);
	}
	public void queryByParentId(){
		Long parentId = getParaToLong("id", 0L);
		Integer page = getParaToInt("page", 1);
		long count = service.findSecondTypeCountByParentId(parentId);
		setAttr("list",service.findSecondTypeListByParentId(page,parentId));
		setAttr("page", new Page(page,count,"/admin/typetwo.jsp?s=1"));
		render("typetwo.jsp");
	}
//	//导入xls
//		public void saveQuestions(){
//			try{
//				UploadFile file = getFile("XLSFile");
//				Workbook book =  Workbook.getWorkbook(new FileInputStream(file.getFile()));   
//				
//				Sheet readsheet = book.getSheet(0); 
//				//获取Sheet表中所包含的总行数   
//	            int rsRows = readsheet.getRows();   
//	            Map<Question,QuestionInfo> questions = new HashMap<>();
//	            Map<Question,Long> courseIds = new HashMap<>();
//	            for(int i=1;i<rsRows;i++){
//	            	Question question = new Question();
//	            	
////	            	Cell name = readsheet.getCell(0, i);   
////	            	if(StringUtils.isNullOrEmpty(name.getContents())){
////	            		continue;
////	            	}
//	            	question.set("status", "1");
//	            	//获取Excel导入模板中的问题内容(第一列)
//	            	Cell title = readsheet.getCell(0, i);
//	            	question.set("title", title.getContents());
//	            	//获取Excel导入模板中的A选项的内容(第二列)
//	            	Cell A = readsheet.getCell(1, i);
//	            	question.set("a", A.getContents());
//	            	//获取Excel导入模板中的B选项的内容(第三列)
//	            	Cell B = readsheet.getCell(2, i);
//	            	question.set("b", B.getContents());
//	            	//获取Excel导入模板中的C选项的内容(第四列)
//	            	Cell C = readsheet.getCell(3, i);
//	            	question.set("c", C.getContents());
//	            	//获取Excel导入模板中的D选项的内容(第五列)
//	            	Cell D = readsheet.getCell(4, i);
//	            	question.set("d", D.getContents());
//	            	//获取Excel导入模板中的答案的内容(第六列)
//	            	Cell answer = readsheet.getCell(5, i);
//	            	question.set("answer", answer.getContents());
//	            	//获取Excel导入模板中的出题人的内容(第七列)
//	            	Cell userName = readsheet.getCell(6, i);
//	            	question.set("user_name", userName.getContents());
//	            	//获取试题类型(第八列)
//	            	Cell typeName = readsheet.getCell(7, i);
//	            	if(typeName.getContents()=="基础"){
//	            		question.set("type", "1");
//	            	}else if(typeName.getContents()=="增强"){
//	            		question.set("type", "2");
//	            	}
//	            	
//	            	Cell birthday = readsheet.getCell(3, i);   
//	            	String temp = birthday.getContents();
//	            	if(StringUtils.isNullOrEmpty(temp)){
//	            		temp = "2015-1-1";
//	            		memberInfo.set("birthday", DateUtils.parseDate(temp,DateUtils.DATE_FORMAT_NORMAL));
//	            	}else{
//	            		memberInfo.set("birthday", DateUtils.parseDate(birthday.getContents(),DateUtils.DATE_FORMAT_NORMAL));
//	            	}
//	            	Cell courseIdCell = readsheet.getCell(7, i);   
//	            	Long courseId = Long.parseLong(StringUtils.isNullOrEmpty(courseIdCell.getContents()) ? "0" : courseIdCell.getContents());
//	            	
//	            	courseIds.put(member, courseId);
//	            	members.put(member, memberInfo);
//	            }
//	            List<String> str = service.saveQuestions(question, questionInfo);
//	            StringBuffer buffer = new StringBuffer();
//	            for(String s : str){
//	            	buffer.append(s).append(",");
//	            }
//	            renderJson(ok("重复数据为:"+buffer.toString()));
//			}catch(Exception e){
//				e.printStackTrace();
//				renderJson(error("系统异常"));
//			}
//		}
}
