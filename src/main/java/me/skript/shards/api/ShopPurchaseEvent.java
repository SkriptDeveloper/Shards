package me.skript.shards.api;

import lombok.Getter;
import me.skript.shards.shop.Shop;
import me.skript.shards.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ShopPurchaseEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;

    private final ShopItem purchasedItem;

    private final Shop shop;

    private boolean cancel;

    public ShopPurchaseEvent(Player player, ShopItem purchasedItem, Shop shop) {
        this.player = player;
        this.purchasedItem = purchasedItem;
        this.shop = shop;
        this.cancel = false;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
