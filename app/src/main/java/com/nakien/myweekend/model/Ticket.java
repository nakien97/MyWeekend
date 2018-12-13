package com.nakien.myweekend.model;

public class Ticket {
    private String email_kh;
    private String id_phieudatve;
    private String id_suatchieu;
    private String ngay_lap;
    private long tong_tien;

    public Ticket(){
    }

    public Ticket(Ticket t) {
        this.email_kh = t.email_kh;
        this.id_phieudatve = t.id_phieudatve;
        this.id_suatchieu = t.id_suatchieu;
        this.ngay_lap = t.ngay_lap;
        this.tong_tien = t.tong_tien;
    }

    public Ticket(String email_kh, String id_phieudatve, String id_suatchieu, String ngay_lap, long tong_tien) {
        this.email_kh = email_kh;
        this.id_phieudatve = id_phieudatve;
        this.id_suatchieu = id_suatchieu;
        this.ngay_lap = ngay_lap;
        this.tong_tien = tong_tien;
    }

    public String getEmail_kh() {
        return email_kh;
    }

    public void setEmail_kh(String email_kh) {
        this.email_kh = email_kh;
    }

    public String getId_phieudatve() {
        return id_phieudatve;
    }

    public void setId_phieudatve(String id_phieudatve) {
        this.id_phieudatve = id_phieudatve;
    }

    public String getId_suatchieu() {
        return id_suatchieu;
    }

    public void setId_suatchieu(String id_suatchieu) {
        this.id_suatchieu = id_suatchieu;
    }

    public String getNgay_lap() {
        return ngay_lap;
    }

    public void setNgay_lap(String ngay_lap) {
        this.ngay_lap = ngay_lap;
    }

    public long getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(long tong_tien) {
        this.tong_tien = tong_tien;
    }
}
