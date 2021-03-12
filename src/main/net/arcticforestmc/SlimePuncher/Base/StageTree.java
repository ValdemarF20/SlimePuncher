package net.arcticforestmc.SlimePuncher.Base;

import net.arcticforestmc.SlimePuncher.Stages.Stage;
import net.arcticforestmc.SlimePuncher.Stages.Stage0_0_SlimePunching;
import net.arcticforestmc.SlimePuncher.Stages.Stage1_0_Test;

public class StageTree {

    private Stage0_0_SlimePunching root = new Stage0_0_SlimePunching();     //ROOT of tree

    private Stage trackingStage;                                            //This is the stage the player is currently on 
   
    

    /**
     * Construct stage tree
     *  
     **/ 
    public StageTree() {
        Stage1_0_Test s1 = new Stage1_0_Test();

        root.addChild(s1);





        /*


                        s1_0
                          |
                          |
                        s0_0

            TREE(NOTE: from bottom to top)
        */

    }

    /**
     * set the current 
     * @param stageIdentifier
     */
    public void setTracking(String stageIdentifier) {
        switch(stageIdentifier) {

            //ROOT STAGE
            case "0_0":
                trackingStage = root;
                return;
            
            //SUBSEQUENT STAGES:

            case "1_0":    
                trackingStage = root.getChild(0); 
                return;


            default:
                return;
                ///???? identifier nonexistent



        }
    }
}
