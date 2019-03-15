package com.lx.pk.utils.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("all")
public class Base64Util {
	// 加密
    public static String encode(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public static String decodeString(String s) {
        String result = null;
        if (null != s){
            try {
                result = new String(Base64Util.decode(s), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] decode(String s) {
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                return decoder.decodeBuffer(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
