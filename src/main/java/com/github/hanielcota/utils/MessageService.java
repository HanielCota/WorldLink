package com.github.hanielcota.services;

import org.bukkit.entity.Player;

public class MessageService {

    private final ConfigUtils config;

    public MessageService(ConfigUtils config) {
        this.config = config;
    }

    public void send(Player player, String path, String worldName) {
        String raw = config.getString("messages." + path, "§cMensagem não configurada: " + path);
        String parsed = raw.replace("{world}", worldName != null ? worldName : "");
        player.sendMessage(parsed);
    }
}
