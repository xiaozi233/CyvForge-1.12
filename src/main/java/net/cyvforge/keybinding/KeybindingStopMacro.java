package net.cyvforge.keybinding;

import net.cyvforge.command.mpk.CommandMacro;
import net.cyvforge.util.defaults.CyvKeybinding;
import org.lwjgl.input.Keyboard;

public class KeybindingStopMacro extends CyvKeybinding {
    public KeybindingStopMacro() {
        super("key.stopmacro.desc", Keyboard.KEY_NONE);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandMacro.macroRunning = 1;
        }
    }
}
