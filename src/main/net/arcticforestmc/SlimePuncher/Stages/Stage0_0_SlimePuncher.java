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
    private GamePlayer owner;
    
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
        Location slimeLocation = new Location(player.getWorld(), gameObject.getStageXTile() + slimeBlockRelativeX, slimeBlockRelativeY, gameObject.getStageZTile() + slimeBlockRelativeZ);
        final Block clickedBlock = e.getClickedBlock();
        if(clickedBlock!=null) {
            Location blockLocation = clickedBlock.getLocation();

            double blockX = blockLocation.getX();
            double blockY = blockLocation.getY();
            double blockZ = blockLocation.getZ();

            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
                if (blockLocation.equals(slimeLocation)) {
                    owner.addBits(1);
                    player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS,10, 3);
                    player.getWorld().spawnParticle(Particle.SLIME, blockX, blockY, blockZ, 1);
                }
            }
        }
    }

    @Override
    public boolean canProgressStage() {
        return (gameObject.getOwnerLevel()>=5);
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
        float stepSize = 1 / radius;        //size of each step

        for(double step = 0; step<circleRadians; step+=stepSize) {
            int x = (int) Math.round(Math.cos(step) * radius) + gameObject.getStageXTile() + arenaFloorRelativeX;
            int z = (int) Math.round(Math.sin(step) * radius) + gameObject.getStageZTile() + arenaFloorRelativeZ;

            if(Math.round(Math.random() * 10) == 1 && mobsAlive <= 5){
                World world = gameObject.getOwner().getWorld();
                world.spawnEntity(new Location(world, x, arenaFloorRelativeY, z), EntityType.ZOMBIE);
                mobsAlive++;
            }
        }
    }

    @Override
    public void onMobDeath(EntityDeathEvent e){
        Entity entity = e.getEntity();
        double entityLocationX = entity.getLocation().getX();
        double entityLocationZ = entity.getLocation().getZ();
        EntityDamageEvent damageEvent = entity.getLastDamageCause();

        if(damageEvent == null) return;
        if(!(entityLocationX < owner.getStageXTile() + GamePlayer.sizeX) && !(entityLocationX > owner.getStageXTile())) return;
        if(!(entityLocationZ < owner.getStageXTile() + GamePlayer.sizeZ) && !(entityLocationZ > owner.getStageXTile())) return;

        if(entity.getType().equals(EntityType.ZOMBIE)) {
            mobsAlive -= 1;
            if (damageEvent.getCause() == (EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                owner.addBits(1);
            }
        }
    }

    @Override
    public void gameTick() {
        // TODO Auto-generated method stub
        
    }
}
