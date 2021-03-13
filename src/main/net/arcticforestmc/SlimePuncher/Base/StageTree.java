package net.arcticforestmc.SlimePuncher.Base;

import net.arcticforestmc.SlimePuncher.Stages.*;


public class StageTree {      

    private Stage0_0_SlimePunching root;                                    //ROOT of tree(first stage)

    private Stage trackingStage;                                            //This is the stage the player is currently on 
   
    

    /**
     * Construct stage tree
     *  
     **/ 
    public StageTree() {
        root = new Stage0_0_SlimePunching();

        Stage[] stages = new Stage[]{
            root,
            new Stage1_0_Test(),
            new Stage2_0_Test(),
            new Stage2_1_Test(),
            new Stage3_0_Test()};


        //CONSTRUCT TREE
        for(Stage stage : stages) {
            if(stage.hasChildren()) {
                int childrenDescriptor[][] = stage.getChildrenDescriptor();
                
                for(int childIdentifier[] : childrenDescriptor) {
                    //Look for this stage and add it as a child to the current stage
                    for(Stage child : stages) {
                        if(child.getStageIdentifier().equals(childIdentifier)) {
                            stage.addChildObject(child);
                        }
                    }
                }
            }
        }





        trackingStage = root;

    }

    private boolean stagesAreSameLevel(Stage a, Stage b) {
        if(a.getStageIdentifier()[0] == b.getStageIdentifier()[0]) {
            return(true);
        }
        return(false);
    }

    /**
     * Get a string of the tree represented in a string map
     * @return
     */
    public String generateDebugTreeGraph() {
        int w = 50; //width
        
        String graph = "";

        
        graph = gs(w/2)+il(root.getStageIdentifier())+gs(w/2);
        Stage base = root;
        while(true) {
            /*
            if(!base.hasChildren()) {
                break;
            }
            else {
                
            }*/
            break;
        }

        return(graph);

    }
    private String gs(int amount) {     //genSpacing
        String r = "";
        for(int i = 0; i<amount; i++) {
            r+="-";
        }
        return(r);
    }
    private String il(int i[])  {       //identifier label
        int stage = i[0];
        int index = i[1];

        return(stage+"_"+index);
    }

    /**
     * Get a stage by it's identifier
     * @param identifier
     * @return
     */
    public Stage getStage(int identifier[]) {
        int level = identifier[0];
        int index = identifier[1];

        return(null);

    } 

    /**
     * set the current stage based on string(used for loading)
     * @param stageIdentifier
     */
    public void setTracking(String stageIdentifier) {
        int _stageIdentifier[] = {Integer.valueOf(stageIdentifier.charAt(0)),Integer.valueOf(stageIdentifier.charAt(2))};
    }

    /**
     * get current tracking stage
     * @return 
     */
    public Stage getCurrentStage() {
        return(trackingStage);
    }
}
