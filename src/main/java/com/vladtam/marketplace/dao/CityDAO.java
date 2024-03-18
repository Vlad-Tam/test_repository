package com.vladtam.marketplace.dao;

import com.vladtam.marketplace.databaseConnection.DatabaseHandler;
import com.vladtam.marketplace.models.BaseModel;
import com.vladtam.marketplace.models.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements BaseDAO{
    private DatabaseHandler dbHandler = new DatabaseHandler();
    public static final Logger logger = LoggerFactory.getLogger(CityDAO.class);
    private static final String GET_FULL_INFO_REQUEST = "SELECT * FROM city WHERE id = ?;";
    private static final String GET_LIST_INFO_REQUEST = "SELECT * FROM city;";
    private static final String INSERT_REQUEST = "INSERT INTO city (name, region) VALUES (?, ?);";
    private static final String DELETE_REQUEST = "DELETE FROM city WHERE id = ?;";
    private static final String UPDATE_REQUEST = "UPDATE city SET name = ?, region = ? WHERE id = ?;";

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String REGION_COLUMN = "region";

    public City getFullInfo(int id) {
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_FULL_INFO_REQUEST)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new City(rs.getInt(ID_COLUMN), rs.getString(NAME_COLUMN), rs.getString(REGION_COLUMN));
                }
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return null;
    }

    public List<BaseModel> getListInfo() {
        List<BaseModel> citiesList = new ArrayList<>();
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_LIST_INFO_REQUEST);
             ResultSet rs = preparedStatement.executeQuery()){
            while (rs.next()) {
                citiesList.add(new City(rs.getInt(ID_COLUMN), rs.getString(NAME_COLUMN), rs.getString(REGION_COLUMN)));
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return citiesList;
    }

    @Override
    public int createNew(BaseModel bsModel) {
        City city = (City)bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, city.getName());
            pstmt.setString(2, city.getRegion());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.trace("A new city was inserted successfully!");
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
                logger.trace("City was deleted successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database delete object error", e);
        }
    }

    @Override
    public void update(BaseModel bsModel) {
        City city = (City) bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_REQUEST)) {
            pstmt.setString(1, city.getName());
            pstmt.setString(2, city.getRegion());
            pstmt.setLong(3, city.getId());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.trace("City was updated successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database update object error", e);
        }
    }
}