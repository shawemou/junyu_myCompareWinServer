package com.my.server.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.my.bean.CompareReturnBean;
import com.my.common.EnumInstance;
import com.my.common.ReturnBean;
import com.my.common.EnumInstance.EReturn;
import com.my.common.EnumInstance.EReturnCompare;
import com.my.util.Check;
import com.my.util.Log4jUtil;

public class JYWebserviceClient{
	public static final int iType = 2201;          // ���رȶԱ�־λ3.9�㷨
	public static final int iSubType_Compare = 1;  //1����֤���ձȶԶ���ץ����
	public static final int iType_V2 = 2202;       //���رȶԱ�־λ4.0�㷨��2015-08-28 ½����
	public static final int iType_V3 = 2204;       //���رȶԱ�־λ4.1�㷨
	public static final int iSubType_Photo = 2;    //2���֤����
	public static final int TIME_OUT_Default = 1000 * 10;// 2015-07-01��60���Ϊ5�� 2015-08-28 ��Ϊ10��½����
	public static final int TIME_OUT_LONG = 1000 * 60;
	
	public static final int iType_V5 = 2203;       //�˹���ϯ

	public static final int iSubType_Shear = 3;    //3��Ƭ����
	
	private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
	private HashMap endpoints = new HashMap();
	private Service service0;
	public JYWebserviceClient() {
		create0();
		Endpoint JYWebserviceSoapEP = service0.addEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoap"), new QName(
				"http://tempuri.org/", "JYWebserviceSoap"),
				"http://127.0.0.1/service.asmx");
		endpoints.put(new QName("http://tempuri.org/", "JYWebserviceSoap"),
				JYWebserviceSoapEP);
		Endpoint JYWebserviceSoapLocalEndpointEP = service0.addEndpoint(
				new QName("http://tempuri.org/",
						"JYWebserviceSoapLocalEndpoint"), new QName(
						"http://tempuri.org/", "JYWebserviceSoapLocalBinding"),
				"xfire.local://JYWebservice");
		endpoints.put(new QName("http://tempuri.org/",
				"JYWebserviceSoapLocalEndpoint"),
				JYWebserviceSoapLocalEndpointEP);
	}

	public JYWebserviceClient(String url) {
		create0();
		Endpoint JYWebserviceSoapEP = service0.addEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoap"), new QName(
				"http://tempuri.org/", "JYWebserviceSoap"), "http://" + url);
		endpoints.put(new QName("http://tempuri.org/", "JYWebserviceSoap"),
				JYWebserviceSoapEP);
		Endpoint JYWebserviceSoapLocalEndpointEP = service0.addEndpoint(
				new QName("http://tempuri.org/",
						"JYWebserviceSoapLocalEndpoint"), new QName(
						"http://tempuri.org/", "JYWebserviceSoapLocalBinding"),
				"xfire.local://JYWebservice");
		endpoints.put(new QName("http://tempuri.org/",
				"JYWebserviceSoapLocalEndpoint"),
				JYWebserviceSoapLocalEndpointEP);
	}

	public Object getEndpoint(Endpoint endpoint) {
		try {
			return proxyFactory.create((endpoint).getBinding(), (endpoint)
					.getUrl());
		} catch (MalformedURLException e) {
			throw new XFireRuntimeException("Invalid URL", e);
		}
	}

	public Object getEndpoint(QName name) {
		Endpoint endpoint = ((Endpoint) endpoints.get((name)));
		if ((endpoint) == null) {
			throw new IllegalStateException("No such endpoint!");
		}
		return getEndpoint((endpoint));
	}

	public Collection getEndpoints() {
		return endpoints.values();
	}

	private void create0() {
		TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance()
				.getXFire().getTransportManager());
		HashMap props = new HashMap();
		props.put("annotations.allow.interface", true);
		AnnotationServiceFactory asf = new AnnotationServiceFactory(
				new Jsr181WebAnnotations(), tm, new AegisBindingProvider(
						new JaxbTypeRegistry()));
		asf.setBindingCreationEnabled(false);
		service0 = asf.create((JYWebserviceSoap.class), props);
		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0,
					new QName("http://tempuri.org/", "JYWebserviceSoap"),
					"http://schemas.xmlsoap.org/soap/http");
		}
		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0,
					new QName("http://tempuri.org/",
							"JYWebserviceSoapLocalBinding"),
					"urn:xfire:transport:local");
		}
	}

	public JYWebserviceSoap getJYWebserviceSoap() {
		return ((JYWebserviceSoap) (this).getEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoap")));
	}

	public JYWebserviceSoap getJYWebserviceSoap(String url) {
		JYWebserviceSoap var = getJYWebserviceSoap();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	public JYWebserviceSoap getJYWebserviceSoapLocalEndpoint() {
		return ((JYWebserviceSoap) (this).getEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoapLocalEndpoint")));
	}

	public JYWebserviceSoap getJYWebserviceSoapLocalEndpoint(String url) {
		JYWebserviceSoap var = getJYWebserviceSoapLocalEndpoint();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	/**
	 * �õ�JYESBHead xml��Ϣ
	 * 
	 * @param strGuid ÿ������
	 * @param iType
	 * @param iSubType
	 * @return JYESBHead xmlͷ��Ϣ
	 */
	public static String createInHeadXml(String strGuid, int iType, int iSubType) {
		return "<JYESBHead><iVersion>1000</iVersion><iType>" + iType
				+ "</iType><iSubType>" + iSubType
				+ "</iSubType><iMsgAttribute>0</iMsgAttribute>"
				+ "<strTaskGuid>" + strGuid + "</strTaskGuid>" + "</JYESBHead>";
	}
	
	/**
	 * �����������ݰ���InXml(��C++ͨ��)
	 * 
	 * @param strDataBase64
	 * @return xml�ַ���
	 */
	public static String createDecodeInXml(String strDataBase64) {
		return "<JYSendRoot><Data><Data_Base64>" + strDataBase64
				+ "</Data_Base64></Data></JYSendRoot>";
	}

	/**
	 * ��̬���ɱȶ������xml�ַ�������Ҫ�������֤��Ƭ������������Ƭ����Ϣ
	 * 
	 * @param strTaskGuid
	 * @param strName
	 * @param strId
	 * @param strIdPhoto
	 * @param arrPhoto
	 * @return
	 */
	public static String createInCompareDataXml(String strTaskGuid, String strName, String strId, String strIdPhoto, String[] arrPhoto) {
		String xmlStr = null;
		ByteArrayOutputStream bos = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("CompareRoot");
			document.appendChild(root);

			Element taskGuidE = document.createElement("TaskGuid");
			taskGuidE.appendChild(document.createTextNode(strTaskGuid));
			root.appendChild(taskGuidE);

			Element nameE = document.createElement("PersonName");
			if (strName != null)
				nameE.appendChild(document.createTextNode(strName));
			root.appendChild(nameE);

			Element idE = document.createElement("PersonId");
			if (strId != null)
				idE.appendChild(document.createTextNode(strId));
			root.appendChild(idE);

			Element idPhoto_Base64E = document.createElement("IdPhoto_Base64");
			idPhoto_Base64E.appendChild(document.createTextNode(strIdPhoto));
			root.appendChild(idPhoto_Base64E);

			Element itemsE = document.createElement("Items");
			if (arrPhoto != null) {
				for (int i = 0; i < arrPhoto.length; i++) {
					Element itemE = document.createElement("Item");
					Element optimalFace_Base64E = document
							.createElement("Photo_Base64");
					optimalFace_Base64E.appendChild(document
							.createTextNode(arrPhoto[i]));
					// optimalFace_Base64E.appendChild(document
					// .createTextNode(""));//������
					itemE.appendChild(optimalFace_Base64E);
					itemsE.appendChild(itemE);
				}
			}
			root.appendChild(itemsE);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();

			// ������������
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			bos = new ByteArrayOutputStream();

			transformer.transform(new DOMSource(document),
					new StreamResult(bos));
			xmlStr = bos.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return xmlStr;
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		return xmlStr;
	}

	/**
	 * ���������Ƭ�Ĵ���XML
	 * @param IDPhotos ��Ҫ�����Ƭ��
	 * @return xml�ַ���
	 */
	public static String createCheckPhotoInXml(String[] IDPhotos) {
		String strXml = null;
		if (IDPhotos == null || IDPhotos.length <= 0) {
			return strXml;
		}
		strXml = "<JYSendRoot>" + "<Data>" + "<IDPhotos>";
		for (String strPhoto : IDPhotos) {
			strXml += "<Item>" + strPhoto + "</Item>";
		}
		strXml += "</IDPhotos>" + "</Data>" + "</JYSendRoot>";
		return strXml;
	}
	
	/**
	 * 
	 * ��������֤������xml
	 * @param IDPhoto
	 * @return
	 */
	public static String createShearPhotoInXml(String IDPhoto){
		return "<JYSendRoot><Photo_Base64>" + IDPhoto + "</Photo_Base64></JYSendRoot>";
	}

	/**
	 * �����ӿڷ��ص�xml,ֻ������һ���װ
	 * @param strXmlIn JYESBReturn���ص�xml�ַ���
	 * @param rtInfo
	 * @return �ж�ESB���سɹ����,�õ�strData�ڵ����Ϣ(�ڵ���ϢҲ��xml�ַ���)
	 */
	public static String parseXmlForData(String strXmlIn) {
		if (strXmlIn == null || ("").equals(strXmlIn)) {
			return null;
		}
		StringReader sr = new StringReader(strXmlIn);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			Element root = document.getDocumentElement();

			NodeList iReturnNode = root.getElementsByTagName("iReturnType");
			if (iReturnNode != null && iReturnNode.getLength() > 0) {
				int iReturn = Integer.parseInt(iReturnNode.item(0).getTextContent());// ESB����ֵ >0�Ǿ��� 12002����δ����
				if (iReturn >= 0 && iReturn <= 2) {
					NodeList returnDataNode = root.getElementsByTagName("strData");
					if (returnDataNode != null && returnDataNode.getLength() > 0) {
						Element returnDataE = (Element) returnDataNode.item(0);
						return returnDataE.getTextContent();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ���������Ƭ�ķ���ֵ
	 * @param xmlString
	 * @param IDPhotos �������Ƭ��
	 * @param iPhotoReturn ��Ƭ�ļ����������
	 * @return
	 */
	public static boolean parserCheckOutXml(String xmlString) {
		if (Check.IsStringNULL(xmlString)) {
			return false;
		}
		Document document = null;
		try {
			StringReader sr = new StringReader(xmlString);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList Return = root.getElementsByTagName("iReturn");
			if (Return != null && Return.getLength() == 1) {
				Element e = (Element) Return.item(0);
				if (!Check.IsStringNULL(e.getTextContent())) {
					int iReturn = Check.IsNum(e.getTextContent());
					if (iReturn >= 0) {// ʧ�ܵĿ���:-1����xmlʧ��;-1000�㷨��ʼ��ʧ��
						NodeList idPhotoItemNL = root.getElementsByTagName("Item");
						if (idPhotoItemNL != null) {
							Element itemE = null;
							for (int i = 0; i < idPhotoItemNL.getLength(); i++) {
								itemE = (Element) idPhotoItemNL.item(i);
								iReturn = Check.IsNum((itemE.getElementsByTagName("Result")).item(0).getTextContent());
								if (iReturn >= 0){
//									Log4jUtil.log.info((itemE.getElementsByTagName("strPhoto")).item(0).getTextContent());
									return true;
								}
							}
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	/**
	 * �����ȶԵ�C++ webservice���xml��ʽ
	 * @param xmlString
	 * @param rtInfo
	 * @return
	 */
	public static CompareReturnBean parserCompareOutXml(String xmlString) {
		CompareReturnBean bean = new CompareReturnBean();
		if (Check.IsStringNULL(xmlString)) {
			bean.setCode(EReturn.RT_Timeout);
			return bean;
		}
		
		Log4jUtil.log.warn(xmlString);
		
		// ����������XmlString�ַ������xml�ļ���ʽ����
		Document document = null;
		try {
			StringReader sr = new StringReader(xmlString);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList Return = root.getElementsByTagName("iReturn");
			if (Return.getLength() >= 1) {
				Element e = (Element) Return.item(0);

				if (!Check.IsStringNULL(e.getTextContent())) {
					bean.setCode(e.getTextContent());
					
					// ��־��¼strLastErr
					NodeList lastErrNL = root.getElementsByTagName("strLastErr");
					if (lastErrNL != null && lastErrNL.getLength() > 0 
							&& !Check.IsStringNULL(lastErrNL.item(0).getTextContent())) {
						Log4jUtil.log.warn(lastErrNL.item(0).getTextContent());
					}
					
					NodeList simNList = root.getElementsByTagName("iSim");
					if (simNList != null && simNList.getLength() > 0) {
						//�ȶ� ���ؽ�� ����
						for (int i = 0; i < simNList.getLength(); i++) {
							int iSimilarity = Integer.valueOf(simNList.item(i).getTextContent());
							if(iSimilarity >= 0){
								bean.setSimilarity(iSimilarity);
							}else{
								bean.setCode(String.valueOf(iSimilarity));
							}
						}
					}
				}else{
					bean.setCode(EReturn.RT_InError);
				}
			}
		} catch (Exception e1) {
			Log4jUtil.log.error("�ȶԷ����쳣", e1);
			bean.setCode(EReturn.RT_InError);
		}
		return bean;
	}

	/**
	 * ִ�е���C++
	 * ���ص��ü��֤����webservice�ӿڣ�����xml�ַ���
	 * @param URL      �ӿ�url
	 * @param strHead  ���(��������xml)
	 * @param strData  ���(��Ƭxml)
	 * @return
	 * &lt;JYESBReturn&gt; <br>
	 * &lt;iReturnType&gt;1&lt;/iReturnType&gt;	&lt;!--�μ�ESB����ֵ����--&gt; <br>
	 * &lt;strData&gt; <br>
	 * &lt;!-- ���ص�XML�˽ڵ��£���Ҫ��xml�������ٴν����ýڵ��ֵ--&gt; <br>
	 * &lt;/strData&gt; <br>
	 * &lt;/JYESBReturn&gt;
	 */
	public static String executeClient(String URL, String strHead, String strData) {
		return executeClient(URL, strHead, strData, TIME_OUT_Default);
	}
	/**
	 * ִ�е���C++
	 * 
	 * @param URL
	 * @param strHead
	 * @param strData
	 * @return
	 */
	public static String executeClient(String url, String strHead, String strData, int iTimeOut) {
		String strResult = null;
		HttpURLConnection httpConnection = null;
		try {
			JYWebserviceClient client = new JYWebserviceClient(url);
			JYWebserviceSoap service = client.getJYWebserviceSoap();
			// /////////////////////////////////////////////////
			Client client2 = ((XFireProxy) Proxy.getInvocationHandler(service)).getClient();
			// �������Ӳ���
			HttpClientParams params = new HttpClientParams();
			params.setBooleanParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
//			params.setLongParameter(HttpClientParams.HEAD_BODY_CHECK_TIMEOUT,1000l);
//			// ���ӳ�ʱʱ�䵥λ�Ǻ���
//			params.setLongParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT,1000l);
//			// �ȴ����ݳ�ʱʱ�䵥λ�Ǻ���
			params.setIntParameter(HttpClientParams.SO_TIMEOUT, iTimeOut);//
//			
			client2.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);
			// /////////////////////////////////////////////////////////////////
			ToIngestInvokeHead head = new ToIngestInvokeHead();
			head.setStrXmlHead(strHead);

			ToIngestInvokeData data = new ToIngestInvokeData();
			data.setStrData(strData);
			ToIngestInvokeResponse invokeResult = service.toIngestInvoke(head, data);
			if (invokeResult != null) {
				strResult = invokeResult.getToIngestInvokeResult();
			}
		} catch (Exception e) {
			return strResult;
		}
		return strResult;
	}
}
