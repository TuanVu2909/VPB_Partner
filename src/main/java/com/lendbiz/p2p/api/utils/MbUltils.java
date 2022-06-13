package com.lendbiz.p2p.api.utils;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.lendbiz.p2p.api.constants.Constants;

public class MbUltils {

    public static String hmacGenerator(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(Constants.HMAC_KEY.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }

    // public static void main(String[] args) throws Exception {
    // String s =
    // "{\"requestID\":\"123456789011\",\"cardNumber\":\"4848030313308715\"}";
    // System.out.println(hmacGenerator(
    // "MIIBITANBgkqhkiG9w0BAQEFAAOCAQ4AMIIBCQKCAQB6YVmCUs3wrl75QXvThV9MNBlEBM1aDmPizdwbatDsmN/ANFU8fs8wz4TaS7nvLOy1xveIHxgAU1v1Q6aN133AIJBBAcyngseGgjyXcrBVpQyH3BWtvMRN756mrtbyrI43NXhUjmmWYlUiI8KFFrGiOkgiF62cVMiZFiW4QSE96sDTxOMoiA8+bu5MB5Jf48CffhbSDAffIW+lqbg+syjrvbTWKvKK0j2U9dI2UVQ4sAgZDYAcFcIYlAQzCatxz3PuC/Kx/WFLG7Ck/bJYm6Qtei3h9WfSv0htBuW39LFLhYQsOZjeuwU7jcR8+iOObt8djcM2X09CXzetrHmiZ6kLAgMBAAE=",
    // s));
    // }
}