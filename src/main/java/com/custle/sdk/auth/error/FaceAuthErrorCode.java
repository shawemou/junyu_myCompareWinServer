package com.custle.sdk.auth.error;

public class FaceAuthErrorCode {

	public static final int success = 0;
	public static final String success_desc = "����ʶ��ɹ����û������Ϣ�����ͨ��";

	public static final int not_judged = -1;
	public static final String not_judged_desc = "�޷��ж����û������Ϣ��˽��δ��";

	// -2 δͨ�������ƶ�̫��
	// -3 δͨ������Ƭ�������ϸ�
	// -4 δͨ����������ʧ��
	// -5 δͨ�������ӳ�ʱ�����Ժ�����
	// -6 δͨ����ESB�������ڲ�����
	// -7 δͨ���������������ڲ�����
	// -8 δͨ�����������֤��Ƭ���ϸ�
	// -9 δͨ�����������������ӳ�ʱ
	// -10 δͨ����ESB���������ӳ�ʱ
	// -11 δͨ����SESSION���ӳ�ʱ
	// -12 δͨ����������һ��
	// -13 δͨ���������޴˺�
	// -14 δͨ�����������֤��Ƭ���޷�ʶ��
	// -15 δͨ�����ϴ�����������Ƭʧ�ܣ��޷�ʶ��
	// -16 δͨ������������ʧ�ܣ��޷�ʶ��

	public static final int data_error = -17;
	public static final String data_error_desc = "��ʽ����ȷ";

	public static final int client_type_error = -18;
	public static final String client_type_error_desc = "��Ч�Ŀͻ�������";

	public static final int client_type_not_match = -19;
	public static final String client_type_not_match_desc = "��Ƭ��ͻ������Ͳ�ƥ��";

	public static final int id_null = -20;
	public static final String id_null_desc = "���֤����Ϊ������";

	public static final int id_error = -21;
	public static final String id_error_desc = "���֤�����ʽ����ȷ";
	
	public static final int name_null = -22;
	public static final String name_null_desc = "����Ϊ������";

	public static final int name_error = -23;
	public static final String name_error_desc = "������ʽ����ȷ";
	
	public static final int photo_null = -24;
	public static final String photo_null_desc = "��ƬΪ������";

	public static final int photo_error = -25;
	public static final String photo_error_desc = "��Ƭ��ʽ����ȷ";
	
	//-26	�������ʧ��
	//-27	��⵽��������
	
	public static final int parameter_null = -90;
	public static final String parameter_null_desc = "�ӿڲ���Ϊ��";
	
	public static final int json_error = -91;
	public static final String json_error_desc = "FaceRequest������JSON����";
	
	public static final int app_cert_null = -92;
	public static final String app_cert_null_desc = "����Ӧ�ü�Ȩʧ�ܣ�û���������֤��";
	
	public static final int app_verify_error = -93;
	public static final String app_verify_error_desc = "����Ӧ�ü�Ȩʧ�ܣ���֤ǩ��ʧ��";
	
	public static final int app_auth_error = -94;
	public static final String app_auth_error_desc = "����Ӧ�ü�Ȩʧ�ܣ�û����֤����Ȩ��";
	
	public static final int app_ip_error = -95;
	public static final String app_ip_error_desc = "����Ӧ�ü�Ȩʧ�ܣ�У��IP����������";
	
	public static final int rkk_proxy_error = -96;
	public static final String rkk_proxy_error_desc = "���˿�ǰ�ýӿ�ͨѶ�쳣";
	
	public static final int face_proxy_error = -97;
	public static final String face_proxy_error_desc = "����֤ϵͳ�ӿ�ͨѶ�쳣";
	
	public static final int system_error = -98;
	public static final String system_error_desc = "ͳһ��֤ƽ̨ϵͳ�쳣";

}
