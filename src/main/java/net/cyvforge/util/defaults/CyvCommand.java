package net.cyvforge.util.defaults;

import net.minecraft.command.ICommandSender;

import java.util.ArrayList;

/**Base subcommand of /cyv.*/
public class CyvCommand {
    public final String name;
    public String usage = "none";
    public String helpString = "WIP";

    public ArrayList<CyvCommand> subCommands = new ArrayList<CyvCommand>();
    public CyvCommand parent; //will be null if this is not a subcommand
    public ArrayList<String> aliases = new ArrayList<String>();
    public boolean hasArgs = false;

    public CyvCommand(String name) {
        this.name = name;
    }

    /**run the command*/
    public void run(ICommandSender sender, String[] args) {

    }

    public String getDetailedHelp() {
        return helpString;
    }

    public java.util.List<String> getTabCompletions(String[] args) {
        return new java.util.ArrayList<>();
    }
}
