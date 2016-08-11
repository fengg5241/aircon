package com.panasonic.b2bacns.bizportal.util;

/**
 * 
 * @author kumar.madhukar
 *
 */
public class PasswordSalt {

	public static String getSalt(String password) {
		final byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8,
				(byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };
		StringBuffer result = new StringBuffer();
		for (byte byt : salt)
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(
					1));
		return result.toString().concat(password);
	}

}
