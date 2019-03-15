package com.lx.pk.utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 字符串脱敏
 */
public class StringHiddenUtil {
	 private static Logger logger = LoggerFactory.getLogger(StringHiddenUtil.class);

	    public static String getHiddenString(String source,Integer begin,Integer end){
	        return StringHiddenUtil.getHiddenString(source, begin, end, '*');
	    }

	    public static String getHiddenString(String source,Integer begin,Integer end,char hidden){
	        if (!StringUtils.isEmpty(source)){
	            if (!(begin >= 0 && begin <=source.length())){
	                logger.warn("隐藏字符异常: begin=>{} end=>{} length=>{}",begin,end,source.length());
	                begin = 0;
	            }

	            if (!(begin <= end && end <= source.length())){
	                logger.warn("隐藏字符异常: begin=>{} end=>{} length=>{}",begin,end,source.length());
	                end = source.length();
	            }
	            char[] sourceArr = source.toCharArray();
	            for (int index = begin;index < end;index++){
	                sourceArr[index] = hidden;
	            }
	            return String.valueOf(sourceArr);
	        }
	        return null;
	    }

	    public static String getHiddenStringByDisplay(String source,Integer begin,Integer end){
	        return StringHiddenUtil.getHiddenStringByDisplay(source, begin, end, '*');
	    }
	    public static String getHiddenStringByDisplay(String source,Integer begin,Integer end,char hidden){
	        if (!StringUtils.isEmpty(source)){
	            int length = source.length();
	            if (!(begin >= 0 && begin <= length)){
	                logger.warn("隐藏字符异常: begin=>{} end=>{} length=>{}",begin,end,length);
	                begin = 0;
	            }

	            if (!(begin + end < source.length() && end < length)){
	                logger.warn("隐藏字符异常: begin=>{} end=>{} length=>{}",begin,end,length);
	                end = 0;
	            }
	            char[] sourceArr = source.toCharArray();
	            for (int index = begin;index < length - end;index++){
	                sourceArr[index] = hidden;
	            }
	            return String.valueOf(sourceArr);
	        }
	        return null;
	    }

	    public static void main(String[] args){
	        System.out.println(StringHiddenUtil.getHiddenStringByDisplay("6215590200002266807",6,4,'*'));
	    }

}
