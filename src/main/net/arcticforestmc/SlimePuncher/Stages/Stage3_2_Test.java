package net.arcticforestmc.SlimePuncher.Stages;

public class Stage3_2_Test extends Stage {

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
