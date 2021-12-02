package me.skript.shards.menus;


import me.skript.shards.Shards;
import me.skript.shards.api.ShopPurchaseEvent;
import me.skript.shards.playerdata.PlayerDataManager;
import me.skript.shards.shop.ShopItem;
import net.lucaudev.api.chat.Chat;
import net.lucaudev.api.gui.menu.types.simple.Gui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Shop {

    private static Shop instance;

    public static Shop getInstance() {
        return instance == null ? (instance = new Shop()) : instance;
    }

    public void open(Player player, me.skript.shards.shop.Shop shop) {
        Shards instance = Shards.getInstance();
        PlayerDataManager playerDataManager = instance.getPlayerDataManager();
        Gui gui = new Gui(shop.getShopTitle(), shop.getInventoryRows());

        shop.getFiller().getSlots().forEach(i -> gui.setItem(i, shop.getFiller().getItemStack(), (clicker, event) -> event.setCancelled(true)));

        for (ShopItem shopItem : shop.getItemList()) {
            gui.setItem(shopItem.getItemSlot(), shopItem.getDisplayItem(), (clicker, event) -> {
                event.setCancelled(true);

                if (shopItem.getItemPrice() > playerDataManager.getPlayerData(clicker).getBalance()) {
                    clicker.sendMessage(Chat.color("&c&l(!) &cNot enough money (remember to change in config)"));
                    return;
                }
                ShopPurchaseEvent shopPurchaseEvent = new ShopPurchaseEvent(clicker, shopItem, shop);
                instance.getServer().getPluginManager().callEvent(shopPurchaseEvent);

                if(shopPurchaseEvent.isCancelled())
                    return;

                playerDataManager.getPlayerData(clicker).setBalance(playerDataManager.getPlayerData(clicker).getBalance() - shopItem.getItemPrice());
                shopItem.getCommandList().forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", clicker.getName())));
                if (shopItem.isGiveDisplayItem()) {
                    clicker.getInventory().addItem(shopItem.getDisplayItem());
                    clicker.updateInventory();
                }
            });
        }
        gui.open(player);
    }


}
