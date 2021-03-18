package net.arcticforestmc.SlimePuncher.Stages;

import org.bukkit.event.Event;

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
    /* Pass the event to this method

    @Override
    public void onInteract(event e){
        
    }
    
    */
    
}
