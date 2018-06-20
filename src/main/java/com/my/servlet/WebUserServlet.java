package com.my.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.bean.WebUserBean;
import com.my.server.WebUserServer;
import com.my.util.Log4jUtil;

public class WebUserServlet extends BaseServlet {
	
	private static final long serialVersionUID = -2647988876699684816L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Log4jUtil.log.warn("ÍøÒ³ÓÃ»§Î¬»¤");
		request.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=GBK");
		
		Map<String,String> mobileMap = setBeanValue(WebUserBean.class,request);
		String method = request.getParameter("method");
		String returnJson = new WebUserServer().methed(method, mobileMap, request);
		Log4jUtil.log.warn(returnJson);
		response.getOutputStream().write(returnJson.getBytes("GBK"));
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)  
		throws ServletException, IOException { 
		doGet(req, resp);
	}
}
	
	
