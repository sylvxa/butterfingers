package lgbt.sylvia.butterfingers;

import lgbt.sylvia.butterfingers.config.ConfigHandler;
import net.fabricmc.api.ClientModInitializer;

public class Butterfingers implements ClientModInitializer {
    private final ConfigHandler configHandler = ConfigHandler.load();
    private static Butterfingers INSTANCE;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ConfigHandler.save(configHandler)));
    }

    public static Butterfingers getInstance() {
        return INSTANCE;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
