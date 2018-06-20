package com.my.server;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.my.dao.WebUserDao;
import com.my.util.Check;
import com.my.util.Log4jUtil;

public class WebUserServer  extends WebUserValidateServer{
	public String methed(String method, Map<String,String> userMap,HttpServletRequest request){
		JSONObject resultJson = new JSONObject();
		try{
			if( !Check.IsStringNULL(method) ){
				if( method.equals("list") ){//�б�
					String page_no = userMap.get("page_no");
					if(Check.BeginIndex(page_no) <= 0){
						userMap.put("page_no","1");
					}
					resultJson.put("data", setJsonList( list(userMap) ));
					resultJson.put("itemCount",queryCount(userMap) );
					resultJson.put("page_no",userMap.get("page_no") );
				}else if(method.equals("add")){//����
					if(validate(userMap, resultJson, "1")){
						Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute("user");
						int i = saveUser(userMap, loginMap);//0ʧ�ܣ�1�ɹ� ��2�Ѵ���
						if(i == 0){
							resultJson.put("success",false);
							resultJson.put("info","����ʧ��");
						}else if(i == 1){
							resultJson.put("success",true);
							resultJson.put("info","����ɹ�");
						}else {
							resultJson.put("success",false);
							resultJson.put("info","��¼���Ѵ����޷�����");
						}
					}
				}else if( method.equals("update") ){//�޸�
					if(validate(userMap, resultJson, "2")){
						int i = updateUser(userMap);
						if(i == 0){
							resultJson.put("success",false);
							resultJson.put("info","����ʧ��");
						}else if(i == 1){
							resultJson.put("success",true);
							resultJson.put("info","����ɹ�");
						}else{
							resultJson.put("success",false);
							resultJson.put("info","��¼���Ѵ����޷�����");
						}
					}
				}else if(method.equals("checkLoginName")){//����¼���Ƿ����
					if( validateLoginName(userMap, resultJson) ){
						int i = checkLoginName(userMap);
						if( i == 0){
							resultJson.put("success",true);
							resultJson.put("info","�˵�¼������ʹ��");
						}else if(i == 1){
							resultJson.put("success",false);
							resultJson.put("info","�˵�¼���Ѵ���");
						}else{
							resultJson.put("success",false);
							resultJson.put("info","��ѯʧ��,�������쳣");
						}
					}
				}else if(method.equals("updatePwd") ){//��������
					if( Check.IsStringNULL(userMap.get("guid")) ){
						resultJson.put("success",false);
						resultJson.put("info","��Ϣȱʧ,�޷���������");
					}else{
						if( updatePwd(userMap.get("guid")) > 0){
							resultJson.put("success",true);
							resultJson.put("info","��������ɹ�");
						}else{
							resultJson.put("success",false);
							resultJson.put("info","��������ʧ��");
						}
					}
				}else if(method.equals("loadUser")){//���ص����û���Ϣ
					resultJson = setJsonMap(loadUser(userMap));
				}else{
					resultJson.put("success",false);
					resultJson.put("info","���õ�ַ������");
				}
				
			}else{
				resultJson.put("success",false);
				resultJson.put("info","���õ�ַ������");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson.toString();
	}
	
	public List<Map<String, Object>> list(Map<String,String> userMap){
		List<Map<String, Object>> list = new WebUserDao().list(userMap);
		for(Map<String, Object> map : list){
			if(map.get("SECRET_TYPE") != null ){
				if( map.get("SECRET_TYPE").toString().equals("2")){
					map.put("SECRET_KEY", "�û����޸�");
				}else{
					map.put("SECRET_KEY", "��ʼ����");
				}
			} 
		}
		return list;
	}
	
	public int queryCount(Map<String,String> userMap){
		return new WebUserDao().queryCount(userMap);
	}
	
	public int saveUser(Map<String,String> userMap,Map<String, Object> loginMap){
		return new WebUserDao().saveUser(userMap, loginMap);
	}
	
	public Map<String, Object> loadUser(Map<String,String> userMap){
		return new WebUserDao().loadUser(userMap.get("guid"));
	}
	
	public int updateUser(Map<String,String> userMap){
		return new WebUserDao().updateUser(userMap);
	}
	
	public int checkLoginName(Map<String,String> userMap){
		return new WebUserDao().checkLoginName(userMap);
	}
	
	public int updatePwd(String guid){
		return new WebUserDao().updatePwd(guid);
	}
	
	public static org.json.JSONArray setJsonList(List<Map<String,Object>> list){
		org.json.JSONArray jary = new org.json.JSONArray();
		for(Map<String,Object> map : list){
			jary.put(map);
		}
		return jary;
	}
	public static JSONObject setJsonMap(Map<String,Object> map){
		JSONObject resultJSON = new JSONObject();
		try{
			for (Entry<String, Object> entry : map.entrySet()) {  
				resultJSON.put(entry.getKey(), entry.getValue());
			}  
		}catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.log.error("Mapתjson����", e);
		}
		return resultJSON;
	}
}
