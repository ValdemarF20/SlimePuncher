package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.arcticforestmc.SlimePuncher.Base.StageTree;

public class StageTreeListener implements Listener{
    private StageTree stageTree;

    public StageTreeListener(StageTree stageTree){
        this.stageTree = stageTree;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();

    }
    
}
