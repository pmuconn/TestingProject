package com.cert.test;

/**
 * Constants.  
 */ 
public interface CryptUtil { 
  
 // --------------- Algorithms.  --------------------- 
  
 // DES. 
 public static final String ALGORITHM_DES = "DES"; 
  
 // DSA. 
 public static final String ALGORITHM_DSA = "DSA"; 
  
 // RSA. 
 public static final String ALGORITHM_RSA = "RSA"; 
 
 // SHA 
 public static final String ALGORITHM_SHA = "SHA"; 
  
 // MD5 
 public static final String ALGORITHM_MD5 = "MD5";  
  
  
 // --------------- Default key size. ----------------- 
  
 public static final int DEFAULT_DSA_KEYSIZE = 1024; 
 public static final int DEFAULT_RSA_KEYSIZE = 1024;  
  
 // -------------- Cipher mode ----------------------- 
  
 // Electronic code book mode. 
 public final static String MODE_ECB = "ECB";  
  
 // Cipher block chaining mode. 
 public final static String MODE_CBC = "CBC"; 
  
 // Propagating cipher block chaining mode.  
 public final static String MODE_PCBC = "PCBC"; 
  
 // Cipher feedback mode - n bits per operation.  
 public final static String MODE_CFB = "CFB"; 
  
 // Output feedback mode - n bits per operation.  
 public final static String MODE_OFB = "OFB"; 
  
  
 // -------------- Padding scheme --------------------- 
  
 // No padding. 
 public final static String PADDING_NO_PADDING = "NoPadding"; 
  
 // PKCS#5-style padding. 
 public final static String PADDING_PKCS5 = "PKCS5Padding"; 
  
  
 // Misc. 
  
 public final static String DES_ECB_PKCS5Padding = "DES/ECB/PKCS5Padding"; 
  
  
 //public static String getPreferredProvider(); 
  
 //public static void setPreferredProvider(String provider); 
  
  
}