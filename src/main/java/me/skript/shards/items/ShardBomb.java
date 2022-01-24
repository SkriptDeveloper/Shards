package me.skript.shards.items;

import com.google.common.collect.Lists;
import me.skript.shards.Shards;
import me.skript.shards.data.Constants;
import net.lucaudev.api.item.ItemBuilder;
import net.lucaudev.api.item.ItemUtils;
import net.lucaudev.api.item.XMaterial;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShardBomb implements Listener {

    private final Shards instance;

    private final List<Item> storedItems;

    public ShardBomb(Shards instance) {
        this.instance = instance;
        this.storedItems = Lists.newArrayList();
    }

    public static ItemStack getShardBomb() {
        FileConfiguration config = Shards.getInstance().getItemFile().getConfig();
        ItemBuilder itemBuilder = new ItemBuilder(XMaterial.matchXMaterial(config.getString("Shard Bomb.Display Item.Material")).get().parseMaterial())
                .name(config.getString("Shard Bomb.Display Item.Name"))
                .appendLore(config.getStringList("Shard Bomb.Display Item.Lore"))
                .data(config.getInt("Shard Bomb.Display Item.Data"));
        itemBuilder.nbt(Constants.SHARD_BOMB_KEY, true);
        itemBuilder.nbt(Constants.SHARD_BOMB_DURATION, config.getInt("Shard Bomb.Settings.Duration"));
        itemBuilder.nbt(Constants.SHARD_BOMB_AMOUNT, config.getString("Shard Bomb.Settings.Shard Count"));
        return itemBuilder.build();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();

        if (!ItemUtils.isValid(itemStack)) {
            return;
        }

        if (event.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }

        if (!isShardBomb(itemStack)) {
            return;
        }

        handle(player, itemStack);
    }

    private void handle(Player player, ItemStack itemStack) {
        Location location = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(2));
        Item item = location.getWorld().dropItem(location, itemStack);
        item.setVelocity(location.getDirection().normalize().multiply(1.2));
        item.setPickupDelay(Integer.MAX_VALUE);
        item.setCustomNameVisible(true);
        item.setCustomName(itemStack.getItemMeta().getDisplayName());
        storedItems.add(item);
    }

    private int getDurationFromShardBomb(ItemStack itemStack) {
        return ItemUtils.getNbtInt(itemStack, Constants.SHARD_BOMB_DURATION);
    }

    private boolean isShardBomb(ItemStack itemStack) {
        return ItemUtils.hasNbt(itemStack, Constants.SHARD_BOMB_KEY);
    }

}
