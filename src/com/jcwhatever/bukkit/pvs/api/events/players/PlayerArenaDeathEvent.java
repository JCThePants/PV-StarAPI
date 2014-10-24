package com.jcwhatever.bukkit.pvs.api.events.players;

import com.jcwhatever.bukkit.generic.events.Cancellable;
import com.jcwhatever.bukkit.pvs.api.arena.Arena;
import com.jcwhatever.bukkit.pvs.api.arena.ArenaPlayer;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Called when a player dies in an arena.
 */
@Cancellable
public class PlayerArenaDeathEvent extends AbstractPlayerEvent {

    private final PlayerDeathEvent _event;
    private final ArenaPlayer _killer;

    /**
     * Constructor.
     *
     * @param arena   The event arena.
     * @param player  The player who died.
     * @param killer  The player who killed the dead player, if any.
     * @param event   The parent event.
     */
    public PlayerArenaDeathEvent(Arena arena, ArenaPlayer player, @Nullable ArenaPlayer killer, PlayerDeathEvent event) {
        super(arena, player, true);
        _event = event;
        _killer = killer;
    }

    /**
     * Get the player who killed the dead player, if any.
     */
    @Nullable
    public ArenaPlayer getKiller() {
        return _killer;
    }

    /**
     * Get Exp to be dropped by the dead player.
     */
    public int getDroppedExp() {
        return _event.getDroppedExp();
    }

    /**
     * Set the Exp to be dropped by the dead player.
     *
     * @param exp  The exp amount.
     */
    public void setDroppedExp(int exp) {
        _event.setDroppedExp(exp);
    }

    /**
     * Get items to be dropped by the dead player.
     */
    public List<ItemStack> getDrops() {
        return _event.getDrops();
    }

    /**
     * Get the death message.
     */
    @Nullable
    public String getDeathMessage() {
        return _event.getDeathMessage();
    }

    /**
     * Set the death message.
     *
     * @param deathMessage  The message.
     */
    public void setDeathMessage(@Nullable String deathMessage) {
        _event.setDeathMessage(deathMessage);
    }

    /**
     * Get the amount of exp the dead player will respawn with.
     */
    public int getNewExp() {
        return _event.getNewExp();
    }

    /**
     * Set the amount of exp the dead player will respawn with.
     *
     * @param exp  The exp amount.
     */
    public void setNewExp(int exp) {
        _event.setNewExp(exp);
    }

    /**
     * Get the number of levels the dead player will respawn with.
     */
    public int getNewLevel() {
        return _event.getNewLevel();
    }

    /**
     * Set the number of levels the dead player will respawn with.
     *
     * @param level  The number of levels.
     */
    public void setNewLevel(int level) {
        _event.setNewLevel(level);
    }

    /**
     * Get the total number of exp the dead player will respawn with.
     */
    public int getNewTotalExp() {
        return _event.getNewTotalExp();
    }

    /**
     * Set the total number of exp the dead player will respawn with.
     *
     * @param totalExp  The total number of exp.
     */
    public void setNewTotalExp(int totalExp) {
        _event.setNewTotalExp(totalExp);
    }

    /**
     * Get if the dead player will keep all levels after respawning.
     */
    public boolean getKeepLevel() {
        return _event.getKeepLevel();
    }

    /**
     * Set if the dead player will keep all levels after respawning.
     *
     * @param keepLevel  The number of levels.
     */
    public void setKeepLevel(boolean keepLevel) {
        _event.setKeepLevel(keepLevel);
    }

}