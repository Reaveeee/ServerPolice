package de.reave.serverPolice.Utility;

import de.reave.serverPolice.DataManagement.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Utils {
    public static HashMap<Player, PlayerData> playerDataMap = new HashMap<>();
    public static String prefixString(String text, ChatColor chatColor){
        return ChatColor.BLUE + "[SP] " + chatColor + text;
    }
}
