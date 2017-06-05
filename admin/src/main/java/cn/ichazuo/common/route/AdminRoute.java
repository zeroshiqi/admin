package cn.ichazuo.common.route;

import com.jfinal.config.Routes;

import cn.ichazuo.controller.AppCommentController;
import cn.ichazuo.controller.AppCourseExamController;
import cn.ichazuo.controller.ArticleController;
import cn.ichazuo.controller.BannerController;
import cn.ichazuo.controller.BusinessController;
import cn.ichazuo.controller.CatalogController;
import cn.ichazuo.controller.CommentController;
import cn.ichazuo.controller.CompanyController;
import cn.ichazuo.controller.CourseController;
import cn.ichazuo.controller.CourseTypeController;
import cn.ichazuo.controller.CrowdfundingController;
import cn.ichazuo.controller.FeedBackController;
import cn.ichazuo.controller.ImageController;
import cn.ichazuo.controller.IndexController;
import cn.ichazuo.controller.InfoController;
import cn.ichazuo.controller.InvoiceController;
import cn.ichazuo.controller.JobController;
import cn.ichazuo.controller.KeywordsController;
import cn.ichazuo.controller.LoginController;
import cn.ichazuo.controller.MemberController;
import cn.ichazuo.controller.OnlineController;
import cn.ichazuo.controller.ComplexReport;
import cn.ichazuo.controller.OrderController;
import cn.ichazuo.controller.OrderCountController;
import cn.ichazuo.controller.OrderReport;
import cn.ichazuo.controller.PayNewReport;
import cn.ichazuo.controller.PushController;
import cn.ichazuo.controller.QuestionController;
import cn.ichazuo.controller.QuestionFirstTypeController;
import cn.ichazuo.controller.QuestionSecondTypeController;
import cn.ichazuo.controller.RegisterReport;
import cn.ichazuo.controller.SecondCatalogController;
import cn.ichazuo.controller.SelfStudyController;
import cn.ichazuo.controller.StudentController;
import cn.ichazuo.controller.TeacherController;
import cn.ichazuo.controller.TicketController;
import cn.ichazuo.controller.TypeController;

/**
 * @ClassName: AdminRoute
 * @Description: (后端路由)
 * @author ZhaoXu
 * @date 2015年8月1日 上午9:50:04
 * @version V1.0
 */
public class AdminRoute extends Routes {

	@Override
	public void config() {
		add("/", IndexController.class);
		add("/login", LoginController.class);
		add("/member", MemberController.class, "member");
		add("/course", CourseController.class, "course");
		add("/article", ArticleController.class, "article");
		add("/comment", CommentController.class, "comment");
		add("/feedback", FeedBackController.class, "feedback");
		add("/push", PushController.class, "push");
		add("/order", OrderController.class, "order");
		add("/question", QuestionController.class, "question");
		add("/typeone", QuestionFirstTypeController.class, "questiontype");
		add("/typetwo", QuestionSecondTypeController.class, "questiontypetwo");
		add("/crowdfunding", CrowdfundingController.class, "crowdfunding");
		add("/image", ImageController.class, "image");
		add("/teacher", TeacherController.class, "teacher");
		add("/ticket", TicketController.class,"ticket");
		add("/type", TypeController.class, "types");
		add("/info", InfoController.class,"info");
		add("/job", JobController.class,"job");
		add("/ordercount", OrderCountController.class, "ordercount");
		add("/complex", ComplexReport.class, "complex");
		add("/orderreport", OrderReport.class, "orderreport");
		add("/company", CompanyController.class, "company");
		add("/business", BusinessController.class, "business");
		add("/keywords", KeywordsController.class, "keywords");
		add("/catalog", CatalogController.class, "catalog");
		add("/catalogtwo", SecondCatalogController.class, "catalogtwo");
		add("/invoice", InvoiceController.class, "invoice");
		add("/coursetype", CourseTypeController.class, "coursetype");
		add("/appcomment", AppCommentController.class, "appcomment");
		add("/appticket", AppCourseExamController.class, "appticket");
		add("/online", OnlineController.class, "online");
		add("/banner", BannerController.class, "banner");
		add("/student", StudentController.class, "student");
		add("/selfstudy", SelfStudyController.class, "selfstudy");
		add("/paynew", PayNewReport.class, "paynew");
		add("/register", RegisterReport.class, "register");
	}

}
