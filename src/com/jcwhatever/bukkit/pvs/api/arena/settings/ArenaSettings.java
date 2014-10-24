package com.jcwhatever.bukkit.pvs.api.arena.settings;

import com.jcwhatever.bukkit.pvs.api.arena.options.OutsidersAction;
import org.bukkit.Location;

import javax.annotation.Nullable;

/**
 * Provides settings for an arena.
 */
public interface ArenaSettings {

    /**
     * Determine if the arena is enabled.
     */
    boolean isEnabled();

    /**
     * Set arena enabled.
     *
     * @param isEnabled  True to enable.
     */
    void setEnabled(boolean isEnabled);

    /**
     * Determine if the arena is visible to players
     * in arena lists and joinable via commands.
     */
    boolean isVisible();

    /**
     * Set the arenas visibility to players.
     */
    void setIsVisible(boolean isVisible);

    /**
     * Get a custom type name for display.
     */
    String getTypeDisplayName();

    /**
     * Set a custom type display name for the arena
     *
     * @param typeDisplayName  The new type display name.
     */
    void setTypeDisplayName(@Nullable String typeDisplayName);

    /**
     * Get the minimum players needed to play the arena.
     */
    int getMinPlayers();

    /**
     * Set the minimum players needed to play the arena.
     *
     * @param minPlayers  The number of players.
     */
    void setMinPlayers(int minPlayers);

    /**
     * Get the maximum players allowed in the arena.
     */
    int getMaxPlayers();

    /**
     * Set the maximum players allowed in the arena.
     *
     * @param maxPlayers  The number of players.
     */
    void setMaxPlayers(int maxPlayers);

    /**
     * Determine if natural mob spawns are enabled
     * in the arena.
     */
    boolean isMobSpawnEnabled();

    /**
     * Set flag for natural mob spawns in the arena.
     */
    void setMobSpawnEnabled(boolean isEnabled);

    /**
     * Determine if player can break blocks in the
     * arena.
     */
    boolean isArenaDamageEnabled();

    /**
     * Set if player can break blocks in the arena.
     */
    void setArenaDamageEnabled(boolean isEnabled);

    /**
     * Determine if arena auto restores when the arena
     * ends.
     */
    boolean isAutoRestoreEnabled();

    /**
     * Set arena auto restore.
     */
    void setAutoRestoreEnabled(boolean isEnabled);

    /**
     * Get the location a player is teleported to when
     * they are removed from the arena region.
     *
     * <p>If the location is not set, the world spawn is returned.</p>
     */
    Location getRemoveLocation();

    /**
     * Set the location a player is teleported to when
     * they are removed from the arena.
     *
     * @param location  The location. Null to use the world spawn location.
     */
    void setRemoveLocation(@Nullable Location location);


    /**
     * Get the action to take when an outsider enters the
     * arena region.
     */
    OutsidersAction getOutsidersAction();

    /**
     * Set the action to take when an outsider enters the
     * arena region.
     *
     * @param action  The action to take.
     */
    void setOutsidersAction(OutsidersAction action);
}