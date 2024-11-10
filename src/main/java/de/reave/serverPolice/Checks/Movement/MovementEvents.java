package de.reave.serverPolice.Checks.Movement;


import de.reave.serverPolice.DataManagement.PlayerData;
import de.reave.serverPolice.Utility.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import static de.reave.serverPolice.Utility.Utils.*;

public class MovementEvents implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        PlayerData playerData = playerDataMap.get(event.getPlayer());
        playerData.setLastPosition(event.getPlayer().getLocation());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        double hBuffer = .5;
        double vBuffer = 0;
        Player player = event.getPlayer();
        PlayerData playerData = playerDataMap.get(player);

        double acceleration = playerData.getLastVelocity().getY();

        Vector expectedPositionVector = new Vector(playerData.getLastPosition().getX() + playerData.getLastVelocity().getX(), playerData.getLastPosition().getY() + playerData.getLastVelocity().getY(), playerData.getLastPosition().getZ() + playerData.getLastVelocity().getZ());

        expectedPositionVector = fixExpectedPositionVector(expectedPositionVector, player, playerData);

        Location expectedPosition = new Location(player.getWorld(), expectedPositionVector.getX(), expectedPositionVector.getY(), expectedPositionVector.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());

        //Checks
            //Position
        if(Math.abs(Math.sqrt(Math.pow(player.getLocation().getX() - expectedPosition.getX(), 2) + Math.pow(player.getLocation().getZ() - expectedPosition.getZ(), 2))) > hBuffer|| Math.abs(player.getLocation().getY() - expectedPosition.getY()) > vBuffer){
            flagPlayerPosition(player, playerData, expectedPosition, event);
        }
            //OnGround
        if(player.isOnGround() != isPlayerOnGround(player)){
            flagPlayerSpoofGround(player, playerData);
        }

        playerData.setLastPosition(player.getLocation());
        playerData.setLastVelocity(player.getVelocity());
        playerData.setLastOnGround(isPlayerOnGround(player));
    }

    public void flagPlayerPosition(Player player, PlayerData playerData, Location expectedPosition, PlayerMoveEvent event) {
        player.getServer().broadcastMessage(prefixString("Unexpected Position | FlagLevel: " + playerData.getFlagLevel() + " | Expected Position: " + expectedPosition.toVector() + " | Position: " + player.getLocation().toVector(), ChatColor.RED));
        playerData.setFlagLevel(playerData.getFlagLevel() + 1);
        if (!expectedPosition.getBlock().isPassable()) {
            player.teleport(new Location(player.getWorld(), playerData.getLastPosition().getX(), playerData.getLastPosition().getY(), playerData.getLastPosition().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
            return;
        }
        player.teleport(expectedPosition);
    }

    public void flagPlayerSpoofGround(Player player, PlayerData playerData){
        player.getServer().broadcastMessage(prefixString("Unexpected OnGround state | FlagLevel: " + playerData.getFlagLevel(), ChatColor.RED));
        playerData.setFlagLevel(playerData.getFlagLevel() + 1);
    }

    private Vector fixExpectedPositionVector(Vector vector, Player player, PlayerData playerData){
        //Case: Player is on ground
        if(isPlayerOnGround(player)){
            playerData.getLastVelocity().setY(0);
            vector.setY(getBlockPlayerStandingOn(player).getBoundingBox().getMaxY());
        }

        return vector;
    }
}
