package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;

public class Stage2_1_Test extends Stage {

    protected Stage2_1_Test(SlimePuncher slimePuncher) {
        super(slimePuncher);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{2,1});
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[][]{{3,1},{3,2}});
    }

    @Override
    public void ownerJoinedIsland() {
        // TODO Auto-generated method stub
        
    }
    
}
