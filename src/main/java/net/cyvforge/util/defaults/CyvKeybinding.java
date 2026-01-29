package net.cyvforge.util.defaults;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

/**CyvFabric custom keybinding*/
public class CyvKeybinding extends KeyBinding {
    public static final Minecraft client = Minecraft.getMinecraft();
    public static final String KEY_CATEGORY_CYVFORGE = "key.cyvforge.category"; //cyvforge keybinding category

    public CyvKeybinding(String name, int keyCode) {
        super(name, keyCode, KEY_CATEGORY_CYVFORGE);
    }

    /**Called on start of tick*/
    public void onTickStart(boolean isPressed) {}

    /**Called on end of tick*/
    public void onTickEnd(boolean isPressed) {}

}
