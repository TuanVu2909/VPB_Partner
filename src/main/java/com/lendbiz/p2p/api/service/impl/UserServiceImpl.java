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

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.*;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.producer.ProducerMessage;
import com.lendbiz.p2p.api.repository.*;
import com.lendbiz.p2p.api.request.*;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.PkgFundDetail;
import com.lendbiz.p2p.api.response.PkgFundResponse;
import com.lendbiz.p2p.api.service.UserService;
import com.lendbiz.p2p.api.utils.StringUtil;
import com.lendbiz.p2p.api.utils.Utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.xpath.operations.Number;
import org.json.JSONObject;
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
    SumGrowthRepository sumGrowthRepository;
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
    AccountNotificationsRepository accountNotificationsRepository;
    @Autowired
    StatementsRepository statementsRepository;
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
    GetRateRepository getRateRepository;
    @Autowired
    TransFerCodeRepo transFerCodeRepo;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    BaoVietRepo baoVietRepo;
    @Autowired
    InvestPackageRepository investPackageRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    FundInvestRepository fundInvestRepository;

    @Autowired
    UpdateAccountRepository accountRepository;

    @Autowired
    CfMastRepository cfMastRepository;

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    ResendOtpRepository otpRepository;

    @Autowired
    VerifyAccountRepository verifyAccountRepository;

    @Autowired
    GetReferenceRepo getReferenceRepo;

    @Autowired
    private ProducerMessage producerMessage;

    @Override
    public ResponseEntity<?> checkExistedAccount(LoginRequest loginRequest) {
        // List<Object> response;
        UserOnline user = userOnlineRepo.getUserOnline(loginRequest.getUsername());
        if (user != null) {
            return response(toResult(user.getCustId()));
        } else {
            throw new BusinessException(ErrorCode.FAIL_LOGIN, ErrorCode.FAIL_LOGIN_DESCRIPTION);
        }

    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        // List<Object> response;

        UserOnline user = userOnlineRepo.getUserOnline(loginRequest.getUsername());
        if (user != null) {
            if (user.getNumberOffail() > 4) {

                Date now = new Date();
                long diff = (now.getTime() - user.getLastChange().getTime()) / 1000 / 60;

                System.out.println(diff);

                if (diff <= 15) {
                    throw new BusinessException(ErrorCode.ACCOUNT_LOCKED,
                            ErrorCode.ACCOUNT_LOCKED_DESCRIPTION);
                } else {
                    userOnlineRepo.resetFail(user.getCustId());
                }

            }

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPwd())) {
                userOnlineRepo.updateNumberFail(user.getCustId());
                if (user.getNumberOffail() == 4) {
                    throw new BusinessException(ErrorCode.ACCOUNT_LOCKED,
                            ErrorCode.ACCOUNT_LOCKED_DESCRIPTION);
                }
                throw new BusinessException(ErrorCode.FAIL_PASSWORD_LOGIN, ErrorCode.FAIL_PASSWORD_LOGIN_DESCRIPTION);
            } else {
                userOnlineRepo.resetFail(user.getCustId());
                if (userOnlineRepo.checkAccountMappingExist(user.getCustId()) == 0) {
                    ReqJoinRequest reqJoinRequest = new ReqJoinRequest();
                    reqJoinRequest.setMobile(loginRequest.getUsername());
                    reqJoinRequest.setDeviceId(loginRequest.getDeviceId());
                    pkgFilterRepo.reqJoin(reqJoinRequest);
                }

                if (!loginRequest.getDeviceId().equalsIgnoreCase(user.getDeviceId())) {
                    userOnlineRepo.updateDeviceIdBioState(0, user.getCustId(), loginRequest.getDeviceId());
                }

            }

            return response(toResult(user.getCustId()));
        } else {
            throw new BusinessException(ErrorCode.FAIL_LOGIN, ErrorCode.FAIL_LOGIN_DESCRIPTION);
        }

    }

    @Override
    public ResponseEntity<?> register(ReqJoinRequest reqJoinRequest) {

        List<CfMast> lstCfmast = cfMastRepository.findByMobileSms(reqJoinRequest.getMobile());
        if (lstCfmast.size() > 0 && lstCfmast.get(0).getStatus().equalsIgnoreCase("P")) {
            try {
                return response(toResult(otpRepository.resendOtp(reqJoinRequest.getMobile())));
            } catch (Exception e) {
                throw new BusinessException(Constants.FAIL, e.getMessage());
            }

        } else {
            // List<Object> response = (ArrayList) pkgFilterRepo.reqJoin(reqJoinRequest);
            // return response(toResult(response.get(0)));
            RegisterEntity regEntity = registerRepository.register(reqJoinRequest.getMobile(),
                    reqJoinRequest.getDeviceId());

            if (regEntity.getErrorCode() == 1) {
                throw new BusinessException(Constants.FAIL, regEntity.getCustId());
            } else {
                return response(toResult(regEntity));
            }

        }

    }

    @Override
    public ResponseEntity<?> resendOtp(ReqJoinRequest reqJoinRequest) {

        try {
            return response(toResult(otpRepository.resendOtp(reqJoinRequest.getMobile())));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> verifyAcc(VerifyAccountInput input) {
        try {
            return response(toResult(verifyAccountRepository.verify(input.getCustId(), input.getVerifyCode())));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.ERROR_500_DESCRIPTION);
        }
    }

    @Override
    public ResponseEntity<?> updateBioState(UpdateBiometricRequest request) {
        try {
            return response(toResult(userOnlineRepo.updateOnlyBioState(request.getState(), request.getCustId())));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.ERROR_500_DESCRIPTION);
        }
    }

    @Override
    public ResponseEntity<?> getUserInfo(String custId) {
        try {
            UserInfoEntity user = userInfoRepository.getUserInfo(custId);
            BankAccountEntity bank = bankAccountRepository.getUserBankAccount(custId);
            String urlAvatar = "";
            String directPathAvatar = "images/" + user.getCustid() + "/avatar/";

            try {
                File folder = new File(directPathAvatar);
                for (final File fileEntry : folder.listFiles()) {
                    if (fileEntry.isDirectory()) {
                        urlAvatar = "";
                    } else {
                        urlAvatar = "https://bagang.lendbiz.vn/lendbiz/avatar/" + user.getCustid() + "/avatar/"
                                + FilenameUtils.removeExtension(fileEntry.getName()) + "/"
                                + FilenameUtils.getExtension(fileEntry.getName());
                    }
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            if (bank == null) {
                bank = new BankAccountEntity();
                bank.setBankAcName("------");
                bank.setBankAccount("------");
                bank.setBankName("------");
            }

            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("bank", bank);
            map.put("avatar", urlAvatar);

            return response(toResult(map));

        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        }
    }

    @Override
    public ResponseEntity<?> setAccountPassword(SetAccountPasswordRequest setAccountPasswordRequest) {
        FirstPasswordEntity entity;
        try {
            entity = firstPasswordRepository.firstPassword(setAccountPasswordRequest.getCustId(),
                    passwordEncoder.encode(setAccountPasswordRequest.getPassword()));

            System.out.println("passwordEncoder.encode(setAccountPasswordRequest.getPassword()): "
                    + passwordEncoder.encode(setAccountPasswordRequest.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
        }

        return response(toResult(entity));
    }

    @Override
    public ResponseEntity<?> updateAccountInfo(UpdateAccountRequest updateRequest) {
        UpdateAccountEntity entity;
        try {
            entity = accountRepository.updateAccount(updateRequest.getCustId(), updateRequest.getFullName(),
                    updateRequest.getIdCode(), updateRequest.getSex(), updateRequest.getDob(),
                    updateRequest.getAddress(), updateRequest.getIdExp(), updateRequest.getIdDate(),
                    updateRequest.getIdPlace(),
                    updateRequest.getBankName(),
                    updateRequest.getBankAccount(),
                    updateRequest.getBankAccountName(),
                    updateRequest.getBankCode());
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
        }

        return response(toResult(entity));
    }

    @Override
    public ResponseEntity<?> updateBankAccountInfo(UpdateAccountRequest updateRequest) {
        UpdateAccountEntity entity;
        try {
            entity = accountRepository.updateBankAccount(updateRequest.getCustId(),
                    updateRequest.getBankName(),
                    updateRequest.getBankAccount(),
                    updateRequest.getBankAccountName(),
                    updateRequest.getBankCode());
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
                    Float.valueOf(input.getRate()),
                    input.getAmt(),
                    input.getContractId(), input.getPayType());
            if (!notify.getPStatus().equals("01")) {
                throw new BusinessException(notify.getPStatus(), notify.getDes());
            }
            return response(toResult(notify));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
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
    public ResponseEntity<?> getAccountNotifications(String custId) {
        try {
            List<NotificationsEntity> list = accountNotificationsRepository.getNotifications(custId);
            if (list.size() == 0)
                throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
            return response(toResult(list));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> updateNotifications(UpdateNotificationsRequest request) {
        try {

            return response(toResult(accountNotificationsRepository.updateNotifications(request.getStatus(),
                    request.getCustId(), request.getId())));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getStatements(String custId) {
        try {
            List<StatementsEntity> list = statementsRepository.getStatements(custId);
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
        NotifyEntity notify = notifyRepo.updateReferenceId(input.getCustId(), input.getRefId());
        if (!notify.getPStatus().equals("01")) {
            throw new BusinessException(notify.getPStatus(), notify.getDes());
        }
        return response(toResult(notify));
    }

    @Override
    public ResponseEntity<?> getRefList(String cif) {
        try {
            ArrayList<ReferenceIdentity> list = getReferenceRepo.getReferenceId(cif);
            int count = getReferenceRepo.checkRefExisted(cif);

            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("count", count);
            return response(toResult(map));
        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
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
        NotifyEntity notify = notifyRepo.createInsurance(insuranceRequest.getPv_custId(),
                insuranceRequest.getPv_packageId(),
                insuranceRequest.getPv_startDate(),
                String.valueOf(insuranceRequest.getPv_fee()),
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
                insuranceRequest.getPv_requireId(),
                insuranceRequest.getPv_insuredPersonFullName(),
                insuranceRequest.getPv_insuredPersonBirthDate(),
                insuranceRequest.getPv_insuredPersonGender(),
                insuranceRequest.getPv_insuredPersonIdNumber(),
                insuranceRequest.getPv_insuredPersonMobile(),
                insuranceRequest.getPv_insuredPersonEmail(),
                insuranceRequest.getPv_insuredPersonAddress(),
                insuranceRequest.getPv_ParentInsuranceCode(),
                insuranceRequest.getPv_InsuredRelationId(),
                insuranceRequest.getPv_insuredPersonNationality(),
                insuranceRequest.getPv_isOutPatientFee(),
                insuranceRequest.getPv_isAccidentFee(),
                insuranceRequest.getPv_isLifeFee(),
                insuranceRequest.getPv_isDentistryFee(),
                insuranceRequest.getPv_isPregnantFee());
        CreatePolicyPartnerRq request = new CreatePolicyPartnerRq();
        request.setGuaranteeCard("0");
        request.setSoNguoiThamGia("1");
        request.setContactAddress("71 nsl");
        request.setContactCategoryType("PERSON");
        request.setContactCode("15606");
        request.setContactDob(insuranceRequest.getPv_beneficiaryBirthDate());
        request.setContactEmail("1");

        return response(toResult(notify));

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
    public ResponseEntity<?> endBear(String cid, String documentNo) {
        NotifyEntity notify = notifyRepo.endBear(cid, documentNo);
        if (!notify.getPStatus().equals("01")) {
            throw new BusinessException(notify.getPStatus(), notify.getDes());
        }
        return response(toResult(notify));
    }

    @Override
    public ResponseEntity<?> getEndRate(GetEndRateRequest request) {
        try {
            if (request.getCalType() == 1) {
                float rate = getRateRepository.callIntNoTerm(request.getAmount(), request.getPayType(),
                        Utils.convertStringToSqlDate(request.getStartDate()));

                return response(toResult(rate));
            } else {
                float res = getRateRepository.callInt(request.getInvestId());

                return response(toResult(res));
            }

        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, e.getMessage());
        }
    }

    // @Override
    // public ResponseEntity<?> getEndRate(GetEndRateRequest request) {
    // try {
    // // GetEndRateEntity rate = getRateRepository.getRateCal(request.getAmount(),
    // // request.getPayType(),
    // // request.getStartDate());

    // float res = getRateRepository.callInt(7517);

    // return response(toResult(res));

    // } catch (Exception e) {
    // throw new BusinessException(Constants.FAIL, e.getMessage());
    // }
    // }

    @Override
    public ResponseEntity<?> getFundInvestDetail(String cid, String packageId) {
        ArrayList<FundInvestDetailEntity> list = fundInvestDetailRepository.getFundInvestDetail(cid, packageId);
        if (list.size() == 0)
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        return response(toResult(list));
    }

    @Override
    public ResponseEntity<?> savePkgFundInfo(PkgSumFundRequest request) {
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
        notifyRepo.saveSumGrowthNavDaily(request.getSum(), strSDate, request.getPkg_id());

        request.getFunNavRequests().forEach((n) -> {
            notifyRepo.saveNavDaily(n.getF_code(), n.getGrowth(), strSDate, request.getPkg_id());
        });

        return response(toResult("success"));
    }

    @Override
    public ResponseEntity<?> getPkgFundInfo() {
        ArrayList<PkgFundInfoEntity> list = pkgFundInfoRepository.findByFund_date();
        ArrayList<SumGrowthEntity> list2 = (ArrayList<SumGrowthEntity>) sumGrowthRepository.findAll();
        ArrayList<PkgFundResponse> list3 = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            PkgFundResponse pkgFundResponse = new PkgFundResponse();
            pkgFundResponse.setFund_date(list2.get(i).getFund_date());
            pkgFundResponse.setSum(list2.get(i).getSum());
            pkgFundResponse.setPkg_id(list2.get(i).getPkg_id());
            ArrayList<PkgFundDetail> details = new ArrayList<>();
            list.forEach((n) -> {
                if (n.getFund_date().equals(pkgFundResponse.getFund_date())
                        && n.getPkg_id().equals(pkgFundResponse.getPkg_id())) {
                    PkgFundDetail pkgFundDetail = new PkgFundDetail();
                    pkgFundDetail.setPkg_id(n.getPkg_id());
                    pkgFundDetail.setF_code(n.getF_code());
                    pkgFundDetail.setGrowth(n.getGrowth());
                    details.add(pkgFundDetail);
                }

            });
            pkgFundResponse.setPkgFundDetail(details);
            list3.add(pkgFundResponse);
        }
        return response(toResult(list3));
    }

    @Autowired
    NinePayDepositRepo ninePayDepositRepo;

    @Override
    public ResponseEntity<?> genTransferCode(String amount, String cif) {
        TransferCodeEntity entity = transFerCodeRepo.genTransferCode(cif);
        if (entity == null) {
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        }
        // ninePayDepositRepo.insertApiTrans(amount, entity.getTransferCode());
        return response(toResult(entity));
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

    @Autowired
    PushRepository pushRepository;

    public String createNotificationOneSignal(NotificationsPushEntity request) {
        String jsonResponse = "";
        try {
            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic ODVmMjdlMGUtODFlYS00OWZmLTg2NmQtMzlkMDg4YjNmODdl");
            con.setRequestMethod("POST");

            // CreateNotificationOneSignalRequest requestObj = new
            // CreateNotificationOneSignalRequest();
            // requestObj.setIncludePlayerIds(request.getDeviceId());

            String strJsonBody = "{" + "\"app_id\": \"e4446f23-9222-4e5d-b51e-ac5ea0f4d956\","
                    + "\"include_player_ids\": [\"" + request.getDeviceId() + "\"],"
                    + "\"data\": {\"id\": \"" + request.getId() + "\", \"investid\": \"" + request.getInvestId()
                    + "\", \"custid\": \"" + request.getCustId() + "\", \"type\": \"" + request.getType() + "\"},"
                    + "\"headings\": {\"en\": \""
                    + request.getTitle() + "\"},"
                    + "\"contents\": {\"en\": \""
                    + request.getMessage() + "\"}" + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("/A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("/A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            pushRepository.updateNotifications(1, request.getCustId(), request.getId());
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch (Throwable t) {
            pushRepository.updateNotifications(99, request.getCustId(), request.getId());
            t.printStackTrace();
        }

        return jsonResponse;
    }

    @Override
    public ResponseEntity<?> withdraw(CashOutRequest request) {
        try {
            JSONObject jsonObject = new JSONObject(request);
            producerMessage.sendCashOu3Gang(jsonObject.toString());
            return response(toResult("OK"));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

}
