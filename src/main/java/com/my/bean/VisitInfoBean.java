package com.my.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xfire.transport.http.XFireServletController;

import com.my.util.GUID;

public class VisitInfoBean {

	private String guid;
	private String type;
	private String ip;
	public Date beginDate = new Date();
	
	private LoginBean loginBean;
	private ModifyPwdBean modifyPwdBean;
	private CompareBean compareBean = new CompareBean();
	
	private Map<String, Object> userMap = new HashMap<String, Object>();
	
	public VisitInfoBean(){
		this.guid = new GUID().toString();
		this.ip = XFireServletController.getRequest().getRemoteAddr();
		
	}
	
	
	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public ModifyPwdBean getModifyPwdBean() {
		return modifyPwdBean;
	}

	public void setModifyPwdBean(ModifyPwdBean modifyPwdBean) {
		this.modifyPwdBean = modifyPwdBean;
	}

	public CompareBean getCompareBean() {
		return compareBean;
	}

	public void setCompareBean(CompareBean compareBean) {
		this.compareBean = compareBean;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Map<String, Object> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, Object> userMap) {
		this.userMap = userMap;
	}
}
