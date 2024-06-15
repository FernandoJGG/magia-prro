package org.magiaperro.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.items.ItemRegistry;

public class GiveCustomCommand extends BaseCommand {

    public GiveCustomCommand() {
        super("givecustom", "Obtener un objeto custom", "/givecustom");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length < 1) {
        	player.sendMessage("Debes insertar un id");
        }
        
        Integer itemId = parseIntOrNull(args[0]);
        
        if (itemId == null) {
        	player.sendMessage("Debes insertar un id en formato numérico");
        }
        ItemStack varita = ItemRegistry.getCustomItem(itemId).buildItemStack();
        
        player.getInventory().addItem(varita);
       
        return true;
    }
    
    // Considerar una clase de utils
    public Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}