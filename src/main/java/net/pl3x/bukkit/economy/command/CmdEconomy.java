package net.pl3x.bukkit.economy.command;

import net.pl3x.bukkit.economy.EconomyPlugin;
import net.pl3x.bukkit.economy.configuration.Config;
import net.pl3x.bukkit.economy.configuration.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class CmdEconomy implements TabExecutor {
    private final EconomyPlugin plugin;

    public CmdEconomy(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && "reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("command.economy")) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.economy")) {
            Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {
            Lang.send(sender, "&d" + plugin.getName() + " v" + plugin.getDescription().getVersion());
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            Config.reload(plugin);
            Lang.reload(plugin);

            Lang.send(sender, "&d" + plugin.getName() + " v" + plugin.getDescription().getVersion() + " reloaded");
            return true;
        }

        // TODO - more subcommands (give, take, set)

        return true;
    }
}
