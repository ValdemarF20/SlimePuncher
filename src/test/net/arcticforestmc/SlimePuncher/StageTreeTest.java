package net.arcticforestmc.SlimePuncher;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mock;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Base.StageTree;
import org.mockito.Mock;


public class StageTreeTest {

    @Mock
    SlimePuncher slimepuncher;
    GamePlayer gameplayer;

    @Test
    public void test() {
       System.out.println(new StageTree(slimepuncher, gameplayer).generateDebugTreeGraph());
    }
}
