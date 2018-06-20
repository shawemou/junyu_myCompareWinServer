package com.my.validate;

import org.json.JSONException;
import org.json.JSONObject;

import com.my.bean.LoginBean;
import com.my.bean.VisitInfoBean;
import com.my.common.ReturnBean;
import com.my.common.EnumInstance.EReturn;
import com.my.common.EnumInstance.EReturnLogin;
import com.my.util.Check;
import com.my.util.Log4jUtil;

/**
 * @ClassName: LoginValidate 
 * @Description: 登录信息验证
 * @author lulinlin
 * @date 2017-11-24 下午02:15:28 
 *
 */
public class LoginValidate {
	
	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean){
		try {
			LoginBean loginBean = new LoginBean();
			JSONObject json = new JSONObject(strJson);
			if( !json.has("loginName") || Check.IsStringNULL(json.getString("loginName"))){
				bean.setCode(EReturnLogin.RT_NotMatch_Null_LoginName);
				return false;
			}else{
				loginBean.setLoginName(json.getString("loginName"));
			}
			
			if( !json.has("password") || Check.IsStringNULL(json.getString("password"))){
				bean.setCode(EReturnLogin.RT_NotMatch_Null_Password);
				return false;
			}else{
				loginBean.setPassword(json.getString("password"));
			}
			
			viBean.setLoginBean(loginBean);
		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return true;
	}
}
