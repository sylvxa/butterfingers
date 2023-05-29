package lgbt.sylvia.butterfingers.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHandler {
    public static final String CONFIG_NAME = "butterfingers.json";
    public int attempts;

    public ConfigHandler(int attempts) {
        this.attempts = attempts;
    }

    public static void save(ConfigHandler config) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(config);

        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = Path.of(configDir.toString(), CONFIG_NAME);
        try (FileWriter writer = new FileWriter(file.toFile())) {
            writer.write(json);
        } catch (IOException ignored) {}

    }

    public static ConfigHandler load() {
        Gson gson = new Gson();
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = Path.of(configDir.toString(), CONFIG_NAME);
        if (Files.exists(file)) {
            try {
                return gson.fromJson(Files.readString(file), ConfigHandler.class);
            } catch (IOException ignored) {}
        }
        return new ConfigHandler(2);
    }
}