package com.lendbiz.p2p.api.model.listbvaddbasevm;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class BvPartner {
    private ReceiverUser receiverUser;
    private InvoiceInfo invoiceInfo;
    private String agreementID;
    private String contactCode;
    private String contactName;
    private String contactDob;
    private String contactCategoryType;
    private String contactAddress;
    private String receiveMethod;
    private String gycbhNumber;
    private String lineID;
    private String statusPolicy;
    private String departmentID;
    private String contactPhone;
    private String contactEmail;
    private String contactIDNumber;
    private String contactCif;
    private SaleToEmp saleToEmp;
    private String checkOTP;
    private String kenhPhanPhoi;
    private long confirmMethod;
    private List<Object> gycFiles;
    private String insuranceTarget;
    private long promoValue;
    private long promoPercent;
    private String thoihanbhTu;
    private String thoihanbhDen;
    private String nguoiycName;
    private String nguoiycNgaysinh;
    private String nguoiycCmnd;
    private List<ListBvgAddBaseVM> listBvgAddBaseVM;
    private String ttskCheck;
    private String q1;
    private String q1QuestionID;
    private String q1Question;
    private String q1QuestionDesc;
    private String q1QuestionNote;
    private String q2;
    private String q2QuestionID;
    private String q2Question;
    private String q2QuestionDesc;
    private String q2QuestionNote;
    private String q3;
    private String q3QuestionID;
    private String q3Question;
    private String q3QuestionDesc;
    private String q3QuestionNote;
    private String q4;
    private String q4QuestionID;
    private String q4Question;
    private String q4QuestionDesc;
    private String q4QuestionNote;
    private boolean hasNguoinhantien;
    private String showGiamDinh;
    private long totalPremium;
    private long netPremium;
    private long changePremium;
    private boolean hasNguoithuhuong;
    private boolean hasTThoadonGTG;
    private boolean hasGks;
    private boolean isShowDgrr;
    private long guaranteeCard;
    private long totalPremiumDGRR;
    private List<File> attachFiles;
    private long tangGiamKhac;
    private long phiKhuyenMai;
    private long promoDiscount;
    private boolean isFromBVcare;
    private long language;
    private long promotionPercentage;
    private long promotionalDiscountFee;

    @JsonProperty("receiverUser")
    public ReceiverUser getReceiverUser() { return receiverUser; }
    @JsonProperty("receiverUser")
    public void setReceiverUser(ReceiverUser value) { this.receiverUser = value; }

    @JsonProperty("invoiceInfo")
    public InvoiceInfo getInvoiceInfo() { return invoiceInfo; }
    @JsonProperty("invoiceInfo")
    public void setInvoiceInfo(InvoiceInfo value) { this.invoiceInfo = value; }

    @JsonProperty("agreementId")
    public String getAgreementID() { return agreementID; }
    @JsonProperty("agreementId")
    public void setAgreementID(String value) { this.agreementID = value; }

    @JsonProperty("contactCode")
    public String getContactCode() { return contactCode; }
    @JsonProperty("contactCode")
    public void setContactCode(String value) { this.contactCode = value; }

    @JsonProperty("contactName")
    public String getContactName() { return contactName; }
    @JsonProperty("contactName")
    public void setContactName(String value) { this.contactName = value; }

    @JsonProperty("contactDob")
    public String getContactDob() { return contactDob; }
    @JsonProperty("contactDob")
    public void setContactDob(String value) { this.contactDob = value; }

    @JsonProperty("contactCategoryType")
    public String getContactCategoryType() { return contactCategoryType; }
    @JsonProperty("contactCategoryType")
    public void setContactCategoryType(String value) { this.contactCategoryType = value; }

    @JsonProperty("contactAddress")
    public String getContactAddress() { return contactAddress; }
    @JsonProperty("contactAddress")
    public void setContactAddress(String value) { this.contactAddress = value; }

    @JsonProperty("receiveMethod")
    public String getReceiveMethod() { return receiveMethod; }
    @JsonProperty("receiveMethod")
    public void setReceiveMethod(String value) { this.receiveMethod = value; }

    @JsonProperty("gycbhNumber")
    public String getGycbhNumber() { return gycbhNumber; }
    @JsonProperty("gycbhNumber")
    public void setGycbhNumber(String value) { this.gycbhNumber = value; }

    @JsonProperty("lineId")
    public String getLineID() { return lineID; }
    @JsonProperty("lineId")
    public void setLineID(String value) { this.lineID = value; }

    @JsonProperty("statusPolicy")
    public String getStatusPolicy() { return statusPolicy; }
    @JsonProperty("statusPolicy")
    public void setStatusPolicy(String value) { this.statusPolicy = value; }

    @JsonProperty("departmentId")
    public String getDepartmentID() { return departmentID; }
    @JsonProperty("departmentId")
    public void setDepartmentID(String value) { this.departmentID = value; }

    @JsonProperty("contactPhone")
    public String getContactPhone() { return contactPhone; }
    @JsonProperty("contactPhone")
    public void setContactPhone(String value) { this.contactPhone = value; }

    @JsonProperty("contactEmail")
    public String getContactEmail() { return contactEmail; }
    @JsonProperty("contactEmail")
    public void setContactEmail(String value) { this.contactEmail = value; }

    @JsonProperty("contactIdNumber")
    public String getContactIDNumber() { return contactIDNumber; }
    @JsonProperty("contactIdNumber")
    public void setContactIDNumber(String value) { this.contactIDNumber = value; }

    @JsonProperty("contactCif")
    public String getContactCif() { return contactCif; }
    @JsonProperty("contactCif")
    public void setContactCif(String value) { this.contactCif = value; }

    @JsonProperty("saleToEmp")
    public SaleToEmp getSaleToEmp() { return saleToEmp; }
    @JsonProperty("saleToEmp")
    public void setSaleToEmp(SaleToEmp value) { this.saleToEmp = value; }

    @JsonProperty("checkOTP")
    public String getCheckOTP() { return checkOTP; }
    @JsonProperty("checkOTP")
    public void setCheckOTP(String value) { this.checkOTP = value; }

    @JsonProperty("kenhPhanPhoi")
    public String getKenhPhanPhoi() { return kenhPhanPhoi; }
    @JsonProperty("kenhPhanPhoi")
    public void setKenhPhanPhoi(String value) { this.kenhPhanPhoi = value; }

    @JsonProperty("confirmMethod")
    public long getConfirmMethod() { return confirmMethod; }
    @JsonProperty("confirmMethod")
    public void setConfirmMethod(long value) { this.confirmMethod = value; }

    @JsonProperty("gycFiles")
    public List<Object> getGycFiles() { return gycFiles; }
    @JsonProperty("gycFiles")
    public void setGycFiles(List<Object> value) { this.gycFiles = value; }

    @JsonProperty("insuranceTarget")
    public String getInsuranceTarget() { return insuranceTarget; }
    @JsonProperty("insuranceTarget")
    public void setInsuranceTarget(String value) { this.insuranceTarget = value; }

    @JsonProperty("promoValue")
    public long getPromoValue() { return promoValue; }
    @JsonProperty("promoValue")
    public void setPromoValue(long value) { this.promoValue = value; }

    @JsonProperty("promoPercent")
    public long getPromoPercent() { return promoPercent; }
    @JsonProperty("promoPercent")
    public void setPromoPercent(long value) { this.promoPercent = value; }

    @JsonProperty("thoihanbhTu")
    public String getThoihanbhTu() { return thoihanbhTu; }
    @JsonProperty("thoihanbhTu")
    public void setThoihanbhTu(String value) { this.thoihanbhTu = value; }

    @JsonProperty("thoihanbhDen")
    public String getThoihanbhDen() { return thoihanbhDen; }
    @JsonProperty("thoihanbhDen")
    public void setThoihanbhDen(String value) { this.thoihanbhDen = value; }

    @JsonProperty("nguoiycName")
    public String getNguoiycName() { return nguoiycName; }
    @JsonProperty("nguoiycName")
    public void setNguoiycName(String value) { this.nguoiycName = value; }

    @JsonProperty("nguoiycNgaysinh")
    public String getNguoiycNgaysinh() { return nguoiycNgaysinh; }
    @JsonProperty("nguoiycNgaysinh")
    public void setNguoiycNgaysinh(String value) { this.nguoiycNgaysinh = value; }

    @JsonProperty("nguoiycCmnd")
    public String getNguoiycCmnd() { return nguoiycCmnd; }
    @JsonProperty("nguoiycCmnd")
    public void setNguoiycCmnd(String value) { this.nguoiycCmnd = value; }

    @JsonProperty("listBvgAddBaseVM")
    public List<ListBvgAddBaseVM> getListBvgAddBaseVM() { return listBvgAddBaseVM; }
    @JsonProperty("listBvgAddBaseVM")
    public void setListBvgAddBaseVM(List<ListBvgAddBaseVM> value) { this.listBvgAddBaseVM = value; }

    @JsonProperty("ttskCheck")
    public String getTtskCheck() { return ttskCheck; }
    @JsonProperty("ttskCheck")
    public void setTtskCheck(String value) { this.ttskCheck = value; }

    @JsonProperty("q1")
    public String getQ1() { return q1; }
    @JsonProperty("q1")
    public void setQ1(String value) { this.q1 = value; }

    @JsonProperty("q1QuestionId")
    public String getQ1QuestionID() { return q1QuestionID; }
    @JsonProperty("q1QuestionId")
    public void setQ1QuestionID(String value) { this.q1QuestionID = value; }

    @JsonProperty("q1Question")
    public String getQ1Question() { return q1Question; }
    @JsonProperty("q1Question")
    public void setQ1Question(String value) { this.q1Question = value; }

    @JsonProperty("q1QuestionDesc")
    public String getQ1QuestionDesc() { return q1QuestionDesc; }
    @JsonProperty("q1QuestionDesc")
    public void setQ1QuestionDesc(String value) { this.q1QuestionDesc = value; }

    @JsonProperty("q1QuestionNote")
    public String getQ1QuestionNote() { return q1QuestionNote; }
    @JsonProperty("q1QuestionNote")
    public void setQ1QuestionNote(String value) { this.q1QuestionNote = value; }

    @JsonProperty("q2")
    public String getQ2() { return q2; }
    @JsonProperty("q2")
    public void setQ2(String value) { this.q2 = value; }

    @JsonProperty("q2QuestionId")
    public String getQ2QuestionID() { return q2QuestionID; }
    @JsonProperty("q2QuestionId")
    public void setQ2QuestionID(String value) { this.q2QuestionID = value; }

    @JsonProperty("q2Question")
    public String getQ2Question() { return q2Question; }
    @JsonProperty("q2Question")
    public void setQ2Question(String value) { this.q2Question = value; }

    @JsonProperty("q2QuestionDesc")
    public String getQ2QuestionDesc() { return q2QuestionDesc; }
    @JsonProperty("q2QuestionDesc")
    public void setQ2QuestionDesc(String value) { this.q2QuestionDesc = value; }

    @JsonProperty("q2QuestionNote")
    public String getQ2QuestionNote() { return q2QuestionNote; }
    @JsonProperty("q2QuestionNote")
    public void setQ2QuestionNote(String value) { this.q2QuestionNote = value; }

    @JsonProperty("q3")
    public String getQ3() { return q3; }
    @JsonProperty("q3")
    public void setQ3(String value) { this.q3 = value; }

    @JsonProperty("q3QuestionId")
    public String getQ3QuestionID() { return q3QuestionID; }
    @JsonProperty("q3QuestionId")
    public void setQ3QuestionID(String value) { this.q3QuestionID = value; }

    @JsonProperty("q3Question")
    public String getQ3Question() { return q3Question; }
    @JsonProperty("q3Question")
    public void setQ3Question(String value) { this.q3Question = value; }

    @JsonProperty("q3QuestionDesc")
    public String getQ3QuestionDesc() { return q3QuestionDesc; }
    @JsonProperty("q3QuestionDesc")
    public void setQ3QuestionDesc(String value) { this.q3QuestionDesc = value; }

    @JsonProperty("q3QuestionNote")
    public String getQ3QuestionNote() { return q3QuestionNote; }
    @JsonProperty("q3QuestionNote")
    public void setQ3QuestionNote(String value) { this.q3QuestionNote = value; }

    @JsonProperty("q4")
    public String getQ4() { return q4; }
    @JsonProperty("q4")
    public void setQ4(String value) { this.q4 = value; }

    @JsonProperty("q4QuestionId")
    public String getQ4QuestionID() { return q4QuestionID; }
    @JsonProperty("q4QuestionId")
    public void setQ4QuestionID(String value) { this.q4QuestionID = value; }

    @JsonProperty("q4Question")
    public String getQ4Question() { return q4Question; }
    @JsonProperty("q4Question")
    public void setQ4Question(String value) { this.q4Question = value; }

    @JsonProperty("q4QuestionDesc")
    public String getQ4QuestionDesc() { return q4QuestionDesc; }
    @JsonProperty("q4QuestionDesc")
    public void setQ4QuestionDesc(String value) { this.q4QuestionDesc = value; }

    @JsonProperty("q4QuestionNote")
    public String getQ4QuestionNote() { return q4QuestionNote; }
    @JsonProperty("q4QuestionNote")
    public void setQ4QuestionNote(String value) { this.q4QuestionNote = value; }

    @JsonProperty("hasNguoinhantien")
    public boolean getHasNguoinhantien() { return hasNguoinhantien; }
    @JsonProperty("hasNguoinhantien")
    public void setHasNguoinhantien(boolean value) { this.hasNguoinhantien = value; }

    @JsonProperty("showGiamDinh")
    public String getShowGiamDinh() { return showGiamDinh; }
    @JsonProperty("showGiamDinh")
    public void setShowGiamDinh(String value) { this.showGiamDinh = value; }

    @JsonProperty("totalPremium")
    public long getTotalPremium() { return totalPremium; }
    @JsonProperty("totalPremium")
    public void setTotalPremium(long value) { this.totalPremium = value; }

    @JsonProperty("netPremium")
    public long getNetPremium() { return netPremium; }
    @JsonProperty("netPremium")
    public void setNetPremium(long value) { this.netPremium = value; }

    @JsonProperty("changePremium")
    public long getChangePremium() { return changePremium; }
    @JsonProperty("changePremium")
    public void setChangePremium(long value) { this.changePremium = value; }

    @JsonProperty("hasNguoithuhuong")
    public boolean getHasNguoithuhuong() { return hasNguoithuhuong; }
    @JsonProperty("hasNguoithuhuong")
    public void setHasNguoithuhuong(boolean value) { this.hasNguoithuhuong = value; }

    @JsonProperty("hasTThoadonGTG")
    public boolean getHasTThoadonGTG() { return hasTThoadonGTG; }
    @JsonProperty("hasTThoadonGTG")
    public void setHasTThoadonGTG(boolean value) { this.hasTThoadonGTG = value; }

    @JsonProperty("hasGks")
    public boolean getHasGks() { return hasGks; }
    @JsonProperty("hasGks")
    public void setHasGks(boolean value) { this.hasGks = value; }

    @JsonProperty("isShowDgrr")
    public boolean getIsShowDgrr() { return isShowDgrr; }
    @JsonProperty("isShowDgrr")
    public void setIsShowDgrr(boolean value) { this.isShowDgrr = value; }

    @JsonProperty("guaranteeCard")
    public long getGuaranteeCard() { return guaranteeCard; }
    @JsonProperty("guaranteeCard")
    public void setGuaranteeCard(long value) { this.guaranteeCard = value; }

    @JsonProperty("totalPremiumDGRR")
    public long getTotalPremiumDGRR() { return totalPremiumDGRR; }
    @JsonProperty("totalPremiumDGRR")
    public void setTotalPremiumDGRR(long value) { this.totalPremiumDGRR = value; }

    @JsonProperty("attachFiles")
    public List<File> getAttachFiles() { return attachFiles; }
    @JsonProperty("attachFiles")
    public void setAttachFiles(List<File> value) { this.attachFiles = value; }

    @JsonProperty("tangGiamKhac")
    public long getTangGiamKhac() { return tangGiamKhac; }
    @JsonProperty("tangGiamKhac")
    public void setTangGiamKhac(long value) { this.tangGiamKhac = value; }

    @JsonProperty("phiKhuyenMai")
    public long getPhiKhuyenMai() { return phiKhuyenMai; }
    @JsonProperty("phiKhuyenMai")
    public void setPhiKhuyenMai(long value) { this.phiKhuyenMai = value; }

    @JsonProperty("promoDiscount")
    public long getPromoDiscount() { return promoDiscount; }
    @JsonProperty("promoDiscount")
    public void setPromoDiscount(long value) { this.promoDiscount = value; }

    @JsonProperty("isFromBVcare")
    public boolean getIsFromBVcare() { return isFromBVcare; }
    @JsonProperty("isFromBVcare")
    public void setIsFromBVcare(boolean value) { this.isFromBVcare = value; }

    @JsonProperty("language")
    public long getLanguage() { return language; }
    @JsonProperty("language")
    public void setLanguage(long value) { this.language = value; }

    @JsonProperty("promotionPercentage")
    public long getPromotionPercentage() { return promotionPercentage; }
    @JsonProperty("promotionPercentage")
    public void setPromotionPercentage(long value) { this.promotionPercentage = value; }

    @JsonProperty("promotionalDiscountFee")
    public long getPromotionalDiscountFee() { return promotionalDiscountFee; }
    @JsonProperty("promotionalDiscountFee")
    public void setPromotionalDiscountFee(long value) { this.promotionalDiscountFee = value; }
}
