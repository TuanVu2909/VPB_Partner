package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.PortfolioInvest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioInvest, Integer> {
    @Procedure("PortfolioInvest.getPortfolio")
    List<PortfolioInvest> getPortfolio(@Param("pv_custId") String cif);
}
