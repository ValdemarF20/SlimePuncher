package net.arcticforestmc.SlimePuncher;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.arcticforestmc.SlimePuncher.Base.StageTree;


public class StageTreeTest {

    @Test
    public void checkOrdered() {
        StageTree tree = new StageTree();
        System.out.println(tree.generateDebugTreeGraph());
    }
    
}
