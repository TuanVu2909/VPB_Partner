package com.lendbiz.p2p.api.model.bvpremium;

import com.fasterxml.jackson.annotation.*;

public class PremiumModel {
    private String chuongTrinhBh;
    private String nguoidbhNgaysinh;
    private Long tuoi;
    private String ngoaitruChk;
    private Long ngoaitruPhi;
    private String tncnChk;
    private Long tncnSotienbh;
    private Long tncnPhi;
    private String smcnChk;
    private Long smcnSotienbh;
    private Long smcnPhi;
    private String nhakhoaChk;
    private Long nhakhoaPhi;
    private String thaisanChk;
    private Long thaisanPhi;
    private Long qlChinhPhi;
    private String insuranceTarget;
    private Long tongPhiBH;
    private Long tongPhiSauTangGiam;
    private String thoihanbhTu;
    private String serial;
    private String oldPolicyNumber;
    private String checkReuse;
    private String checkTtskNdbh;

    @JsonProperty("chuongTrinhBh")
    public String getChuongTrinhBh() { return chuongTrinhBh; }
    @JsonProperty("chuongTrinhBh")
    public void setChuongTrinhBh(String value) { this.chuongTrinhBh = value; }

    @JsonProperty("nguoidbhNgaysinh")
    public String getNguoidbhNgaysinh() { return nguoidbhNgaysinh; }
    @JsonProperty("nguoidbhNgaysinh")
    public void setNguoidbhNgaysinh(String value) { this.nguoidbhNgaysinh = value; }

    @JsonProperty("tuoi")
    public Long getTuoi() { return tuoi; }
    @JsonProperty("tuoi")
    public void setTuoi(Long value) { this.tuoi = value; }

    @JsonProperty("ngoaitruChk")
    public String getNgoaitruChk() { return ngoaitruChk; }
    @JsonProperty("ngoaitruChk")
    public void setNgoaitruChk(String value) { this.ngoaitruChk = value; }

    @JsonProperty("ngoaitruPhi")
    public Long getNgoaitruPhi() { return ngoaitruPhi; }
    @JsonProperty("ngoaitruPhi")
    public void setNgoaitruPhi(Long value) { this.ngoaitruPhi = value; }

    @JsonProperty("tncnChk")
    public String getTncnChk() { return tncnChk; }
    @JsonProperty("tncnChk")
    public void setTncnChk(String value) { this.tncnChk = value; }

    @JsonProperty("tncnSotienbh")
    public Long getTncnSotienbh() { return tncnSotienbh; }
    @JsonProperty("tncnSotienbh")
    public void setTncnSotienbh(Long value) { this.tncnSotienbh = value; }

    @JsonProperty("tncnPhi")
    public Long getTncnPhi() { return tncnPhi; }
    @JsonProperty("tncnPhi")
    public void setTncnPhi(Long value) { this.tncnPhi = value; }

    @JsonProperty("smcnChk")
    public String getSmcnChk() { return smcnChk; }
    @JsonProperty("smcnChk")
    public void setSmcnChk(String value) { this.smcnChk = value; }

    @JsonProperty("smcnSotienbh")
    public Long getSmcnSotienbh() { return smcnSotienbh; }
    @JsonProperty("smcnSotienbh")
    public void setSmcnSotienbh(Long value) { this.smcnSotienbh = value; }

    @JsonProperty("smcnPhi")
    public Long getSmcnPhi() { return smcnPhi; }
    @JsonProperty("smcnPhi")
    public void setSmcnPhi(Long value) { this.smcnPhi = value; }

    @JsonProperty("nhakhoaChk")
    public String getNhakhoaChk() { return nhakhoaChk; }
    @JsonProperty("nhakhoaChk")
    public void setNhakhoaChk(String value) { this.nhakhoaChk = value; }

    @JsonProperty("nhakhoaPhi")
    public Long getNhakhoaPhi() { return nhakhoaPhi; }
    @JsonProperty("nhakhoaPhi")
    public void setNhakhoaPhi(Long value) { this.nhakhoaPhi = value; }

    @JsonProperty("thaisanChk")
    public String getThaisanChk() { return thaisanChk; }
    @JsonProperty("thaisanChk")
    public void setThaisanChk(String value) { this.thaisanChk = value; }

    @JsonProperty("thaisanPhi")
    public Long getThaisanPhi() { return thaisanPhi; }
    @JsonProperty("thaisanPhi")
    public void setThaisanPhi(Long value) { this.thaisanPhi = value; }

    @JsonProperty("qlChinhPhi")
    public Long getQlChinhPhi() { return qlChinhPhi; }
    @JsonProperty("qlChinhPhi")
    public void setQlChinhPhi(Long value) { this.qlChinhPhi = value; }

    @JsonProperty("insuranceTarget")
    public String getInsuranceTarget() { return insuranceTarget; }
    @JsonProperty("insuranceTarget")
    public void setInsuranceTarget(String value) { this.insuranceTarget = value; }

    @JsonProperty("tongPhiBH")
    public Long getTongPhiBH() { return tongPhiBH; }
    @JsonProperty("tongPhiBH")
    public void setTongPhiBH(Long value) { this.tongPhiBH = value; }

    @JsonProperty("tongPhiSauTangGiam")
    public Long getTongPhiSauTangGiam() { return tongPhiSauTangGiam; }
    @JsonProperty("tongPhiSauTangGiam")
    public void setTongPhiSauTangGiam(Long value) { this.tongPhiSauTangGiam = value; }

    @JsonProperty("thoihanbhTu")
    public String getThoihanbhTu() { return thoihanbhTu; }
    @JsonProperty("thoihanbhTu")
    public void setThoihanbhTu(String value) { this.thoihanbhTu = value; }

    @JsonProperty("serial")
    public String getSerial() { return serial; }
    @JsonProperty("serial")
    public void setSerial(String value) { this.serial = value; }

    @JsonProperty("oldPolicyNumber")
    public String getOldPolicyNumber() { return oldPolicyNumber; }
    @JsonProperty("oldPolicyNumber")
    public void setOldPolicyNumber(String value) { this.oldPolicyNumber = value; }

    @JsonProperty("checkReuse")
    public String getCheckReuse() { return checkReuse; }
    @JsonProperty("checkReuse")
    public void setCheckReuse(String value) { this.checkReuse = value; }

    @JsonProperty("checkTtskNdbh")
    public String getCheckTtskNdbh() { return checkTtskNdbh; }
    @JsonProperty("checkTtskNdbh")
    public void setCheckTtskNdbh(String value) { this.checkTtskNdbh = value; }
}
