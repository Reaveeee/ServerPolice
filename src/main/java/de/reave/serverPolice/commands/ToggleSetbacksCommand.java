package de.reave.serverPolice.commands;

import de.reave.serverPolice.dataManagement.PlayerData;
import de.reave.serverPolice.utility.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleSetbacksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;
        PlayerData playerData = Utils.playerDataMap.get(player);
        playerData.setSetbacks(!playerData.isSetbacks());
        player.sendMessage(Utils.prefixString("Set setbacks to: " + playerData.isSetbacks(), ChatColor.GREEN));
        return true;
    }
}
