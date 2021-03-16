package net.arcticforestmc.SlimePuncher;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class SlimePuncher extends JavaPlugin {

    public static DataHandler dataHandler;


    @Override
    public void onEnable() {
        dataHandler = new DataHandler(this);
    } 
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
        if(cmd.getName().equalsIgnoreCase("testdingdong")) {
            GamePlayer test = new GamePlayer(Bukkit.getPlayer(args[0]), this); 
            System.out.println(test.getStageTree().generateDebugTreeGraph());
        }
        return(true);
    }
}
