package net.arcticforestmc.SlimePuncher.Base;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;


public class StageEventDispatcher implements Listener{
    private final GamePlayer gamePlayerObject;

    //TODO: Maybe make it work for all gameplayers so you only need one instance
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

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        gamePlayerObject.getStageTree().stages.forEach((stage) -> stage.onEntityDamageEvent(e));
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent e) {
        gamePlayerObject.getStageTree().stages.forEach((stage) -> stage.onProjectileHitEvent(e));
    }
}
