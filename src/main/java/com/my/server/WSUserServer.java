package com.my.server;

import java.util.List;
import java.util.Map;

import com.my.bean.VisitInfoBean;
import com.my.common.ReturnBean;
import com.my.common.EnumInstance.EReturn;
import com.my.common.EnumInstance.EReturnLogin;
import com.my.common.EnumInstance.EReturnModifyPwd;
import com.my.dao.WSUserDao;
import com.my.util.AESCodec;

/**
 * @ClassName: WSUserServer 
 * @Description: webservice用户 
 * @author lulinlin
 * @date 2017-11-24 下午04:46:09 
 *
 */
public class WSUserServer {

	/**
	 * 用户登录
	 * @param viBean
	 * @param bean
	 */
	public void login(VisitInfoBean viBean, ReturnBean bean){
		WSUserDao userDao = new WSUserDao();
		List<Map<String, Object>> list = userDao.login(viBean,bean);
		if(list == null){
			bean.setCode(EReturn.RT_InError);
		}else if( list.size() <= 0){
			bean.setCode(EReturnLogin.RT_NotMatch_Format_Login);
		}else{
			Map<String, Object> map = list.get(0);
			if( map.get("BUSABLE") == null || map.get("BUSABLE").toString().equals("2") ){
				bean.setCode(EReturnLogin.RT_NotMatch_Format_Busable);
			}else{
				if( map.get("PASSWORD") == null ){
					bean.setCode(EReturnLogin.RT_NotMatch_Format_Login);
				}else{
					String password = AESCodec.aesEncrypt(viBean.getLoginBean().getPassword());
					if(map.get("PASSWORD").toString().equalsIgnoreCase(password)){
						bean.setCode(EReturnLogin.RT_Success);
					}else{
						bean.setCode(EReturnLogin.RT_NotMatch_Format_Login);
					}
				}
			}
		}
		userDao.saveLoginLog(viBean, bean);
	}
	
	/**
	 * 密码修改
	 * @param viBean
	 * @param bean
	 */
	public void passwordModify(VisitInfoBean viBean, ReturnBean bean){
		List<Map<String, Object>> list = new WSUserDao().modifyLogin(viBean,bean);
		if(list == null){
			bean.setCode(EReturn.RT_InError);
		}else if( list.size() <= 0){
			bean.setCode(EReturnModifyPwd.RT_NotMatch_Format_Login);
		}else{
			Map<String, Object> map = list.get(0);
			if( map.get("BUSABLE") == null || map.get("BUSABLE").toString().equals("2") ){
				bean.setCode(EReturnModifyPwd.RT_NotMatch_Format_Busable);
			}else{
				if( map.get("PASSWORD") == null ){
					bean.setCode(EReturnModifyPwd.RT_NotMatch_Format_Login);
				}else{
					String password = AESCodec.aesEncrypt(viBean.getModifyPwdBean().getPassword());
					if(map.get("PASSWORD").toString().equalsIgnoreCase(password)){
						if(new WSUserDao().passwordModify(viBean, bean)){
							bean.setCode(EReturnModifyPwd.RT_Success);
						}else{
							bean.setCode(EReturn.RT_InError);
						}
					}else{
						bean.setCode(EReturnModifyPwd.RT_NotMatch_Format_Login);
					}
				}
			}
		}
	}
}
