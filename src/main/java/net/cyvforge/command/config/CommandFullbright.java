package net.cyvforge.command.config;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;

public class CommandFullbright extends CyvCommand {
    public CommandFullbright() {
        super("fullbright");
        this.hasArgs = false;
        this.helpString = "Toggles fullbright mode.";

        this.aliases.add("fb");
        this.aliases.add("bright");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        boolean newState = !CyvClientConfig.getBoolean("fullbright", false);
        CyvClientConfig.set("fullbright", newState);

        net.cyvforge.event.ConfigLoader.save(CyvForge.config, false);

        Minecraft.getMinecraft().gameSettings.gammaSetting = newState ? 1000f : 1f;

        Minecraft.getMinecraft().gameSettings.saveOptions();

        String status = newState ? "enabled" : "disabled";
        CyvForge.sendChatMessage("Fullbright has been " + status + ".");
    }
}