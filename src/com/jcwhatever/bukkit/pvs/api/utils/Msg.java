package com.jcwhatever.bukkit.pvs.api.utils;

import com.jcwhatever.bukkit.generic.messaging.Messenger;
import com.jcwhatever.bukkit.generic.messaging.ChatPaginator;
import com.jcwhatever.bukkit.generic.messaging.ChatPaginator.PaginatorTemplate;
import com.jcwhatever.bukkit.generic.utils.PreCon;
import com.jcwhatever.bukkit.generic.utils.TextUtils;
import com.jcwhatever.bukkit.pvs.api.PVStarAPI;
import com.jcwhatever.bukkit.pvs.api.arena.Arena;
import com.jcwhatever.bukkit.pvs.api.arena.ArenaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Player chat and console logging utilities.
 */
public class Msg {

    private Msg() {}

    /**
     * Tell a command sender a message.
     *
     * @param sender   The sender.
     * @param message  The message to tell.
     * @param params   Optional format parameters.
     */
    public static void tell(CommandSender sender, String message, Object...params) {
        PreCon.notNull(sender);
        PreCon.notNullOrEmpty(message);
        PreCon.notNull(params);

        Messenger.tell(PVStarAPI.getPlugin(), sender, message, params);
    }

    /**
     * Tell an arena player a message.
     *
     * @param player   The arena player.
     * @param message  The message to tell.
     * @param params   Optional format parameters.
     */
    public static void tell(ArenaPlayer player, String message, Object...params) {
        PreCon.notNull(player);
        PreCon.notNullOrEmpty(message);
        PreCon.notNull(params);

        Messenger.tell(PVStarAPI.getPlugin(), player.getHandle(), message, params);
    }

    /**
     * Tell a collection of arena players a message.
     *
     * @param players  The arena players.
     * @param message  The message to tell.
     * @param params   Optional format parameters.
     */
    public static void tell(Collection<ArenaPlayer> players, String message, Object...params) {
        PreCon.notNull(players);
        PreCon.notNullOrEmpty(message);
        PreCon.notNull(params);

        String formatted = TextUtils.format(message, params);

        for (ArenaPlayer player : players) {
            Messenger.tell(PVStarAPI.getPlugin(), player.getHandle(), formatted);
        }
    }

    /**
     * Tell a command sender a message in error format.
     *
     * @param sender   The sender.
     * @param message  The message to tell.
     * @param params   Optional format parameters.
     */
    public static void tellError(CommandSender sender, String message, Object...params) {
        PreCon.notNull(sender);
        PreCon.notNullOrEmpty(message);
        PreCon.notNull(params);

        Messenger.tell(PVStarAPI.getPlugin(), sender, "{RED}" + message, params);
    }

    /**
     * Tell an arena player a message in error format.
     *
     * @param player   The arena player.
     * @param message  The message to tell.
     * @param params   Optional format parameters.
     */
    public static void tellError(ArenaPlayer player, String message, Object...params) {
        PreCon.notNull(player);
        PreCon.notNullOrEmpty(message);
        PreCon.notNull(params);

        Messenger.tell(PVStarAPI.getPlugin(), player.getHandle(), "{RED}" + message, params);
    }

    /**
     * Tell all arena players with a relation to the specified arena a message.
     *
     * @param arena    The arena.
     * @param message  The message to tell.
     * @param params   Optional format parameters.
     */
    public static void tellArena(Arena arena, String message, Object... params) {
        PreCon.notNull(arena);
        PreCon.notNullOrEmpty(message);
        PreCon.notNull(params);

        List<ArenaPlayer> lobby = arena.getLobbyManager().getPlayers();
        List<ArenaPlayer> spectators = arena.getSpectatorManager().getPlayers();
        List<ArenaPlayer> gamers = arena.getGameManager().getPlayers();

        String formatted = TextUtils.format(message, params);

        for (ArenaPlayer player : lobby)
            tell(player.getHandle(), formatted);

        for (ArenaPlayer player : spectators)
            tell(player.getHandle(), formatted);

        for (ArenaPlayer player : gamers)
            tell(player.getHandle(), formatted);
    }

    /**
     * Broadcast a message to all players who are not in an arena.
     *
     * @param message  The message to tell.
     * @param params   Optional format parameters.
     */
    public static void tellNonArena(String message, Object... params) {
        PreCon.notNullOrEmpty(message);
        PreCon.notNull(params);

        String formatted = TextUtils.format(message, params);

        Player[] players = Bukkit.getServer().getOnlinePlayers();
        for (Player p : players) {

            ArenaPlayer player = PVStarAPI.getArenaPlayer(p);
            if (player.getArena() != null)
                continue;

            tell(p, formatted);
        }
    }

    /**
     * Tell a player an important message that will be stored if the player is not online.
     *
     * @param playerId  The id of the player.
     * @param context   The context of the message.
     * @param message   The message to tell.
     * @param params    Optional format parameters.
     */
    public static void tellImportant(UUID playerId, String context, String message, Object...params) {
    	Messenger.tellImportant(PVStarAPI.getPlugin(), playerId, context, message, params);
    }

    /**
     * Broadcast a message to all players on the server.
     *
     * @param message  The message to broadcast.
     * @param params   Optional format parameters.
     */
    public static void broadcast(String message, Object...params) {
        Messenger.broadcast(PVStarAPI.getPlugin(), message, params);

    }

    /**
     * Broadcast a message to all players on the server, excluding
     * the specified players.
     *
     * @param message  The message to broadcast.
     * @param exclude  The player to exclude from the broadcast.
     * @param params   Optional format parameters.
     */
    public static void broadcast(String message, Collection<Player> exclude, Object...params) {
        Messenger.broadcast(PVStarAPI.getPlugin(), message, exclude, params);
    }

    /**
     * Get a paginator for listing things in chat.
     *
     * @param title   The title of the paginator.
     * @param params  Optional format parameters.
     */
    public static ChatPaginator getPaginator(String title, Object...params) {
        return new ChatPaginator(PVStarAPI.getPlugin(), 6, PaginatorTemplate.HEADER, PaginatorTemplate.FOOTER, TextUtils.format(title, params));
    }

    /**
     * Write a debug message to the console and log. Message is
     * disregarded unless debugging is turned on.
     *
     * @param message  The message to write.
     * @param params   Optional format parameters.
     */
    public static void debug(String message, Object...params) {
        if (!PVStarAPI.getPlugin().isDebugging())
            return;
        Messenger.debug(PVStarAPI.getPlugin(), message, params);
    }

    /**
     * Write an info message to the console and log.
     *
     * @param message  The message to write.
     * @param params   Optional format parameters.
     */
    public static void info(String message, Object...params) {
		Messenger.info(PVStarAPI.getPlugin(), message, params);
	}

    /**
     * Write a warning message to the console and log.
     *
     * @param message  The message to write.
     * @param params   Optional format parameters.
     */
    public static void warning(String message, Object...params) {
    	Messenger.warning(PVStarAPI.getPlugin(), message, params);
    }

    /**
     * Write a severe error message to the console and log.
     *
     * @param message  The message to write.
     * @param params   Optional format parameters.
     */
    public static void severe(String message, Object...params) {
    	Messenger.severe(PVStarAPI.getPlugin(), message, params);
    }

}
