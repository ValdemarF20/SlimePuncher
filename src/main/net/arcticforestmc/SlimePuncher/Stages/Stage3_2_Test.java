package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;

public class Stage3_2_Test extends Stage {

    protected Stage3_2_Test(SlimePuncher slimePuncher) {
        super(slimePuncher);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{3,2});
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
