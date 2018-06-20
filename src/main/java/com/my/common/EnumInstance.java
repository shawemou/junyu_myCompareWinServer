package com.my.common;

import java.util.HashMap;
import java.util.Map;

public class EnumInstance {
	
	public final static String loginType = "1";//��¼type
	public final static String modifyPwdType = "2";//�����޸�type
	public final static String compareType = "3";//���رȶ�type
	
	/**
	 * ���÷�����
	 */
	public static class EReturn {
		public final static String RT_NotMatch_Format = "-1001"; // �ӿ���α���
		public final static String RT_NotMatch_Format_Json = "-1002"; // �ӿ����JSON��ʽ����ȷ
		public final static String RT_NotMatch_type_null = "-1003"; // �ӿ�����type����
		public final static String RT_NotMatch_Service_null = "-1004"; // �ӿ�����type��Ӧ�ķ��񲻴���
	
		public final static String RT_InError = "-2000";// �������ڲ�����
		public final static String RT_Timeout = "-2001";// ���������ӳ�ʱ
	}
	
	/**
	 * ��¼������
	 */
	public static class EReturnLogin extends EReturn{
		public final static String RT_Success = "0"; //�ɹ�
		public final static String RT_NotMatch_Null_LoginName = "-1101"; //��¼������
		public final static String RT_NotMatch_Null_Password = "-1102"; //�������
		public final static String RT_NotMatch_Format_Login = "-1103"; //��¼�������벻��ȷ
		public final static String RT_NotMatch_Format_Busable = "-1104"; //�˻���ͣ��
		
		public static Map<String,String> map = new HashMap<String,String>();
		static{
			map.put(RT_Success, "�ɹ�");
			map.put(RT_NotMatch_Null_LoginName, "��¼������");
			map.put(RT_NotMatch_Null_Password, "�������");
			map.put(RT_NotMatch_Format_Login, "��¼�������벻��ȷ");
			map.put(RT_NotMatch_Format_Busable, "�˻���ͣ��");
			
			map.put(RT_NotMatch_Format, "�ӿ���α���");
			map.put(RT_NotMatch_Format_Json, "�ӿ����JSON��ʽ����ȷ");
			map.put(RT_NotMatch_type_null, "�ӿ�����type����");
			map.put(RT_NotMatch_Service_null, "�ӿ�����type��Ӧ�ķ��񲻴���");
			map.put(RT_InError, "�������ڲ�����");
			map.put(RT_Timeout, "���������ӳ�ʱ");
		}
	}
	/**
	 * �����޸ķ�����
	 */
	public static class EReturnModifyPwd extends EReturn{
		public final static String RT_Success = "0"; //�ɹ�
		public final static String RT_NotMatch_Null_LoginName = "-1201"; //��¼������
		public final static String RT_NotMatch_Null_Password = "-1202"; //�������
		public final static String RT_NotMatch_Null_NewPassword = "-1203"; //���������
		public final static String RT_NotMatch_Format_Login = "-1204"; //��¼�������벻��ȷ
		public final static String RT_NotMatch_Format_Busable = "-1205"; //�˻���ͣ��
		
		public static Map<String,String> map = new HashMap<String,String>();
		static{
			map.put(RT_Success, "�ɹ�");
			map.put(RT_NotMatch_Null_LoginName, "��¼������");
			map.put(RT_NotMatch_Null_Password, "�������");
			map.put(RT_NotMatch_Null_NewPassword, "���������");
			map.put(RT_NotMatch_Format_Login, "��¼�������벻��ȷ");
			map.put(RT_NotMatch_Format_Busable, "�˻���ͣ��");
			
			map.put(RT_NotMatch_Format, "�ӿ���α���");
			map.put(RT_NotMatch_Format_Json, "�ӿ����JSON��ʽ����ȷ");
			map.put(RT_NotMatch_type_null, "�ӿ�����type����");
			map.put(RT_NotMatch_Service_null, "�ӿ�����type��Ӧ�ķ��񲻴���");
			map.put(RT_InError, "�������ڲ�����");
			map.put(RT_Timeout, "���������ӳ�ʱ");
		}
	}
	/**
	 * ���رȶԷ�����
	 */
	public static class EReturnCompare extends EReturn{
		public final static String RT_Success = "0"; //ϵͳ�ж�Ϊͬһ��
		public final static String RT_Unsure = "1"; //ϵͳ����ȷ���Ƿ�Ϊͬһ��
		public final static String RT_Fail = "2"; //ϵͳ�ж�Ϊ��ͬ��
		
		public final static String RT_NotMatch_Null_LoginName = "-1301"; //��¼������
		public final static String RT_NotMatch_Null_name = "-1302"; //��������
		public final static String RT_NotMatch_Null_ID = "-1303"; //֤���������
		public final static String RT_NotMatch_Null_IDPhoto = "-1304"; //֤�������ձ���
		public final static String RT_NotMatch_Null_UserPhoto = "-1305"; //�ֳ��ձ���
		
		public final static String RT_NotMatch_Format_Login = "-1306"; //��¼������ȷ
		public final static String RT_NotMatch_Format_Busable = "-1310"; //�˻���ͣ��
		
		public final static String RT_NotMatch_Null_ChipPhoto = "-1307"; //оƬ�ո�ʽ����ȷ
		public final static String RT_NotMatch_Format_IDPhoto = "-1308"; //֤�������ո�ʽ����ȷ
		public final static String RT_NotMatch_Format_UserPhoto = "-1309"; //�ֳ��ո�ʽ����ȷ
		
		public final static String RT_Not_Face_ChipPhoto = "-1311"; //оƬ�ռ������ʧ��
		public final static String RT_Not_Face_IDPhoto = "-1312"; //֤�������ռ������ʧ��
		public final static String RT_Not_Face_UserPhoto = "-1313"; //�ֳ��ռ������ʧ��
		
		public final static String RT_More_Face_ChipPhoto = "-1314"; //оƬ�ռ�⵽��������
		public final static String RT_More_Face_IDPhoto  = "-1315"; //֤�������ռ�⵽��������
		public final static String RT_More_Face_UserPhoto = "-1316"; //�ֳ��ռ�⵽��������
		
		public final static String RT_InError_source = "-1321";//��Դ��֤�ӿڵ���ʧ��
		public static Map<String,String> map = new HashMap<String,String>();
		static{
			map.put(RT_Success, "ϵͳ�ж�Ϊͬһ��");
			map.put(RT_Unsure, "ϵͳ����ȷ���Ƿ�Ϊͬһ��");
			map.put(RT_Fail, "ϵͳ�ж�Ϊ��ͬ��");
			
			map.put(RT_NotMatch_Null_LoginName, "��¼������");
			map.put(RT_NotMatch_Null_name, "��������");
			map.put(RT_NotMatch_Null_ID, "֤���������");
			map.put(RT_NotMatch_Null_IDPhoto, "֤�������ձ���");
			map.put(RT_NotMatch_Null_UserPhoto, "�ֳ��ձ���");
			
			map.put(RT_NotMatch_Format_Login, "��¼������ȷ");
			map.put(RT_NotMatch_Format_Busable, "�˻���ͣ��");
			
			map.put(RT_NotMatch_Null_ChipPhoto, "оƬ�ո�ʽ����ȷ");
			map.put(RT_NotMatch_Format_IDPhoto, "֤�������ո�ʽ����ȷ");
			map.put(RT_NotMatch_Format_UserPhoto, "�ֳ��ո�ʽ����ȷ");
			
			map.put(RT_Not_Face_ChipPhoto, "оƬ�ռ������ʧ��");
			map.put(RT_Not_Face_IDPhoto, "֤�������ռ������ʧ��");
			map.put(RT_Not_Face_UserPhoto, "�ֳ��ռ������ʧ��");
			
			map.put(RT_More_Face_ChipPhoto, "оƬ�ռ�⵽��������");
			map.put(RT_More_Face_IDPhoto, "֤�������ռ�⵽��������");
			map.put(RT_More_Face_UserPhoto, "�ֳ��ռ�⵽��������");
			
			map.put(RT_InError_source, "��Դ��֤�ӿڵ���ʧ��");
			
			map.put(RT_NotMatch_Format, "�ӿ���α���");
			map.put(RT_NotMatch_Format_Json, "�ӿ����JSON��ʽ����ȷ");
			map.put(RT_NotMatch_type_null, "�ӿ�����type����");
			map.put(RT_NotMatch_Service_null, "�ӿ�����type��Ӧ�ķ��񲻴���");
			map.put(RT_InError, "�������ڲ�����");
			map.put(RT_Timeout, "���������ӳ�ʱ");
		}
	}
	
	// ���ȶԷ�����
	public final class EReturnJY {
		public final static String RT_Success = "0";// �ɹ�
		public final static String RT_Unsure = "1";// �޷��ж�
		public final static String RT_Fail = "2";// δͨ��,���ƶ�̫��
		public final static String RT_Transfer = "2000";// ESB�ɹ�����Ϣת��

		public final static String RT_Unqualified = "-3";// ��Ƭ�������ϸ�
		// ȥ����ԭ����-4������ʧ��
		public final static String RT_Fail_IDPhoto = "-4";// �������֤��Ƭ���ϸ�(ԭ����-8)
		
		public final static String RT_NotMatch_Format_IDPhoto = "-7";// 1��֤���ս���ʧ��
		public final static String RT_NotMatch_Format_Photo = "-8";// ����ץ���ս���ʧ��
		
		public final static String RT_Fail_Feature_IdPhoto = "-6";// ��ȡ֤��������ֵʧ��
		public final static String RT_Fail_Feature = "-2";// ����ץ������ȡ������ʧ��
		
		public final static String RT_More_Face_IDPhoto = "-9";// 1��֤���ռ�⵽��������
		public final static String RT_More_Face_Photo = "-10";// ����ץ���ռ�⵽��������
	}
	
	//��Դ�ӿڷ�����
	public final class EReturnSource {
		public final static String RT_Success = "0";// �ɹ�
	}
}
