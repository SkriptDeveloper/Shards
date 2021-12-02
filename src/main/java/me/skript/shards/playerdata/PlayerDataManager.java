package me.skript.shards.playerdata;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import me.skript.shards.Shards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Shards instance;

    private final Map<UUID, PlayerData> playerDataMap = Maps.newHashMap();

    public PlayerDataManager(Shards instance) {
        this.instance = instance;
    }


    @SneakyThrows
    public void loadPlayerData(Player player){
        File file = new File(instance.getDataFolder().getAbsoluteFile() + "/player-data/" + player.getUniqueId() + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        PlayerData playerData = getPlayerData(player.getUniqueId());
        if(!yamlConfiguration.contains("Balance")){
            playerData.setBalance(instance.getSettingsFile().getConfig().getLong("Settings.Default Balance"));
        } else {
            playerData.setBalance(yamlConfiguration.getLong("Balance"));
        }

        if(!yamlConfiguration.contains("Pay Toggle")){
            playerData.setPayEnabled(true);
        } else {
            playerData.setPayEnabled(yamlConfiguration.getBoolean("Pay Toggle"));
        }

        yamlConfiguration.save(file);
    }


    @SneakyThrows
    public void savePlayerData(Player player){
        File file = new File(instance.getDataFolder().getAbsoluteFile() + "/player-data/" + player.getUniqueId() + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        PlayerData playerData = getPlayerData(player);
        yamlConfiguration.set("Balance", playerData.getBalance());
        yamlConfiguration.set("Pay Toggle", playerData.isPayEnabled());
        yamlConfiguration.save(file);
    }

    public void onDisable(){
        Bukkit.getOnlinePlayers().forEach(this::savePlayerData);
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID uuid){
        return playerDataMap.get(uuid);
    }

    public boolean hasPlayerData(Player player){
        return playerDataMap.containsKey(player.getUniqueId());
    }

    public void removePlayerData(Player player){
        playerDataMap.remove(player.getUniqueId());
    }

    public void createPlayerData(UUID uuid){
        playerDataMap.put(uuid, new PlayerData(uuid));
    }

}
