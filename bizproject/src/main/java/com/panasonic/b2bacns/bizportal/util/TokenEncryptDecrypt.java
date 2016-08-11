package com.panasonic.b2bacns.bizportal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class generates a token and encrypt and decrypt it.
 * 
 * @author akansha
 *
 */
@Component
public class TokenEncryptDecrypt {

	public static Cipher dcipher, ecipher;

	private static final Logger logger = Logger
			.getLogger(TokenEncryptDecrypt.class);

	/**
	 * getToken: generate random unique number for token.
	 * 
	 * @return {@link String}
	 */
	public static String getToken() {
		return UUID.randomUUID().toString();
	}

	/**
	 * initialise Cipher values
	 * 
	 * @param str
	 */
	public static void getCipher(String str) {
		// 8-bytes Salt
		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
				(byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };

		// Iteration count
		int iterationCount = 19;

		try {

			// Generate a temporary key. In practice, you would save this key
			// Encrypting with DES Using a Pass Phrase
			KeySpec keySpec = new PBEKeySpec(str.toCharArray(), salt,
					iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("pbewithmd5anddes")
					.generateSecret(keySpec);

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameters to the cipthers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
					iterationCount);

			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (InvalidAlgorithmParameterException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (InvalidKeySpecException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (InvalidKeyException e) {
			logger.error("Exception:" + e.getMessage());
		}
	}

	/**
	 * Encrypt Token by concatenating it with email id
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> encrypt(String email) {
		// Cipher ecipher = null;
		try {
			
			String token = getToken();
			
			Map<String, String> encryptedToken = new HashMap<String, String>();
			
			StringBuilder builder = new StringBuilder();
			
			builder.append(token);
            
            builder.append("&&");
            
            builder.append(email);
            
			getCipher("");

			// Encode the string into bytes using utf-8
			byte[] utf8 = builder.toString().getBytes("UTF8");
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string
			
			String encryptToken = Base64.encodeBase64String(enc);
			
			if(StringUtils.contains(encryptToken, "+")){
				
				encryptToken = StringUtils.replace(encryptToken, "+", "$$");
				
			}
			
			encryptedToken.put("encryptedToken", encryptToken);
			encryptedToken.put("token", token);
			
			return encryptedToken;

		} catch (BadPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:" + e.getMessage());
		}
		return null;
	}

	/**
	 * Encrypt Token by concatenating it with email id
	 * 
	 * @param str
	 * @return
	 */
	public static String encrypt() {
		// Cipher ecipher = null;
		try {

			String token = getToken();

			StringBuilder builder = new StringBuilder();

			builder.append(token);

			getCipher("");

			// Encode the string into bytes using utf-8
			byte[] utf8 = builder.toString().getBytes("UTF8");
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string

			String encryptToken = Base64.encodeBase64String(enc);

			return encryptToken;

		} catch (BadPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:" + e.getMessage());
		}
		return null;
	}

	/**
	 * Generate encrypted token for provided email and token
	 * 
	 * @param email
	 * @param token
	 * @return
	 */
	public static String getEncryptedToken(String email, String token) {
		String encryptToken = "";

		try {
			StringBuilder builder = new StringBuilder();

			builder.append(token);

			builder.append("&&");

			builder.append(email);

			getCipher("");

			// Encode the string into bytes using utf-8
			byte[] utf8 = builder.toString().getBytes("UTF8");
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string

			encryptToken = Base64.encodeBase64String(enc);

			if (StringUtils.contains(encryptToken, "+")) {

				encryptToken = StringUtils.replace(encryptToken, "+", "$$");

			}
		} catch (BadPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:" + e.getMessage());
		}

		return encryptToken;
	}

	/**
	 * Decrypt Token and return original token and email id
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> decrypt(String encrptedToken) {
		// Cipher dcipher = null;
		try {

			if (StringUtils.contains(encrptedToken, "$$")) {

				encrptedToken = StringUtils.replace(encrptedToken, "$$", "+");
			}

			Map<String, String> decryptedToken = new HashMap<String, String>();

			getCipher("");
			// Decode base64 to get bytes
			@SuppressWarnings("restriction")
			byte[] dec = Base64.decodeBase64(encrptedToken);
			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);
			// Decode using utf-8
			String decryptedString = new String(utf8, "UTF8");

			decryptedToken.put("token",
					StringUtils.split(decryptedString, "&&")[0]);
			decryptedToken.put("email",
					StringUtils.split(decryptedString, "&&")[1]);

			return decryptedToken;
		} catch (BadPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (IOException e) {
			logger.error("Exception:" + e.getMessage());
		}
		return null;
	}

}
