package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class Stage2_1_Test extends Stage {

    public Stage2_1_Test(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
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

    @Override
    public int[] npcStageRelativeCoords() {
        return new int[]{0,0,0};
    }
}
