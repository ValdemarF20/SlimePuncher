package net.arcticforestmc.SlimePuncher.Stages;

public class Stage2_1_Test extends Stage {

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{2,1});
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[][]{{3,0}});
    }
    
}
