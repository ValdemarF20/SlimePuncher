package net.arcticforestmc.SlimePuncher.Managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;


import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class StageGeneration {


    /** 
     * Generate next stage from schematic.
     */
    public static void generateStage(GamePlayer player, String stageIdentifier, int x, int y, int z) {
        File schematicFile = new File("plugins/SlimePuncher/stageSchematics/"+stageIdentifier+".schematic");
        ClipboardFormat format = ClipboardFormat.findByFile(schematicFile);

        Clipboard clipboard = null;
        ClipboardReader reader = null;

        com.sk89q.worldedit.world.World weWorld = new BukkitWorld(player.getOwner().getWorld());
        WorldData weWorldData= weWorld.getWorldData();

        try {
            reader = format.getReader(new FileInputStream(schematicFile));
            clipboard = reader.read(weWorldData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        
 
        EditSession editSession = SlimePuncher.worldEdit.getEditSessionFactory().getEditSession(weWorld, -1);


        Operation operation = new ClipboardHolder(clipboard, weWorldData).createPaste(editSession, weWorldData)
        .to(new Vector(x, y, z)).ignoreAirBlocks(true).build();
        
        try {
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }


    }
    
}
