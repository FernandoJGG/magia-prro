package org.magiaperro.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.gui.base.GuiGraphic;
import org.magiaperro.gui.base.PersistentGui;
import org.magiaperro.gui.base.strategies.PDCSaveStrategy;
import org.magiaperro.main.Keys;

import net.kyori.adventure.text.Component;

public class BackpackCommand extends BaseCommand {

    public BackpackCommand() {
        super("backpack", "Abre una mochila", "/backpack");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        
        //TEST: establece mana a 200
        player.getPersistentDataContainer().set(Keys.MANA, PersistentDataType.INTEGER, 200);
        
        GuiGraphic[] graphics = new GuiGraphic[] {
        	new GuiGraphic (
        		/*Material*/ 	Material.BLACK_STAINED_GLASS_PANE,
        		/*Slots*/ 		new int[] {0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26},
        		/*Text*/ 		Component.text("")
        	)
        };
        int[] persistentSlots = new int[] {10,11,12,13,14,15,16};
        int size = 27;
        PersistentGui backpack = new PersistentGui(
        	/* Size */		size, 
        	/* Title */		Component.text("Mochila"), 
        	/* Input */		persistentSlots, 
        	/* OutPut */	new int[0], 
        	/* Strategy */	new PDCSaveStrategy(player.getPersistentDataContainer()),
        	/* Graphics */	graphics
        );
        backpack.openInterface(player);
        

        //TEST: luego, sin pasar ese valor a mochila establece mana a 1000. comprobar a cuanto se queda
        player.getPersistentDataContainer().set(Keys.MANA, PersistentDataType.INTEGER, 1000);
        
        return true;
    }
}
