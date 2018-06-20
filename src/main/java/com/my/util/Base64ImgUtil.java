package com.my.util;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

public class Base64ImgUtil {
	public static void main(String[] args) {
		// ���Դ�ͼƬ�ļ�ת��ΪBase64����
		System.out.println(GetImageStr("D:\\dat\\����.jpg"));
//		String strImg = GetImageStr("e://Koala.jpg");
		// ���Դ�Base64����ת��ΪͼƬ�ļ�
//		GenerateImage(strImg, "e://t.jpg");

	}

	/**
	 * ����ͼƬ·����ͼƬ����BASE64����
	 * 
	 * @param imgFilePathͼƬ�ļ�����·��
	 * @return ͼƬ��base64�����ַ���
	 */
	public static String GetImageStr(String imgFilePath) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
		byte[] data = null;
		InputStream in = null;
		// ��ȡͼƬ�ֽ�����
		try {
			in = new FileInputStream(imgFilePath);
			// data = new byte[in.available()];
			// data=new byte[1024];

			int count = 0;
			while (count == 0) {
				count = in.available();
			}
			data = new byte[count];
			in.read(data);

			// in.read(data);
			in.close();
		} catch (IOException e) {
//			e.printStackTrace();
			Log4jUtil.log.error("�����ļ�·����ȡ�ļ�ʧ��");
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// ���ֽ�����Base64����
		return BASE64Encoder.encode(data);// ����Base64��������ֽ������ַ�??
	}

	/**
	 * ��BASE64�ַ������벢������ļ�
	 * 
	 * @param imgStr
	 *            base64������ַ���
	 * @param imgFilePath
	 *            ����ͼƬ���ļ�·��
	 * @return �ɹ���ʧ��
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// ���ֽ������ַ�������Base64���벢����ͼ??
		if (imgStr == null) // ͼ������Ϊ��
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// Base64����
			byte[] bytes = decoder.decodeBuffer(imgStr);

			// for (int i = 0; i < bytes.length; ++i) {
			// if (bytes[i] < 0) {//
			// // �����쳣����
			// bytes[i] += 256;
			// }
			// }

			// ����jpegͼƬ
			out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��BASE64�ַ������벢������ֽ�����
	 * @param imgStr  base64������ַ���
	 * @return ����Base64�������ֽ�����
	 */
	public static byte[] GenerateImageByte(String imgStr) {// ���ֽ������ַ�������Base64���벢����ͼƬ
		if (imgStr == null) // ͼ������Ϊ��
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64����
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0, j = bytes.length; i < j; ++i) {
				if (bytes[i] < 0) {// �����쳣����
					bytes[i] += 256;
				}
			}
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param p_Str
	 *            Base64������ַ���
	 * @return byte[]���͵���Ƭ��Ϣ
	 * @throws IOException
	 */
	public static byte[] fromBase64(String p_Str) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(p_Str);
		} catch (IOException e) {
			Log4jUtil.log.error("Base64������Ƭתbyteʱ�����쳣", e);
		}
		return null;
	}

	/**
	 * @param bytes
	 *            byte[]���͵���Ƭ��Ϣ
	 * @return Java Image���󡣿���ֱ����java�����л��Ƶ�UI����
	 */
	public static Image getImage(byte[] bytes) {
		Image img = null;
		InputStream isPhoto = null;
		try {
			isPhoto = new ByteArrayInputStream(bytes);
			img = ImageIO.read(isPhoto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (isPhoto != null) {
					isPhoto.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return img;
	}
}
