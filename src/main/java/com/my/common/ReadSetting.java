package com.my.common;

import java.io.File;
import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniSection;

public class ReadSetting {
	public final static String SETTINGFILENAME = "WEB-INF\\settings.ini";
	
	private static ReadSetting readSetting = null;

	// ????????
	public static ReadSetting getInstance(String settingpath, String settingFileName) {
		readSetting = new ReadSetting(settingpath, settingFileName);
		return readSetting;
	}

	public static ReadSetting getInstance() {
		return readSetting;
	}

	private ReadSetting() {

	}

	private ReadSetting(String webRootPath, String settingFileName) {
		try {
			String strCompletePath = null;// ?????????????��??
			// ?????????????
			//apache tomcat?????��???????\
			if (webRootPath.endsWith("/") || webRootPath.endsWith("\\")) {
			} else {
				webRootPath = webRootPath + "\\";
			}
			strCompletePath = webRootPath + settingFileName;
			
			File inifile = new File(strCompletePath);
			IniFile ini = new BasicIniFile(true);
			IniFileReader reader = new IniFileReader(ini, inifile);
			reader.read();
			
			IniSection ocr = ini.getSection("ocr");
			if (ocr != null) {
				if( ocr.hasItem("service_url") )
					service_url = ocr.getItem("service_url").getValue();
				if( ocr.hasItem("connection_timeout") )
					connection_timeout = ocr.getItem("connection_timeout").getValue();
				if( ocr.hasItem("so_timeout") )
					so_timeout = ocr.getItem("so_timeout").getValue();
				if( ocr.hasItem("license") )
					license = ocr.getItem("license").getValue();
				if( ocr.hasItem("secret_key") )
					secret_key = ocr.getItem("secret_key").getValue();
			}
			
			IniSection compare = ini.getSection("compare");
			if (compare != null) {
				if( compare.hasItem("compare_url") )
					compare_url = compare.getItem("compare_url").getValue();
				if( compare.hasItem("similarity1") )
					similarity1 = compare.getItem("similarity1").getValue();
				if( compare.hasItem("similarity2") )
					similarity2 = compare.getItem("similarity2").getValue();
				if( compare.hasItem("similarity3") )
					similarity3 = compare.getItem("similarity3").getValue();
			}
			
			IniSection oracle = ini.getSection("oracle");
			if (oracle != null) {
				if( oracle.hasItem("con_url") )
					con_url = oracle.getItem("con_url").getValue();
				if( oracle.hasItem("conn_user") )
					conn_user = oracle.getItem("conn_user").getValue();
				if( oracle.hasItem("conn_pwd") )
					conn_pwd = oracle.getItem("conn_pwd").getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getService_url() {
		return service_url;
	}

	public void setService_url(String serviceUrl) {
		service_url = serviceUrl;
	}

	public String getConnection_timeout() {
		return connection_timeout;
	}

	public void setConnection_timeout(String connectionTimeout) {
		connection_timeout = connectionTimeout;
	}

	public String getSo_timeout() {
		return so_timeout;
	}

	public void setSo_timeout(String soTimeout) {
		so_timeout = soTimeout;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secretKey) {
		secret_key = secretKey;
	}

	public String getCompare_url() {
		return compare_url;
	}

	public void setCompare_url(String compareUrl) {
		compare_url = compareUrl;
	}

	public String getSimilarity1() {
		return similarity1;
	}

	public void setSimilarity1(String similarity1) {
		this.similarity1 = similarity1;
	}

	public String getSimilarity2() {
		return similarity2;
	}

	public void setSimilarity2(String similarity2) {
		this.similarity2 = similarity2;
	}
	public String getSimilarity3() {
		return similarity3;
	}

	public void setSimilarity3(String similarity3) {
		this.similarity3 = similarity3;
	}
	public String getCon_url() {
		return con_url;
	}

	public void setCon_url(String conUrl) {
		con_url = conUrl;
	}

	public String getConn_user() {
		return conn_user;
	}

	public void setConn_user(String connUser) {
		conn_user = connUser;
	}
	public String getConn_pwd() {
		return conn_pwd;
	}
	public void setConn_pwd(String connPwd) {
		conn_pwd = connPwd;
	}

	private String service_url;
	private String connection_timeout;
	private String so_timeout;
	private String license;
	private String secret_key;

	private String compare_url;
	private String similarity1;
	private String similarity2;
	private String similarity3;
	
	private String con_url;
	private String conn_user;
	private String conn_pwd;
}
