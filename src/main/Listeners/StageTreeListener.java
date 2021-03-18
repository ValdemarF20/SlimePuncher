package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class StageTreeListener implements Listener{
    private GamePlayer owner;

    public StageTreeListener(GamePlayer owner){
        this.owner = owner;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
    }
    
}
