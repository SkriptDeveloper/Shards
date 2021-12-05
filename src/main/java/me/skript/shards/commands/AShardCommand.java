package me.skript.shards.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.skript.shards.Shards;
import me.skript.shards.menus.Shop;
import me.skript.shards.playerdata.PlayerData;
import net.lucaudev.api.chat.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("adminshard|ashard")
@CommandPermission("shard.admin")
public class AShardCommand extends BaseCommand {

    private Shards instance;

    public AShardCommand(Shards instance) {
        this.instance = instance;
    }

    @HelpCommand
    public void helpCommand(CommandSender commandSender) {
        instance.getLangFile().getConfig().getStringList("Commands.Admin.Help").forEach(s -> commandSender.sendMessage(Chat.color(s)));
    }

    @Subcommand("openshop")
    @CommandCompletion("@shops")
    public void openShopCommand(CommandSender commandSender, String shop) {
        Shop.getInstance().open((Player) commandSender, Shards.getInstance().getShopManager().getShopByNameIgnoreCase(shop));
    }

    @Subcommand("reload")
    public void reloadCommand(CommandSender commandSender){
        long startTime = System.currentTimeMillis();
        instance.getCategoryFile().reload();
        instance.getLangFile().reload();
        instance.getSettingsFile().reload();
        instance.getShopManager().loadShops();
        instance.getShopManager().getShopFileManager().loadFiles();
        commandSender.sendMessage(Chat.color("&c&l(!) &cReloaded in " + (System.currentTimeMillis() - startTime) + "ms"));
    }

    @Subcommand("setshards")
    public void setShardsCommand(CommandSender commandSender, OnlinePlayer onlinePlayer, long amount){
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(onlinePlayer.player);
        playerData.setBalance(amount);
        commandSender.sendMessage(Chat.color("&c&l(!) &cUpdated shards for &e&n" + onlinePlayer.player.getName()));
    }

    @Subcommand("addshards|addshard")
    public void addShardsCommand(CommandSender commandSender, OnlinePlayer onlinePlayer, long amount){
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(onlinePlayer.player);
        playerData.setBalance(playerData.getBalance() + amount);
        commandSender.sendMessage(Chat.color("&c&l(!) &cUpdated shards for &e&n" + onlinePlayer.player.getName()));
    }

    @Subcommand("removeshards|removeshard")
    public void removeShardsCommand(CommandSender commandSender, OnlinePlayer onlinePlayer, long amount){
        PlayerData playerData = instance.getPlayerDataManager().getPlayerData(onlinePlayer.player);
        playerData.setBalance(playerData.getBalance() - amount);
        commandSender.sendMessage(Chat.color("&c&l(!) &cUpdated shards for &e&n" + onlinePlayer.player.getName()));
    }


}
