package com.vladtam.marketplace.dao;

import com.vladtam.marketplace.databaseConnection.DatabaseHandler;
import com.vladtam.marketplace.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementDAO implements BaseDAO{
    private DatabaseHandler dbHandler = new DatabaseHandler();
    public static final Logger logger = LoggerFactory.getLogger(AdvertisementDAO.class);
    private static final String GET_FULL_INFO_REQUEST = "SELECT advert.*, categ.name AS category_name, " +
            "categ.description AS category_description, vendor.name AS vendor_name, vendor.surname AS vendor_surname " +
            "FROM advertisement advert JOIN category categ ON advert.id_category = categ.id JOIN user_account vendor " +
            "ON advert.id_vendor = vendor.id WHERE advert.id = ?;";
    private static final String GET_LIST_INFO_REQUEST = "SELECT id, name, price FROM advertisement;";
    private static final String INSERT_REQUEST = "INSERT INTO advertisement " +
            "(name, description, price, status, id_vendor, id_category) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_REQUEST = "DELETE FROM advertisement WHERE id = ?;";
    private static final String UPDATE_REQUEST = "UPDATE advertisement SET name = ?, description = ?, price = ?, status = ?, " +
            "id_vendor = ?, id_category = ?  WHERE id = ?;";

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String PRICE_COLUMN = "price";
    private static final String STATUS_COLUMN = "status";

    private static final String VENDOR_ID_COLUMN = "id_vendor";
    private static final String VENDOR_NAME_COLUMN = "vendor_name";
    private static final String VENDOR_SURNAME_COLUMN = "vendor_surname";

    private static final String CATEGORY_ID_COLUMN = "id_category";
    private static final String CATEGORY_NAME_COLUMN = "category_name";
    private static final String CATEGORY_DESCRIPTION_COLUMN = "category_description";

    public Advertisement getFullInfo(int id) {
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_FULL_INFO_REQUEST)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return  new Advertisement(rs.getInt(ID_COLUMN), rs.getString(NAME_COLUMN),
                            rs.getString(DESCRIPTION_COLUMN), rs.getDouble(PRICE_COLUMN),
                            rs.getBoolean(STATUS_COLUMN), new Category(rs.getInt(CATEGORY_ID_COLUMN),
                            rs.getString(CATEGORY_NAME_COLUMN), rs.getString(CATEGORY_DESCRIPTION_COLUMN)),
                            new User(rs.getInt(VENDOR_ID_COLUMN), rs.getString(VENDOR_NAME_COLUMN),
                            rs.getString(VENDOR_SURNAME_COLUMN)));
                }
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return null;
    }

    public List<BaseModel> getListInfo() {
        List<BaseModel> advertisementsList = new ArrayList<>();
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_LIST_INFO_REQUEST);
             ResultSet rs = preparedStatement.executeQuery()){
            while (rs.next()) {
                advertisementsList.add(new Advertisement(rs.getInt(ID_COLUMN),
                        rs.getString(NAME_COLUMN), rs.getDouble(PRICE_COLUMN)));
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return advertisementsList;
    }

    @Override
    public int createNew(BaseModel bsModel) {
        Advertisement advertisement = (Advertisement)bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, advertisement.getName());
            pstmt.setString(2, advertisement.getDescription());
            pstmt.setDouble(3, advertisement.getPrice());
            pstmt.setBoolean(4, advertisement.getSaleStatus());
            pstmt.setLong(5, advertisement.getVendor().getId());
            pstmt.setLong(6, advertisement.getCategory().getId());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.trace("A new advertisement was inserted successfully!");
            }
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Database create object error", e);
        }
        return -1;
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_REQUEST)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.trace("Advertisement was deleted successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database delete object error", e);
        }
    }

    @Override
    public void update(BaseModel bsModel) {
        Advertisement advertisement = (Advertisement)bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_REQUEST)) {
            pstmt.setString(1, advertisement.getName());
            pstmt.setString(2, advertisement.getDescription());
            pstmt.setDouble(3, advertisement.getPrice());
            pstmt.setBoolean(4, advertisement.getSaleStatus());
            pstmt.setLong(5, advertisement.getVendor().getId());
            pstmt.setLong(6, advertisement.getCategory().getId());
            pstmt.setLong(7, advertisement.getId());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.trace("Advertisement was updated successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database update object error", e);
        }
    }
}
