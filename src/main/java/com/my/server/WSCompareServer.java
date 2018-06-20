package com.my.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.TestIDPhotoAuthService;
import com.my.bean.CompareReturnBean;
import com.my.bean.VisitInfoBean;
import com.my.common.ReadSetting;
import com.my.common.ReturnBean;
import com.my.common.EnumInstance.EReturn;
import com.my.common.EnumInstance.EReturnCompare;
import com.my.common.EnumInstance.EReturnJY;
import com.my.dao.WSCompareDao;
import com.my.util.Check;
import com.my.util.Log4jUtil;

public class WSCompareServer {

	public void client(VisitInfoBean viBean, ReturnBean bean){
		List<Map<String, Object>> list = new WSCompareDao().findUser(viBean, bean);
		if(list == null){
			bean.setCode(EReturn.RT_InError);
		}else if( list.size() <= 0){
			bean.setCode(EReturnCompare.RT_NotMatch_Format_Login);
		}else{
			Map<String, Object> map = list.get(0);
			viBean.setUserMap(map);
			if( map.get("BUSABLE") == null || map.get("BUSABLE").toString().equals("2") ){
				bean.setCode(EReturnCompare.RT_NotMatch_Format_Busable);
			}else{
				//���رȶԷ���
				localCompare(viBean, bean);
				
				if( EReturnCompare.RT_Success.equals(bean.getCode()) ){
					//��оƬ��ʱ�����̵߳��ö�Դ��֤�ӿ�
					if(!Check.IsStringNULL(viBean.getCompareBean().getStrChipPhoto())){//��оƬ��
						new WSSourceCompareServerThread(viBean).start();
					}else{
						//û��оƬ��ʱ��˳����ö�Դ��֤�ӿ�
						sourceCompare(viBean, bean);
					}
				}
			}
		}
	}
	
	public void localCompare(VisitInfoBean viBean, ReturnBean bean){
		//�����ձȶ��ֳ���
		Future<CompareReturnBean> fs1 = null;
		ExecutorService es1 = Executors.newSingleThreadExecutor();
    	if( !Check.IsStringNULL(viBean.getCompareBean().getStrIDPhoto()) && !Check.IsStringNULL(viBean.getCompareBean().getStrUserPhoto())){
    		Log4jUtil.log.warn("�����ձȶ��ֳ��ձȶԿ�ʼ");
    		fs1 = es1.submit(new WSCompareServerThread(viBean.getCompareBean().getStrIDPhoto(), viBean.getCompareBean().getStrUserPhoto()) );
    	}
    	
    	//�����ձȶ�оƬ��
    	Future<CompareReturnBean> fs2 = null;
    	ExecutorService es2 = Executors.newSingleThreadExecutor();
    	if( !Check.IsStringNULL(viBean.getCompareBean().getStrIDPhoto()) && !Check.IsStringNULL(viBean.getCompareBean().getStrChipPhoto())){
    		Log4jUtil.log.warn("�����ձȶ�оƬ�ձȶԿ�ʼ");
    		fs2 = es2.submit(new WSCompareServerThread(viBean.getCompareBean().getStrIDPhoto(), viBean.getCompareBean().getStrChipPhoto()) );
    	}
    	
    	//�ֳ��ձȶ�оƬ��
    	Future<CompareReturnBean> fs3 = null;
    	ExecutorService es3 = Executors.newSingleThreadExecutor();
    	if( !Check.IsStringNULL(viBean.getCompareBean().getStrUserPhoto()) && !Check.IsStringNULL(viBean.getCompareBean().getStrChipPhoto())){
    		Log4jUtil.log.warn("�ֳ��ձȶ�оƬ�ձȶԿ�ʼ");
    		fs3 = es3.submit(new WSCompareServerThread(viBean.getCompareBean().getStrUserPhoto(), viBean.getCompareBean().getStrChipPhoto()) );
    	}
    	
    	CompareReturnBean rbean1 = null;
    	if(fs1 != null){
			try {
				rbean1 = fs1.get();
				bean.setCrBean1(rbean1);
			} catch (Exception e) {
				Log4jUtil.log.error("�����ձȶ��ֳ��ձȽ��߳�1�쳣", e);
			}finally{
				es1.shutdown();
			}
		}
    	
    	CompareReturnBean rbean2 = null;
    	if(fs2 != null){
			try {
				rbean2 = fs2.get();
				bean.setCrBean2(rbean2);
			} catch (Exception e) {
				Log4jUtil.log.error("�����ձȶ�оƬ�ձȽ��߳�2�쳣", e);
			}finally{
				es2.shutdown();
			}
		}
    	
    	CompareReturnBean rbean3 = null;
    	if(fs3 != null){
			try {
				rbean3 = fs3.get();
				bean.setCrBean3(rbean3);
			} catch (Exception e) {
				Log4jUtil.log.error("�ֳ��ձȶ�оƬ�ձȽ��߳�3�쳣", e);
			}finally{
				es3.shutdown();
			}
		}
    	
    	/**
    	 * ������ȫʱ,fs2�����ձȶ�оƬ��,����ͨ�������������ȶ���һͨ����Ϊͨ��
    	 */
    	if(fs1 != null && fs2 != null && fs3 != null){
    		String code2 = resetCode2(rbean2);
    		if( !code2.equals(EReturnCompare.RT_Success)  ){
	    		bean.setCode(code2);
	    		return;
    		}else{
    			String code1 = resetCode1(rbean1);
    			String code3 = resetCode3(rbean3);
    			if( !code1.equals(EReturnCompare.RT_Success) && !code3.equals(EReturnCompare.RT_Success)  ){
    				bean.setCode(code1);
    				return;
    			}else{
    				bean.setCode(EReturnCompare.RT_Success);
    				return;
    			}
    		}
    	}else if(fs1 != null){
    		//�����ձȶ��ֳ���
    		String code1 = resetCode1(rbean1);
    		bean.setCode(code1);
			return;
    	}else if(fs3 != null){
    		//�ֳ��ձȶ�оƬ��
    		String code3 = resetCode3(rbean3);
    		bean.setCode(code3);
			return;
    	}
	}
	
	private String resetCode1(CompareReturnBean rbean1){
		String code = resetCode(rbean1, ReadSetting.getInstance().getSimilarity1());
		if( !code.equals(EReturnCompare.RT_Success)  ){
    		if( code.equals(EReturn.RT_Timeout) || code.equals(EReturn.RT_InError) || code.equals(EReturnCompare.RT_Fail)){
    			return code;
    		}else if(EReturnJY.RT_NotMatch_Format_IDPhoto.equals(code)){
    			return EReturnCompare.RT_NotMatch_Format_IDPhoto;
    		}else if(EReturnJY.RT_NotMatch_Format_Photo.equals(code)){
    			return EReturnCompare.RT_NotMatch_Format_UserPhoto;
    		}else if( EReturnJY.RT_Fail_Feature_IdPhoto.equals(code)){
    			return EReturnCompare.RT_Not_Face_IDPhoto;
    		}else if( EReturnJY.RT_Fail_Feature.equals(code) ){
    			return EReturnCompare.RT_Not_Face_UserPhoto;
    		}else if( EReturnJY.RT_More_Face_IDPhoto.equals(code) ){
    			return EReturnCompare.RT_More_Face_IDPhoto;
    		}else if( EReturnJY.RT_More_Face_Photo.equals(code) ){
    			return EReturnCompare.RT_More_Face_UserPhoto;
    		}else{
    			return EReturn.RT_InError;
    		}
		}
		return code;
	}
	
	private String resetCode2(CompareReturnBean rbean2){
		String code = resetCode(rbean2, ReadSetting.getInstance().getSimilarity2());
		if( !code.equals(EReturnCompare.RT_Success)  ){
    		if( code.equals(EReturn.RT_Timeout) || code.equals(EReturn.RT_InError) || code.equals(EReturnCompare.RT_Fail)){
    			return code;
    		}else if(EReturnJY.RT_NotMatch_Format_IDPhoto.equals(code)){
    			return EReturnCompare.RT_NotMatch_Format_IDPhoto;
    		}else if(EReturnJY.RT_NotMatch_Format_Photo.equals(code)){
    			return EReturnCompare.RT_NotMatch_Null_ChipPhoto;
    		}else if( EReturnJY.RT_Fail_Feature_IdPhoto.equals(code)){
    			return EReturnCompare.RT_Not_Face_IDPhoto;
    		}else if( EReturnJY.RT_Fail_Feature.equals(code) ){
    			return EReturnCompare.RT_Not_Face_ChipPhoto;
    		}else if( EReturnJY.RT_More_Face_IDPhoto.equals(code) ){
    			return EReturnCompare.RT_More_Face_IDPhoto;
    		}else if( EReturnJY.RT_More_Face_Photo.equals(code) ){
    			return EReturnCompare.RT_More_Face_ChipPhoto;
    		}else{
    			return EReturn.RT_InError;
    		}
		}
		return code;
	}
	
	private String resetCode3(CompareReturnBean rbean3){
		String code = resetCode(rbean3, ReadSetting.getInstance().getSimilarity3());
		if( !code.equals(EReturnCompare.RT_Success)  ){
    		if( code.equals(EReturn.RT_Timeout) || code.equals(EReturn.RT_InError) || code.equals(EReturnCompare.RT_Fail)){
    			return code;
    		}else if(EReturnJY.RT_NotMatch_Format_IDPhoto.equals(code)){
    			return EReturnCompare.RT_NotMatch_Format_UserPhoto;
    		}else if(EReturnJY.RT_NotMatch_Format_Photo.equals(code)){
    			return EReturnCompare.RT_NotMatch_Null_ChipPhoto;
    		}else if( EReturnJY.RT_Fail_Feature_IdPhoto.equals(code)){
    			return EReturnCompare.RT_Not_Face_UserPhoto;
    		}else if( EReturnJY.RT_Fail_Feature.equals(code) ){
    			return EReturnCompare.RT_Not_Face_ChipPhoto;
    		}else if( EReturnJY.RT_More_Face_IDPhoto.equals(code) ){
    			return EReturnCompare.RT_More_Face_UserPhoto;
    		}else if( EReturnJY.RT_More_Face_Photo.equals(code) ){
    			return EReturnCompare.RT_More_Face_ChipPhoto;
    		}else{
    			return EReturn.RT_InError;
    		}
		}
		return code;
	}
	
	/**
	 * ���ƶȱȽ�
	 * @param ri
	 * @param cbean
	 * @return
	 */
	private static String resetCode(CompareReturnBean rbean, String similarity){
		if( rbean == null){
			return EReturn.RT_InError;
		}else{
			if( rbean.getCode() == null || EReturn.RT_InError.equals(rbean.getCode()) ){
				return EReturn.RT_InError;
			}else if(EReturn.RT_Timeout.equals(rbean.getCode())){
				return EReturn.RT_Timeout;
			}else if(EReturnJY.RT_Success.equals(rbean.getCode())
					|| EReturnJY.RT_Unsure.equals(rbean.getCode())
					|| EReturnJY.RT_Fail.equals(rbean.getCode()) ){
				if(rbean.getSimilarity() >= Check.IsNum(similarity)){
					return EReturnCompare.RT_Success;
				}else{
					return EReturnCompare.RT_Fail;
				}
				
			//��ࡢ�Ҳ�������ʧ�ܣ���ࡢ�Ҳ��⵽��������
			}else if(EReturnJY.RT_Fail_Feature_IdPhoto.equals(rbean.getCode())
					|| EReturnJY.RT_Fail_Feature.equals(rbean.getCode())
					|| EReturnJY.RT_NotMatch_Format_IDPhoto.equals(rbean.getCode())
					|| EReturnJY.RT_NotMatch_Format_Photo.equals(rbean.getCode()) 
					|| EReturnJY.RT_More_Face_IDPhoto.equals(rbean.getCode()) 
					|| EReturnJY.RT_More_Face_Photo.equals(rbean.getCode()) ){
				return rbean.getCode();
			}else{
				return EReturn.RT_InError;
			}
		}
	}
	
	/**
	 * ��Դ��֤�ӿڷ���
	 * @param viBean
	 * @param bean
	 */
	public void sourceCompare(VisitInfoBean viBean, ReturnBean bean){
		CompareReturnBean crBean4 = bean.getCrBean4();
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
		}
	}
}
