package com.lendbiz.p2p.api.request;

import com.lendbiz.p2p.api.request.baovietEntity.AddressDistrictData;
import com.lendbiz.p2p.api.request.baovietEntity.InvoiceInfo;
import com.lendbiz.p2p.api.request.baovietEntity.ListBvgAddBaseVM;
import com.lendbiz.p2p.api.request.baovietEntity.ReceiverUser;
import com.lendbiz.p2p.api.request.baovietEntity.SaleToEmp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreatePolicyPartnerRequest {
    private String note = "";
    private String guaranteeCard = "";
    private long soNguoiThamGia = 1;
    private String agreementId = "";
    private String checkOTP = "";
    private String contactAddress = "";
    private String contactCategoryType = "";
    private String contactCode = "";
    private String contactDob = "";
    private String contactEmail = "";
    private String contactIdNumber = "";
    private String contactName = "";
    private String contactPhone = "";
    private String departmentId = "";
    private String riskAssessmentStatus = "";
    private String statusPolicyId = "";
    private long discount;
    private String gycbhId = "";
    private String urnCanBo = "";
    private String gycbhNumber = "";
    private boolean hasNguoithuhuong;
    private boolean hasNguoinhantien;
    private boolean hasTThoadonGTG;
    private String isShowDgrr;
    private boolean hasGks;
    private String insuranceTarget = "";
    private InvoiceInfo invoiceInfo;
    private String lineId = "";
    private ListBvgAddBaseVM[] listBvgAddBaseVM;
    private String nguoinhanCmnd = "";
    private String nguoinhanName = "";
    private String nguoinhanQuanhe = "";
    private String nguointDiachi = "";
    private String nguointDienthoai = "";
    private String nguointEmail = "";
    private String nguointNgaysinh = "";
    private String nguoiycCmnd = "";
    private String nguoiycName = "";
    private String nguoiycNgaysinh = "";
    private String oldGycbhNumber = "";
    private long percentId;
    private String ttskCheck = "";
    private String q1 = "";
    private String q2 = "";
    private String q3 = "";
    private String receiveMethod = "";
    private ReceiverUser receiverUser;
    private SaleToEmp saleToEmp;
    private String showGiamDinh = "";
    private String statusPolicy = "";
    private long tanggiamPhi;
    private String tanggiamPhiNoidung = "";
    private String thoihanbhDen = "";
    private String thoihanbhTu = "";
    private long confirmMethod;
    private Object[] gycFiles;
    private long totalPremium;
    private String urlPolicy = "";
    private String conversationContent = "";
    private String inceptionDate = "";
    private String expiredDate = "";
    private String kenhPhanPhoi = "";
    private String nguoithCmnd = "";
    private String nguoithQuanhe = "";
    private String nguoidbhCmnd = "";
    private String nguoidbhQuanhe = "";
    private String contactCif = "";
    private AddressDistrictData contactAddressDistrictData;
    private String checkTtskNdbh = "";
    private long tongPhiBH;
}
