package net.cyvforge.command.mpk;

import net.cyvforge.CyvForge;
import net.cyvforge.event.events.ParkourTickListener;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandSetcond extends CyvCommand {
    public CommandSetcond() {
        super("setcond");
        this.hasArgs = true;
        this.usage = "<x1> <x2> <z1> <z2>";
        this.helpString = "Set landing block detection zone.";
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        try {
            double x1 = Double.parseDouble(args[0]);
            double x2 = Double.parseDouble(args[1]);
            double z1 = Double.parseDouble(args[2]);
            double z2 = Double.parseDouble(args[3]);

            if (ParkourTickListener.landingBlock != null) {
                ParkourTickListener.landingBlock.adjustCond(x1, x2, z1, z2);
                CyvForge.sendChatMessage("Successfully set landing conditions.");
            } else {
                CyvForge.sendChatMessage("No target block exists.");
            }

        } catch (Exception e) {
            CyvForge.sendChatMessage("Please enter valid coordinates.");
        }
    }
}