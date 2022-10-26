package com.example.common;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SHA256 {

	public static String getHashedPassword(String password) throws Exception {
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] sha256_result = sha256.digest(password.getBytes());
		return String.format("%040x", new BigInteger(1, sha256_result));
	}

	public static String getHashedValue(String value) throws Exception {
		MessageDigest sha256 = MessageDigest.getInstance("SHA-1");
		byte[] sha256_result = sha256.digest(value.getBytes());
		return String.format("%040x", new BigInteger(1, sha256_result));

	}

}
