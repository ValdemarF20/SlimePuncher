package net.arcticforestmc.SlimePuncher.Managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.world.World;

import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Base.GamePlayer;

public class StageGeneration {


    /** 
     * Generate next stage from schematic.
     */
    public void generateStage(GamePlayer player, String stageIdentifier) {
        File schematicFile = new File("plugins/SlimePuncher/stageSchematics/"+stageIdentifier+".schematic");
        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);


        try {
            ClipboardReader reader = format.getReader(new FileInputStream(schematicFile));
            Clipboard clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        


    }
    
}
