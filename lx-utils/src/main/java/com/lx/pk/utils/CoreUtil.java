package com.lx.pk.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreUtil {

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    /*
     * 判断是否为整数 包含小数点
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

	/**
	 * 去掉字符串中的字母
	 * @param buffer
	 * @return
     */
	public static String replaceChararter(String buffer){
		if(StringUtils.isBlank(buffer)){
			return buffer;
		}
		return  buffer.replaceAll("[a-zA-Z]","" );
	}

	public static void main(String[] args) {
//    	String id="H078";
//    	String subID=id.substring(id.lastIndexOf("H")+1);
//    	System.out.println(subID);
//    	System.out.println(CoreUtil.isInteger(subID));
		System.out.println(isInteger("120h"));
	}
    
}
