package com.my.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtil {

	static {
		InputStream in = Log4jUtil.class.getClassLoader().getResourceAsStream("log4j.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(prop);
		// BasicConfigurator.configure();// Ĭ������

		// ��J2EEӦ��ʹ��Log4j������������������ʱ����Log4j�������ļ����г�ʼ����������web.xml�н���

	}
	public static org.apache.log4j.Logger log = Logger.getLogger(Log4jUtil.class);

	public static void main(String[] args) {
		log.info("Info Message!");
	}
}
