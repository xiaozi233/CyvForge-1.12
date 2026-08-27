package net.cyvforge.event.events;

import net.cyvforge.command.mpk.CommandMacro;
import net.cyvforge.config.CyvClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;

public class MacroRecorder {
    private float lastYaw = 0;
    private float lastPitch = 0;

    private float pendingYawDelta = 0;
    private float pendingPitchDelta = 0;

    public static ArrayList<ArrayList<String>> clipBuffer = new ArrayList<>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) return;
        GameSettings gs = mc.gameSettings;

        float currentYawDelta = mc.player.rotationYaw - lastYaw;
        float currentPitchDelta = mc.player.rotationPitch - lastPitch;

        ArrayList<String> tickData = new ArrayList<>();
        tickData.add(String.valueOf(gs.keyBindForward.isKeyDown()));
        tickData.add(String.valueOf(gs.keyBindLeft.isKeyDown()));
        tickData.add(String.valueOf(gs.keyBindBack.isKeyDown()));
        tickData.add(String.valueOf(gs.keyBindRight.isKeyDown()));
        tickData.add(String.valueOf(gs.keyBindJump.isKeyDown()));
        tickData.add(String.valueOf(gs.keyBindSprint.isKeyDown()));
        tickData.add(String.valueOf(gs.keyBindSneak.isKeyDown()));
        tickData.add(String.valueOf(gs.keyBindUseItem.isKeyDown()));
        tickData.add(String.valueOf(pendingYawDelta));
        tickData.add(String.valueOf(pendingPitchDelta));

        if (CommandMacro.isRecording) {
            CommandMacro.macro.add(tickData);
        }

        if (CyvClientConfig.getBoolean("macroClipEnabled", false)) {
            clipBuffer.add(tickData);

            int maxTicks = CyvClientConfig.getInt("macroClipTicks", 40);
            while (clipBuffer.size() > maxTicks && !clipBuffer.isEmpty()) {
                clipBuffer.remove(0);
            }
        } else if (!clipBuffer.isEmpty()) {
            clipBuffer.clear();
        }

        pendingYawDelta = currentYawDelta;
        pendingPitchDelta = currentPitchDelta;

        lastYaw = mc.player.rotationYaw;
        lastPitch = mc.player.rotationPitch;
    }
}