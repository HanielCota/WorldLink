package com.github.hanielcota.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.github.hanielcota.utils.ConfigUtils;
import com.github.hanielcota.utils.MessageService;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;

/**
 * Command used to set a custom spawn point for the current world.
 * <p>
 * Saves the player's current location into the plugin configuration under the
 * path {@code spawns.<world>} using the format {@code x,y,z,yaw,pitch}.
 */
@CommandAlias("setworldspawn")
@RequiredArgsConstructor
public class SetWorldSpawnCommand extends BaseCommand {

    private final ConfigUtils config;
    private final MessageService messageService;

    /**
     * Handles the /setworldspawn command for the executing player.
     * <p>
     * The player's precise coordinates (with yaw/pitch) are formatted and stored
     * in the configuration. A confirmation message is sent to the player.
     *
     * @param player the player executing the command (injected by ACF)
     */
    @Default
    @Description("Set the custom spawn point of the current world.")
    public void onSetSpawn(Player player) {
        if (player == null) return;

        Location loc = player.getLocation();
        String worldName = loc.getWorld().getName();

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();

        String value = String.format(Locale.ROOT, "%.6f,%.6f,%.6f,%.2f,%.2f", x, y, z, yaw, pitch);

        config.getConfig().set("spawns." + worldName, value);
        config.saveConfig();

        messageService.send(player, "set-spawn", Map.of("world", worldName, "coords", value));
    }
}
