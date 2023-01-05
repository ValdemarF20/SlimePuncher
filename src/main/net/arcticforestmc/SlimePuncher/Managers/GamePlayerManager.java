package net.arcticforestmc.SlimePuncher.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Stages.Stage0_0_SlimePuncher;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class GamePlayerManager implements Listener {
    private final SlimePuncher slimePuncher;
    
    public GamePlayerManager(SlimePuncher slimePuncher){
        this.slimePuncher = slimePuncher;
    }

    public static HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!gamePlayers.containsKey(e.getPlayer().getUniqueId())) {

            GamePlayer obj = new GamePlayer(player, slimePuncher);
            gamePlayers.put(player.getUniqueId(), obj);

            Stage0_0_SlimePuncher stage0_0 = (Stage0_0_SlimePuncher) obj.getStageTree().getStageFromIdentifier("0_0");
            stage0_0.spawnEnemies();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        gamePlayers.remove(e.getPlayer().getUniqueId());
    }
}
