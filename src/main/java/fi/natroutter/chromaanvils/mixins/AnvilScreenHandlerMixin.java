package fi.natroutter.colorfulanvils.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import fi.natroutter.colorfulanvils.utilities.Colors;
import fi.natroutter.colorfulanvils.utilities.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow private @Nullable String newItemName;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;set(Lnet/minecraft/component/ComponentType;Ljava/lang/Object;)Ljava/lang/Object;",
            shift = At.Shift.AFTER,
            ordinal = 0
    ))
    private void updateResult(CallbackInfo ci, @Local(ordinal = 1) ItemStack stack) {
        ModifyResult(stack);
    }

    @Inject(method = "setNewItemName", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;set(Lnet/minecraft/component/ComponentType;Ljava/lang/Object;)Ljava/lang/Object;",
            shift = At.Shift.AFTER,
            ordinal = 0
    ))
    private void setNewItemName(String newItemName, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack stack) {
        ModifyResult(stack);
    }

    @Inject(method = "sanitize", at = @At("HEAD"), cancellable = true)
    private static void sanitize(String name, CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(name);
    }

    @Unique
    private void ModifyResult(ItemStack stack) {
        if (this.newItemName != null) {
            Component comp = Colors.deserialize(this.newItemName);

            String sComp = Colors.serialize(comp);
            String plain = Colors.plain(comp);
            String name = Utils.extractWithTags(sComp, 50);

            debug("SComp: " + sComp + " ("+sComp.length()+")");
            debug("plain: " + plain+ " ("+plain.length()+")");
            debug("name: " + name+ " ("+name.length()+")");
            debug(" ");

            comp = Colors.deserialize(name);
            stack.set(DataComponentTypes.CUSTOM_NAME, Colors.toNative(comp));
        }
    }
    private void debug(Object v) {System.out.println(v);}

}