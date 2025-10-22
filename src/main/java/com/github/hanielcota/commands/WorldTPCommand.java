package com.github.hanielcota.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Syntax;
import com.github.hanielcota.enums.TeleportResult;
import com.github.hanielcota.enums.TeleportTargetWorld;
import com.github.hanielcota.services.TeleportService;
import com.github.hanielcota.utils.MessageService;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Command that teleports a player to a configured world spawn.
 * <p>
 * Registered aliases: {@code tpworld}, {@code worldtp}.
 */
@CommandAlias("tpworld|worldtp")
@RequiredArgsConstructor
public class WorldTPCommand extends BaseCommand {

    private final JavaPlugin plugin;
    private final TeleportService teleportService;
    private final MessageService messageService;

    private static final Map<TeleportResult, String> RESULT_KEYS = Map.of(
            TeleportResult.SUCCESS, "success",
            TeleportResult.WORLD_NOT_FOUND, "world-not-found",
            TeleportResult.LOCATION_NOT_SAFE, "location-not-safe",
            TeleportResult.GENERIC_FAILURE, "generic-failure"
    );

    /**
     * Executes the teleport command for the calling player.
     * <p>
     * This method expects a {@link TeleportTargetWorld} parameter resolved by ACF.
     * It sends a "teleporting" message, performs the async teleport and then sends a
     * result message on the main thread when the teleport completes.
     *
     * @param player      the player executing the command (injected by ACF)
     * @param targetWorld the target world enum value (injected by ACF)
     */
    @Default
    @Syntax("<world>")
    @Description("Teleport to a predefined world.")
    public void onTeleport(Player player, TeleportTargetWorld targetWorld) {
        if (player == null) return;
        if (targetWorld == null) return;

        messageService.send(player, "teleporting", Map.of("world", targetWorld.name()));

        teleportService.teleportToWorld(player, targetWorld).thenAccept(result ->
                Bukkit.getScheduler().runTask(plugin, () -> {
                    String messageKey = RESULT_KEYS.getOrDefault(result, "generic-failure");
                    messageService.send(player, messageKey, Map.of("world", targetWorld.getWorldName()));
                })
        );
    }
}
