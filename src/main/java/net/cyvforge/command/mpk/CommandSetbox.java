package net.cyvforge.command.mpk;

import net.cyvforge.CyvForge;
import net.cyvforge.event.events.ParkourTickListener;
import net.cyvforge.util.defaults.CyvCommand;
import net.cyvforge.util.parkour.LandingBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.AxisAlignedBB;

public class CommandSetbox extends CyvCommand {
    public CommandSetbox() {
        super("setbox");
        this.usage = "<x1> <x2> <y1> <y2> <z1> <z2>";
        this.helpString = "Creates landing zone with set dimensions.";
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        try {
            AxisAlignedBB box = getAxisAlignedBB(args);

            if (ParkourTickListener.landingBlock != null) {
                ParkourTickListener.landingBlock.bb = new AxisAlignedBB[]{box};
                CyvForge.sendChatMessage("Landing box changed.");

            } else {


                ParkourTickListener.landingBlock = new LandingBlock(box);
                CyvForge.sendChatMessage("Landing box set.");

            }
        } catch (Exception e) {
            e.printStackTrace();
            CyvForge.sendChatMessage("Invalid setbox syntax.");
        }
    }

    private static AxisAlignedBB getAxisAlignedBB(String[] args) {
        AxisAlignedBB player = Minecraft.getMinecraft().player.getEntityBoundingBox();

        double lengthX = Math.abs(player.maxX - player.minX);
        double lengthZ = Math.abs(player.maxZ - player.minZ);

        double x1 = Double.parseDouble(args[0]) + lengthX/2;
        double x2 = Double.parseDouble(args[1]) - lengthX/2;
        double y1 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);
        double z1 = Double.parseDouble(args[4]) + lengthZ/2;
        double z2 = Double.parseDouble(args[5]) - lengthZ/2;

        return new AxisAlignedBB(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }
}