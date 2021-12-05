package me.skript.shards.shop;

import lombok.Getter;
import me.skript.shards.data.Filler;

import java.util.List;

@Getter
public class Shop {

    private String shopTitle;

    private int inventoryRows;

    private Filler filler;

    private List<ShopItem> itemList;

    public Shop(String shopTitle, int inventoryRows, Filler filler, List<ShopItem> itemList){
        this.shopTitle = shopTitle;
        this.inventoryRows = inventoryRows;
        this.filler = filler;
        this.itemList = itemList;
    }



}
