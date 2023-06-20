package com.trickypr.tpHistory.store;

import com.trickypr.tpHistory.TPHistory;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends Database {

    public SQLite(TPHistory plugin, String dbName) {
        super(plugin, dbName);
    }

    @Override
    public @NotNull Connection getConnection() {
        if (connection != null) return connection;

        String path = plugin.getDataFolder() + dbName + ".sqlite";

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (SQLException e) {
            handleException("Could not connect to database: " + path, e);
        }

        return connection;
    }
}
