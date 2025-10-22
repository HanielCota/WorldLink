package com.github.hanielcota.services;

import com.github.hanielcota.enums.TeleportResult;
import com.github.hanielcota.enums.TeleportTargetWorld;
import com.github.hanielcota.utils.SpawnConfigUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service responsible for performing player teleports to configured world spawn points.
 * <p>
 * This service validates the target world exists and resolves the configured spawn
 * location via {@link SpawnConfigUtils}. Teleports are performed asynchronously and
 * return a {@link CompletableFuture} with a {@link TeleportResult} describing the outcome.
 */
@RequiredArgsConstructor
public class TeleportService {

    private final Logger logger;
    private final SpawnConfigUtils spawnConfigUtils;

    /**
     * Attempts to teleport a player to the spawn location of the requested world.
     * <p>
     * The method performs the following checks in order:
     * - null checks for parameters (returns GENERIC_FAILURE)
     * - world existence (returns WORLD_NOT_FOUND)
     * - resolved spawn location safety (returns LOCATION_NOT_SAFE if null)
     * <p>
     * The actual teleport is executed asynchronously using the Bukkit API; the returned
     * future completes with SUCCESS when teleport succeeded or with an appropriate
     * failure {@link TeleportResult}.
     *
     * @param player      the player to teleport (must not be null)
     * @param targetWorld the enum identifying the target world
     * @return a CompletableFuture that completes with the teleport result
     */
    public CompletableFuture<TeleportResult> teleportToWorld(Player player, TeleportTargetWorld targetWorld) {

        if (player == null || targetWorld == null) {
            return CompletableFuture.completedFuture(TeleportResult.GENERIC_FAILURE);
        }

        World world = Bukkit.getWorld(targetWorld.getWorldName());
        if (world == null) {
            return CompletableFuture.completedFuture(TeleportResult.WORLD_NOT_FOUND);
        }

        Location targetLocation = spawnConfigUtils.getSpawnLocation(world.getName());
        if (targetLocation == null) {
            return CompletableFuture.completedFuture(TeleportResult.LOCATION_NOT_SAFE);
        }

        return player.teleportAsync(targetLocation)
                .thenApply(success -> {
                    if (success) {
                        return TeleportResult.SUCCESS;
                    }

                    return TeleportResult.GENERIC_FAILURE;
                })
                .exceptionally(ex -> {
                    logger.log(
                            Level.WARNING,
                            "Failed to teleport player " + player.getName(),
                            ex
                    );

                    return TeleportResult.GENERIC_FAILURE;
                });
    }
}
