package com.nakien.myweekend.model;

public class RecordBooking {
    private String id_ghe;
    private String id_phieudatve;
    private long don_gia;

    public RecordBooking(){
    }

    public RecordBooking(RecordBooking r) {
        this.id_ghe = r.id_ghe;
        this.id_phieudatve = r.id_phieudatve;
        this.don_gia = r.don_gia;
    }


    public RecordBooking(String id_ghe, String id_phieudatve, long don_gia) {
        this.id_ghe = id_ghe;
        this.id_phieudatve = id_phieudatve;
        this.don_gia = don_gia;
    }

    public long getDon_gia() {
        return don_gia;
    }

    public void setDon_gia(long don_gia) {
        this.don_gia = don_gia;
    }

    public String getId_ghe() {
        return id_ghe;
    }

    public void setId_ghe(String id_ghe) {
        this.id_ghe = id_ghe;
    }

    public String getId_phieudatve() {
        return id_phieudatve;
    }

    public void setId_phieudatve(String id_phieudatve) {
        this.id_phieudatve = id_phieudatve;
    }
}
