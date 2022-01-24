package me.skript.shards.shop;

import lombok.Getter;
import me.skript.shards.data.Filler;

import java.util.List;

@Getter
public class Shop {

    private final String shopTitle;

    private final int inventoryRows;

    private final Filler filler;

    private final List<ShopItem> itemList;

    public Shop(String shopTitle, int inventoryRows, Filler filler, List<ShopItem> itemList) {
        this.shopTitle = shopTitle;
        this.inventoryRows = inventoryRows;
        this.filler = filler;
        this.itemList = itemList;
    }


}
