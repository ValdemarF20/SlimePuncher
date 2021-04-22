package net.arcticforestmc.SlimePuncher.Commands;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.Managers.GamePlayerManager;
import net.arcticforestmc.SlimePuncher.Stages.Stage0_0_SlimePuncher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Test implements CommandExecutor {
    private final HashMap<UUID, GamePlayer> gamePlayers = GamePlayerManager.gamePlayers;

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        GamePlayer gamePlayer = gamePlayers.get(playerUUID);

        //The amount of bits the player has
        String bits = String.valueOf(gamePlayer.getBits());
        gamePlayer.getOwner().sendMessage("Bits: " + bits);

        //Current amount of mobs alive
        gamePlayer.getOwner().sendMessage("Mobs alive: " + Stage0_0_SlimePuncher.mobsAlive);

        //All current GamePlayers
        for (UUID name: gamePlayers.keySet()) {
            UUID uuid = UUID.fromString(name.toString());
            Player playerFromUUID = Bukkit.getPlayer(uuid);

            String value = gamePlayers.get(name).toString();

            player.sendMessage(playerFromUUID + " " + value);

            player.sendMessage(String.valueOf(gamePlayers.size()));
        }

        //Are mobs spawning?
        System.out.println(Arrays.toString(Stage0_0_SlimePuncher.getMobsAreSpawning()));

        return true;
    }
}