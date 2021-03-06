package com.my.server;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.my.util.Check;
import com.my.util.Log4jUtil;

public class WebUserValidateServer {

	/**
	 * 入参验证
	 * @param userMap
	 * @param resultJson
	 * @param type 1为新增 2为修改
	 * @return
	 */
	public boolean validate(Map<String,String> userMap, JSONObject resultJson, String type){
		try {
			if( !type.equals("1")){
				if( Check.IsStringNULL(userMap.get("guid")) ){
					resultJson.put("success",false);
					resultJson.put("info","信息缺失,无法变更");
				}
			}
			
			if( Check.IsStringNULL(userMap.get("login_name")) ){
				resultJson.put("success",false);
				resultJson.put("info","登录名必填");
				return false;
			}
			if( userMap.get("login_name").length() < 6 || userMap.get("login_name").length() > 20  ){
				resultJson.put("success",false);
				resultJson.put("info","登录名长度小于6位或大于20位");
				return false;
			}
			
			if( Check.IsStringNULL(userMap.get("name")) ){
				resultJson.put("success",false);
				resultJson.put("info","姓名必填");
				return false;
			}
			if( userMap.get("name").length() > 15  ){
				resultJson.put("success",false);
				resultJson.put("info","姓名长度大于15位");
				return false;
			}
			if( !Check.IsStringNULL(userMap.get("gender")) ){
				if( !userMap.get("gender").equals("1") && !userMap.get("gender").equals("2") ){
					resultJson.put("success",false);
					resultJson.put("info","性别输入有误");
					return false;
				}
			}
			if( !Check.IsStringNULL(userMap.get("id_number")) && userMap.get("id_number").length() > 18  ){
				resultJson.put("success",false);
				resultJson.put("info","证件号码长度大于18位");
				return false;
			}
			if( !Check.IsStringNULL(userMap.get("department")) && userMap.get("department").length() > 50  ){
				resultJson.put("success",false);
				resultJson.put("info","部门名称长度大于50位");
				return false;
			}
			if( !Check.IsStringNULL(userMap.get("detail_des")) && userMap.get("detail_des").length() > 200  ){
				resultJson.put("success",false);
				resultJson.put("info","备注长度大于200位");
				return false;
			}
		} catch (JSONException e) {
			Log4jUtil.log.error("新增用户时，入参格式判断异常", e);
		}
		return true;
	}
	
	public boolean validateLoginName(Map<String,String> userMap, JSONObject resultJson){
		try {
			if( Check.IsStringNULL(userMap.get("login_name")) ){
				resultJson.put("success",false);
				resultJson.put("info","登录名必填");
				return false;
			}
			if( userMap.get("login_name").length() < 6 || userMap.get("login_name").length() > 20  ){
				resultJson.put("success",false);
				resultJson.put("info","登录名长度小于6位或大于20位");
				return false;
			}
		} catch (JSONException e) {
			Log4jUtil.log.error("检测用户名是否唯一时，入参格式判断异常", e);
		}
		return true;
	}
}
