package com.lendbiz.p2p.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.google.gson.Gson;
import com.lendbiz.p2p.api.exception.BusinessException;

import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.response.BearResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

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

    public static BearResponse getProductInfo(BearRequest bearRequest) {
        long monthValue = Long.parseLong(bearRequest.getTerm());
        long moneyVal = Long.parseLong(bearRequest.getAmt());
        String rate = "0.0";
        BearResponse bearResponse = new BearResponse();
        bearResponse.setAmt(bearRequest.getAmt());
        bearResponse.setTerm(bearRequest.getTerm());
        bearResponse.setPid(bearRequest.getPid());
        bearResponse.setPayType(bearRequest.getPayType());
        switch (bearRequest.getPid()) {
            case "13":
                if (monthValue == 6) {
                    if (moneyVal < 5000000 && moneyVal >= 1000000) {
                        rate = "5.1";
                    } else if (moneyVal >= 5000000 && moneyVal < 10000000) {
                        rate = "5.6";
                    } else if (moneyVal >= 10000000) {
                        rate = "6.6";
                    } else {
                        rate = "0.0";
                    }
                } else if (monthValue == 12) {
                    if (moneyVal < 5000000 && moneyVal >= 1000000) {
                        rate = "7.7";
                    } else if (moneyVal >= 5000000 && moneyVal < 10000000) {
                        rate = "8.2";
                    } else if (moneyVal >= 10000000) {
                        rate = "8.7";
                    } else {
                        rate = "0.0";
                    }
                }
                break;
            case "14":

                switch (bearRequest.getTerm()) {
                    case "1":
                        rate = "5.5";
                        break;
                    case "3":
                        rate = "6.5";
                        break;
                    case "6":
                        rate = "7.5";
                        if (bearRequest.getPayType().equals("1"))
                            rate = "7.0";
                        break;
                    case "12":
                        rate = "8.5";
                        if (bearRequest.getPayType().equals("1"))
                            rate = "8.0";
                        break;
                    default:
                        rate = "0.0";
                }

                break;
            case "15":
                rate = "4.3";
        }
        LocalDate startDate = LocalDate.parse(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate endDate = LocalDate.parse(java.time.LocalDate.now().plusMonths(monthValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        float rateValue = Float.parseFloat(rate);
        float amtFloat = (float) moneyVal;
        float yearProfit = amtFloat * (rateValue / 100);


        float profitPerDay =  yearProfit / 365;
        float profitPerMonth =  yearProfit / 12;
        float monthlyProfit =  profitPerMonth *monthValue;
        float totalByMonth = moneyVal + monthlyProfit;

        bearResponse.setRate(rate);
        bearResponse.setDay(daysBetween);
        bearResponse.setMonthlyProfit(String.valueOf((long)monthlyProfit));
        bearResponse.setTotal(String.valueOf((long)totalByMonth));
        bearResponse.setStartDate(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        bearResponse.setEndDate(
                java.time.LocalDate.now().plusMonths(monthValue).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        if (bearRequest.getPid().equals("15")){
            bearResponse.setProfitPerDay((long)profitPerDay);
            bearResponse.setMonthlyProfit("");
            bearResponse.setTotal("");
        }
        return bearResponse;
    }
}
