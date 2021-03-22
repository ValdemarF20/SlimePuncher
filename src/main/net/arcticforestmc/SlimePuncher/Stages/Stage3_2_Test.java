package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class Stage3_2_Test extends Stage {

    public Stage3_2_Test(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
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
    
    @Override
    public boolean canProgressStage() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void gameTick() {
        // TODO Auto-generated method stub
        
    }
}
