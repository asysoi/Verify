package cci.cert.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

import cci.cert.certificate.CCICertLoader;



public class DesEncrypter {
  Cipher ecipher;
  Cipher dcipher;

  static final Logger LOG = Logger.getLogger(DesEncrypter.class);
  
  public DesEncrypter() throws Exception {
	SecretKey key = generateKey();  
    ecipher = Cipher.getInstance("DES");
    dcipher = Cipher.getInstance("DES");
    ecipher.init(Cipher.ENCRYPT_MODE, key);
    dcipher.init(Cipher.DECRYPT_MODE, key);
  }

  public String encrypt(String str) throws Exception {
    byte[] utf8 = str.getBytes("UTF8");
    byte[] enc = ecipher.doFinal(utf8);
    return org.apache.commons.codec.binary.Base64.encodeBase64String(enc);
  }

  public String decrypt(String str) throws Exception {
    byte[] dec = org.apache.commons.codec.binary.Base64.decodeBase64(str);
    byte[] utf8 = dcipher.doFinal(dec);
    return new String(utf8, "UTF8");
  }
  
  private SecretKey generateKey() {
	  SecretKey key = null;
	  try {
		  DESKeySpec keySpec = new DESKeySpec("Vh%dj&?j".getBytes("UTF8"));
		  SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		  key = keyFactory.generateSecret(keySpec);
	  } catch (Exception ex) {
		  LOG.error("Key generation error. " + ex.getMessage()); 
	  }
      return key;
  }
}