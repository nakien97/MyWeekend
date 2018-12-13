package com.nakien.myweekend.model;

public class Cinema {
    private String id_rap;
    private String id_cum;
    private String hang_dau;
    private String hang_cuoi;
    private int so_ghe_moi_hang;

    public Cinema(){
    }

    public Cinema(Cinema c) {
        this.id_rap = c.id_rap;
        this.id_cum = c.id_cum;
        this.hang_dau = c.hang_dau;
        this.hang_cuoi = c.hang_cuoi;
        this.so_ghe_moi_hang = c.so_ghe_moi_hang;
    }

    public String getId_rap() {
        return id_rap;
    }

    public void setId_rap(String id_rap) {
        this.id_rap = id_rap;
    }

    public String getId_cum() {
        return id_cum;
    }

    public void setId_cum(String id_cum) {
        this.id_cum = id_cum;
    }

    public String getHang_dau() {
        return hang_dau;
    }

    public void setHang_dau(String hang_dau) {
        this.hang_dau = hang_dau;
    }

    public String getHang_cuoi() {
        return hang_cuoi;
    }

    public void setHang_cuoi(String hang_cuoi) {
        this.hang_cuoi = hang_cuoi;
    }

    public int getSo_ghe_moi_hang() {
        return so_ghe_moi_hang;
    }

    public void setSo_ghe_moi_hang(int so_ghe_moi_hang) {
        this.so_ghe_moi_hang = so_ghe_moi_hang;
    }
}
