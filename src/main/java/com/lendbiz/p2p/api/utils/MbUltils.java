package com.lendbiz.p2p.api.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.exception.BusinessException;

public class MbUltils {

    public static String hmacGenerator(String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(Constants.HMAC_KEY.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }

    public static String generateSha256Rsa(String message) {
        File file = null;
        FileInputStream fis = null;
        DataInputStream dis = null;
        byte[] keyBytes = null;
        try {
            // String publicKeyB64 =
            // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArmu62tBvleKni7qqCAXgnR2uI8ZRf76e7P2rWs+hc1ddE4iUoefyev1I9YUFGb4oyom4YN4jcmFedKYZQTF67A/WvfGRkCHjF9I9/QSp7BKl8StcxWrkpAsyn/G37OH1eq9GLKol0eRrIOm/tILAmm8SDVhBwIM51vIg7cNF9ZidWJjMfIDus7xPU1LtlyvugQw0ND4ZD6BcFRyNi3uKiaxv+jn/17xZcuP5YGhbVPnUG3GYSMfsW54Nl6lYUI5f5QTtrq/b0jI8Qoo8MAagLJ+InYVK+ba8B1nT4jDO4Iq2/ZhnEkYoYrxRylEZHs0pLlQg0fQzidtLrIoKnwNFCQIDAQAB";

            // String private64 =
            // "MIIEowIBAAKCAQEArmu62tBvleKni7qqCAXgnR2uI8ZRf76e7P2rWs+hc1ddE4iUoefyev1I9YUFGb4oyom4YN4jcmFedKYZQTF67A/WvfGRkCHjF9I9/QSp7BKl8StcxWrkpAsyn/G37OH1eq9GLKol0eRrIOm/tILAmm8SDVhBwIM51vIg7cNF9ZidWJjMfIDus7xPU1LtlyvugQw0ND4ZD6BcFRyNi3uKiaxv+jn/17xZcuP5YGhbVPnUG3GYSMfsW54Nl6lYUI5f5QTtrq/b0jI8Qoo8MAagLJ+InYVK+ba8B1nT4jDO4Iq2/ZhnEkYoYrxRylEZHs0pLlQg0fQzidtLrIoKnwNFCQIDAQABAoIBAGB2eifMAPp/GLRhbuny/i9Gm+lzI92nedON03fsvtjAZ1yHO9RzKBq2++O1RoXfMkYZdpw0HpyCly+iMPNqPxZRCC2SbTOuWh7xipDlRGioQ5gmqC8SmBLVkpD8qQDcUtwgFYpcK4ByeIECZaCac5S1euFgfzTOHm3vCSEVzxos+4rqGJO130j+r0MRhPG4wDGiTeAfsHrRFZsTGMxaIVw5a4z9bFHGMGE6tMD5ASyPci3doOPMdORtsihGQtPB34x0lApb6sbGgBa6a9R4KdWV5Eh786yghZajvT3k9QzwMw9AegLISZA2IXTk0RvQ1tRNKAoDkHXQQb3EpRkEcRECgYEA6DRa+l5DulNQNSH6r7QMoWtBoKNueijIX2t6T1h92ElAIgq7/DMoP+I0d2YEutSwd/Un86SicB5fVp2xESO4lj0mWLq3M1qRr8vJape6K9nRU9gkEuY4pej7FjB4Sh05MDJN33KD/ksw0xT1zJ5g58lsnB8+Dp46YxwvGVeiK7UCgYEAwEt6r8Wb3RBymTddK/IDaOWMFgnKNpWF3Kp58v7sOW+QOLq3pX+JTKRkD3ecj6ULmkCw0JH/v+k7pS9SNDfBtl8OPy/aI0DfWq6TbXvu+tyxMSxbdif884XQJzKObFqULjTLip3w7va2An9hAXk52yfWX7qajXjT7Y6qGVXyUIUCgYEAl2Wbc+aGLjQn9ZykAG9aVNzDu2MTox54Fo7S/mzqEF0iLY/mJXeNZKmuH7Mqnk4SiQQU+mx7ZLLI4oaKbmxl678PA4cp75e7qW0rdA5dWj34l7wzOQdWJAaVv0/2/guzv9LspyXZHbIUsWvXH4ScFkRkRh6wrYNI+lnj85gJqokCgYA/pzyffqxPiyhEK5199IjIamPqz6bOVsSO/SeNFnGfuZxmCvjLoeITKr+slw2ORzYGA7AD+rBMGKqTzYxKmMJY2jJfHDn7PXgyDmPi9AlxpShKfMJx5F7JRdxXfVbD61/tw7A03H9quuQZA41a7YQHVbKS0bRjbEHK+1S3OkoR+QKBgC+AT4tlCBkqDDM4PygWcN/RNgj9SVKQ86f7cEvMTrptiRmqsMCF71bN5EqlkV4ipIUn0vPh82KyxBud8hraRe9oHKv8IEFS4hhebBqDBh9fgGHtD1Ih0/EJ10oEqI9FwqlnodoF5kztUw25pYuWqEc3UApnzaSwYO8qvw4EWrcL";

            KeyFactory kf = KeyFactory.getInstance("RSA");
            // byte[] decoded = Base64.getDecoder().decode(publicKeyB64);
            // byte[] decoded2 = Base64.getDecoder().decode(private64);

            // PublicKey RSAPublicKey = kf.generatePublic(new X509EncodedKeySpec(decoded));

            // PrivateKey RSAPrivateKey = kf.generatePrivate(new
            // PKCS8EncodedKeySpec(decoded2));
            file = new File("private_key.der");
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            keyBytes = new byte[(int) file.length()];
            dis.readFully(keyBytes);
            dis.close();

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey RSAPrivateKey = kf.generatePrivate(spec);

            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(RSAPrivateKey);
            privateSignature.update(message.getBytes(StandardCharsets.UTF_8));

            byte[] signature = privateSignature.sign();

            // Let's check the signature
            // Signature publicSignature = Signature.getInstance("SHA256withRSA");
            // publicSignature.initVerify(RSAPublicKey);
            // publicSignature.update(message.getBytes(StandardCharsets.UTF_8));
            // boolean isCorrect = publicSignature.verify(signature);

            // System.out.println("Signature correct: " + isCorrect);

            // The public key can be used to encrypt a message, the private key can be used
            // to decrypt it.
            // Encrypt the message
            // Cipher encryptCipher = Cipher.getInstance("RSA");
            // encryptCipher.init(Cipher.ENCRYPT_MODE, RSAPublicKey);

            // byte[] cipherText = encryptCipher.doFinal(message.getBytes());

            // // Now decrypt it
            // Cipher decriptCipher = Cipher.getInstance("RSA");
            // decriptCipher.init(Cipher.DECRYPT_MODE, RSAPrivateKey);

            // String decipheredMessage = new String(decriptCipher.doFinal(cipherText),
            // StandardCharsets.UTF_8);

            // System.out.println(decipheredMessage);

            return Base64.getEncoder().encodeToString(signature);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.SIGN_FAIL, ErrorCode.SIGN_FAIL_DESCRIPTION);
        }

    }

    public static void main(String[] args)
            throws NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeySpecException, IOException {
        generateSha256Rsa("0001604822947CONG TY CO PHAN LENDBIZ VIETNAM0129837294NGUYEN VAN TEST1500000");
    }

}