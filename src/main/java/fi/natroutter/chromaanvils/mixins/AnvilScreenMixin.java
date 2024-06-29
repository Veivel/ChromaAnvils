package fi.natroutter.colorfulanvils.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import fi.natroutter.colorfulanvils.utilities.Colors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyori.adventure.text.Component;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {

    public AnvilScreenMixin(AnvilScreenHandler handler,PlayerInventory playerInventory,Text title,Identifier texture) {
        super(handler, playerInventory, title, texture);
    }

    @Shadow
    private TextFieldWidget nameField;

    @Inject(method = "setup", at = @At(value = "TAIL"))
    public void setup(CallbackInfo ci) {
        nameField.setMaxLength(255);
    }

    //this.nameField.setText(stack.isEmpty() ? "" : stack.getName().getString());

    @Inject(method = "onSlotUpdate", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setText(Ljava/lang/String;)V",
            shift = At.Shift.AFTER,
            ordinal = 0
    ))
    private void updateResult(CallbackInfo ci, @Local(argsOnly = true) ItemStack stack) {
        Text text = stack.getName();
        String name = text.getString();
        Component comp = text.asComponent();

        if (comp.getClass().getTypeName().endsWith("TextComponentImpl")) {
            name = Colors.serialize(comp);
        }
        nameField.setText(stack.isEmpty() ? "" : name);
    }

}