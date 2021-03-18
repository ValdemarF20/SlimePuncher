package net.arcticforestmc.SlimePuncher.Base;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.arcticforestmc.SlimePuncher.Stages.Stage;


public class StageTreeListener implements Listener{
    private GamePlayer owner;

    public StageTreeListener(GamePlayer owner){
        this.owner = owner;
    }

    private void sendEvents(Event event) {

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        owner.getStageTree().stages.forEach((stage) -> stage.onInteractEvent(e));
    }
    
}
