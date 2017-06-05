package cn.ichazuo.common.utils;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportExcel {
	/***************************************************************************
	 * @param fileName
	 *            EXCEL文件名称
	 * @param listTitle
	 *            EXCEL文件第一行列标题集合
	 * @param listContent
	 *            EXCEL文件正文数据集合
	 * @return
	 */
	public final static String exportExcel(String fileName, List<ExportModel> listContent,
			HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";	
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			sheet.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet.addCell(new Label(1, 0, "性别", wcf_center));
			sheet.addCell(new Label(2, 0, "手机号", wcf_center));
			sheet.addCell(new Label(3, 0, "微信号", wcf_center));
			sheet.addCell(new Label(4, 0, "单位名称", wcf_center));
			sheet.addCell(new Label(5, 0, "职位", wcf_center));
			sheet.addCell(new Label(6, 0, "课程名称", wcf_center));
			sheet.addCell(new Label(7, 0, "订单号", wcf_center));
			sheet.addCell(new Label(8, 0, "支付类型", wcf_center));
			sheet.addCell(new Label(9, 0, "数量", wcf_center));
			sheet.addCell(new Label(10, 0, "价格", wcf_center));
			sheet.addCell(new Label(11, 0, "购买时间", wcf_center));
			sheet.addCell(new Label(12, 0, "收票地址", wcf_center));
			sheet.addCell(new Label(13, 0, "邮箱", wcf_center));
			sheet.addCell(new Label(14, 0, "审核状态", wcf_center));
			sheet.addCell(new Label(15, 0, "退款状态", wcf_center));
			sheet.addCell(new Label(16, 0, "提问", wcf_center));
			sheet.addCell(new Label(17, 0, "是否赠送", wcf_center));

			/** ***************以下是EXCEL正文数据********************* */
			int i = 1;
			for (ExportModel obj : listContent) {
//				sheet.addCell(new Label(0, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(1, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(2, i, obj.getMobile(), wcf_left));
//				sheet.addCell(new Label(3, i, obj.getNumber() + "", wcf_left));
//				sheet.addCell(new Label(4, i, obj.getPrice() + "", wcf_left));
//				sheet.addCell(new Label(5, i, obj.getCoursename(), wcf_left));
//				sheet.addCell(new Label(6, i, obj.getUpdateTime(), wcf_left));
//				sheet.addCell(new Label(7, i, obj.getType(), wcf_left));
//				sheet.addCell(new Label(8, i, obj.getWeixin(), wcf_left));
//				sheet.addCell(new Label(9, i, obj.getWork(), wcf_left));
//				sheet.addCell(new Label(10, i, obj.getJob(), wcf_left));
				sheet.addCell(new Label(0, i, obj.getName(), wcf_left));
				sheet.addCell(new Label(1, i, obj.getSex(), wcf_left));
				sheet.addCell(new Label(2, i, obj.getMobile(), wcf_left));
				sheet.addCell(new Label(3, i, obj.getWeixin(), wcf_left));
				sheet.addCell(new Label(4, i, obj.getWork(), wcf_left));
				sheet.addCell(new Label(5, i, obj.getJob(), wcf_left));
				sheet.addCell(new Label(6, i, obj.getCoursename(), wcf_left));
				sheet.addCell(new Label(7,i,obj.getCode(),wcf_left));
				sheet.addCell(new Label(8, i, obj.getType(), wcf_left));
				sheet.addCell(new Label(9, i, obj.getNumber() + "", wcf_left));
				sheet.addCell(new Label(10, i, obj.getPrice() + "", wcf_left));
				sheet.addCell(new Label(11, i, obj.getUpdateTime(), wcf_left));
				sheet.addCell(new Label(12, i, obj.getAddress(), wcf_left));
				sheet.addCell(new Label(13, i, obj.getEmail(), wcf_left));
				sheet.addCell(new Label(14, i, obj.getReviewStatus(), wcf_left));
				sheet.addCell(new Label(15, i, obj.getRefundResult(), wcf_left));
				sheet.addCell(new Label(16, i, obj.getContent(), wcf_left));
				sheet.addCell(new Label(17, i, obj.getIsGift(), wcf_left));
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}
	
	public final static String exportExcel2(String fileName, List<ExportModel2> listContent,
			HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";	
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			sheet.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet.addCell(new Label(1, 0, "性别", wcf_center));
			sheet.addCell(new Label(2, 0, "手机号", wcf_center));
			sheet.addCell(new Label(3, 0, "微信号", wcf_center));
			sheet.addCell(new Label(4, 0, "单位名称", wcf_center));
			sheet.addCell(new Label(5, 0, "职位", wcf_center));
			sheet.addCell(new Label(6, 0, "课程名称", wcf_center));
			sheet.addCell(new Label(7, 0, "讲师", wcf_center));
			sheet.addCell(new Label(8, 0, "订单号", wcf_center));
			sheet.addCell(new Label(9, 0, "支付类型", wcf_center));
			sheet.addCell(new Label(10, 0, "数量", wcf_center));
			sheet.addCell(new Label(11, 0, "价格", wcf_center));
			sheet.addCell(new Label(12, 0, "购买时间", wcf_center));
			sheet.addCell(new Label(13, 0, "收票地址", wcf_center));
			sheet.addCell(new Label(14, 0, "提问", wcf_center));
			sheet.addCell(new Label(15, 0, "推广码", wcf_center));

			/** ***************以下是EXCEL正文数据********************* */
			int i = 1;
			for (ExportModel2 obj : listContent) {
//				sheet.addCell(new Label(0, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(1, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(2, i, obj.getMobile(), wcf_left));
//				sheet.addCell(new Label(3, i, obj.getNumber() + "", wcf_left));
//				sheet.addCell(new Label(4, i, obj.getPrice() + "", wcf_left));
//				sheet.addCell(new Label(5, i, obj.getCoursename(), wcf_left));
//				sheet.addCell(new Label(6, i, obj.getUpdateTime(), wcf_left));
//				sheet.addCell(new Label(7, i, obj.getType(), wcf_left));
//				sheet.addCell(new Label(8, i, obj.getWeixin(), wcf_left));
//				sheet.addCell(new Label(9, i, obj.getWork(), wcf_left));
//				sheet.addCell(new Label(10, i, obj.getJob(), wcf_left));
				sheet.addCell(new Label(0, i, obj.getName(), wcf_left));
				sheet.addCell(new Label(1, i, obj.getSex(), wcf_left));
				sheet.addCell(new Label(2, i, obj.getMobile(), wcf_left));
				sheet.addCell(new Label(3, i, obj.getWeixin(), wcf_left));
				sheet.addCell(new Label(4, i, obj.getWork(), wcf_left));
				sheet.addCell(new Label(5, i, obj.getJob(), wcf_left));
				sheet.addCell(new Label(6, i, obj.getCoursename(), wcf_left));
				sheet.addCell(new Label(7, i, obj.getTeacherId(), wcf_left));
				sheet.addCell(new Label(8,i,obj.getCode(),wcf_left));
				sheet.addCell(new Label(9, i, obj.getType(), wcf_left));
				sheet.addCell(new Label(10, i, obj.getNumber() + "", wcf_left));
				sheet.addCell(new Label(11, i, obj.getPrice() + "", wcf_left));
				sheet.addCell(new Label(12, i, obj.getUpdateTime(), wcf_left));
				sheet.addCell(new Label(13, i, obj.getAddress(), wcf_left));
				sheet.addCell(new Label(14, i, obj.getContent(), wcf_left));
				sheet.addCell(new Label(15, i, obj.getFrom1(), wcf_left));
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}
	
	public final static String exportInvoiceExcel(String fileName, List<InvoiceExportModel> listContent,
			HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";	
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			sheet.addCell(new Label(0, 0, "课程名称", wcf_center));
			sheet.addCell(new Label(1, 0, "发票收货人", wcf_center));
			sheet.addCell(new Label(2, 0, "收货人手机号", wcf_center));
			sheet.addCell(new Label(3, 0, "金额", wcf_center));
			sheet.addCell(new Label(4, 0, "发票类型", wcf_center));
			sheet.addCell(new Label(5, 0, "发票抬头", wcf_center));
			sheet.addCell(new Label(6, 0, "发票寄送地址", wcf_center));
			sheet.addCell(new Label(7, 0, "备注", wcf_center));
			sheet.addCell(new Label(8, 0, "开课时间", wcf_center));
			sheet.addCell(new Label(9, 0, "发票状态", wcf_center));

			/** ***************以下是EXCEL正文数据********************* */
			int i = 1;
			for (InvoiceExportModel obj : listContent) {
//				sheet.addCell(new Label(0, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(1, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(2, i, obj.getMobile(), wcf_left));
//				sheet.addCell(new Label(3, i, obj.getNumber() + "", wcf_left));
//				sheet.addCell(new Label(4, i, obj.getPrice() + "", wcf_left));
//				sheet.addCell(new Label(5, i, obj.getCoursename(), wcf_left));
//				sheet.addCell(new Label(6, i, obj.getUpdateTime(), wcf_left));
//				sheet.addCell(new Label(7, i, obj.getType(), wcf_left));
//				sheet.addCell(new Label(8, i, obj.getWeixin(), wcf_left));
//				sheet.addCell(new Label(9, i, obj.getWork(), wcf_left));
//				sheet.addCell(new Label(10, i, obj.getJob(), wcf_left));
				sheet.addCell(new Label(0, i, obj.getCourseName(), wcf_left));
				sheet.addCell(new Label(1, i, obj.getInvoiceName(), wcf_left));
				sheet.addCell(new Label(2, i, obj.getInvoiceMobile(), wcf_left));
				sheet.addCell(new Label(3, i, String.valueOf(obj.getPrice()), wcf_left));
				sheet.addCell(new Label(4, i, obj.getInvoiceType(), wcf_left));
				sheet.addCell(new Label(5, i, obj.getInvoiceTitle(), wcf_left));
				sheet.addCell(new Label(6, i, obj.getInvoiceAddress(), wcf_left));
				sheet.addCell(new Label(7, i, obj.getInvoiceRemarks(), wcf_left));
				sheet.addCell(new Label(8,i,obj.getBeginTime(),wcf_left));
				sheet.addCell(new Label(9, i, obj.getInvoiceStatus(), wcf_left));
//				sheet.addCell(new Label(9, i, obj.getNumber() + "", wcf_left));
//				sheet.addCell(new Label(10, i, obj.getPrice() + "", wcf_left));
//				sheet.addCell(new Label(11, i, obj.getUpdateTime(), wcf_left));
//				sheet.addCell(new Label(12, i, obj.getAddress(), wcf_left));
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}
	
	public final static String exportBookOrderExcel(String fileName, List<BookExportModel> listContent,
			HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";	
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			sheet.addCell(new Label(0, 0, "订单号", wcf_center));
			sheet.addCell(new Label(1, 0, "姓名", wcf_center));
			sheet.addCell(new Label(2, 0, "手机号", wcf_center));
			sheet.addCell(new Label(3, 0, "公司", wcf_center));
			sheet.addCell(new Label(4, 0, "购买数量", wcf_center));
			sheet.addCell(new Label(5, 0, "支付金额", wcf_center));
			sheet.addCell(new Label(6, 0, "省份", wcf_center));
			sheet.addCell(new Label(7, 0, "城市", wcf_center));
			sheet.addCell(new Label(8, 0, "地址", wcf_center));
			sheet.addCell(new Label(9, 0, "购买时间", wcf_center));
			sheet.addCell(new Label(10, 0, "发票类型", wcf_center));
			sheet.addCell(new Label(11, 0, "发票抬头", wcf_center));
			sheet.addCell(new Label(12, 0, "留言", wcf_center));
			sheet.addCell(new Label(13, 0, "渠道", wcf_center));
			sheet.addCell(new Label(14, 0, "发货状态", wcf_center));
			sheet.addCell(new Label(15, 0, "快递单号", wcf_center));
			sheet.addCell(new Label(16, 0, "备注", wcf_center));

			/** ***************以下是EXCEL正文数据********************* */
			int i = 1;
			for (BookExportModel obj : listContent) {
//				sheet.addCell(new Label(0, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(1, i, obj.getName(), wcf_left));
//				sheet.addCell(new Label(2, i, obj.getMobile(), wcf_left));
//				sheet.addCell(new Label(3, i, obj.getNumber() + "", wcf_left));
//				sheet.addCell(new Label(4, i, obj.getPrice() + "", wcf_left));
//				sheet.addCell(new Label(5, i, obj.getCoursename(), wcf_left));
//				sheet.addCell(new Label(6, i, obj.getUpdateTime(), wcf_left));
//				sheet.addCell(new Label(7, i, obj.getType(), wcf_left));
//				sheet.addCell(new Label(8, i, obj.getWeixin(), wcf_left));
//				sheet.addCell(new Label(9, i, obj.getWork(), wcf_left));
//				sheet.addCell(new Label(10, i, obj.getJob(), wcf_left));
				sheet.addCell(new Label(0, i, obj.getCode(), wcf_left));
				sheet.addCell(new Label(1, i, obj.getName(), wcf_left));
				sheet.addCell(new Label(2, i, obj.getMobile(), wcf_left));
				sheet.addCell(new Label(3, i, obj.getWork(), wcf_left));
				sheet.addCell(new Label(4, i, obj.getNumber(), wcf_left));
				sheet.addCell(new Label(5, i, obj.getPrice(), wcf_left));
				sheet.addCell(new Label(6, i, obj.getProvince(), wcf_left));
				sheet.addCell(new Label(7, i, obj.getCity(), wcf_left));
				sheet.addCell(new Label(8, i, obj.getAddress(), wcf_left));
				sheet.addCell(new Label(9, i, obj.getUpdateTime(), wcf_left));
				sheet.addCell(new Label(10, i, obj.getInvoiceType(), wcf_left));
				sheet.addCell(new Label(11, i, obj.getInvoiceTitle() + "", wcf_left));
				sheet.addCell(new Label(12, i, obj.getContent(), wcf_left));
				sheet.addCell(new Label(13, i, obj.getFrom1(), wcf_left));
				sheet.addCell(new Label(14, i, obj.getHandleStatus(), wcf_left));
				sheet.addCell(new Label(15, i, obj.getCourierNumber(), wcf_left));
				sheet.addCell(new Label(16, i, obj.getMemo(), wcf_left));
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}
}
