package com.github.hanielcota;

import co.aikar.commands.PaperCommandManager;
import com.github.hanielcota.commands.SetWorldSpawnCommand;
import com.github.hanielcota.commands.WorldTPCommand;
import com.github.hanielcota.services.TeleportService;
import com.github.hanielcota.utils.ConfigUtils;
import com.github.hanielcota.utils.MessageService;
import com.github.hanielcota.utils.SpawnConfigUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin main class for WorldLink.
 * <p>
 * This class initializes configuration, services and command registration when
 * the plugin is enabled and performs basic cleanup when disabled.
 */
@Getter
public final class WorldLink extends JavaPlugin {

    private PaperCommandManager commandManager;
    private TeleportService teleportService;
    private MessageService messageService;
    private SpawnConfigUtils spawnConfigUtils;

    /**
     * Called when the plugin is enabled. Loads configuration, initializes
     * services and registers commands.
     */
    @Override
    public void onEnable() {
        // Load configuration
        ConfigUtils configUtils = new ConfigUtils(this, "config.yml");

        // Initialize services
        this.messageService = new MessageService(configUtils);
        this.spawnConfigUtils = new SpawnConfigUtils(configUtils);
        this.teleportService = new TeleportService(getLogger(), spawnConfigUtils);

        // Initialize command manager
        this.commandManager = new PaperCommandManager(this);

        // Register commands
        commandManager.registerCommand(new WorldTPCommand(this, teleportService, messageService));
        commandManager.registerCommand(new SetWorldSpawnCommand(configUtils, messageService));

        getLogger().info("WorldLink enabled.");
    }

    /**
     * Called when the plugin is disabled. Unregisters commands and performs
     * any necessary cleanup.
     */
    @Override
    public void onDisable() {
        if (commandManager != null) {
            commandManager.unregisterCommands();
        }

        getLogger().info("WorldLink disabled.");
    }
}
