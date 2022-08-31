package com.lendbiz.p2p.api.repository;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.*;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.*;
import com.lendbiz.p2p.api.utils.JsonMapper;

import com.lendbiz.p2p.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class PackageFilterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Object getProductField() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API")
                .withProcedureName("GET_CONFIG_PRODUCT")
                // .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));

        Map<String, Object> map = jdbcCall.execute();

        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        return entry.getValue();
    }

    public Object get9PayTrans() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API")
                .withProcedureName("GET_NINE_PAY_TRANS")
                // .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("p_custId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("p_cursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_custId", "012198");

        long start = System.currentTimeMillis();
        Map<String, Object> map = jdbcCall.execute(params);
        System.out.println(System.currentTimeMillis() - start);

        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        return entry.getValue();
    }

    public Object getTransHistory(String customerId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API")
                .withProcedureName("GET_BG_TRANS_HIS")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("p_custId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("p_cursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_custId", customerId);

        Map<String, Object> map = jdbcCall.execute(params);

        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        return entry.getValue();
    }

    public void insertLogs(InsertLogRequest insertLogRequest) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API")
                .withProcedureName("fn_insert_logs").declareParameters(new SqlParameter("p_request_id", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_user_id", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_function_name", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_function_id", Types.INTEGER))
                .declareParameters(new SqlParameter("p_from_ip", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_request_type", Types.INTEGER))
                .declareParameters(new SqlParameter("p_response_status", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_request_body", Types.CLOB))
                .declareParameters(new SqlParameter("p_response_body", Types.CLOB));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_request_id", insertLogRequest.getRequestId());
        params.addValue("p_user_id", insertLogRequest.getUserId());
        params.addValue("p_function_name", insertLogRequest.getFunctionName());
        params.addValue("p_function_id", insertLogRequest.getFunctionId());
        params.addValue("p_from_ip", insertLogRequest.getFromIp());
        params.addValue("p_request_type", insertLogRequest.getRequestType());
        params.addValue("p_response_status", insertLogRequest.getResponseStatus());
        params.addValue("p_request_body", insertLogRequest.getRequestBody());
        params.addValue("p_response_body", insertLogRequest.getResponseBody());

        jdbcCall.execute(params);

    }

    public Object login(String username, String password, String deviceId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API_AUTHENTICATION")
                .withProcedureName("LOGIN")
                // .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("pv_Username", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_Password", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_Deviceid", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_Username", username);
        params.addValue("pv_Password", password);
        params.addValue("pv_Deviceid", deviceId);

        Long start = System.currentTimeMillis();
        Map<String, Object> map = jdbcCall.execute(params);
        // jdbcCall.execute(params);
        System.out.println(System.currentTimeMillis() - start);
        // Long end = System.currentTimeMillis() - start;

        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        // String body = JsonMapper.writeValueAsString(entry.getValue());

        // ObjectMapper mapper = new ObjectMapper();
        // JsonNode root;
        // try {
        // root = mapper.readTree(body);
        // if (root.get(0) != null && root.get(0).get("ERRORCODE") != null) {
        // if (root.get(0).get("ERRORCODE").asInt() == 99)
        // throw new BusinessException(Constants.FAIL,
        // root.get(0).get("STATUS").textValue());
        // }
        // } catch (JsonProcessingException e) {
        // throw new BusinessException(ErrorCode.FAILED_TO_JSON,
        // ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        // }

        return entry.getValue();
    }

    // public Object reqJoin(ReqJoinRequest reqJoinRequest) {

    // SimpleJdbcCall jdbcCall = new
    // SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API_AUTHENTICATION")
    // .withProcedureName("REQJOIN").declareParameters(new SqlParameter("pv_Type",
    // Types.VARCHAR))
    // .declareParameters(new SqlParameter("pv_Fullname", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Sex", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_DOB", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Place", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_IDCode", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_IDDate", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_IDPlace", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_OrgAddress", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Address", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_IDWard", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_IDDistrict", Types.INTEGER))
    // .declareParameters(new SqlParameter("Pv_Phone", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Mobile", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Email", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_BankAccount", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Bank", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Address", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_TaxNo", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Online", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_ProductInfo", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_ConsultalInfo", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Position", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_Job", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_exFullname", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_exPosition", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_exJob", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_exMobile", Types.VARCHAR))
    // .declareParameters(new SqlParameter("Pv_ExEmail", Types.VARCHAR))
    // .declareParameters(new SqlParameter("pv_ExAddress", Types.VARCHAR))
    // .declareParameters(new SqlParameter("pv_ExBank", Types.VARCHAR))
    // .declareParameters(new SqlParameter("pv_ExSTK", Types.VARCHAR))
    // .declareParameters(new SqlParameter("pv_password", Types.VARCHAR))
    // .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));

    // MapSqlParameterSource params = new MapSqlParameterSource();
    // params.addValue("pv_Type", reqJoinRequest.getType());
    // params.addValue("pv_Fullname", reqJoinRequest.getFullName());
    // params.addValue("Pv_Sex", reqJoinRequest.getSex());
    // params.addValue("Pv_DOB", reqJoinRequest.getDob());
    // params.addValue("Pv_Place", reqJoinRequest.getPlace());
    // params.addValue("Pv_IDCode", reqJoinRequest.getIdCode());
    // params.addValue("Pv_IDDate", reqJoinRequest.getIdDate());
    // params.addValue("Pv_IDPlace", reqJoinRequest.getIdPlace());
    // params.addValue("Pv_OrgAddress", reqJoinRequest.getOrgAddress());
    // params.addValue("Pv_Address", reqJoinRequest.getAddress());
    // params.addValue("Pv_IDWard", reqJoinRequest.getIdWard());
    // params.addValue("Pv_IDDistrict", reqJoinRequest.getIdDistrict());
    // params.addValue("Pv_Phone", reqJoinRequest.getPhone());
    // params.addValue("Pv_Mobile", reqJoinRequest.getMobile());
    // params.addValue("Pv_Email", reqJoinRequest.getEmail());
    // params.addValue("Pv_BankAccount", reqJoinRequest.getBankAccount());
    // params.addValue("Pv_Bank", reqJoinRequest.getBank());
    // params.addValue("Pv_TaxNo", reqJoinRequest.getTaxNo());
    // params.addValue("Pv_Online", reqJoinRequest.getOnline());
    // params.addValue("Pv_MatchOrdSMS", reqJoinRequest.getMatchOrdSms());
    // params.addValue("Pv_ProductInfo", reqJoinRequest.getProductInfo());
    // params.addValue("Pv_ConsultalInfo", reqJoinRequest.getConsultalInfo());
    // params.addValue("Pv_OfficeName", reqJoinRequest.getOfficeName());
    // params.addValue("Pv_Position", reqJoinRequest.getPosition());
    // params.addValue("Pv_Job", reqJoinRequest.getJob());
    // params.addValue("Pv_exFullname", reqJoinRequest.getExFullName());
    // params.addValue("Pv_exPosition", reqJoinRequest.getExPosition());
    // params.addValue("Pv_exJob", reqJoinRequest.getExJob());
    // params.addValue("Pv_exMobile", reqJoinRequest.getExMobile());
    // params.addValue("Pv_ExEmail", reqJoinRequest.getExEmail());
    // params.addValue("pv_ExAddress", reqJoinRequest.getExAddress());
    // params.addValue("pv_ExBank", reqJoinRequest.getExBank());
    // params.addValue("pv_ExSTK", reqJoinRequest.getExStk());
    // params.addValue("pv_password", reqJoinRequest.getPassword());

    // Map<String, Object> map = jdbcCall.execute(params);
    // Map.Entry<String, Object> entry = map.entrySet().iterator().next();

    // String body = JsonMapper.writeValueAsString(entry.getValue());

    // ObjectMapper mapper = new ObjectMapper();
    // JsonNode root;
    // try {
    // root = mapper.readTree(body);
    // if (root.get(0) != null && root.get(0).get(":B1") != null &&
    // root.get(0).get(":B2") != null) {
    // // responseId = root.get(0).get(":B1").textValue();
    // } else {
    // throw new BusinessException(Constants.FAIL,
    // root.get(0).get("ERRMSG").textValue());
    // }

    // } catch (JsonProcessingException e) {
    // throw new BusinessException(ErrorCode.FAILED_TO_JSON,
    // ErrorCode.FAILED_TO_JSON_DESCRIPTION);
    // }

    // return entry.getValue();
    // }

    public Object reqJoin(ReqJoinRequest reqJoinRequest) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API_AUTHENTICATION")
                .withProcedureName("REQJOIN_V2")
                .declareParameters(new SqlParameter("Pv_Mobile", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("Pv_Mobile", reqJoinRequest.getMobile());

        Map<String, Object> map = jdbcCall.execute(params);
        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        String body = JsonMapper.writeValueAsString(entry.getValue());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(body);
            if (root.get(0) != null && root.get(0).get(":B1") != null && root.get(0).get(":B2") != null) {
                // responseId = root.get(0).get(":B1").textValue();
            } else {
                throw new BusinessException(Constants.FAIL,
                        root.get(0).get("ERRMSG").textValue());
            }

        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON,
                    ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        }

        return entry.getValue();
    }

    public String verifyAcc(VerifyAccountInput verifyAccountInput) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API_AUTHENTICATION")
                .withProcedureName("VERIFY_ACCOUNT").declareParameters(new SqlParameter("pv_custid", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_verifycode", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));
        ;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custid", verifyAccountInput.getCustId());
        params.addValue("pv_verifycode", verifyAccountInput.getVerifyCode());
        jdbcCall.execute(params);
        return "success";
    }

    public Object setFirstPassword(SetAccountPasswordRequest setAccountPasswordRequest) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API_AUTHENTICATION")
                .withProcedureName("FIRST_PASSWORD").declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_Password", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));
        ;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custid", setAccountPasswordRequest.getCustId());
        params.addValue("pv_Password", setAccountPasswordRequest.getPassword());
        Map<String, Object> map = jdbcCall.execute(params);
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("PV_REFCURSOR");
        if (arrayList.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return arrayList.get(0);
    }

    public Object getAccountAsset(String custId) {
        Map.Entry<String, Object> entry;
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getAccountAsset").declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", custId);
        Map<String, Object> map = jdbcCall.execute(params);
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return arrayList.get(0);
    }

    public Object getAccountInvest(String custId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getAccountInvest").declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", custId);
        Map<String, Object> map = jdbcCall.execute(params);
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return arrayList;
    }

    public Object getProduct() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getproduct")
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));
        System.out.println("End: " + (System.currentTimeMillis()));
        Map<String, Object> map = jdbcCall.execute();
        System.out.println("End: " + (System.currentTimeMillis()));
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return arrayList;
    }

    public Object getCoin(String cif) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getCoin")
                .declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", cif);
        Map<String, Object> map = jdbcCall.execute(params);
        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        String body = JsonMapper.writeValueAsString(entry.getValue());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(body);
            if (root.get(0).get("COINAMOUNT").asText().equals("0")) {
                throw new BusinessException(ErrorCode.NO_DATA,
                        ErrorCode.NO_DATA_DESCRIPTION);
            }

        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON,
                    ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        }
        return root.get(0);
    }

    public Object changeCoin(AccountInput input) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("changeCoin")
                .declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_coinAmount", Types.NUMERIC))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", input.getCustId());
        params.addValue("pv_coinAmount", input.getCoin());
        Map<String, Object> map = jdbcCall.execute(params);
        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        String body = JsonMapper.writeValueAsString(entry.getValue());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(body);
            if (root.get(0).get(":B2") != null) {
                if (root.get(0).get(":B2").asText().equals("0")) {
                    throw new BusinessException("22",
                            root.get(0).get(":B1").asText());
                }
            }
            if (root.get(0).get("0") != null) {
                if (root.get(0).get("0").asText().equals("0") && root.get(0).get("0") != null) {
                    throw new BusinessException("23",
                            root.get(0).get("CÓLỖIXẢYRA.VUILÒNGLIÊNHỆBỘPHẬNHỖTRỢ").asText());
                }
            }

        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON,
                    ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        }
        return root.get(0);
    }

    public Object updateReferenceId(AccountInput accountInput) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("updateReferenceId")
                .declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_refId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", accountInput.getCustId());
        params.addValue("pv_refId", accountInput.getRefId());
        Map<String, Object> map = jdbcCall.execute(params);
        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        String body = JsonMapper.writeValueAsString(entry.getValue());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {

            root = mapper.readTree(body);
            if (root.get(0).get(":B2") != null) {
                if (root.get(0).get(":B2").asText().equals("0")) {
                    throw new BusinessException(ErrorCode.NO_refId,
                            ErrorCode.NO_refId_DESCRIPTION);
                }
            }

        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON,
                    ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        }
        return "success";
    }

    public Object getAccountInvestByProduct(AccountInput accountInput) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getAccountInvestByProduct")
                .returningResultSet("pv_refcursor", BeanPropertyRowMapper.newInstance(InvestAssets.class))
                .declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_pid", Types.NUMERIC))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", accountInput.getCustId());
        params.addValue("pv_pid", accountInput.getProductId());
        Map<String, Object> map = jdbcCall.execute(params);
        List<InvestAssets> listContacts = (List<InvestAssets>) map.get("pv_refcursor");
        if (listContacts.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        // BearRequest bearRequest = new BearRequest();
        // bearRequest.setPayType("2");// BearRequest bearRequest = new BearRequest();
        // bearRequest.setPayType("2");
        // bearRequest.setPid(accountInput.getProductId());
        // if (!accountInput.getProductId().equals("15")) {
        // listContacts.forEach((element) -> {
        // bearRequest.setTerm(element.getTerm());
        // bearRequest.setAmt(element.getAmount());
        // bearRequest.setRate(element.getRate());
        // element.setProfit(Utils.getProductInfo(bearRequest).getMonthlyProfit());
        // String startDate = element.getStart_date().replace("00:00:00", "");
        // startDate = startDate.replace(" ", "");
        // LocalDate date = LocalDate.parse(startDate,
        // DateTimeFormatter.ISO_LOCAL_DATE);
        // startDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        // element.setStart_date(startDate);
        // String endDate = element.getEnd_date().replace("00", "");
        // endDate = endDate.replace(":", "");
        // endDate = endDate.replace(" ", "");
        // date = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        // endDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        // element.setEnd_date(endDate);
        //
        // });
        // }
        return listContacts;
    }

    public Object getRate(String pId, String term, String amt) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getRate")
                .declareParameters(new SqlParameter("pv_term", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_pid", Types.NUMERIC))
                .declareParameters(new SqlParameter("pv_amt", Types.NUMERIC))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_term", term);
        params.addValue("pv_pid", pId);
        params.addValue("pv_amt", amt);
        Map<String, Object> map = jdbcCall.execute(params);
        System.out.println(map.get("pv_refcursor"));
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return arrayList;
    }

    public Object saveTrans(Card9PayEntity entity) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("createTrans9Pay")
                .declareParameters(new SqlParameter("cid", Types.VARCHAR))
                .declareParameters(new SqlParameter("pid", Types.VARCHAR))
                .declareParameters(new SqlParameter("tid", Types.VARCHAR))
                .declareParameters(new SqlParameter("pri", Types.VARCHAR))
                .declareParameters(new SqlParameter("pstatus", Types.VARCHAR))
                .declareParameters(new SqlParameter("scode", Types.VARCHAR))
                .declareParameters(new SqlParameter("ccode", Types.VARCHAR))

                .declareParameters(new SqlOutParameter("rs", Types.NUMERIC));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cid", entity.getCustid());
        params.addValue("tid", entity.getTrans_Id());
        params.addValue("pid", entity.getProduct_id());
        params.addValue("pri", entity.getPrice());
        params.addValue("pstatus", entity.getPay_status());
        params.addValue("scode", entity.getSeri_code());
        params.addValue("ccode", entity.getCard_code());

        Map<String, Object> map = jdbcCall.execute(params);
        String result = map.get("rs").toString();
        if (!result.equals("0")) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return result;
    }

    public Object createInsurance(InsuranceRequest input) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("createInsurance")
                .returningResultSet("pv_refcursor", BeanPropertyRowMapper.newInstance(NotifyEntity.class))
                .declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_packageId", Types.NUMERIC))
                .declareParameters(new SqlParameter("pv_startDate", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_fee", Types.NUMERIC))
                .declareParameters(new SqlParameter("pv_beneficiaryFullName", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_beneficiaryBirthDate", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_beneficiaryIdNumber", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_RelationId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_isSick", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_isTreatedIn3Years", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_isTreatedNext12Months", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_isTreatedSpecialIn3Years", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_isRejectInsurance", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_isNormal", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_isConfirm", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonFullName", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonBirthDate", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonGender", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonIdNumber", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonMobile", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonEmail", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonAddress", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_ParentInsuranceCode", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_InsuredRelationId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_insuredPersonNationality", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));


        try {
//            Date sDate = new SimpleDateFormat("dd-MM-yyyy").parse(input.getPv_startDate());
//            Date bDate = new SimpleDateFormat("dd-MM-yyyy").parse(input.getPv_beneficiaryBirthDate());
//            Date ipDate = new SimpleDateFormat("dd-MM-yyyy").parse(input.getPv_insuredPersonBirthDate());
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("pv_custId", input.getPv_custId());
            params.addValue("pv_packageId", input.getPv_packageId());
            params.addValue("pv_startDate", input.getPv_startDate());
            params.addValue("pv_fee", input.getPv_fee());
            params.addValue("pv_beneficiaryFullName", input.getPv_beneficiaryFullName());
            params.addValue("pv_beneficiaryBirthDate", input.getPv_beneficiaryBirthDate());
            params.addValue("pv_beneficiaryIdNumber", input.getPv_beneficiaryIdNumber());
            params.addValue("pv_RelationId", input.getPv_RelationId());
            params.addValue("pv_isSick", input.getPv_isSick());
            params.addValue("pv_isTreatedIn3Years", input.getPv_isTreatedIn3Years());
            params.addValue("pv_isTreatedNext12Months", input.getPv_isTreatedNext12Months());
            params.addValue("pv_isTreatedSpecialIn3Years", input.getPv_isTreatedSpecialIn3Years());
            params.addValue("pv_isRejectInsurance", input.getPv_isRejectInsurance());
            params.addValue("pv_isNormal", input.getPv_isNormal());
            params.addValue("pv_isConfirm", input.getPv_isConfirm());
            params.addValue("pv_insuredPersonFullName", input.getPv_insuredPersonFullName());
            params.addValue("pv_insuredPersonBirthDate", input.getPv_insuredPersonBirthDate());
            params.addValue("pv_insuredPersonGender", input.getPv_insuredPersonGender());
            params.addValue("pv_insuredPersonIdNumber", input.getPv_insuredPersonIdNumber());
            params.addValue("pv_insuredPersonMobile", input.getPv_insuredPersonMobile());
            params.addValue("pv_insuredPersonEmail", input.getPv_insuredPersonEmail());
            params.addValue("pv_insuredPersonAddress", input.getPv_insuredPersonAddress());
            params.addValue("pv_ParentInsuranceCode", input.getPv_ParentInsuranceCode());
            params.addValue("pv_InsuredRelationId", input.getPv_InsuredRelationId());
            params.addValue("pv_insuredPersonNationality", input.getPv_insuredPersonNationality());


            Map<String, Object> map = jdbcCall.execute(params);
            Map.Entry<String, Object> entry = map.entrySet().iterator().next();
//            NotifyEntity notify = (NotifyEntity) map.get("pv_refcursor");
            String body = JsonMapper.writeValueAsString(entry.getValue());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;

            try {

                root = mapper.readTree(body);
                if (root.get(0).get(":B2") != null) {
                    String status = root.get(0).get(":B2").textValue();
                    if (status.equals("0")) {
                        throw new BusinessException(Constants.FAIL,
                                root.get(0).get(":B1").textValue());
                    }
                }

            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorCode.FAILED_TO_JSON,
                        ErrorCode.FAILED_TO_JSON_DESCRIPTION);
            }

            return entry;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public Object getTerm(String pId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pck_test")
                .withProcedureName("datetest")
                .declareParameters(new SqlParameter("pd", Types.DATE))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));
        try {
            Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse("26-03-2022");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("pd", utilDate);
            Map<String, Object> map = jdbcCall.execute(params);
            ArrayList<Object> arrayList = (ArrayList<Object>) map.get("pv_refcursor");
            if (arrayList.size() == 0) {
                throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
            }
            return arrayList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object findTrans9PayByDate(String sDate, String eDate, String cif) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("findTransByDate")
                .returningResultSet("c_ref", BeanPropertyRowMapper.newInstance(Card9PayEntity.class))
                .declareParameters(new SqlParameter("pdate", Types.VARCHAR))
                .declareParameters(new SqlParameter("pedate", Types.VARCHAR))
                .declareParameters(new SqlParameter("pcif", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("c_ref", Types.REF_CURSOR));
        Date sDateF = null;
        Date eDateF = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sDateF = sdf.parse(sDate);
            eDateF = sdf.parse(eDate);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String strSDate = formatter.format(sDateF);
        String strEDate = formatter.format(eDateF);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdate", strSDate);
        params.addValue("pedate", strEDate);
        params.addValue("pcif", cif);
        Map<String, Object> map = jdbcCall.execute(params);
        List<Card9PayEntity> arrayList = (List<Card9PayEntity>) map.get("c_ref");
        if (arrayList.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return arrayList;
    }

    public ArrayList<Object> getPayType() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getPayType")
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        Map<String, Object> map = jdbcCall.execute();
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size() == 0) {
            throw new BusinessException(ErrorCode.NO_DATA, ErrorCode.NO_DATA_DESCRIPTION);
        }
        return arrayList;
    }

}
