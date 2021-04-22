package net.arcticforestmc.SlimePuncher.Stages;

import java.util.ArrayList;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import org.bukkit.event.player.PlayerJoinEvent;

public abstract class Stage {

    protected ArrayList<Stage> children = new ArrayList<>();
    protected SlimePuncher plugin;
    protected GamePlayer gamePlayerObject;


    /**
     * Construct object
     * @return
     */
    protected Stage(SlimePuncher slimePuncher, GamePlayer gamePlayer) {
        plugin = slimePuncher;
        this.gamePlayerObject = gamePlayer;
    }

    /**
     * gets called every tick by the stagetree
     */
    public abstract void gameTick();
    
    /**
     * Get identifier of what stage this is
     * @return will return an array of 2 values, stage level, and index.
     */
    public abstract int[] getStageIdentifier();

    /**
     * This method defines the children of this stage, used to contruct a tree. Here you will put the identifiers of the stages you want this stage to lead to when done. An empty list would mean this is the end of the road, last stage.
     * @param child
     */
    public abstract int[][] getChildrenDescriptor();

    /**
     * Add a child Object to the current tree node, 
     * @param child
     */
    public void addChildObject(Stage child) {
        children.add(child);
    }

    /** 
     * Get the children nodes of this stage.
     * @param childIndex which index to get, aka which "path" to get.
     * @return child
     */
    public Stage getChildObject(int childIndex) {
        if(children.size()-1>=childIndex) {
            return(children.get(childIndex));
        }
        else {
            //no child object provided.
            return(null);
        }
    }
    

    /**
     * Get all children objects
     * @return children
     */
    public ArrayList<Stage> getChildrenObjects() {
        return(children);
    }

    /**
     * Should this stage have any children(if no its the end - its the leaf of the tree)
     * @return if should have children
     */
    public boolean hasChildren() {
        return getChildrenDescriptor().length > 0;
    }

    /**
     * When owner joins arena(for example he gets online)
     */
    public abstract void ownerJoinedArena();

    /**
     * Gets an index of spawn location depending on stage
     * @return stage spawn location
     */
    public abstract int[] getStageRelativeSpawnCoords();

    /**
     * this defines the tunnels bounds that lead to the next stage.
     * @return the bounds format goes as follows [ [[x,z],[x,z]], [[x,z],[x,z]] ]    what you are defining here are the x,z coordinates that define a line at which when the player crosses, the tunnel should build the next stage and take the player to it.
     */
    public abstract int[][][] nextStageTunnelRelativeBounds();

    /**
     * This defines the relative location for the stage's NPC shop guy
     * @return Array of relative XYZ of shop npc
     */
    public abstract int[] npcStageRelativeCoords();
    


    //EVENTS:

    public void onInteractEvent(PlayerInteractEvent e) {}
    public void onEntityDeathEvent(EntityDeathEvent e) {}
}
