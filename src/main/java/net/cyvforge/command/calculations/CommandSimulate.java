package net.cyvforge.command.calculations;

import net.cyvforge.event.events.GuiHandler;
import net.cyvforge.gui.GuiSimulate;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandSimulate extends CyvCommand {
    public CommandSimulate() {
        super("simulate");
        this.helpString = "Simulates movement given a string of functions.";

        this.aliases.add("sim");
        this.aliases.add("s");
        this.aliases.add("%");

    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        GuiHandler.setScreen(new GuiSimulate());
    }
}
