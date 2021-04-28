package net.arcticforestmc.SlimePuncher;



import net.arcticforestmc.SlimePuncher.Commands.EnableGamePlayer;
import net.arcticforestmc.SlimePuncher.Commands.SetMobsAlive;
import net.arcticforestmc.SlimePuncher.Commands.Test;
import net.arcticforestmc.SlimePuncher.Commands.DisableGamePlayer;
import net.arcticforestmc.SlimePuncher.Managers.GamePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Managers.DataManager;

import com.sk89q.worldedit.WorldEdit;

public class SlimePuncher extends JavaPlugin {

    public static YamlDataHandler yamlDataHandler;

    public static DataManager dataManager;

    public static WorldEdit worldEdit;

    public GamePlayer test;


    protected GamePlayerManager gamePlayerManager;

    //SIZE OF ARENAS:
    public static int sizeX = 75;
    public static int sizeZ = 75;

    @Override
    public void onEnable() {
        yamlDataHandler = new YamlDataHandler(this);
        dataManager = new DataManager(this);
        gamePlayerManager = new GamePlayerManager(this);

        yamlDataHandler.createDirectoryIfMissing("plugins/SlimePuncher");
        yamlDataHandler.createDirectoryIfMissing("plugins/SlimePuncher/stageSchematics");


        this.getCommand("test").setExecutor(new Test());
        this.getCommand("disablegameplayer").setExecutor(new DisableGamePlayer(this));
        this.getCommand("enablegameplayer").setExecutor(new EnableGamePlayer(this));
        this.getCommand("setmobsalive").setExecutor(new SetMobsAlive());

        this.getServer().getPluginManager().registerEvents(new GamePlayerManager(this), this);
        
        worldEdit = WorldEdit.getInstance();

        gamePlayerHandler();
    }

    public void gamePlayerHandler(){
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (GamePlayer gp :
                    GamePlayerManager.gamePlayers.values()) {
                gp.gameTick();
            }
        }, 1L, 1L);

    }

    @Override
    public void onDisable() {
        //dataManager.update();
    }

}
