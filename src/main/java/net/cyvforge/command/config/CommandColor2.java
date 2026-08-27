package net.cyvforge.command.config;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientColorHelper;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;
import java.util.List;

public class CommandColor2 extends CyvCommand {
    public CommandColor2() {
        super("color2");
        this.hasArgs = true;
        this.usage = "[color]";
        this.helpString = "Sets color2 for display and chat.";
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            CyvForge.sendChatMessage("Invalid color. For a list of colors use /cyv colors");
            return;
        }

        if (CyvClientColorHelper.setColor2(args[0].toLowerCase())) {
            CyvForge.sendChatMessage("Successfully changed color 2.");
        } else {
            CyvForge.sendChatMessage("Invalid color. For a list of colors use /cyv colors");
        }
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        return java.util.Arrays.asList(net.cyvforge.config.CyvClientColorHelper.colorStrings);
    }
}
