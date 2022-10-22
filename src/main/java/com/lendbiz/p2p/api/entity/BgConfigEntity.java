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

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "BgConfigEntity.findViaProcedure", procedureName = "PKG_API.GET_CONFIG_PRODUCT", resultClasses = BgConfigEntity.class, parameters = { //
                @StoredProcedureParameter(name = "p_cursor", mode = ParameterMode.REF_CURSOR, type = Void.class) }) //
})
@Getter
@Setter
@ToString
public class BgConfigEntity {
    @Id
    // @GeneratedValue
    private String id;
    @Column(name = "PRODNAME")
    private String prodName;
    @Column(name = "ICONURL")
    private String iconUrl;
    @Column(name = "TABID")
    private String tabId;
    @Column(name = "TABTITLE")
    private String tabTitle;
    @Column(name = "ROUTING")
    private String routing;
    @Column(name = "CURRENTPAGE")
    private int currentPage;
    @Column(name = "SORTORDER")
    private int sortOrder;
}
