package net.arcticforestmc.SlimePuncher.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import michalec.connor.ArcticStatsAPI.StatOperation;
import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Base.SerializedGamePlayer;

public class DataManager {
    private String host = "";
    private String port = "3306";
    private String database = "";
    private String username = "";
    private String password = "";
    
    private static Connection connection;
    private Statement statement;

    StatOperation operation;



    private ArrayList<GamePlayer> players = new ArrayList<>();
    private final JavaPlugin plugin;

    public DataManager(JavaPlugin plugin) {
        operation = SlimePuncher.statsAPI.makeOperation("SlimePuncher");

        this.plugin = plugin;
        updateSchedule();

    }

    public void updateSchedule() {
        //update sql every 20 minutes, in addition to every time server restarts.
        new BukkitRunnable() {
            public void run() {
                //update();
            }
        }.runTaskTimer(plugin, 20*60*20, 20*60*20);
    }

    public void update() {
        for(GamePlayer player : players) {
            //serialize gameplayer data
            SerializedGamePlayer data = new SerializedGamePlayer(player);


            //UPDATE SQL
            
            HashMap<String, String> updateData = new HashMap<>();
            updateData.put("TRACKINGSTAGE",  player.getStageTree().getTracking().getStageIdentifier()[0]+"_"+player.getStageTree().getTracking().getStageIdentifier()[1]);
            updateData.put("BITS", String.valueOf(player.getBits()));
            updateData.put("XPBITS", String.valueOf(player.getXpBits()));
            updateData.put("ARENAXTILE", String.valueOf(player.getArenaXTile()));

            try {
                operation.setPlayerStats(player.getOwner().getUniqueId().toString(), updateData);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return;
        }
    }

    public void addGamePlayer(GamePlayer player) {
        players.add(player);
    }

    public int fetchCurrentlyRegisteredPlayers() {
        int count = 0;

        try {
            count = operation.listAllPlayers().size();

        } catch (SQLException e) {
            e.printStackTrace();
        }
   
        return(count);
    }


    public SerializedGamePlayer loadGamePlayerIfCan(UUID player) {

        SerializedGamePlayer target = null;

        try {
            if(operation.playerExists(player.toString())) {

                HashMap<String,String> res = operation.readPlayerStats(player.toString());


                target = new SerializedGamePlayer(player.toString(), res.get("TRACKINGSTAGE"), Integer.valueOf(res.get("BITS")), Integer.valueOf(res.get("XPBITS")), Integer.valueOf(res.get("ARENAXTILE")));
            }

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return(target);
    }
}
