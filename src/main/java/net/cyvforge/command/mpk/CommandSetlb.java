package net.cyvforge.command.mpk;

import net.cyvforge.CyvForge;
import net.cyvforge.event.events.ParkourTickListener;
import net.cyvforge.util.defaults.CyvCommand;
import net.cyvforge.util.parkour.LandingAxis;
import net.cyvforge.util.parkour.LandingBlock;
import net.cyvforge.util.parkour.LandingMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;

public class CommandSetlb extends CyvCommand {
    public CommandSetlb() {
        super("setlb");
        hasArgs = true;
        usage = "[arguments]";
        this.helpString = "Set landing block";
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        run(args);
    }

    public static void run(String[] args) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;

        new Thread(() -> {
            LandingMode mode = LandingMode.landing;
            LandingAxis axis = LandingAxis.both;
            boolean box = false;
            boolean target = false;
            for (String s : args) {
                s = s.toLowerCase();
                if (s.equals("x")) axis = LandingAxis.x;
                else if (s.equals("z")) axis = LandingAxis.z;
                else if (s.equals("land") || s.equals("landing")) mode = LandingMode.landing;
                else if (s.equals("hit")) mode = LandingMode.hit;
                else if (s.equals("zneo") || s.equals("z-neo") || s.equals("neo") || s.equals("z_neo")) mode = LandingMode.z_neo;
                else if (s.equals("enter")) mode = LandingMode.enter;
                else if (s.equals("box")) box = true;
                else if (s.equals("target")) target = true;
            }

            if (target) {
                RayTraceResult hit = player.rayTrace(100, 0);
                if (hit.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                    try {
                        BlockPos pos = hit.getBlockPos();
                        List<AxisAlignedBB> list = CyvForge.getHitbox(pos, mc.world);

                        if (list != null && list.isEmpty()) {
                            CyvForge.sendChatMessage("Please look at a valid block.");
                        } else {
                            ParkourTickListener.landingBlock = new LandingBlock(pos, mode, axis, box);
                            CyvForge.sendChatMessage("Successfully set landing block.");
                        }
                    } catch (Exception e) {
                        CyvForge.sendChatMessage("Please look at a valid block.");
                    }
                } else {
                    CyvForge.sendChatMessage("Please look at a valid block.");
                }
            }
            else {
                if (player.onGround) {
                    BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
                    List<AxisAlignedBB> list = CyvForge.getHitbox(pos, mc.world);

                    if (list != null && list.isEmpty()) {
                        pos = pos.down();
                        list = CyvForge.getHitbox(pos, mc.world);
                    }

                    if (list != null && list.isEmpty()) {
                        CyvForge.sendChatMessage("Please stand on a valid block.");
                    } else {
                        ParkourTickListener.landingBlock = new LandingBlock(pos, mode, axis, box);
                        CyvForge.sendChatMessage("Successfully set landing block.");
                    }

                } else {
                    CyvForge.sendChatMessage("Please stand on a valid block.");
                }
            }
        }, "Set landing block").start();
    }
}