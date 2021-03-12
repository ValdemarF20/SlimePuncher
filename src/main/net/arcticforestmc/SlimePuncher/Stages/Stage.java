package net.arcticforestmc.SlimePuncher.Stages;

import java.util.ArrayList;

public abstract class Stage {

    private ArrayList<Stage> children = new ArrayList<>();
    
    
    /**
     * Get identifier of what stage this is
     * @return will return an array of 2 values, level, and index.
     */
    public abstract int[] getStageIdentifier();

    /**
     * Add a child to the current tree node, 
     */
    public void addChild(Stage child) {
        children.add(child);
    }

    /** 
     * Get the children nodes of this stage.
     * @param childIndex which index to get, aka which "path" to get.
     */
    public Stage getChild(int childIndex) {
        return(children.get(childIndex));
    }
    
}
