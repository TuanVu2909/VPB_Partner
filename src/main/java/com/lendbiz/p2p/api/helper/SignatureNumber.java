package com.lendbiz.p2p.api.helper;

import com.lendbiz.p2p.api.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Slf4j
public class SignatureNumber {
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    public static final String algo = "SHA256withRSA";

    public String sign(String data) throws Exception {
        //is base64 string input
        if (!Base64.isBase64(data)) {
            throw new BusinessException("Data input for signature is not in Base64 string format");
        }
        //sample
        String privateKey = "-----BEGIN RSA PRIVATE KEY-----" +
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDetwVBdnLgS4ZB\n" +
                "4OYQEsROIdls70MoyDYytK69prH+wnbq71teTtk0i2IRNypafw2qgzaFSUHyGMJQ\n" +
                "VdCSXYL+4rkMKi9LZkUayVRinWsdB/GJa4PRxE79y42DO+cSANmto7U1iYp2LkVn\n" +
                "kHMaWymxL+07fZxvWDcZPmWr70aMXQzaLINercIavZxb54uEqrnLmborJz+1uYY8\n" +
                "cuONiLfFYYbayKlEEMDrD6WnoMr70P6Q7xYE5FfWFHzS2gFIdXZZaHyiOLTXWtE6\n" +
                "E4a0czTHM/MgCq6iQM/njs5wMa36jXK/CbSV4mX+VgNqIoYpDlQEIZR0BR6yxHlo\n" +
                "UMMyRJ7ZAgMBAAECggEACp7FVWOFukRftEyDdSQv9MwitPaM/wRIg6FWo9NmjOtz\n" +
                "Txr3b9mM/EmZPmxYKB5MnIfK+lgf9Fd2elrK812H5sDH1+rx6dX2D+zvicaZEP+e\n" +
                "QhtO9nXI2fA7hZTQitqpz856q+7jrkAqCWI7tlMs1u2eJCHPj+H5AzhLpExorfVN\n" +
                "eJCWREDUm4ojmO76747l0xmcIapVmR2HfhCLbrXd36NHAW+dmJXgMzN797WA+bBE\n" +
                "ONvDkhQUmGrgUpLzfdF2NiAYgNJNy89Z+CoWGnGx1rYk+R5MjfjXH0B5bkbwLQmt\n" +
                "eQWtmLzqoztjTtOxQn9+Y1jBfIPLo80StTLjUwtF8QKBgQDyWqGrlPPzQJVlVKgn\n" +
                "fD2PIMUdvNjvLY8tGmaH45eKq3PJoZG7Tg8sE3N/yOKUN/2dhTKpt9YdH/KEeca5\n" +
                "7r3PgEaWX3ihh9xOPmesM9CRrBRWuifIzH+T8gxeQLA9WlyHYLPtmu0LcZ8pADdf\n" +
                "09vPjVH5Ij1PwDGkaxe2FftGfwKBgQDrQU3+Mkcx9sRBExz/I2D6Al0Hiwg1MYvN\n" +
                "WM/l8+NR0SO6T0f0K41uFuwGnupAkUN4o6IgvYUj0+ZeOgGySyDgaR20kVjhbQfM\n" +
                "R39u2C9qkujqrTyKfOPgiG4dJ5CLrviu22KrWiZeDFZzuE/4R7+HLi+vS+r/DcIX\n" +
                "Ua0wYwlepwKBgQCuzKnQ3NV7PLjEZmQGXrjz09n4ke4RzNV43ZLYLlJuBS21X2CC\n" +
                "LVIKI1lp8FVkPGIjTB/3g43eYd2EqWSpVMX4tPe0m9WhBCHlGWOjM5gaiXf9+bSa\n" +
                "Qs09wxYioRu8lMEqL5h3JwgT+fanFu3Zoy/mqnJWc9iu/duQUGHAxmf/NwKBgDcl\n" +
                "2OWk3aGtyjXDCEIbBgm1ch9pjSQDGFWr4xhxwrEUSL7NBP4NE5tiDjRDHoDCTMHE\n" +
                "AiaQptxOOSuPh4RHLk7M9WS1ydMWXwSZIhtW3E6sX6Nbttx0CGtDMCTveGf2PV+o\n" +
                "GAo5GNNlKwL3iu+VyiUjQ5RhRJZDoGMdB29XWAPzAoGBALX2iok2ZkWVKvNhOkkN\n" +
                "eBsbfvcUp8HC5KNfnthjkUuozZGkZ3yLwKFtXpTaQ7QNCWRR0dlxBtfivyFbUIEI\n" +
                "wXPCxc3Gsqc7gtORQenpVVD5Gkebstt+Lk8hQ3zmjCA9Tt8DNAIpUKAL9f/eiXof\n" +
                "HJE0qPyAZmKDmbjyHFk93xxq" +
                "-----END RSA PRIVATE KEY-----";
        PrivateKey priKey = readPrivateKey(privateKey);

        Signature signature = Signature.getInstance(algo, "BC");
        signature.initSign(priKey, new SecureRandom());
        byte[] message = data.getBytes();
        signature.update(message);
        byte[] sigBytes = signature.sign();
        byte[] base64EncodeByte = Base64.encodeBase64(sigBytes);
        String signedData = new String(base64EncodeByte);
        return signedData;

    }

    public Boolean verify(String data, String signedData, String certPath) throws Exception {
        try {
            PublicKey pubKey = getPublicKey(certPath);

            Signature signature = Signature.getInstance(algo, "BC");
            byte[] sig = Base64.decodeBase64(signedData.getBytes());

            byte[] message = data.getBytes();
            signature.initVerify(pubKey);
            signature.update(message);
            Boolean isVerify = signature.verify(sig);
            log.info("========End OK==========");
            return isVerify;
        }
        catch (Exception e) {
            log.error("Error={}", e.getMessage());
            throw e;
        }
    }

    public PublicKey getPublicKey(String certfile) {
        FileInputStream cerFile;
        try {
            cerFile = new FileInputStream(certfile);
            CertificateFactory f = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) f
                    .generateCertificate(cerFile);
            return certificate.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public RSAPrivateKey readPrivateKey(String privateKey) throws Exception {
        String privateKeyPEM = privateKey
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END RSA PRIVATE KEY-----", "");
        //System.out.println("privateKeyPEM\n" + privateKeyPEM);
        byte[] encoded = Base64.decodeBase64(privateKeyPEM.getBytes("UTF-32"));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
