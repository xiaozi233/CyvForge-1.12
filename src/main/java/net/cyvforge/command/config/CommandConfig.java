package net.cyvforge.command.config;

import net.cyvforge.event.events.GuiHandler;
import net.cyvforge.gui.GuiModConfig;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandConfig extends CyvCommand {
    public CommandConfig() {
        super("config");
        this.helpString = "Opens the mod config GUI.";

        this.aliases.add("configuration");
        this.aliases.add("settings");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        GuiHandler.setScreen(new GuiModConfig(false));
    }
}
