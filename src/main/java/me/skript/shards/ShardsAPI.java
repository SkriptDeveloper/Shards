package me.skript.shards;

import org.bukkit.entity.Player;

public class ShardsAPI {

    private static ShardsAPI instance;

    public static ShardsAPI getInstance() {
        return instance == null ? (instance = new ShardsAPI()) : instance;
    }

    public void setBalance(Player player, long newBalance){
        Shards.getInstance().getPlayerDataManager().getPlayerData(player).setBalance(newBalance);
    }



}
