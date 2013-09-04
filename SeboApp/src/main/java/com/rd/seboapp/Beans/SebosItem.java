package com.rd.seboapp.Beans;

/**
 * Created by RD on 03/09/13.
 */
public class SebosItem {
    private String link;
    private String title;
    private String city;
    private String state;
    private int totalBooks;
    private long rating;

    public SebosItem(String link, String title, String city, String state, int totalBooks, long rating) {
        this.link = link;
        this.title = title;
        this.city = city;
        this.state = state;
        this.totalBooks = totalBooks;
        this.rating = rating;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}