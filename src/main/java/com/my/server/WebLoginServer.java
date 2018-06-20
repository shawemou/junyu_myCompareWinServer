package com.my.server;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.my.common.BaseReturn;
import com.my.dao.WebLoginDao;
import com.my.util.AESCodec;

public class WebLoginServer {

	public BaseReturn login(String login_name, String login_psd,BaseReturn br, HttpServletRequest request){
		List<Map<String, Object>> list = new WebLoginDao().login(login_name);
		if(list == null || list.size() <= 0){
			br.setSuccess(false);
			br.setInfo("��¼ʧ��,��¼�������벻����");
			return br;
		}else{
			Map<String, Object> map = list.get(0);
			if(map.get("BUSABLE") != null && map.get("BUSABLE").toString().equals("0")){
				br.setSuccess(false);
				br.setInfo("���û���ͣ��");
				return br;
			}

			if(map.get("LOGIN_PWD") == null || !map.get("LOGIN_PWD").toString().equals(AESCodec.aesEncrypt(login_psd))){
				br.setSuccess(false);
				br.setInfo("������֤��ͨ��");
				return br;
			}
			request.getSession().setAttribute("user", map);
		}
		
		br.setSuccess(true);
		br.setInfo("��¼�ɹ�");
		return br;
	}
}
