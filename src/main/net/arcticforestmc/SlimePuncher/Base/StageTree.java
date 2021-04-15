package net.arcticforestmc.SlimePuncher.Base;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Managers.StageGeneration;
import net.arcticforestmc.SlimePuncher.Stages.*;


public class StageTree implements Listener{      

    private Stage0_0_SlimePuncher root;                                    //ROOT of tree(first stage)

    private Stage trackingStage;                                            //This is the stage the player is currently on 
   
    public ArrayList<Stage> stages = new ArrayList<>();


    /**
     * Construct stage tree
     *  
     **/ 
    public StageTree(SlimePuncher plugin, GamePlayer gamePlayerObject, String trackingStageIdentifier) {
        
        root = new Stage0_0_SlimePuncher(plugin, gamePlayerObject);
        
        
        //NOTE: Order doesnt matter here. Make sure there is a leaf/end game without any children.
        stages.addAll(Arrays.asList(
            root,
            new Stage1_0_Test(plugin, gamePlayerObject),
            new Stage2_0_Test(plugin, gamePlayerObject),
            new Stage2_1_Test(plugin, gamePlayerObject),
            new Stage3_0_Test(plugin, gamePlayerObject),
            new Stage3_1_Test(plugin, gamePlayerObject),
            new Stage3_2_Test(plugin, gamePlayerObject),
            new Stage4_0_Test(plugin, gamePlayerObject),
            new Stage5_0_Test(plugin, gamePlayerObject)));
            
            
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
            
            
            setTracking(getStageFromIdentifier(trackingStageIdentifier));
            
            //Registers the events
            plugin.getServer().getPluginManager().registerEvents(new StageEventDispatcher(gamePlayerObject), plugin);

            //Start tunnel stage bounds detection for next stages
            nextStageBoundsDetection(plugin, gamePlayerObject);

            //progressTracking(0, gamePlayerObject);
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

    public void nextStageBoundsDetection(JavaPlugin plugin, GamePlayer player) {
         new BukkitRunnable() {
             @Override
             public void run() {
                //every tick detect if the player is going in a tunnel, if he is then progress to next stage and do appropriate animations and stuff

                int[][][] tunnels = getTracking().nextStageTunnelRelativeBounds();

                ArrayList<Location[]> tunnelsData = new ArrayList();

                for(int[][] tunnel : tunnels) {
                    int tunnelPoints[][] = new int[2][3];
                    for(int tunnelPointIndex = 0; tunnelPointIndex < tunnel.length; tunnelPointIndex++) {
                        int[] tunnelPoint = tunnel[tunnelPointIndex];
                        tunnelPoints[tunnelPointIndex] = tunnelPoint;
                    }
                    //construct data for 1 tunnel:
                    Location pointData[] = new Location[2];
                    //point 1 of rect:
                    pointData[0] = new Location(player.getOwner().getWorld(), player.getArenaXTile()+tunnelPoints[0][0], player.getArenaYLevel(), player.getStageZTile()+tunnelPoints[0][1]);
                    //point 2 of rect:
                    pointData[1] = new Location(player.getOwner().getWorld(), player.getArenaXTile()+tunnelPoints[1][0], player.getArenaYLevel(), player.getStageZTile()+tunnelPoints[1][1]);
                    
                    tunnelsData.add(pointData);
                }
             }
         }.runTaskTimer(plugin, 0, 0);



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
     * Use this when the player should progress, this will interface with stage generation, once this method is triggered a wall will be closed behind the player, the stage generator will create the next stage, and the wall to the next stage will be opened.
     * @param childStage this is the child stage number that the player chose.
     */
    public void progressTracking(int childStage, GamePlayer player) {
        String id = trackingStage.getStageIdentifier()[0]+"_"+childStage;
        //String id = trackingStage.getStageIdentifier()[0]+1+"_"+childStage;

        setTracking(getStageFromIdentifier(id));
        
        StageGeneration.generateStage(player, id, player.getArenaXTile(), player.getArenaYLevel(), player.getStageZTile());

    }


    /**
     * set the current stage based on string(used for loading)
     * @param stage
     */
    private void setTracking(Stage stage) {
        trackingStage = stage;
    }

    /**
     * Get stage object from stage identifier
     * @return
     */
    public Stage getStageFromIdentifier(String stageIdentifier) {
        int _stageIdentifier[] = {Integer.valueOf(String.valueOf(stageIdentifier.charAt(0))),Integer.valueOf(String.valueOf(stageIdentifier.charAt(2)))}; //string.valueof is needed 

        for(Stage stage : stages) {
            if(stage.getStageIdentifier()[0] == _stageIdentifier[0] && stage.getStageIdentifier()[1] == _stageIdentifier[1]) {
                return(stage);
            }
        }

        return(null);

    }



    /**
     * Get current stage stage
     * @return
     */
    public Stage getTracking() {
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
