package me.skript.shards.menus;

import me.skript.shards.Shards;
import me.skript.shards.shop.Shop;
import net.lucaudev.api.gui.menu.types.simple.Gui;
import net.lucaudev.api.item.ItemBuilder;
import net.lucaudev.api.item.XMaterial;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Category {

    private static Category instance;

    public static Category getInstance() {
        return instance == null ? (instance = new Category()) : instance;
    }


    public void open(Player player) {
        Shards instance = Shards.getInstance();
        FileConfiguration config = instance.getCategoryFile().getConfig();

        Gui gui = new Gui(config.getString("Menu Settings.Title"), config.getInt("Menu Settings.Size"));
        config.getIntegerList("Menu Settings.Filler.Slots").forEach(i -> gui.setItem(i, new ItemBuilder(XMaterial.matchXMaterial(config.getString("Menu Settings.Filler.Material")).get()).data(config.getInt("Menu Settings.Filler.Data")).build(), (clicker, event) -> event.setCancelled(true)));

        for(String string : config.getConfigurationSection("Shops").getKeys(false)){
            gui.setItem(config.getInt("Shops." + string + ".Slot"), new ItemBuilder(XMaterial.matchXMaterial(config.getString("Shops." + string + ".Material")).get().parseMaterial()).name(config.getString("Shops." + string + ".Name")).appendLore(config.getStringList("Shops." + string + ".Lore")).data(config.getInt("Shops." + string + ".Data")).build(), (clicker, event) -> {
                Shop shop = instance.getShopManager().getShopByNameIgnoreCase(config.getString("Shops." + string + ".Shop"));
                Validate.notNull(shop, "Player just attempted to open null shop!");
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(instance, () -> me.skript.shards.menus.Shop.getInstance().open(player, shop), 3L);
            });
        }

        gui.open(player);
    }


}
