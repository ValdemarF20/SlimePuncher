package net.arcticforestmc.SlimePuncher.Stages;

import org.bukkit.entity.Player;

import net.arcticforestmc.SlimePuncher.SlimePuncher;

public class Stage3_0_Test extends Stage {

    public Stage3_0_Test(SlimePuncher slimePuncher, Player owner) {
        super(slimePuncher, owner);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{3,0});
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[][]{{4,0}});
    }

    @Override
    public void ownerJoinedIsland() {
        // TODO Auto-generated method stub
        
    }
    
}
