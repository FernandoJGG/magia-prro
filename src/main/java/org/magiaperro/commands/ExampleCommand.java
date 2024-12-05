package org.magiaperro.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineEntity;
import org.magiaperro.machines.base.MachineID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ExampleCommand extends BaseCommand {

    public ExampleCommand() {
        super("example", "Este es un comando de ejemplo", "/example");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        Entity chicken = player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
        chicken.customName(Component.text("El posho que hornea")
        		.decoration(TextDecoration.ITALIC, true)
                .color(NamedTextColor.YELLOW)
                .decoration(TextDecoration.BOLD, false));
        Machine machine = Machine.fromId(MachineID.AlloyFurnace);
        machine.instantiateMachine(new MachineEntity(chicken));
        
        return true;
    }
}