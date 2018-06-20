package com.my.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.my.common.BaseReturn;
import com.my.server.WebLoginServer;
import com.my.util.Check;
import com.my.util.Log4jUtil;
import com.my.validate.WebLoginValidate;

public class WebLoginServlet extends BaseServlet {

	private static final long serialVersionUID = 470922641220769990L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Log4jUtil.log.warn("ÍøÒ³ÓÃ»§µÇÂ¼");
		request.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=GBK");
		
		String login_name = Check.HadStr(request.getParameter("login_name"));
		String login_psd = Check.HadStr(request.getParameter("login_psd"));
		
		BaseReturn br = WebLoginValidate.validate(login_name, login_psd);
		if( br.isSuccess() ){
			br = new WebLoginServer().login(login_name, login_psd, br, request);
		}
		
		JSONObject resultJson = new JSONObject();
		try {
			resultJson.put("success", br.isSuccess());
			resultJson.put("info", br.getInfo());
			Log4jUtil.log.warn(resultJson.toString());
			response.getOutputStream().write(resultJson.toString().getBytes("GBK"));
		} catch (JSONException e) {
			response.getOutputStream().write("µÇÂ¼Òì³£".getBytes("GBK"));
		    Log4jUtil.log.error("µÇÂ¼Òì³£", e);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)  
		throws ServletException, IOException { 
		doGet(req, resp);
	}
}
