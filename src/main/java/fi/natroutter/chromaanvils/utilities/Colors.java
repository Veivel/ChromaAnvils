package fi.natroutter.colorfulanvils.utilities;

import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.text.Text;

public class Colors {

    public static volatile FabricServerAudiences adventure;

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

    public static FabricServerAudiences adventure() {
        FabricServerAudiences ret = adventure;
        if(ret == null) {
            throw new IllegalStateException("Tried to access Adventure without a running server!");
        }
        return ret;
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
        return adventure().toNative(component);
    }

}
