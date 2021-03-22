package net.arcticforestmc.SlimePuncher.Stages;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class Stage0_0_SlimePuncher extends Stage {
    private Event event;
    private GamePlayer owner;
 
    public Stage0_0_SlimePuncher(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
        //TODO Auto-generated constructor stub
    }

    

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{0,0});
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[][]{{1,0}});
    }

    @Override
    public void ownerJoinedIsland() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void onInteractEvent(PlayerInteractEvent e){
        Location slimeLocation;
        Player player = e.getPlayer();
        slimeLocation = new Location(player.getWorld(), 0, 0, 0);   //Requires configuration
        final Block clickedBlock = e.getClickedBlock();
        Location blockLocation = clickedBlock.getLocation();

        if (!(e.getAction() == Action.LEFT_CLICK_BLOCK) && !(e.getHand().equals(EquipmentSlot.HAND))){return;}

        if (blockLocation == slimeLocation){
            owner.addBits();
            player.playSound(player.getLocation(), Sound.BLOCK_SLIME_STEP, SoundCategory.BLOCKS,10, 3);
        }
    }
}
