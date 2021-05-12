package com.neueda.services.atm.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ATMamount {

    private int id;
    private String location;
    private int fifty;
    private int twenty;
    private int ten;
    private int five;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "fifty", nullable = false)
    public int getFifty() {
        return fifty;
    }

    public void setFifty(int fifty) {
        this.fifty = fifty;
    }

    @Column(name = "twenty", nullable = false)
    public int getTwenty() {
        return twenty;
    }

    public void setTwenty(int twenty) {
        this.twenty = twenty;
    }

    @Column(name = "ten", nullable = false)
    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    @Column(name = "five", nullable = false)
    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    @Override
    public String toString() {
        return "ATMamount{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", fifty=" + fifty +
                ", twenty=" + twenty +
                ", ten=" + ten +
                ", five=" + five +
                '}';
    }
}
