package com.cert.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Common utility functions for messsage digest. A message digest takes an arbitrary amount of input data and produces a short,
 * digested version of the data.
 */
public class MessageDigestUtil implements CryptUtil {


	/**
	 * Returns the message digest of the given message with the SHA algorithm.
	 * 
	 * @param mesg
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] messageDigestWithSHA(byte[] mesg) throws NoSuchAlgorithmException {
		return messageDigest(mesg, ALGORITHM_SHA);
	}

	/**
	 * Returns the message digest of the stream with the SHA algorithm. Note that the input stream will be read till the end and it
	 * will NOT be closed in this method.
	 * 
	 * @param in
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static byte[] messageDigestWithSHA(InputStream in) throws NoSuchAlgorithmException, IOException {
		return messageDigest(in, ALGORITHM_SHA);
	}

	/**
	 * Returns the message digest of the specified file with the SHA algorithm.
	 * 
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static byte[] messageDigestWithSHA(File file) throws NoSuchAlgorithmException, IOException {
		FileInputStream fis = new FileInputStream(file);
		try {
			return messageDigestWithSHA(fis);
		}
		finally {
			fis.close();
		}
	}

	/**
	 * Verifies the digest of given message with the specified digest with the SHA algorithm.
	 * 
	 * @param message
	 * @param digest
	 * @return <code>true</code> if verification completes successfully; <code>false</code> otherwise.
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verifyDigestWithSHA(byte[] message, byte[] digest) throws NoSuchAlgorithmException {
		return MessageDigest.isEqual(messageDigestWithSHA(message), digest);
	}

	/**
	 * Verifies the digest of given input stream with the specified digest with the SHA algorithm. Note that the input stream will
	 * be read till the end and it will NOT be closed in this method.
	 * 
	 * @param in
	 * @param digest
	 * @return <code>true</code> if verification completes successfully; <code>false</code> otherwise.
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verifyDigestWithSHA(InputStream in, byte[] digest) throws NoSuchAlgorithmException, IOException {
		return MessageDigest.isEqual(messageDigestWithSHA(in), digest);
	}

	/**
	 * Verifies the digest of given file with the specified digest with the SHA algorithm.
	 * 
	 * @param file
	 * @param digest
	 * @return <code>true</code> if verification completes successfully; <code>false</code> otherwise.
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verifyDigestWithSHA(File file, byte[] digest) throws NoSuchAlgorithmException, IOException {
		return MessageDigest.isEqual(messageDigestWithSHA(file), digest);
	}

	/**
	 * Returns the message digest of the given message with the MD5 algorithm.
	 * 
	 * @param mesg
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] messageDigestWithMD5(byte[] mesg) throws NoSuchAlgorithmException {
		return messageDigest(mesg, ALGORITHM_MD5);
	}

	/**
	 * Returns the message digest of the stream with the MD5 algorithm. Note that the input stream will be read till the end and it
	 * will NOT be closed in this method.
	 * 
	 * @param in
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static byte[] messageDigestWithMD5(InputStream in) throws NoSuchAlgorithmException, IOException {
		return messageDigest(in, ALGORITHM_MD5);
	}

	/**
	 * Compares the two files with their MD5 digests. Return <code>true</code> if their MD5 digests match; <code>false</code>
	 * otherwise.
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static boolean compareFile(File file1, File file2) throws NoSuchAlgorithmException, IOException {
		return verifyDigestWithMD5(file1, messageDigestWithMD5(file2));
	}

	/**
	 * Returns the string representation of the message digest of the specified file with the MD5 algorithm.
	 * 
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String messageDigestWithMD5AsString(File file) throws NoSuchAlgorithmException, IOException {
		byte[] digest = messageDigestWithMD5(file);
		StringBuilder hexString = new StringBuilder();

		for (int i = 0; i < digest.length; i++ ) {
			String plainText = Integer.toHexString(0xFF & digest[i]);

			if (plainText.length() < 2) {
				plainText = "0" + plainText; // leading zero.
			}

			hexString.append(plainText);
		}

		return hexString.toString();
	}

	/**
	 * Returns the message digest of the specified file with the MD5 algorithm.
	 * 
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static byte[] messageDigestWithMD5(File file) throws NoSuchAlgorithmException, IOException {
		FileInputStream fis = new FileInputStream(file);
		try {
			return messageDigestWithMD5(fis);
		}
		finally {
			fis.close();
		}
	}

	/**
	 * Verifies the digest of given message with the specified digest with the MD5 algorithm.
	 * 
	 * @param message
	 * @param digest
	 * @return <code>true</code> if verification completes successfully; <code>false</code> otherwise.
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verifyDigestWithMD5(byte[] message, byte[] digest) throws NoSuchAlgorithmException {
		return MessageDigest.isEqual(messageDigestWithMD5(message), digest);
	}

	/**
	 * Verifies the digest of given input stream with the specified digest with the MD5 algorithm. Note that the input stream will
	 * be read till the end and it will NOT be closed in this method.
	 * 
	 * @param in
	 * @param digest
	 * @return <code>true</code> if verification completes successfully; <code>false</code> otherwise.
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verifyDigestWithMD5(InputStream in, byte[] digest) throws NoSuchAlgorithmException, IOException {
		return MessageDigest.isEqual(messageDigestWithMD5(in), digest);
	}

	/**
	 * Verifies the digest of given file with the specified digest with the MD5 algorithm.
	 * 
	 * @param file
	 * @param digest
	 * @return <code>true</code> if verification completes successfully; <code>false</code> otherwise.
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verifyDigestWithMD5(File file, byte[] digest) throws NoSuchAlgorithmException, IOException {
		return MessageDigest.isEqual(messageDigestWithMD5(file), digest);
	}

	/**
	 * Returns the digest of the given message with the specified algorithm.
	 * 
	 * @param message
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] messageDigest(byte[] message, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		return md.digest(message);
	}

	/**
	 * Returns the digest of the given InputStream with the specified algorithm. Note that the input stream will be read till the
	 * end and it will NOT be closed in this method.
	 * 
	 * @param in
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static byte[] messageDigest(InputStream in, String algorithm) throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance(algorithm);

		DigestInputStream digestInputStream = new DigestInputStream(in, md);
		byte[] buffer = new byte[1024 * 8];
		while (digestInputStream.read(buffer) != -1)
			;

		return md.digest();
	}

}

