package net.cyvforge.event;

import net.cyvforge.CyvForge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MacroFileInit {
    public static final String NAME = "macro.json";
    public static final String PATH = ConfigLoader.PATH + "macros/";
    public static final String FILEPATH = PATH + NAME;

    public static File macroFile;

    //get next possible file name
    public static String getNextAvailableName(String prefix) {
        int i = 1;
        while (new File(PATH + prefix + i + ".json").exists()) {
            i++;
        }
        return prefix + i;
    }

    //initialises file
    public static void setupFile(String name) {
        File dir = new File(PATH);
        if (!dir.exists()) dir.mkdirs();
        dir = new File(PATH);
        macroFile = new File(PATH + name + ".json");

        try {
            if (!macroFile.exists()) {
                macroFile.createNewFile();
                FileWriter writer = new FileWriter(macroFile);
                writer.write("[]");
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //swaps or creates a new file
    public static void swapFile(String name) {
        File dir = new File(PATH);
        if (!dir.exists()) dir.mkdirs();
        dir = new File(PATH);
        macroFile = new File(PATH + name + ".json");

        try {
            if (!macroFile.exists()) {
                macroFile.createNewFile();
                FileWriter writer = new FileWriter(macroFile);
                writer.write("[]");
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
