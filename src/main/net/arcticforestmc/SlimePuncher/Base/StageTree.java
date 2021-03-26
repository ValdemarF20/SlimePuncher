package net.arcticforestmc.SlimePuncher.Base;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import net.arcticforestmc.SlimePuncher.Listeners.MobDeathListener;
import org.bukkit.event.Listener;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Stages.*;


public class StageTree implements Listener{      

    private Stage0_0_SlimePuncher root;                                    //ROOT of tree(first stage)

    private Stage trackingStage;                                            //This is the stage the player is currently on 
   
    protected ArrayList<Stage> stages = new ArrayList<>();

    private GamePlayer owner;

    /**
     * Construct stage tree
     *  
     **/ 
    public StageTree(SlimePuncher plugin, GamePlayer owner) {
        root = new Stage0_0_SlimePuncher(plugin, owner);
        

        //NOTE: Order doesnt matter here. Make sure there is a leaf/end game without any children.
        stages.addAll(Arrays.asList(
            root,
            new Stage1_0_Test(plugin, owner),
            new Stage2_0_Test(plugin, owner),
            new Stage2_1_Test(plugin, owner),
            new Stage3_0_Test(plugin, owner),
            new Stage3_1_Test(plugin, owner),
            new Stage3_2_Test(plugin, owner),
            new Stage4_0_Test(plugin, owner),
            new Stage5_0_Test(plugin, owner)));


        //CONSTRUCT TREE
        for(Stage stage : stages) {
            if(stage.hasChildren()) {
                int childrenDescriptor[][] = stage.getChildrenDescriptor();
                
                for(int childIdentifier[] : childrenDescriptor) {
                    //Look for this stage and add it as a child to the current stage
                    for(Stage child : stages) {
                        if(Arrays.equals(child.getStageIdentifier(), childIdentifier)) {
                            stage.addChildObject(child);
                        }
                    }
                }
            }
        }

        trackingStage = root;
        
        //Registers the interaction event
        plugin.getServer().getPluginManager().registerEvents(new StageTreeListener(owner), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MobDeathListener(), plugin);
    }

    /**
     * gets called every tick by gameplayer
     */
    public void gameTick() {
        //tick every stage
        for(Stage stage : stages) {
            stage.gameTick();
        }
    }



    /**
     * Get a string of the tree represented in a string map
     * @return
     */
    public String generateDebugTreeGraph() {
        
        String graph;

        Stage base = root;

        ArrayList<Stage> alreadyBranched = new ArrayList<>();

        //Go down tree
        graph = "\n[ROOT->"+branch(base, alreadyBranched);

        return(graph);

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
     * Get current stage stage
     * @return
     */
    public Stage getTracking() {
        return(trackingStage);
    }

    /**
     * get current tracking stage
     * @return 
     */
    public Stage getCurrentStage() {
        return(trackingStage);
    }
    private String branch(Stage base, ArrayList<Stage> alreadyBranched) {
        String s = "";

        for(int childIndex = 0; childIndex<base.getChildrenObjects().size(); childIndex++) {

            Stage child = base.getChildObject(childIndex);


            if(childIndex>0) {
                s+= "\n"+gs(calcSpacing(child));
            }

            boolean childNew = true;
            //If this child has the same child as a previous child before it, dont continue it, merge.
            for(Stage testStage : alreadyBranched) {
                    if(testStage == child) {
                        childNew = false;
                    }
                }

            if(child.hasChildren() && childNew) {
                s += "("+child.getStageIdentifier()[0]+","+child.getStageIdentifier()[1]+")"+"->";
                s += branch(child, alreadyBranched);
                alreadyBranched.add(child);
            }
            else if(childNew) {
                //leaf found
                s += "("+child.getStageIdentifier()[0]+","+child.getStageIdentifier()[1]+"]";
                alreadyBranched.add(child);
            }
            else {
                //leaf found previously
                s += "|";
            }
        }
        return(s);

    }
    private String gs(int amount) {
        String r = "";
        for(int i = 0; i< amount; i++) {
            r+=" ";
        }
        return(r);
    }
    private int calcSpacing(Stage stage) {
        int amount = 0;
        for(int level = 0; level<=stage.getStageIdentifier()[0]-1; level++) {
            amount+=1+String.valueOf(stage.getStageIdentifier()[0]).length()+1+String.valueOf(stage.getStageIdentifier()[1]).length()+1+2;
        }

        return(amount);
    }

    //Ignore:
    /*
    private String il(int i[])  {       //identifier label
        int stage = i[0];
        int index = i[1];

        return(stage+"_"+index);
    }
        private boolean stagesAreSameLevel(Stage a, Stage b) {
        if(a.getStageIdentifier()[0] == b.getStageIdentifier()[0]) {
            return(true);
        }
        return(false);
    }
    private ArrayList<Stage> stageParents(Stage stage, ArrayList<ArrayList<Stage>> levels) {
        ArrayList<Stage> parents = new ArrayList<>();
        for(ArrayList<Stage> level : levels) {
            for(Stage a : level) {
                if(a.getStageIdentifier()[0] == stage.getStageIdentifier()[0] && stage.getStageIdentifier()[0]!=0) {
                    if(!parents.contains(getParent(a, levels))) {
                        parents.add(getParent(a, levels));
                    }
                }
            }
        }
        return(parents);
    }
    private Stage getParent(Stage stage, ArrayList<ArrayList<Stage>> levels) {
        Stage parentStage = null;

        for(int levelIndex = 0; levelIndex<levels.size(); levelIndex++) {
            ArrayList<Stage> level = levels.get(levelIndex);
            if(levelIndex == stage.getStageIdentifier()[0]) {
                for(int stageIndex = 0; stageIndex<level.size(); stageIndex++) {
                    //Find stage 
                    Stage teststage = level.get(stageIndex);
                    if(stage.getStageIdentifier()[1] == teststage.getStageIdentifier()[1]) {
                        //find parent
                        for(Stage parentTestStage : levels.get(levelIndex-1)) {
                            for(int desc[] : parentTestStage.getChildrenDescriptor()) {
                                if(Arrays.equals(desc, stage.getStageIdentifier())) {
                                    //This parent has the child
                                    
                                    parentStage = parentTestStage;
                                }
                            }
                        }
                    }
                }
            }
        }

        return(parentStage);
    }
    private ArrayList<Stage> getSiblingsInclusive(Stage stage, ArrayList<ArrayList<Stage>> levels) {
        ArrayList<Stage> siblingsI = new ArrayList<Stage>();
        if(!stage.equals(root)) {
            siblingsI.addAll(getParent(stage, levels).getChildrenObjects());
        }
        else {
            siblingsI.add(stage);   //root
        }
        return(siblingsI);
    }
    private ArrayList<Stage> getLevelStages(int level) {
        ArrayList<Stage> levelStages = new ArrayList<>();

        for(Stage stage : stages) {
            if(stage.getStageIdentifier()[0] == level) {
               levelStages.add(stage); 
            }

        }

        return(levelStages);
    }*/






}
