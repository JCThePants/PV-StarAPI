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

package com.jcwhatever.pvs.api.events.players;

import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.pvs.api.arena.IArena;
import com.jcwhatever.pvs.api.arena.IArenaPlayer;
import com.jcwhatever.pvs.api.arena.context.IContextManager;
import com.jcwhatever.pvs.api.arena.options.PlayerLeaveArenaReason;
import com.jcwhatever.pvs.api.arena.options.RemoveFromContextReason;

import org.bukkit.Location;

import javax.annotation.Nullable;

/**
 * Called after a player is removed from an arena.
 *
 * <p>Not to be confused with {@link PlayerPreRemoveFromContextEvent} and
 * {@link PlayerRemovedFromContextEvent}, which are used when a player is removed
 * from an arenas {@link IContextManager}. (ie. lobby, game or spectator)</p>
 *
 * <p>Although {@link PlayerPreRemoveFromContextEvent} and {@link PlayerRemovedFromContextEvent}
 * are called before {@link PlayerLeaveArenaEvent}, in cases where the player is
 * not actually leaving the arena ({@link RemoveFromContextReason#CONTEXT_CHANGE}),
 * {@link PlayerLeaveArenaEvent} is not called.</p>
 */
public class PlayerLeaveArenaEvent extends AbstractPlayerEvent {

    private final PlayerLeaveArenaReason _reason;
    private final Location _initialRestoreLocation;
    private Location _restoreLocation;

    /**
     * Constructor.
     *
     * @param arena           The event arena.
     * @param player          The player who was removed.
     * @param relatedManager  The manager the player is being removed from.
     * @param reason          The reason the player was removed.
     */
    public PlayerLeaveArenaEvent(IArena arena,
                                 IArenaPlayer player,
                                 IContextManager relatedManager,
                                 PlayerLeaveArenaReason reason,
                                 @Nullable Location restoreLocation) {

        super(arena, player, relatedManager);

        PreCon.notNull(reason);
        PreCon.notNull(relatedManager);

        _reason = reason;
        _initialRestoreLocation = restoreLocation;
        _restoreLocation = restoreLocation;
    }

    /**
     * Get the reason the player was removed.
     */
    public PlayerLeaveArenaReason getReason() {
        return _reason;
    }

    /**
     * Determine if the player is being restored to a location.
     */
    public boolean isRestoring() {
        return _reason != PlayerLeaveArenaReason.FORWARDING;
    }

    /**
     * Get the restore location initially set when
     * the event was called.
     */
    @Nullable
    public Location getInitialRestoreLocation() {
        return isRestoring() ? _initialRestoreLocation : null;
    }

    /**
     * Get the restore location.
     */
    @Nullable
    public Location getRestoreLocation() {
        return isRestoring() ? _restoreLocation : null;
    }

    /**
     * Set the restore location.
     *
     * @param location The location.
     */
    public void setRestoreLocation(@Nullable Location location) {
        _restoreLocation = location;
    }
}