package p.jay.aes;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.tomcat.util.codec.binary.Base64;

import flexjson.JSONSerializer;

public class Encrypter {
	
	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	
	public static String encrypt(String keyStr, String secret) throws Exception {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		HashMap<String, String> data = new HashMap<String, String>();
		
		byte[] encodedKey = Base64.decodeBase64(keyStr);
		encodedKey = Arrays.copyOf(encodedKey, 16);
		Key key = new SecretKeySpec(encodedKey, 0, encodedKey.length, AES);

		Cipher cipher = Cipher.getInstance(AES_CBC, new BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		
		byte[] encryptedSecret = cipher.doFinal(secret.getBytes("utf-8"));
		byte[] encryptedBase64 = Base64.encodeBase64(encryptedSecret);
		
		data.put("Encrypted", new String(encryptedBase64));
		data.put("IV", Base64.encodeBase64String(iv));
		
		JSONSerializer serializer = new JSONSerializer();
		
		return serializer.serialize(data);
	}

	public static String decrypt(String keyStr, String iv, String encrypted) throws Exception {
		byte[] encodedKey = Base64.decodeBase64(keyStr);
		encodedKey = Arrays.copyOf(encodedKey, 16);
		Key key = new SecretKeySpec(encodedKey, 0, encodedKey.length, AES);

		Cipher cipher = Cipher.getInstance(AES_CBC, new BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.decodeBase64(iv)));

		byte[] encryptedBytes = Base64.decodeBase64(encrypted.getBytes("utf-8"));
		byte[] clearMsg = cipher.doFinal(encryptedBytes);
		return new String(clearMsg);
	}
}
