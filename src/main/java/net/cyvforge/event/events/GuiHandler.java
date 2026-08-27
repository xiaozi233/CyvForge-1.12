package net.cyvforge.event.events;

import net.cyvforge.util.defaults.CyvGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

//This class is used to handle GUIs for CyvFabric
public class GuiHandler {
    public static int scrollBuffer = 0;
    private static GuiScreen screenAwaiting; //screen which will be shown next tick

    public static void setScreen(GuiScreen screen) {
        screenAwaiting = screen;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null) return; //don't run unless in-game
        if (event.phase != TickEvent.Phase.START) return;

        if (Minecraft.getMinecraft().currentScreen instanceof CyvGui) {
            int d = Mouse.getDWheel();
            if (d != 0) {
                scrollBuffer += d;
            }
        } else {
            scrollBuffer = 0;
        }

        if (screenAwaiting != null) {
            Minecraft.getMinecraft().displayGuiScreen(screenAwaiting); //set the screen
            screenAwaiting = null; //now that no screen is awaiting, clear it
        }
    }

}
