package com.github.hanielcota.utils;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Utility for reading configured spawn points from a configuration file.
 * <p>
 * This reads the value under the path `spawns.<worldName>` and parses it into
 * a {@link Location}. If no custom value is configured or the value is
 * invalid, the world's default spawn location is returned. If the world is not
 * loaded, this returns {@code null}.
 */
@AllArgsConstructor
public class SpawnConfigUtils {

    private final ConfigUtils config;

    /**
     * Retrieves the configured spawn location for a world.
     * <p>
     * The configuration expects a comma-separated value in the format:
     * {@code x,y,z[,yaw,pitch]}. If yaw/pitch are omitted they default to 0.
     * If the configured value is missing or invalid this method falls back to
     * the world's default spawn location. Returns {@code null} when the world
     * with the provided name cannot be found.
     *
     * @param worldName the name of the world to get the spawn for
     * @return the parsed spawn {@link Location}, the world's default spawn if
     * configured value is missing/invalid, or {@code null} if the world is not loaded
     */
    public Location getSpawnLocation(String worldName) {
        FileConfiguration cfg = config.getConfig();
        String raw = cfg.getString("spawns." + worldName);

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }

        if (raw == null || raw.isBlank()) {
            return world.getSpawnLocation();
        }

        try {
            String[] parts = raw.split(",");

            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double z = Double.parseDouble(parts[2]);

            float yaw = 0f;
            float pitch = 0f;

            if (parts.length >= 5) {
                yaw = Float.parseFloat(parts[3]);
                pitch = Float.parseFloat(parts[4]);
            }

            return new Location(world, x + 0.5, y, z + 0.5, yaw, pitch);
        } catch (Exception e) {
            Bukkit.getLogger().warning(
                    "Invalid spawn for world '" + worldName + "' in config.yml"
            );

            return world.getSpawnLocation();
        }
    }
}
