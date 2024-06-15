package org.magiaperro.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.gui.CustomInventoryHolder;

public class ExampleCommand extends BaseCommand {

    public ExampleCommand() {
        super("example", "Este es un comando de ejemplo", "/example");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

    	InventoryHolder a = new CustomInventoryHolder();
    	player.openInventory(a.getInventory());
    	
        return true;
    }
}