package org.magiaperro.commands;

import java.util.HashMap;
import java.util.Map;

import org.magiaperro.commands.base.BaseCommand;
import org.magiaperro.main.Main;

public class CommandRegistry {

    private final static Map<String, BaseCommand> commands = new HashMap<>();

	@SuppressWarnings("unused")
	public static void register() {
		// Registra los comandos
		ExampleCommand ejemploComando = new ExampleCommand();
		BackpackCommand backpack = new BackpackCommand();
		GiveCustomCommand giveCustom = new GiveCustomCommand();
	}
    
    public static void registerCommand(BaseCommand command) {
    	commands.put(command.name, command);
    	Main.instance.getCommand(command.name).setExecutor(Main.instance);
    }

    
    public static BaseCommand getCommand(String command) {
        return commands.get(command);
    }

}
