package com.example.greeningapp;

public class ReviewData {
    private String Review_image;
    private String Write_review;
    private float Rating;
    private String Review_date;
    private String Username;

    public String getUsername() { return Username; }

    public void setUsername(String username) { Username = username; }

    public String getReview_image() {
        return Review_image;
    }

    public void setReview_image(String review_image) {
        Review_image = review_image;
    }

    public String getWrite_review() {
        return Write_review;
    }

    public void setWrite_review(String write_review) {
        Write_review = write_review;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getReview_date() {
        return Review_date;
    }

    public void setReview_date(String review_date) {
        this.Review_date = review_date;
    }
}