/*
 * Apache License, Version 2.0
 *
 * Copyright (c) 2021 TU HOANG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lendbiz.p2p.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.*;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.repository.*;
import com.lendbiz.p2p.api.request.*;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.service.UserService;
import com.lendbiz.p2p.api.utils.StringUtil;
import com.lendbiz.p2p.api.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/***********************************************************************
 *
 * @package：com.lendbiz.p2p.api.service.impl，@class-name：UserServiceImpl.java
 *
 * @copyright Copyright: 2021-2022
 * @creator Hoang Thanh Tu <br/>
 * @create-time Apr 9, 2021 10:43:45 AM
 *
 ***********************************************************************/
@Service("userService")
public class UserServiceImpl extends BaseResponse<UserService> implements UserService {
    @Autowired
    InvestPackageDetailRepository investPackageDetailRepository;

    @Autowired
    PkgFundInfoRepository pkgFundInfoRepository;
    @Autowired
    FundInvestDetailRepository fundInvestDetailRepository;
    @Autowired
    NAVRepository navRepository;
    @Autowired
    PackageFilterRepository pkgFilterRepo;
    @Autowired
    RelationRepo relationRepo;
    @Autowired
    FundListRepository fundListRepository;
    @Autowired
    InvestAssetsRepository investAssetsRepository;
    @Autowired
    UserOnlineRepository userOnlineRepo;
    @Autowired
    ProductGMRepository productGMRepository;
    @Autowired
    AccountAssetRepository accountAssetRepository;
    @Autowired
    AccountInvestRepository accountInvestRepository;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    FirstPasswordRepository firstPasswordRepository;
    @Autowired
    RateRepo rateRepo;
    @Autowired
    RateConfigRepo rateConfRepo;
    @Autowired
    TermRepo termRepo;
    @Autowired
    PayRepo payRepo;
    @Autowired
    CoinRepo coinRepo;
    @Autowired
    NotifyRepo notifyRepo;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    BaoVietRepo baoVietRepo;
    @Autowired
    InvestPackageRepository investPackageRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    FundInvestRepository fundInvestRepository;

    @Autowired
    UpdateAccountRepository accountRepository;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        // List<Object> response;

        UserOnline user = userOnlineRepo.getUserOnline(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPwd())) {
            throw new BusinessException(ErrorCode.FAIL_LOGIN, ErrorCode.FAIL_LOGIN_DESCRIPTION);
        } else {
            if (userOnlineRepo.checkAccountMappingExist(user.getCustId()) == 0) {
                ReqJoinRequest reqJoinRequest = new ReqJoinRequest();
                reqJoinRequest.setMobile(loginRequest.getUsername());
                pkgFilterRepo.reqJoin(reqJoinRequest);
            }

        }

        return response(toResult(user.getCustId()));

    }

    @Override
    public ResponseEntity<?> register(ReqJoinRequest reqJoinRequest) {

        List<Object> response = (ArrayList) pkgFilterRepo.reqJoin(reqJoinRequest);
        return response(toResult(response.get(0)));
    }

    @Override
    public ResponseEntity<?> verifyAcc(VerifyAccountInput input) {
        try {
            return response(toResult(pkgFilterRepo.verifyAcc(input)));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getUserInfo(String mobile) {
        try {
            return response(toResult(userInfoRepository.getUserInfo(mobile)));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> setAccountPassword(SetAccountPasswordRequest setAccountPasswordRequest) {
        FirstPasswordEntity entity;
        try {
            entity = firstPasswordRepository.firstPassword(setAccountPasswordRequest.getCustId(),
                    passwordEncoder.encode(setAccountPasswordRequest.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
        }

        return response(toResult(entity));
    }

    @Override
    public ResponseEntity<?> updateAccountInfo(UpdateAccountRequest updateRequest) {
        FirstPasswordEntity entity;
        try {
            entity = accountRepository.updateAccount(updateRequest.getCustId(), updateRequest.getFullName(),
                    updateRequest.getIdCode(), updateRequest.getSex(), updateRequest.getDob(),
                    updateRequest.getAddress(), updateRequest.getIdExp(), updateRequest.getIdDate(),
                    updateRequest.getIdPlace());
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
        }

        return response(toResult(entity));
    }

    @Override
    public ResponseEntity<?> createBear(AccountInput input) {
        try {
            NotifyEntity notify = notifyRepo.createBear(input.getCustId(), input.getProductId(),
                    input.getTerm(),
                    input.getRate(),
                    input.getAmt(),
                    input.getContractId(), input.getPayType());
            if (!notify.getPStatus().equals("01")) {
                throw new BusinessException(notify.getPStatus(), notify.getDes());
            }
            return response(toResult(notify));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, "data connection failed");
        }
    }

    @Override
    public ResponseEntity<?> getAccountAsset(String custId) {
        AccountAssetEntity entity = accountAssetRepository.getAccountAsset(custId);
        if (entity == null)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(entity));

    }

    @Override
    public ResponseEntity<?> getAccountInvest(String custId) {
        try {
            ArrayList<AccountInvest> list = accountInvestRepository.getAccountInvest(custId);
            if (list.size() == 0)
                throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
            return response(toResult(list));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getPortfolioInvest(String custId) {
        try {
            List<PortfolioInvest> list = portfolioRepository.getPortfolio(custId);
            if (list.size() == 0)
                throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
            return response(toResult(list));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getProduct() {
        try {
            return response(toResult(productGMRepository.getproduct()));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getPayType() {
        try {
            return response(toResult(payRepo.getPay()));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getRate(String term, String productId, String amount) {

        ArrayList<RateEntity> list = rateRepo.getRatePro(productId, term, amount);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);

        return response(toResult(list));

    }

    @Override
    public ResponseEntity<?> getConfigRate() {

        List<RateConfigEntity> list = rateConfRepo.getRate();
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);

        return response(toResult(list));

    }

    @Override
    public ResponseEntity<?> getTerm(String productId) {
        try {
            return response(toResult(termRepo.getTerm(productId)));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAccountInvestByProduct(AccountInput accountInput) {
        try {
            ArrayList<InvestAssets> list = investAssetsRepository.getAccountInvestByProduct(accountInput.getCustId(),
                    accountInput.getProductId());
            if (list.size() == 0)
                throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
            // ArrayList<InvestAssetResponse> investAssetResponseList = new ArrayList<>();
            // BearRequest bearRequest = new BearRequest();
            //
            // bearRequest.setPayType("2");
            // bearRequest.setPid(accountInput.getProductId());
            // if (!accountInput.getProductId().equals("15")) {
            // list.forEach((element) -> {
            // bearRequest.setTerm(element.getTerm());
            // bearRequest.setAmt(element.getAmount());
            // bearRequest.setRate(element.getRate());
            // InvestAssetResponse response = new InvestAssetResponse();
            // response.setAmount(element.getAmount());
            // response.setRate(element.getRate());
            // response.setDocumentno(element.getDocumentno());
            // response.setTerm(element.getTerm());
            // response.setProfit(Utils.getProductInfo(bearRequest).getMonthlyProfit());
            // String startDate = element.getStart_date().replace("00:00:00", "");
            // startDate = startDate.replace(" ", "");
            // LocalDate date = LocalDate.parse(startDate,
            // DateTimeFormatter.ISO_LOCAL_DATE);
            // startDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            // element.setStart_date(startDate);
            // String endDate = element.getEnd_date().replace("00", "");
            // endDate = endDate.replace(":", "");
            // endDate = endDate.replace(" ", "");
            // date = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
            // endDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            // element.setEnd_date(endDate);
            // response.setEnd_date(endDate);
            // response.setStart_date(startDate);
            // investAssetResponseList.add(response);
            //
            // });
            // }

            return response(toResult(list));
        } catch (Exception e) {
            throw new BusinessException("11", e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getProductInfo(BearRequest bearRequest) {
        try {
            return response(toResult(Utils.getProductInfo(bearRequest)));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getCoin(String cif) {
        try {
            CoinEntity coinEntity = coinRepo.getCoin(cif);
            return response(toResult(coinEntity));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> changeCoin(AccountInput input) {
        NotifyEntity notify = notifyRepo.changeCoin(input.getCustId(), input.getCoin());
        if (!notify.getPStatus().equals("01")) {
            throw new BusinessException(notify.getPStatus(), notify.getDes());
        }
        return response(toResult(notify));

    }

    @Override
    public ResponseEntity<?> updateReferenceId(AccountInput input) {
        NotifyEntity notify = notifyRepo.updateReferenceId(input.getCustId(), input.getPv_refId());
        if (!notify.getPStatus().equals("01")) {
            throw new BusinessException(notify.getPStatus(), notify.getDes());
        }
        return response(toResult(notify));
    }

    @Override
    public ResponseEntity<?> getBankInfo() {
        ArrayList<BankInfoEntity> list = (ArrayList<BankInfoEntity>) bankRepository.findAll();
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getInsurancePackage() {
        ArrayList<BaoVietEntity> list = baoVietRepo.getInsurancePackage();
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getRelation() {
        ArrayList<RelationEntity> list = relationRepo.getRelation();
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> createInsurance(InsuranceRequest insuranceRequest) {

        // return response(toResult(pkgFilterRepo.createInsurance(insuranceRequest)));
        return response(toResult(notifyRepo.createInsurance(insuranceRequest.getPv_custId(),
                insuranceRequest.getPv_packageId(),
                insuranceRequest.getPv_startDate(),
                insuranceRequest.getPv_fee(),
                insuranceRequest.getPv_beneficiaryFullName(),
                insuranceRequest.getPv_beneficiaryBirthDate(),
                insuranceRequest.getPv_beneficiaryIdNumber(),
                insuranceRequest.getPv_RelationId(),
                insuranceRequest.getPv_isSick(),
                insuranceRequest.getPv_isTreatedIn3Years(),
                insuranceRequest.getPv_isTreatedNext12Months(),
                insuranceRequest.getPv_isTreatedSpecialIn3Years(),
                insuranceRequest.getPv_isRejectInsurance(),
                insuranceRequest.getPv_isNormal(),
                insuranceRequest.getPv_isConfirm(),
                insuranceRequest.getPv_insuredPersonFullName(),
                insuranceRequest.getPv_insuredPersonBirthDate(),
                insuranceRequest.getPv_insuredPersonGender(),
                insuranceRequest.getPv_insuredPersonIdNumber(),
                insuranceRequest.getPv_insuredPersonMobile(),
                insuranceRequest.getPv_insuredPersonEmail(),
                insuranceRequest.getPv_insuredPersonAddress(),
                insuranceRequest.getPv_ParentInsuranceCode(),
                insuranceRequest.getPv_InsuredRelationId(),
                insuranceRequest.getPv_insuredPersonNationality())));

    }

    @Override
    public ResponseEntity<?> createNavDaily(GmFundNavRequest request) {
        NotifyEntity notify = notifyRepo.createNavDaily(request.getFundCode(), request.getNav(), request.getNavDate());

        return response(toResult(notify));

    }

    @Override
    public ResponseEntity<?> createFundInvest(GmFundNavRequest request) {
        NotifyEntity notify = notifyRepo.createFundInvest(request.getPv_custId(), request.getPv_amt(),
                request.getPv_packageId());

        return response(toResult(notify));
    }

    @Override
    public ResponseEntity<?> getFundList() {

        ArrayList<FundListEntity> list = fundListRepository.getFundList();
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getInvestPackage() {
        ArrayList<InvestPackageEntity> list = investPackageRepository.getInvestPackage();
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getInvestPackageDetail(String pkId) {

        ArrayList<InvestPackageDetailEntity> list = (ArrayList<InvestPackageDetailEntity>) investPackageDetailRepository
                .getInvestPackageDetail(pkId);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getFundNAV() {

        ArrayList<GmFundNAVEntity> list = (ArrayList<GmFundNAVEntity>) navRepository.getAll();
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getFundNAByFundID(String fid) {
        ArrayList<GmFundNAVEntity> list = (ArrayList<GmFundNAVEntity>) navRepository.getAllByFundID(fid);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> createFundInvestOptionally(GmFundNavRequest request) {
        NotifyEntity notify = notifyRepo.createFundInvestOptionally(request.getPv_custId(),
                request.getPv_amt1(),
                request.getPv_amt2(),
                request.getPv_amt3(),
                request.getPv_amt4(),
                request.getPv_amt5(),
                request.getPv_amt6(),
                request.getPv_amt7(),
                request.getPv_amt8(),
                request.getPv_amt9(),
                request.getPv_amt10(),
                request.getPv_amt11(),
                request.getPv_amt12(),
                request.getPv_amt13(),
                request.getPv_amt14());
        return response(toResult(notify));
    }

    @Override
    public ResponseEntity<?> getFundInvest(String cid) {
        ArrayList<FundInvestEntity> list = fundInvestRepository.getFundInvest(cid);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> getFundInvestDetail(String cid, String packageId) {
        ArrayList<FundInvestDetailEntity> list = fundInvestDetailRepository.getFundInvestDetail(cid, packageId);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> savePkgFundInfo(PkgFundInfoEntity request) {
        Date sDateF = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sDateF = sdf.parse(request.getFund_date());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String strSDate = formatter.format(sDateF);

        pkgFundInfoRepository.save(strSDate, request.getGrowth(), request.getF_code(), request.getPkg_id());
        return response(toResult("success"));
    }

    @Override
    public String checkSession(String session) {
        logger.info("[" + session + "] << checkSession >>");
        if (StringUtil.isEmty(session))
            throw new BusinessException("user or pass invalid");

        Optional<UserOnline> userOnline = userOnlineRepo.findBySession(session);
        if (!userOnline.isPresent())

            throw new BusinessException(ErrorCode.SESSION_TIMEOUT, ErrorCode.SESSION_TIMEOUT_DESCRIPTION);

        return userOnline.get().getCustId();
    }

    @Override
    public ResponseEntity<?> getTransHistory(String customerId) {

        return response(toResult(pkgFilterRepo.getTransHistory(customerId)));

    }

}
