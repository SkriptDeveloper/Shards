package me.skript.shards.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @AllArgsConstructor
public class Filler {

    private ItemStack itemStack;

    private List<Integer> slots;


}
