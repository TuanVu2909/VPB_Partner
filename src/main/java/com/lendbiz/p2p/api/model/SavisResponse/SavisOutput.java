package com.lendbiz.p2p.api.model.SavisResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SavisOutput {
    private DanToc hoTen;
    private DanToc ngaySinh;
    private DanToc gioiTinh;
    private Liveness liveness;
    private DanToc hoKhauThuongTru;
    private DanToc ngayHetHan;
    private DanToc danToc;
    private DanToc nguyenQuan;
    private DanToc id;
    private ClassName className;

    @JsonProperty("ho_ten")
    public DanToc getHoTen() {
        return hoTen;
    }

    @JsonProperty("ho_ten")
    public void setHoTen(DanToc value) {
        this.hoTen = value;
    }

    @JsonProperty("ngay_sinh")
    public DanToc getNgaySinh() {
        return ngaySinh;
    }

    @JsonProperty("ngay_sinh")
    public void setNgaySinh(DanToc value) {
        this.ngaySinh = value;
    }

    @JsonProperty("gioi_tinh")
    public DanToc getGioiTinh() {
        return gioiTinh;
    }

    @JsonProperty("gioi_tinh")
    public void setGioiTinh(DanToc value) {
        this.gioiTinh = value;
    }

    @JsonProperty("liveness")
    public Liveness getLiveness() {
        return liveness;
    }

    @JsonProperty("liveness")
    public void setLiveness(Liveness value) {
        this.liveness = value;
    }

    @JsonProperty("ho_khau_thuong_tru")
    public DanToc getHoKhauThuongTru() {
        return hoKhauThuongTru;
    }

    @JsonProperty("ho_khau_thuong_tru")
    public void setHoKhauThuongTru(DanToc value) {
        this.hoKhauThuongTru = value;
    }

    @JsonProperty("ngay_het_han")
    public DanToc getNgayHetHan() {
        return ngayHetHan;
    }

    @JsonProperty("ngay_het_han")
    public void setNgayHetHan(DanToc value) {
        this.ngayHetHan = value;
    }

    @JsonProperty("dan_toc")
    public DanToc getDanToc() {
        return danToc;
    }

    @JsonProperty("dan_toc")
    public void setDanToc(DanToc value) {
        this.danToc = value;
    }

    @JsonProperty("nguyen_quan")
    public DanToc getNguyenQuan() {
        return nguyenQuan;
    }

    @JsonProperty("nguyen_quan")
    public void setNguyenQuan(DanToc value) {
        this.nguyenQuan = value;
    }

    @JsonProperty("id")
    public DanToc getID() {
        return id;
    }

    @JsonProperty("id")
    public void setID(DanToc value) {
        this.id = value;
    }

    @JsonProperty("class_name")
    public ClassName getClassName() {
        return className;
    }

    @JsonProperty("class_name")
    public void setClassName(ClassName value) {
        this.className = value;
    }
}