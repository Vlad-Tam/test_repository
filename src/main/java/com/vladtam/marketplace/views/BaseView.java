package com.vladtam.marketplace.views;

import com.vladtam.marketplace.models.BaseModel;

import java.util.Scanner;

public interface BaseView {
    BaseModel createNew(Scanner scan);
    void outputPage(int id);
    BaseModel updateModel(BaseModel bsModel, Scanner scan);
}
