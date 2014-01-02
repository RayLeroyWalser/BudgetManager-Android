package com.jpintado.budgetmanager.library.crypto;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.Security;
import java.security.interfaces.RSAPrivateCrtKey;

import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERObject;
import org.spongycastle.asn1.x509.RSAPublicKeyStructure;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.openssl.PEMReader;

public class RSA {

    private static final String DEBUG_TAG = "[RSA]";
    static PKCS1Encoding engine = new PKCS1Encoding(new RSAEngine());

    public static String encrypt(String publicKey, String data) {
        try {
            publicKey = publicKey.substring(31, publicKey.length()-29);
            InputStream is = new ByteArrayInputStream(Base64.decode(publicKey, Base64.DEFAULT));
            ASN1InputStream asn1is = new ASN1InputStream(is);
            DERObject obj = asn1is.readObject();
            is.close();
            RSAPublicKeyStructure rsa = new RSAPublicKeyStructure((ASN1Sequence)obj);

            RSAKeyParameters rsaPublicKey = new RSAKeyParameters(false, rsa.getModulus(),rsa.getPublicExponent());
            engine.init(true, rsaPublicKey);
            byte[] data_b64 = data.getBytes();
            byte[] encrypted_data_bytes  = engine.processBlock(data_b64, 0, data_b64.length);

            return Base64.encodeToString(encrypted_data_bytes, Base64.DEFAULT);
        } catch(Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return null;
    }

    public static String decrypt(String privateKey, String data) {
        try {
            RSAPrivateCrtKeyParameters rsaPrivateKey = parseRSAKey(privateKey);
            return decrypt(rsaPrivateKey, data);
        } catch(Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return null;
    }

    public static String decrypt(RSAPrivateCrtKeyParameters rsaPrivateKey, String data) {
        try {
            engine.init(false, rsaPrivateKey);
            byte[] data_b64 = Base64.decode(data, Base64.DEFAULT);
            byte[] decrypted_data_bytes = engine.processBlock(data_b64, 0, data_b64.length);

            return new String(decrypted_data_bytes);
        } catch(Exception ex)
        {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return null;
    }

    public static RSAPrivateCrtKeyParameters parseRSAKey(String privateKey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyPair kp = (KeyPair) new PEMReader(new StringReader(privateKey)).readObject();
            RSAPrivateCrtKey k = (RSAPrivateCrtKey) kp.getPrivate();

            return new RSAPrivateCrtKeyParameters(
                    k.getModulus(),
                    k.getPublicExponent(),
                    k.getPrivateExponent(),
                    k.getPrimeP(),
                    k.getPrimeQ(),
                    k.getPrimeExponentP(),
                    k.getPrimeExponentQ(),
                    k.getCrtCoefficient()
            );
        } catch(Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return null;
    }
}