package com.jcwhatever.bukkit.pvs.api.arena;

import com.jcwhatever.bukkit.generic.events.GenericsEventHandler;
import com.jcwhatever.bukkit.generic.events.GenericsEventListener;
import com.jcwhatever.bukkit.generic.regions.RestorableRegion;
import com.jcwhatever.bukkit.generic.storage.IDataNode;
import com.jcwhatever.bukkit.generic.utils.Scheduler;
import com.jcwhatever.bukkit.pvs.api.PVStarAPI;
import com.jcwhatever.bukkit.pvs.api.arena.options.AddPlayerReason;
import com.jcwhatever.bukkit.pvs.api.arena.options.ArenaPlayerRelation;
import com.jcwhatever.bukkit.pvs.api.arena.options.OutOfBoundsAction;
import com.jcwhatever.bukkit.pvs.api.arena.options.OutsidersAction;
import com.jcwhatever.bukkit.pvs.api.arena.options.RemovePlayerReason;
import com.jcwhatever.bukkit.pvs.api.events.ArenaEndedEvent;
import com.jcwhatever.bukkit.pvs.api.events.ArenaStartedEvent;
import com.jcwhatever.bukkit.pvs.api.events.region.ArenaRegionPreRestoreEvent;
import com.jcwhatever.bukkit.pvs.api.events.region.ArenaRegionPreSaveEvent;
import com.jcwhatever.bukkit.pvs.api.events.region.ArenaRegionRestoredEvent;
import com.jcwhatever.bukkit.pvs.api.events.region.ArenaRegionSavedEvent;
import com.jcwhatever.bukkit.pvs.api.events.region.PlayerEnterArenaRegionEvent;
import com.jcwhatever.bukkit.pvs.api.events.region.PlayerLeaveArenaRegionEvent;
import com.jcwhatever.bukkit.pvs.api.utils.Msg;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A region that represents the bounds of an arena.
 */
public class ArenaRegion extends RestorableRegion implements GenericsEventListener {

    private final Arena _arena;

    /**
     * Constructor.
     *
     * @param arena     The owning arena.
     * @param dataNode  The regions data node.
     */
    public ArenaRegion(Arena arena, IDataNode dataNode) {
        super(PVStarAPI.getPlugin(), arena.getId().toString(), dataNode);
        _arena = arena;

        _arena.getEventManager().register(this);

        setMeta(ArenaRegion.class.getName(), this);
    }

    /**
     * Get the owning arena.
     */
    public Arena getArena() {
        return _arena;
    }

    /**
     * Determine if {@code onPlayerEnter} can be called.
     *
     * @param p  The player entering the region.
     *
     * @return  True to allow calling {@code onPlayerEnter}.
     */
    @Override
    protected boolean canDoPlayerEnter(Player p) {
        return _arena.getSettings().getOutsidersAction() != OutsidersAction.NONE &&
               _arena.getGameManager().isRunning() &&
               p.isOnline() &&
               !p.isDead();
    }

    /**
     * Called when a player enters the region.
     *
     * @param p  The player entering the region.
     */
    @Override
    protected void onPlayerEnter(Player p) {

        final ArenaPlayer player = PVStarAPI.getArenaPlayer(p);
        if (_arena.equals(player.getArena())) {
            return;
        }

        _arena.getEventManager().call(new PlayerEnterArenaRegionEvent(_arena, player));

        // check again later, gives time for leaving players to be removed from arena
        Scheduler.runTaskLater(PVStarAPI.getPlugin(), 5, new Runnable() {

            @Override
            public void run() {

                if (!_arena.getRegion().contains(player.getLocation()))
                    return;

                OutsidersAction action = _arena.getSettings().getOutsidersAction();

                switch (action) {
                    case NONE:
                        // do nothing
                        return;

                    case JOIN:
                        _arena.join(player, AddPlayerReason.PLAYER_JOIN);
                        break;

                    case KICK:
                        Location kickLocation = _arena.getSettings().getRemoveLocation();

                        player.getHandle().teleport(kickLocation);
                        Msg.tellError(player, "You're not allowed inside the arena during a match.");
                        break;
                }
            }
        });
    }

    /**
     * Determine if {@code onPlayerLeave} can be called.
     *
     * @param p  The player leaving the region.
     *
     * @return  True to allow calling {@code onPlayerLeave}.
     */
    @Override
    protected boolean canDoPlayerLeave(Player p) {
        return _arena.getGameManager().isRunning() &&
                _arena.getGameManager().getSettings().getOutOfBoundsAction() != OutOfBoundsAction.NONE;
    }

    /**
     * Called when a player leaves the region.
     *
     * @param p  The player leaving the region.
     */
    @Override
    protected void onPlayerLeave(Player p) {

        ArenaPlayer player = PVStarAPI.getArenaPlayer(p);
        if (!_arena.equals(player.getArena()))
            return;

        _arena.getEventManager().call(new PlayerLeaveArenaRegionEvent(_arena, player));

        if (_arena.equals(player.getArena()) && player.getArenaRelation() == ArenaPlayerRelation.GAME) {

            switch (_arena.getGameManager().getSettings().getOutOfBoundsAction()) {

                case KICK:
                    _arena.remove(player, RemovePlayerReason.KICK);
                    Msg.tellError(p, "Kicked for leaving the arena.");
                    break;

                case WIN:
                    if (!_arena.getGameManager().isGameOver())
                        _arena.getGameManager().setWinner(player);
                    break;

                case LOSE:
                    if (!_arena.getGameManager().isGameOver())
                        _arena.remove(player, RemovePlayerReason.LOSE);
                    break;

                case RESPAWN:
                    _arena.getGameManager().respawnPlayer(player);
                    break;

                default:
                    break;

            }
        }
    }

    /**
     * The file prefix of region save files.
     */
    @Override
    protected final String getFilePrefix() {
        return "arena." + _arena.getId();
    }

    /**
     * Called when the arena region is saved to disk.
     */
    @Override
    protected void onSave() {
        getArena().setBusy();
        _arena.getEventManager().call(new ArenaRegionPreSaveEvent(_arena));
    }

    /**
     * Called when the arena region save operation is completed.
     */
    @Override
    protected void onSaveComplete() {
        getArena().setIdle();
        _arena.getEventManager().call(new ArenaRegionSavedEvent(_arena));
    }

    /**
     * Called when the arena region is restored from disk.
     */
    @Override
    protected void onRestore() {
        getArena().setBusy();
        _arena.getEventManager().call(new ArenaRegionPreRestoreEvent(_arena));
    }

    /**
     * Called when the arena region restore operation is complete.
     */
    @Override
    protected void onRestoreComplete() {
        getArena().setIdle();
        _arena.getEventManager().call(new ArenaRegionRestoredEvent(_arena));
    }

    /**
     * Event handler. Sets player watcher flag on region if required when
     * the arena starts.
     */
    @GenericsEventHandler
    private void onArenaStarted(ArenaStartedEvent event) {
        if (_arena.getGameManager().getSettings().getOutOfBoundsAction() != OutOfBoundsAction.NONE) {
            setIsPlayerWatcher(true);
        }
    }

    /**
     * Event handler. Sets the arena player watcher flag to false
     * when the arena ends.
     */
    @GenericsEventHandler
    private void onArenaEnded(ArenaEndedEvent event) {
        setIsPlayerWatcher(false);
    }

}