package net.arcticforestmc.SlimePuncher;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Managers.DataManager;

public class SlimePuncher extends JavaPlugin {

    public static YamlDataHandler yamlDataHandler;

    public static DataManager dataManager;

    @Override
    public void onEnable() {
        yamlDataHandler = new YamlDataHandler(this);
        dataManager = new DataManager(this);
    } 

    @Override
    public void onDisable() {
        dataManager.update();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
        if(cmd.getName().equalsIgnoreCase("testdingdong")) {
            GamePlayer test = new GamePlayer(Bukkit.getPlayer(args[0]), this); 

            //create a handler class for all the gamerplayers, this is fine for now tho
            
            new BukkitRunnable(){
                @Override
                public void run() {
                    test.gameTick();
                }
            }.runTaskTimer(this, 0, 0);
        }
        return(true);
    }
}
