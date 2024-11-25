package de.reave.serverPolice.utility;

import de.reave.serverPolice.dataManagement.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Utils {
    public static HashMap<Player, PlayerData> playerDataMap = new HashMap<>();
    public static String prefixString(String text, ChatColor chatColor){
        return ChatColor.BLUE + "[SP] " + chatColor + text;
    }

    public static boolean isPlayerOnGround(Player player){
        double checkX = 0, checkZ = 0;
        double piece = (player.getBoundingBox().getWidthX() - .01) / 16;
        for(int x = -8; x <= 8; x++){
            checkX = player.getLocation().getX() + x * piece;
            for(int z = -8; z <= 8; z++){
                checkZ = player.getLocation().getZ() + z * piece;
                Location checkLocation = new Location(player.getWorld(), checkX,  player.getLocation().getY() - 0.00001, checkZ);
                if(!checkLocation.getBlock().isPassable()) return true;
            }
        }
        return false;
    }

    public static Block getBlockPlayerStandingOn(Player player){
        double checkX = 0, checkZ = 0;
        double piece = (player.getBoundingBox().getWidthX() - .01) / 16;
        for(int x = -8; x <= 8; x++){
            checkX = player.getLocation().getX() + x * piece;
            for(int z = -8; z <= 8; z++){
                checkZ = player.getLocation().getZ() + z * piece;
                Location checkLocation = new Location(player.getWorld(), checkX,  player.getLocation().getY() - 0.00001, checkZ);
                if(!checkLocation.getBlock().isPassable()) return checkLocation.getBlock();
            }
        }
        return null;
    }
}
