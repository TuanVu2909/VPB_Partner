package com.lendbiz.p2p.api.request;

import com.lendbiz.p2p.api.entity.InvoiceInfo;
import com.lendbiz.p2p.api.entity.ListBvgAddBaseVM;
import com.lendbiz.p2p.api.entity.ReceiverUser;
import com.lendbiz.p2p.api.entity.SaleToEmp;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private  ListBvgAddBaseVM[]  listBvgAddBaseVM;
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

}
@Data
@Getter
@Setter
class  ContactAddressDistrictData {
    private String pkPostcode;
    private String pkDistrict;
    private String pkProvince;
}