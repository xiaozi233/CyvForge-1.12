package net.cyvforge.keybinding;

import net.cyvforge.command.mpk.CommandSetlb;
import net.cyvforge.util.defaults.CyvKeybinding;
import org.lwjgl.input.Keyboard;

public class KeybindingSetlbZ extends CyvKeybinding {
    public KeybindingSetlbZ() {
        super("key.setlbZ.desc", Keyboard.KEY_Z);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandSetlb.run(new String[]{"z", "target"});
        }
    }
}