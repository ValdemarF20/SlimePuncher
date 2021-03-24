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

                //TODO: Not most efficient, probably incorporate this into addOwner
                //Check if row exists with uuid:
                ResultSet result = statement.executeQuery("SELECT EXISTS(SELECT * from SlimePuncher WHERE UUID="+player.getOwner().getUniqueId().toString()+");");
                while(result.next()) {
                    if(result.getBoolean("EXISTS")) {
                        //UPDATE SQL
                        statement.executeUpdate("");
                        return;
                    }
                }

                //write NEW to SQL
                statement.executeUpdate(String.format(
                    "INSERT INTO SlimePuncher (UUID,TRACKINGSTAGE,BITS,XPBITS,STAGETILE) VALUES (%s, %s. %d. %d, %d)",
                    player.getOwner().getUniqueId().toString(), player.getStageTree().getTracking().getStageIdentifier()[0]+"_"+player.getStageTree().getTracking().getStageIdentifier()[1], player.getBits(), player.getXpBits(), player.getStageTile()));
        
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

        return(target);
    }
}
