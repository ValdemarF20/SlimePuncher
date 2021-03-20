package net.arcticforestmc.SlimePuncher.Base;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class StageTreeListener implements Listener{
    private GamePlayer owner;

    public StageTreeListener(GamePlayer owner){
        this.owner = owner;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        owner.getStageTree().stages.forEach((stage) -> stage.onInteractEvent(e));
    }
}
