package me.skript.shards;

import me.skript.shards.playerdata.PlayerData;
import me.skript.shards.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShardsAPI {

    private static ShardsAPI instance;

    public static ShardsAPI getInstance() {
        return instance == null ? (instance = new ShardsAPI()) : instance;
    }

    public void setBalance(Player player, long newBalance) {
        Shards.getInstance().getPlayerDataManager().getPlayerData(player).setBalance(newBalance);
    }

    public PlayerDataManager getPlayerDataManager() {
        return Shards.getInstance().getPlayerDataManager();
    }

    public List<PlayerData> getTop(int number) {
        return Shards.getInstance().getPlayerDataManager().getPlayerDataMap()
                .values().stream().sorted(Comparator.comparing(PlayerData::getBalance).reversed())
                .limit(number).collect(Collectors.toList());
    }

}
