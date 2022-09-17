package com.lendbiz.p2p.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "UpdateAccountEntity.updateAccount", procedureName = "PKG_API.PRC_UPDATE_EKYC_INFO", resultClasses = UpdateAccountEntity.class, parameters = { //
                @StoredProcedureParameter(name = "p_fullName", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_idCode", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_sex", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_dob", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_address", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_id_exp", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_id_date", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_idPlace", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankAccount", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankAccountName", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankName", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankCode", mode = ParameterMode.IN, type = String.class) }), //

        @NamedStoredProcedureQuery(name = "UpdateAccountEntity.updateBankAccount", procedureName = "PKG_API.PRC_UPDATE_BANK_INFO", resultClasses = UpdateAccountEntity.class, parameters = { //
                @StoredProcedureParameter(name = "p_custId", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankAccount", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankAccountName", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankName", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_bankCode", mode = ParameterMode.IN, type = String.class) })

})
public class UpdateAccountEntity {

    @Id
    private String key;

}
