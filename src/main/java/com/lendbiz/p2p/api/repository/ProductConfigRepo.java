package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.ProductConfigEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductConfigRepo extends JpaRepository<ProductConfigEntity, String> {

        @Query(value = "SELECT id id, product_name prodName, icon_url iconUrl, tab_id tabId, tab_title tabTitle, routing routing, current_page currentPage, orders sortOrder FROM BG_PRODUCTS where state = 'Y'", nativeQuery = true)
        List<ProductConfigEntity> getProductConfig();

}
