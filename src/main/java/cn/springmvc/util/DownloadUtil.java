package cn.springmvc.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DownloadUtil {

	private static Logger log = Logger.getLogger(DownloadUtil.class);

	public static void downloadFile(HttpServletResponse response,
			String fileName, byte[] fileByte) {
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			response.addHeader("Content-Length", "" + fileByte.length);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(
					response.getOutputStream());
			outputStream.write(fileByte);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	public static void downloadExcel(HttpServletResponse response,
			HSSFWorkbook workbook, String fileName) {
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			response.addHeader("Content-Lenght", ""
					+ workbook.getBytes().length);
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(
					response.getOutputStream());
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
}
