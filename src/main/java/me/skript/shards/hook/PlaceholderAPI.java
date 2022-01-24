package me.skript.shards.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.skript.shards.Shards;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {

    private final Shards instance;

    public PlaceholderAPI(Shards instance) {
        this.instance = instance;
    }


    @Override
    public String getIdentifier() {
        return "shards";
    }

    @Override
    public String getAuthor() {
        return "lolskript";
    }

    @Override
    public String getVersion() {
        return "1.9-RECODE";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (params.equalsIgnoreCase("player_balance")) {
            return String.valueOf(instance.getPlayerDataManager().getPlayerData(player).getBalance());
        } else if (params.equalsIgnoreCase("player_purchase_count")) {
            return String.valueOf(instance.getPlayerDataManager().getPlayerData(player).getPurchasedItemCount());
        }
        return "";
    }
}
