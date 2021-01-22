package com.blb.wfx_cust.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作cookie的工具类
 * @author Administrator
 *
 */
public class CookieUtils {

	private static final String ENC = "UTF-8";
	/**
	 * 保存Cookie
	 * @param resp
	 * @param name
	 * @param value
	 * @param time
	 */
	public static void save(HttpServletResponse resp,String name,String value,int time){
		try {
			Cookie cookie = new Cookie(URLEncoder.encode(name, ENC),
									URLEncoder.encode(value, ENC));
			cookie.setPath("/");
			cookie.setMaxAge(time);
			resp.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取Cookie的值
	 */
	public static String read(HttpServletRequest req,String name){
		Cookie[] cookies = req.getCookies();
		try {
			for(Cookie c : cookies){
				if(name.equals(URLDecoder.decode(c.getName(), ENC))){
					return URLDecoder.decode(c.getValue(),ENC);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
