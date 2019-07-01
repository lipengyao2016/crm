package com.crm.utils;

/**
 * 流水号生成
 * @author Administrator
 *
 */
public class SequenceNumUtil {
	 
	/**
	 * 根据长度生成流水号,小于长度则在前面添加0
	 * @param length 流水号长度
	 * @param num 当前数字
	 * @return 流水号
	 */
	public static String getSequenceNum(int length,Long num){
		String strNum=String.valueOf(num);
		if(strNum.length()>=length){
			return strNum;
		}
		StringBuilder seqNum=new StringBuilder();
		for(int i=0;i<length-strNum.length();i++){
			seqNum.append("0");
		}
		seqNum.append(strNum);
		return seqNum.toString();
	}
}
