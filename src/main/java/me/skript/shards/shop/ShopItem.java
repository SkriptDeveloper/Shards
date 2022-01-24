package me.skript.shards.shop;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class ShopItem {

    private ItemStack displayItem;

    private boolean giveDisplayItem;

    private int itemPrice;

    private int itemSlot;

    private List<String> commandList;

    public ShopItem(ItemStack displayItem, boolean giveDisplayItem, int itemPrice, int itemSlot, List<String> commandList) {
        this.displayItem = displayItem;
        this.giveDisplayItem = giveDisplayItem;
        this.itemPrice = itemPrice;
        this.itemSlot = itemSlot;
        this.commandList = commandList;
    }


}
