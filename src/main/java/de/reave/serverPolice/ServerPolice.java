package de.reave.serverPolice;

import de.reave.serverPolice.Checks.Movement.MovementEvents;
import de.reave.serverPolice.DataManagement.DataManagementEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerPolice extends JavaPlugin {

    @Override
    public void onEnable() {
        //getCommand("commandname").setExecutor(new CommandClass());
        //getServer().getPluginManager().registerEvents(new EventsClass(), this);

        getServer().getPluginManager().registerEvents(new DataManagementEvents(), this);
        getServer().getPluginManager().registerEvents(new MovementEvents(), this);
    }

    @Override
    public void onDisable() {

    }
}
