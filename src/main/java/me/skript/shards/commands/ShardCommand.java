package me.skript.shards.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.skript.shards.Shards;
import me.skript.shards.data.Constants;
import me.skript.shards.menus.Category;
import me.skript.shards.playerdata.PlayerData;
import net.lucaudev.api.chat.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("ConstantConditions")
@CommandAlias("shards|shard|coolmanshard")
@CommandPermission("shard.use")
public class ShardCommand extends BaseCommand {

    private Shards instance;

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

    @Subcommand("paytoggle|togglepay")
    public void payToggleCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(player);
        if (playerData.isPayEnabled()) {
            playerData.setPayEnabled(false);
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay Toggle.Disable")));
        } else {
            playerData.setPayEnabled(true);
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay Toggle.Enable")));
        }

    }

    @Subcommand("balance|bal")
    public void balanceCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(player);
        player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Balance")
                .replace("%player_balance%", String.valueOf(playerData.getBalance()))
                .replace("%player_balance_formatted%", Constants.DECIMAL_FORMAT.format(playerData.getBalance()))));
    }

    @Subcommand("pay|sendshards")
    @Syntax("<player> <amount>")
    public void payCommand(CommandSender commandSender, OnlinePlayer targetPlayer, long amount) {
        Player player = (Player) commandSender;
        PlayerData senderPlayerData = instance.getPlayerDataManager().getPlayerData(player);
        PlayerData targetPlayerData = instance.getPlayerDataManager().getPlayerData(targetPlayer.player);

        if (player.getUniqueId() == targetPlayer.player.getUniqueId()) {
            return;
        }

        if (!(targetPlayerData.isPayEnabled())) {
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Toggled")));
            return;
        }

        if (amount > senderPlayerData.getBalance()) {
            player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Amount")));
            return;
        }

        senderPlayerData.setBalance(senderPlayerData.getBalance() - amount);
        targetPlayerData.setBalance(targetPlayerData.getBalance() + amount);

        player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Sent"))
                .replace("%shards%", String.valueOf(amount))
                .replace("%shards_formatted%", Constants.DECIMAL_FORMAT.format(amount))
                .replace("%player%", targetPlayer.player.getName()));

        targetPlayer.player.sendMessage(Chat.color(instance.getLangFile().getConfig().getString("Commands.Shard.Pay.Received"))
                .replace("%shards%", String.valueOf(amount))
                .replace("%shards_formatted%", Constants.DECIMAL_FORMAT.format(amount))
                .replace("%player%", player.getName()));
    }


}
