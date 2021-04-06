package net.arcticforestmc.SlimePuncher.Commands;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetBitsCommand implements CommandExecutor {
    private final GamePlayer gamePlayer;

    public GetBitsCommand(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){

        System.out.println(gamePlayer);
        //String bits = String.valueOf(gamePlayer.getBits());

        //gamePlayer.getOwner().sendMessage(bits);

        return true;
    }
}