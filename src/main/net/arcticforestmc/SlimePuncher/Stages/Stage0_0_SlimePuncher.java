package net.arcticforestmc.SlimePuncher.Stages;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.SplittableRandom;
import java.util.UUID;

public class Stage0_0_SlimePuncher extends Stage {
    private static final SplittableRandom SPLITTABLE_RANDOM = new SplittableRandom();
    private final ArrayList<Zombie> mobsAliveList = new ArrayList<>();
    public static final boolean[] mobsAreSpawning = {false};

    public Stage0_0_SlimePuncher(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
        //TODO Auto-generated constructor stub
    }

    public static boolean[] getMobsAreSpawning() {
        return mobsAreSpawning;
    }

    private Location prevArrowLocation = new Location(gamePlayerObject.getOwner().getWorld(), 0, 0, 0);

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
        int slimeBlockRelativeZ = 38;

        Location slimeLocation = new Location(player.getWorld(), gamePlayerObject.getArenaXTile() + slimeBlockRelativeX, gamePlayerObject.getArenaYLevel()+1, gamePlayerObject.getStageZTile() + slimeBlockRelativeZ);

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

    public void spawnEnemies() {

        if(!(mobsAliveList.isEmpty())) return;

        final float circleRadians = (float) (2.0F*Math.PI); //Radians in a circle idk google: https://socratic.org/questions/how-do-you-convert-360-degrees-to-radianss

        //requires configuration
        int arenaFloorRelativeX = gamePlayerObject.getArenaXTile() + 38;       //arenaFloorRelative decides where the mobs are spawning
        int arenaFloorRelativeY = gamePlayerObject.getArenaYLevel() + 1;
        int arenaFloorRelativeZ = gamePlayerObject.getStageZTile() + 38;
        float radius = 10;                  //radius in blocks
        float stepSize = 1 / radius;        //size of each step <- multiplicative inverse
        World world = gamePlayerObject.getOwner().getWorld();

        for (double step = 0; step < circleRadians; step += stepSize) {
            if (SPLITTABLE_RANDOM.nextInt(1, 25) == 1) {
                int x = (int) Math.round(Math.cos(step) * radius) + gamePlayerObject.getArenaXTile() + arenaFloorRelativeX;
                int z = (int) Math.round(Math.sin(step) * radius) + gamePlayerObject.getStageZTile() + arenaFloorRelativeZ;
                if (mobsAlive < 5) {
                    Zombie zombie = (Zombie) world.spawnEntity(new Location(world, x, arenaFloorRelativeY, z), EntityType.ZOMBIE);
                    zombie.getEquipment().setHelmet(new ItemStack(Material.STONE_BUTTON)); //quick and dirty way to prevent zombies from burning
                    zombie.setBaby(false);
                    applyAttributes(zombie);
                    mobsAlive++;
                    mobsAliveList.add(zombie);
                }
            }
        }
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

        //Arrow speed
        int customSpeed = 2;

        //Flying object on arrow
        ItemStack slimeBall = new ItemStack(Material.SLIME_BALL);

        //General attributes for the shooterZombie
        zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
        zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!(zombie.isDead())) {
                    Arrow arrow = zombie.launchProjectile(Arrow.class, ((target.getLocation().toVector().add(target.getVelocity())).subtract(zombie.getLocation().toVector())).normalize().multiply(customSpeed));

                    ArmorStand armorStand = (ArmorStand) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.ARMOR_STAND);

                    armorStand.setVisible(false);
                    armorStand.setGravity(false);
                    armorStand.setMarker(true);

                    armorStand.setInvulnerable(true);

                    //Create the skull for the armor stand head
                    String base64_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDkzNGE5ZjVhYjE3ODlhN2Q4ZGQ5NmQzMjQ5M2NkYWNmZjU3N2Q4YzgxZTdiMjM5MTdkZmYyZTMyYmQwYmMxMCJ9fX0=";
                    armorStand.setHelmet(makeTextureSkull(base64_value));

                    //rotate slime
                    armorStand.setHeadPose(new EulerAngle(0.0D, 0.0D, 0.0D));

                    arrow.setSilent(true);

                    //Trick client into thinking arrow is dead
                    ((CraftPlayer) target).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(arrow.getEntityId()));

                    //Remove arrow + armorstand if arrow is not moving
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(!isMoving(arrow.getLocation())) {
                                armorStand.remove();
                                arrow.remove();
                                this.cancel();
                            }
                            armorStand.teleport(adjustedArmorStandLocation(arrow.getLocation()));
                        }
                    }.runTaskTimer(plugin, 1, 5);

                }
            }
        }.runTaskTimer(plugin, 50, 80); //Fire arrow every 50 ticks
    }

    private Location adjustedArmorStandLocation(Location in) {
        //position armor stand so it looks like slime ball is traveling
        Location location = in;

        location.setY(location.getY()-2.5);

        return(location);
    }

    /*
    @Override
    public void onProjectileHitEvent(ProjectileHitEvent e) {
        if(e.getEntityType().equals(EntityType.ARROW)) {
            if(isInStage(e.getEntity().getLocation())) {
                e.getEntity().remove();
            }
        }
    }
     */

    @Override
    public void onEntityDeathEvent(EntityDeathEvent e){
        Entity entity = e.getEntity();
        if(!(entity instanceof Zombie)) return;

        double entityLocationX = entity.getLocation().getX();
        double entityLocationZ = entity.getLocation().getZ();
        EntityDamageEvent damageEvent = entity.getLastDamageCause();

        if(damageEvent == null) return;
        if(!(entityLocationX < gamePlayerObject.getArenaXTile() + SlimePuncher.sizeX) && !(entityLocationX > gamePlayerObject.getArenaXTile())) return;
        if(!(entityLocationZ < gamePlayerObject.getArenaXTile() + SlimePuncher.sizeZ) && !(entityLocationZ > gamePlayerObject.getArenaXTile())) return;

        if(mobsAliveList.contains(entity)) {
            mobsAlive -= 1;
            mobsAliveList.remove(entity);
            System.out.println("Inside death event");

            if (damageEvent.getCause() == (EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                gamePlayerObject.addBits(1);
                gamePlayerObject.addXpBits(1);
            }
            if(mobsAliveList.isEmpty()) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        spawnEnemies();
                    }
                }.runTaskLater(plugin, 300);
            }
        }
    }

    @Override
    public void gameTick() {
        // TODO Auto-generated method stub
        //spawnEnemiesIfNotSpawning();
        Player player = gamePlayerObject.getOwner();
        for(Arrow arrow : arrowsInStage(player)) {
            player.spawnParticle(Particle.SLIME, arrow.getLocation(), 2, 0.01, 0.01 ,0.01, 0.01);
        }
    }

    private ArrayList<Arrow> arrowsInStage(Player player) {
        ArrayList<Arrow> arrows = new ArrayList();
        for(Entity e : player.getWorld().getEntities()) {
            if(e.getType().equals(EntityType.ARROW)) {
                Location l = e.getLocation();
                if(isInStage(l)) {
                    arrows.add((Arrow) e);
                }

            }
        }

        return(arrows);
    }

    private boolean isInStage(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        if(x>gamePlayerObject.getArenaXTile() && x<gamePlayerObject.getArenaXTile()+SlimePuncher.sizeX) {
            if(z>gamePlayerObject.getStageZTile() && z<gamePlayerObject.getStageZTile()+SlimePuncher.sizeZ) {
                return(true);
            }
        }
        return(false);
    }

    @Override
    public int[][][] nextStageTunnelRelativeBounds() {
        //1 tunnel because only 1 next stage.
        return new int[][][]{{{20,10},{50,40}}};
    }

    public boolean isMoving(Location current){
        boolean isMovingBoolean;

        double currentX = current.getX();
        double currentY = current.getY();
        double currentZ = current.getZ();

        double prevX = prevArrowLocation.getX();
        double prevY = prevArrowLocation.getY();
        double prevZ = prevArrowLocation.getZ();

        isMovingBoolean = currentX != prevX && currentZ != prevZ;
        prevArrowLocation = current;

        return isMovingBoolean;
    }

    public void setMobsAlive(int i){
        mobsAlive = i;
    }

    @Override
    public int[] npcStageRelativeCoords() {
        return new int[]{0,0,0};
    }

    public static ItemStack makeTextureSkull(String code){
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
        if(code == null) return item;
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(code.getBytes()), code);
        profile.getProperties().put("textures", new Property("textures", code));

        setGameProfile(meta, profile);

        meta.setDisplayName(ChatColor.WHITE+"UNKNOWN Head");
        item.setItemMeta(meta);
        return item;
    }

    public static void setGameProfile(ItemMeta meta, GameProfile profile){
        try{
            Field fieldProfileItem = meta.getClass().getDeclaredField("profile");
            fieldProfileItem.setAccessible(true);
            fieldProfileItem.set(meta, profile);
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e){e.printStackTrace();}
    }
}