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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import com.lendbiz.p2p.api.service.VNPTService;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.lendbiz.p2p.api.constants.Constants;
import com.lendbiz.p2p.api.constants.ErrorCode;
import com.lendbiz.p2p.api.entity.AccountAssetEntity;
import com.lendbiz.p2p.api.entity.AccountInput;
import com.lendbiz.p2p.api.entity.AccountInvest;
import com.lendbiz.p2p.api.entity.BankAccountEntity;
import com.lendbiz.p2p.api.entity.BankInfoEntity;
import com.lendbiz.p2p.api.entity.BaoVietEntity;
import com.lendbiz.p2p.api.entity.CfMast;
import com.lendbiz.p2p.api.entity.CoinEntity;
import com.lendbiz.p2p.api.entity.FirstPasswordEntity;
import com.lendbiz.p2p.api.entity.FundInvestDetailEntity;
import com.lendbiz.p2p.api.entity.FundInvestEntity;
import com.lendbiz.p2p.api.entity.FundListEntity;
import com.lendbiz.p2p.api.entity.GetEndRateRequest;
import com.lendbiz.p2p.api.entity.GmFundNAVEntity;
import com.lendbiz.p2p.api.entity.InvestAssets;
import com.lendbiz.p2p.api.entity.InvestPackageDetailEntity;
import com.lendbiz.p2p.api.entity.InvestPackageEntity;
import com.lendbiz.p2p.api.entity.NotificationsEntity;
import com.lendbiz.p2p.api.entity.NotificationsPushEntity;
import com.lendbiz.p2p.api.entity.NotifyEntity;
import com.lendbiz.p2p.api.entity.PkgFundInfoEntity;
import com.lendbiz.p2p.api.entity.PortfolioInvest;
import com.lendbiz.p2p.api.entity.RateConfigEntity;
import com.lendbiz.p2p.api.entity.RateEntity;
import com.lendbiz.p2p.api.entity.ReferenceIdentity;
import com.lendbiz.p2p.api.entity.RegisterEntity;
import com.lendbiz.p2p.api.entity.RelationEntity;
import com.lendbiz.p2p.api.entity.ResendOtpEntity;
import com.lendbiz.p2p.api.entity.StatementsEntity;
import com.lendbiz.p2p.api.entity.SumGrowthEntity;
import com.lendbiz.p2p.api.entity.TransferCodeEntity;
import com.lendbiz.p2p.api.entity.UpdateAccountEntity;
import com.lendbiz.p2p.api.entity.UserInfoEntity;
import com.lendbiz.p2p.api.entity.UserOnline;
import com.lendbiz.p2p.api.entity.VerifyAccountInput;
import com.lendbiz.p2p.api.entity.Version3Gang;
import com.lendbiz.p2p.api.exception.BusinessException;
import com.lendbiz.p2p.api.producer.ProducerMessage;
import com.lendbiz.p2p.api.repository.AccountAssetRepository;
import com.lendbiz.p2p.api.repository.AccountInvestRepository;
import com.lendbiz.p2p.api.repository.AccountNotificationsRepository;
import com.lendbiz.p2p.api.repository.BankAccountRepository;
import com.lendbiz.p2p.api.repository.BankRepository;
import com.lendbiz.p2p.api.repository.BaoVietRepo;
import com.lendbiz.p2p.api.repository.CfMastRepository;
import com.lendbiz.p2p.api.repository.CoinRepo;
import com.lendbiz.p2p.api.repository.ContractInfoRepository;
import com.lendbiz.p2p.api.repository.FirstPasswordRepository;
import com.lendbiz.p2p.api.repository.FundInvestDetailRepository;
import com.lendbiz.p2p.api.repository.FundInvestRepository;
import com.lendbiz.p2p.api.repository.FundListRepository;
import com.lendbiz.p2p.api.repository.GetRateRepository;
import com.lendbiz.p2p.api.repository.GetReferenceRepo;
import com.lendbiz.p2p.api.repository.InvestAssetsRepository;
import com.lendbiz.p2p.api.repository.InvestPackageDetailRepository;
import com.lendbiz.p2p.api.repository.InvestPackageRepository;
import com.lendbiz.p2p.api.repository.NAVRepository;
import com.lendbiz.p2p.api.repository.NinePayDepositRepo;
import com.lendbiz.p2p.api.repository.NotifyRepo;
import com.lendbiz.p2p.api.repository.PackageFilterRepository;
import com.lendbiz.p2p.api.repository.PayRepo;
import com.lendbiz.p2p.api.repository.PkgFundInfoRepository;
import com.lendbiz.p2p.api.repository.PortfolioRepository;
import com.lendbiz.p2p.api.repository.ProductGMRepository;
import com.lendbiz.p2p.api.repository.PushRepository;
import com.lendbiz.p2p.api.repository.RateConfigRepo;
import com.lendbiz.p2p.api.repository.RateRepo;
import com.lendbiz.p2p.api.repository.RegisterRepository;
import com.lendbiz.p2p.api.repository.RelationRepo;
import com.lendbiz.p2p.api.repository.ResendOtpRepository;
import com.lendbiz.p2p.api.repository.StatementsRepository;
import com.lendbiz.p2p.api.repository.SumGrowthRepository;
import com.lendbiz.p2p.api.repository.TermRepo;
import com.lendbiz.p2p.api.repository.TransFerCodeRepo;
import com.lendbiz.p2p.api.repository.UpdateAccountRepository;
import com.lendbiz.p2p.api.repository.UserInfoRepository;
import com.lendbiz.p2p.api.repository.UserOnlineRepository;
import com.lendbiz.p2p.api.repository.VerifyAccountRepository;
import com.lendbiz.p2p.api.repository.Version3GangRepository;
import com.lendbiz.p2p.api.request.BearRequest;
import com.lendbiz.p2p.api.request.CashOutRequest;
import com.lendbiz.p2p.api.request.CreatePolicyPartnerRq;
import com.lendbiz.p2p.api.request.GmFundNavRequest;
import com.lendbiz.p2p.api.request.InsuranceRequest;
import com.lendbiz.p2p.api.request.LoginRequest;
import com.lendbiz.p2p.api.request.PkgSumFundRequest;
import com.lendbiz.p2p.api.request.ReqJoinRequest;
import com.lendbiz.p2p.api.request.SetAccountPasswordRequest;
import com.lendbiz.p2p.api.request.SignContractRequestV2;
import com.lendbiz.p2p.api.request.UpdateAccountRequest;
import com.lendbiz.p2p.api.request.UpdateBiometricRequest;
import com.lendbiz.p2p.api.request.UpdateNotificationsRequest;
import com.lendbiz.p2p.api.response.BaseResponse;
import com.lendbiz.p2p.api.response.PkgFundDetail;
import com.lendbiz.p2p.api.response.PkgFundResponse;
import com.lendbiz.p2p.api.response.SignPdfResponse;
import com.lendbiz.p2p.api.service.FilesStorageService;
import com.lendbiz.p2p.api.service.SavisService;
import com.lendbiz.p2p.api.service.UserService;
import com.lendbiz.p2p.api.utils.StringUtil;
import com.lendbiz.p2p.api.utils.Utils;

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

    @Qualifier("bankAccountRepository")
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    FundInvestRepository fundInvestRepository;

    @Autowired
    UpdateAccountRepository accountRepository;

    @Qualifier("cfMastRepository")
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

    public String getCustId(List<CfMast> lstCfmast) {
        List<CfMast> newLstCfmast = new ArrayList<>();
        String custId = null;
        if (lstCfmast.size() > 1) {
            lstCfmast.forEach((n) -> {
                try {
                    if (n.getStatus().equalsIgnoreCase("A")) {
                        newLstCfmast.add(n);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            if (newLstCfmast.size() > 0) {
                custId = newLstCfmast.get(0).getCustid();
            } else {
                custId = lstCfmast.get(0).getCustid();
            }

        } else if (lstCfmast.size() == 1) {
            custId = lstCfmast.get(0).getCustid();
        }
        return custId;
    }

    @Qualifier("version3GangRepository")
    @Autowired
    Version3GangRepository version3GangRepository;

    @Override
    public ResponseEntity<?> checkVersion3GangOutdated(String version) {
        // List<Object> response;
        Version3Gang verConfig = version3GangRepository.getVersion();

        if (verConfig.getVersion().equalsIgnoreCase(version)) {
            return response(toResult(verConfig.getVersion() + " " + version));
        } else {
            throw new BusinessException(ErrorCode.VERSION_OUTDATED, ErrorCode.VERSION_OUTDATED_DESCRIPTION);
        }

    }

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
        List<CfMast> lstCfmast = cfMastRepository.findByMobileSms(loginRequest.getUsername());

        String custId = getCustId(lstCfmast);

        UserOnline user = userOnlineRepo.getUserOnline(custId);
        if (user != null) {
            if (user.getNumberOffail() > 4) {

                Date now = new Date();
                long diff = (now.getTime() - user.getLastChange().getTime()) / 1000 / 60;

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
                    registerRepository.register(reqJoinRequest.getMobile(),
                            reqJoinRequest.getDeviceId(), custId);
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
        CfMast cfmast;
        List<CfMast> lstCfmast = cfMastRepository.findByMobileSms(reqJoinRequest.getMobile());
        String custId = getCustId(lstCfmast);
        if (custId != null) {
            cfmast = cfMastRepository.findByCustid(custId).get();
            if (cfmast.getStatus().equalsIgnoreCase("P")) {
                try {

                    if (userOnlineRepo.checkAccountMappingExist(custId) == 0) {

                        return response(
                                toResult(registerRepository.register(reqJoinRequest.getMobile(),
                                        reqJoinRequest.getDeviceId(), custId)));

                    } else {
                        ResendOtpEntity resOtp = otpRepository.resendOtp(reqJoinRequest.getMobile(),
                                custId);
                        RegisterEntity regResendOtp = new RegisterEntity();
                        regResendOtp.setAccountStatus(cfmast.getStatus());
                        regResendOtp.setCode(resOtp.getCode());
                        regResendOtp.setCustId(custId);
                        regResendOtp.setErrorCode(0);
                        return response(
                                toResult(regResendOtp));
                    }

                } catch (Exception e) {
                    throw new BusinessException(Constants.FAIL, e.getMessage());
                }

            } else {
                // List<Object> response = (ArrayList) pkgFilterRepo.reqJoin(reqJoinRequest);
                // return response(toResult(response.get(0)));
                // String custId = getCustId(lstCfmast);
                RegisterEntity regEntity = registerRepository.register(reqJoinRequest.getMobile(),
                        reqJoinRequest.getDeviceId(), custId);

                if (regEntity.getErrorCode() == 1) {
                    throw new BusinessException(Constants.FAIL, regEntity.getCustId());
                } else {
                    return response(toResult(regEntity));
                }

            }
        } else {
            // List<Object> response = (ArrayList) pkgFilterRepo.reqJoin(reqJoinRequest);
            // return response(toResult(response.get(0)));
            // String custId = getCustId(lstCfmast);
            RegisterEntity regEntity = registerRepository.register(reqJoinRequest.getMobile(),
                    reqJoinRequest.getDeviceId(), custId);

            if (regEntity.getErrorCode() == 1) {
                throw new BusinessException(Constants.FAIL, regEntity.getCustId());
            } else {
                return response(toResult(regEntity));
            }

        }

    }

    @Override
    public ResponseEntity<?> resendOtp(ReqJoinRequest reqJoinRequest) {
        List<CfMast> lstCfmast = cfMastRepository.findByMobileSms(reqJoinRequest.getMobile());
        String custId = getCustId(lstCfmast);
        try {
            ResendOtpEntity entity = otpRepository.resendOtp(reqJoinRequest.getMobile(), custId);

            if (entity.getCode().equals("0")) {
                throw new BusinessException(Constants.FAIL, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
            }

            return response(toResult(entity));
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
            String userPhone = user.getMobileSms();
            String urlAvatar = "";
            String directPathAvatar = "images/" + user.getCustid() + "/avatar/";
            String lbcAccount = "4585326647075";
            String lbcName = "CONG TY CO PHAN LENDBIZ CAPITAL";
            String ruleAgreementUrl = "https://bagang.lendbiz.vn/lendbiz/view/1/" + userPhone;
            String contractUrl = "https://bagang.lendbiz.vn/lendbiz/view/2/" + userPhone;
            String inviteFriendTitle = "40.000 VND";
            String inviteFriendCash = "10.000 VND";
            String inviteFriendDescription = "Mời bạn bè sử dụng 3Gang để cùng nhận thưởng. Với mỗi tài khoản được mở thành công và phát sinh giao dịch, bạn sẽ nhận được 40.000 VND. Đặc biệt, người được giới thiệu cũng nhận thêm 10.000 VND vào tài khoản 3Gang ngay sau khi phát sinh giao dịch tích lũy đầu tiên.";
            String[] xuTitle = "Bạn sẽ nhận được sau một tháng kể từ ngày phát sinh giao dịch:|Nhà đầu tư có số dư tích lũy có kỳ hạn sẽ được tặng xu vào ngày sinh nhật với điều kiện:"
                    .split("\\|");
            String[] xuDescription = "+ 1 xu với mỗi 100.000 VND tích lũy có kỳ hạn.|+ 2 xu với mỗi 100.000 VND tích lũy có kỳ hạn vào ngày sinh nhật của bạn.|+ Nhận 20 xu nếu số dư tích lũy có kỳ hạn từ 1.000.000 – 10.000.000 VND|+ Nhận 100 xu nếu số dư tích lũy có kỳ hạn từ 10.000.000 – 50.000.000 VND|+ Nhận 200 xu nếu số dư tích lũy có kỳ hạn trên 50.000.000 VND"
                    .split("\\|");
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
            map.put("lbcAccount", lbcAccount);
            map.put("lbcName", lbcName);
            map.put("ruleAgreementUrl", ruleAgreementUrl);
            map.put("contractUrl", contractUrl);
            map.put("inviteFriendTitle", inviteFriendTitle);
            map.put("inviteFriendCash", inviteFriendCash);
            map.put("inviteFriendDescription", inviteFriendDescription);
            map.put("xuTitle", xuTitle);
            map.put("xuDescription", xuDescription);

            return response(toResult(map));

        } catch (Exception e) {
            throw new BusinessException(Constants.FAIL, ErrorCode.NO_DATA_DESCRIPTION);
        }
    }

    @Override
    public ResponseEntity<?> setAccountPassword(SetAccountPasswordRequest setAccountPasswordRequest) {
        FirstPasswordEntity entity = new FirstPasswordEntity();
        List<CfMast> lstCfmast = cfMastRepository.findByMobileSms(setAccountPasswordRequest.getCustId());
        String custId = getCustId(lstCfmast);
        if (custId != null) {
            try {
                entity = firstPasswordRepository.firstPassword(custId,
                        passwordEncoder.encode(setAccountPasswordRequest.getPassword()));

                logger.info("passwordEncoder.encode(setAccountPasswordRequest.getPassword()): "
                        + passwordEncoder.encode(setAccountPasswordRequest.getPassword()));
            } catch (Exception e) {
                throw new BusinessException(Constants.FAIL, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
            }
        } else {
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

            if (entity.equals("1")) {
                throw new BusinessException(Constants.FAIL, ErrorCode.UNKNOWN_ERROR_DESCRIPTION);
            } else {
                if (contractInfoRepository.countByCustId(updateRequest.getCustId()) > 0) {
                    contractInfoRepository.update(updateRequest.getCustId(), 20);
                } else {
                    contractInfoRepository.create(updateRequest.getCustId() + updateRequest.getIdCode(),
                            updateRequest.getCustId(), "", "", "3GANG", "Hợp đồng",
                            updateRequest.getCustId() + updateRequest.getIdCode());
                }
            }

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
            logger.info(ex.getMessage());
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

            logger.info("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            logger.info("httpResponse: " + httpResponse);

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
            logger.info("jsonResponse:\n" + jsonResponse);

            try {
                Firestore db = FirestoreClient.getFirestore();
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("change", String.valueOf(System.currentTimeMillis()));

                ApiFuture<WriteResult> future = db.collection("balance").document(request.getCustId())
                        .set(data);
            } catch (Exception t) {
                pushRepository.updateNotifications(101, request.getCustId(), request.getId());
                logger.info("Error!" + t.getMessage());
            }

            logger.info("successfully!");

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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ContractInfoRepository contractInfoRepository;

    @Autowired
    FilesStorageService filesStorageService;

    @Override
    public void autoSignContract() {
        List<CfMast> lstCfmast = cfMastRepository.findAllActive();
        String inSourceDoc = "contracts/default/dkdv.docx";
        String outputDocs = "contracts/default/output.docx";
        MultipartFile mckFile = null;
        String docNo = UUID.randomUUID().toString();

        if (lstCfmast.size() > 0) {
            logger.info("Start auto sign! ~>" + docNo);

            for (CfMast cfMast : lstCfmast) {
                contractInfoRepository.update(cfMast.getCustid(), 21);
            }

            for (CfMast cfMast : lstCfmast) {
                BankAccountEntity bank = new BankAccountEntity();
                try {
                    filesStorageService.initContracts(cfMast.getMobileSms());
                    try {
                        bank = bankAccountRepository.getUserBankAccount(cfMast.getCustid());
                        if (bank == null) {
                            bank = new BankAccountEntity();
                            bank.setBankAccount("[Số tài khoản]");
                            bank.setBankName("[Ngân hàng]");
                        }
                    } catch (Exception e) {
                        bank.setBankAccount("[Số tài khoản]");
                        bank.setBankName("[Ngân hàng]");
                    }

                    outputDocs = "contracts/sign/" + cfMast.getMobileSms() + "/hopdong_output.docx";
                    Utils.fillDataToContract(cfMast, bank, inSourceDoc, outputDocs);

                    File inputWord = new File(outputDocs);
                    FileInputStream inputStream = new FileInputStream(inputWord);
                    mckFile = new MockMultipartFile("hd", inputWord.getName(), "text/plain",
                            IOUtils.toByteArray(inputStream));

                    logger.info("---------Start call converter---------------");
                    final String uri = "http://45.117.83.201:9013/esign/v1.0/convert-pdf";
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    headers.set("requestId", docNo);

                    MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
                    ByteArrayResource contentsAsResource = Utils.convertFile(mckFile);
                    multiValueMap.add("file", contentsAsResource);
                    multiValueMap.add("output", docNo + ".pdf");

                    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multiValueMap, headers);
                    ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);

                    // mapping response
                    // OutputStream outputStream;
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root;
                    if (responseEntityStr.getStatusCodeValue() == 200) {
                        try {
                            root = mapper.readTree(responseEntityStr.getBody());
                            byte[] data;
                            try {
                                data = root.get("data").binaryValue();

                                FileUtils.writeByteArrayToFile(
                                        new File("contracts/sign/" + cfMast.getMobileSms() + "/hopdong_3gang.pdf"),
                                        data);

                                // FileUtils.savePdf(pathOutput, data);

                                if (signContract("contracts/sign/" + cfMast.getMobileSms() + "/hopdong_3gang.pdf",
                                        "contracts/sign/" + cfMast.getMobileSms() + "/signed_3gang.pdf",
                                        cfMast)) {
                                    try {
                                        File deleteInputFile = new File(
                                                "contracts/sign/" + cfMast.getMobileSms() + "/hopdong_output.docx");
                                        File deleteGeneratedFile = new File(
                                                "contracts/sign/" + cfMast.getMobileSms() + "/hopdong_3gang.pdf");
                                        deleteInputFile.delete();
                                        deleteGeneratedFile.delete();

                                    } catch (Exception e) {
                                        logger.info(e.getMessage());
                                    }

                                    contractInfoRepository.update(cfMast.getCustid(), 22);

                                    logger.info("Success generated contract PDF!");
                                } else {
                                    contractInfoRepository.update(cfMast.getCustid(), 20);
                                }

                            } catch (IOException e) {
                                contractInfoRepository.update(cfMast.getCustid(), 20);
                                e.printStackTrace();
                            }

                        } catch (JsonProcessingException e) {
                            contractInfoRepository.update(cfMast.getCustid(), 20);
                            throw new BusinessException(ErrorCode.FAILED_TO_JSON, ErrorCode.FAILED_TO_JSON_DESCRIPTION);
                        }

                    } else {
                        contractInfoRepository.update(cfMast.getCustid(), 20);
                    }

                } catch (Exception e) {
                    contractInfoRepository.update(cfMast.getCustid(), 20);
                    logger.info(e.getMessage());
                }
            }
            logger.info("End auto sign! ~>" + docNo);
        }
    }

    @Autowired
    SavisService savisService;

    public boolean signContract(String sourcePdf, String outputSigned, CfMast cfMast) {
        logger.info("[" + sourcePdf + "] << signContract >>");
        try {
            ArrayList<String> positions = new ArrayList<String>();
            SignContractRequestV2 request = new SignContractRequestV2();
            SignContractRequestV2 signByLendBizRequest = new SignContractRequestV2();

            String direct = "";

            MultipartFile contract = null;
            File pdf = null;
            request.setSignedBy(cfMast.getFullName());

            // Chu ky thu 1 cua khach hang
            request.setPage("8");
            request.setLlx("100");
            request.setLly("550");
            request.setUrx("250");
            request.setUry("470");
            request.setDetail("1,6");
            request.setReason("Đồng ý ký hợp đồng");
            request.setLocation("Việt Nam");
            request.setIsLBC("lendbiz");
            request.setType("client");
            String position = request.toString();
            positions.add(position);
            request.setPositions(positions);

            // Chu ky thu 2 cua cong ty lendbiz
            signByLendBizRequest.setPage("8");
            signByLendBizRequest.setLlx("340");
            signByLendBizRequest.setLly("550");
            signByLendBizRequest.setUrx("500");
            signByLendBizRequest.setUry("470");
            signByLendBizRequest.setDetail("1,6");
            signByLendBizRequest.setReason("Đồng ý ký hợp đồng");
            signByLendBizRequest.setLocation("Việt Nam");
            signByLendBizRequest.setIsLBC("lbc");
            signByLendBizRequest.setType("org");
            positions = new ArrayList<>();
            String positionLendBiz = signByLendBizRequest.toString();
            positions.add(positionLendBiz);
            signByLendBizRequest.setPositions(positions);

            pdf = new File(sourcePdf);

            /** Call api sign contract **/

            FileInputStream input = new FileInputStream(pdf);
            contract = new MockMultipartFile("hd", pdf.getName(), "text/plain", IOUtils.toByteArray(input));

            // Ky khach hang
            Optional<SignPdfResponse> otpFirstSignResult = savisService.signContract(contract, request);
            contract = new MockMultipartFile("hdauto", pdf.getName(), "text/plain", otpFirstSignResult.get().getData());
            // Ky LBC
            Optional<SignPdfResponse> otpSignResult = savisService.signContract(contract, signByLendBizRequest);

            logger.info("[Sign pdf] direct {}", direct);
            filesStorageService.saveContract(otpSignResult.get().getData(), outputSigned);
        } catch (Exception e) {
            contractInfoRepository.update(cfMast.getCustid(), 20);
            logger.info(e.getMessage());
            return false;
        }

        return true;
    }
}
