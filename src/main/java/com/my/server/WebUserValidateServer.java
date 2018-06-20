package com.my.server;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.my.util.Check;
import com.my.util.Log4jUtil;

public class WebUserValidateServer {

	/**
	 * �����֤
	 * @param userMap
	 * @param resultJson
	 * @param type 1Ϊ���� 2Ϊ�޸�
	 * @return
	 */
	public boolean validate(Map<String,String> userMap, JSONObject resultJson, String type){
		try {
			if( !type.equals("1")){
				if( Check.IsStringNULL(userMap.get("guid")) ){
					resultJson.put("success",false);
					resultJson.put("info","��Ϣȱʧ,�޷����");
				}
			}
			
			if( Check.IsStringNULL(userMap.get("login_name")) ){
				resultJson.put("success",false);
				resultJson.put("info","��¼������");
				return false;
			}
			if( userMap.get("login_name").length() < 6 || userMap.get("login_name").length() > 20  ){
				resultJson.put("success",false);
				resultJson.put("info","��¼������С��6λ�����20λ");
				return false;
			}
			
			if( Check.IsStringNULL(userMap.get("name")) ){
				resultJson.put("success",false);
				resultJson.put("info","��������");
				return false;
			}
			if( userMap.get("name").length() > 15  ){
				resultJson.put("success",false);
				resultJson.put("info","�������ȴ���15λ");
				return false;
			}
			if( !Check.IsStringNULL(userMap.get("gender")) ){
				if( !userMap.get("gender").equals("1") && !userMap.get("gender").equals("2") ){
					resultJson.put("success",false);
					resultJson.put("info","�Ա���������");
					return false;
				}
			}
			if( !Check.IsStringNULL(userMap.get("id_number")) && userMap.get("id_number").length() > 18  ){
				resultJson.put("success",false);
				resultJson.put("info","֤�����볤�ȴ���18λ");
				return false;
			}
			if( !Check.IsStringNULL(userMap.get("department")) && userMap.get("department").length() > 50  ){
				resultJson.put("success",false);
				resultJson.put("info","�������Ƴ��ȴ���50λ");
				return false;
			}
			if( !Check.IsStringNULL(userMap.get("detail_des")) && userMap.get("detail_des").length() > 200  ){
				resultJson.put("success",false);
				resultJson.put("info","��ע���ȴ���200λ");
				return false;
			}
		} catch (JSONException e) {
			Log4jUtil.log.error("�����û�ʱ����θ�ʽ�ж��쳣", e);
		}
		return true;
	}
	
	public boolean validateLoginName(Map<String,String> userMap, JSONObject resultJson){
		try {
			if( Check.IsStringNULL(userMap.get("login_name")) ){
				resultJson.put("success",false);
				resultJson.put("info","��¼������");
				return false;
			}
			if( userMap.get("login_name").length() < 6 || userMap.get("login_name").length() > 20  ){
				resultJson.put("success",false);
				resultJson.put("info","��¼������С��6λ�����20λ");
				return false;
			}
		} catch (JSONException e) {
			Log4jUtil.log.error("����û����Ƿ�Ψһʱ����θ�ʽ�ж��쳣", e);
		}
		return true;
	}
}
