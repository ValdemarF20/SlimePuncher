package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.Base.EntityHider;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.SplittableRandom;

public class Stage0_0_SlimePuncher extends Stage {
    private final EntityHider entityHider;
    private static final SplittableRandom SPLITTABLE_RANDOM = new SplittableRandom();

    public Stage0_0_SlimePuncher(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
        this.entityHider = plugin.getEntityHider();
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
    public void ownerJoinedArena() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int[] getStageRelativeSpawnCoords() {
        return new int[]{0, 0, 0};
    }


    @Override
    public void onInteractEvent(PlayerInteractEvent e){
        if(!(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)))return;

        Player player = e.getPlayer();

        final Block clickedBlock = e.getClickedBlock();
        Location blockLocation = clickedBlock.getLocation();

        //Requires configuration
        int slimeBlockRelativeX = 38;
        int slimeBlockRelativeY = 7;
        int slimeBlockRelativeZ = 38;

        Location slimeLocation = new Location(player.getWorld(), gamePlayerObject.getArenaXTile() + slimeBlockRelativeX, slimeBlockRelativeY, gamePlayerObject.getStageZTile() + slimeBlockRelativeZ);

        if (blockLocation.equals(slimeLocation)) {

            double blockX = blockLocation.getX();
            double blockY = blockLocation.getY();
            double blockZ = blockLocation.getZ();

            gamePlayerObject.addBits(1);
            gamePlayerObject.addXpBits(1);
            player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 10, 3);
            player.getWorld().spawnParticle(Particle.SLIME, blockX, blockY, blockZ, 1);
        }
    }

    /**
     * Go around the arena edge once and spawn enemies randomly
     */
    public static int mobsAlive = 0;
    private void spawnEnemyTick() {

        if(!(mobsAlive < 5)) return;

        final float circleRadians = (float) (2.0F*Math.PI); //Radians in a circle idk google: https://socratic.org/questions/how-do-you-convert-360-degrees-to-radianss

        //requires configuration
        int arenaFloorRelativeX = 38;       //arenaFloorRelative decides where the mobs are spawning
        int arenaFloorRelativeY = 6;
        int arenaFloorRelativeZ = 38;
        float radius = 10;                  //radius in blocks
        float stepSize = 1 / radius;        //size of each step <- multiplicative inverse
        World world = gamePlayerObject.getOwner().getWorld();

        new BukkitRunnable(){
            @Override
            public void run() {
                for(double step = 0; step<circleRadians; step+=stepSize) {
                    if(Math.round(Math.random() * 50) == 1) {
                        int x = (int) Math.round(Math.cos(step) * radius) + gamePlayerObject.getArenaXTile() + arenaFloorRelativeX;
                        int z = (int) Math.round(Math.sin(step) * radius) + gamePlayerObject.getStageZTile() + arenaFloorRelativeZ;

                        if(mobsAlive < 5) {
                            Zombie zombie = (Zombie) world.spawnEntity(new Location(world, x, arenaFloorRelativeY, z), EntityType.ZOMBIE);
                            applyAttributes(zombie);
                            mobsAlive++;
                        }
                    }
                }
            }
        }.runTaskLater(plugin, 300);
    }

    public void applyAttributes(Zombie zombie){
        switch(SPLITTABLE_RANDOM.nextInt(1, 3)){
            case 1:
                shooterZombie(zombie);
                break;
            case 2:
                tankZombie(zombie);
                break;
            case 3:
                fastZombie(zombie);
                break;
            default:
                break;
        }
    }

    public void fastZombie(Zombie zombie){
        zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
        zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);
    }

    public void tankZombie(Zombie zombie){
        zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(4);
        zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
    }

    public void shooterZombie(Zombie zombie){
        Player target = gamePlayerObject.getOwner();
        if (target == null) return;

        int customSpeed = 3;
        ItemStack slimeBall = new ItemStack(Material.SLIME_BALL);


        zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
        zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!(zombie.isDead())) {
                    Arrow arrow = zombie.launchProjectile(Arrow.class, ((target.getLocation().toVector().add(target.getVelocity())).subtract(zombie.getLocation().toVector())).normalize().multiply(customSpeed));
                    //entityHider.hideEntity(target, arrow);

                    ArmorStand armorStand = (ArmorStand) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.ARMOR_STAND);

                    armorStand.setVisible(true);
                    armorStand.setItemInHand(slimeBall);
                    armorStand.setGravity(false);
                    armorStand.setInvulnerable(true);

                    arrow.setSilent(true);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (arrow.isDead()) {
                                this.cancel();
                                armorStand.remove();
                            } else {
                                armorStand.teleport(arrow.getLocation().subtract((arrow.getVelocity().normalize().multiply(0.5).subtract(new Vector(0, 1, 0)))));
                            }
                        }
                    }.runTaskTimer(plugin, 1, 1); //
                }
            }
        }.runTaskTimer(plugin, 50, 50); //Fire arrow every 50 ticks
    }

    @Override
    public void onEntityDeathEvent(EntityDeathEvent e){

        System.out.println("Inside EntityDeathEvent");

        Entity entity = e.getEntity();
        double entityLocationX = entity.getLocation().getX();
        double entityLocationZ = entity.getLocation().getZ();
        EntityDamageEvent damageEvent = entity.getLastDamageCause();

        if(damageEvent == null) return;
        if(!(entityLocationX < gamePlayerObject.getArenaXTile() + SlimePuncher.sizeX) && !(entityLocationX > gamePlayerObject.getArenaXTile())) return;
        if(!(entityLocationZ < gamePlayerObject.getArenaXTile() + SlimePuncher.sizeZ) && !(entityLocationZ > gamePlayerObject.getArenaXTile())) return;

        if(entity.getType() == EntityType.ZOMBIE) {
            mobsAlive -= 1;

            if (damageEvent.getCause() == (EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                gamePlayerObject.addBits(1);
                gamePlayerObject.addXpBits(1);
            }
        }
    }

    @Override
    public void gameTick() {
        // TODO Auto-generated method stub
        spawnEnemyTick();
    }



    @Override
    public int[][][] nextStageTunnelRelativeBounds() {
        //1 tunnel because only 1 next stage.
        return new int[][][]{{{0,0},{0,0}}};
    }

    public void setMobsAlive(int i){
        mobsAlive = i;
    }
}
