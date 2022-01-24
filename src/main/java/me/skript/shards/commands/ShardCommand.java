package me.skript.shards.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.skript.shards.Shards;
import me.skript.shards.menus.Category;
import me.skript.shards.playerdata.PlayerData;
import net.lucaudev.api.chat.Chat;
import net.lucaudev.api.data.Constants;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("ConstantConditions")
@CommandAlias("shards|shard|coolmanshard")
@CommandPermission("shard.use")
public class ShardCommand extends BaseCommand {

    private final Shards instance;

    public ShardCommand(Shards instance) {
        this.instance = instance;
    }

    @Default
    public void defaultCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Chat.color("&c&l(!) &cYou have to be a player"));
            return;
        }
        Category.getInstance().open((Player) commandSender);
    }

    @HelpCommand
    public void helpCommand(CommandSender commandSender) {
        instance.getLangFile().getConfig().getStringList("Commands.Shard.Help.Message").forEach(s -> commandSender.sendMessage(Chat.color(s)));
    }

    @Subcommand("paytoggle|togglepay|toggle")
    public void payToggleCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(player);
        if (playerData.isPayEnabled()) {
            playerData.setPayEnabled(false);
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay Toggle.Disable.Message")));
        } else {
            playerData.setPayEnabled(true);
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay Toggle.Enable.Message")));
        }

    }

    @Subcommand("balance|bal")
    public void balanceCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(player);
        player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Balance.Message")
                .replace("%player_balance%", String.valueOf(playerData.getBalance()))
                .replace("%player_balance_formatted%", Constants.BALANCE_FORMAT.format(playerData.getBalance()))));
    }

    @Subcommand("pay|sendshards")
    @Syntax("<player> <amount>")
    public void payCommand(CommandSender commandSender, OnlinePlayer targetPlayer, long amount) {
        Player player = (Player) commandSender;
        PlayerData senderPlayerData = instance.getPlayerDataManager().getPlayerData(player);
        PlayerData targetPlayerData = instance.getPlayerDataManager().getPlayerData(targetPlayer.getPlayer());

        if (player.getUniqueId() == targetPlayer.getPlayer().getUniqueId()) {
            return;
        }

        if (!(targetPlayerData.isPayEnabled())) {
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Toggled.Message")));
            return;
        }

        if (amount > senderPlayerData.getBalance()) {
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Amount.Message")));
            return;
        }

        senderPlayerData.setBalance(senderPlayerData.getBalance() - amount);
        targetPlayerData.setBalance(targetPlayerData.getBalance() + amount);

        player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Sent.Message"))
                .replace("%shards%", String.valueOf(amount))
                .replace("%shards_formatted%", Constants.BALANCE_FORMAT.format(amount))
                .replace("%player%", targetPlayer.getPlayer().getName()));

        targetPlayer.getPlayer().sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Received.Message"))
                .replace("%shards%", String.valueOf(amount))
                .replace("%shards_formatted%", Constants.BALANCE_FORMAT.format(amount))
                .replace("%player%", player.getName()));
    }


}
