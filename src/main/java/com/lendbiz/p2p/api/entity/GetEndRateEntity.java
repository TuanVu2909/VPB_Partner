package com.lendbiz.p2p.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.apache.commons.collections4.list.CursorableLinkedList.Cursor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@NamedStoredProcedureQueries({
                @NamedStoredProcedureQuery(name = "GetEndRateEntity.getRateCal", procedureName = "pkg_api.fn_calIntNoTerm", resultClasses = GetEndRateEntity.class, parameters = { //
                                @StoredProcedureParameter(name = "pv_amt", mode = ParameterMode.IN, type = Float.class),
                                @StoredProcedureParameter(name = "pv_payType", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "pv_startDate", mode = ParameterMode.IN, type = String.class),
                                @StoredProcedureParameter(name = "p_result_cur", mode = ParameterMode.REF_CURSOR, type = Void.class),
                }),

})
@Getter
@Setter
@ToString
public class GetEndRateEntity {
        @Id
        @Column(name = "intcal")
        private String result;

}
