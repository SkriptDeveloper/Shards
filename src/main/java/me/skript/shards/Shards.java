package me.skript.shards;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.skript.shards.commands.AShardCommand;
import me.skript.shards.commands.ShardCommand;
import me.skript.shards.listener.PlayerListener;
import me.skript.shards.playerdata.PlayerDataManager;
import me.skript.shards.shop.ShopManager;
import net.lucaudev.api.gui.menu.types.simple.GuiListener;
import net.lucaudev.api.spigot.FileManager;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public final class Shards extends JavaPlugin {

    @Getter
    private static Shards instance;

    private FileManager categoryFile;

    private FileManager settingsFile;

    private ShopManager shopManager;

    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        instance = this;
        categoryFile = new FileManager(this, "categories.yml", getDataFolder().getAbsolutePath());
        settingsFile = new FileManager(this, "settings.yml", getDataFolder().getAbsolutePath());
        playerDataManager = new PlayerDataManager(this);
        shopManager = new ShopManager(this);

        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        PaperCommandManager paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.registerCommand(new AShardCommand(this));
        paperCommandManager.registerCommand(new ShardCommand(this));
    }

    @Override
    public void onDisable() {
        playerDataManager.onDisable();
    }
}
