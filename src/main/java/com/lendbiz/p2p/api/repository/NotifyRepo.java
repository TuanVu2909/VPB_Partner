package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.NotifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NotifyRepo extends JpaRepository<NotifyEntity, String> {

    @Procedure("NotifyEntity.changeCoin")
    NotifyEntity changeCoin(@Param("pv_custId") String pv_custId, @Param("pv_coinAmount") String pv_coinAmount);


    @Procedure("NotifyEntity.updateReferenceId")
    NotifyEntity updateReferenceId(@Param("pv_custId") String pv_custId, @Param("pv_refId") String pv_refId);

    @Procedure("NotifyEntity.createBear")
    NotifyEntity createBear(@Param("pv_custId") String pv_custId
            , @Param("pv_pid") String pv_pid
            , @Param("pv_term") String pv_term
            , @Param("pv_rate") String pv_rate
            , @Param("pv_amt") String pv_amt
            , @Param("pv_contractId") String pv_contractId
            , @Param("pv_payType") String pv_payType);


    @Procedure("NotifyEntity.createNavDaily")
    NotifyEntity createNavDaily(@Param("pv_fundId") String pv_fundId
            , @Param("pv_nav") String pv_nav
            , @Param("pv_navDate") String pv_navDate);


    @Procedure("NotifyEntity.insert_trans9pay")
    NotifyEntity insert_trans9pay(@Param("cid") String pv_custId
            , @Param("tid") String tid
            , @Param("pid") String pid
            , @Param("pri") String pri
            , @Param("pstatus") String pstatus
            , @Param("scode") String scode
            , @Param("ccode") String ccode
            , @Param("c_amount") String c_amount);

    @Procedure("NotifyEntity.createInsurance")
    NotifyEntity createInsurance(@Param("pv_custId") String pv_custId
            , @Param("pv_packageId") String pv_packageId
            , @Param("pv_startDate") String pv_startDate
            , @Param("pv_fee") String pv_fee
            , @Param("pv_beneficiaryFullName") String pv_beneficiaryFullName
            , @Param("pv_beneficiaryBirthDate") String pv_beneficiaryBirthDate

            , @Param("pv_beneficiaryIdNumber") String pv_beneficiaryIdNumber
            , @Param("pv_RelationId") String pv_RelationId
            , @Param("pv_isSick") String pv_isSick
            , @Param("pv_isTreatedIn3Years") String pv_isTreatedIn3Years


            , @Param("pv_isTreatedNext12Months") String pv_isTreatedNext12Months
            , @Param("pv_isTreatedSpecialIn3Years") String pv_isTreatedSpecialIn3Years
            , @Param("pv_isRejectInsurance") String pv_isRejectInsurance
            , @Param("pv_isNormal") String pv_isNormal

            , @Param("pv_isConfirm") String pv_isConfirm
            , @Param("pv_insuredPersonFullName") String pv_insuredPersonFullName
            , @Param("pv_insuredPersonBirthDate")  String pv_insuredPersonBirthDate
            , @Param("pv_insuredPersonGender") String pv_insuredPersonGender


            , @Param("pv_insuredPersonIdNumber") String pv_insuredPersonIdNumber
            , @Param("pv_insuredPersonMobile") String pv_insuredPersonMobile
            , @Param("pv_insuredPersonEmail") String pv_insuredPersonEmail
            , @Param("pv_insuredPersonAddress") String pv_insuredPersonAddress
            , @Param("pv_ParentInsuranceCode") String pv_ParentInsuranceCode
            , @Param("pv_InsuredRelationId") String pv_InsuredRelationId

            , @Param("pv_insuredPersonNationality") String pv_insuredPersonNationality

    );






}
