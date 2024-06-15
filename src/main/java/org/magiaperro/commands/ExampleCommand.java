package org.magiaperro.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.items.ItemRegistry;
import org.magiaperro.items.base.ItemID;

public class ExampleCommand extends BaseCommand {

    public ExampleCommand() {
        super("example", "Este es un comando de ejemplo", "/example");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        
        // Crear una instancia de CustomItem
        ItemStack varita = ItemRegistry.getCustomItem(ItemID.Varita).buildItemStack();
        
        // Entregar el item personalizado al jugador
        player.getInventory().addItem(varita);
       
        sender.sendMessage("Has recibido un item personalizado.");
        return true;
    }
}