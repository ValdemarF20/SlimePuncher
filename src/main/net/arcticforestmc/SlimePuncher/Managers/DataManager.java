package net.arcticforestmc.SlimePuncher.Managers;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Base.SerializedGamePlayer;

public class DataManager {


    private ArrayList<GamePlayer> owners = new ArrayList<>();
    private JavaPlugin plugin;

    public DataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        updateSchedule();
    }

    public void updateSchedule() {
        //update sql every 20 minutes, in addition to every time server restarts.
        new BukkitRunnable() {
            public void run() {
                update();
            }
        }.runTaskTimer(plugin, 20*60*20, 20*60*20);
    }

    public void update() {
        for(GamePlayer owner : owners) {
            //serialize gameplayer data
            SerializedGamePlayer data = new SerializedGamePlayer(owner);

            //write to SQL
        }
    }

    public void addOwner(GamePlayer owner) {

    }
}
