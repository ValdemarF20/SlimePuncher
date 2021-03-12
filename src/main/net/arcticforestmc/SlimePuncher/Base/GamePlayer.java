package net.arcticforestmc.SlimePuncher.Base;

import org.bukkit.entity.Player;

public class GamePlayer {

    private int stageTile;      //X-coordinate of player's stage tile(start)
    private int bits;           //How many bits does the player have
    private int level;          //what level(stage) is the player on
   
    /**
     * Keeps track of a player and their corresponding data
     * @param player
     */
    public GamePlayer(Player player) {
        //Check if this player has played the game before
        if(playedBefore(player)) {

        }

    }

    /**
     * Get if player has played the game before, do they have stage progress?
     * @param player
     * @return 
     */
    public boolean playedBefore(Player player) {
        
    }

    /**
     * Load player's from data files(only will work if player has played before)
     * @param player
     */
    public void loadData() {
    
    }
}
