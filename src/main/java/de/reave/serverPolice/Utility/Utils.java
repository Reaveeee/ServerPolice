package de.reave.serverPolice.Utility;

import org.bukkit.ChatColor;

public class Utils {
    public static String prefixString(String text, ChatColor chatColor){
        return ChatColor.BLUE + "[SP] " + chatColor + text;
    }
}
