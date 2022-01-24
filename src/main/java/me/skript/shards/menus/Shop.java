package me.skript.shards.menus;


import me.skript.shards.Shards;
import me.skript.shards.api.ShopPurchaseEvent;
import me.skript.shards.playerdata.PlayerDataManager;
import me.skript.shards.shop.ShopItem;
import net.lucaudev.api.chat.Chat;
import net.lucaudev.api.data.Constants;
import net.lucaudev.api.gui.menu.types.simple.Gui;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Shop {

    private static Shop instance;

    public static Shop getInstance() {
        return instance == null ? (instance = new Shop()) : instance;
    }

    public void open(Player player, me.skript.shards.shop.Shop shop) {
        Shards instance = Shards.getInstance();
        PlayerDataManager playerDataManager = instance.getPlayerDataManager();
        FileConfiguration config = instance.getLangFile().getConfig();
        Gui gui = new Gui(shop.getShopTitle(), shop.getInventoryRows());

        for (int i : shop.getFiller().getSlots())
            gui.setItem(i, shop.getFiller().getItemStack(), (clicker, event) -> event.setCancelled(true));

        for (ShopItem shopItem : shop.getItemList()) {
            gui.setItem(shopItem.getItemSlot(), shopItem.getDisplayItem(), (clicker, event) -> {
                event.setCancelled(true);

                if (shopItem.getItemPrice() > playerDataManager.getPlayerData(clicker).getBalance()) {
                    clicker.sendMessage(Chat.color(config.getString("Shop.Purchase.Insufficient.Message")
                            .replace("%shards%", String.valueOf(playerDataManager.getPlayerData(clicker).getBalance()))
                            .replace("%shards_formatted%", Constants.BALANCE_FORMAT.format(playerDataManager.getPlayerData(clicker).getBalance()))));
                    return;
                }

                ShopPurchaseEvent shopPurchaseEvent = new ShopPurchaseEvent(clicker, shopItem, shop);
                instance.getServer().getPluginManager().callEvent(shopPurchaseEvent);

                if (shopPurchaseEvent.isCancelled())
                    return;

                playerDataManager.getPlayerData(clicker).setPurchasedItemCount(playerDataManager.getPlayerData(clicker).getPurchasedItemCount() + 1);
                playerDataManager.getPlayerData(clicker).setBalance(playerDataManager.getPlayerData(clicker).getBalance() - shopItem.getItemPrice());

                for (String string : shopItem.getCommandList())
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", clicker.getName()));

                if (shopItem.isGiveDisplayItem()) {
                    clicker.getInventory().addItem(shopItem.getDisplayItem());
                    clicker.updateInventory();
                }

                clicker.sendMessage(Chat.color(config.getString("Shop.Purchase.Purchased.Message")
                        .replace("%cost%", String.valueOf(shopItem.getItemPrice()))
                        .replace("%item%", shopItem.getDisplayItem().getItemMeta().hasDisplayName() ? shopItem.getDisplayItem().getItemMeta().getDisplayName() : shopItem.getDisplayItem().getType().toString())
                        .replace("%shards%", String.valueOf(playerDataManager.getPlayerData(clicker).getBalance()))
                        .replace("%shards_formatted%", Constants.BALANCE_FORMAT.format(playerDataManager.getPlayerData(clicker).getBalance()))));
            });
        }
        gui.open(player);
    }


}
