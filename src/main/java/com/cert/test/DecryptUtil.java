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
 * Utility functions to decrypt data.
 */
public class DecryptUtil implements CryptUtil {

	/**
	 * Decrypts an encrypted byte array (with specified offset and length) using the given RSA public key.
	 * 
	 * @param key
	 * @param encryptedBytes
	 * @param encryptedBytesOffset
	 * @param encryptedBytesLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] decryptWithRSAPublicKey(RSAPublicKey key, byte[] encryptedBytes, int encryptedBytesOffset, int encryptedBytesLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		return decrypt(ALGORITHM_RSA, key, null, encryptedBytes, encryptedBytesOffset, encryptedBytesLength);
	}

	/**
	 * Decrypts the specified encrypted message input stream with the given RSA public key, and outputs the plain message the the
	 * specified output stream. The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param key
	 * @param encryptedMessageInput
	 * @param messageOutput
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void decryptWithRSAPublicKey(RSAPublicKey key, InputStream encryptedMessageInput, OutputStream messageOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		decrypt(ALGORITHM_RSA, key, null, encryptedMessageInput, messageOutput);
	}

	/**
	 * Decrypts an encrypted byte array (with specified offset and length) using the given RSA private key.
	 * 
	 * @param key
	 * @param encryptedBytes
	 * @param encryptedBytesOffset
	 * @param encryptedBytesLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] decryptWithRSAPrivateKey(RSAPrivateKey key, byte[] encryptedBytes, int encryptedBytesOffset, int encryptedBytesLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		return decrypt(ALGORITHM_RSA, key, null, encryptedBytes, encryptedBytesOffset, encryptedBytesLength);
	}

	/**
	 * Decrypts the specified encrypted message input stream with the given RSA private key, and outputs the plain message the the
	 * specified output stream. The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param key
	 * @param encryptedMessageInput
	 * @param messageOutput
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void decryptWithRSAPrivateKey(RSAPrivateKey key, InputStream encryptedMessageInput, OutputStream messageOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		decrypt(ALGORITHM_RSA, key, null, encryptedMessageInput, messageOutput);
	}

	/**
	 * Decrypts an encrypted byte array (with specified offset and length) using the given DES key.
	 * 
	 * @param key
	 * @param encryptedBytes
	 * @param encryptedBytesOffset
	 * @param encryptedBytesLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] decryptWithDES_ECB_PKC5Padding(Key key, byte[] encryptedBytes, int encryptedBytesOffset, int encryptedBytesLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		return decrypt(DES_ECB_PKCS5Padding, key, null, encryptedBytes, encryptedBytesOffset, encryptedBytesLength);
	}

	/**
	 * Decrypts the specified encrypted message input stream with the given DES key, and outputs the plain message the the specified
	 * output stream. The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param key
	 * @param encryptedMessageInput
	 * @param messageOutput
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void decryptWithDES_EBC_PKC5Padding(Key key, InputStream encryptedMessageInput, OutputStream messageOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		decrypt(DES_ECB_PKCS5Padding, key, null, encryptedMessageInput, messageOutput);
	}


	/**
	 * Decrypts an encrypted byte array (with specified offset and length) using the given key and optional algorithm param.
	 * 
	 * @param algorithm valid formats: "DES" [algorithm], "DES/ECB/PKCS5Padding" [algorithm/mode/padding]
	 * @param key
	 * @param params algorithm-specific parameters. Set to <code>null</code> if no params are required.
	 * @param encryptedBytes
	 * @param encryptedBytesOffset
	 * @param encryptedBytesLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] decrypt(String algorithm, Key key, AlgorithmParameterSpec params, byte[] encryptedBytes, int encryptedBytesOffset, int encryptedBytesLength)
			throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(algorithm);
		if (params == null)
			cipher.init(Cipher.DECRYPT_MODE, key);
		else
			cipher.init(Cipher.DECRYPT_MODE, key, params);
		return cipher.doFinal(encryptedBytes, encryptedBytesOffset, encryptedBytesLength);
	}


	/**
	 * Decrypts the specified encrypted message input stream with the given key, and outputs the plain message the the specified
	 * output stream. The input stream will be read till the end and it will not be closed in this method.
	 * 
	 * @param algorithm valid formats: "DES" [algorithm], "DES/ECB/PKCS5Padding" [algorithm/mode/padding]
	 * @param key
	 * @param params algorithm-specific parameters. Set to <code>null</code> if no params are required.
	 * @param encryptedMessageInput
	 * @param messageOutput
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void decrypt(String algorithm, Key key, AlgorithmParameterSpec params, InputStream encryptedMessageInput, OutputStream messageOutput)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(algorithm);

		if (params == null)
			cipher.init(Cipher.DECRYPT_MODE, key);
		else
			cipher.init(Cipher.DECRYPT_MODE, key, params);

		CipherOutputStream out = new CipherOutputStream(messageOutput, cipher);

		byte[] buffer = new byte[1024 * 8];

		int length;
		while ((length = encryptedMessageInput.read(buffer)) != -1)
			out.write(buffer, 0, length);

		out.flush();

		// should we close it?
		out.close();
	}

}

