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
        CyvForge.sendChatMessage("List of usable colors:");
        for (String colorName : CyvClientColorHelper.colorStrings) {
            for (CyvClientColorHelper.CyvClientColor c : CyvClientColorHelper.colors) {
                if (c.name.equals(colorName)) {
                    CyvForge.sendChatMessage(c.getChatFormatting() + c.name);
                    break;
                }
            }
        }
    }
}
