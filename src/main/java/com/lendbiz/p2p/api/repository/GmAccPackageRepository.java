package com.lendbiz.p2p.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lendbiz.p2p.api.entity.GmAccPackacgeEntity;

@Repository("gmAccPackageRepository")
public interface GmAccPackageRepository extends CrudRepository<GmAccPackacgeEntity, String> {

}
