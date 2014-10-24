package com.jcwhatever.bukkit.pvs.api.modules;

import com.jcwhatever.bukkit.generic.language.LanguageManager;
import com.jcwhatever.bukkit.generic.modules.IJarModule;
import com.jcwhatever.bukkit.generic.storage.DataStorage;
import com.jcwhatever.bukkit.generic.storage.DataStorage.DataPath;
import com.jcwhatever.bukkit.generic.storage.IDataNode;
import com.jcwhatever.bukkit.generic.utils.PreCon;
import com.jcwhatever.bukkit.pvs.api.PVStarAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * PVStar module abstract class.
 *
 * <p>PV star modules must extend this class in order to be recognized.</p>
 */
public abstract class PVStarModule implements IJarModule {

    private final LanguageManager _languageManager = new LanguageManager();
    private final Map<String, IDataNode> _customNodes = new HashMap<>(10);
    private ModuleInfo _moduleInfo;
    private IDataNode _dataNode;
    private boolean _isPreEnabled;
    private boolean _isEnabled;
    private boolean _isDisposed;

    /**
     * Get the name of the module.
     */
    public final String getName() {
        return getInfo().getName();
    }

    /**
     * Get the module display version.
     */
    public final String getVersion() {
        return getInfo().getVersion();
    }

    /**
     * Get the module description.
     */
    public final String getDescription() {
        return getInfo().getDescription();
    }

    /**
     * Get the logical version of the module.
     */
    public final long getLogicalVersion() {
        return getInfo().getLogicalVersion();
    }

    /**
     * Get the names of required Bukkit dependencies.
     */
    public final Set<String> getBukkitDepends() {
        return getInfo().getBukkitDepends();
    }

    /**
     * Get the names of optional Bukkit dependencies.
     */
    public final Set<String> getBukkitSoftDepends() {
        return getInfo().getBukkitSoftDepends();
    }

    /**
     * Get the names of required PV-Star module dependencies.
     */
    public Set<String> getModuleDepends() {
        return getInfo().getModuleDepends();
    }

    /**
     * Get the names of optional PV-Star module dependencies.
     * @return
     */
    public Set<String> getModuleSoftDepends() {
        return getInfo().getModuleSoftDepends();
    }

    /**
     * Determine if the module has been pre-enabled.
     */
    public final boolean isPreEnabled() {
        return _isPreEnabled;
    }

    /**
     * Determine if the module is enabled.
     */
    public final boolean isEnabled() {
        return _isEnabled;
    }

    /**
     * Determine if the module has been disposed.
     */
    public final boolean isDisposed() {
        return _isDisposed;
    }

    /**
     * Internal Use Only. Called before the module is enabled.
     */
    public final void preEnable() {

        if (_isPreEnabled)
            throw new RuntimeException(
                    "Module named " + getName() + " is already pre-enabled. " +
                    " Please note that the preEnable() method is reserved for internal use only.");

        if (_isDisposed)
            throw new RuntimeException(
                    "Module named " + getName() + " is disposed and cannot be pre-enabled. " +
                            " Please note that the preEnable() method is reserved for internal use only.");

        _isPreEnabled = true;

        onRegisterTypes();
    }

    /**
     * Internal Use only. Called to enable the module.
     */
    public final void enable () {

        if (_isEnabled)
            throw new RuntimeException(
                    "Module named " + getName() + " is already enabled. " +
                            " Please note that the enable() method is reserved for internal use only.");

        if (_isDisposed)
            throw new RuntimeException(
                    "Module named " + getName() + " is disposed and cannot be enabled. " +
                            " Please note that the enable() method is reserved for internal use only.");

        _isEnabled = true;

        onEnable();
    }

    /**
     * Internal Use Only. Called to dispose resources used by the module before it
     * is discarded.
     */
    @Override
    public final void dispose() {

        if (_isDisposed)
            throw new RuntimeException(
                    "Module named " + getName() + " is disposed and cannot be re-disposed. " +
                            " Please note that the dispose() method is reserved for internal use only.");

        _isDisposed = true;

        onDispose();
    }

    /**
     * Get the modules language manager.
     */
    public final LanguageManager getLanguageManager() {
        return _languageManager;
    }

    /**
     * Get the modules data node.
     * @return
     */
    public final IDataNode getDataNode() {
        if (_dataNode == null) {
            _dataNode = DataStorage.getStorage(PVStarAPI.getPlugin(), new DataPath("modules." + getName() + ".config"));
            _dataNode.load();
        }

        return _dataNode;
    }

    /**
     * Get a data storage node.
     *
     * @param path  The relative data path of the node.
     */
    public final IDataNode getDataNode(String path) {
        PreCon.notNullOrEmpty(path);

        String key = path.toLowerCase();

        IDataNode node = _customNodes.get(key);
        if (node != null)
            return node;

        DataPath dataPath = new DataPath("modules." + getName()).getPath(path);
        node = DataStorage.getStorage(PVStarAPI.getPlugin(), dataPath);
        node.load();

        _customNodes.put(key, node);

        return node;
    }

    /**
     * Called during pre-enable. Types should be registered here.
     */
    protected abstract void onRegisterTypes();

    /**
     * Called when the module is enabled.
     */
    protected abstract void onEnable();

    /**
     * Called when the module is disposed.
     */
    protected void onDispose() {}


    // get module info
    private ModuleInfo getInfo() {
        if (_moduleInfo == null)
            _moduleInfo = PVStarAPI.getPlugin().getModuleInfo(this);

        return _moduleInfo;
    }

}