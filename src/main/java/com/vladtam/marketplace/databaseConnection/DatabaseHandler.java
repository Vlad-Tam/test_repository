package com.vladtam.marketplace.databaseConnection;

import com.vladtam.marketplace.dao.AddressDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    Connection dbconnection;
    public static final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);

    public Connection getConnection(){
        String connectionString = "jdbc:postgresql://" + Config.dbHost + ":" + Config.dbPort +
                "/" + Config.dbName + "?autoReconnect=true&useSSL=false";
        try {
            Class.forName("org.postgresql.Driver");
            dbconnection = DriverManager.getConnection(connectionString, Config.dbUser, Config.dbPass);
        }catch(ClassNotFoundException e1){
            logger.error("Database connect ClassNotFoundException error", e1);
        } catch(SQLException e2){
            logger.error("Database connect SQLException error", e2);
        }
        return dbconnection;
    }
}
