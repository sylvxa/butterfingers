package lgbt.sylvia.butterfingers.config;

import lgbt.sylvia.butterfingers.Butterfingers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;

    public ConfigScreen(Screen screen) {
        super(Text.of("Butterfingers Config"));
        this.parent = screen;
        initWidgets();
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    private void initWidgets() {
        Window window = MinecraftClient.getInstance().getWindow();
        ConfigHandler configHandler = Butterfingers.getInstance().getConfigHandler();
        double value = configHandler.attempts;
        int width = window.getScaledWidth();
        int height = window.getScaledHeight();
        addDrawableChild(new SliderWidget(4, 4, width - 8, 20, Text.of("Attempts: " + (int) value), value/10) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.of(String.format("Attempts: " + (int) (this.value * 10))));
            }

            @Override
            protected void applyValue() {
                configHandler.attempts = (int) (this.value * 10);
            }
        });

        addDrawableChild(new ButtonWidget.Builder(Text.of("Save"), (button) -> {
            ConfigHandler.save(configHandler);
            this.close();
        }).dimensions(4, height - 24, width - 8, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
        super.render(context, mouseX, mouseY, delta);
    }
}
