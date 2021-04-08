package net.arcticforestmc.SlimePuncher.Commands;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Base.StageTree;
import net.arcticforestmc.SlimePuncher.Managers.GamePlayerManager;
import net.arcticforestmc.SlimePuncher.Stages.Stage0_0_SlimePuncher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static java.lang.Integer.parseInt;

public class SetMobsAlive implements CommandExecutor {
    private final HashMap<UUID, GamePlayer> gamePlayers = GamePlayerManager.gamePlayers;

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        GamePlayer gamePlayer = gamePlayers.get(player.getUniqueId());
        Stage0_0_SlimePuncher stage0 = (Stage0_0_SlimePuncher) gamePlayer.getStageTree().getStageFromIdentifier("0_0");

        stage0.setMobsAlive(parseInt(args[0]));

        return true;
    }
}
