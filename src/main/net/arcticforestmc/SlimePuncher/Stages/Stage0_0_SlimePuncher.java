package net.arcticforestmc.SlimePuncher.Stages;

import org.bukkit.entity.Player;

import net.arcticforestmc.SlimePuncher.SlimePuncher;

public class Stage0_0_SlimePuncher extends Stage {

    public Stage0_0_SlimePuncher(SlimePuncher slimePuncher, Player owner) {
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
    
}
