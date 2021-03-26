package net.arcticforestmc.SlimePuncher.Base;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;


public class StageEventDispatcher implements Listener{
    private GamePlayer gamePlayerObject;

    public StageEventDispatcher(GamePlayer owner){
        this.gamePlayerObject = owner;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        gamePlayerObject.getStageTree().stages.forEach((stage) -> stage.onInteractEvent(e));
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent e){
        gamePlayerObject.getStageTree().stages.forEach((stage) -> stage.onEntityDeathEvent(e));
    }
}
