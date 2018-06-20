package com.my.validate;

import org.json.JSONException;
import org.json.JSONObject;

import com.my.bean.CompareBean;
import com.my.bean.VisitInfoBean;
import com.my.common.ReturnBean;
import com.my.common.EnumInstance.EReturn;
import com.my.common.EnumInstance.EReturnCompare;
import com.my.util.Check;
import com.my.util.Log4jUtil;

/**
 * @ClassName: CompareValidate 
 * @Description: 比对信息验证 
 * @author lulinlin
 * @date 2017-11-24 下午02:14:22 
 *
 */
public class CompareValidate {

	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean){
		boolean boo = true;
		try {
			CompareBean compareBean = viBean.getCompareBean();
			JSONObject json = new JSONObject(strJson);
			if( !json.has("loginName") || Check.IsStringNULL(json.getString("loginName"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_LoginName);
				boo = false;
			}else{
				compareBean.setLoginName(json.getString("loginName"));
			}
			
			if( !json.has("name") || Check.IsStringNULL(json.getString("name"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_name);
				boo = false;
			}else{
				compareBean.setName(json.getString("name"));
			}
			
			if( !json.has("certid") || Check.IsStringNULL(json.getString("certid"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_ID);
				boo = false;
			}else{
				compareBean.setCertid(json.getString("certid"));
			}
			
			//证件翻拍照必填
			if( !json.has("strIDPhoto") || Check.IsStringNULL(json.getString("strIDPhoto"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_IDPhoto);
				boo = false;
			}else{
				compareBean.setStrIDPhoto(json.getString("strIDPhoto"));
			}
			
			//现场照必填
			if( !json.has("strUserPhoto") || Check.IsStringNULL(json.getString("strUserPhoto"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_UserPhoto);
				boo = false;
			}else{
				compareBean.setStrUserPhoto(json.getString("strUserPhoto"));
			}
			
			//芯片照不必填
			if( json.has("strChipPhoto") && !Check.IsStringNULL(json.getString("strChipPhoto"))){
				compareBean.setStrChipPhoto(json.getString("strChipPhoto"));
			}
			
			if( json.has("sex")){
				compareBean.setSex(json.getString("sex"));
			}
			if( json.has("birthday")){
				compareBean.setBirthday(json.getString("birthday"));
			}
			if( json.has("fork")){
				compareBean.setFork(json.getString("fork"));
			}
			if( json.has("address")){
				compareBean.setAddress(json.getString("address"));
			}
			if( json.has("issue_authority")){
				compareBean.setIssue_authority(json.getString("issue_authority"));
			}
			if( json.has("vaild_priod")){
				compareBean.setVaild_priod(json.getString("vaild_priod"));
			}
			if( json.has("strChipPhoto")){
				compareBean.setStrChipPhoto(json.getString("strChipPhoto"));
			}
		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return boo;
	}
}
