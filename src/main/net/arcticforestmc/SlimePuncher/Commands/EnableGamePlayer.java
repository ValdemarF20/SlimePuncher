package net.arcticforestmc.SlimePuncher.Commands;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Managers.GamePlayerManager;
import net.arcticforestmc.SlimePuncher.SlimePuncher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EnableGamePlayer implements CommandExecutor {
    private final HashMap<UUID, GamePlayer> gamePlayers = GamePlayerManager.gamePlayers;
    private final SlimePuncher slimePuncher;

    public EnableGamePlayer(SlimePuncher slimePuncher){
        this.slimePuncher = slimePuncher;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        Player player = Bukkit.getServer().getPlayer(args[0]);

        GamePlayer gamePlayer = new GamePlayer(player, slimePuncher);

        gamePlayers.put(player.getUniqueId(), gamePlayer);

        return true;
    }

}
