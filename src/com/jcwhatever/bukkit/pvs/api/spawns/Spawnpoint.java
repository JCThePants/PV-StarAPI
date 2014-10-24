package com.jcwhatever.bukkit.pvs.api.spawns;

import com.jcwhatever.bukkit.generic.mixins.INamedLocation;
import com.jcwhatever.bukkit.generic.mixins.INamedLocationDistance;
import com.jcwhatever.bukkit.generic.mixins.implemented.NamedLocationDistance;
import com.jcwhatever.bukkit.generic.utils.PreCon;
import com.jcwhatever.bukkit.pvs.api.arena.Arena;
import com.jcwhatever.bukkit.pvs.api.arena.ArenaPlayer;
import com.jcwhatever.bukkit.pvs.api.arena.ArenaTeam;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A named location used for specifying locations within arenas.
 *
 * @author JC The Pants
 *
 */
public class Spawnpoint extends Location implements INamedLocation {


    private final String _name;
    private final String _searchName;
    private final SpawnType _type;
    private ArenaTeam _team = ArenaTeam.NONE;


    /**
     * Constructor.
     *
     * @param name   The spawn point name.
     * @param type   The spawn type.
     * @param world  The world.
     * @param x      X coordinates.
     * @param y      Y coordinates.
     * @param z      Z coordinates.
     */
    public Spawnpoint(String name, SpawnType type, World world, double x, double y, double z) {
        super(world, x, y, z);

        PreCon.notNullOrEmpty(name);
        PreCon.notNull(type);

        _name = name;
        _searchName = name.toLowerCase();
        _type = type;
    }

    /**
     * Constructor.
     *
     * @param name   The spawn point name.
     * @param type   The spawn type.
     * @param world  The world.
     * @param x      X coordinates.
     * @param y      Y coordinates.
     * @param z      Z coordinates.
     * @param yaw    Yaw angle.
     * @param pitch  Pitch angle.
     */
    public Spawnpoint(String name, SpawnType type, World world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);

        PreCon.notNullOrEmpty(name);
        PreCon.notNull(type);

        _name = name;
        _searchName = name.toLowerCase();
        _type = type;
    }

    /**
     * Constructor.
     *
     * @param name      The spawn point name.
     * @param type      The spawn type.
     * @param location  The location data to duplicate.
     */
    public Spawnpoint(String name, SpawnType type, Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        PreCon.notNullOrEmpty(name);
        PreCon.notNull(type);

        _name = name;
        _searchName = name.toLowerCase();
        _type = type;
    }

    /**
     * Constructor.
     *
     * @param name   The spawn point name.
     * @param type   The spawn type.
     * @param team   The spawn team.
     * @param world  The world.
     * @param x      X coordinates.
     * @param y      Y coordinates.
     * @param z      Z coordinates.
     */
    public Spawnpoint(String name, SpawnType type, ArenaTeam team, World world, double x, double y, double z) {
        super(world, x, y, z);

        PreCon.notNullOrEmpty(name);
        PreCon.notNull(type);
        PreCon.notNull(team);

        _name = name;
        _searchName = name.toLowerCase();
        _type = type;
        _team = team;
    }

    /**
     * Constructor.
     *
     * @param name   The spawn point name.
     * @param type   The spawn type.
     * @param team   The spawn team.
     * @param world  The world.
     * @param x      X coordinates.
     * @param y      Y coordinates.
     * @param z      Z coordinates.
     * @param yaw    Yaw angle.
     * @param pitch  Pitch angle.
     */
    public Spawnpoint(String name, SpawnType type, ArenaTeam team, World world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);

        PreCon.notNullOrEmpty(name);
        PreCon.notNull(type);
        PreCon.notNull(team);

        _name = name;
        _searchName = name.toLowerCase();
        _type = type;
        _team = team;
    }

    /**
     * Constructor.
     *
     * @param name      The spawn point name.
     * @param type      The spawn type.
     * @param team      The spawn team.
     * @param location  The location data to duplicate.
     */
    public Spawnpoint(String name, SpawnType type, ArenaTeam team, Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        PreCon.notNullOrEmpty(name);
        PreCon.notNull(type);
        PreCon.notNull(team);

        _name = name;
        _searchName = name.toLowerCase();
        _type = type;
        _team = team;
    }


    /**
     * Get the spawn name.
     */
    @Override
    public final String getName() {
        return _name;
    }

    /**
     * Get the name of the spawn in lower case.
     */
    @Override
    public final String getSearchName() {
        return _searchName;
    }

    /**
     * Get the location
     */
    @Override
    public Location getLocation() {
        return this;
    }

    /**
     * Get the type of spawn point.
     */
    public SpawnType getSpawnType() {
        return _type;
    }

    /**
     * Get the spawn point team.
     */
    public ArenaTeam getTeam() {
        return _team;
    }

    /**
     * Get the distance to the specified location.
     *
     * @param location  The destination location.
     */
    @Override
    public INamedLocationDistance getDistance(Location location) {
        PreCon.notNull(location);

        return new NamedLocationDistance(this, location);
    }


    /**
     * Spawn an entity if the spawn type allows.
     *
     * @param arena  The arena to spawn for.
     * @param count  The number of entities to spawn.
     *
     * @return Null if the spawn type is not a spawner.
     */
    @Nullable
    public List<Entity> spawn(Arena arena, int count) {
        PreCon.notNull(arena);
        PreCon.greaterThanZero(count);

        if (!_type.isSpawner())
            return null;

        return _type.spawn(arena, getLocation(), count);
    }

    /**
     * Teleport an arena player to the spawnpoint.
     *
     * @param p  The player to spawn.
     */
    public void spawn(ArenaPlayer p) {
        PreCon.notNull(p);

        p.getHandle().teleport(this, TeleportCause.PLUGIN);
    }

}