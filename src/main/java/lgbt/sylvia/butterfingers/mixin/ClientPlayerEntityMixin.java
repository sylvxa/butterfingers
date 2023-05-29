package lgbt.sylvia.butterfingers.mixin;

import lgbt.sylvia.butterfingers.Butterfingers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    Item lastItem;
    int currentAttempts;

    private int getMaxAttempts() {
        return Butterfingers.getInstance().getConfigHandler().attempts;
    }

    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    public void drop(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        Item item = player.getMainHandStack().getItem();
        currentAttempts++;
        if (item instanceof ToolItem || item instanceof ArmorItem || item instanceof BowItem || item instanceof TridentItem) {
            if (!item.equals(lastItem)) {
                lastItem = item;
                currentAttempts = 1;
            }

            if (currentAttempts >= getMaxAttempts()) {
                currentAttempts = 0;
                return;
            }

            warn();
            cir.setReturnValue(false);
        } else {
            currentAttempts = 0;
        }
    }

    private void warn() {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        int remaining = getMaxAttempts() - currentAttempts;
        String dropKey = MinecraftClient.getInstance().options.dropKey.getBoundKeyLocalizedText().getString();
        String warning = String.format("§cHit §l%s §r§c%s more time%s to drop it.", dropKey, remaining, remaining == 1 ? "" : "s");
        player.sendMessage(Text.of(warning), true);
    }
}
