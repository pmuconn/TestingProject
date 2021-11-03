package com.cert.test;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class CertTest {

	public static void main(String[] args) {

		try {
			InputStream is = CertTest.class.getResourceAsStream("rice.keystore");

			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			String password = "r1c3pw";
			keystore.load(is, password.toCharArray());
			
			KeyStoreUtil.printKeyStoreInfo(keystore);
			
			KeyPair kp = KeyStoreUtil.getKey(keystore, "rice", "r1c3pw");
						
			byte[] kpe = kp.getPublic().getEncoded();
			byte[] kpp = kp.getPrivate().getEncoded();
			
		}
		catch (Exception e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		}


	}

}
