package org.magiaperro.main;

import org.magiaperro.commands.CommandRegistry;
import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.entities.projectiles.ProjectileRegistry;
import org.magiaperro.items.*;
import org.magiaperro.recipes.VanillaRecipes;
import org.magiaperro.listeners.*;
import org.magiaperro.machines.MachineRegistry;
import org.magiaperro.spells.SpellRegistry;
import org.bukkit.Bukkit;
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
        MachineRegistry.register();
        ProjectileRegistry.register();
        VanillaRecipes.register();
        
        this.registerListeners();
    }
    
    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new OnPlayerInteractListener(), this);    
        getServer().getPluginManager().registerEvents(new BlockDisappearListener(), this);    
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new LoadListener(), this);
        getServer().getPluginManager().registerEvents(new EntitiesListener(), this);
    }

    @Override
    public void onDisable() {
        // Se ejecuta cuando el plugin se deshabilita (se descarga)
        getLogger().info("El plugin MediterraneaRPG se ha deshabilitado.");
        Bukkit.getScheduler().cancelTasks(instance);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BaseCommand baseCommand = CommandRegistry.getCommand(command.getName());
        if (baseCommand != null) {
            return baseCommand.execute(sender, command, label, args);
        }
        return false;
    }
    
    public static Long getWorldFullTime() {
    	return Bukkit.getWorld(Keys.MAIN_WORLD).getFullTime();
    }
}