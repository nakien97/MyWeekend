package com.nakien.myweekend.item;

public class History {
    private String imgBanner;
    private String dateTimeBook;
    private String dateTime;
    private String filmName;
    private String cinemasCenter;
    private String cinema;
    private int ticket;
    private long total;

    public History(){
    }

    public History(String imgBanner, String dateTimeBook, String dateTime, String filmName, String cinemasCenter, String cinema, int ticket, long total) {
        this.imgBanner = imgBanner;
        this.dateTimeBook = dateTimeBook;
        this.dateTime = dateTime;
        this.filmName = filmName;
        this.cinemasCenter = cinemasCenter;
        this.cinema = cinema;
        this.ticket = ticket;
        this.total = total;
    }

    public String getImgBanner() {
        return imgBanner;
    }

    public void setImgBanner(String imgBanner) {
        this.imgBanner = imgBanner;
    }

    public String getDateTimeBook() {
        return dateTimeBook;
    }

    public void setDateTimeBook(String dateTimeBook) {
        this.dateTimeBook = dateTimeBook;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getCinemasCenter() {
        return cinemasCenter;
    }

    public void setCinemasCenter(String cinemasCenter) {
        this.cinemasCenter = cinemasCenter;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
