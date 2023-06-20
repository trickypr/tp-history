// This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
// distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package com.trickypr.tpHistory;

import com.trickypr.tpHistory.commands.TPHistoryCommand;
import com.trickypr.tpHistory.events.TeleportEvent;
import com.trickypr.tpHistory.store.Database;
import com.trickypr.tpHistory.store.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public final class TPHistory extends JavaPlugin {
    Database db;

    @Override
    public void onEnable() {
        // Connect to database
        db = new SQLite(this, "teleports");

        // Initialize database
        try {
            Statement stmt = db.getStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS teleports (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    +"uuid TEXT NOT NULL,"
                    +"from_x INTEGER NOT NULL,"
                    +"from_y INTEGER NOT NULL,"
                    +"from_z INTEGER NOT NULL,"
                    +"to_x INTEGER NOT NULL,"
                    +"to_y INTEGER NOT NULL,"
                    +"to_z INTEGER NOT NULL)");
        } catch (SQLException e) {
            db.handleException("Could not initialize teleports table", e);
            return;
        }

        TeleportEvent tpEventHandler = new TeleportEvent(this);
        TPHistoryCommand command = new TPHistoryCommand(this);

        Bukkit.getPluginManager().registerEvents(tpEventHandler, this);
        Objects.requireNonNull(getCommand("tphist")).setExecutor(command);
    }

    @Override
    public void onDisable() {
        try {
            db.close();
        } catch (SQLException e) {
            db.handleException("Failed to close teleports database", e);
        }
    }

    public void logTeleport(Teleport teleport) {
        try {
            teleport.add(db.getStatement());
        } catch (SQLException e) {
            db.handleException("Failed to log teleport", e);
        }
    }

    public ArrayList<Teleport> getTeleports(Player player) {
        try {
            return Teleport.getFromDB(player, db.getStatement());
        } catch (SQLException e) {
            db.handleException("Failed to query teleport", e);
        }

        return new ArrayList<>();
    }
}
