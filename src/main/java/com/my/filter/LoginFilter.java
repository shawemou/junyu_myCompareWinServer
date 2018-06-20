package com.my.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	
	public void destroy() {
	}
	
	//��ȫ�ַ��ʽ��п���
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String login = req.getRequestURL().toString().toUpperCase();
		
		
		Map<String, String[]> map = request.getParameterMap();
		BufferedReader br = new BufferedReader(new FileReader("E:/text_win.txt"));
		String str = null;
		String strall = "";
		while((str=br.readLine())!=null){
			strall = strall + str + System.lineSeparator(); 
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter("E:/text_win.txt"));
		bw.write(strall);
		bw.newLine();
		bw.write(login);
		bw.newLine();
		for (Entry<String, String[]> entry : map.entrySet()) {
			String[] value = entry.getValue();
			StringBuffer sb = new StringBuffer();
			for (String string : value) {
				sb.append(string+",");
			}
			bw.write(entry.getKey()+":"+sb+"  ;");
			bw.newLine();
		}
		bw.flush();
		bw.close();
		
//		if(login.indexOf("MOBILE.HTML") > 0){
//			chain.doFilter(request, response);
//		}else{
			if( login.indexOf("INDEX.HTML") > 0
					|| login.indexOf("USERADD.HTML") > 0
					|| login.indexOf("USERUPDATE.HTML") > 0
					|| login.indexOf("WEBUSER") > 0){
				
				HttpSession session = req.getSession(false);
				
				//��������Ժ���URL��д����λ������û����󣬾Ͳ���򵥵��ж�UserContextContainer�����ڲ��ڻỰ����
				Object obj = ( session == null ? null : session.getAttribute("user") );
				if(session != null && obj != null){
					chain.doFilter(request, response);
				}else{
					//����HTTP����ͷ�ж��Ƿ�AJAX����
					String requestType = ((HttpServletRequest)request).getHeader("X-Requested-With");
					if(requestType != null && "XMLHttpRequest".equals(requestType)){
						PrintWriter printWriter = response.getWriter();   
                        printWriter.print("{\"sessionState\":0}");   
                        printWriter.flush();   
                        printWriter.close();
					}else{
						request.getRequestDispatcher("login.html").forward(request, response);
					}
				}
			}else{
				chain.doFilter(request, response);
			}
//		}
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public static void main(String[] args) {
	}
}
