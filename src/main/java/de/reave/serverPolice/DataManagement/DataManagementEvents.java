package de.reave.serverPolice.DataManagement;

import de.reave.serverPolice.Utility.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.reave.serverPolice.Utility.Utils.playerDataMap;

public class DataManagementEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        playerDataMap.put(event.getPlayer(), new PlayerData(event.getPlayer().getLocation(), event.getPlayer().getVelocity()));
    }
}
