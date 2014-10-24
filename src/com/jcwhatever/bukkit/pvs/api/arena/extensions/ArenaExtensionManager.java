package com.jcwhatever.bukkit.pvs.api.arena.extensions;

import com.jcwhatever.bukkit.pvs.api.arena.Arena;

import javax.annotation.Nullable;
import java.util.Set;

/**
 *
 *  Manages extension instances for a specific arena instance.
 *
 */
public abstract class ArenaExtensionManager {

    /**
     * Get the owning arena.
     */
    public abstract Arena getArena();

    /**
     * Determine if the arena has an extension.
     *
     * @param typeName  The name of the extension.
     */
    public abstract boolean has(String typeName);

    /**
     * Determine if the arena has an extension.
     *
     * @param clazz  The extension type.
     */
    public abstract boolean has(Class<? extends ArenaExtension> clazz);

    /**
     * Get all arena extension instances.
     */
    public abstract Set<ArenaExtension> getAll();

    /**
     * Get an arena extension instance.
     *
     * @param name  The name of the instance.
     */
    @Nullable
    public abstract ArenaExtension get(String name);

    /**
     * Get an arena extension instance.
     *
     * @param clazz  The extension type.
     * @param <T>    The extension type.
     */
    @Nullable
    public abstract <T extends ArenaExtension> T get(Class<T> clazz);

    /**
     * Add an extension to the arena.
     *
     * @param name  The name of the extension type.
     * @param <T>   The extension type.
     *
     * @return  The extension instance.
     */
    @Nullable
    public abstract <T extends ArenaExtension> T add(String name);

    /**
     * Enable an installed extension.
     *
     * @param name  The name of the extension type.
     *
     * @return  True if enabled.
     */
    public abstract boolean enableExtension(String name);

    /**
     * Disable an installed extension.
     *
     * @param name  The name of the extension type.
     *
     * @return  True if disabled.
     */
    public abstract boolean disableExtension(String name);

    /**
     * Remove an arena extension.
     *
     * @param clazz  The extension type.
     */
    public abstract boolean remove(Class<? extends ArenaExtension> clazz);

    /**
     * Remove an arena extension.
     *
     * @param name  The name of the extension type.
     */
    public abstract boolean remove(String name);

    /**
     * Initialize an extension.
     *
     * @param extension  The extension to initialize.
     * @param info       The extension info annotation.
     */
    protected void initExtension(ArenaExtension extension, ArenaExtensionInfo info) {
        extension.init(info, getArena());
    }
}
