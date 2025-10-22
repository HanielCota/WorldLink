package com.github.hanielcota.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;

/**
 * Helper for retrieving and parsing message templates from a configuration.
 * <p>
 * This class wraps {@link ConfigUtils} and provides convenience methods to get
 * MiniMessage or legacy-serialized components with optional placeholder/tag
 * resolvers.
 */
@SuppressWarnings("unused")
public record MessageUtils(ConfigUtils config) {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .character('&')
            .build();

    public MessageUtils {
        if (config == null) {
            throw new IllegalArgumentException("ConfigUtils cannot be null");
        }
    }

    private Component getNullPathErrorComponent() {
        config.getPlugin().getLogger().warning("Attempted to get message with null path: " + config.getFileName());
        return Component.text("Config error: null path", NamedTextColor.RED);
    }

    /**
     * Retrieves a MiniMessage-parsed component from the configured path.
     *
     * @param path configuration path to the message template (e.g. "messages.example")
     * @return parsed {@link Component}; on config errors a red error component is returned
     */
    public Component getMessage(String path) {
        if (path == null) {
            return getNullPathErrorComponent();
        }
        String message = config.getString(path, "<red>Missing message: " + path + "</red>");
        return MINI_MESSAGE.deserialize(message);
    }

    /**
     * Retrieves a MiniMessage-parsed component and applies tag resolvers.
     *
     * @param path      configuration path to the message template
     * @param resolvers optional resolvers to replace tags inside the message
     * @return parsed {@link Component}
     */
    public Component getMessage(String path, TagResolver... resolvers) {
        if (path == null) {
            return getNullPathErrorComponent();
        }

        String message = config.getString(path, "<red>Missing message: " + path + "</red>");

        if (resolvers == null || resolvers.length == 0) {
            return MINI_MESSAGE.deserialize(message);
        }

        ArrayList<TagResolver> safe = new ArrayList<>();
        for (TagResolver r : resolvers) {
            if (r != null) safe.add(r);
        }

        if (safe.isEmpty()) {
            return MINI_MESSAGE.deserialize(message);
        }

        return MINI_MESSAGE.deserialize(message, safe.toArray(new TagResolver[0]));
    }

    /**
     * Retrieves a MiniMessage-parsed component and replaces simple placeholder pairs.
     * <p>
     * The placeholders array is expected to contain key/value pairs in sequence.
     * Example: {@code new String[] { "world", "Earth", "player", "Jane" }}
     *
     * @param path         configuration path to the message template
     * @param placeholders alternating key/value pairs used to replace tokens like {@code <key>} in the message
     * @return parsed {@link Component}
     */
    public Component getMessage(String path, String... placeholders) {
        if (path == null) return getNullPathErrorComponent();
        if (placeholders == null || placeholders.length == 0) return getMessage(path);
        if (placeholders.length % 2 != 0) {
            config.getPlugin().getLogger().warning("Odd placeholder count for getMessage. Ignoring placeholders.");
            return getMessage(path);
        }

        String result = config.getString(path, "<red>Missing message: " + path + "</red>");
        for (int i = 0; i + 1 < placeholders.length; i += 2) {
            String key = placeholders[i];
            String value = placeholders[i + 1];
            if (key == null || value == null) {
                config.getPlugin().getLogger().warning("Null placeholder (key or value) in getMessage for path: " + path);
                continue;
            }

            result = result.replace("<" + key + ">", value);
        }

        return MINI_MESSAGE.deserialize(result);
    }

    /**
     * Retrieves a legacy-formatted message (using '&' color codes) and converts it to a Component.
     *
     * @param path configuration path to the legacy message
     * @return parsed {@link Component}
     */
    public Component getLegacyMessage(String path) {
        if (path == null) {
            return getNullPathErrorComponent();
        }
        String message = config.getString(path, "&cMissing message: " + path);
        return LEGACY_SERIALIZER.deserialize(message);
    }
}