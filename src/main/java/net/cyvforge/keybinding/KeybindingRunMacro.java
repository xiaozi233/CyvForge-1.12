package net.cyvforge.keybinding;

import net.cyvforge.command.mpk.CommandMacro;
import net.cyvforge.util.defaults.CyvKeybinding;
import org.lwjgl.input.Keyboard;

public class KeybindingRunMacro extends CyvKeybinding {
    public KeybindingRunMacro() {
        super("key.runmacro.desc", Keyboard.KEY_V);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandMacro.runMacro(null);
        }
    }
}
