package com.lendbiz.p2p.api.repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.InsertLogRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.utils.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
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

    public int insertLogs(InsertLogRequest insertLogRequest) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
                .withFunctionName("fn_insert_logs").declareParameters(new SqlParameter("p_requestId", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_messageType", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_status", Types.INTEGER))
                .declareParameters(new SqlParameter("p_bodyDetail", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_httpMethod", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_sourceAppId", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_sourceAppIp", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_destAppId", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_destAppPort", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_authorization", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_path", Types.VARCHAR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_requestId", insertLogRequest.getRequestId());
        params.addValue("p_messageType", insertLogRequest.getMessageType());
        params.addValue("p_status", insertLogRequest.getStatus());
        params.addValue("p_bodyDetail", insertLogRequest.getBodyDetail());
        params.addValue("p_httpMethod", insertLogRequest.getHttpMethod());
        params.addValue("p_sourceAppId", insertLogRequest.getSourceAppId());
        params.addValue("p_sourceAppIp", insertLogRequest.getSourceAppIp());
        params.addValue("p_destAppId", insertLogRequest.getDestAppId());
        params.addValue("p_destAppPort", insertLogRequest.getDestAppPort());
        params.addValue("p_authorization", insertLogRequest.getAuthorization());
        params.addValue("p_path", insertLogRequest.getPath());

        int res = jdbcCall.executeFunction(BigDecimal.class, params).intValue();

        if (res == 0) {
            throw new BusinessException("119", "Insert logs fail!");
        }

        return res;
    }

    public Object login(String username, String password, String deviceId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API_AUTHENTICATION")
                .withProcedureName("LOGIN").declareParameters(new SqlParameter("pv_Username", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_Password", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_Deviceid", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_Username", username);
        params.addValue("pv_Password", password);
        params.addValue("pv_Deviceid", deviceId);

        Map<String, Object> map = jdbcCall.execute(params);
        Map.Entry<String, Object> entry = map.entrySet().iterator().next();

        String body = JsonMapper.writeValueAsString(entry.getValue());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(body);
            if (root.get(0) != null && root.get(0).get("ERRORCODE") != null) {
                if (root.get(0).get("ERRORCODE").asInt() == 99)
                    throw new BusinessException(Constants.FAIL, root.get(0).get("STATUS").textValue());
            }
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        }

        return entry.getValue();
    }

    public Object reqJoin(ReqJoinRequest reqJoinRequest) {
        Map.Entry<String, Object> entry = null;

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_API_AUTHENTICATION")
                .withProcedureName("REQJOIN").declareParameters(new SqlParameter("pv_Type", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_Fullname", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Sex", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_DOB", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Place", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_IDCode", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_IDDate", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_IDPlace", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_OrgAddress", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Address", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_IDWard", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_IDDistrict", Types.INTEGER))
                .declareParameters(new SqlParameter("Pv_Phone", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Mobile", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Email", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_BankAccount", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Bank", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Address", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_TaxNo", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Online", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_ProductInfo", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_ConsultalInfo", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Position", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_Job", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_exFullname", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_exPosition", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_exJob", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_exMobile", Types.VARCHAR))
                .declareParameters(new SqlParameter("Pv_ExEmail", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_ExAddress", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_ExBank", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_ExSTK", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_password", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("PV_REFCURSOR", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_Type", reqJoinRequest.getType());
        params.addValue("pv_Fullname", reqJoinRequest.getFullName());
        params.addValue("Pv_Sex", reqJoinRequest.getSex());
        params.addValue("Pv_DOB", reqJoinRequest.getDob());
        params.addValue("Pv_Place", reqJoinRequest.getPlace());
        params.addValue("Pv_IDCode", reqJoinRequest.getIdCode());
        params.addValue("Pv_IDDate", reqJoinRequest.getIdDate());
        params.addValue("Pv_IDPlace", reqJoinRequest.getIdPlace());
        params.addValue("Pv_OrgAddress", reqJoinRequest.getOrgAddress());
        params.addValue("Pv_Address", reqJoinRequest.getAddress());
        params.addValue("Pv_IDWard", reqJoinRequest.getIdWard());
        params.addValue("Pv_IDDistrict", reqJoinRequest.getIdDistrict());
        params.addValue("Pv_Phone", reqJoinRequest.getPhone());
        params.addValue("Pv_Mobile", reqJoinRequest.getMobile());
        params.addValue("Pv_Email", reqJoinRequest.getEmail());
        params.addValue("Pv_BankAccount", reqJoinRequest.getBankAccount());
        params.addValue("Pv_Bank", reqJoinRequest.getBank());
        params.addValue("Pv_TaxNo", reqJoinRequest.getTaxNo());
        params.addValue("Pv_Online", reqJoinRequest.getOnline());
        params.addValue("Pv_MatchOrdSMS", reqJoinRequest.getMatchOrdSms());
        params.addValue("Pv_ProductInfo", reqJoinRequest.getProductInfo());
        params.addValue("Pv_ConsultalInfo", reqJoinRequest.getConsultalInfo());
        params.addValue("Pv_OfficeName", reqJoinRequest.getOfficeName());
        params.addValue("Pv_Position", reqJoinRequest.getPosition());
        params.addValue("Pv_Job", reqJoinRequest.getJob());
        params.addValue("Pv_exFullname", reqJoinRequest.getExFullName());
        params.addValue("Pv_exPosition", reqJoinRequest.getExPosition());
        params.addValue("Pv_exJob", reqJoinRequest.getExJob());
        params.addValue("Pv_exMobile", reqJoinRequest.getExMobile());
        params.addValue("Pv_ExEmail", reqJoinRequest.getExEmail());
        params.addValue("pv_ExAddress", reqJoinRequest.getExAddress());
        params.addValue("pv_ExBank", reqJoinRequest.getExBank());
        params.addValue("pv_ExSTK", reqJoinRequest.getExStk());
        params.addValue("pv_password", reqJoinRequest.getPassword());

        try {
            Map<String, Object> map = jdbcCall.execute(params);
            entry = map.entrySet().iterator().next();

        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }

        // String body = JsonMapper.writeValueAsString(entry.getValue());

        // ObjectMapper mapper = new ObjectMapper();
        // JsonNode root;
        // try {
        // root = mapper.readTree(body);
        // if (root.get(0) != null && root.get(0).get("0") != null) {
        // // responseId = root.get(0).get(":B1").textValue();
        // } else {
        // throw new BusinessException(Constants.FAIL,
        // root.get(0).get("ERRMSG").textValue());
        // }

        // } catch (JsonProcessingException e) {
        // throw new BusinessException(ErrorCode.FAILED_TO_JSON,
        // ErrorCode.FAILED_TO_JSON_DESCRIPTION);
        // }

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
    public Object getAccountAsset(String custId) {
        Map.Entry<String, Object> entry ;
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getAccountAsset").declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", custId);
        Map<String, Object> map = jdbcCall.execute(params);
        ArrayList <Object> arrayList  = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size()==0){
            throw new BusinessException("01", "No data");
        }
        return arrayList;
    }


    public Object getAccountInvest(String custId) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getAccountInvest").declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", custId);
        Map<String, Object> map = jdbcCall.execute(params);
        ArrayList <Object> arrayList  = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size()==0){
            throw new BusinessException("01", "No data");
        }
        return arrayList;
    }
    public Object getProduct( ) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getproduct")
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));
        ;
        Map<String, Object> map = jdbcCall.execute();
        ArrayList <Object> arrayList  = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size()==0){
            throw new BusinessException("01", "No data");
        }
        return arrayList;
    }
    public Object getAccountInvestByProduct(AccountInput accountInput) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCK_GM")
                .withProcedureName("getAccountInvestByProduct")
                .declareParameters(new SqlParameter("pv_custId", Types.VARCHAR))
                .declareParameters(new SqlParameter("pv_pid", Types.NUMERIC))
                .declareParameters(new SqlOutParameter("pv_refcursor", Types.REF_CURSOR));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pv_custId", accountInput.getCustId());
        params.addValue("pv_pid", Integer.parseInt(accountInput.getProductId()));
        Map<String, Object> map = jdbcCall.execute(params);
        System.out.println(map.get("pv_refcursor"));
        ArrayList <Object> arrayList  = (ArrayList<Object>) map.get("pv_refcursor");
        if (arrayList.size()==0){
            throw new BusinessException("01", "No data");
        }
        return arrayList;
    }

}
