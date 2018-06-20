package com.my.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.common.ReadSetting;

/**
 * @ClassName: InitServlet 
 * @Description: ƽ̨������Ϣ����
 * @author lulinlin
 * @date 2015-8-11 ����02:35:21 
 *
 */
public class InitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1500079584721183937L;
	public static List<Map<String, Object>> bankList = null;
	
	@Override
	public void init() throws ServletException {
		ReadSetting.getInstance(this.getServletContext().getRealPath("/"),ReadSetting.SETTINGFILENAME);
		super.init();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
    	throws ServletException, IOException { 
		init();
	}
}
