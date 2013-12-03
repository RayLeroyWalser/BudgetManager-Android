package com.jpintado.budgetmanager.library.crypto;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCBC {
    private static final String HEX = "0123456789ABCDEF";

    public static String encrypt(String pass, String cleartext) throws Exception
    {
        pass = padto32(pass);
        byte[] rawKey = pass.getBytes("UTF8");
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return Base64.encodeToString(result, Base64.DEFAULT);
    }

    public static String decrypt(String pass, String encrypted) throws Exception
    {
        pass = padto32(pass);
        byte[] rawKey = pass.getBytes("UTF-8");
        byte[] enc = toByte(toHex(Base64.decode(encrypted, Base64.DEFAULT)));
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        for (int i = 0; i < iv.length; i++) iv[i] = 0;
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        for (int i = 0; i < iv.length; i++) iv[i] = 0;
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        return cipher.doFinal(encrypted);
    }

    private static byte[] toByte(String hexString)
    {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    private static String toHex(byte[] buf)
    {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (byte aBuf : buf)
        {
            appendHex(result, aBuf);
        }
        return new String(result);
    }

    private static void appendHex(StringBuffer sb, byte b)
    {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    private static String padto32(String s)
    {
        if (s.length() > 32)
            s = s.substring(0, 32);
        StringBuilder sb = new StringBuilder(s);
        for (int i = s.length(); i < 32; i++)
        {
            sb.append(" ");
        }
        s = sb.toString();
        return s;
    }

}