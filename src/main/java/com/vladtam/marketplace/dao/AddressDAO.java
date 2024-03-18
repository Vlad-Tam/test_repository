package com.vladtam.marketplace.dao;

import com.vladtam.marketplace.databaseConnection.DatabaseHandler;
import com.vladtam.marketplace.models.Address;
import com.vladtam.marketplace.models.BaseModel;
import com.vladtam.marketplace.models.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO implements BaseDAO{
    private DatabaseHandler dbHandler = new DatabaseHandler();
    public static final Logger logger = LoggerFactory.getLogger(AddressDAO.class);
    private static final String GET_FULL_INFO_REQUEST = "SELECT addr.*, ci.name AS city_name, ci.region " +
            "AS city_region FROM address addr JOIN city ci ON addr.id_city = ci.id WHERE addr.id = ?;";
    private static final String GET_LIST_INFO_REQUEST = "SELECT addr.*, ci.name AS city_name, ci.region AS city_region " +
            "FROM address addr JOIN city ci ON addr.id_city = ci.id;";
    private static final String INSERT_REQUEST = "INSERT INTO address (street, house_number, flat_number, id_city) " +
            "VALUES (?, ?, ?, ?);";
    private static final String DELETE_REQUEST = "DELETE FROM address WHERE id = ?;";
    private static final String UPDATE_REQUEST = "UPDATE address " +
            "SET street = ?, house_number = ?, flat_number = ?, id_city = ? WHERE id = ?";

    private static final String ID_COLUMN = "id";
    private static final String STREET_COLUMN = "street";
    private static final String HOUSE_COLUMN = "house_number";
    private static final String FLAT_COLUMN = "flat_number";

    private static final String CITY_ID_COLUMN = "id_city";
    private static final String CITY_NAME_COLUMN = "city_name";
    private static final String CITY_REGION_COLUMN = "city_region";

    public Address getFullInfo(int id) {
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_FULL_INFO_REQUEST)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Address(rs.getInt(ID_COLUMN), new City(rs.getInt(CITY_ID_COLUMN), rs.getString(CITY_NAME_COLUMN),
                            rs.getString(CITY_REGION_COLUMN)), rs.getString(STREET_COLUMN), rs.getInt(HOUSE_COLUMN), rs.getInt(FLAT_COLUMN));
                }
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return null;
    }

    public List<BaseModel> getListInfo() {
        List<BaseModel> addressesList = new ArrayList<>();
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_LIST_INFO_REQUEST);
             ResultSet rs = preparedStatement.executeQuery()){
            while (rs.next()) {
                addressesList.add(new Address(rs.getInt(ID_COLUMN), new City(rs.getInt(CITY_ID_COLUMN), rs.getString(CITY_NAME_COLUMN),
                        rs.getString(CITY_REGION_COLUMN)), rs.getString(STREET_COLUMN), rs.getInt(HOUSE_COLUMN), rs.getInt(FLAT_COLUMN)));
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return addressesList;
    }

    @Override
    public int createNew(BaseModel bsModel) {
        Address address = (Address)bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, address.getStreet());
            pstmt.setInt(2, address.getHouseNumber());
            pstmt.setInt(3, address.getFlatNumber());
            pstmt.setLong(4, address.getCity().getId());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.trace("A new address was inserted successfully!");
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
                logger.trace("Address was deleted successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database delete object error", e);
        }
    }

    @Override
    public void update(BaseModel bsModel) {
        Address address = (Address)bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_REQUEST)) {
            pstmt.setString(1, address.getStreet());
            pstmt.setInt(2, address.getHouseNumber());
            pstmt.setInt(3, address.getFlatNumber());
            pstmt.setLong(4, address.getCity().getId());
            pstmt.setLong(5, address.getId());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.trace("Address was updated successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database update object error", e);
        }
    }
}
