package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.Product9PayCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface C9payProductRepo extends JpaRepository<Product9PayCardEntity,String> {

}
