package net.cyvforge.command.calculations;

import net.cyvforge.CyvForge;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;

import java.text.DecimalFormat;

public class CommandSetSensitivity extends CyvCommand {
    public CommandSetSensitivity() {
        super("setsensitivity");
        this.hasArgs = true;
        this.usage = "<value from 0.0 to 1.0>";
        this.helpString = "Sets your current sensitivity. (100% = 0.5, ranges from 0.0 to 1.0)";

        this.aliases.add("ss");
        this.aliases.add("setsens");

    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        try {
            double sensitivity = Double.parseDouble(args[0]);

            //check
            if (sensitivity > 1 || sensitivity < 0) {
                CyvForge.sendChatMessage("Sensitivity cannot be greater than 1 (200%) or less than 0.");
                return;

            }

            Minecraft.getMinecraft().gameSettings.mouseSensitivity = (float) sensitivity;
            Minecraft.getMinecraft().gameSettings.saveOptions();

            double percentage = 200 * sensitivity;
            DecimalFormat df = CyvForge.df;

            CyvForge.sendChatMessage("Sensitivity set to " + df.format(percentage) + "%");


        } catch (Exception e) {
            CyvForge.sendChatMessage("Please input a valid sensitivity constant (100% = 0.5)");

        }
    }
}
