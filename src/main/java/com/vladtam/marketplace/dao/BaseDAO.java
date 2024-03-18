package com.vladtam.marketplace.dao;

import com.vladtam.marketplace.models.BaseModel;

import java.util.List;

public interface BaseDAO {
    BaseModel getFullInfo(int id);
    List<BaseModel> getListInfo();
    int createNew(BaseModel bsModel);
    void delete(int id);
    void update(BaseModel bsModel);
}
