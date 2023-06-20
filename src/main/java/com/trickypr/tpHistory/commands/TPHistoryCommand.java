// This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
// distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package com.trickypr.tpHistory.commands;

import com.trickypr.tpHistory.TPHistory;
import com.trickypr.tpHistory.Teleport;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TPHistoryCommand implements CommandExecutor {

    TPHistory plugin;

    public TPHistoryCommand(TPHistory plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        //noinspection SwitchStatementWithTooFewBranches
        switch (args[0]) {
            case "player" -> {
                if (args.length == 1) {
                    sender.sendMessage("Please provide a username");
                    return true;
                }
                String playerName = args[1];
                Player player = Bukkit.getPlayer(playerName);
                if (player == null) {
                    sender.sendMessage("Could not find player");
                    return true;
                }
                ArrayList<Teleport> teleports = plugin.getTeleports(player);
                for (Teleport teleport : teleports) {
                    TextComponent message = Component.text("Teleport from ")
                            .append(Component.text(teleport.fromStr()).color(NamedTextColor.BLUE).clickEvent(ClickEvent.suggestCommand("/tp " + sender.getName() + " " + teleport.fromStr())))
                            .append(Component.text(" -> "))
                            .append(Component.text(teleport.toStr()).color(NamedTextColor.BLUE).clickEvent(ClickEvent.suggestCommand("/tp " + sender.getName() + " " + teleport.toStr())));
                    sender.sendMessage(message);
                }
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
