package net.cyvforge.command.mpk;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.cyvforge.CyvForge;
import net.cyvforge.event.events.ParkourTickListener;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CommandGetCoords extends CyvCommand {
    public CommandGetCoords() {
        super("getcoords");
        this.hasArgs = true;
        this.usage = "/mpk getcoords [1-16]";
        this.helpString = "Copies current position/coordinates into your clipboard.\nCan display 1-16 decimal places. Defaults to client's df value if unspecified.";

        this.aliases.add("coord");
        this.aliases.add("coords");
        this.aliases.add("getcoord");
        this.aliases.add("getposition");
        this.aliases.add("position");
    }

    private static DecimalFormat createDecimalFormat(int decimalPlaces) {
        StringBuilder patternBuilder = new StringBuilder("#0.");
        for (int i = 0; i < decimalPlaces; i++) {
            patternBuilder.append('0');
        }

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat(patternBuilder.toString(), decimalFormatSymbols);
        decimalFormat.setGroupingUsed(false);

        return decimalFormat;
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) {
            return;
        }
        DecimalFormat df = CyvForge.df;
        if (args != null && args.length >= 1) {
            try {
                int dp = MathHelper.clamp(Integer.parseInt(args[0].trim()), 1, 16);
                df = createDecimalFormat(dp);
            } catch (Exception ignored) {
            }
        }
        String xs = df.format(ParkourTickListener.x);
        String ys = df.format(ParkourTickListener.y);
        String zs = df.format(ParkourTickListener.z);
        String yaws = df.format(ParkourTickListener.formatYaw(ParkourTickListener.f));
        String pitchs = df.format(ParkourTickListener.p);
        String toCopy = xs + " " + ys + " " + zs + " " + yaws + " " + pitchs;
        GuiScreen.setClipboardString(toCopy);
        String msg = ChatFormatting.GREEN + "Copied position to clipboard:\n"
                + ChatFormatting.YELLOW + "X: " + xs + "\n"
                + ChatFormatting.YELLOW + "Y: " + ys + "\n"
                + ChatFormatting.YELLOW + "Z: " + zs + "\n"
                + ChatFormatting.YELLOW + "Yaw: " + yaws + "\n"
                + ChatFormatting.YELLOW + "Pitch: " + pitchs + ChatFormatting.RESET;
        player.sendMessage(new TextComponentString(msg));
    }

}
