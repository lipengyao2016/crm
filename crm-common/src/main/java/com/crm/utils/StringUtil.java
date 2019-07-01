package com.crm.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.crm.vo.UserLoginInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	public static final String AES_KEY = "0123456789ABCDEF";

	public static final String Cipher_KEY = "AES/ECB/PKCS7Padding";

	public static final String success_code = "1";

	public static final String success_msg = "成功";

	public static final String fail_code = "0";

	public static final String fail_msg = "失败";

	public static final String fail_msg_paramError = "参数错误";

	public static final String encoding = "utf-8";

	public static final String dbname = "topstrong";

	public static final String SUCCESS = "success"; // 请求操作成功时返回页面的信息
	public static final String ERROR = "error"; // 请求操作失败时返回页面的信息
	public static final String SESSION_ID = "sessionId"; // 浏览器端的sessionId
	public static final String USER_LOGIN_ID = "userLoginId"; // session的用户id
	public static final String USER_NAME = "userName"; // session的用户姓名
	public static final String USER_CODE = "userCode"; // session的用户编号
	public static final String POSITION_ID = "positionId"; // session的用户职位编码
	public static final String POSITION_NAME = "positionName"; // session的用户职位名称
	public static final List<String> INVALID_CHARACTER; // 非法字符集
	public static final List<String> SPECIAL_CHARACTER; // 特殊字符集
	static {
		INVALID_CHARACTER = new ArrayList<>();
		INVALID_CHARACTER.add("'");
		INVALID_CHARACTER.add("\"");
		INVALID_CHARACTER.add("--");
		INVALID_CHARACTER.add("/");
		INVALID_CHARACTER.add("/*");
		INVALID_CHARACTER.add("*/");
	}

	static {
		SPECIAL_CHARACTER = new ArrayList<>();
		SPECIAL_CHARACTER.add(" ");
		SPECIAL_CHARACTER.add("#");
		SPECIAL_CHARACTER.add("~");
		SPECIAL_CHARACTER.add("`");
		SPECIAL_CHARACTER.add("$");
		SPECIAL_CHARACTER.add("%");
		SPECIAL_CHARACTER.add("^");
		SPECIAL_CHARACTER.add("&");
		SPECIAL_CHARACTER.add("*");
		SPECIAL_CHARACTER.add("(");
		SPECIAL_CHARACTER.add(")");
		SPECIAL_CHARACTER.add("（");
		SPECIAL_CHARACTER.add("）");
		SPECIAL_CHARACTER.add("=");
		SPECIAL_CHARACTER.add("[");
		SPECIAL_CHARACTER.add("]");
		SPECIAL_CHARACTER.add("@");
		SPECIAL_CHARACTER.add("{");
		SPECIAL_CHARACTER.add("}");
		SPECIAL_CHARACTER.add("<");
		SPECIAL_CHARACTER.add(">");
		SPECIAL_CHARACTER.add("《");
		SPECIAL_CHARACTER.add("》");
		SPECIAL_CHARACTER.add("*");
		SPECIAL_CHARACTER.add(".");
		SPECIAL_CHARACTER.add("'");
		SPECIAL_CHARACTER.add("\"");
		SPECIAL_CHARACTER.add("-");
		SPECIAL_CHARACTER.add("/");
		SPECIAL_CHARACTER.add("/*");
		SPECIAL_CHARACTER.add("*/");
	}

	public static final Logger log = LoggerFactory.getLogger(StringUtil.class);
	/**
	 * 匹配数字
	 */
	public static final Pattern pattern = Pattern.compile("^[(-?\\d+\\.\\d+)|(-?\\d+)]+$");

	/**
	 * 匹配Integer
	 */
	public static final Pattern pattern1 = Pattern.compile("^[(-?\\d+)]+$");

	/**
	 * 匹配Double
	 */
	public static final Pattern pattern2 = Pattern.compile("^[(-?\\d+\\.\\d+)]+$");

	public static boolean isEmpty(Object arg) {
		return arg == null || arg.equals("") || arg.equals("null") || arg.equals("(null)");
	}

	public static boolean isNotEmpty(Object arg) {
		return arg != null && !arg.equals("") && !arg.equals("null") && !arg.equals("(null)");
	}

	/**
	 * 获取登录人信息
	 */
	public static String getUserLoginId(HttpServletRequest request) {
		String userLoginId = "";
		UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
		if (loginVo != null) {
			userLoginId = loginVo.getUserLoginId();
		} else {
			userLoginId = "system";
		}
		return userLoginId;
	}

	/**
	 * 获取登录人信息
	 */
	public static Long getUserId(HttpServletRequest request) {
		Long id = null;
		UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
		if (loginVo != null) {
			id = loginVo.getId();
		} else {
			id = 0L;
		}
		return id;
	}

	/**
	 * 生成以零开头,长度指定的数字编码
	 */
	public static String getNo(int n, int length) {
		String no = String.valueOf(n);
		while (no.length() < length) {
			no = "0" + no;
		}
		return no;
	}

	/**
	 * 生成自动增长id
	 */
//	public static String generateId(JdbcTemplate jt, String tablename) {
//		String pk = (String) jt.queryForObject("select column_name from information_schema.columns where table_schema='"
//				+ dbname + "' and table_name='" + tablename + "' and column_key='PRI'", String.class);
//		String code = "";
//		Map<String, Object> temp = jt.queryForMap("select max(cast(" + pk + " as unsigned integer)) as maxcode from "
//				+ tablename + " where " + pk + " regexp '^[0-9]+$'");
//		if (temp.get("maxcode") == null) {
//			code = "1";
//		} else {
//			String maxcode = temp.get("maxcode").toString();
//			code = String.valueOf(Integer.parseInt(maxcode) + 1);
//		}
//		return code;
//	}

	public static String whereOrAnd(String sql) {
		return sql.indexOf("where") == -1 ? " where " : " and ";
	}

	public static List<String> stringToList(String arg) {
		String[] s = arg.split(",");
		for (int i = 0; i < s.length; i++) {
			s[i] = s[i].trim();
		}
		return Arrays.asList(s);
	}

	public static String examOptionString(String option) {
		String temp = "";
		String[] ss = option.split(";");
		for (int i = 0; i < ss.length; i++) {
			temp += intToOptionTitle(i) + "." + ss[i] + "<br/>";
		}
		return temp;
	}

	public static String examResultString(String result) {
		String temp = "";
		String[] ss = result.split(";");
		for (int i = 0; i < ss.length; i++) {
			temp += intToOptionTitle(Integer.parseInt(ss[i])) + ",";
		}
		if (temp.length() > 0) {
			temp = temp.substring(0, temp.length() - 1);
		}
		return temp;
	}

	private static String intToOptionTitle(int i) {
		String optionTitle = "";
		switch (i) {
		case 0:
			optionTitle = "A";
			break;
		case 1:
			optionTitle = "B";
			break;
		case 2:
			optionTitle = "C";
			break;
		case 3:
			optionTitle = "D";
			break;
		default:
			break;
		}
		return optionTitle;
	}

	public static String formatMoney(double money) {
		return new DecimalFormat("#.##").format(money);
	}
//
//	public static String generateSQL(String tablename, String type, JdbcTemplate jt, Map<String, Object> cond,
//			Map<String, Object> exe) {
//		List<Map<String, Object>> list = jt.queryForList("desc " + tablename);
//		String sql = "";
//		if (type.indexOf("select") != -1) {
//			if (type.indexOf("count") != -1) {
//				sql = "select count(*)";
//			} else {
//				sql = "select *";
//			}
//			sql += " from " + tablename;
//			if (cond != null && !cond.isEmpty()) {
//				Iterator<String> it = cond.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next();
//					for (Map<String, Object> column : list) {
//						if (column.get("Field").equals(key) && isNotEmpty(cond.get(key))) {
//							sql += whereOrAnd(sql) + key + "=";
//							if (isNeedQuote((String) column.get("Type"))) {
//								sql += "'" + cond.get(key) + "'";
//							} else {
//								sql += cond.get(key);
//							}
//						}
//					}
//					if (key.equals("keyword") && isNotEmpty(cond.get(key))) {
//						sql += whereOrAnd(sql) + "(";
//						for (Map<String, Object> column : list) {
//							String colname = column.get("Field").toString();
//							if (colname.indexOf("name") != -1 || colname.indexOf("desc") != -1
//									|| colname.indexOf("title") != -1) {
//								sql += column.get("Field") + " like '%" + cond.get(key) + "%' or ";
//							}
//						}
//						sql = sql.substring(0, sql.lastIndexOf("or") - 1) + ")";
//					}
//				}
//			}
//		}
//		if (type.equals("insert")) {
//			sql = "insert into " + tablename + " (";
//			String values = "";
//			for (Map<String, Object> column : list) {
//				if (!(column.get("Key").equals("PRI") && column.get("Extra").equals("auto_increment"))) {
//					sql += "`" + column.get("Field") + "`,";
//					Object value = exe.get(column.get("Field"));
//					if (isEmpty(value)) {
//						if (column.get("Null").equals("YES")) {
//							values += "null,";
//						}
//						if (column.get("Null").equals("NO")) {
//							if (column.get("Default") == null) {
//								if (isNeedQuote((String) column.get("Type"))) {
//									values += "'',";
//								} else {
//									values += "0,";
//								}
//							} else {
//								if (isNeedQuote((String) column.get("Type"))) {
//									values += "'" + column.get("Default") + "',";
//								} else {
//									values += column.get("Default") + ",";
//								}
//							}
//						}
//					} else {
//						if (isNeedQuote((String) column.get("Type"))) {
//							values += "'" + value + "',";
//						} else {
//							values += value + ",";
//						}
//					}
//				}
//			}
//			sql = sql.substring(0, sql.length() - 1);
//			values = values.substring(0, values.length() - 1);
//			sql += ") values (" + values + ")";
//		}
//		if (type.equals("update")) {
//			sql = "update " + tablename + " set ";
//			Iterator<String> it = exe.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next();
//				for (Map<String, Object> column : list) {
//					if (column.get("Field").equals(key)) {
//						Object value = exe.get(key);
//						if (isEmpty(value) && column.get("Null").equals("YES")) {
//							sql += "`" + key + "`=null,";
//						} else {
//							if (isNeedQuote((String) column.get("Type"))) {
//								sql += "`" + key + "`='" + exe.get(key) + "',";
//							} else {
//								sql += "`" + key + "`=" + exe.get(key) + ",";
//							}
//						}
//					}
//				}
//			}
//			sql = sql.substring(0, sql.length() - 1);
//			if (cond != null && !cond.isEmpty()) {
//				it = cond.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next();
//					for (Map<String, Object> column : list) {
//						if (column.get("Field").equals(key) && isNotEmpty(cond.get(key))) {
//							sql += whereOrAnd(sql) + key + "=";
//							if (isNeedQuote((String) column.get("Type"))) {
//								sql += "'" + cond.get(key) + "'";
//							} else {
//								sql += cond.get(key);
//							}
//						}
//					}
//				}
//			}
//		}
//		return sql;
//	}
//
//	public static String generateSQL2(String tablename, String type, JdbcTemplate jt, Map<String, Object> cond,
//			Map<String, Object> exe) {
//		List<Map<String, Object>> list = jt.queryForList("desc " + tablename);
//		String sql = "";
//		if (type.indexOf("select") != -1) {
//			if (type.indexOf("count") != -1) {
//				sql = "select count(*)";
//			} else {
//				sql = "select *";
//			}
//			sql += " from " + tablename;
//			if (cond != null && !cond.isEmpty()) {
//				Iterator<String> it = cond.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next();
//					for (Map<String, Object> column : list) {
//						if (column.get("Field").equals(key) && isNotEmpty(cond.get(key))) {
//							sql += whereOrAnd(sql) + key + "=";
//							if (isNeedQuote((String) column.get("Type"))) {
//								sql += "'" + cond.get(key) + "'";
//							} else {
//								sql += cond.get(key);
//							}
//						}
//					}
//					if (key.equals("keyword") && isNotEmpty(cond.get(key))) {
//						sql += whereOrAnd(sql) + "(";
//						for (Map<String, Object> column : list) {
//							String colname = column.get("Field").toString();
//							if (colname.indexOf("name") != -1 || colname.indexOf("desc") != -1
//									|| colname.indexOf("title") != -1) {
//								sql += column.get("Field") + " like '%" + cond.get(key) + "%' or ";
//							}
//						}
//						sql = sql.substring(0, sql.lastIndexOf("or") - 1) + ")";
//					}
//				}
//			}
//		}
//		if (type.equals("insert")) {
//			sql = "insert into " + tablename + " (";
//			String values = "";
//			for (Map<String, Object> column : list) {
//				if (!(column.get("Key").equals("PRI") && column.get("Extra").equals("auto_increment"))) {
//					if (isNotEmpty(exe.get(column.get("Field")))) {
//						sql += column.get("Field") + ",";
//						Object value = exe.get(column.get("Field"));
//						if (isEmpty(value)) {
//							if (column.get("Null").equals("YES")) {
//								values += "null,";
//							}
//							if (column.get("Null").equals("NO")) {
//								if (column.get("Default") == null) {
//									if (isNeedQuote((String) column.get("Type"))) {
//										values += "'',";
//									} else {
//										values += "0,";
//									}
//								} else {
//									if (isNeedQuote((String) column.get("Type"))) {
//										values += "'" + column.get("Default") + "',";
//									} else {
//										values += column.get("Default") + ",";
//									}
//								}
//							}
//						} else {
//							if (isNeedQuote((String) column.get("Type"))) {
//								values += "'" + value + "',";
//							} else {
//								values += value + ",";
//							}
//						}
//
//					}
//				}
//			}
//			sql = sql.substring(0, sql.length() - 1);
//			values = values.substring(0, values.length() - 1);
//			sql += ") values (" + values + ")";
//		}
//		if (type.equals("update")) {
//			sql = "update " + tablename + " set ";
//			Iterator<String> it = exe.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next();
//				for (Map<String, Object> column : list) {
//					if (column.get("Field").equals(key)) {
//						if (isNotEmpty(exe.get(column.get("Field")))) {
//							Object value = exe.get(key);
//							if (isEmpty(value) && column.get("Null").equals("YES")) {
//								sql += key + "=null,";
//							} else {
//								if (isNeedQuote((String) column.get("Type"))) {
//									sql += key + "='" + exe.get(key) + "',";
//								} else {
//									sql += key + "=" + exe.get(key) + ",";
//								}
//							}
//						}
//					}
//				}
//			}
//			sql = sql.substring(0, sql.length() - 1);
//			if (cond != null && !cond.isEmpty()) {
//				it = cond.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next();
//					for (Map<String, Object> column : list) {
//						if (column.get("Field").equals(key) && isNotEmpty(cond.get(key))) {
//							sql += whereOrAnd(sql) + key + "=";
//							if (isNeedQuote((String) column.get("Type"))) {
//								sql += "'" + cond.get(key) + "'";
//							} else {
//								sql += cond.get(key);
//							}
//						}
//					}
//				}
//			}
//		}
//		return sql;
//	}

	public static boolean isNeedQuote(String colType) {
		if (colType.indexOf("int") != -1) {
			return false;
		}
		if (colType.indexOf("float") != -1) {
			return false;
		}
		if (colType.indexOf("double") != -1) {
			return false;
		}
		if (colType.indexOf("numeric") != -1) {
			return false;
		}
		if (colType.indexOf("decimal") != -1) {
			return false;
		}
		return true;
	}

	public static String getTableName(String methodname) {
		StringBuilder tablename = new StringBuilder();
		int index = methodname.indexOf("ForAjax");
		if (index != -1) {
			methodname = methodname.substring(0, index);
		}
		for (int i = 0; i < methodname.length(); i++) {
			char c = methodname.charAt(i);
			if ((int) c >= 65 && (int) c <= 90) {
				boolean found = false;
				for (int j = i + 1; j < methodname.length(); j++) {
					char d = methodname.charAt(j);
					if ((int) d >= 65 && (int) d <= 90) {
						if (tablename.length() == 0) {
							tablename.append(methodname.substring(i, j).toLowerCase());
						} else {
							tablename.append("_" + methodname.substring(i, j).toLowerCase());
						}
						found = true;
						break;
					}
				}
				if (!found) {
					tablename.append("_" + methodname.substring(i).toLowerCase());
				}
			}
		}
		return tablename.toString();
	}

	public static String getBeanName(String tablename) {
		StringBuilder beanname = new StringBuilder();
		for (int j = 0; j < tablename.length(); j++) {
			char d = tablename.charAt(j);
			if (d == '_') {
				beanname.append(tablename.substring(0, 1).toUpperCase() + tablename.substring(1, j));
				break;
			}
		}
		for (int i = 0; i < tablename.length(); i++) {
			char c = tablename.charAt(i);
			if (c == '_') {
				boolean found = false;
				for (int j = i + 1; j < tablename.length(); j++) {
					char d = tablename.charAt(j);
					if (d == '_') {
						beanname.append(tablename.substring(i + 1, i + 2).toUpperCase() + tablename.substring(i + 2, j));
						found = true;
						break;
					}
				}
				if (!found) {
					beanname.append(tablename.substring(i + 1, i + 2).toUpperCase() + tablename.substring(i + 2));
				}
			}
		}
		return beanname.toString();
	}

	/**
	 * 十六进制字符串转字节数组
	 */
	public static byte[] HexStringToBytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	/**
	 * 十六进制字符串转字节数组
	 */
	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	/**
	 * 字节数组转十六进制字符串
	 */
	public static String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		}
		int len = data.length;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if ((data[i] & 0xFF) < 16)
				str.append("0" + Integer.toHexString(data[i] & 0xFF));
			else
				str.append(Integer.toHexString(data[i] & 0xFF));
		}
		return str.toString();
	}

	/*
	 * public static String decrypt(String arg) throws Exception{ if(isEmpty(arg)){
	 * return arg; } String returnvalue=null; SecretKey secretkey=new
	 * SecretKeySpec(AES_KEY.getBytes(),"AES"); Cipher
	 * cipher=Cipher.getInstance(Cipher_KEY); cipher.init(Cipher.DECRYPT_MODE,
	 * secretkey); returnvalue=new
	 * String(cipher.doFinal(Base64.decodeBase64(arg)),encoding); return
	 * returnvalue; }
	 * 
	 * public static String encrypt(String arg) throws Exception{ if(isEmpty(arg)){
	 * return arg; } String returnvalue=null; SecretKey secretkey=new
	 * SecretKeySpec(AES_KEY.getBytes(),"AES"); Cipher
	 * cipher=Cipher.getInstance(Cipher_KEY); cipher.init(Cipher.ENCRYPT_MODE,
	 * secretkey); returnvalue=new
	 * String(Base64.encodeBase64(cipher.doFinal(arg.getBytes(encoding)))); return
	 * returnvalue; }
	 * 
	 * public static void main(String[] args) { try { Security.addProvider(new
	 * BouncyCastleProvider());
	 * System.out.println(decrypt("uumWuUhkXaBpIr4kD/P+oQ==")); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 */
	/*
	 * public static String decrypt(String arg) throws Exception{ if(isEmpty(arg)){
	 * return arg; } String returnvalue=null; SecretKey secretkey=new
	 * SecretKeySpec(AES_KEY.getBytes(),"AES"); Cipher
	 * cipher=Cipher.getInstance(Cipher_KEY); cipher.init(Cipher.DECRYPT_MODE,
	 * secretkey); returnvalue=new
	 * String(cipher.doFinal(Base64.decodeBase64(arg)),encoding); return
	 * returnvalue; }
	 * 
	 * public static String encrypt(String arg) throws Exception{ if(isEmpty(arg)){
	 * return arg; } String returnvalue=null; SecretKey secretkey=new
	 * SecretKeySpec(AES_KEY.getBytes(),"AES"); Cipher
	 * cipher=Cipher.getInstance(Cipher_KEY); cipher.init(Cipher.ENCRYPT_MODE,
	 * secretkey); returnvalue=new
	 * String(Base64.encodeBase64(cipher.doFinal(arg.getBytes(encoding)))); return
	 * returnvalue; }
	 * 
	 * public static void main(String[] args) { try { Security.addProvider(new
	 * BouncyCastleProvider());
	 * System.out.println(decrypt("uumWuUhkXaBpIr4kD/P+oQ==")); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 */
	/**
	 * 处理权限菜单树形数据
	 * 
	 * @param source
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> doHandleTreeData(List<Map> source) {
		List<Map> result = new LinkedList<Map>();
		Map<String, Map> tempdata = new HashMap<String, Map>();
		for (int i = 0; i < source.size(); i++) {
			Map<String, String> map1 = source.get(i);
			// 预处理是否存在下级标识
			map1.put("haxNext", "0");
			tempdata.put(map1.get("parent_path"), map1);

		}

		List<String> existsname = new ArrayList<String>();
		// 正则匹配下级，将下级放入上级中
		Set<String> keySet = tempdata.keySet();
		for (String string : keySet) {
			if (existsname.contains(string)) {
				continue;
			}
			Map<String, Object> tempmap = tempdata.get(string);
			String pathpattern = "^" + string + "\\d{1,}/$";
			for (String string2 : keySet) {
				if (Pattern.matches(pathpattern, string2)) {
					tempmap.put("chlidren", tempdata.get(string2));
					tempmap.put("haxNext", "1");
					// 记录已加入父级的权限项
					existsname.add(string2);
				}
			}
			result.add(tempmap);
		}
		// 剔除已经加入父级的权限项
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> map1 = result.get(i);
			for (String existname : existsname) {
				if (map1.get("parent_path").equals((existname))) {
					result.remove(i);
				}
			}
		}
		return result;
	}

	/**
	 * 出去数据库日期带".0"
	 * 
	 * @param datestr
	 * @return
	 */
	public static String doHandleDateString(String datestr) {
		if (isNotEmpty(datestr) && datestr.indexOf(".0") != -1) {
			return datestr.substring(0, datestr.indexOf(".0"));
		} else {
			return datestr;
		}
	}

	// 判断一个字符串是否都为数字
	public static boolean isDigit2(String strNum) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) strNum);
		return matcher.matches();
	}

	/**
	 * 将以逗号隔开的字符串加上单引号,并去掉首尾逗号
	 * 
	 * @param str
	 * @return
	 */
	public static String concatString(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		if (str.startsWith(",")) {
			str = str.substring(1);
		}
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}

		str = "'" + str.replaceAll(",", "','") + "'";
		return str;
	}
	// public static void main(String[] args) {
	// System.out.println(doHandleDateString("2017-09-20 19:08:15.0"));
	// }

	/**
	 * 获取经销商id
	 */
	public static Long getAgencyId(HttpServletRequest request) {
		Long agencyId = 0L;
		UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
		if (loginVo != null) {
			agencyId = loginVo.getAgencyId();
		}
		return agencyId;
	}
	/**
	 * 获取用户登录信息
	 */
	public static UserLoginInfoVo getUserLoginInfoVo(HttpServletRequest request) {
		return (UserLoginInfoVo) request.getAttribute("loginUserInfo");
	}


}
