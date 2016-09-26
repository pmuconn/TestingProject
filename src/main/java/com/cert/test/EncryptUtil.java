package com.cert.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Utility functions to encrypt data. To get RSA cipher, you need download bouncycastle packages. Procedures: 1). Download the
 * correct version of bouncycastle class from www.bouncycastle.org - new release 2). If you are using JDK 1.4 you must use the
 * signed jar for the provider and you must download the unrestricted policy files for the Sun JCE if you want the provider to work
 * properly. The policy files can be found at the same place as the JDK 1.4 download. Further information on this can be found in
 * the Sun documentation on the JCE. 3). Store the downloaded bouncycastle .jar file in C:\j2sdk1.4.2_02\jre\lib\ext 4). Store the
 * downloaded policy files in C:\j2sdk1.4.2_02\jre\lib\security
 */
public class EncryptUtil implements CryptUtil {

	/**
	 * Encrypts the specified message (with offset and length) with the given RSA private key.
	 * 
	 * @param algorithm
	 * @param key
	 * @param params
	 * @param message
	 * @param messageOffset
	 * @param messageLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] encryptWithRSAPrivateKey(RSAPrivateKey key, byte[] message, int messageOffset, int messageLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

		return encrypt(ALGORITHM_RSA, key, null, message, messageOffset, messageLength);
	}

	/**
	 * Encrypts the specified message input stream with the given RSA private key, output the encrypted code to the output stream
	 * specified. The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param key
	 * @param messageInput
	 * @param encryptedOutput
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void encryptWithRSAPrivateKey(RSAPrivateKey key, InputStream messageInput, OutputStream encryptedOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		encrypt(ALGORITHM_RSA, key, null, messageInput, encryptedOutput);
	}

	/**
	 * Encrypts the specified message (with offset and length) with the given RSA public key.
	 * 
	 * @param algorithm
	 * @param key
	 * @param params
	 * @param message
	 * @param messageOffset
	 * @param messageLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] encryptWithRSAPublicKey(RSAPublicKey key, byte[] message, int messageOffset, int messageLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

		return encrypt(ALGORITHM_RSA, key, null, message, messageOffset, messageLength);
	}

	/**
	 * Encrypts the specified message input stream with the given RSA public key, output the encrypted code to the output stream
	 * specified. The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param key
	 * @param messageInput
	 * @param encryptedOutput
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void encryptWithRSAPublicKey(RSAPublicKey key, InputStream messageInput, OutputStream encryptedOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		encrypt(ALGORITHM_RSA, key, null, messageInput, encryptedOutput);
	}

	/**
	 * Encrypts the specified message (with offset and length) with the given DES key.
	 * 
	 * @param key
	 * @param message
	 * @param messageOffset
	 * @param messageLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] encryptWithDES_ECB_PKCS5Padding(Key key, byte[] message, int messageOffset, int messageLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		return encrypt(DES_ECB_PKCS5Padding, key, null, message, messageOffset, messageLength);
	}

	/**
	 * Encrypts the specified message input stream with the given DES key, output the encrypted code to the output stream specified.
	 * The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param key
	 * @param messageInput
	 * @param encryptedOutput
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void encryptWithDES_ECB_PKCS5Padding(Key key, InputStream messageInput, OutputStream encryptedOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		encrypt(DES_ECB_PKCS5Padding, key, null, messageInput, encryptedOutput);

	}

	/**
	 * Encrypts the specified message (with offset and length) with the given algorithm, key and optional params.
	 * 
	 * @param algorithm valid formats: "DES" [algorithm], "DES/ECB/PKCS5Padding" [algorithm/mode/padding]
	 * @param key
	 * @param params algorithm-specific parameters. Set to <code>null</code> if no params are required.
	 * @param message
	 * @return the encrypted message
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] encrypt(String algorithm, Key key, AlgorithmParameterSpec params, byte[] message, int messageOffset, int messageLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(algorithm);
		if (params == null)
			cipher.init(Cipher.ENCRYPT_MODE, key);
		else
			cipher.init(Cipher.ENCRYPT_MODE, key, params);
		return cipher.doFinal(message, messageOffset, messageLength);
	}

	/**
	 * Encrypts the specified message input stream with the given algorithm, key and optional params, output the encrypted code to
	 * the output stream specified. The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param algorithm valid formats: "DES" [algorithm], "DES/ECB/PKCS5Padding" [algorithm/mode/padding]
	 * @param key
	 * @param params algorithm-specific parameters. Set to <code>null</code> if no params are required.
	 * @param messageInput
	 * @param encryptedOutput
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void encrypt(String algorithm, Key key, AlgorithmParameterSpec params, InputStream messageInput, OutputStream encryptedOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(algorithm);

		if (params == null)
			cipher.init(Cipher.ENCRYPT_MODE, key);
		else
			cipher.init(Cipher.ENCRYPT_MODE, key, params);

		CipherOutputStream out = new CipherOutputStream(encryptedOutput, cipher);

		byte[] buffer = new byte[1024 * 8];

		int length;
		while ((length = messageInput.read(buffer)) != -1)
			out.write(buffer, 0, length);

		out.flush();

		// should we close it?
		out.close();
	}
}
