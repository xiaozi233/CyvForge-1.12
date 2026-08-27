package net.cyvforge.event.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SignCommandListener {

    @SubscribeEvent
    public void onSignInteract(PlayerInteractEvent.RightClickBlock event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.world == null || mc.player == null) return;

        Block block = mc.world.getBlockState(event.getPos()).getBlock();

        if (block instanceof BlockSign) {
            TileEntity te = mc.world.getTileEntity(event.getPos());

                if (te instanceof TileEntitySign) {
                    TileEntitySign sign = (TileEntitySign) te;

                    StringBuilder sb = new StringBuilder();
                    for (ITextComponent line : sign.signText) {
                        String lineText = line.getUnformattedText().trim();
                        if (!lineText.isEmpty()) {
                            if (sb.length() > 0) sb.append(" ");
                            sb.append(lineText);
                        }
                    }

                    String fullCommand = sb.toString().trim();

                    if (fullCommand.startsWith("/")) {
                        if (ClientCommandHandler.instance.executeCommand(mc.player, fullCommand) == 0) {
                            mc.player.sendChatMessage(fullCommand);
                        }

                        if (event.isCancelable()) {
                            event.setCanceled(true);
                        }
                    }
                }
        }
    }
}