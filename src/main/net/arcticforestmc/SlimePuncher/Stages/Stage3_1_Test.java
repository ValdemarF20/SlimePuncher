package net.arcticforestmc.SlimePuncher.Stages;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class Stage3_1_Test extends Stage {

    public Stage3_1_Test(SlimePuncher slimePuncher, GamePlayer owner) {
        super(slimePuncher, owner);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int[] getStageIdentifier() {
        return(new int[]{3,1});
    }

    @Override
    public int[][] getChildrenDescriptor() {
        return(new int[][]{{4,0}});
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

