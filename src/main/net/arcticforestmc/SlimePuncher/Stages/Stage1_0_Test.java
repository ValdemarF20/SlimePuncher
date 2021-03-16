package net.arcticforestmc.SlimePuncher.Stages;

import org.bukkit.entity.Player;

import net.arcticforestmc.SlimePuncher.SlimePuncher;

public class Stage1_0_Test extends Stage {

    public Stage1_0_Test(SlimePuncher slimePuncher, Player owner) {
        super(slimePuncher, owner);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int[] getStageIdentifier() {
        int identifier[] = {1,0};
        return identifier;
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[][]{{2,0},{2,1}});
    }

    @Override
    public void ownerJoinedIsland() {
        // TODO Auto-generated method stub
        
    }
    
}
