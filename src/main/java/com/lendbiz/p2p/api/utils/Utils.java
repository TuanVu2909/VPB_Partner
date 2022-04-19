package com.lendbiz.p2p.api.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.google.gson.Gson;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.exception.BusinessException;

import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.response.BearResponse;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/***********************************************************************
 *
 * @package：com.lendbiz.p2p.api.utils，@class-name：Utils.java
 *
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:56:36 AM
 *
 ***********************************************************************/
public class Utils {
    public static final Logger logger = LogManager.getLogger(Utils.class);
    public static final String SUB_ID = "lendbiz_";

    public static String generateId(int numberOfCharactor) {
        String alpha = "abcdefghijklmnopqrstuvwxyz0123456789"; // a-z

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, alpha.length() - 1);
            char ch = alpha.charAt(number);
            sb.append(ch);
        }
        return SUB_ID + sb.toString();
    }

    public static int randomNumber(int min, int max) {
        Random generator = new Random();
        return generator.nextInt((max - min) + 1) + min;
    }

    public static boolean isCompareDate(Date refIdDate, String issueDate) {
        try {
            Date issue = new SimpleDateFormat("dd/MM/yyyy").parse(issueDate);
            Date refDate = new SimpleDateFormat("yyyy-MM-dd").parse(refIdDate.toString());

            if (refDate.compareTo(issue) == 0) {
                return true;
            }

            return false;
        } catch (ParseException e) {
            throw new BusinessException("07", "Can not compare date with date of identity");
        }
    }

    public static String decodeCif(String cif) {
        byte[] valueDecoded = Base64.decodeBase64(cif);
        return new String(valueDecoded);
    }

    public static String encodeCif(String cif) {
        byte[] valueEncoded = Base64.encodeBase64(cif.getBytes());
        return new String(valueEncoded);
    }

    /**
     * @param inputStringDate
     * @return
     */
    public static String formatDateString(String inputStringDate) {
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        String reformattedStr = "";

        try {

            reformattedStr = myFormat.format(fromUser.parse(inputStringDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

    public static boolean isCompareDate(Date value) {
        try {
            String issue = new SimpleDateFormat("dd/MM/yyyy").format(value);
            Date sentDate = new SimpleDateFormat("dd/MM/yyyy").parse(issue);
            String ref = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Calendar.getInstance().getTimeInMillis()));
            Date now = new SimpleDateFormat("dd/MM/yyyy").parse(ref);

            if (sentDate.compareTo(now) == 0) {
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static final Long parseLong(String value) {

        try {
            value = value.replace(",", "");
            value = value.replace(".00", "");
            value = value.replace("*", "");
            value = value.replace("<br/>", "");
            value = value.replace("</strong>", "");
            return Long.parseLong(value.trim());
        } catch (Exception e) {
            logger.info("Can not convert amount to number: {}", value);
            return Long.parseLong("-1");
        }
    }

    public static final Date convertStringToDate(String value) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(value);
        } catch (ParseException e) {
            throw new BusinessException("08", "Can not convert date from string");
        }
    }

    public static final Date convertStringToDateTechcombank(String value) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(value);
        } catch (ParseException e) {
            throw new BusinessException("08", "Can not convert date from string");
        }
    }

    public static final String convertDateToString(Date date) {
        try {
            Date dt = new Date(date.getTime());
            return new SimpleDateFormat("dd/MM/yyyy").format(dt);
        } catch (Exception e) {
            throw new BusinessException("08", "Can not convert date to string");
        }
    }

    public static final boolean isNumber(String value) {
        String regex = "[0-9]+";
        return value.matches(regex);
    }

    public static String parseObjectToString(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T parseStringToObject(String json, Class<T> classObject) {
        try {
            return new Gson().fromJson(json, classObject);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getDate());
    }

    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = sdf.format(cal.getTime());
        System.out.println("Current date in String Format: " + strDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        try {
            date = sdf1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = sdf.format(date);

        return dateString;

    }

    private static final Charset ASCII = Charset.forName("US-ASCII");


    public static String decrypt(String base64Cipher) {

        byte[] cipherBytes = java.util.Base64.getDecoder().decode(base64Cipher);
        byte[] iv = "1234123412341234".getBytes(ASCII);
        byte[] keyBytes = "wU6JlogmAFU0MQEF4FXhEISiTauXC9eZ".getBytes(ASCII);
        SecretKey aesKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = null;
        String upToNCharacters = "";
        try {
            cipher = Cipher.getInstance("AES/CBC/NOPADDING");
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParams);
            byte[] result = cipher.doFinal(cipherBytes);
            byte[] bytes = Hex.decodeHex(Hex.encodeHexString(result).toCharArray());
            String resultString = new String(bytes, "UTF-8");
            upToNCharacters = resultString.substring(0, Math.min(resultString.length(), 15));
//            System.out.println("card_code : " + upToNCharacters);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NO_DATA, e.getMessage());
        }
        return upToNCharacters;
    }


    public static HashMap<String, String> crateMessage(HashMap<String, String> map) {
        String date = LocalDate.now().toString();
        HashMap<String, String> rq9Pay = new HashMap<>();
        date = date.replace("-", "");
        String unId = UUID.randomUUID().toString();
        String rqId = "L6WPKJXN4Y9P" + date + unId;
        ;
        String messageString = "";
        if (map.get("type").equals("1")) {
            messageString = rqId + "|L6WPKJXN4Y|" + map.get("service_id");
        } else if (map.get("type").equals("2")) {
            messageString = rqId + "|L6WPKJXN4Y|" + map.get("product_id") + "|" + map.get("qua");
        } else if (map.get("type").equals("3")) {
            messageString = rqId + "|L6WPKJXN4Y|TRANSACTION|" + map.get("content_id");
        } else if (map.get("type").equals("4")) {
            messageString = rqId + "|L6WPKJXN4Y|" + map.get("rq_time");

        }

        rq9Pay.put("rqId", rqId);
        rq9Pay.put("mess", messageString);

        return rq9Pay;

    }

    ;

    public static String[] getSignatureNinePay(HashMap<String, String> map) {
        HashMap<String, String> createMessRqId = crateMessage(map);
        String rqId = createMessRqId.get("rqId");
        String messageString = createMessRqId.get("mess");
        String publicKeyB64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6cEUTrHSw9ZHfirrMZ8Lq2SkdGhZuEzxTr5DYi989G/ulDdNGgSHrpIB58JEtAsCSxdme7YgO3C8aZWpuOqWN07Wh+XBK/4imZPlhDt5h9InOHK90m6zVAOE6V4JFtE3k05Nz7p9RMxoizHTjZSQEvj13bK9WeCjFPAppBgvOJZJKrWHkuu2mrF4o9lD4bIoyIZbdjC8ynzarS4GIk+hXIiOs5+rff76bZiVX0hApGmnEtPs3IaD4wOCfBJFiOEKzkX7xgZPrYkn26KivPhO5/4ozRZXZKHM25dXRtQD7OnQBXhaLdVmRN4XnqctrTUxsFazcCLZAvg3CkqbH+2MuQIDAQAB";

        String private64 = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDpwRROsdLD1kd+KusxnwurZKR0aFm4TPFOvkNiL3z0b+6UN00aBIeukgHnwkS0CwJLF2Z7tiA7cLxplam46pY3TtaH5cEr/iKZk+WEO3mH0ic4cr3SbrNUA4TpXgkW0TeTTk3Pun1EzGiLMdONlJAS+PXdsr1Z4KMU8CmkGC84lkkqtYeS67aasXij2UPhsijIhlt2MLzKfNqtLgYiT6FciI6zn6t9/vptmJVfSECkaacS0+zchoPjA4J8EkWI4QrORfvGBk+tiSfboqK8+E7n/ijNFldkoczbl1dG1APs6dAFeFot1WZE3heepy2tNTGwVrNwItkC+DcKSpsf7Yy5AgMBAAECggEBAI2A9YMiAJ3OxJ6q6o+yYQUTOr7BQLbh1R4+7Vmtt63daIbO51y9q0lRV9ftcVuwYmYEt2Rifyace2JLWF/5mo/GqPXBbuvrvxRLRKCExHAlFtsEEsclfmd4beg7pbLrWgJRX6dFlHmmaA8DTbaiXYkurt/TSO70nqSZMgwFG+jbYSiFrNURUDG0fuCM/qVjs0cwquKJzHGxFIKD54SGkg0EH6Ch2iHFEcNHp+ZlA4oKdiGo9oyr1EiGjQwAgxV8f0li3xIwk5NYvAG032WPyOAZTQAmC+LPGhpS6WLk6VKdjs5720gmsy8kMFNckkPMTZGejzclHfWHaugpeMKw5dECgYEA9bzfyBmkhorNLSrqK6BsG3et09ixXEt26R1E/DMrub07HzAqgm+cLhsi3zIkHg0K89rwqNMP/zu74yAwj9TY8S1saLXNpaOn9M+ruJHRAebucEAGOMF0GmpsXeOCj3PBTW5MSAg9Mpt1X8J+uqyl6nlogY23arsQl6x7x+by9E0CgYEA84QXcO0SXsrz3I5GJ2xEHksv5kaYDWhLEhSGSsm4Xa86DCE7FKewaIwjZIkqFcGkUvsvpUhD4JVf3J4l2ATci6YxFxYVLPPXbyHE5hZuvIPP95J4VnymMj+7thSf/XFCxsu7Y1QwogslfzOHZ2QscGPcMVgl4Dt3x9c/Cfd3YB0CgYEAv7Wx/7HBoRT4LJymQ5LLxEIB4pvTAmX9RrAG+ZoSKr1uOk6hW1EnTvDsq6O6eZdDTCsqRQskF8LKOc8LE6rB9KWzRZ1P6kFa7qp1FXDs1ccLjZblQ7HomhMcp8KuQKvVykqaSDflRm3xi9t4crnuVpaQ6UFeLm3x6+IsTy/lqqECgYEAtQdtpbWYAoA96aia3pPNz/d1FGtGfjEaHcbETrTHKl4pePr7QM+ohRAo/4Q4lRPvZQD22phuXXauXQP0fjKfAfH6bH8uHsznSuZ/yczDZcFXyWRJsYHYy2I12ZZbmb2pNsAd/imIPe6rYXSdJG+D2cli2Av/nEKZOSb65h3h0MECgYAw9hv16CtTC1ygtK7QO7EohJUFtwlPBF98ysZ7hw8zPo5/USD648dPv8+eIvcGjdCB1I2H0FAmSD7WytqlZ22kYVO9yHbKOgCnaFO3RadSCBqL7nMRla55U7PjooFCjF6iQUC8UR4cp+EqZjRRx2kbhu2atqJ5/nCnS8lcejquEA==";
        try {
            byte[] decoded = java.util.Base64.getDecoder().decode(publicKeyB64);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey RSAPublicKey = kf.generatePublic(new X509EncodedKeySpec(decoded));
            byte[] decoded2 = java.util.Base64.getDecoder().decode(private64);
            PrivateKey RSAPrivateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(decoded2));
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(RSAPrivateKey, new SecureRandom());
            byte[] message = messageString.getBytes();
            signature.update(message);
            byte[] sigBytes = signature.sign();
            String sig = java.util.Base64.getEncoder().encodeToString(sigBytes);
            Signature signature1 = Signature.getInstance("SHA1withRSA");
            signature1.initVerify(RSAPublicKey);
            signature1.update(message);
            boolean result = signature1.verify(sigBytes);
            System.out.println("result-signature = " + result);
            String[] arrays = new String[2];
            arrays[0] = rqId;
            arrays[1] = sig;
            return arrays;
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | InvalidKeySpecException ex) {
            throw new BusinessException(ErrorCode.SIGN_FAIL, ErrorCode.SIGN_FAIL_DESCRIPTION);
        }


    }

    public static BearResponse getProductInfo(BearRequest bearRequest) {
        long monthValue = Long.parseLong(bearRequest.getTerm());
        long moneyVal = Long.parseLong(bearRequest.getAmt());
        String rate = bearRequest.getRate();
        BearResponse bearResponse = new BearResponse();
        bearResponse.setAmt(bearRequest.getAmt());
        bearResponse.setTerm(bearRequest.getTerm());
        bearResponse.setPid(bearRequest.getPid());
        bearResponse.setPayType(bearRequest.getPayType());
//        switch (bearRequest.getPid()) {
//            case "13":
//                if (monthValue == 6) {
//                    if (moneyVal < 5000000 && moneyVal >= 1000000) {
//                        rate = "5.1";
//                    } else if (moneyVal >= 5000000 && moneyVal < 10000000) {
//                        rate = "5.6";
//                    } else if (moneyVal >= 10000000) {
//                        rate = "6.6";
//                    } else {
//                        rate = "0.0";
//                    }
//                } else if (monthValue == 12) {
//                    if (moneyVal < 5000000 && moneyVal >= 1000000) {
//                        rate = "7.7";
//                    } else if (moneyVal >= 5000000 && moneyVal < 10000000) {
//                        rate = "8.2";
//                    } else if (moneyVal >= 10000000) {
//                        rate = "8.7";
//                    } else {
//                        rate = "0.0";
//                    }
//                }
//                break;
//            case "14":
//
//                switch (bearRequest.getTerm()) {
//                    case "1":
//                        rate = "5.5";
//                        break;
//                    case "3":
//                        rate = "6.5";
//                        break;
//                    case "6":
//                        rate = "7.5";
//                        if (bearRequest.getPayType().equals("1"))
//                            rate = "7.0";
//                        break;
//                    case "12":
//                        rate = "8.5";
//                        if (bearRequest.getPayType().equals("1"))
//                            rate = "8.0";
//                        break;
//                    default:
//                        rate = "0.0";
//                }
//
//                break;
//            case "15":
//                rate = "4.3";
//        }
        LocalDate startDate = LocalDate.parse(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate endDate = LocalDate.parse(java.time.LocalDate.now().plusMonths(monthValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        float rateValue = Float.parseFloat(rate);
        float amtFloat = (float) moneyVal;
        float yearProfit = amtFloat * (rateValue / 100);
        int term = Integer.parseInt(bearRequest.getTerm());

        int daysInYear = Year.of(2015).length();
        float profitPerDay = yearProfit / daysInYear;
        bearResponse.setProfitPerDay(String.valueOf((long) profitPerDay));
        bearResponse.setStartDate(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        bearResponse.setRate(rate);
        if (bearResponse.getPid().equals("15")) {
            return bearResponse;
        }

        float profitPerMonth = yearProfit / 12;

//        float totalByMonth = moneyVal + monthlyProfit;
        float profit = profitPerDay * daysBetween;
        float total = moneyVal + profit;
        float monthlyProfit = profit / term;
        if (bearRequest.getPayType().equals("1")){
            bearResponse.setMonthlyProfit(String.valueOf((long) monthlyProfit));
        }

        bearResponse.setProfit(String.valueOf(profit));
        bearResponse.setDay(String.valueOf(daysBetween));
        bearResponse.setTotal(String.valueOf((long) total));
        bearResponse.setEndDate(
                java.time.LocalDate.now().plusMonths(monthValue).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return bearResponse;
    }

    public static String createOtpId() {
        return UUID.randomUUID().toString();
    }
}
