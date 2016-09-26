package com.cert.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.swing.JOptionPane;

/**
 * KeyStore Utility. A KeyStore is a handy box that holds keys and certificates.
 * <p>
 * A KeyStore contains two types of entries. The first type contains a private key and a chain of certificates that correspond to
 * the matching public key - <i>private key entry</i>. This is useful for signing and distributing code and other data. The private
 * key is used to sign data; the certificates can be presented as credentials backing up the signature. The second type of keystore
 * entry contains a certificate from someone you trust - <i>trusted certificate entry </i>. This can be used in tandem with the
 * security policy utility, policytool, to define a security policy for a trusted code signer.
 * <p>
 * KeyStore holds all this information, organized by aliases, or short names. Entries are stored and retrieved using an alias,
 * similar to the way a Hashtable or Properties object works.
 */
public class KeyStoreUtil {

	/**
	 * Loads certificate chain from a key store. The chain is sorted in this order: user cert comes first and root cert stays at the
	 * last.
	 * 
	 * @param store
	 * @param alias
	 * @return
	 * @throws KeyStoreException
	 * @throws CertificateException
	 */

	public static Certificate[] getCertificateChain(KeyStore store, String alias) throws KeyStoreException, CertificateException {
		if (store == null || alias == null)
			return null;

		Certificate[] certificates = null;
		X509Certificate[] certificateChain = null;

		certificates = store.getCertificateChain(alias);
		if (certificates == null)
			throw new CertificateException("Certificate chain not found for alias: " + alias);

		certificateChain = new X509Certificate[certificates.length];
		for (int i = 0; i < certificates.length; i++ ) {
			if (certificates[i] instanceof X509Certificate)
				certificateChain[i] = (X509Certificate) certificates[i];
			else
				throw new CertificateException("Non-X.509 certificate found in certificate chain");
		}

		// Sort the chain in the order if necessary: user cert comes first and root cert stays at the end of chain.
		// Not nessary at all. see javadoc for: KeyStore.getCertificateChain.
		// End of storting.

		return certificateChain;
	}

	/**
	 * Loads a private key from a key store.
	 * 
	 * @param store
	 * @param alias
	 * @param password - the password for recovering the key.
	 * @return
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyException
	 */
	public static KeyPair getKey(KeyStore store, String alias, String password) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyException {

		Key key = store.getKey(alias, password.toCharArray());
		if ( !(key instanceof PrivateKey))
			throw new KeyException("The key associate with alias [" + alias + "] is not a private key");

		Certificate certificate = store.getCertificate(alias);

		return new KeyPair(certificate.getPublicKey(), (PrivateKey) key);
	}

	/**
	 * Loads a <code>KeyStore</code> from a url.
	 * 
	 * @param url
	 * @param password password of the keystore.
	 * @param storeType if <code>null</code>, KeyStore.getDefaultType() will be used.
	 * @return @throws KeyStoreException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static KeyStore loadKeyStore(URL url, String password, String storeType) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
		if (url == null)
			return null;

		KeyStore store = KeyStore.getInstance(storeType == null ? KeyStore.getDefaultType() : storeType);

		InputStream is = url.openStream();
		store.load(is, password.toCharArray());
		is.close();

		return store;
	}

	/**
	 * Loads a <code>KeyStore</code> from a file.
	 * 
	 * @param file
	 * @param password password of the keystore.
	 * @param storeType if <code>null</code>, KeyStore.getDefaultType() will be used.
	 * @return @throws KeyStoreException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static KeyStore loadKeyStore(File file, String password, String storeType) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
		return loadKeyStore(new URL("file:" + file.getCanonicalPath()), password, storeType);
	}

	/**
	 * Loads a <code>KeyStore</code> from a file.
	 * 
	 * @param path file path
	 * @param password password of the keystore.
	 * @param storeType if <code>null</code>, KeyStore.getDefaultType() will be used.
	 * @return @throws KeyStoreException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static KeyStore loadKeyStore(String path, String password, String storeType) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
		return loadKeyStore(new File(path), password, storeType);
	}

	/**
	 * Loads a <code>KeyStore</code> from the default location (i.e, the <code>.keystore</code> file under directory
	 * <code>user.home</code>)
	 * 
	 * @param file
	 * @param password password of the keystore.
	 * @param storeType if <code>null</code>, KeyStore.getDefaultType() will be used.
	 * @return @throws KeyStoreException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static KeyStore loadKeyStore(String password, String storeType) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
		return loadKeyStore(System.getProperty("user.home") + File.separator + ".keystore", password, storeType);
	}

	/**
	 * Returns the default keystore file ('.keystore' under 'user.home').
	 * 
	 * @return <code>null</code> if the default keystore does not exist.
	 */
	public static File getDefaultKeyStore() {
		File file = new File(System.getProperty("user.home") + File.separator + ".keystore");
		if (file.exists())
			return file;
		return null;
	}

	/**
	 * Exams the key store.
	 * 
	 * @param keyStore
	 */
	@SuppressWarnings({ "unchecked" })
	public static void printKeyStoreInfo(KeyStore keyStore) {
		PrintStream out = System.out;
		try {
			int sn = 0;
			Enumeration enum2 = keyStore.aliases();
			while (enum2.hasMoreElements()) {
				String alias = (String) enum2.nextElement();
				out.println("Alias #" + sn + ": " + alias);
				out.println("Key Entry? " + keyStore.isKeyEntry(alias));
				out.println("Cert. Entry? " + keyStore.isCertificateEntry(alias));

				try {
					if (keyStore.isKeyEntry(alias)) {
						String password = JOptionPane.showInputDialog(null, "Password for alias " + alias, "");
						KeyPair keyPair = getKey(keyStore, alias, password);
						PrivateKey privateKey = keyPair.getPrivate();
						PublicKey publicKey = keyPair.getPublic();
						out.println("Private key: " + privateKey);
						out.println("Public key: " + publicKey);
					}
					else {
						Certificate[] certs = getCertificateChain(keyStore, alias);
						for (int i = 0; certs != null && i < certs.length; i++ ) {
							out.println("Cert $" + i + ": " + certs[i]);
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				out.println("---------------------------------------");

				sn++ ;
			}


		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


}
