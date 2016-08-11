package com.panasonic.b2bacns.bizportal.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.panasonic.b2bacns.bizportal.util.PasswordSalt;

/**
 * This class is used to encrypt the password using SHA-256 algorithm
 * 
 * @author kumar.madhukar
 *
 */
public class PasswordEncryptionDecryption {

	public static String getEncryptedPassword(String password)
			throws NoSuchAlgorithmException {

		byte[] result = PasswordSalt.getSalt(password).getBytes(
				Charset.forName(BizConstants.CHARSET_ENCODE_FORMAT));

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		for (int i = 0; i < 1000; i++) {
			md.update(result);
			result = md.digest();
		}
		return bytesToHex(result);
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes)
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(
					1));
		return result.toString();
	}

}
