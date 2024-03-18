package com.vladtam.marketplace.models;

import com.vladtam.marketplace.dao.BaseDAO;
import com.vladtam.marketplace.dao.CityDAO;
import com.vladtam.marketplace.views.BaseView;
import com.vladtam.marketplace.views.CityView;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class City implements BaseModel {
    private Integer id;
    private String name;
    private String region;
    private static CityDAO cityDao = new CityDAO();

    public City(){}

    public City(Integer id, String name, String region) {
        this.id = id;
        this.name = name;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "id=" + id + ", г." + name + ", " + region + " обл.";
    }

    @Override
    public String outputFullInfo() {
        return "CITY INFO:\n" +
                "id = " + id + "\n" +
                "name = " + name + "\n" +
                "region = " + region + "\n";
    }

    @Override
    public int createNew(Scanner scan) {
        BaseDAO cityDao = new CityDAO();
        BaseView cityView = new CityView();
        return cityDao.createNew(cityView.createNew(scan));
    }

    @Override
    public void delete(int id) {
        BaseDAO cityDao = new CityDAO();
        cityDao.delete(id);
    }

    @Override
    public void update(BaseModel bsModel, Scanner scan) {
        BaseDAO cityDao = new CityDAO();
        BaseView cityView = new CityView();
        BaseModel city = cityView.updateModel(bsModel, scan);
        cityDao.update(city);
    }

    @Override
    public List<BaseModel> getListInfo() {
        BaseDAO cityDao = new CityDAO();
        return cityDao.getListInfo();
    }

    @Override
    public BaseModel getFullInfo(int id) {
        BaseDAO cityDao = new CityDAO();
        return cityDao.getFullInfo(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) && Objects.equals(name, city.name) && Objects.equals(region, city.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, region);
    }
}
