package com.lendbiz.p2p.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.springframework.transaction.annotation.Transactional;

@Entity
@NamedStoredProcedureQueries({ //
        @NamedStoredProcedureQuery(name = "Role.findRolesViaProcedure", procedureName = "collect_roles", resultClasses = Role.class, parameters = { //
                @StoredProcedureParameter(name = "role_list_o", mode = ParameterMode.REF_CURSOR, type = Void.class) }) //
})

public class Role {

    @Id
    @GeneratedValue //
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
