/*
 * This file is part of PV-StarAPI for Bukkit, licensed under the MIT License (MIT).
 *
 * Copyright (c) JCThePants (www.jcwhatever.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


package com.jcwhatever.pvs.api.arena;

import com.jcwhatever.nucleus.managed.teleport.TeleportMode;
import com.jcwhatever.nucleus.mixins.IMeta;
import com.jcwhatever.nucleus.mixins.INamed;
import com.jcwhatever.nucleus.utils.MetaStore;
import com.jcwhatever.pvs.api.arena.context.IContextManager;
import com.jcwhatever.pvs.api.arena.mixins.IArenaOwned;
import com.jcwhatever.pvs.api.arena.options.ArenaContext;
import com.jcwhatever.pvs.api.arena.options.PlayerLeaveArenaReason;
import com.jcwhatever.pvs.api.arena.options.TeamChangeReason;
import com.jcwhatever.pvs.api.arena.settings.IContextSettings;
import com.jcwhatever.pvs.api.stats.ISessionStatTracker;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

/**
 * Wraps the {@link org.bukkit.entity.Player} object and carries extra arena
 * related meta data regarding the player.
 */
public interface IArenaPlayer extends INamed, IMeta, IArenaOwned {

    /**
     * Get the player instance unique ID.
     */
    UUID getUniqueId();

    /**
     * Get the players display name.
     *
     * @return The player name if the player has no display name set.
     */
    String getDisplayName();

    /**
     * Get the players current location.
     */
    Location getLocation();

    /**
     * Copy the players current location values into an output {@link Location}.
     *
     * @param output  The output {@link Location}.
     *
     * @return  The output {@link Location}.
     */
    Location getLocation(Location output);

    /**
     * Get the player entity.
     *
     * @return  The entity or null if does not exist.
     */
    @Nullable
    Entity getEntity();

    /**
     * Determine if the player is dead.
     */
    boolean isDead();

    /**
     * Determine if the player is on the server.
     */
    boolean isOnline();

    /**
     * Get the arena the player is currently in.
     */
    @Override
    @Nullable
    IArena getArena();

    /**
     * Get the most recent date/time that a player joined an arena during
     * their current login session.
     */
    @Nullable
    Date getJoinDate();

    /**
     * Determine if the player is ready to play.
     */
    boolean isReady();

    /**
     * Set the player ready variable.
     *
     * @param isReady  True if the player is ready.
     */
    void setReady(boolean isReady);

    /**
     * Determine if the player is immobilized.
     */
    boolean isImmobilized();

    /**
     * Set the players immobilized flag.
     *
     * @param isImmobilized  True to immobilize player.
     */
    void setImmobilized(boolean isImmobilized);

    /**
     * Determine if the player is invulnerable to damage.
     */
    boolean isInvulnerable();

    /**
     * Set the players invulnerability flag.
     *
     * @param isInvulnerable  True to make the player invulnerable to damage.
     */
    void setInvulnerable(boolean isInvulnerable);

    /**
     * Get the players team.
     */
    ArenaTeam getTeam();

    /**
     * Set the players team.
     *
     * @param team  The team to set.
     */
    void setTeam(ArenaTeam team, TeamChangeReason reason);

    /**
     * Get the number of lives the player has left.
     */
    int getLives();

    /**
     * Get the total points earned in the current session.
     */
    int getTotalPoints();

    /**
     * Get the number of points the player has in the current session.
     */
    int getPoints();

    /**
     * Increment player points by the specified amount.
     *
     * @param amount  The amount to increment. Can be negative.
     *
     * @return  The new points value.
     */
    int incrementPoints(int amount);

    /**
     * Get the players current session statistics.
     */
    ISessionStatTracker getSessionStats();

    /**
     * Get the {@link IArenaPlayerGroup} the player is part of.
     */
    @Nullable
    IArenaPlayerGroup getPlayerGroup();

    /**
     * Get the players relation to the arena.
     */
    ArenaContext getContext();

    /**
     * Get the context manager the player belongs to in the current arena.
     * (i.e. Lobby, Game, Spectator)
     *
     * @return  The manager of the players arena context or null if not in
     * an arena.
     */
    @Nullable
    IContextManager getContextManager();

    /**
     * Get {@link IContextSettings} implementation from the
     * arena the player is in based on their current arena relation.
     *
     * <p>i.e. If the player is in the lobby, returns the lobby managers
     * settings.</p>
     *
     * @return  The settings of the players arena context or null if not in
     * an arena.
     */
    @Nullable
    IContextSettings getContextSettings();

    /**
     * Transfer a player from their current arena context to the
     * specified manager context.
     *
     * @param context  The arena context to transfer the player to.
     *
     * @return  True if the player was transferred.
     */
    boolean changeContext(ArenaContext context);

    /**
     * Get the players meta data object for a specific arena,
     * which is used until the {@link IArenaPlayer} instance is disposed.
     *
     * @param arenaId  The ID of the arena.
     */
    MetaStore getMeta(UUID arenaId);

    /**
     * Get the global meta data object which is used until the
     * {@link IArenaPlayer} instance is disposed.
     */
    @Override
    MetaStore getMeta();

    /**
     * Get the players session meta data object, which is used until the
     * player joins another arena.
     */
    MetaStore getSessionMeta();

    /**
     * Get the players health.
     */
    double getHealth();

    /**
     * Set the players health.
     *
     * @param health  The players health.
     */
    void setHealth(double health);

    /**
     * Get the players max health.
     */
    double getMaxHealth();

    /**
     * Set the players max health.
     *
     * @param maxHealth  The players max health.
     */
    void setMaxHealth(double maxHealth);

    /**
     * Kill the player
     */
    void kill();

    /**
     * Kill the player and blame the specified arena player.
     *
     * @param blame  The arena player to blame.
     */
    void kill(@Nullable IArenaPlayer blame);

    /**
     * Respawn the specified player at a spawnpoint appropriate to their
     * current arena relation.
     *
     * <p>i.e. a lobby player gets teleported to one of the lobby spawns.</p>
     *
     * <p>This does not force a dead player to respawn. It simply teleports the
     * player back to once of the spawnpoints related to the players current
     * arena context relation.</p>
     *
     * @return  The location the player was spawned at or null if failed.
     */
    @Nullable
    Location respawn();

    /**
     * Remove the player from the arena using the {@link PlayerLeaveArenaReason#PLAYER_LEAVE}
     * reason.
     *
     * @return  True if successful, otherwise false.
     */
    boolean leaveArena();

    /**
     * Remove the player from the arena using the {@link PlayerLeaveArenaReason#KICK}
     * reason.
     *
     * @return  True if successful, otherwise false.
     */
    boolean kick();

    /**
     * Remove the player from the arena using the {@link PlayerLeaveArenaReason#LOSE}
     * reason.
     *
     * @return  True if successful, otherwise false.
     */
    boolean loseGame();

    /**
     * Teleport the player.
     *
     * @param location  The location.
     *
     * @return  True if teleported, otherwise false.
     */
    boolean teleport(Location location);

    /**
     * Teleport the player.
     *
     * @param location  The location.
     * @param mode      The teleport mode.
     *
     * @return  True if teleported, otherwise false.
     */
    boolean teleport(Location location, TeleportMode mode);
}

