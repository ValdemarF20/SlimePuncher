package net.arcticforestmc.SlimePuncher.Commands;

import net.arcticforestmc.SlimePuncher.Base.GamePlayer;
import net.arcticforestmc.SlimePuncher.SlimePuncher;
import net.arcticforestmc.SlimePuncher.Stages.Stage0_0_SlimePuncher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MobsAlive implements CommandExecutor {
    private Stage0_0_SlimePuncher slimePuncher;
    private GamePlayer test;
    private SlimePuncher main;

    public MobsAlive(Stage0_0_SlimePuncher slimePuncher, GamePlayer test, SlimePuncher main){
        this.slimePuncher = slimePuncher;
        this.test = test;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        test = new GamePlayer(Bukkit.getPlayer(args[0]), main);

        int mobsAlive = slimePuncher.getMobsAlive();

        System.out.println(mobsAlive);

        sender.sendMessage(String.valueOf(mobsAlive));

        return true;
    }
}
