package net.cyvforge.command.mpk;


import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandToggleInvFmm extends CyvCommand {
    public CommandToggleInvFmm() {
        super("invFmm");
        this.helpString = "Toggle Inv Fmm.";
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        CyvClientConfig.set("invFmm", !CyvClientConfig.getBoolean("invFmm", false));
        CyvForge.sendChatMessage("Successfully toggle inv fmm.");
    }
}
