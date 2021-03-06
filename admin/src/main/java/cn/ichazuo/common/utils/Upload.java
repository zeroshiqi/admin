package cn.ichazuo.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

public class Upload {
	public String upload(UploadFile file, String folder) {
		String resultPath = "";
		// 获得文件后缀
		String suffix = getSuffix(file);
		// 创建随机文件名
		String uuidFileName = CodeUtils.getUUID();
		// 获得当前时间作为文件夹
		String nowDate = DateUtils.getCurrentDate(DateUtils.DATE_FORMAT_NORMAL);
		// 保存路径
		String path = PropKit.get("upload.path") + folder + File.separator + nowDate + File.separator;
		
		try {
			File targetFile = new File(path, uuidFileName + suffix);
			if (!targetFile.exists()) {
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				targetFile.createNewFile();
			}
			File source = file.getFile();
			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] bts = new byte[300];
            while (fis.read(bts, 0, 300) != -1) {
                fos.write(bts, 0, 300);
            }
            fos.close();
            fis.close();
            source.delete();
			resultPath = File.separator + folder + File.separator + nowDate + File.separator + uuidFileName + suffix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPath;
	}

	/**
	 * @Title: getSuffix
	 * @Description: (获得后缀名)
	 * @param file
	 * @return
	 */
	public String getSuffix(UploadFile file) {
		// 原文件名
		String oldFileName = file.getOriginalFileName();
		// 获得文件后缀
		String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
		return suffix;
	}
	
	public String uploadPdf(UploadFile file, String folder) {
		String resultPath = "";
		// 获得文件后缀
		String suffix = getSuffix(file);
		// 创建随机文件名
		String uuidFileName = CodeUtils.getUUID();
		// 获得当前时间作为文件夹
		String nowDate = DateUtils.getCurrentDate(DateUtils.DATE_FORMAT_NORMAL);
		// 保存路径
		String path = PropKit.get("upload.path") + folder + "/" + nowDate + "/";
		
		try {
			File targetFile = new File(path, uuidFileName + suffix);
			if (!targetFile.exists()) {
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				targetFile.createNewFile();
			}
			File source = file.getFile();
			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] bts = new byte[300];
            while (fis.read(bts, 0, 300) != -1) {
                fos.write(bts, 0, 300);
            }
            fos.close();
            fis.close();
            source.delete();
			resultPath = "/" + folder + "/" + nowDate + "/" + uuidFileName + suffix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPath;
	}
}
