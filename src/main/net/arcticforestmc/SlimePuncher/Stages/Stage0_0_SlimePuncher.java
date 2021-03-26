package net.arcticforestmc.SlimePuncher.Stages;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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
    public void ownerJoinedArena() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void onInteractEvent(PlayerInteractEvent e){  
        //Requires configuration
        int slimeBlockRelativeX = 0;
        int slimeBlockRelativeY = 10;
        int slimeBlockRelativeZ = 0;
        
        Player player = e.getPlayer();
        Location slimeLocation = new Location(player.getWorld(), gamePlayerObject.getArenaXTile() + slimeBlockRelativeX, slimeBlockRelativeY, gamePlayerObject.getStageZTile() + slimeBlockRelativeZ);
        final Block clickedBlock = e.getClickedBlock();
        if(clickedBlock!=null) {
            Location blockLocation = clickedBlock.getLocation();

            double blockX = blockLocation.getX();
            double blockY = blockLocation.getY();
            double blockZ = blockLocation.getZ();

            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
                if (blockLocation.equals(slimeLocation)) {
                    gamePlayerObject.addBits(1);
                    player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS,10, 3);
                    player.getWorld().spawnParticle(Particle.SLIME, blockX, blockY, blockZ, 1);
                }
            }
        }
    }

    @Override
    public boolean canProgressStage() {
        return (gamePlayerObject.getOwnerLevel()>=5);
    }

    /**
     * Go around the arena edge once and spawn enemies randomly
     */
    private int mobsAlive = 0;
    private void spawnEnemyTick() {
        final float circleRadians = (float) (2.0F*Math.PI); //Radians in a circle idk google: https://socratic.org/questions/how-do-you-convert-360-degrees-to-radianss

        //requires configuration
        int arenaFloorRelativeX = 0;
        int arenaFloorRelativeY = 105;
        int arenaFloorRelativeZ = 0;
        float radius = 25;                  //radius in blocks
        float stepSize = 1 / radius;        //size of each step <- multiplicative inverse

        for(double step = 0; step<circleRadians; step+=stepSize) {
            int x = (int) Math.round(Math.cos(step) * radius) + gamePlayerObject.getArenaXTile() + arenaFloorRelativeX;
            int z = (int) Math.round(Math.sin(step) * radius) + gamePlayerObject.getStageZTile() + arenaFloorRelativeZ;

            if(Math.round(Math.random() * 10) == 1 && mobsAlive <= 5){
                World world = gamePlayerObject.getOwner().getWorld();
                world.spawnEntity(new Location(world, x, arenaFloorRelativeY, z), EntityType.ZOMBIE);
                mobsAlive++;
            }
        }
    }

    @Override
    public void onEntityDeathEvent(EntityDeathEvent e){
        Entity entity = e.getEntity();
        double entityLocationX = entity.getLocation().getX();
        double entityLocationZ = entity.getLocation().getZ();
        EntityDamageEvent damageEvent = entity.getLastDamageCause();

        if(damageEvent == null) return;
        if(!(entityLocationX < gamePlayerObject.getArenaXTile() + SlimePuncher.sizeX) && !(entityLocationX > gamePlayerObject.getArenaXTile())) return;
        if(!(entityLocationZ < gamePlayerObject.getArenaXTile() + SlimePuncher.sizeZ) && !(entityLocationZ > gamePlayerObject.getArenaXTile())) return;

        if(entity.getType().equals(EntityType.ZOMBIE)) {
            mobsAlive -= 1;
            if (damageEvent.getCause() == (EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                gamePlayerObject.addBits(1);
            }
        }
    }

    @Override
    public void gameTick() {
        // TODO Auto-generated method stub
        
    }
}
