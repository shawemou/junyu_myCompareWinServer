package com.custle.sdk.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import javax.security.cert.CertificateException;

import org.bouncycastle.util.encoders.Base64;

/**
 * ����P12֤��
 * 
 */
public class P12CertificateInfo {

	private X509Certificate cert;
	private PrivateKey prikey;

	/**
	 * @param pkcs12
	 * @param passwd
	 * @throws CertificateException
	 * @throws IOException
	 */
	public P12CertificateInfo(String pkcs12, String passwd)
			throws CertificateException, IOException {
		loadCertificate(pkcs12.getBytes(), passwd);
	}

	private void loadCertificate(byte[] pkcs12, String passwd)
			throws CertificateException, IOException {
		try {
			// base64����pkcs12���ݿ�
			byte[] pksc12 = Base64.decode(pkcs12);
			ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
					pksc12);
			// ȡ���ض����͵�KeyStore
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			// ����keyStore
			keyStore.load(tInputStringStream, passwd.toCharArray());
			// ��ȡ֤���������ʱĬ��p12��ֻ��һ��֤��
			String keyalias = "";
			Enumeration<String> enu = keyStore.aliases();
			if (enu.hasMoreElements()) {
				keyalias = enu.nextElement();
			}
			// ���ݱ���ȡ��֤��
			cert = (X509Certificate) keyStore.getCertificate(keyalias);
			prikey = (PrivateKey) keyStore.getKey(keyalias, passwd
					.toCharArray());
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CertificateException(e.getMessage());
		}
	}

	public String getBase64Cert() {
		String base64Cert = null;
		try {
			base64Cert = new String(Base64.encode((cert.getEncoded())));
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}
		return base64Cert;
	}

	public int getKeyLength() throws InvalidKeySpecException {
		KeyFactory kf;
		RSAPublicKey rsaPubKey;
		try {
			kf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			return 0;
		}
		// ת����RSA��Կ��
		X509EncodedKeySpec encodedPublicKey = new X509EncodedKeySpec(cert
				.getPublicKey().getEncoded());
		rsaPubKey = (RSAPublicKey) kf.generatePublic(encodedPublicKey);
		// �õ���Կģ
		BigInteger m = rsaPubKey.getModulus();
		return m.bitLength();
	}

	public String getBase64PubKey() {
		return new String(Base64.encode((cert.getPublicKey().getEncoded())));
	}

	public PrivateKey getPrikey() {
		return prikey;
	}

	public X509Certificate getCert() {
		return cert;
	}

}
