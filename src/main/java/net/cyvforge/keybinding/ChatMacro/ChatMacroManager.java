package net.cyvforge.keybinding.ChatMacro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMacroManager {
    public static List<ChatMacro> hotkeys = new ArrayList<>();
    private static final File file = new File(Minecraft.getMinecraft().gameDir, "config/cyvforge/hotkeys.json");

    public static void load() {
        if (!file.exists()) {
            hotkeys = new java.util.ArrayList<>();
            return;
        }
        try (Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            java.util.List<ChatMacro> loaded = new com.google.gson.Gson().fromJson(reader, new com.google.gson.reflect.TypeToken<java.util.List<ChatMacro>>(){}.getType());
            if (loaded != null) {
                hotkeys = loaded;
                for (ChatMacro hk : hotkeys) {
                    if (hk.keyCodes == null) hk.keyCodes = new java.util.ArrayList<>();
                }
            } else {
                hotkeys = new java.util.ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            hotkeys = new java.util.ArrayList<>();
        }
    }

    public static void save() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
            new GsonBuilder().setPrettyPrinting().create().toJson(hotkeys, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }
}