package net.arcticforestmc.SlimePuncher;


import com.sk89q.worldedit.WorldEdit;

import net.arcticforestmc.SlimePuncher.Base.EntityHider;
import net.arcticforestmc.SlimePuncher.Stages.Stage0_0_SlimePuncher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Managers.DataManager;

import static net.arcticforestmc.SlimePuncher.Base.EntityHider.Policy.BLACKLIST;

public class SlimePuncher extends JavaPlugin {

    public static YamlDataHandler yamlDataHandler;

    public static DataManager dataManager;

    public static WorldEdit worldEdit;

    public GamePlayer test;

    protected EntityHider entityHider;

    //SIZE OF ARENAS:
    public static int sizeX = 100;
    public static int sizeZ = 100;

    @Override
    public void onEnable() {
        yamlDataHandler = new YamlDataHandler(this);
        dataManager = new DataManager(this);
        entityHider = new EntityHider(this, BLACKLIST);

        System.out.println(entityHider);
        
        if(Bukkit.getPluginManager().getPlugin("WorldEdit")==null) {
            System.out.println("WorldEdit MUST Be installed");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        
        worldEdit = WorldEdit.getInstance();
        
    } 

    @Override
    public void onDisable() {
        //dataManager.update();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
        if(cmd.getName().equalsIgnoreCase("testdingdong")) {
            test = new GamePlayer(Bukkit.getPlayer(args[0]), this);

            //create a handler class for all the gamerplayers, this is fine for now tho
            
            new BukkitRunnable(){
                @Override
                public void run() {
                    test.gameTick();
                }
            }.runTaskTimer(this, 0, 0);

            Stage0_0_SlimePuncher slimePuncherStage = (Stage0_0_SlimePuncher) test.getStageTree().getStageFromIdentifier("0_0");
            int mobsAlive = slimePuncherStage.getMobsAlive();
            sender.sendMessage(String.valueOf(mobsAlive));

        }
        return(true);
    }
    public EntityHider getEntityHider(){
        return entityHider;
    }
}
