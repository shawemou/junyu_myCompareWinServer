package com.my.servlet;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.my.util.Check;

/**
 * @ClassName: BaseServlet 
 * @Description: 基础servlet 
 */
public class BaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 9065700071398491716L;

	@Override
	public void destroy() {
		super.destroy();
	}
	
	/**
	 * 从request根据javabean的属性,获取值，放入Map中
	 * @return
	 */
	public Map<String,String> setBeanValue(Class<?> clazz,HttpServletRequest request){
		BeanInfo beaninfo = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			beaninfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pr = beaninfo.getPropertyDescriptors();
			for(int i = 0; i < pr.length;i++){
				if(!pr[i].getName().toString().equals("class"))
					map.put(pr[i].getName().toString(),decode(getRequestString(request, pr[i].getName().toString())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 从request获取参数的值
	 * @return
	 */
	public static String getRequestString(HttpServletRequest request,String name){		
		if(request != null){
			return request.getParameter(name) == null ? "" : request.getParameter(name).equals("undefined")?"":request.getParameter(name);
		}else{			
			return "";
		}
	}
	
	public static String decode(String reqString){
		if( !Check.IsStringNULL(reqString) ){
			try {
				return URLDecoder.decode(reqString,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return reqString;
	}
}
