package net.arcticforestmc.SlimePuncher.Stages;

import org.bukkit.Location;
import org.bukkit.Material;
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
 
    public Stage0_0_SlimePuncher(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
        //TODO Auto-generated constructor stub



        //TEST
        spawnEnemyTick();
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
        //Requires configuration
        int slimeBlockRelativeX = 0;
        int slimeBlockRelativeY = 10;
        int slimeBlockRelativeZ = 0;
        
        Player player = e.getPlayer();
        Location slimeLocation = new Location(player.getWorld(), gameObject.getStageTile() + slimeBlockRelativeX, slimeBlockRelativeY, slimeBlockRelativeZ);   
        final Block clickedBlock = e.getClickedBlock();
        Location blockLocation = clickedBlock.getLocation();

        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
            if (blockLocation.equals(slimeLocation)) {
                //owner.addBits();
                player.playSound(player.getLocation(), Sound.BLOCK_SLIME_STEP, SoundCategory.BLOCKS,10, 3);
            }
        }
    }



    @Override
    public boolean canProgressStage() {
        return (gameObject.getPlayerLevel()>=5);
    }

    /**
     * Go around the arena edge once and spawn enemies randomly
     */
    private void spawnEnemyTick() {
        final float circleRadians = (float) (2.0F*Math.PI); //Radians in a circle idk google: https://socratic.org/questions/how-do-you-convert-360-degrees-to-radianss

        //requires configuration
        int arenaFloorRelativeX = 0;
        int arenaFloorRelativeY = 100;
        int arenaFloorRelativeZ = 0;
        float stepSize = 0.01F;         //size of each step
        int radius = 50;                //radius in blocks



        for(double step = 0; step<circleRadians; step+=stepSize) {
            int x = (int) Math.round(Math.cos(step) * radius) + gameObject.getStageTile() + arenaFloorRelativeX;
            int z = (int) Math.round(Math.sin(step) * radius) + arenaFloorRelativeZ;

            gameObject.getOwner().getWorld().getBlockAt(x, arenaFloorRelativeY, z).setType(Material.STONE);
        }
    }



    @Override
    public void gameTick() {
        // TODO Auto-generated method stub
        
    }
    
}
