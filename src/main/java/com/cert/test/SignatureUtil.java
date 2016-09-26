package com.cert.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Provides signature utility functions.
 * <p>
 * A signature provides two security services, authentication and integrity. A signature gives you assurance that a message has not
 * been tampered with and that it originated from a certain person. a signature is a message digest that is encrypted with the
 * signer's private key. Only the signer's public key can decrypt the signature, which provides authentication. If the message
 * digest of the message matches the decrypted message digest from the signature, then integrity is also assured.
 * <p>
 * Signatures do not provide confidentiality. A signature accompanies a plaintext message. Anyone can intercept and read the
 * message. Signatures are useful for distributing software and documentation because they foil forgery.
 */
public class SignatureUtil implements CryptUtil {

	/**
	 * Returns the signature of the message using the DSA algorithm.
	 * 
	 * @param privateKey
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static byte[] generateSignatureWithDSA(PrivateKey privateKey, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signature = Signature.getInstance(ALGORITHM_DSA);
		signature.initSign(privateKey);
		signature.update(message);
		return signature.sign();
	}

	/**
	 * Returns the signature of the input stream with specified private key using DSA. Note that the input stream will be read till
	 * the end and it will not be closed in this method.
	 * 
	 * @param privateKey
	 * @param in
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws IOException
	 */
	public static byte[] generateSignatureWithDSA(PrivateKey privateKey, InputStream in) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Signature signature = Signature.getInstance(ALGORITHM_DSA);
		signature.initSign(privateKey);
		byte[] buffer = new byte[1024 * 4];
		int length;
		while ((length = in.read(buffer)) != -1)
			signature.update(buffer, 0, length);

		return signature.sign();
	}

	/**
	 * Returns the signature of a file with the specified private key using DSA.
	 * 
	 * @param privateKey
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws SignatureException
	 */
	public static byte[] generateSignatureWithDSA(PrivateKey privateKey, File file) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
		FileInputStream fileInputStream = new FileInputStream(file);
		try {
			return generateSignatureWithDSA(privateKey, fileInputStream);
		}
		finally {
			fileInputStream.close();
		}
	}

	/**
	 * Verifies the signature of the message with the specified signature using the DSA algorithm.
	 * 
	 * @param publicKey
	 * @param message
	 * @param signature
	 * @return <code>true</code> if verification completes successfully; <code>false</code> otherwise.
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 */
	public static boolean verifySignatureWithDSA(PublicKey publicKey, byte[] message, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signatureObject = Signature.getInstance(ALGORITHM_DSA);
		signatureObject.initVerify(publicKey);

		signatureObject.update(message);
		return signatureObject.verify(signature);
	}

	/**
	 * Verifies the signature of the input stream with the specified signature using the DSA algorithm. The input stream will be
	 * read till the end and it will not be closed in this method.
	 * 
	 * @param publicKey
	 * @param in
	 * @param signature
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws IOException
	 */
	public static boolean verifySignatureWithDSA(PublicKey publicKey, InputStream in, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Signature signatureObject = Signature.getInstance(ALGORITHM_DSA);
		signatureObject.initVerify(publicKey);

		int length;
		byte[] buffer = new byte[1024 * 4];
		while ((length = in.read(buffer)) != -1)
			signatureObject.update(buffer, 0, length);
		return signatureObject.verify(signature);
	}

	/**
	 * Verifies the signature of the given file with the specified signature using DSA.
	 * 
	 * @param publicKey
	 * @param file
	 * @param signature
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws IOException
	 */
	public static boolean verifySignatureWithDSA(PublicKey publicKey, File file, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		try {
			return verifySignatureWithDSA(publicKey, fileInputStream, signature);
		}
		finally {
			fileInputStream.close();
		}
	}

}
