package net.cyvforge.keybinding;

import net.cyvforge.command.mpk.CommandSetmm;
import net.cyvforge.util.defaults.CyvKeybinding;
import org.lwjgl.input.Keyboard;

public class KeybindingSetmm extends CyvKeybinding {
    public KeybindingSetmm() {
        super("key.setmm.desc", Keyboard.KEY_M);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandSetmm.run(new String[]{"target"});
        }
    }
}