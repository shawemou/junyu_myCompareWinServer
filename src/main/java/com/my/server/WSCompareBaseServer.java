package com.my.server;

import com.my.bean.CompareReturnBean;
import com.my.common.ReadSetting;
import com.my.server.Client.JYWebserviceClient;
import com.my.util.GUID;
import com.my.util.Log4jUtil;

public class WSCompareBaseServer {

	private static int TOTAL_COUNT = 2;
	
	/**
	 * 本地比对接口服务
	 * @param viBean
	 * @param bean
	 */
	public static CompareReturnBean localCompare(String strPhoto1, String strPhoto2){
		String[] arrPhoto = new String[]{ strPhoto2};
		String headXml = JYWebserviceClient.createInHeadXml(new GUID().toString(),JYWebserviceClient.iType_V3,JYWebserviceClient.iSubType_Compare);
		String compareDataXml = JYWebserviceClient.createInCompareDataXml(new GUID().toString(), null, null, strPhoto1, arrPhoto);
		
		String compare_url = ReadSetting.getInstance().getCompare_url();
		String clientReturn = null;
		int iTryCount = 0;
		do {
			iTryCount++;
			clientReturn = JYWebserviceClient.executeClient(compare_url , headXml,compareDataXml);
//			Log4jUtil.log.warn(clientReturn);
			if (clientReturn != null) {
				break;
			}
		}while (iTryCount < TOTAL_COUNT);
		
		if(iTryCount > 1){
			Log4jUtil.log.warn("---ESB比对照片次数:" + iTryCount);
		}
		
		return JYWebserviceClient.parserCompareOutXml(JYWebserviceClient.parseXmlForData(clientReturn));
	}
}
