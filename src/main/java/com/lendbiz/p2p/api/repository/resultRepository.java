// package com.lendbiz.p2p.api.repository;

// import java.util.List;

// import com.lendbiz.p2p.api.entity.CustomEntity;

// import org.springframework.data.jpa.repository.query.Procedure;
// import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface resultRepository extends CrudRepository<CustomEntity, Long>{

//     @Procedure(name = "PKG_LB_USERS.prc_get_query")
//     List<CustomEntity> getQuery(@Param("p_query") String query);
// }
