package com.jcpdev.brickinventoryserver.Models;

import jakarta.persistence.*;

@Entity
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long barcode;
    @Column(columnDefinition = "bigint default '0'")
    private int quantity;
    @Column(columnDefinition = " varchar(999) default 'https://cdn.shopify.com/s/files/1/0876/7136/files/small_bt_250x@2x.png?v=1613547089'")
    private String photoUrl;

    public long getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quanity) {
        this.quantity = quanity;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }




}
