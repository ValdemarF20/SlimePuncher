package net.arcticforestmc.SlimePuncher.Stages;

public class Stage3_1_Test extends Stage {

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{3,1});
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
