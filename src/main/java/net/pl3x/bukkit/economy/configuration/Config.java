package net.pl3x.bukkit.economy.configuration;

import com.google.common.base.Throwables;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Config {
    public static String LANGUAGE_FILE = "lang-en.yml";

    public static String CURRENCY_NAME_PLURAL = "dollars";
    public static String CURRENCY_NAME_SINGULAR = "dollar";

    public static double STARTING_AMOUNT = 100.0D;

    private static void init() {
        LANGUAGE_FILE = getString("language-file", LANGUAGE_FILE);

        CURRENCY_NAME_PLURAL = getString("currency.name.plural", CURRENCY_NAME_PLURAL);
        CURRENCY_NAME_SINGULAR = getString("currency.name.singular", CURRENCY_NAME_SINGULAR);

        STARTING_AMOUNT = getDouble("starting-amount", STARTING_AMOUNT);
    }

    // ############################  DO NOT EDIT BELOW THIS LINE  ############################

    /**
     * Reload the configuration file
     */
    public static void reload(Plugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not load config.yml, please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }
        config.options().header("This is the configuration file for " + plugin.getName());
        config.options().copyDefaults(true);

        Config.init();

        try {
            config.save(configFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save " + configFile, ex);
        }
    }

    private static YamlConfiguration config;

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }
}
