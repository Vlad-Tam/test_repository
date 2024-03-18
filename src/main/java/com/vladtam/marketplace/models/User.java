package com.vladtam.marketplace.models;

import com.vladtam.marketplace.dao.BaseDAO;
import com.vladtam.marketplace.dao.UserDAO;
import com.vladtam.marketplace.views.BaseView;
import com.vladtam.marketplace.views.UserView;

import java.util.*;

public class User implements BaseModel{
    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;
    private Date registrationDate;
    private Address address;
    private Set<Advertisement> salesList;
    private Set<Advertisement> wishList;
    private Set<Review> commentsList;

    public User(){}

    public User(Integer id, String name, String surname, String phoneNumber, String email, String password, Date registrationDate, Address address, Set<Advertisement> salesList, Set<Advertisement> wishList, Set<Review> commentsList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.address = address;
        this.salesList = salesList;
        this.wishList = wishList;
        this.commentsList = commentsList;
    }

    public User(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Advertisement> getSalesList() {
        return salesList;
    }

    public void setSalesList(Set<Advertisement> salesList) {
        this.salesList = salesList;
    }

    public Set<Advertisement> getWishList() {
        return wishList;
    }

    public void setWishList(Set<Advertisement> wishList) {
        this.wishList = wishList;
    }

    public Set<Review> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(Set<Review> commentsList) {
        this.commentsList = commentsList;
    }

    @Override
    public String toString() {
        return "id=" + id + " " + name + " " + surname;
    }

    @Override
    public String outputFullInfo() {
        String salesListInfo = new String();
        String wishListInfo = new String();
        String commentsListInfo = new String();
        if(salesList != null) {
            for (Advertisement s : salesList) {
                salesListInfo += s;
                salesListInfo += "\n";
            }
        }else salesListInfo = "empty\n";
        if(wishList != null) {
            for (Advertisement w : wishList) {
                wishListInfo += w;
                wishListInfo += "\n";
            }
        }else wishListInfo = "empty\n";
        if(commentsList != null) {
            for (Review r : commentsList) {
                commentsListInfo += r;
                commentsListInfo += " \""+ r.getComment() +"\"";
                commentsListInfo += "\n";
            }
        }else commentsListInfo = "empty\n";
        return "USER INFO:\n" +
                "id = " + id + "\n" +
                "name = " + name + "\n" +
                "surname = " + surname + "\n" +
                "phoneNumber = " + phoneNumber + "\n" +
                "email = " + email + "\n" +
                "password = " + password + "\n" +
                "registrationDate = " + registrationDate + "\n" +
                "address = " + address + "\n\n" +
                "SALES LIST:\n" + salesListInfo + "\n" +
                "WISH LIST:\n" + wishListInfo + "\n" +
                "COMMENTS LIST:\n" + commentsListInfo + "\n";
    }

    @Override
    public int createNew(Scanner scan) {
        BaseDAO userDao = new UserDAO();
        BaseView userView = new UserView();
        return userDao.createNew(userView.createNew(scan));
    }

    @Override
    public void delete(int id) {
        BaseDAO userDao = new UserDAO();
        userDao.delete(id);
    }

    @Override
    public void update(BaseModel bsModel, Scanner scan) {
        BaseDAO userDAO = new UserDAO();
        BaseView userView = new UserView();
        BaseModel user = userView.updateModel(bsModel, scan);
        userDAO.update(user);
    }

    @Override
    public List<BaseModel> getListInfo() {
        BaseDAO userDao = new UserDAO();
        return userDao.getListInfo();
    }

    @Override
    public BaseModel getFullInfo(int id) {
        BaseDAO userDao = new UserDAO();
        return userDao.getFullInfo(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(registrationDate, user.registrationDate) && Objects.equals(address, user.address) && Objects.equals(salesList, user.salesList) && Objects.equals(wishList, user.wishList) && Objects.equals(commentsList, user.commentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phoneNumber, email, password, registrationDate, address, salesList, wishList, commentsList);
    }
}
