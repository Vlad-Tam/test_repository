package com.vladtam.marketplace.dao;

import com.vladtam.marketplace.databaseConnection.DatabaseHandler;
import com.vladtam.marketplace.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO implements BaseDAO{
    private DatabaseHandler dbHandler = new DatabaseHandler();
    public static final Logger logger = LoggerFactory.getLogger(ReviewDAO.class);
    private static final String GET_FULL_INFO_REQUEST = "SELECT r.id, r.rating, r.comment, sender.id AS sender_id, sender.name AS sender_name, " +
            "sender.surname AS sender_surname, receiver.id AS receiver_id, receiver.name AS receiver_name, " +
            "receiver.surname AS receiver_surname FROM review r JOIN user_account sender ON r.id_sender = sender.id " +
            "JOIN user_account receiver ON r.id_receiver = receiver.id WHERE r.id = ?;";
    private static final String GET_LIST_INFO_REQUEST = "SELECT r.id, r.rating, sender.id AS sender_id, " +
            "sender.name AS sender_name, sender.surname AS sender_surname, receiver.id AS receiver_id, " +
            "receiver.name AS receiver_name, receiver.surname AS receiver_surname FROM review r " +
            "JOIN user_account sender ON r.id_sender = sender.id " +
            "JOIN user_account receiver ON r.id_receiver = receiver.id;";
    private static final String INSERT_REQUEST = "INSERT INTO review (rating, comment, id_sender, id_receiver) " +
            "VALUES (?, ?, ?, ?);";
    private static final String DELETE_REQUEST = "DELETE FROM review WHERE id = ?;";

    private static final String UPDATE_REQUEST = "UPDATE review SET rating = ?, comment = ?, " +
            "id_sender = ?, id_receiver = ? WHERE id = ?;";

    private static final String ID_COLUMN = "id";
    private static final String RATING_COLUMN = "rating";
    private static final String COMMENT_COLUMN = "comment";

    private static final String SENDER_ID_COLUMN = "sender_id";
    private static final String SENDER_NAME_COLUMN = "sender_name";
    private static final String SENDER_SURNAME_COLUMN = "sender_surname";

    private static final String RECEIVER_ID_COLUMN = "receiver_id";
    private static final String RECEIVER_NAME_COLUMN = "receiver_name";
    private static final String RECEIVER_SURNAME_COLUMN = "receiver_surname";

    public Review getFullInfo(int id) {
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_FULL_INFO_REQUEST)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Review(rs.getInt(ID_COLUMN), new User(rs.getInt(RECEIVER_ID_COLUMN),
                            rs.getString(RECEIVER_NAME_COLUMN), rs.getString(RECEIVER_SURNAME_COLUMN)),
                            new User(rs.getInt(SENDER_ID_COLUMN), rs.getString(SENDER_NAME_COLUMN),
                            rs.getString(SENDER_SURNAME_COLUMN)), rs.getByte(RATING_COLUMN), rs.getString(COMMENT_COLUMN));
                }
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return null;
    }

    public List<BaseModel> getListInfo() {
        List<BaseModel> reviewsList = new ArrayList<>();
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_LIST_INFO_REQUEST);
             ResultSet rs = preparedStatement.executeQuery()){
            while (rs.next()) {
                reviewsList.add(new Review(rs.getInt(ID_COLUMN), new User(rs.getInt(RECEIVER_ID_COLUMN),
                        rs.getString(RECEIVER_NAME_COLUMN), rs.getString(RECEIVER_SURNAME_COLUMN)),
                        new User(rs.getInt(SENDER_ID_COLUMN), rs.getString(SENDER_NAME_COLUMN),
                        rs.getString(SENDER_SURNAME_COLUMN)), rs.getByte(RATING_COLUMN)));
            }
        } catch (SQLException e) {
            logger.error("Database read error", e);
        }
        return reviewsList;
    }

    @Override
    public int createNew(BaseModel bsModel) {
        Review review = (Review) bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setByte(1, review.getRate());
            pstmt.setString(2, review.getComment());
            pstmt.setLong(3, review.getSender().getId());
            pstmt.setLong(4, review.getReceiver().getId());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.trace("A new review was inserted successfully!");
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
                logger.trace("Review was deleted successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database delete object error", e);
        }
    }

    @Override
    public void update(BaseModel bsModel) {
        Review review = (Review) bsModel;
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_REQUEST)) {
            pstmt.setByte(1, review.getRate());
            pstmt.setString(2, review.getComment());
            pstmt.setLong(3, review.getSender().getId());
            pstmt.setLong(4, review.getReceiver().getId());
            pstmt.setLong(5, review.getId());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.trace("Review was updated successfully!");
            }
        } catch (SQLException e) {
            logger.error("Database update object error", e);
        }
    }
}
