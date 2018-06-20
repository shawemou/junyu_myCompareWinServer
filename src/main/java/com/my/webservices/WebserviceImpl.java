package com.my.webservices;

import org.json.JSONException;
import org.json.JSONObject;

import com.my.bean.VisitInfoBean;
import com.my.common.EnumInstance;
import com.my.common.EnumInstance.EReturnCompare;
import com.my.common.EnumInstance.EReturnLogin;
import com.my.common.EnumInstance.EReturnModifyPwd;
import com.my.common.EnumInstance.EReturnSource;
import com.my.common.ReturnBean;
import com.my.dao.WSCompareDao;
import com.my.server.WSCompareServer;
import com.my.server.WSUserServer;
import com.my.util.Check;
import com.my.util.Log4jUtil;
import com.my.validate.MainValidate;

/**
 * @ClassName: JYWebserviceImpl 
 * @Description: ���ӿڷ��񣬶��ⷢ��Webservice
 * @author lulinlin
 * @date 2015-7-28 ����10:16:14 
 *
 */
public class WebserviceImpl implements IWebservice {

	/**
	 * ���Ե���
	 * @param message ������Ϣ
	 * @return 
	 */
	public String example(String message) {
		return message + "_success";
	}

	/**
	 * �������
	 */
	public String method(String strJson) {
		Log4jUtil.log.warn("��ʼ:" + strJson.length());
		
		VisitInfoBean viBean = new VisitInfoBean();
		ReturnBean bean = new ReturnBean();
		
		//������Ϣ��֤
		if( !MainValidate.validate(strJson, viBean, bean)){
			return getReturnStr(viBean, bean);
		}
		
		if( EnumInstance.loginType.equals(viBean.getType())){
			new WSUserServer().login(viBean, bean);
		}else if(EnumInstance.modifyPwdType.equals(viBean.getType())){
			new WSUserServer().passwordModify(viBean, bean);
		}else if(EnumInstance.compareType.equals(viBean.getType())){
			new WSCompareServer().client(viBean, bean);
		}
		
		return getReturnStr(viBean, bean);
	}

	private String getReturnStr(VisitInfoBean viBean, ReturnBean bean){
		JSONObject json = new JSONObject();
		try {
			json.put("code", Integer.parseInt(bean.getCode()));
			if( EnumInstance.loginType.equals(viBean.getType())){
				json.put("info", EReturnLogin.map.get(bean.getCode()));
			}else if(EnumInstance.modifyPwdType.equals(viBean.getType())){
				json.put("info", EReturnModifyPwd.map.get(bean.getCode()));
			}else if(EnumInstance.compareType.equals(viBean.getType())){
				setCompareInfo(viBean, bean, json);
				new WSCompareDao().saveCompare(viBean, bean);
			}else{
				json.put("info", EReturnLogin.map.get(bean.getCode()));
			}
			Log4jUtil.log.warn("����:" + json.toString());
		} catch (JSONException e) {
			Log4jUtil.log.error("��װ����json�쳣", e);
		}
		return json.toString();
	}
	
	/**
	 * �ȶԷ�����д��������
	 * @param viBean
	 * @param bean
	 */
	private void setCompareInfo(VisitInfoBean viBean, ReturnBean bean,JSONObject json){
		try {
			String info = EReturnCompare.map.get(bean.getCode());
			json.put("code", Integer.parseInt(bean.getCode()));
			json.put("info", info );
			
//			��Դ�ӿڵ���ʧ��ʱ�����ζ��巵�����������ע��ʱ��2018��04��19��
//			��оƬ��ʱ�����ձȶ�ͨ��ʱ����Դ�ӿڵ��÷�ʽ�Ѹ�Ϊ���̵߳���
//			û��оƬ��ʱ�����ĺ��ֳ��ȶ�ͨ��ʱ��˳����ö�Դ�ӿ�
			if(EReturnCompare.RT_Success.equals(bean.getCode()) && !Check.IsStringNULL(bean.getCrBean4().getCode()) ){
				if( !EReturnSource.RT_Success.equals(bean.getCrBean4().getCode())){
					bean.setCode(EReturnCompare.RT_InError_source);
					String info4 = bean.getCrBean4().getInfo() == null ? "" : (":" + bean.getCrBean4().getInfo());
					info = EReturnCompare.map.get(bean.getCode()) + info4;
					json.put("code", Integer.parseInt(bean.getCode()));
					json.put("info", info);
				}
			}
			bean.setInfo(info);
			
//			if(EReturnCompare.RT_Success.equals(bean.getCode()) ){
//				if( Check.IsStringNULL(bean.getCrBean4().getCode()) ){
//					bean.setCode(EReturnCompare.RT_InError_source);
//					json.put("code", Integer.parseInt(bean.getCode()));
//					json.put("info", EReturnCompare.map.get(bean.getCode()));
//				}else if(EReturnSource.RT_Success.equals(bean.getCrBean4().getCode())){
//					json.put("info", EReturnCompare.map.get(bean.getCode()));
//				}else{
//					bean.setCode(EReturnCompare.RT_InError_source);
//					String info = bean.getCrBean4().getInfo() == null ? "" : (":" + bean.getCrBean4().getInfo());
//					json.put("code", Integer.parseInt(bean.getCode()));
//					json.put("info", EReturnCompare.map.get(bean.getCode()) + info );
//				}
//			}else{
//				json.put("info", EReturnCompare.map.get(bean.getCode()));
//			}
		} catch (JSONException e) {
			Log4jUtil.log.error("��װ�ȶԽӿڷ���json�쳣", e);
		}
	}
	
}
