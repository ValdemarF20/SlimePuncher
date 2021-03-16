package net.arcticforestmc.SlimePuncher.Stages;

public class Stage1_0_Test extends Stage {

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
