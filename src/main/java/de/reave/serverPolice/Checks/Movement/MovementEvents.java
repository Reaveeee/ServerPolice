package de.reave.serverPolice.Checks.Movement;


import de.reave.serverPolice.DataManagement.PlayerData;
import de.reave.serverPolice.Utility.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import static de.reave.serverPolice.Utility.Utils.playerDataMap;
import static de.reave.serverPolice.Utility.Utils.prefixString;

public class MovementEvents implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        double hBuffer = .5;
        double vBuffer = .1;
        Player player = event.getPlayer();
        PlayerData playerData = playerDataMap.get(player);

        Vector expectedPositionVector = new Vector(playerData.getLastPosition().getX() + playerData.getLastVelocity().getX(), playerData.getLastPosition().getY() + playerData.getLastVelocity().getY(), playerData.getLastPosition().getZ() + playerData.getLastVelocity().getZ());

        if(player.isOnGround()){
            playerData.getLastVelocity().setY(0);
            Location blockLocation = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()-.1, player.getLocation().getZ());
            Block block = blockLocation.getBlock();
            if(!block.isPassable()){
                expectedPositionVector.setY(block.getBoundingBox().getMaxY());
            }
        }

        Location expectedPosition = new Location(player.getWorld(), expectedPositionVector.getX(), expectedPositionVector.getY(), expectedPositionVector.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());

        if(Math.abs(Math.sqrt(Math.pow(player.getLocation().getX() - expectedPosition.getX(), 2) + Math.pow(player.getLocation().getZ() - expectedPosition.getZ(), 2))) > hBuffer|| Math.abs(player.getLocation().getY() - expectedPosition.getY()) > vBuffer){
            flagPlayer(player, playerData, expectedPosition);
        }

        playerData.setLastPosition(player.getLocation());
        playerData.setLastVelocity(player.getVelocity());
        playerData.setLastOnGround(player.isOnGround());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        PlayerData playerData = playerDataMap.get(event.getPlayer());
        playerData.setLastPosition(event.getPlayer().getLocation());
    }

    public void flagPlayer(Player player, PlayerData playerData, Location expectedPosition) {
        player.getServer().broadcastMessage(prefixString("Unexpected Position | FlagLevel: " + playerData.getFlagLevel() + " | Expected Position: " + expectedPosition.toVector() + " | Position: " + player.getLocation().toVector(), ChatColor.RED));
        playerData.setFlagLevel(playerData.getFlagLevel() + 1);
        if (!expectedPosition.getBlock().isPassable()) {
            return;
        }
        player.teleport(expectedPosition);
    }
}
