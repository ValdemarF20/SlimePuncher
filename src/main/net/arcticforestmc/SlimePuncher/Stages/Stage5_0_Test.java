package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class Stage5_0_Test extends Stage {

    public Stage5_0_Test(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{5,0});
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[0][]);
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
