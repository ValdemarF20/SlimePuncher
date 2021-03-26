package net.arcticforestmc.SlimePuncher.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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



    private ArrayList<GamePlayer> players = new ArrayList<>();
    private JavaPlugin plugin;

    public DataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        updateSchedule();

        try {
            openConnection();
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSchedule() {
        //update sql every 20 minutes, in addition to every time server restarts.
        new BukkitRunnable() {
            public void run() {
            }
        }.runTaskTimer(plugin, 20*60*20, 20*60*20);
    }

    public void update() {
        for(GamePlayer player : players) {
            try {
                //serialize gameplayer data
                SerializedGamePlayer data = new SerializedGamePlayer(player);


                if(loadGamePlayerIfCan(player.getOwner().getUniqueId())!=null) { //does the uuid exist in database
                    //UPDATE SQL
                    statement.executeUpdate(String.format("UPDATE SlimePuncher SET TRACKINGSTAGE='%s', BITS=%d, XPBITS=%d, ARENAXTILE=%d WHERE UUID='%s'",
                    player.getStageTree().getTracking().getStageIdentifier()[0]+"_"+player.getStageTree().getTracking().getStageIdentifier()[1],  player.getBits(),  player.getXpBits(),  player.getArenaXTile(), player.getOwner().getUniqueId().toString()));
                    return;
                }

                //it doesnt so write NEW to SQL
                statement.executeUpdate(String.format(
                    "INSERT INTO SlimePuncher (UUID,TRACKINGSTAGE,BITS,XPBITS,ARENAXTILE) VALUES ('%s', '%s'. %d. %d, %d)",
                    player.getOwner().getUniqueId().toString(), player.getStageTree().getTracking().getStageIdentifier()[0]+"_"+player.getStageTree().getTracking().getStageIdentifier()[1], player.getBits(), player.getXpBits(), player.getArenaXTile()));
        
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    public void addGamePlayer(GamePlayer player) {
        players.add(player);
    }

    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                + this.host + ":" + this.port + "/" + this.database,
                this.username, this.password);
    }


    public SerializedGamePlayer loadGamePlayerIfCan(UUID player) {

        SerializedGamePlayer target = null;

        try {
            ResultSet existResult = statement.executeQuery("SELECT EXISTS(SELECT * from SlimePuncher WHERE UUID="+player+");");
            //only 1 row is needed because only 1 row should match
            if(existResult.getBoolean("EXISTS")) {
                ResultSet dataResult = statement.executeQuery("SELECT * from SlimePuncher WHERE UUID="+player+");");
                


                target = new SerializedGamePlayer(dataResult.getString("UUID"), dataResult.getString("TRACKINGSTAGE"), dataResult.getInt("BITS"), dataResult.getInt("XPBITS"), dataResult.getInt("ARENAXTILE"));
            }
        } catch(SQLException e) {

        }

        return(target);
    }
}
