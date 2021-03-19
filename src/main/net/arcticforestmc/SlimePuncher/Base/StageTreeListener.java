package net.arcticforestmc.SlimePuncher.Base;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public class StageTreeListener implements Listener{
    private GamePlayer owner;
    public Location slimeLocation;

    public StageTreeListener(GamePlayer owner){
        this.owner = owner;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        slimeLocation = new Location(player.getWorld(), 0, 0, 0);   //Requires configuration
        final Block clickedBlock = e.getClickedBlock();
        Location blockLocation = clickedBlock.getLocation();

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK) && !(e.getHand().equals(EquipmentSlot.HAND))){return;}

        if (blockLocation == slimeLocation){
            owner.addBits();
        }
        owner.getStageTree().stages.forEach((stage) -> stage.onInteractEvent(e));
    }
}
