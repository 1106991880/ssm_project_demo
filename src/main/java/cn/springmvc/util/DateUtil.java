package cn.springmvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ʱ���ʽ��������
 * 
 * @author dongdong
 *
 */

public class DateUtil {

	private static SimpleDateFormat sdf;

	public static String formatDateToString(Date date) {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null != date) {
			return sdf.format(date);
		} else {
			new Exception("date format error");
			return null;
		}
	}

	public static Date formatDateToDate(Date date) {
		Date formatDate = null;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (null != date) {
				formatDate = sdf.parse(sdf.format(date));
				return formatDate;
			} else {
				new Exception("date format error");
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;
	}
}
