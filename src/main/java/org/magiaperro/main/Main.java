package org.magiaperro.main;

import org.magiaperro.blocks.BlockRegistry;
import org.magiaperro.commands.CommandRegistry;
import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.items.*;
import org.magiaperro.listeners.OnPlayerInteractListener;
import org.magiaperro.listeners.InventoryListener;
import org.magiaperro.listeners.BlockDisappearListener;
import org.magiaperro.listeners.BlockPlaceListener;
import org.magiaperro.spells.SpellRegistry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        
        ItemRegistry.register();
        SpellRegistry.register();
        CommandRegistry.register();
        BlockRegistry.register();
        
        this.registerListeners();
    }
    
    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new OnPlayerInteractListener(), this);    
        getServer().getPluginManager().registerEvents(new BlockDisappearListener(), this);    
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
    }

    @Override
    public void onDisable() {
        // Se ejecuta cuando el plugin se deshabilita (se descarga)
        getLogger().info("El plugin MediterraneaRPG se ha deshabilitado.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BaseCommand baseCommand = CommandRegistry.getCommand(command.getName());
        if (baseCommand != null) {
            return baseCommand.execute(sender, command, label, args);
        }
        return false;
    }
}