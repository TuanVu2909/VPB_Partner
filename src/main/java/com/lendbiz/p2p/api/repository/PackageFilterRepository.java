package com.lendbiz.p2p.api.repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

import com.lendbiz.p2p.api.entity.AuthProfileEntity;
import com.lendbiz.p2p.api.entity.CustomEntity;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.request.AddGroupRequest;
import com.lendbiz.p2p.api.request.AddUserRequest;
import com.lendbiz.p2p.api.request.DeleteUserRequest;
import com.lendbiz.p2p.api.request.InsertLogRequest;
import com.lendbiz.p2p.api.request.UpdateGroupRequest;
import com.lendbiz.p2p.api.request.UpdateUserRequest;
import com.lendbiz.p2p.api.utils.StringUtil;

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

	@Autowired
	private AuthRepository authRepository;
	
	public CustomEntity getQuery(String query) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withProcedureName("prc_get_query").declareParameters(new SqlParameter("p_query", Types.VARCHAR))
				.declareParameters(new SqlOutParameter("p_cursor", Types.REF_CURSOR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p_query", query);

		Map<String, Object> map = jdbcCall.execute(params);
		Map.Entry<String, Object> entry = map.entrySet().iterator().next();

		CustomEntity cEntity = new CustomEntity();
		cEntity.setResult(entry.getValue());

		return cEntity;
	}

	public CustomEntity insertUser(AddUserRequest addRequest) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withProcedureName("prc_insert_user").declareParameters(new SqlParameter("p_tlname", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tlfullname", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tllev", Types.INTEGER))
				.declareParameters(new SqlParameter("p_brid", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tltitle", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tlprn", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tlgroup", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_pin", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_description", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tltype", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_active", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_idcode", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_homeorder", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_teleorder", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_exttel", Types.VARCHAR))
				.declareParameters(new SqlOutParameter("p_refcursor", Types.REF_CURSOR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p_tlname", addRequest.getTlName());
		params.addValue("p_tlfullname", addRequest.getTlFullName());
		params.addValue("p_tllev", addRequest.getTlLev());
		params.addValue("p_brid", addRequest.getBrId());
		params.addValue("p_tltitle", addRequest.getTlTitle());
		params.addValue("p_tlprn", addRequest.getTlPrn());
		params.addValue("p_tlgroup", addRequest.getTlGroup());
		params.addValue("p_pin", addRequest.getPin());
		params.addValue("p_description", addRequest.getDescription());
		params.addValue("p_tltype", addRequest.getTlType());
		params.addValue("p_active", addRequest.getActive());
		params.addValue("p_idcode", addRequest.getIdCode());
		params.addValue("p_homeorder", addRequest.getHomeOrder());
		params.addValue("p_teleorder", addRequest.getTeleOrder());
		params.addValue("p_exttel", addRequest.getExtTel());

		Map<String, Object> map = jdbcCall.execute(params);
		Map.Entry<String, Object> entry = map.entrySet().iterator().next();

		CustomEntity cEntity = new CustomEntity();
		cEntity.setResult(entry.getValue());

		return cEntity;
	}

	public int updatetUser(UpdateUserRequest updateRequest) {

		updateRequest = newProfileBuilder(updateRequest);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withFunctionName("fn_update_user").declareParameters(new SqlParameter("p_tlid", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tlname", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tlfullname", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tllev", Types.INTEGER))
				.declareParameters(new SqlParameter("p_brid", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tltitle", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tlprn", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tlgroup", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_pin", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_description", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_tltype", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_active", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_idcode", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_homeorder", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_teleorder", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_exttel", Types.VARCHAR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p_tlid", updateRequest.getTlId());
		params.addValue("p_tlname", updateRequest.getTlName());
		params.addValue("p_tlfullname", updateRequest.getTlFullName());
		params.addValue("p_tllev", updateRequest.getTlLev());
		params.addValue("p_brid", updateRequest.getBrId());
		params.addValue("p_tltitle", updateRequest.getTlTitle());
		params.addValue("p_tlprn", updateRequest.getTlPrn());
		params.addValue("p_tlgroup", updateRequest.getTlGroup());
		params.addValue("p_pin", updateRequest.getPin());
		params.addValue("p_description", updateRequest.getDescription());
		params.addValue("p_tltype", updateRequest.getTlType());
		params.addValue("p_active", updateRequest.getActive());
		params.addValue("p_idcode", updateRequest.getIdCode());
		params.addValue("p_homeorder", updateRequest.getHomeOrder());
		params.addValue("p_teleorder", updateRequest.getTeleOrder());
		params.addValue("p_exttel", updateRequest.getExtTel());

		int res = jdbcCall.executeFunction(BigDecimal.class, params).intValue();

		if (res == 0) {
			throw new BusinessException("14", "Update fail!");
		}

		return res;
	}

	public int deleteUser(DeleteUserRequest deleteRequest) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withFunctionName("fn_delete_user").declareParameters(new SqlParameter("p_tlid", Types.VARCHAR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p_tlid", deleteRequest.getTlId());

		int res = jdbcCall.executeFunction(BigDecimal.class, params).intValue();

		if (res == 0) {
			throw new BusinessException("15", "Delete fail!");
			
		}

		return res;
	}

	public CustomEntity getConfigData(String type) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withProcedureName("prc_get_data_conf").declareParameters(new SqlParameter("p_type", Types.VARCHAR))
				.declareParameters(new SqlOutParameter("p_cursor", Types.REF_CURSOR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p_type", type);

		Map<String, Object> map = jdbcCall.execute(params);
		Map.Entry<String, Object> entry = map.entrySet().iterator().next();

		CustomEntity cEntity = new CustomEntity();
		cEntity.setResult(entry.getValue());

		return cEntity;
	}

	public CustomEntity insertGroup(AddGroupRequest addRequest) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withFunctionName("fn_insert_group").declareParameters(new SqlParameter("p_grpName", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_grpType", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_grpRight", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_active", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_description", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_prgrpId", Types.VARCHAR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p_grpName", addRequest.getGrpName());
		params.addValue("p_grpType", addRequest.getGrpType());
		params.addValue("p_grpRight", addRequest.getGrpRight());
		params.addValue("p_active", addRequest.getActive().toUpperCase());
		params.addValue("p_description", addRequest.getDescription());
		params.addValue("p_prgrpId", addRequest.getPrgrpId());

		Map<String, Object> map = jdbcCall.execute(params);
		Map.Entry<String, Object> entry = map.entrySet().iterator().next();

		CustomEntity cEntity = new CustomEntity();
		cEntity.setResult(entry.getValue());

		return cEntity;
	}

	public CustomEntity updateGroup(UpdateGroupRequest updateRequest) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withFunctionName("fn_update_group").declareParameters(new SqlParameter("p_grpName", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_grpType", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_grpRight", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_active", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_description", Types.VARCHAR))
				.declareParameters(new SqlParameter("p_prgrpId", Types.VARCHAR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p_grpName", updateRequest.getGrpName());
		params.addValue("p_grpType", updateRequest.getGrpType());
		params.addValue("p_grpRight", updateRequest.getGrpRight());
		params.addValue("p_active", updateRequest.getActive().toUpperCase());
		params.addValue("p_description", updateRequest.getDescription());
		params.addValue("p_prgrpId", updateRequest.getPrgrpId());

		Map<String, Object> map = jdbcCall.execute(params);
		Map.Entry<String, Object> entry = map.entrySet().iterator().next();

		CustomEntity cEntity = new CustomEntity();
		cEntity.setResult(entry.getValue());

		return cEntity;
	}

	private UpdateUserRequest newProfileBuilder(UpdateUserRequest updateRequest) {
		
		AuthProfileEntity newProfile = authRepository.getByTLId(updateRequest.getTlId());

		if (newProfile != null) {
			if (StringUtil.isEmty(updateRequest.getActive())) {
				updateRequest.setActive(newProfile.getActive());
			}

			if (StringUtil.isEmty(updateRequest.getBrId())) {
				updateRequest.setBrId(newProfile.getBrId());
			}

			if (StringUtil.isEmty(updateRequest.getDescription())) {
				updateRequest.setDescription(newProfile.getDescription());
			}

			if (StringUtil.isEmty(updateRequest.getExtTel())) {
				updateRequest.setExtTel(newProfile.getExtTel());
			}

			if (StringUtil.isEmty(updateRequest.getHomeOrder())) {
				updateRequest.setHomeOrder(newProfile.getHomeOrder());
			}

			if (StringUtil.isEmty(updateRequest.getIdCode())) {
				updateRequest.setIdCode(newProfile.getIdCode());
			}

			if (StringUtil.isEmty(updateRequest.getPin())) {
				updateRequest.setPin(newProfile.getPin());
			}

			if (StringUtil.isEmty(updateRequest.getTeleOrder())) {
				updateRequest.setTeleOrder(newProfile.getTeleOrder());
			}

			if (StringUtil.isEmty(updateRequest.getTlFullName())) {
				updateRequest.setTlFullName(newProfile.getTlFullName());
			}

			if (StringUtil.isEmty(updateRequest.getTlGroup())) {
				updateRequest.setTlGroup(newProfile.getTlGroup());
			}

			if (updateRequest.getTlLev() != 0) {
				updateRequest.setTlLev(newProfile.getTlLev());
			}

			if (StringUtil.isEmty(updateRequest.getTlName())) {
				updateRequest.setTlName(newProfile.getTlName());
			}

			if (StringUtil.isEmty(updateRequest.getTlPrn())) {
				updateRequest.setTlPrn(newProfile.getTlPrn());
			}

			if (StringUtil.isEmty(updateRequest.getTlTitle())) {
				updateRequest.setTlTitle(newProfile.getTlTitle());
			}

			if (StringUtil.isEmty(updateRequest.getTlType())) {
				updateRequest.setTlType(newProfile.getTlType());
			}

		} else {
			throw new BusinessException("115", "User not exists!");
		}

		return updateRequest;
	}

	public int insertLogs(InsertLogRequest insertLogRequest) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_LB_AUTHENTICATION")
				.withFunctionName("fn_insert_logs")
				.declareParameters(new SqlParameter("p_requestId", Types.VARCHAR))
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

	public CustomEntity login(String username, String password, String deviceId) {

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

		CustomEntity cEntity = new CustomEntity();
		cEntity.setResult(entry.getValue());

		return cEntity;
	}

}
