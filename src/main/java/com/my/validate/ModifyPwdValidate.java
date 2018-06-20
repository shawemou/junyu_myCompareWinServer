package com.my.validate;

import org.json.JSONException;
import org.json.JSONObject;

import com.my.bean.ModifyPwdBean;
import com.my.bean.VisitInfoBean;
import com.my.common.ReturnBean;
import com.my.common.EnumInstance.EReturn;
import com.my.common.EnumInstance.EReturnModifyPwd;
import com.my.util.Check;
import com.my.util.Log4jUtil;

/**
 * @ClassName: ModifyPwdValidate 
 * @Description:  密码修改验证 
 * @author lulinlin
 * @date 2017-11-24 下午02:16:48 
 *
 */
public class ModifyPwdValidate {
	
	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean){
		try {
			ModifyPwdBean modifyPwdBean = new ModifyPwdBean();
			JSONObject json = new JSONObject(strJson);
			if( !json.has("loginName") || Check.IsStringNULL(json.getString("loginName"))){
				bean.setCode(EReturnModifyPwd.RT_NotMatch_Null_LoginName);
				return false;
			}else{
				modifyPwdBean.setLoginName(json.getString("loginName"));
			}
			
			if( !json.has("password") || Check.IsStringNULL(json.getString("password"))){
				bean.setCode(EReturnModifyPwd.RT_NotMatch_Null_Password);
				return false;
			}else{
				modifyPwdBean.setPassword(json.getString("password"));
			}
			
			if( !json.has("newPassword") || Check.IsStringNULL(json.getString("newPassword"))){
				bean.setCode(EReturnModifyPwd.RT_NotMatch_Null_NewPassword);
				return false;
			}else{
				modifyPwdBean.setNewPassword(json.getString("newPassword"));
			}
			
			viBean.setModifyPwdBean(modifyPwdBean);
		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return true;
	}
}
