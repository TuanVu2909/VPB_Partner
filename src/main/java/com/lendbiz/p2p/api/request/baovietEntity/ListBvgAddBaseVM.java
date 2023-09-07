package com.lendbiz.p2p.api.request.baovietEntity;

import com.lendbiz.p2p.api.request.AttachFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListBvgAddBaseVM {
    private String checkBenhLyNghiemTrong;
    private int checkReuse;
    private String chuongTrinhBh;
    private int confirmMethod;
    private int defaultDiscountBvcare;
    private int defaultLoadingBvcare;
    private String defaultTangGiamNDBvcare;
    private double discount;
    private AttachFile gksFile;
    private String gycbhId;
    private Boolean hasExtracare;
    private Boolean hasNguoithuhuong;
    private String isShowPersonList;
    private String ketqua;
    private int loading;
    private String ngoaitruChk;
    private double ngoaitruPhi;
    private String nguoidbhCmnd;
    private String nguoidbhDiachi;
    private String nguoidbhDienthoai;
    private String nguoidbhEmail;
    private String nguoidbhGioitinh;
    private String nguoidbhName;
    private String nguoidbhNgaysinh;
    private String nguoidbhQuanhe;
    private String nguoithCmnd;
    private String nguoithDiachi;
    private String nguoithDienthoai;
    private String nguoithEmail;
    private String nguoithName;
    private String nguoithNgaysinh;
    private String nguoithQuanhe;
    private String nhakhoaChk;
    private double nhakhoaPhi;
    private String oldGCN;
    private String oldPolicyNumber;
    private int percentId;
    private int personOrder;
    private double phuPhiBh;
    private String policyParent;
    private double qlChinhPhi;
    private QuocTich quocTich;
    private int quocTichCode;
    private String riskAssessmentContent;
    private String riskAssessmentStatus;
    private String serial;
    private String skipRiskAssessment;
    private String smcnChk;
    private double smcnPhi;
    private double smcnSotienbh;
    private int taiTuc;
    private double tangGiamPhanTram;
    private double tanggiamPhi;
    private String tanggiamPhiNoidung;
    private String thaisanChk;
    private double thaisanPhi;
    private String tncnChk;
    private double tncnPhi;
    private double tncnSotienbh;
    private double tongPhiBH;
    private double tongPhiSauTangGiam;
    private String ttskCheck;
    private int tuoi;
    private int tuoiNDBH;
    private int tyLEBoiThuong;
}
