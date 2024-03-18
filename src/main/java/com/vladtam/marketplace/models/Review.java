package com.vladtam.marketplace.models;

import com.vladtam.marketplace.dao.*;
import com.vladtam.marketplace.views.BaseView;
import com.vladtam.marketplace.views.ReviewView;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Review implements BaseModel {
    private Integer id;
    private User receiver;
    private User sender;
    private Byte rate;
    private String comment;

    public Review(){}

    public Review(Integer id, User receiver, User sender, Byte rate, String comment) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.rate = rate;
        this.comment = comment;
    }

    public Review(Integer id, User receiver, User sender, Byte rate) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "id=" + id + " " + sender.getName() + " " + sender.getSurname() + " -> " + receiver.getName() + " " +
                receiver.getSurname() + " (Rate:" + rate + ")";
    }

    @Override
    public String outputFullInfo() {
        return "REVIEW INFO:\n" +
                "id = " + id + "\n" +
                "receiver = (" + receiver + ")\n" +
                "sender = (" + sender + ")\n" +
                "rate = " + rate + "\n" +
                "comment = " + comment + "\n";
    }

    @Override
    public int createNew(Scanner scan) {
        BaseDAO reviewDao = new ReviewDAO();
        BaseView reviewView = new ReviewView();
        return reviewDao.createNew(reviewView.createNew(scan));
    }

    @Override
    public void delete(int id) {
        BaseDAO reviewDao = new ReviewDAO();
        reviewDao.delete(id);
    }

    @Override
    public void update(BaseModel bsModel, Scanner scan) {
        BaseDAO reviewDao = new ReviewDAO();
        BaseView reviewView = new ReviewView();
        BaseModel review = reviewView.updateModel(bsModel, scan);
        reviewDao.update(review);
    }

    @Override
    public List<BaseModel> getListInfo() {
        BaseDAO reviewDao = new ReviewDAO();
        return reviewDao.getListInfo();
    }

    @Override
    public BaseModel getFullInfo(int id) {
        BaseDAO reviewDao = new ReviewDAO();
        return reviewDao.getFullInfo(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) && Objects.equals(receiver, review.receiver) && Objects.equals(sender, review.sender) && Objects.equals(rate, review.rate) && Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiver, sender, rate, comment);
    }
}
