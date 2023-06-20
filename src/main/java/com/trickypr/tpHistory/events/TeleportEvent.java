package com.trickypr.tpHistory.events;

import com.trickypr.tpHistory.TPHistory;
import com.trickypr.tpHistory.Teleport;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportEvent implements Listener {
    TPHistory plugin;

    public TeleportEvent(TPHistory plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Teleport teleport = new Teleport(e.getPlayer(), e.getFrom(), e.getTo());
        plugin.logTeleport(teleport);
    }

}
