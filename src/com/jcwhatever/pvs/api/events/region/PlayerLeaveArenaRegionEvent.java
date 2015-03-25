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


package com.jcwhatever.pvs.api.events.region;

import com.jcwhatever.pvs.api.arena.Arena;
import com.jcwhatever.pvs.api.arena.ArenaPlayer;
import com.jcwhatever.pvs.api.events.AbstractArenaEvent;
import com.jcwhatever.nucleus.regions.options.LeaveRegionReason;
import com.jcwhatever.nucleus.utils.PreCon;

/**
 * Called when a player leaves an arena region.
 */
public class PlayerLeaveArenaRegionEvent extends AbstractArenaEvent {

    private final ArenaPlayer _player;
    private final LeaveRegionReason _reason;

    /**
     * Constructor.
     *
     * @param arena   The event arena.
     * @param player  The player leaving the arena.
     */
    public PlayerLeaveArenaRegionEvent(Arena arena, ArenaPlayer player, LeaveRegionReason reason) {
        super(arena);
        PreCon.notNull(player);
        PreCon.notNull(reason);

        _player = player;
        _reason = reason;
    }

    /**
     * Get the player leaving the arena region.
     */
    public ArenaPlayer getPlayer() {
        return _player;
    }

    /**
     * Get the reason the player is leaving the
     * arena region.
     */
    public LeaveRegionReason getReason() {
        return _reason;
    }
}