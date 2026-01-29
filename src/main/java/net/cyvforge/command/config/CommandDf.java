package net.cyvforge.command.config;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

import java.text.DecimalFormatSymbols;

public class CommandDf extends CyvCommand {
    public CommandDf() {
        super("df");
        this.hasArgs = true;
        this.usage = "[precision]";
        this.helpString = "Set the decimal precision of the mod from 0-16.";

        aliases.add("decimalprecision");
        aliases.add("precision");
        aliases.add("decimals");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        try {
            int df = Integer.parseInt(args[0]);

            if (df >= 0 && df <= 16) {
                CyvClientConfig.set("df",df);
                CyvForge.df.setMaximumFractionDigits(CyvClientConfig.getInt("df",df));

                if (CyvClientConfig.getBoolean("trimZeroes", true)) CyvForge.df.setMinimumFractionDigits(0);
                else CyvForge.df.setMinimumFractionDigits(CyvClientConfig.getInt("df",5));

                DecimalFormatSymbols s = new DecimalFormatSymbols();
                s.setDecimalSeparator('.');
                CyvForge.df.setDecimalFormatSymbols(s);
                CyvForge.sendChatMessage("Decimal precision set to " + df + ".");
            } else {
                CyvForge.sendChatMessage("Please enter a valid number from 0-16.");
            }

        } catch (Exception e) {
            CyvForge.sendChatMessage("Please enter a valid number from 0-16.");
        }
    }
}
