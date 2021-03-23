package net.arcticforestmc.SlimePuncher.Base;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.arcticforestmc.SlimePuncher.SlimePuncher;


public class GamePlayer implements Listener{

    private int stageTile;          //X-coordinate of player's stage tile(start)
    private int bits;               //How many bits does the player currently have
    private int xpBits;             //Used for leveling, it will not be deducted when spent and under certain circumstances it will not go up by the same amount as bits.
    private int ownerLevel;        //what level is the player on (More than one player level might be required for stageLevel up)
    private StageTree stageTree;    //This players stageTree
    private Player owner;

    /**
     * gets called every tick by slimepuncher
     */
    public void gameTick() {
        stageTree.gameTick();
    }
   
    /**
     * Keeps track of a player and their corresponding data
     * @param player
     * @param plugin
     */
    public GamePlayer(Player player, SlimePuncher plugin) {
        owner = player;
        
        //Check if this player has played the game before
        if(playedBefore(player)) {

        }
        else {
            stageTree = new StageTree(plugin, this);
        }

    }

    /**
     * Get if player has played the game before, do they have stage progress?
     * @param player
     * @return has the player played before.
     */
    public boolean playedBefore(Player player) {
        return(false); //TEMPORARY
    }

    /*
    Gets the player from GamePlayer
    */

    public Player getOwner(){
        return owner;
    }

    /**
     * Load player's stage/data files(only will work if player has played before)
     * @param owner
     */
    public void loadData(Player owner) {

    }

    public StageTree getStageTree() {
        return(stageTree);
    }

    public void addBits(){
        // TODO: Check if player will level up before adding bits
        int multiplier = 1;
        int i = 1;
        bits+=i;
        xpBits = xpBits + (i * multiplier);
        //test
    }

    /**
     * get arena x-coord/stage tile
     * @return
     */
    public int getStageTile() {
        return(stageTile);
    }

    /**
     * Get level of player, NOT the level of the stage the player is on
     * @return
     */
    public int getOwnerLevel() {
        return(ownerLevel);
    }

    /**
     * Get amount of bits player has
     * @return
     */
    public int getBits() {
        return(bits);
    }
    
     /**
     * Get xp bits
     * @return 
     */
    public int getXpBits() {
        return(bits);
    }
}
