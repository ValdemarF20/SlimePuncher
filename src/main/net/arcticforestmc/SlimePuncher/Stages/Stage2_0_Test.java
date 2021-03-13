package net.arcticforestmc.SlimePuncher.Stages;

public class Stage2_0_Test extends Stage {

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{2,0});
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[][]{{3,0}});
    }
    
}
