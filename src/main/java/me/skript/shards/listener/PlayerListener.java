package me.skript.shards.listener;

import me.skript.shards.Shards;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private Shards instance;

    public PlayerListener(Shards instance){
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        instance.getPlayerDataManager().createPlayerData(player.getUniqueId());
        instance.getPlayerDataManager().loadPlayerData(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        instance.getPlayerDataManager().savePlayerData(player);
        instance.getPlayerDataManager().removePlayerData(player);
    }


}
