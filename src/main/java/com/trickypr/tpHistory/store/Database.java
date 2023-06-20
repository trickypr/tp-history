package com.trickypr.tpHistory.store;

import com.trickypr.tpHistory.TPHistory;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {
    TPHistory plugin;
    String dbName;

    @Nullable protected Connection connection;

    public Database(TPHistory plugin, String dbName) {
        this.plugin = plugin;
        this.dbName = dbName;
    }

    @NotNull
    public abstract Connection getConnection();

    public Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public void close() throws SQLException {
        if (connection == null) return;

        connection.close();
        connection = null;
    }

    public void handleException(String context, SQLException e) {
        Bukkit.getLogger().severe(context + ". Exiting");
        Bukkit.getLogger().severe(e.getMessage());
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
}
