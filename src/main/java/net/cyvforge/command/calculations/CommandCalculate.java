package net.cyvforge.command.calculations;

import net.cyvforge.CyvForge;
import net.cyvforge.util.MathEvaluator;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

import static net.cyvforge.CyvForge.df;

public class CommandCalculate extends CyvCommand {
    public CommandCalculate() {
        super("calculate");
        this.hasArgs = true;
        this.usage = "<math expression>";
        this.helpString = "Parse a string for a math expression to evaluate.";

        this.aliases.add("calc");
        this.aliases.add("==");
        this.aliases.add("=");

    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            CyvForge.sendChatMessage("Please input something to calculate.");

        } else {
            try {
                String text = String.join(" ", args);
                CyvForge.sendChatMessage("Calculating " + text + "\n = "
                        + df.format(MathEvaluator.getInstance().eval(text)));
            } catch (Exception e) {
                CyvForge.sendChatMessage("Error while calculating:\n" + e.getMessage());
            }
        }
    }

}
