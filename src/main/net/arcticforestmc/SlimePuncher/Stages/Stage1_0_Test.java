package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class Stage1_0_Test extends Stage {

    public Stage1_0_Test(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
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
    public void ownerJoinedArena() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int[] getStageRelativeSpawnCoords() {
        return new int[]{0, 0, 0};
    }


    @Override
    public void gameTick() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int[][][] nextStageTunnelRelativeBounds() {
        return new int[][][]{{{}}};
    }
    
}
