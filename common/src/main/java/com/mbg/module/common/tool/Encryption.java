
package com.mbg.module.common.tool;


import com.mbg.module.common.util.LogUtils;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;


public class Encryption {

    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public Encryption(String strKey) {
        Key key;
        try {
            key = getKey(strKey.getBytes());
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
           LogUtils.e(e.toString());
        }
    }

    public String encrypt(String encryptionText) throws Exception {
        return byteArrayToHexStr(encrypt(encryptionText.getBytes()));
    }

    public byte[] encrypt(byte[] byteArray) throws Exception {
        return encryptCipher.doFinal(byteArray);
    }

    public String decrypt(String cipherText) throws Exception {
        return new String(decrypt(hexStrToByteArray(cipherText)));
    }

    public byte[] decrypt(byte[] byteArray) throws Exception {
        return decryptCipher.doFinal(byteArray);
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }

    public static String byteArrayToHexStr(byte[] byteArray) throws Exception {
        int len = byteArray.length;
        StringBuffer sb = new StringBuffer(len * 2);
        for (int i = 0; i < len; i++) {
            int intTmp = byteArray[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStrToByteArray(String hexString) throws Exception {
        byte[] byteArrayIn = hexString.getBytes();
        int iLen = byteArrayIn.length;
        byte[] byteArrayOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(byteArrayIn, i, 2);
            byteArrayOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return byteArrayOut;
    }

    public static String getMD5ForString(String content) {
        StringBuilder md5Buffer = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] tempBytes = digest.digest(content.getBytes());
            int digital;
            for (int i = 0; i < tempBytes.length; i++) {
                digital = tempBytes[i];
                if (digital < 0) {
                    digital += 256;
                }
                if (digital < 16) {
                    md5Buffer.append("0");
                }
                md5Buffer.append(Integer.toHexString(digital));
            }
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e(e.toString());
        }
        return md5Buffer.toString();
    }

}
