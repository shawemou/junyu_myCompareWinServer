package com.my.webservices;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IWebservice {
	
	/**
	 * ���Ե���
	 * @param message
	 * @return �ַ���
	 */
	@WebResult(name = "strResult")
	public String example(@WebParam(name = "message") String message);
	
	@WebResult(name = "strJsonResult")
	public String method(@WebParam(name = "strJson") String strJson);
}
