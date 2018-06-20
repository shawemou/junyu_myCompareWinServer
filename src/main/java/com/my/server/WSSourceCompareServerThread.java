package com.my.server;

import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.TestIDPhotoAuthService;
import com.my.bean.CompareReturnBean;
import com.my.bean.VisitInfoBean;
import com.my.dao.WSSourceCompareDao;
import com.my.util.Check;
import com.my.util.Log4jUtil;

/**
 * @ClassName: WSSourceCompareServerThread 
 * @Description: ���̵߳��ö�Դ�ӿڣ�����ȶԽ��
 * @author lulinlin
 * @date 2018-4-19 ����04:41:18 
 *
 */
public class WSSourceCompareServerThread extends Thread {

	private VisitInfoBean viBean;
	
	public WSSourceCompareServerThread(){}
	
	public WSSourceCompareServerThread(VisitInfoBean viBean){
		this.viBean = viBean;
	}
	
	public void run(){
		CompareReturnBean crBean4 = new CompareReturnBean();//��Դ��֤�ӿڱȶԽ��
		String resultString = TestIDPhotoAuthService.runQryIDPhoto(viBean.getCompareBean().getName(),viBean.getCompareBean().getCertid(), viBean.getCompareBean().getStrIDPhoto());
		if( !Check.IsStringNULL(resultString)){
			try {
				JSONObject json = new JSONObject(resultString);
				if(json.has("Result") && !Check.IsStringNULL(json.getString("Result")) ){
					crBean4.setCode(json.getString("Result"));
				}
				if(json.has("Return") && !Check.IsStringNULL(json.getString("Return")) ){
					crBean4.setInfo(json.getString("Return"));
				}
			} catch (JSONException e) {
				Log4jUtil.log.error("������Դ��֤�ӿڷ���json�쳣", e);
			}
			
			if( !Check.IsStringNULL(crBean4.getCode()) || !Check.IsStringNULL(crBean4.getInfo()) ){
				new WSSourceCompareDao().saveCompare(viBean, crBean4);
			}
		}
	}

	public VisitInfoBean getViBean() {
		return viBean;
	}

	public void setViBean(VisitInfoBean viBean) {
		this.viBean = viBean;
	}
}
