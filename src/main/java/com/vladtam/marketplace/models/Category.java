package com.vladtam.marketplace.models;

import com.vladtam.marketplace.dao.*;
import com.vladtam.marketplace.views.BaseView;
import com.vladtam.marketplace.views.CategoryView;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Category implements BaseModel{
    private Integer id;
    private String name;
    private String description;

    public Category(){}

    public Category(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "id=" + id + " " + name;
    }

    @Override
    public String outputFullInfo() {
        return "CATEGORY INFO:\n" +
                "id = " + id + "\n" +
                "name = " + name + "\n" +
                "description = " + description + "\n";
    }

    @Override
    public int createNew(Scanner scan) {
        BaseDAO categoryDao = new CategoryDAO();
        BaseView categoryView = new CategoryView();
        return categoryDao.createNew(categoryView.createNew(scan));
    }

    @Override
    public List<BaseModel> getListInfo() {
        CategoryDAO categoryDao = new CategoryDAO();
        return categoryDao.getListInfo();
    }

    @Override
    public void delete(int id) {
        BaseDAO categoryDao = new CategoryDAO();
        categoryDao.delete(id);
    }

    @Override
    public void update(BaseModel bsModel, Scanner scan) {
        BaseDAO categoryDao = new CategoryDAO();
        BaseView categoryView = new CategoryView();
        BaseModel cathegory = categoryView.updateModel(bsModel, scan);
        categoryDao.update(cathegory);
    }

    @Override
    public BaseModel getFullInfo(int id) {
        BaseDAO categoryDao = new CategoryDAO();
        return categoryDao.getFullInfo(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
