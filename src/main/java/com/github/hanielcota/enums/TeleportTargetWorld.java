package com.github.hanielcota.enums;


import lombok.Getter;

/**
 * Enum identifying the common world targets supported by the plugin.
 * <p>
 * Each enum constant maps to the actual world folder/name used by the server.
 */
@Getter
public enum TeleportTargetWorld {

    OVERWORLD("world"),
    NETHER("world_nether"),
    THE_END("world_the_end");

    private final String worldName;

    TeleportTargetWorld(String worldName) {
        this.worldName = worldName;
    }

}