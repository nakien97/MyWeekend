package com.nakien.myweekend.item;

public class MenuItem {
    private int imgMenuItem;
    private String txtMenuItem;

    public MenuItem(int imgMenuItem, String txtMenuItem) {
        this.imgMenuItem = imgMenuItem;
        this.txtMenuItem = txtMenuItem;
    }

    public int getImgMenuItem() {
        return imgMenuItem;
    }

    public void setImgMenuItem(int imgMenuItem) {
        this.imgMenuItem = imgMenuItem;
    }

    public String getTxtMenuItem() {
        return txtMenuItem;
    }

    public void setTxtMenuItem(String txtMenuItem) {
        this.txtMenuItem = txtMenuItem;
    }
}
