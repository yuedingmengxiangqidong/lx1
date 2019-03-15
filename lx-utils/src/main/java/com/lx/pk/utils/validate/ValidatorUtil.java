package com.lx.pk.utils.validate;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author pc 常见正则表达式
 *
 */
@SuppressWarnings("all")
public class ValidatorUtil {
	private static final String MAIL_REGEX = "[a-zA-Z_0-9]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}" ;
    private static final String PHONE_REGEX = "";
    private static final String MOBILE_REGEX = "^(13[0-9]|14[57]|15[012356789]|17[3678]|18[0-9])[0-9]{8}$";
    private static final String QQ_REGEX = "^[1-9][0-9]{4,9}$";
    private static final String PASSWORD_REGEX = "[A-Za-z0-9#@!~%^&*,.=?<>_`~+]{6,16}";
    private static final String VALIDATE_CODE_REGEX = "^[0-9]{6}$";
    private static final String TRADE_PWD_REGEX = "^[0-9]{6}$";

    public static boolean isPassword(String password){
        return match(ValidatorUtil.PASSWORD_REGEX,password);
    }

    public static boolean isValidateCode(String str){
        return match(ValidatorUtil.VALIDATE_CODE_REGEX,str);
    }

    public static boolean isTradePassword(String str){
        return match(ValidatorUtil.TRADE_PWD_REGEX,str);
    }

    public static boolean isEmail(String str){
        return match(ValidatorUtil.MAIL_REGEX,str);
    }

    public static boolean isPhone(String str){
        return match(ValidatorUtil.PHONE_REGEX,str);
    }

    public static boolean isMobile(String str){
        return match(ValidatorUtil.MOBILE_REGEX,str);
    }

    public static boolean isQQ(String str){
        return match(ValidatorUtil.QQ_REGEX,str);
    }

    public static boolean validate(String regex,String str) {
        if(!StringUtils.isEmpty(regex) && !StringUtils.isEmpty(str)){
            return match(regex,str);
        }else{
            return false;
        }
    }

    private static boolean match( String regex ,String str ){
        Pattern pattern = Pattern.compile(regex);
        Matcher  matcher = pattern.matcher( str );
        return matcher.matches();
    }

   
	public static class ValidatorException extends RuntimeException{
        private Integer code;
        private String message;
        ValidatorException(Integer code,String message){
            super();
            this.code = code;
            this.message = message;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }

}
