package net.arcticforestmc.SlimePuncher;

import org.bukkit.plugin.java.JavaPlugin;

public class SlimePuncher extends JavaPlugin {

    public static DataHandler dataHandler;


    @Override
    public void onEnable() {
        dataHandler = new DataHandler(this);
    } 


}
