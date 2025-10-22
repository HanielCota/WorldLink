package com.github.hanielcota.utils;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("unused")
@Getter
public final class ConfigUtils {

    private final JavaPlugin plugin;
    private final String fileName;
    private final File configFile;
    private FileConfiguration config;

    /**
     * Creates a new ConfigUtils bound to the provided plugin and file name.
     * If the file does not exist it will attempt to copy the default resource
     * from the plugin jar. The configuration is then loaded and ready to read.
     *
     * @param plugin   the owning JavaPlugin instance (must not be null)
     * @param fileName the filename within the plugin folder to manage (must not be null/empty)
     */
    public ConfigUtils(JavaPlugin plugin, String fileName) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name (fileName) cannot be null or empty");
        }

        this.plugin = plugin;
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);

        saveDefaultConfig();
        reloadConfig();
    }

    /**
     * Reloads the configuration from the file, applying defaults from the embedded resource if available.
     */
    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(configFile);

        try (InputStream defaultConfigStream = plugin.getResource(fileName)) {
            if (defaultConfigStream == null) {
                return;
            }

            try (InputStreamReader reader = new InputStreamReader(defaultConfigStream)) {
                FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(reader);
                this.config.setDefaults(defaultConfig);
            }

        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load default configuration: " + fileName, e);
        }
    }

    /**
     * Gets the FileConfiguration, reloading it if it's null.
     * This method is manually kept because @Getter would not provide the lazy-load logic.
     *
     * @return The loaded FileConfiguration.
     */
    public FileConfiguration getConfig() {
        if (this.config == null) {
            reloadConfig();
        }
        return this.config;
    }

    /**
     * Saves the current configuration to disk.
     */
    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save configuration: " + fileName, e);
        }
    }

    /**
     * Ensures the default resource is copied from the plugin jar when the file is missing.
     */
    public void saveDefaultConfig() {
        if (configFile.exists()) {
            return;
        }

        try {
            Files.createDirectories(plugin.getDataFolder().toPath());

            try (InputStream stream = plugin.getResource(fileName)) {

                if (stream == null) {
                    plugin.getLogger().warning("Default resource not found in JAR: " + fileName);

                    if (!configFile.createNewFile()) {
                        plugin.getLogger().severe("Could not create empty configuration file: " + fileName);
                    }
                    return;
                }

                Files.copy(stream, configFile.toPath());
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save default configuration: " + fileName, e);
        }
    }

    /**
     * Logs a warning about attempting to access a null path.
     */
    private void logNullPathWarning() {
        plugin.getLogger().warning("Attempted to access a NULL path in config '" + fileName + "'.");
    }

    public String getString(String path) {
        return getString(path, "");
    }

    public String getString(String path, String def) {
        if (path == null) {
            logNullPathWarning();
            return def;
        }
        return getConfig().getString(path, def);
    }

    public int getInt(String path) {
        return getInt(path, 0);
    }

    public int getInt(String path, int def) {
        if (path == null) {
            logNullPathWarning();
            return def;
        }
        return getConfig().getInt(path, def);
    }

    public double getDouble(String path) {
        return getDouble(path, 0.0);
    }

    public double getDouble(String path, double def) {
        if (path == null) {
            logNullPathWarning();
            return def;
        }
        return getConfig().getDouble(path, def);
    }

    public boolean getBoolean(String path) {
        if (path == null) {
            logNullPathWarning();
            return false;
        }
        return getConfig().getBoolean(path, false);
    }

    public List<String> getStringList(String path) {
        if (path == null) {
            logNullPathWarning();
            return Collections.emptyList();
        }
        return getConfig().getStringList(path);
    }

}

