package net.cyvforge.command;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.command.ICommandSender;

public class CommandInertia extends CyvCommand {
    public CommandInertia() {
        super("inertia");
        this.hasArgs = true;
        this.usage = "[subcategory]";
        this.helpString = "Configure inertia listener. Use /cyv help inertia for more info.";
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        boolean enabled = CyvClientConfig.getBoolean("inertiaEnabled", false);
        String subcategory = "toggle";

        //add a checker for no args to not toggle on/off inertia

        if (args.length != 0) {
            subcategory = args[0].toLowerCase();
        }

        if (subcategory.equals("toggle")) {
            if (enabled) {
                CyvClientConfig.set("inertiaEnabled", false);
                CyvForge.sendChatMessage("Inertia listener toggled off.");
            } else {
                CyvClientConfig.set("inertiaEnabled", true);
                CyvForge.sendChatMessage("Inertia listener toggled on.");
            }

        } else if (subcategory.equals("min") || subcategory.equals("minimum")) {
            try {
                CyvClientConfig.set("inertiaMin", Double.parseDouble(args[1]));
                CyvForge.sendChatMessage("Inertia minimum speed threshold set to " + args[1] + ".");
            } catch (Exception e) {
                CyvForge.sendChatMessage("Invalid minimum speed.");
            }

        } else if (subcategory.equals("max") || subcategory.equals("maximum")) {
            try {
                CyvClientConfig.set("inertiaMax", Double.parseDouble(args[1]));
                CyvForge.sendChatMessage("Inertia maximum speed threshold set to " + args[1] + ".");
            } catch (Exception e) {
                CyvForge.sendChatMessage("Invalid maximum speed.");
            }

        } else if (subcategory.equals("mode") || subcategory.equals("axis")) {
            if (args[1].equalsIgnoreCase("x")) {
                CyvClientConfig.set("inertiaAxis", 'x');
                CyvForge.sendChatMessage("Inertia axis set to x.");
            } else if (args[1].equalsIgnoreCase("z")) {
                CyvClientConfig.set("inertiaAxis", 'z');
                CyvForge.sendChatMessage("Inertia axis set to z.");
            } else {
                CyvForge.sendChatMessage("Invalid axis. Only x and z are allowed.");
            }

        } else if (subcategory.equals("tick") || subcategory.equals("t")) {
            try {
                if (Integer.parseInt(args[1]) > 12 || Integer.parseInt(args[1]) < 1) {
                    CyvForge.sendChatMessage("Inertia tick must be from 1 to 12.");
                } else {
                    CyvClientConfig.set("inertiaTick", Integer.parseInt(args[1]));
                    CyvForge.sendChatMessage("Inertia tick set to " + args[1] + ".");
                }
            } catch (Exception e) {
                CyvForge.sendChatMessage("Invalid tick.");
            }

        } else if (subcategory.equals("ground") || subcategory.equals("groundmode") || subcategory.equals("groundtype")) {
            if (args[1].equalsIgnoreCase("normal")) {
                CyvClientConfig.set("inertiaGroundType", "normal");
                CyvForge.sendChatMessage("Ground type set to normal.");
            } else if (args[1].equalsIgnoreCase("ice")) {
                CyvClientConfig.set("inertiaGroundType", "ice");
                CyvForge.sendChatMessage("Ground type set to ice.");
            } else if (args[1].equalsIgnoreCase("slime")) {
                CyvClientConfig.set("inertiaGroundType", "slime");
                CyvForge.sendChatMessage("Ground type set to slime.");
            } else {
                CyvForge.sendChatMessage("Invalid ground type. Only normal, ice, and slime are allowed.");
            }

        } else {
            CyvForge.sendChatMessage("Unrecognized argument.");
        }
    }

    @Override
    public String getDetailedHelp() {
        return "Toggle on/off or modify inertia listener settings. Subcategories:"
                + "\n[toggle]: Toggle listener on or off."
                + "\n[min/max]: Set minimum and maximum speed bounds for a chat message."
                + "\n[mode]: Set listener to x or z axis."
                + "\n[tick]: Set airtick to check for inertia."
                + "\n[ground]: Change ground type between ice, slime, or normal.";
    }

}
