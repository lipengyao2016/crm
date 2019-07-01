package com.crm.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.crm.common.MessageException;
import com.crm.common.MessageException;

/**
 * 数据类型处理工具类
 * 
 * @author Administrator
 *
 */
public class DataConvertUtil {

	/**
	 * 转换成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		if (obj == null || "null".equals(obj)) {
			return "";
		}

		return obj.toString();
	}

	/**
	 * 是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		// 先判断是否为整数
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		}

		// 这个正则表达式会将0~9当做非法数字
		pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 转换成long类型
	 * 
	 * @param obj
	 * @return
	 */
	public static long getLong(Object obj) {
		String str = getString(obj);
		if ("".equals(str)) {
			return 0;
		}
		if (!isNumeric(str)) {
			throw new MessageException("非法数字");
		}
		return Long.parseLong(str);
	}

	/**
	 * 转换成double类型
	 * 
	 * @param obj
	 * @return
	 */
	public static double getDouble(Object obj) {
		String str = getString(obj);
		if ("".equals(str)) {
			return 0.00;
		}
		if (!isNumeric(str)) {
			throw new MessageException("非法数字");
		}

		BigDecimal bg = new BigDecimal(str);
		return bg.doubleValue();
	}

	/**
	 * 转换成double类型
	 * 
	 * @param obj
	 * @param scale
	 *            指定保留几位小数
	 * @return
	 */
	public static double getDouble(Object obj, int scale) {
		String str = getString(obj);
		if ("".equals(str)) {
			return 0.00;
		}
		if (!isNumeric(str)) {
			throw new MessageException("非法数字");
		}

		BigDecimal bg = new BigDecimal(str);
		double d = bg.doubleValue();
		return round(d, scale);
	}

	/**
	 * 转换成int类型
	 * 
	 * @param obj
	 * @return
	 */
	public static int getInt(Object obj) {
		String str = getString(obj);
		if ("".equals(str)) {
			return 0;
		}
		if (!isNumeric(str)) {
			throw new MessageException("非法数字");
		}
		return Integer.valueOf(obj.toString());
	}

	/**
	 * 转换成getBigDecimal类型
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal getBigDecimal(Object obj) {
		String str = getString(obj);
		if ("".equals(str)) {
			return new BigDecimal("0.00");
		}
		if (!isNumeric(str)) {
			throw new MessageException("非法数字");
		}

		return new BigDecimal(str);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("scale必须为正数或0");
		}
		BigDecimal b = new BigDecimal(v + "");
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
