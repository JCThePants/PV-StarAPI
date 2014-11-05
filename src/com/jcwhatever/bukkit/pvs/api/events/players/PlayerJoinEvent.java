/* This file is part of PV-StarAPI for Bukkit, licensed under the MIT License (MIT).
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


package com.jcwhatever.bukkit.pvs.api.events.players;

import com.jcwhatever.bukkit.generic.mixins.ICancellable;
import com.jcwhatever.bukkit.generic.utils.PreCon;
import com.jcwhatever.bukkit.pvs.api.arena.Arena;
import com.jcwhatever.bukkit.pvs.api.arena.ArenaPlayer;
import com.jcwhatever.bukkit.pvs.api.arena.options.AddPlayerReason;

import javax.annotation.Nullable;

/**
 * Event called when player joins an arena through the arena method
 * {@code join}.
 */
public class PlayerJoinEvent extends AbstractPlayerEvent implements ICancellable{

    private final AddPlayerReason _reason;
    private String _rejectionMessage;
    private boolean _isCancelled;

    /**
     * Constructor.
     *
     * @param arena   The arena being joined.
     * @param player  The player joining.
     * @param reason  The reason the player is joining.
     */
    public PlayerJoinEvent(Arena arena, ArenaPlayer player, AddPlayerReason reason) {
        super(arena, player);

        PreCon.notNull(reason);

        _reason = reason;
    }

    /**
     * Get the reason the player is joining the arena.
     */
    public AddPlayerReason getReason() {
        return _reason;
    }

    /**
     * Get the message displayed to the player if the event is cancelled.
     */
    @Nullable
    public String getRejectionMessage() {
        return _rejectionMessage;
    }

    /**
     * Set the message displayed to the player if the event is cancelled.
     *
     * @param rejectionMessage  The message.
     */
    public void setRejectionMessage(@Nullable String rejectionMessage) {
        _rejectionMessage = rejectionMessage;
    }

    @Override
    public boolean isCancelled() {
        return _isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        _isCancelled = isCancelled;
    }
}