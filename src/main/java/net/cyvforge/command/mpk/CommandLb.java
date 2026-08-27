package net.cyvforge.command.mpk;

import net.cyvforge.CyvForge;
import net.cyvforge.event.events.GuiHandler;
import net.cyvforge.event.events.ParkourTickListener;
import net.cyvforge.gui.GuiLb;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandLb extends CyvCommand {
    public CommandLb() {
        super("lb");
        this.helpString = "Open GUI for modifying landing block settings.";

        this.aliases.add("landingblock");
        this.aliases.add("landing");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if (ParkourTickListener.landingBlock != null) GuiHandler.setScreen(new GuiLb(ParkourTickListener.landingBlock));
        else {
            CyvForge.sendChatMessage("You must set a landing block to use this command.");
        }
    }
}