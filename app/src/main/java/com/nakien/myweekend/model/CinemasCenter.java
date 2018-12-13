package com.nakien.myweekend.model;
public class CinemasCenter {
    private String idCumRap;
    private String TenCum;
    private String DiaChi;
    private long gia_ve;

    public CinemasCenter(){
    }

    public CinemasCenter(String idCumRap, String TenCum, String DiaChi, long gia_ve) {
        this.idCumRap = idCumRap;
        this.TenCum = TenCum;
        this.DiaChi = DiaChi;
        this.gia_ve = gia_ve;
    }

    public CinemasCenter(CinemasCenter cinemas) {
        this.idCumRap = cinemas.idCumRap;
        this.TenCum = cinemas.TenCum;
        this.DiaChi = cinemas.DiaChi;
        this.gia_ve = cinemas.gia_ve;
    }


    public long getGia_ve() {
        return gia_ve;
    }

    public void setGia_ve(long gia_ve) {
        this.gia_ve = gia_ve;
    }

    public String getIdCumRap() {
        return idCumRap;
    }

    public void setIdCumRap(String idCumRap) {
        this.idCumRap = idCumRap;
    }

    public String getTenCum() {
        return TenCum;
    }

    public void setTenCum(String tenCum) {
        TenCum = tenCum;
    }

    public String getDiachi() {
        return DiaChi;
    }

    public void setDiachi(String diachi) {
        this.DiaChi = diachi;
    }
}
