package com.lendbiz.p2p.api.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.lendbiz.p2p.api.entity.Role;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Serializable> {

    @Procedure(name = "Role.findRolesViaProcedure")
    Stream<Role> findRolesViaProcedure();
}