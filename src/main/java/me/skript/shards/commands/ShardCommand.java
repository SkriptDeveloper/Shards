package me.skript.shards.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.skript.shards.Shards;
import me.skript.shards.menus.Category;
import me.skript.shards.playerdata.PlayerData;
import net.lucaudev.api.chat.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("shards|shard|coolmanshard")
public class ShardCommand extends BaseCommand {

    private Shards instance;

    public ShardCommand(Shards instance) {
        this.instance = instance;
    }

    @Default
    @CommandPermission("shard.use")
    public void defaultCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Chat.color("&c&l(!) &cYou have to be a player"));
            return;
        }
        Category.getInstance().open((Player) commandSender);
    }

    @Subcommand("paytoggle|togglepay")
    @CommandPermission("shard.use")
    public void payToggleCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(player);
        if(playerData.isPayEnabled()){
            playerData.setPayEnabled(false);
            player.sendMessage(Chat.color("&e&l(!) &eYou will &c&lNOT RECEIVE &eshard payments!"));
        } else {
            playerData.setPayEnabled(true);
            player.sendMessage(Chat.color("&e&l(!) &eYou will &a&lRECEIVE &eshard payments!"));
        }
    }

    @SuppressWarnings("Duplication")
    @Subcommand("balance|bal")
    public void balanceCommand(CommandSender commandSender){

    }


}
