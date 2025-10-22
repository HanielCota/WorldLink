package com.github.hanielcota.enums;

/**
 * Represents the result of an attempted teleport operation.
 * <p>
 * Used by {@link com.github.hanielcota.services.TeleportService} to indicate
 * success or the specific failure reason so callers can react accordingly.
 */
public enum TeleportResult {
    /** Teleport succeeded. */
    SUCCESS,
    /** Target world was not found/loaded. */
    WORLD_NOT_FOUND,
    /** Resolved spawn location was not safe or could not be determined. */
    LOCATION_NOT_SAFE,
    /** A generic or unknown failure occurred during teleport. */
    GENERIC_FAILURE
}