package com.vladtam.marketplace.models;

import java.util.List;
import java.util.Scanner;

public interface BaseModel {
    List<BaseModel> getListInfo();
    String outputFullInfo();
    Integer getId();
    BaseModel getFullInfo(int id);
    int createNew(Scanner scan);
    void delete(int id);
    void update(BaseModel bsModel, Scanner scan);
}
