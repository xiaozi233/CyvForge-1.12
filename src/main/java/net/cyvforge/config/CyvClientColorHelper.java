package net.cyvforge.config;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class CyvClientColorHelper {
    public static CyvClientColor color1;
    public static CyvClientColor color2;
    public static ArrayList<CyvClientColor> colors;
    public static String[] colorStrings;

    static {
        colors = new ArrayList<>();
        colors.add(new CyvClientColor("dark_red", ChatFormatting.DARK_RED));
        colors.add(new CyvClientColor("red", ChatFormatting.RED));
        colors.add(new CyvClientColor("gold", ChatFormatting.GOLD));
        colors.add(new CyvClientColor("yellow", ChatFormatting.YELLOW));
        colors.add(new CyvClientColor("dark_green", ChatFormatting.DARK_GREEN));
        colors.add(new CyvClientColor("green", ChatFormatting.GREEN));
        colors.add(new CyvClientColor("aqua", ChatFormatting.AQUA));
        colors.add(new CyvClientColor("dark_aqua", ChatFormatting.DARK_AQUA));
        colors.add(new CyvClientColor("dark_blue", ChatFormatting.DARK_BLUE));
        colors.add(new CyvClientColor("blue", ChatFormatting.BLUE));
        colors.add(new CyvClientColor("pink", ChatFormatting.LIGHT_PURPLE));
        colors.add(new CyvClientColor("purple", ChatFormatting.DARK_PURPLE));
        colors.add(new CyvClientColor("white", ChatFormatting.WHITE));
        colors.add(new CyvClientColor("gray", ChatFormatting.GRAY));
        colors.add(new CyvClientColor("dark_gray", ChatFormatting.DARK_GRAY));
        colors.add(new CyvClientColor("black", ChatFormatting.BLACK));

        colorStrings = colors.stream().map(c -> c.name).toArray(String[]::new);

        //aliases
        colors.add(new CyvClientColor("dred", ChatFormatting.DARK_RED));
        colors.add(new CyvClientColor("dgreen", ChatFormatting.DARK_GREEN));
        colors.add(new CyvClientColor("daqua", ChatFormatting.DARK_AQUA));
        colors.add(new CyvClientColor("dblue", ChatFormatting.DARK_BLUE));
        colors.add(new CyvClientColor("light_purple", ChatFormatting.LIGHT_PURPLE));
        colors.add(new CyvClientColor("lpurple", ChatFormatting.LIGHT_PURPLE));
        colors.add(new CyvClientColor("dark_purple", ChatFormatting.DARK_PURPLE));
        colors.add(new CyvClientColor("dpurple", ChatFormatting.DARK_PURPLE));
        colors.add(new CyvClientColor("dgray", ChatFormatting.DARK_GRAY));
    }

    private static String getOfficialName(CyvClientColor c) {
        for (int i = 0; i < 16; i++) {
            if (colors.get(i).formatting == c.formatting) {
                return colors.get(i).name;
            }
        }
        return c.name;
    }

    public static void checkColor(String c1, String c2) {
        color1 = colors.get(6);
        color2 = colors.get(12);

        for (CyvClientColor c : colors) {
            if (c.name.equalsIgnoreCase(c1)) color1 = c;
            if (c.name.equalsIgnoreCase(c2)) color2 = c;
        }

        CyvClientConfig.set("color1", getOfficialName(color1));
        CyvClientConfig.set("color2", getOfficialName(color2));
    }

    public static boolean setColor1(String s) {
        for (CyvClientColor c : colors) {
            if (c.name.equalsIgnoreCase(s)) {
                color1 = c;
                CyvClientConfig.set("color1", getOfficialName(c));
                return true;
            }
        }
        return false;
    }

    public static boolean setColor2(String s) {
        for (CyvClientColor c : colors) {
            if (c.name.equalsIgnoreCase(s)) {
                color2 = c;
                CyvClientConfig.set("color2", getOfficialName(c));
                return true;
            }
        }
        return false;
    }

    public static class CyvClientColor {
        public final String name;
        public final ChatFormatting formatting;

        CyvClientColor(String name, ChatFormatting formatting) {
            this.name = name;
            this.formatting = formatting;
        }

        public long getDrawColor() {
            int color = Minecraft.getMinecraft().fontRenderer.getColorCode(formatting.toString().charAt(1));
            return color | 0xFF000000;
        }

        public String getChatFormatting() {
            return formatting.toString();
        }

    }

}