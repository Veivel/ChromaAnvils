package fi.natroutter.chromaanvils.utilities;

import net.kyori.adventure.platform.fabric.FabricAudiences;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Objects;

public class Colors {

    public static volatile FabricAudiences audiences;

    public static MiniMessage miniMessage() {
        return MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.color())
                        .resolver(StandardTags.decorations())
                        .resolver(StandardTags.font())
                        .resolver(StandardTags.gradient())
                        .resolver(StandardTags.rainbow())
                        .resolver(StandardTags.transition())
                        .resolver(StandardTags.reset())
                        .build()
                ).build();
    }

    public static FabricAudiences getAudience() {
        //throw new IllegalStateException("Tried to access Adventure without a running server!");
        if (audiences == null) return null;
        return audiences;
    }

    public static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static String serialize(Component component) {
        return miniMessage().serialize(component);
    }

    public static Component deserialize(String value) {
        return miniMessage().deserialize(value);
    }

    public static Text toNative(Component component) {
        if (getAudience() == null) return null;
        return getAudience().toNative(component);
    }

}
