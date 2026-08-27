package net.cyvforge.util;


import java.util.HashMap;
import java.util.Map;

public class HUDPreset {
    public String presetName;
    public Map<String, String> positions = new HashMap<>();

    public HUDPreset(String name) {
        this.presetName = name;
    }
}