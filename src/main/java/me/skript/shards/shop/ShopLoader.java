package me.skript.shards.shop;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import me.skript.shards.Shards;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@Setter
public class ShopLoader {

    private final Map<String, YamlConfiguration> yamlConfigurationMap = Maps.newHashMap();

    private Shards instance;

    public ShopLoader(Shards instance) {
        this.instance = instance;
        loadFiles();
    }

    public void loadFiles() {
        File directory = new File(instance.getDataFolder().getAbsoluteFile() + "/shops/");
        if (!directory.exists()) {
            instance.saveResource("shops/example.yml", false);
        }
        try (Stream<Path> paths = java.nio.file.Files.walk(Paths.get(instance.getDataFolder().getAbsolutePath() + "/shops/"))) {
            paths.filter(java.nio.file.Files::isRegularFile).forEach(file -> {
                String plain = file.getFileName().toString();
                if (plain.contains(".")) plain = plain.substring(0, plain.lastIndexOf('.'));
                getYamlConfigurationMap().put(plain, YamlConfiguration.loadConfiguration(file.toFile()));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
