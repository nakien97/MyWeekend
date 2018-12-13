package com.nakien.myweekend.model;

public class Schedule {
    private String key;
    private String id_film;
    private String id_cum;
    private String id_rap;
    private String ngay;
    private String gio_bd;
    public Schedule(){
    }

    public Schedule(Schedule s) {
        this.key = s.key;
        this.id_film = s.id_film;
        this.id_cum = s.id_cum;
        this.id_rap = s.id_rap;
        this.ngay = s.ngay;
        this.gio_bd = s.gio_bd;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId_film() {
        return id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public String getId_cum() {
        return id_cum;
    }

    public void setId_cum(String id_cum) {
        this.id_cum = id_cum;
    }

    public String getId_rap() {
        return id_rap;
    }

    public void setId_rap(String id_rap) {
        this.id_rap = id_rap;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio_bd() {
        return gio_bd;
    }

    public void setGio_bd(String gio_bd) {
        this.gio_bd = gio_bd;
    }
}
