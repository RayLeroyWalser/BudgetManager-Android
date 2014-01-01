package com.jpintado.budgetmanager.library.crypto;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERObject;
import org.spongycastle.asn1.x509.RSAPublicKeyStructure;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class RSA {

    private static final String DEBUG_TAG = "[RSA]";
    static PKCS1Encoding engine = new PKCS1Encoding(new RSAEngine());

    public static String encrypt(String public_key, String data) {
        try {
            public_key = public_key.substring(31,public_key.length()-29);
            InputStream is = new ByteArrayInputStream(Base64.decode(public_key, Base64.DEFAULT));
            ASN1InputStream asn1is = new ASN1InputStream(is);
            DERObject obj = asn1is.readObject();
            is.close();
            RSAPublicKeyStructure rsa = new RSAPublicKeyStructure((ASN1Sequence)obj);

            RSAKeyParameters publicKey = new RSAKeyParameters(false, rsa.getModulus(),rsa.getPublicExponent());
            engine.init(true, publicKey);
            byte[] data_b64 = data.getBytes();
            byte[] encrypted_data_bytes  = engine.processBlock(data_b64, 0, data_b64.length);

            return Base64.encodeToString(encrypted_data_bytes, Base64.DEFAULT);
        }
        catch(Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return null;
    }
}