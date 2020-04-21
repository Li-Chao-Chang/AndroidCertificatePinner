package com.example.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class OutputCerKey {

    /**
     * Get peer certificate(Public key to sha256 to base64)
     * @param certFileInputSteam Crt or der or pem file with a valid certificate
     * @return
     */
    public static String GetSHA256(InputStream certFileInputSteam){

        try{
            X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X509")
                    .generateCertificate(certFileInputSteam);
            byte[] publicKeyEncoded = x509Certificate.getPublicKey().getEncoded();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] publicKeySha256 = messageDigest.digest(publicKeyEncoded);
            //Base64.getEncoder() Android26以上
            byte[] publicKeyShaBase64 = Base64.getEncoder().encode(publicKeySha256);
            String Sha256key = "sha256/" + new String(publicKeyShaBase64);
            return Sha256key;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (certFileInputSteam != null) {
                    certFileInputSteam.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
