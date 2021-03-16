package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;

public class Stage1_0_Test extends Stage {

    protected Stage1_0_Test(SlimePuncher slimePuncher) {
        super(slimePuncher);
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
