package com.my.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.my.dao.base.CommonDao;

/**
 * @ClassName: WebLoginDao 
 * @Description: web���û���¼ 
 *
 */
public class WebLoginDao extends CommonDao  {
	
	public List<Map<String, Object>> login(String login_name){
		String sql = "SELECT GUID,LOGIN_PWD,NAME,BUSABLE FROM T_ADMIN T WHERE T.LOGIN_NAME = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(login_name);
		return queryReturnListMap(sql,params);
	}
}
