package com.github.hanielcota.utils;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Lightweight service used to send simple, placeholder-based messages to players.
 * <p>
 * This service uses {@link ConfigUtils#getString(String)} to read raw message
 * templates and performs basic token replacement using curly braces, e.g.
 * {@code {world}}.
 */
@RequiredArgsConstructor
public class MessageService {

    private final ConfigUtils config;

    /**
     * Sends a message to the provided player using a configured message template.
     * <p>
     * The template is looked up at {@code messages.<path>} and each entry from the
     * {@code placeholders} map is replaced like {@code {key} -> value}.
     *
     * @param player the recipient player
     * @param path the message key under the `messages` section in the config
     * @param placeholders map of placeholder keys to values to replace in the template
     */
    public void send(Player player, String path, Map<String, String> placeholders) {
        String raw = config.getString("messages." + path, "§cMessage not configured: " + path);
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            raw = raw.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        player.sendMessage(raw);
    }
}
