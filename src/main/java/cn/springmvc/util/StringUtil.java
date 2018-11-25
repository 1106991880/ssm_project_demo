package cn.springmvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * String������
 * 
 * @author dongdong
 * 
 */

public class StringUtil {

	private static Logger log = Logger.getLogger(StringUtil.class);

	/**
	 * �ж��ַ��Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �ж��ַ��Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		if (obj instanceof String) {
			return !isEmpty(String.valueOf(obj));
		} else {
			if (null != obj) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * StringתDate
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (isNotEmpty(str)) {
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				log.info(e.getCause());
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * StringתDateTime
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDateTime(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if (isNotEmpty(str)) {
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				log.info(e.getCause());
				e.printStackTrace();
			}
		}
		return date;
	}

}
