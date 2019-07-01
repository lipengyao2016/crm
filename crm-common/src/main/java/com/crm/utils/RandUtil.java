package com.crm.utils;

import com.crm.common.MessageException;
import com.crm.common.MessageException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.List;

public class RandUtil {
	public static int randInt(int min, int max) {
		return (int) Math.round(Math.random() * (max - min)) + min;
	}

	public static Object randListValue(List<?> list) {
		return list.get(randInt(0, list.size() - 1));
	}

	public static Object randArrayValue(Object[] array) {
		return array[randInt(0, array.length - 1)];
	}

	public static String randSex() {
		return (String) randArrayValue(new String[] { "男", "女" });
	}

	public static String randTel() {
		StringBuilder tel = new StringBuilder();
		for (int i = 1; i <= 9; i++) {
			tel.append(String.valueOf(randInt(0, 9)));
		}
		return "1" + randArrayValue(new Integer[] { 3, 5, 8 }) + tel;
	}

	public static int randYear() {
		return randInt(2015, 2015);
	}

	public static int randMonth() {
		return randInt(1, 12);
	}

	public static int randDay(int year, int month) {
		boolean isLeapYear = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
		int[] daysOfMonth = new int[] { 31, isLeapYear ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		return randInt(1, daysOfMonth[month - 1]);
	}

	public static int randHour() {
		return randInt(0, 23);
	}

	public static int randMinute() {
		return randInt(0, 59);
	}

	public static int randSecond() {
		return randInt(0, 59);
	}

	public static long randTime() {
		String year = String.valueOf(randYear());
		String month = String.valueOf(randMonth());
		String day = String.valueOf(randDay(Integer.parseInt(year), Integer.parseInt(month)));
		String hour = String.valueOf(randHour());
		String minute = String.valueOf(randMinute());
		String second = String.valueOf(randSecond());
		month = month.length() == 1 ? ("0" + month) : month;
		day = day.length() == 1 ? ("0" + day) : day;
		hour = hour.length() == 1 ? ("0" + hour) : hour;
		minute = minute.length() == 1 ? ("0" + minute) : minute;
		second = second.length() == 1 ? ("0" + second) : second;
		return Long.parseLong(year + month + day + hour + minute + second);
	}

	public static String randDate() {
		return randStringTime().substring(0, 10);
	}

	public static String randStringTime() {
		String time = String.valueOf(randTime());
		String year = time.substring(0, 4);
		String month = time.substring(4, 6);
		String day = time.substring(6, 8);
		String hour = time.substring(8, 10);
		String minute = time.substring(10, 12);
		String second = time.substring(12, 14);
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
	}

	public static String randValCode(int length) {
		StringBuilder valCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			valCode.append(String.valueOf(randInt(0, 9)));
		}
		return valCode.toString();
	}

	/**
	 * 根据汉语字符串生成以每个汉字首字母大写组成的字符串id
	 * 

	public static String buildIdByHanyu(String hanyu, String startStr) {
		String result = startStr + "_";
		HanyuPinyinOutputFormat hpof = new HanyuPinyinOutputFormat();
		hpof.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大小写
		hpof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 无声调
		hpof.setVCharType(HanyuPinyinVCharType.WITH_V);// u:用v代替

		for (String character : StringUtil.SPECIAL_CHARACTER) {
			hanyu = hanyu.replaceAll("\\" + character, "");
		}

		char[] cs = hanyu.toCharArray();
		for (char c : cs) {
			String[] strArr = null;
			try {
				strArr = PinyinHelper.toHanyuPinyinStringArray(c, hpof);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
				throw new MessageException("未知错误");
			}
			if (strArr != null && strArr.length > 0) {
				result += strArr[0].charAt(0);
			} else {
				result += Character.toString(c);
			}
		}

		return result;
	}
	 */
	public static String appendSeqNum(String maxId, String result, Integer sequenceNum) {
		StringBuilder tail = new StringBuilder("-");
		for (int i = 0; i < sequenceNum; i++) {
			tail.append( "0");
		}
		tail.append("1");

		if (maxId == null || "".equals(maxId)) {
			result = result + tail;
		} else {
			String id;
			String[] arr = maxId.split("-");
			if (arr.length == 1) {
				result = result + tail;
				return result;
			} else {
				id = arr[arr.length - 1];
			}
			while (true) {
				if (id.startsWith("0")) {
					id = id.replaceFirst("0", "");
				} else {
					break;
				}
			}

			Long initNum = DataConvertUtil.getLong(id);

			// 流水号
			String seqNum = SequenceNumUtil.getSequenceNum(sequenceNum, initNum + 1);
			result += "-" + seqNum;
		}

		return result;
	}

	/**
	 * 根据汉语字符串生成以每个汉字首字母大写组成的字符串id
	 *
	 * @param hanyu
	 * @param startStr
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String buildIdByHanyu(String hanyu, String startStr) {
		String result = startStr + "_";
		HanyuPinyinOutputFormat hpof = new HanyuPinyinOutputFormat();
		hpof.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大小写
		hpof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 无声调
		hpof.setVCharType(HanyuPinyinVCharType.WITH_V);// u:用v代替

		for (String character : StringUtil.SPECIAL_CHARACTER) {
			hanyu = hanyu.replaceAll("\\" + character, "");
		}

		char[] cs = hanyu.toCharArray();
		for (char c : cs) {
			String[] strArr = null;
			try {
				strArr = PinyinHelper.toHanyuPinyinStringArray(c, hpof);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
				throw new MessageException("未知错误");
			}
			if (strArr != null && strArr.length > 0) {
				result += strArr[0].charAt(0);
			} else {
				result += Character.toString(c);
			}
		}

		return result;
	}

	public static void main(String[] args) {
		String result = RandUtil.buildIdByHanyu("意向成交", "P");
		System.out.println(result);
	}
}
