package com.yuwang.pinju.core.constant.system;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class RSAKeyPairGenerator {

	public static void main(String[] args) {
		int size = 1024;
		if (args.length > 0) {
			try {
				size = Integer.parseInt(args[0]);
			} catch (Exception e) {
			}
		}
		KeyPairGenerator gen;
		try {
			gen = KeyPairGenerator.getInstance("RSA");
			gen.initialize(size);
			System.out.println(new RSAKey(gen.generateKeyPair(), size));
		} catch (NoSuchAlgorithmException ignore) {
		}
	}

	private static class RSAKey {

		private RSAPublicKey publicKey;
		private RSAPrivateKey privateKey;
		private int size;

		private RSAKey(KeyPair keyPair, int size) {
			this.publicKey = (RSAPublicKey)keyPair.getPublic();
			this.privateKey = (RSAPrivateKey)keyPair.getPrivate();
			this.size = size;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("RSA: keysize: ").append(size);
			builder.append("\npublic key");
			builder.append("\n  modules : ").append(publicKey.getModulus().toString(16));
			builder.append("\n  exponent: ").append(publicKey.getPublicExponent().toString(16));
			builder.append("\n  public key(hex): ").append(Hex.encodeHex(publicKey.getEncoded()));
			builder.append("\n  public key(b64): ").append(new String(Base64.encodeBase64(publicKey.getEncoded())));
			builder.append("\nprivate key");
			builder.append("\n  modules : ").append(privateKey.getModulus().toString(16));
			builder.append("\n  exponent: ").append(privateKey.getPrivateExponent().toString(16));
			builder.append("\n  private key(hex): ").append(Hex.encodeHex(privateKey.getEncoded()));
			builder.append("\n  private key(b64): ").append(new String(Base64.encodeBase64(privateKey.getEncoded())));
			return builder.toString();
		}
	}
}
