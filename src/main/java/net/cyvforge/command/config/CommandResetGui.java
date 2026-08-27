package net.cyvforge.command.config;

import net.cyvforge.CyvForge;
import net.cyvforge.hud.HUDManager;
import net.cyvforge.hud.structure.DraggableHUDElement;
import net.cyvforge.hud.structure.ScreenPosition;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandResetGui extends CyvCommand {
    public CommandResetGui() {
        super("resetgui");
        this.hasArgs = false;
        this.helpString = "Resets all HUD labels to their default positions.";

        this.aliases.add("guireset");
        this.aliases.add("resetlabels");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        try {
            for (DraggableHUDElement renderer : HUDManager.registeredRenderers) {
                ScreenPosition defaultPos = renderer.getDefaultPosition();

                if (defaultPos != null) {
                    renderer.save(defaultPos);

                    renderer.load();
                }
            }

            CyvForge.sendChatMessage("All HUD labels have been reset to default positions.");

        } catch (Exception e) {
            e.printStackTrace();
            CyvForge.sendChatMessage("An error occurred while resetting the GUI.");
        }
    }
}