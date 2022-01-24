package me.skript.shards;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.skript.shards.commands.AShardCommand;
import me.skript.shards.commands.ShardCommand;
import me.skript.shards.hook.PlaceholderAPI;
import me.skript.shards.items.ShardBomb;
import me.skript.shards.listener.PlayerListener;
import me.skript.shards.playerdata.PlayerDataManager;
import me.skript.shards.shop.ShopManager;
import net.lucaudev.api.gui.menu.listeners.BukkitMenuListener;
import net.lucaudev.api.gui.menu.types.simple.GuiListener;
import net.lucaudev.api.spigot.FileManager;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public class Shards extends JavaPlugin {

    @Getter
    private static Shards instance;

    private FileManager categoryFile;

    private FileManager settingsFile;

    private FileManager langFile;

    private FileManager itemFile;

    private ShopManager shopManager;

    private PlayerDataManager playerDataManager;

    private PaperCommandManager paperCommandManager;

    @Override
    public void onEnable() {
        instance = this;
        categoryFile = new FileManager(this, "categories.yml", getDataFolder().getAbsolutePath());
        settingsFile = new FileManager(this, "settings.yml", getDataFolder().getAbsolutePath());
        langFile = new FileManager(this, "lang.yml", getDataFolder().getAbsolutePath());
        itemFile = new FileManager(this, "items.yml", getDataFolder().getAbsolutePath());
        playerDataManager = new PlayerDataManager(this);
        shopManager = new ShopManager(this);
        paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.registerCommand(new AShardCommand(this));
        paperCommandManager.registerCommand(new ShardCommand(this));
        paperCommandManager.getCommandCompletions().registerCompletion("shops", c -> shopManager.getShopMap().keySet());
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitMenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ShardBomb(this), this);

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPI(this);
        }
    }

    @Override
    public void onDisable() {
        playerDataManager.onDisable();
    }
}
