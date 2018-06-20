package com.my.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.my.bean.VisitInfoBean;
import com.my.common.ReturnBean;
import com.my.dao.base.CommonDao;
import com.my.util.AESCodec;
import com.my.util.GUID;

/**
 * @ClassName: WSUserDao 
 * @Description: webservice用户 
 * @author lulinlin
 * @date 2017-11-24 下午02:48:02 
 *
 */
public class WSUserDao extends CommonDao {

	/**
	 * 登录
	 * @param viBean
	 * @param bean
	 * @return
	 */
	public List<Map<String, Object>> login(VisitInfoBean viBean, ReturnBean bean){
		String sql = "SELECT PASSWORD,BUSABLE FROM T_USER T WHERE T.LOGIN_NAME = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(viBean.getLoginBean().getLoginName());
		return queryReturnListMap(sql,params);
	}
	
	/**
	 * 登录
	 * @param viBean
	 * @param bean
	 * @return
	 */
	public List<Map<String, Object>> modifyLogin(VisitInfoBean viBean, ReturnBean bean){
		String sql = "SELECT PASSWORD,BUSABLE FROM T_USER T WHERE T.LOGIN_NAME = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(viBean.getModifyPwdBean().getLoginName());
		return queryReturnListMap(sql,params);
	}
	
	/**
	 * 保存登录日志
	 * @param viBean
	 * @param bean
	 * @param ip
	 * @return
	 */
	public boolean saveLoginLog(VisitInfoBean viBean, ReturnBean bean){
		String preparedSql = "INSERT INTO T_USER_LOGIN_LOG (GUID,LOGIN_NAME,PASSWORD,CODE,IP) VALUES (?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(new GUID().toString());
		params.add(viBean.getLoginBean().getLoginName() );
		params.add(AESCodec.aesEncrypt(viBean.getLoginBean().getPassword()));
		params.add(bean.getCode());
		params.add(viBean.getIp());
		return modify(preparedSql, params) > 0;
	}
	
	/**
	 * 修改密码
	 * @param viBean
	 * @param bean
	 * @return
	 */
	public boolean passwordModify(VisitInfoBean viBean, ReturnBean bean){
		String preparedSql = "UPDATE T_USER T SET T.PASSWORD = ?,T.SECRET_TYPE = '2' WHERE T.LOGIN_NAME = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(AESCodec.aesEncrypt( viBean.getModifyPwdBean().getNewPassword() ));
		params.add(viBean.getModifyPwdBean().getLoginName());
		return modify(preparedSql, params) > 0;
	}
}
