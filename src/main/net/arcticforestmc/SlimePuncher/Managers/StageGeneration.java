package net.arcticforestmc.SlimePuncher.Managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;


import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class StageGeneration {


    /** 
     * Generate next stage from schematic.
     */
    public void generateStage(GamePlayer player, String stageIdentifier) {
        File schematicFile = new File("plugins/SlimePuncher/stageSchematics/"+stageIdentifier+".schematic");
        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);

        Clipboard clipboard = null;
        ClipboardReader reader = null;

        try {
            reader = format.getReader(new FileInputStream(schematicFile));
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(player.getOwner().getWorld());
 
        EditSession editSession = SlimePuncher.worldEdit.getEditSessionFactory().getEditSession(weWorld, -1);

        Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
        .to(BlockVector3.at(0, 6, 0)).ignoreAirBlocks(true).build();
        
        try {
            Operations.complete(operation);
            editSession.flushSession(); //lock in changes
        } catch (WorldEditException e) {
            e.printStackTrace();
        }


    }
    
}
