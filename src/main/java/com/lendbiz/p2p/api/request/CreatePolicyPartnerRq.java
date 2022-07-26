package com.lendbiz.p2p.api.request;

import com.lendbiz.p2p.api.entity.InvoiceInfo;
import com.lendbiz.p2p.api.entity.ListBvgAddBaseVM;
import com.lendbiz.p2p.api.entity.ReceiverUser;
import com.lendbiz.p2p.api.entity.SaleToEmp;
import lombok.*;

@Getter
@Setter
@Data
@ToString
public class CreatePolicyPartnerRq {
    private String note;
    private String guaranteeCard;
    private String soNguoiThamGia;
    private String agreementId;
    private String checkOTP;
    private String contactAddress;
    private String contactCategoryType;
    private String contactCode;
    private String contactDob;
    private String contactEmail;
    private String contactIdNumber;
    private String contactName;
    private String contactPhone;
    private String departmentId;
    private String riskAssessmentStatus;
    private String statusPolicyId;
    private String discount;
    private String gycbhId;
    private String urnCanBo;
    private String gycbhNumber;
    private String hasNguoithuhuong;
    private String hasNguoinhantien;
    private String hasTThoadonGTG;
    private String isShowDgrr;
    private String hasGks;
    private String insuranceTarget;
    private InvoiceInfo invoiceInfo;
    private String lineId;
    private ListBvgAddBaseVM[] listBvgAddBaseVM;
    private String nguoinhanCmnd;
    private String nguoinhanName;
    private String nguoinhanQuanhe;
    private String nguointDiachi;
    private String nguointDienthoai;
    private String nguointEmail;
    private String nguointNgaysinh;
    private String nguoiycCmnd;
    private String nguoiycName;
    private String nguoiycNgaysinh;
    private String oldGycbhNumber;
    private String percentID;
    private String ttskCheck;
    private String q1;
    private String q2;
    private String q3;
    private String receiveMethod;
    private ReceiverUser receiverUser;
    private SaleToEmp saleToEmp;
    private String showGiamDinh;
    private String statusPolicy;
    private String tanggiamPhi;
    private String tanggiamPhiNoidung;
    private String thoihanbhDen;
    private String thoihanbhTu;
    private String confirmMethod;
    private Object[] gycFiles;
    private String totalPremium;
    private String urlPolicy;
    private String conversationContent;
    private String inceptionDate;
    private String expiredDate;
    private String kenhPhanPhoi;
    private String nguoithCmnd;
    private String nguoithQuanhe;
    private String nguoidbhCmnd;
    private String nguoidbhQuanhe;
    private String contactCif;
    private ContactAddressDistrictData contactAddressDistrictData;
    private String tongPhiBH;
    private String checkTtskNdbh;
    
    public void setContactAddressDistrictData() {
        this.contactAddressDistrictData = new ContactAddressDistrictData();
    }

    public CreatePolicyPartnerRq() {
    }

    public CreatePolicyPartnerRq(String note, String agreementId, String checkOTP, String riskAssessmentStatus, String statusPolicyId, String gycbhId, String urnCanBo, String gycbhNumber, String nguoinhanCmnd, String nguoinhanName, String nguoinhanQuanhe, String nguointDiachi, String nguointDienthoai, String nguointEmail, String nguointNgaysinh, String oldGycbhNumber, String showGiamDinh, String tanggiamPhiNoidung, String urlPolicy, String conversationContent, String nguoithCmnd, String nguoithQuanhe, String nguoidbhCmnd, String nguoidbhQuanhe) {
        this.note = note;
        this.agreementId = agreementId;
        this.checkOTP = checkOTP;
        this.riskAssessmentStatus = riskAssessmentStatus;
        this.statusPolicyId = statusPolicyId;
        this.gycbhId = gycbhId;
        this.urnCanBo = urnCanBo;
        this.gycbhNumber = gycbhNumber;
        this.nguoinhanCmnd = nguoinhanCmnd;
        this.nguoinhanName = nguoinhanName;
        this.nguoinhanQuanhe = nguoinhanQuanhe;
        this.nguointDiachi = nguointDiachi;
        this.nguointDienthoai = nguointDienthoai;
        this.nguointEmail = nguointEmail;
        this.nguointNgaysinh = nguointNgaysinh;
        this.oldGycbhNumber = oldGycbhNumber;
        this.showGiamDinh = showGiamDinh;
        this.tanggiamPhiNoidung = tanggiamPhiNoidung;
        this.urlPolicy = urlPolicy;
        this.conversationContent = conversationContent;
        this.nguoithCmnd = nguoithCmnd;
        this.nguoithQuanhe = nguoithQuanhe;
        this.nguoidbhCmnd = nguoidbhCmnd;
        this.nguoidbhQuanhe = nguoidbhQuanhe;
    }
}

@Data
@Getter
@Setter
class ContactAddressDistrictData {
    private String pkPostcode;
    private String pkDistrict;
    private String pkProvince;

    public ContactAddressDistrictData() {
        this.pkPostcode = "00000000";
        this.pkDistrict = "Địa chỉ khác, Khác";
        this.pkProvince = "Khác";
    }

}