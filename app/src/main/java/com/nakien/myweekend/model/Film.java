package com.nakien.myweekend.model;

public class Film {
    private String id_film;
    private String tenphim;
    private String url_banner_ngang;
    private String url_banner_doc;
    private String khoichieu;
    private String mieuta;
    private String id_dotuoi;
    private String id_trangthaiphim;
    private String theloai;
    private String daodien;
    private String dienvien;
    private int thoiluong;

    public Film() {
    }

    public Film(Film f) {
        this.id_film = f.id_film;
        this.tenphim = f.tenphim;
        this.url_banner_doc = f.url_banner_doc;
        this.url_banner_ngang = f.url_banner_ngang;
        this.khoichieu = f.khoichieu;
        this.mieuta = f.mieuta;
        this.id_dotuoi = f.id_dotuoi;
        this.id_trangthaiphim = f.id_trangthaiphim;
        this.theloai = f.theloai;
        this.daodien = f.daodien;
        this.dienvien = f.dienvien;
        this.thoiluong = f.thoiluong;
    }

    public String getId_film() {
        return id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public String getUrl_banner_ngang() {
        return url_banner_ngang;
    }

    public void setUrl_banner_ngang(String url_banner_ngang) {
        this.url_banner_ngang = url_banner_ngang;
    }

    public String getUrl_banner_doc() {
        return url_banner_doc;
    }

    public void setUrl_banner_doc(String url_banner_doc) {
        this.url_banner_doc = url_banner_doc;
    }

    public String getKhoichieu() {
        return khoichieu;
    }

    public void setKhoichieu(String khoichieu) {
        this.khoichieu = khoichieu;
    }

    public String getMieuta() {
        return mieuta;
    }

    public void setMieuta(String mieuta) {
        this.mieuta = mieuta;
    }

    public String getId_dotuoi() {
        return id_dotuoi;
    }

    public void setId_dotuoi(String id_dotuoi) {
        this.id_dotuoi = id_dotuoi;
    }

    public String getId_trangthaiphim() {
        return id_trangthaiphim;
    }

    public void setId_trangthaiphim(String id_trangthaiphim) {
        this.id_trangthaiphim = id_trangthaiphim;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getDaodien() {
        return daodien;
    }

    public void setDaodien(String daodien) {
        this.daodien = daodien;
    }

    public String getDienvien() {
        return dienvien;
    }

    public void setDienvien(String dienvien) {
        this.dienvien = dienvien;
    }

    public int getThoiluong() {
        return thoiluong;
    }

    public void setThoiluong(int thoiluong) {
        this.thoiluong = thoiluong;
    }
}
