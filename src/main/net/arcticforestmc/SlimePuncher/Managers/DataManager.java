package net.arcticforestmc.SlimePuncher.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
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



    private ArrayList<GamePlayer> owners = new ArrayList<>();
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
        owners.add(owner);
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


    public GamePlayer loadGamePlayerObjectIfCan(UUID player) {

        GamePlayer target = null;

        return(target);
    }


}
