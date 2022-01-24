package me.skript.shards.shop;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import me.skript.shards.Shards;
import me.skript.shards.data.Filler;
import net.lucaudev.api.item.ItemBuilder;
import net.lucaudev.api.item.XMaterial;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
@Getter
public class ShopManager {

    private final ShopLoader shopFileManager;

    private final Map<String, Shop> shopMap = Maps.newHashMap();

    private final Shards instance;

    public ShopManager(Shards instance) {
        this.instance = instance;
        this.shopFileManager = new ShopLoader(instance);
        loadShops();
    }

    public void loadShops() {
        getShopFileManager().getYamlConfigurationMap().forEach((s, config) -> {
            List<ShopItem> categoryItems = Lists.newArrayList();
            for (String key : config.getConfigurationSection("Menu Items").getKeys(false)) {
                categoryItems.add(new ShopItem(
                        new ItemBuilder(XMaterial.matchXMaterial(config.getString("Menu Items." + key + ".Display Item.Material")).get().parseMaterial())
                                .name(config.getString("Menu Items." + key + ".Display Item.Name"))
                                .data(config.getInt("Menu Items." + key + ".Display Item.Data"))
                                .appendLore(config.getStringList("Menu Items." + key + ".Display Item.Lore")).build(),
                        config.getBoolean("Menu Items." + key + ".Settings.Give Display"),
                        config.getInt("Menu Items." + key + ".Settings.Price"),
                        config.getInt("Menu Items." + key + ".Settings.Slot"),
                        config.getStringList("Menu Items." + key + ".Settings.Commands"))
                );
            }
            shopMap.put(s,
                    new Shop(config.getString("Menu Settings.Title"),
                            config.getInt("Menu Settings.Size"),
                            new Filler(new ItemBuilder(XMaterial.matchXMaterial(config.getString("Menu Settings.Filler.Material")).get())
                                    .data(config.getInt("Menu Settings.Filler.Data")).build(),
                                    config.getIntegerList("Menu Settings.Filler.Slots")), categoryItems));
        });
    }

    public Shop getShopByNameIgnoreCase(String paramString) {
        return getShopMap().entrySet().stream().filter(stringShopEntry -> stringShopEntry.getKey().equalsIgnoreCase(paramString)).findFirst().get().getValue();
    }

}
