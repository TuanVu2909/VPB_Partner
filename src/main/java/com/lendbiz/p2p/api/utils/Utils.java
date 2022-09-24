package com.lendbiz.p2p.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.google.gson.Gson;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.BankAccountEntity;
import com.lendbiz.p2p.api.entity.CfMast;
import com.lendbiz.p2p.api.exception.BusinessException;

import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.response.BearResponse;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

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

    public static long test() {
        LocalDate start = LocalDate.of(2010, 1, 1);
        LocalDate stop = LocalDate.now(ZoneId.of("America/Montreal"));
        long years = java.time.temporal.ChronoUnit.YEARS.between(start, stop);
        return years;
    }

    public static long getAge(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDate start = localDate;
        ;
        LocalDate stop = LocalDate.now(ZoneId.of("America/Montreal"));
        long years = java.time.temporal.ChronoUnit.YEARS.between(start, stop);
        return years;
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

    public static final Date convertStringToDate3Gang(String value) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(value);
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
        byte[] keyBytes = "D8WkvpZwr3Gj4lGAU3MFFoh1y4b2xx9Q".getBytes(ASCII);
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
            // System.out.println("card_code : " + upToNCharacters);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NO_DATA, e.getMessage());
        }
        return upToNCharacters;
    }

    public static HashMap<String, String> createMessage(HashMap<String, String> map) {
        String date = LocalDate.now().toString();
        HashMap<String, String> rq9Pay = new HashMap<>();
        date = date.replace("-", "");
        String unId = UUID.randomUUID().toString();
        String rqId = "QDKZYOKPER9P" + date + unId;
        ;
        String messageString = "";
        if (map.get("type").equals("1")) {
            messageString = rqId + "|QDKZYOKPER|" + map.get("service_id");
        } else if (map.get("type").equals("2")) {
            messageString = rqId + "|QDKZYOKPER|" + map.get("product_id") + "|" + map.get("qua");
        } else if (map.get("type").equals("3")) {
            messageString = rqId + "|QDKZYOKPER|TRANSACTION|" + map.get("content_id");
        } else if (map.get("type").equals("4")) {
            messageString = rqId + "|QDKZYOKPER|" + map.get("rq_time");

        }

        rq9Pay.put("rqId", rqId);
        rq9Pay.put("mess", messageString);

        return rq9Pay;

    }

    public static String createMessageBalance(String requestId, String requestTime) {
        String date = LocalDate.now().toString();
        date = date.replace("-", "");

        String messageString = "";
        messageString = requestId + "|QDKZYOKPER|" + requestTime;

        return messageString;

    }

    public static java.sql.Date convertStringToSqlDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date parsed;
        try {
            parsed = format.parse(date);
            return new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    public static String[] getSignatureNinePay(HashMap<String, String> map) {
        HashMap<String, String> createMessRqId = createMessage(map);
        String rqId = createMessRqId.get("rqId");
        String messageString = createMessRqId.get("mess");
        // String publicKeyB64 =
        // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6cEUTrHSw9ZHfirrMZ8Lq2SkdGhZuEzxTr5DYi989G/ulDdNGgSHrpIB58JEtAsCSxdme7YgO3C8aZWpuOqWN07Wh+XBK/4imZPlhDt5h9InOHK90m6zVAOE6V4JFtE3k05Nz7p9RMxoizHTjZSQEvj13bK9WeCjFPAppBgvOJZJKrWHkuu2mrF4o9lD4bIoyIZbdjC8ynzarS4GIk+hXIiOs5+rff76bZiVX0hApGmnEtPs3IaD4wOCfBJFiOEKzkX7xgZPrYkn26KivPhO5/4ozRZXZKHM25dXRtQD7OnQBXhaLdVmRN4XnqctrTUxsFazcCLZAvg3CkqbH+2MuQIDAQAB";
        String publicKeyB64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAguQ6CFi+oBnSctFYa25UIz/FcItO+yLR8HYL4Iz+zbn7WKEzELsd13Q6oXi8ksM5K5CJAKUDekcPqcfXelgK2WYlNrQwFKWa297pbWJfJhC7fh4AK5yJRgQ/jupsOdWAt68A5KhCUt4J/WcK/SiJBUpccVeiyU1Cc9q8v2O3EWcpXsgOFVa+84AAyBpLruwTBUhX7RI/PiZ14hFZN5AdGy0vn+J0pFYpMpQ9fx7tnXNagXzCf0jDxx94UobuzeZ21zEylXsBvIpG19BfD+6h2RoGlgMRJG3x4xBarQnCZOW3PTWpENOdXU5d5pDfOOpMf/wN2VviQbot4pJ0tjzSjQIDAQAB";
        // String private64 =
        // "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDpwRROsdLD1kd+KusxnwurZKR0aFm4TPFOvkNiL3z0b+6UN00aBIeukgHnwkS0CwJLF2Z7tiA7cLxplam46pY3TtaH5cEr/iKZk+WEO3mH0ic4cr3SbrNUA4TpXgkW0TeTTk3Pun1EzGiLMdONlJAS+PXdsr1Z4KMU8CmkGC84lkkqtYeS67aasXij2UPhsijIhlt2MLzKfNqtLgYiT6FciI6zn6t9/vptmJVfSECkaacS0+zchoPjA4J8EkWI4QrORfvGBk+tiSfboqK8+E7n/ijNFldkoczbl1dG1APs6dAFeFot1WZE3heepy2tNTGwVrNwItkC+DcKSpsf7Yy5AgMBAAECggEBAI2A9YMiAJ3OxJ6q6o+yYQUTOr7BQLbh1R4+7Vmtt63daIbO51y9q0lRV9ftcVuwYmYEt2Rifyace2JLWF/5mo/GqPXBbuvrvxRLRKCExHAlFtsEEsclfmd4beg7pbLrWgJRX6dFlHmmaA8DTbaiXYkurt/TSO70nqSZMgwFG+jbYSiFrNURUDG0fuCM/qVjs0cwquKJzHGxFIKD54SGkg0EH6Ch2iHFEcNHp+ZlA4oKdiGo9oyr1EiGjQwAgxV8f0li3xIwk5NYvAG032WPyOAZTQAmC+LPGhpS6WLk6VKdjs5720gmsy8kMFNckkPMTZGejzclHfWHaugpeMKw5dECgYEA9bzfyBmkhorNLSrqK6BsG3et09ixXEt26R1E/DMrub07HzAqgm+cLhsi3zIkHg0K89rwqNMP/zu74yAwj9TY8S1saLXNpaOn9M+ruJHRAebucEAGOMF0GmpsXeOCj3PBTW5MSAg9Mpt1X8J+uqyl6nlogY23arsQl6x7x+by9E0CgYEA84QXcO0SXsrz3I5GJ2xEHksv5kaYDWhLEhSGSsm4Xa86DCE7FKewaIwjZIkqFcGkUvsvpUhD4JVf3J4l2ATci6YxFxYVLPPXbyHE5hZuvIPP95J4VnymMj+7thSf/XFCxsu7Y1QwogslfzOHZ2QscGPcMVgl4Dt3x9c/Cfd3YB0CgYEAv7Wx/7HBoRT4LJymQ5LLxEIB4pvTAmX9RrAG+ZoSKr1uOk6hW1EnTvDsq6O6eZdDTCsqRQskF8LKOc8LE6rB9KWzRZ1P6kFa7qp1FXDs1ccLjZblQ7HomhMcp8KuQKvVykqaSDflRm3xi9t4crnuVpaQ6UFeLm3x6+IsTy/lqqECgYEAtQdtpbWYAoA96aia3pPNz/d1FGtGfjEaHcbETrTHKl4pePr7QM+ohRAo/4Q4lRPvZQD22phuXXauXQP0fjKfAfH6bH8uHsznSuZ/yczDZcFXyWRJsYHYy2I12ZZbmb2pNsAd/imIPe6rYXSdJG+D2cli2Av/nEKZOSb65h3h0MECgYAw9hv16CtTC1ygtK7QO7EohJUFtwlPBF98ysZ7hw8zPo5/USD648dPv8+eIvcGjdCB1I2H0FAmSD7WytqlZ22kYVO9yHbKOgCnaFO3RadSCBqL7nMRla55U7PjooFCjF6iQUC8UR4cp+EqZjRRx2kbhu2atqJ5/nCnS8lcejquEA==";
        String private64 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCC5DoIWL6gGdJy0VhrblQjP8Vwi077ItHwdgvgjP7NuftYoTMQux3XdDqheLySwzkrkIkApQN6Rw+px9d6WArZZiU2tDAUpZrb3ultYl8mELt+HgArnIlGBD+O6mw51YC3rwDkqEJS3gn9Zwr9KIkFSlxxV6LJTUJz2ry/Y7cRZyleyA4VVr7zgADIGkuu7BMFSFftEj8+JnXiEVk3kB0bLS+f4nSkVikylD1/Hu2dc1qBfMJ/SMPHH3hShu7N5nbXMTKVewG8ikbX0F8P7qHZGgaWAxEkbfHjEFqtCcJk5bc9NakQ051dTl3mkN846kx//A3ZW+JBui3iknS2PNKNAgMBAAECggEAeaLsByupYpD8kDTZjWSHDgbKTY84Q4uVO3gIC5IbjEVEPZX4JZkt3zYk8j+IRn7jlweHObEfbTeyZ53OMeS1mFgjRxXmBJntMlyR4lPjhjVQd6Abmmuq6gUyp3ZUQt6U8p3QH635jJbJA9MJVD+iLedvxuig359LnOxuMRHSY3l1A61xJ3kbPjFgoIU1LCEpO/y92XQmQwxTXGH/46JqV6wfOlnSkhQ5tyS/hNbcbKlPWT3Ec0oiG0G7aHcL4DQCXjA99d25blr1ka78jOX80hpnfVeq2QvrURTl9OMvg0+6e385oA+8u4GdXWJhfrxERlRsbVWL4nq2n04+JKzIxQKBgQDlUZ6aFJVZe1uhSDpycnd2iKGDMcP0gM4Rojph5mF58Hn758yNabExu5GT0ECl6ISwI7Vcc8atiGL31le/8TMyDBQaAw7j2R0klpyz1CHIgyoKftlcyc0IO2AuNToJbakMNJd+77gf8XMB+tnTIwlAMGn2b3ug/+tQuDnQUjSonwKBgQCSHuX8J+NYIXG5kPl8h1RRtkBEwXweYVjaLVjwPXib5ydOAVRmWVA0stO922F4sxAZbjsEzq2fxTu+nWOpQGn3o5pw31iR1bRqXIWJoc5hU/2FeteAfUlgEbSf/KWXEUJeaKmkv/cUtZIOcY+ZSBTUbaFnzgJ3K3oghmBNlil5UwKBgE+x5eS2cpD+hgGvEGDl1w6ivT7S9L7sZLFMJ+4OGqI+KKabEEO1D5exEA7GuTDvde2g+05+DfYRCBQJscVLFfe2quW/WlVlBvPIQ/1bI8KDkwDCxNeyakcKG4rzhI/yRGWxgVTkCqFPUCDIBRuTeYuNR8YVSGnThvwuiikT6KkRAoGBAIPkNe1jx7aeAXvhGwDHWlmjKuGkz7bQYZYVhojaw4ndnMGxO5Bin53hqHRfl9yNrEN4Fveg4mErc2D7yaBVDxFC8D1frB9+iSnUN4jir1l0qYqZNfm5nMiFUsdyEbmJD6IN0Mpvp8NenOZCpL5TrNGN3znGXr1YVhUTlcuSdf8XAoGAcsgjj/IU5H1ulkrJJHs/vTm2TWB+kejmpNy1wkR8DtxijKAGR2y9UcS8C22m3D6s5EXpNY35y1MVs5weZbH4gEdgNW/uO/M1oLS3FlC8oAShnA2gLvoyC07aVHMn/ASe4LfGOzYxOb41wR5BCdZe5N5PWgUC5+qD4t4SE99pLsc=";
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

            System.out.println("result-rqId = " + rqId);
            System.out.println("result-sig = " + sig);
            return arrays;
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | InvalidKeySpecException ex) {
            throw new BusinessException(ErrorCode.SIGN_FAIL, ErrorCode.SIGN_FAIL_DESCRIPTION);
        }

    }

    public static String getSignatureNinePayBalance(HashMap<String, String> map) {
        String createMessRqId = createMessageBalance(map.get("rqId"), (map.get("rq_time")));
        // String publicKeyB64 =
        // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6cEUTrHSw9ZHfirrMZ8Lq2SkdGhZuEzxTr5DYi989G/ulDdNGgSHrpIB58JEtAsCSxdme7YgO3C8aZWpuOqWN07Wh+XBK/4imZPlhDt5h9InOHK90m6zVAOE6V4JFtE3k05Nz7p9RMxoizHTjZSQEvj13bK9WeCjFPAppBgvOJZJKrWHkuu2mrF4o9lD4bIoyIZbdjC8ynzarS4GIk+hXIiOs5+rff76bZiVX0hApGmnEtPs3IaD4wOCfBJFiOEKzkX7xgZPrYkn26KivPhO5/4ozRZXZKHM25dXRtQD7OnQBXhaLdVmRN4XnqctrTUxsFazcCLZAvg3CkqbH+2MuQIDAQAB";
        String publicKeyB64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAguQ6CFi+oBnSctFYa25UIz/FcItO+yLR8HYL4Iz+zbn7WKEzELsd13Q6oXi8ksM5K5CJAKUDekcPqcfXelgK2WYlNrQwFKWa297pbWJfJhC7fh4AK5yJRgQ/jupsOdWAt68A5KhCUt4J/WcK/SiJBUpccVeiyU1Cc9q8v2O3EWcpXsgOFVa+84AAyBpLruwTBUhX7RI/PiZ14hFZN5AdGy0vn+J0pFYpMpQ9fx7tnXNagXzCf0jDxx94UobuzeZ21zEylXsBvIpG19BfD+6h2RoGlgMRJG3x4xBarQnCZOW3PTWpENOdXU5d5pDfOOpMf/wN2VviQbot4pJ0tjzSjQIDAQAB";
        // String private64 =
        // "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDpwRROsdLD1kd+KusxnwurZKR0aFm4TPFOvkNiL3z0b+6UN00aBIeukgHnwkS0CwJLF2Z7tiA7cLxplam46pY3TtaH5cEr/iKZk+WEO3mH0ic4cr3SbrNUA4TpXgkW0TeTTk3Pun1EzGiLMdONlJAS+PXdsr1Z4KMU8CmkGC84lkkqtYeS67aasXij2UPhsijIhlt2MLzKfNqtLgYiT6FciI6zn6t9/vptmJVfSECkaacS0+zchoPjA4J8EkWI4QrORfvGBk+tiSfboqK8+E7n/ijNFldkoczbl1dG1APs6dAFeFot1WZE3heepy2tNTGwVrNwItkC+DcKSpsf7Yy5AgMBAAECggEBAI2A9YMiAJ3OxJ6q6o+yYQUTOr7BQLbh1R4+7Vmtt63daIbO51y9q0lRV9ftcVuwYmYEt2Rifyace2JLWF/5mo/GqPXBbuvrvxRLRKCExHAlFtsEEsclfmd4beg7pbLrWgJRX6dFlHmmaA8DTbaiXYkurt/TSO70nqSZMgwFG+jbYSiFrNURUDG0fuCM/qVjs0cwquKJzHGxFIKD54SGkg0EH6Ch2iHFEcNHp+ZlA4oKdiGo9oyr1EiGjQwAgxV8f0li3xIwk5NYvAG032WPyOAZTQAmC+LPGhpS6WLk6VKdjs5720gmsy8kMFNckkPMTZGejzclHfWHaugpeMKw5dECgYEA9bzfyBmkhorNLSrqK6BsG3et09ixXEt26R1E/DMrub07HzAqgm+cLhsi3zIkHg0K89rwqNMP/zu74yAwj9TY8S1saLXNpaOn9M+ruJHRAebucEAGOMF0GmpsXeOCj3PBTW5MSAg9Mpt1X8J+uqyl6nlogY23arsQl6x7x+by9E0CgYEA84QXcO0SXsrz3I5GJ2xEHksv5kaYDWhLEhSGSsm4Xa86DCE7FKewaIwjZIkqFcGkUvsvpUhD4JVf3J4l2ATci6YxFxYVLPPXbyHE5hZuvIPP95J4VnymMj+7thSf/XFCxsu7Y1QwogslfzOHZ2QscGPcMVgl4Dt3x9c/Cfd3YB0CgYEAv7Wx/7HBoRT4LJymQ5LLxEIB4pvTAmX9RrAG+ZoSKr1uOk6hW1EnTvDsq6O6eZdDTCsqRQskF8LKOc8LE6rB9KWzRZ1P6kFa7qp1FXDs1ccLjZblQ7HomhMcp8KuQKvVykqaSDflRm3xi9t4crnuVpaQ6UFeLm3x6+IsTy/lqqECgYEAtQdtpbWYAoA96aia3pPNz/d1FGtGfjEaHcbETrTHKl4pePr7QM+ohRAo/4Q4lRPvZQD22phuXXauXQP0fjKfAfH6bH8uHsznSuZ/yczDZcFXyWRJsYHYy2I12ZZbmb2pNsAd/imIPe6rYXSdJG+D2cli2Av/nEKZOSb65h3h0MECgYAw9hv16CtTC1ygtK7QO7EohJUFtwlPBF98ysZ7hw8zPo5/USD648dPv8+eIvcGjdCB1I2H0FAmSD7WytqlZ22kYVO9yHbKOgCnaFO3RadSCBqL7nMRla55U7PjooFCjF6iQUC8UR4cp+EqZjRRx2kbhu2atqJ5/nCnS8lcejquEA==";
        String private64 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCC5DoIWL6gGdJy0VhrblQjP8Vwi077ItHwdgvgjP7NuftYoTMQux3XdDqheLySwzkrkIkApQN6Rw+px9d6WArZZiU2tDAUpZrb3ultYl8mELt+HgArnIlGBD+O6mw51YC3rwDkqEJS3gn9Zwr9KIkFSlxxV6LJTUJz2ry/Y7cRZyleyA4VVr7zgADIGkuu7BMFSFftEj8+JnXiEVk3kB0bLS+f4nSkVikylD1/Hu2dc1qBfMJ/SMPHH3hShu7N5nbXMTKVewG8ikbX0F8P7qHZGgaWAxEkbfHjEFqtCcJk5bc9NakQ051dTl3mkN846kx//A3ZW+JBui3iknS2PNKNAgMBAAECggEAeaLsByupYpD8kDTZjWSHDgbKTY84Q4uVO3gIC5IbjEVEPZX4JZkt3zYk8j+IRn7jlweHObEfbTeyZ53OMeS1mFgjRxXmBJntMlyR4lPjhjVQd6Abmmuq6gUyp3ZUQt6U8p3QH635jJbJA9MJVD+iLedvxuig359LnOxuMRHSY3l1A61xJ3kbPjFgoIU1LCEpO/y92XQmQwxTXGH/46JqV6wfOlnSkhQ5tyS/hNbcbKlPWT3Ec0oiG0G7aHcL4DQCXjA99d25blr1ka78jOX80hpnfVeq2QvrURTl9OMvg0+6e385oA+8u4GdXWJhfrxERlRsbVWL4nq2n04+JKzIxQKBgQDlUZ6aFJVZe1uhSDpycnd2iKGDMcP0gM4Rojph5mF58Hn758yNabExu5GT0ECl6ISwI7Vcc8atiGL31le/8TMyDBQaAw7j2R0klpyz1CHIgyoKftlcyc0IO2AuNToJbakMNJd+77gf8XMB+tnTIwlAMGn2b3ug/+tQuDnQUjSonwKBgQCSHuX8J+NYIXG5kPl8h1RRtkBEwXweYVjaLVjwPXib5ydOAVRmWVA0stO922F4sxAZbjsEzq2fxTu+nWOpQGn3o5pw31iR1bRqXIWJoc5hU/2FeteAfUlgEbSf/KWXEUJeaKmkv/cUtZIOcY+ZSBTUbaFnzgJ3K3oghmBNlil5UwKBgE+x5eS2cpD+hgGvEGDl1w6ivT7S9L7sZLFMJ+4OGqI+KKabEEO1D5exEA7GuTDvde2g+05+DfYRCBQJscVLFfe2quW/WlVlBvPIQ/1bI8KDkwDCxNeyakcKG4rzhI/yRGWxgVTkCqFPUCDIBRuTeYuNR8YVSGnThvwuiikT6KkRAoGBAIPkNe1jx7aeAXvhGwDHWlmjKuGkz7bQYZYVhojaw4ndnMGxO5Bin53hqHRfl9yNrEN4Fveg4mErc2D7yaBVDxFC8D1frB9+iSnUN4jir1l0qYqZNfm5nMiFUsdyEbmJD6IN0Mpvp8NenOZCpL5TrNGN3znGXr1YVhUTlcuSdf8XAoGAcsgjj/IU5H1ulkrJJHs/vTm2TWB+kejmpNy1wkR8DtxijKAGR2y9UcS8C22m3D6s5EXpNY35y1MVs5weZbH4gEdgNW/uO/M1oLS3FlC8oAShnA2gLvoyC07aVHMn/ASe4LfGOzYxOb41wR5BCdZe5N5PWgUC5+qD4t4SE99pLsc=";
        try {
            byte[] decoded = java.util.Base64.getDecoder().decode(publicKeyB64);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey RSAPublicKey = kf.generatePublic(new X509EncodedKeySpec(decoded));
            byte[] decoded2 = java.util.Base64.getDecoder().decode(private64);
            PrivateKey RSAPrivateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(decoded2));
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(RSAPrivateKey, new SecureRandom());
            byte[] message = createMessRqId.getBytes();
            signature.update(message);
            byte[] sigBytes = signature.sign();
            String sig = java.util.Base64.getEncoder().encodeToString(sigBytes);
            Signature signature1 = Signature.getInstance("SHA1withRSA");
            signature1.initVerify(RSAPublicKey);
            signature1.update(message);
            boolean result = signature1.verify(sigBytes);
            System.out.println("result-signature = " + result);

            System.out.println("result-sig = " + sig);
            return sig;
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
        // switch (bearRequest.getPid()) {
        // case "13":
        // if (monthValue == 6) {
        // if (moneyVal < 5000000 && moneyVal >= 1000000) {
        // rate = "5.1";
        // } else if (moneyVal >= 5000000 && moneyVal < 10000000) {
        // rate = "5.6";
        // } else if (moneyVal >= 10000000) {
        // rate = "6.6";
        // } else {
        // rate = "0.0";
        // }
        // } else if (monthValue == 12) {
        // if (moneyVal < 5000000 && moneyVal >= 1000000) {
        // rate = "7.7";
        // } else if (moneyVal >= 5000000 && moneyVal < 10000000) {
        // rate = "8.2";
        // } else if (moneyVal >= 10000000) {
        // rate = "8.7";
        // } else {
        // rate = "0.0";
        // }
        // }
        // break;
        // case "14":
        //
        // switch (bearRequest.getTerm()) {
        // case "1":
        // rate = "5.5";
        // break;
        // case "3":
        // rate = "6.5";
        // break;
        // case "6":
        // rate = "7.5";
        // if (bearRequest.getPayType().equals("1"))
        // rate = "7.0";
        // break;
        // case "12":
        // rate = "8.5";
        // if (bearRequest.getPayType().equals("1"))
        // rate = "8.0";
        // break;
        // default:
        // rate = "0.0";
        // }
        //
        // break;
        // case "15":
        // rate = "4.3";
        // }
        LocalDate startDate = LocalDate
                .parse(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate endDate = LocalDate.parse(
                java.time.LocalDate.now().plusMonths(monthValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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

        // float totalByMonth = moneyVal + monthlyProfit;
        float profit = profitPerDay * daysBetween;
        float total = moneyVal + profit;
        float monthlyProfit = profit / term;
        if (bearRequest.getPayType().equals("1")) {
            bearResponse.setMonthlyProfit(String.valueOf((long) monthlyProfit));
        }

        bearResponse.setProfit(String.valueOf(profit));
        bearResponse.setDay(String.valueOf(daysBetween));
        bearResponse.setTotal(String.valueOf((long) total));
        bearResponse.setEndDate(
                java.time.LocalDate.now().plusMonths(monthValue).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return bearResponse;
    }

    public static void main(String[] args) {

    }

    public static ByteArrayResource convertFile(MultipartFile sourceFile) {
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(sourceFile.getBytes()) {
                @Override
                public String getFilename() {
                    return sourceFile.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_FILE, ErrorCode.FAILED_TO_FILE_DESCRIPTION);
        }

        return resource;
    }

    public static void fillDataToContract(CfMast cfmast, BankAccountEntity bank, String urlInputDocx,
            String urlOutputDocx) throws Docx4JException, IOException, JAXBException {
        String filePath = urlInputDocx;

        Utils docx4j = new Utils();
        WordprocessingMLPackage template = docx4j.getTemplate(filePath);

        Date date = new Date();
        // Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
                // this.put("[Họ tên]", contractId);

                // this.put("${DAY}", String.valueOf(day));
                // this.put("${MONTH}", String.valueOf(month));
                // this.put("${YEAR}", String.valueOf(year));

                // this.put("${FULL_NAME}", cfmast.getFullName().toUpperCase());
                // this.put("${ID}", cfmast.getIdCode());
                // this.put("${IDDATE}", String.valueOf(cfmast.getIdDate()));
                // this.put("${IDPLACE}", cfmast.getIdPlace());
                // this.put("${ADDRESS}", cfmast.getAddress());
                // this.put("${PHONE}", cfmast.getMobileSms());
                // this.put("${EMAIL}", cfmast.getEmail());
                // this.put("${BANKACC}", cfmast.getBankacctNo());
                // this.put("${BANKCODE}", cfmast.getBankCode());
                this.put("${docno}", "3GANG" + UUID.randomUUID().toString());

                this.put("${date}", String.valueOf(day));
                this.put("${month}", String.valueOf(month));
                this.put("${year}", String.valueOf(year));

                this.put("${fullname}", cfmast.getFullName());
                this.put("${idcode}", cfmast.getIdCode());
                this.put("${idplace}", cfmast.getIdPlace());
                this.put("${iddate}", String.valueOf(cfmast.getIdDate()));
                this.put("${phone}", cfmast.getMobileSms());
                this.put("${address}", cfmast.getAddress());
                this.put("${curraddress}", cfmast.getAddress());
                this.put("${email}", cfmast.getEmail());
                this.put("${bankno}", bank.getBankAccount());
                this.put("${bankname}", bank.getBankName());

            }

            @Override
            public String get(Object key) {
                return super.get(key);
            }
        });

        docx4j.writeDocxToStream(template, urlOutputDocx);
    }

    private void writeDocxToStream(WordprocessingMLPackage template, String target)
            throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }

    public static void searchAndReplace(List<Object> texts, Map<String, String> values) {

        // -- scan all expressions
        // Will later contain all the expressions used though not used at the moment
        List<String> els = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        int PASS = 0;
        int PREPARE = 1;
        int READ = 2;
        int mode = PASS;

        // to nullify
        List<int[]> toNullify = new ArrayList<int[]>();
        int[] currentNullifyProps = new int[4];

        for (int i = 0; i < texts.size(); i++) {
            Object text = texts.get(i);
            Text textElement = (Text) text;
            String newVal = "";
            String v = textElement.getValue();
            StringBuilder textSofar = new StringBuilder();
            int extra = 0;
            char[] vchars = v.toCharArray();
            for (int col = 0; col < vchars.length; col++) {
                char c = vchars[col];
                textSofar.append(c);
                switch (c) {
                    case '$': {
                        mode = PREPARE;
                        sb.append(c);
                    }
                        break;
                    case '{': {
                        if (mode == PREPARE) {
                            sb.append(c);
                            mode = READ;
                            currentNullifyProps[0] = i;
                            currentNullifyProps[1] = col + extra - 1;
                        } else {
                            if (mode == READ) {
                                sb = new StringBuilder();
                                mode = PASS;
                            }
                        }
                    }
                        break;
                    case '}': {
                        if (mode == READ) {
                            mode = PASS;
                            sb.append(c);
                            els.add(sb.toString());
                            newVal += textSofar.toString()
                                    + (null == values.get(sb.toString()) ? sb.toString() : values.get(sb.toString()));
                            textSofar = new StringBuilder();
                            currentNullifyProps[2] = i;
                            currentNullifyProps[3] = col + extra;
                            toNullify.add(currentNullifyProps);
                            currentNullifyProps = new int[4];
                            extra += sb.toString().length();
                            sb = new StringBuilder();
                        } else if (mode == PREPARE) {
                            mode = PASS;
                            sb = new StringBuilder();
                        }
                    }
                    default: {
                        if (mode == READ)
                            sb.append(c);
                        else if (mode == PREPARE) {
                            mode = PASS;
                            sb = new StringBuilder();
                        }
                    }
                }
            }
            newVal += textSofar.toString();
            textElement.setValue(newVal);
        }

        if (toNullify.size() > 0)
            for (int i = 0; i < texts.size(); i++) {
                if (toNullify.size() == 0)
                    break;
                currentNullifyProps = toNullify.get(0);
                Object text = texts.get(i);
                Text textElement = (Text) text;
                String v = textElement.getValue();
                StringBuilder nvalSB = new StringBuilder();
                char[] textChars = v.toCharArray();
                for (int j = 0; j < textChars.length; j++) {
                    char c = textChars[j];
                    if (null == currentNullifyProps) {
                        nvalSB.append(c);
                        continue;
                    }
                    int floor = currentNullifyProps[0] * 100000 + currentNullifyProps[1];
                    int ceil = currentNullifyProps[2] * 100000 + currentNullifyProps[3];
                    int head = i * 100000 + j;
                    if (!(head >= floor && head <= ceil)) {
                        nvalSB.append(c);
                    }

                    if (j > currentNullifyProps[3] && i >= currentNullifyProps[2]) {
                        toNullify.remove(0);
                        if (toNullify.size() == 0) {
                            currentNullifyProps = null;
                            continue;
                        }
                        currentNullifyProps = toNullify.get(0);
                    }
                }
                textElement.setValue(nvalSB.toString());
            }
    }

    private WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));
        return template;
    }

    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>) obj).getValue();

        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }

    public static String createOtpId() {
        return UUID.randomUUID().toString();
    }
}
