package me.skript.shards.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import me.skript.shards.Shards;
import me.skript.shards.menus.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("adminshop|ashop")
@CommandPermission("shard.admin")
public class AShardCommand extends BaseCommand {

    private Shards instance;

    public AShardCommand(Shards instance) {
        this.instance = instance;
    }

    @HelpCommand
    public void helpCommand(CommandSender commandSender){

    }

    @Subcommand("openshop")
    public void openShopCommand(CommandSender commandSender, String shop){
        Shop.getInstance().open((Player) commandSender, Shards.getInstance().getShopManager().getShopByNameIgnoreCase(shop));
    }



}
