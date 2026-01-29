package net.cyvforge.keybinding;

import net.cyvforge.command.mpk.CommandSetlb;
import net.cyvforge.util.defaults.CyvKeybinding;
import org.lwjgl.input.Keyboard;

public class KeybindingSetlbX extends CyvKeybinding {
    public KeybindingSetlbX() {
        super("key.setlbX.desc", Keyboard.KEY_X);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandSetlb.run(new String[]{"x", "target"});
        }
    }
}