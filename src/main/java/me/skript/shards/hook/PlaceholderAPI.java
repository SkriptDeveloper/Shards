package me.skript.shards.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.skript.shards.Shards;
import net.lucaudev.api.data.Constants;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {

    private final Shards instance;

    public PlaceholderAPI(Shards instance) {
        this.instance = instance;
        register();
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
        return "1.9.2";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (params.equalsIgnoreCase("player_balance")) {
            return String.valueOf(instance.getPlayerDataManager().getPlayerData(player).getBalance());
        } else if (params.equalsIgnoreCase("player_balance_formatted")) {
            return Constants.BALANCE_FORMAT.format(instance.getPlayerDataManager().getPlayerData(player).getBalance());
        } else if (params.equalsIgnoreCase("player_purchase_count")) {
            return String.valueOf(instance.getPlayerDataManager().getPlayerData(player).getPurchasedItemCount());
        }
        return "";
    }
}
