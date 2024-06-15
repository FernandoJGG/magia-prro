package org.magiaperro.commands.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.magiaperro.commands.CommandRegistry;

public abstract class BaseCommand {
    public final String name;
    public final String description;
    public final String usage;
    
    protected String permission;
    protected Boolean playerOnly = true;
    protected Boolean adminOnly = true;

    public BaseCommand(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        
        CommandRegistry.registerCommand(this);
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            sender.sendMessage("No se puede ejecutar desde consola.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(permission)) {
            player.sendMessage("No tienes permiso para usar este comando.");
            return true;
        }
        if (adminOnly && !player.isOp()) {
            player.sendMessage("No tienes permiso para usar este comando.");
            return true;
        }

        return execute(player, command, label, args);
    }

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);

    protected boolean hasPermission(CommandSender sender) {
        return permission == null || permission.isEmpty() || sender.hasPermission(permission);
    }

    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }
}