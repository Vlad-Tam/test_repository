package com.vladtam.marketplace.models;

import com.vladtam.marketplace.dao.AddressDAO;
import com.vladtam.marketplace.dao.BaseDAO;
import com.vladtam.marketplace.views.AddressView;
import com.vladtam.marketplace.views.BaseView;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Address implements BaseModel{
    private Integer id;
    private City city;
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;

    public Address(){}

    public Address(Integer id, City city, String street, Integer houseNumber, Integer flatNumber) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public String toString() {
        return "id=" + id + " " + city.getRegion() + " обл., г." + city.getName() + ", ул." + street + " д." + houseNumber + ", кв." + flatNumber;
    }

    @Override
    public String outputFullInfo() {
        return "ADDRESS INFO:\n" +
                "id = " + id + "\n" +
                "city_id = " + city.getId() + "\n" +
                "region = " + city.getRegion() + "\n" +
                "city = " + city.getName() + "\n" +
                "street = " + street + "\n" +
                "houseNumber = " + houseNumber + "\n" +
                "flatNumber = " + flatNumber + "\n";
    }

    @Override
    public int createNew(Scanner scan) {
        BaseDAO addressDao = new AddressDAO();
        BaseView addressView = new AddressView();
        return addressDao.createNew(addressView.createNew(scan));
    }

    @Override
    public void delete(int id) {
        BaseDAO addressDao = new AddressDAO();
        addressDao.delete(id);
    }

    @Override
    public void update(BaseModel bsModel, Scanner scan) {
        BaseDAO addressDao = new AddressDAO();
        BaseView addressView = new AddressView();
        BaseModel address = addressView.updateModel(bsModel, scan);
        addressDao.update(address);
    }

    @Override
    public List<BaseModel> getListInfo() {
        BaseDAO addressDao = new AddressDAO();
        return addressDao.getListInfo();
    }

    @Override
    public BaseModel getFullInfo(int id) {
        BaseDAO addressDao = new AddressDAO();
        return addressDao.getFullInfo(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && Objects.equals(flatNumber, address.flatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, houseNumber, flatNumber);
    }
}
