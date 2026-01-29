package net.cyvforge.keybinding;

import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.util.defaults.CyvKeybinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class KeybindingTogglesprint extends CyvKeybinding {
    public static SprintMode currentMode = SprintMode.DISABLED;

    public KeybindingTogglesprint() {
        super("key.togglesprint.desc", Keyboard.KEY_NONE);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isSprintToggled(){
        return currentMode != SprintMode.DISABLED;
    }

    @Override
    public void onTickStart(boolean isPressed) {
        if (currentMode == SprintMode.FMM) {
            if (Minecraft.getMinecraft().currentScreen == null) {
                GameSettings settings = Minecraft.getMinecraft().gameSettings;
                KeyBinding.setKeyBindState(settings.keyBindSprint.getKeyCode(), true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (currentMode == SprintMode.JAM) {
            if (event.phase == TickEvent.Phase.START && event.player == Minecraft.getMinecraft().player) {
                if (Minecraft.getMinecraft().currentScreen == null) {
                    GameSettings settings = Minecraft.getMinecraft().gameSettings;
                    KeyBinding.setKeyBindState(settings.keyBindSprint.getKeyCode(), true);
                }
            }
        }
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            switch (currentMode) {
                case JAM: case FMM:
                    currentMode = SprintMode.DISABLED;
                    GameSettings settings = Minecraft.getMinecraft().gameSettings;
                    KeyBinding.setKeyBindState(settings.keyBindSprint.getKeyCode(), false);
                    break;
                case DISABLED:
                    currentMode = CyvClientConfig.getBoolean("invfmm", true) ? SprintMode.FMM : SprintMode.JAM;
                    break;
            }
        }
    }

    public enum SprintMode {
        DISABLED,
        JAM,
        FMM
    }
}