package net.cyvforge.keybinding.ChatMacro;

import java.util.ArrayList;
import java.util.List;

public class ChatMacro {
    public String commandOn = "";
    public String commandOff = "";
    public List<Integer> keyCodes = new ArrayList<>();
    public boolean isChain = false;
    public boolean isLink = false;
    public boolean isClient = false;
    public boolean chainState = false;

    public ChatMacro() {}
}