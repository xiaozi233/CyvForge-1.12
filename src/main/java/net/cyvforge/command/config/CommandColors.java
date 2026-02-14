package net.cyvforge.command.config;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientColorHelper;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandColors extends CyvCommand {
    public CommandColors() {
        super("colors");
        this.helpString = "Get the list of colors usable for display and chat.";
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        StringBuilder str = new StringBuilder("List of colors usable:");
        for (CyvClientColorHelper.CyvClientColor c : CyvClientColorHelper.colors) {
            str.append("\n").append(c.getChatFormatting()).append(c.name);
        }

        CyvForge.sendChatMessage(str.toString());
    }
}
