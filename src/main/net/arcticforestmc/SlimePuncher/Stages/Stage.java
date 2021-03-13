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
     * This method defines the children of this stage, used to contruct a tree. Here you will put the identifiers of the stages you want this stage to lead to when done. An empty list would mean this is the end of the road, last stage.
     * @param child
     */
    public abstract int[][] getChildrenDescriptor();

    /**
     * Add a child Object to the current tree node, 
     * @param child
     */
    public void addChildObject(Stage child) {
        children.add(child);
    }

    /** 
     * Get the children nodes of this stage.
     * @param childIndex which index to get, aka which "path" to get.
     * @return child
     */
    public Stage getChildObject(int childIndex) {
        if(children.size()-1<=childIndex) {
            return(children.get(childIndex));
        }
        else {
            //no child object provided.
            return(null);
        }
    }


    /**
     * Get all children objects
     * @return children
     */
    public ArrayList<Stage> getChildrenObjects() {
        return(children);
    }

    /**
     * Should this stage have any children(if no its the end - its the leaf of the tree)
     * @return if should have children
     */
    public boolean hasChildren() {
        if(getChildrenDescriptor().length>0) {
            return(true);
        }
        return(false);
    }

    
}
