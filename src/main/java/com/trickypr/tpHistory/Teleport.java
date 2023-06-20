// This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
// distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package com.trickypr.tpHistory;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Teleport {
    Player player;
    Location from;
    Location to;

    public Teleport(Player player, Location from, Location to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public static ArrayList<Teleport> getFromDB(Player player, Statement statement) throws SQLException {
        ArrayList<Teleport> teleports = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM teleports WHERE (uuid = '"+ player.getUniqueId() +"')");

        while (resultSet.next()) {
            Location from = new Location(player.getWorld(), resultSet.getDouble("from_x"), resultSet.getDouble("from_y"), resultSet.getDouble("from_z"));
            Location to = new Location(player.getWorld(), resultSet.getDouble("to_x"), resultSet.getDouble("to_y"), resultSet.getDouble("to_z"));
            teleports.add(new Teleport(player, from, to));
        }

        return teleports;
    }

    public void add(Statement statement) throws SQLException {
        statement.executeUpdate("INSERT INTO teleports (uuid, from_x, from_y, from_z, to_x, to_y, to_z)"
                + "VALUES('" + player.getUniqueId() + "', "
                + from.getBlockX() + "," + from.getBlockY() + "," + from.getBlockZ() + ","
                + to.getBlockX() + "," + to.getBlockY() + "," + to.getBlockZ() + ")");
    }

    public String fromStr() {
        return formatLocation(from);
    }

    public String toStr() {
        return formatLocation(to);
    }

    public String formatLocation(Location loc) {
        return loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }
}
