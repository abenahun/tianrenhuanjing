package com.tianren.methane.jniutils;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

import android.util.Log;

/**
 * AES encryption and decryption tool.
 * @author 王蒙
 * @creation 2015年3月17日
 * @todo：AES加密
 * 
 */
public class AESTool {

	private byte[] initVector = { 0x32, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31,
			0x38, 0x27, 0x36, 0x35, 0x33, 0x23, 0x32, 0x31 };
	/**
	 * Encrypt the content with a given key using aes algorithm.
	 * @param content
	 * @param key must contain exactly 32 characters，hi-smart-iot-key
	 * @return
	 * @throws Exception 
	 */
	private String TAG = "AESTool";
	public static String AES_KEY = "hi-smart-iot-key";//入参key，统一赋值为hi-smart-iot-key
	public static  AESTool aesToolInstance = null;
	
	private AESTool(){
	}
	public static AESTool getInstance(){
		if(aesToolInstance==null){
			aesToolInstance = new AESTool();
		}
		return aesToolInstance;
	}
	//加密
	public String content(String content, String key) throws Exception {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		}
		String encrypted = null;
		byte[] keyBytes = key.getBytes();
		if (keyBytes.length != 32 && keyBytes.length != 24
				&& keyBytes.length != 16) {
			throw new IllegalArgumentException(
					"Key length must be 128/192/256 bits!");
		}
		byte[] encryptedBytes = null;
		encryptedBytes = encrypt(content.getBytes(), keyBytes, initVector);
		Log.d(TAG,"encryptedBytes  :"+encryptedBytes);
		encrypted = new String(Hex.encode(encryptedBytes));
		Log.d(TAG,"encrypted  :"+encrypted);
		return encrypted;
	}

	/**
	 * Decrypt the content with a given key using aes algorithm.
	 * 
	 * @param content
	 * @param key must contain exactly 32 characters
	 * @return
	 * @throws Exception 
	 */
	//解密
	public String decrypt(String content, String key) throws Exception {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		}
		String decrypted = null;
		byte[] encryptedContent = Hex.decode(content);
		byte[] keyBytes = key.getBytes();
		byte[] decryptedBytes = null;
		if (keyBytes.length != 32 && keyBytes.length != 24
				&& keyBytes.length != 16) {
			throw new IllegalArgumentException(
					"Key length must be 128/192/256 bits!");
		}
		decryptedBytes = decrypt(encryptedContent, keyBytes, initVector);
		decrypted = new String(decryptedBytes);
		return decrypted;
	}

	/**
	 * Encrypt data.
	 * @param plain
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key),
				iv);
		aes.init(true, ivAndKey);
		return cipherData(aes, plain);
	}

	/**
	 * Decrypt data. 
	 * @param cipher
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] cipher, byte[] key, byte[] iv)
			throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key),
				iv);
		aes.init(false, ivAndKey);
		return cipherData(aes, cipher);
	}

	/**
	 * Encrypt or decrypt data.
	 * @param cipher
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data)
			throws Exception {
		int minSize = cipher.getOutputSize(data.length);
		byte[] outBuf = new byte[minSize];
		int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
		int length2 = cipher.doFinal(outBuf, length1);
		int actualLength = length1 + length2;
		byte[] result = new byte[actualLength];
		System.arraycopy(outBuf, 0, result, 0, result.length);
		return result;
	}
}