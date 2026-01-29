package net.cyvforge.command.checkpoint;

import net.cyvforge.CyvForge;
import net.cyvforge.util.ChatFormattingString;
import net.cyvforge.util.defaults.CyvCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;

import java.text.DecimalFormat;
import java.util.Arrays;

public class CommandCheckpoint extends CyvCommand {
    public CommandCheckpoint() {
        super("checkpoint");
        this.hasArgs = true;
        this.usage = "[x] [y] [z] [yaw] [pitch] [optional custom name]";
        this.helpString = "Convert an item into a checkpoint item with custom coords binded to it. " +
                "If no coordinate provided, defaults to your current position.";

        this.aliases.add("cp");
        this.aliases.add("setcheckpoint");
        this.aliases.add("setcp");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if (Minecraft.getMinecraft().getIntegratedServer() == null) {
            CyvForge.sendChatMessage("Checkpoint teleporter items cannot be created in multiplayer.");
            return;
        }

        ItemStack item;
        try {
            item = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
            //check if item is valid
            if (isBannedItem(item.getItem())) { //subclass of block?
                CyvForge.sendChatMessage("This item cannot be converted to a checkpoint item.");
                return;
            }
        } catch (Exception e) {
            CyvForge.sendChatMessage("Hold an item to convert to a checkpoint item.");
            return;
        }

        try {
            NBTTagCompound nbt = new NBTTagCompound();

            if (args.length == 0) {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                DecimalFormat df = CyvForge.df;

                nbt.setDouble("coordX", Double.parseDouble(df.format(player.posX)));
                nbt.setDouble("coordY", Double.parseDouble(df.format(player.posY)));
                nbt.setDouble("coordZ", Double.parseDouble(df.format(player.posZ)));
                nbt.setFloat("coordYaw", Float.parseFloat(df.format(player.rotationYaw)));
                nbt.setFloat("coordPitch", Float.parseFloat(df.format(player.rotationPitch)));

                NBTTagList lore = new NBTTagList();
                lore.appendTag(new NBTTagString(ChatFormattingString.YELLOW+"Checkpoint Teleport Item"));

                lore.appendTag(new NBTTagString(ChatFormattingString.YELLOW+"Coords: "
                        +ChatFormattingString.AQUA+df.format(player.posX)+" / "+df.format(player.posY)+" / "
                        +df.format(player.posZ)+" ("+df.format(player.rotationYaw)+" / "+df.format(player.rotationPitch)+")"));

                NBTTagCompound display = new NBTTagCompound();
                display.setTag("Lore", lore);

                nbt.setTag("display", display);

            } else {

                nbt.setDouble("coordX", Double.parseDouble(args[0]));
                nbt.setDouble("coordY", Double.parseDouble(args[1]));
                nbt.setDouble("coordZ", Double.parseDouble(args[2]));
                nbt.setFloat("coordYaw", Float.parseFloat(args[3]));
                nbt.setFloat("coordPitch", Float.parseFloat(args[4]));

                NBTTagList lore = new NBTTagList();
                lore.appendTag(new NBTTagString(ChatFormattingString.YELLOW+"Checkpoint Teleport Item"));

                lore.appendTag(new NBTTagString(ChatFormattingString.YELLOW+"Coords: "
                        +ChatFormattingString.AQUA+args[0]+" / "+args[1]+" / "+args[2]+" ("+args[3]+" / "+args[4]+")"));

                NBTTagCompound display = new NBTTagCompound();
                display.setTag("Lore", lore);

                nbt.setTag("display", display);

            }

            nbt.setBoolean("isCP", true);

            EntityPlayerMP player = CommandBase.getPlayer(Minecraft.getMinecraft().getIntegratedServer(), Minecraft.getMinecraft().player, Minecraft.getMinecraft().player.getName());

            player.getHeldItem(EnumHand.MAIN_HAND).setTagCompound(nbt);

            if (args.length > 5) {
                String[] customName = Arrays.copyOfRange(args, 5, args.length);
                String joinedCustomName = String.join(" ", customName);

                player.getHeldItem(EnumHand.MAIN_HAND).setStackDisplayName(ChatFormattingString.RED+joinedCustomName);


            } else {
                player.getHeldItem(EnumHand.MAIN_HAND).setStackDisplayName(ChatFormattingString.RED+"Checkpoint Item");

            }
        } catch (Exception e) {
            CyvForge.sendChatMessage("Please enter valid coords.");
            return;
        }

        try {
            CyvForge.sendChatMessage("Checkpoint Teleporter coords of " + item.getDisplayName() + "\247f set to " + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + ".");
        } catch (Exception e) {
            CyvForge.sendChatMessage("Checkpoint Teleporter coords of " + item.getDisplayName() + "\247f set.");
        }
    }

    public boolean isBannedItem(Item item) {
        boolean isBanned = item instanceof ItemArmor;
        isBanned = isBanned || item instanceof ItemArmorStand;
        isBanned = isBanned || item instanceof ItemBed;
        isBanned = isBanned || item instanceof ItemBlock;
        isBanned = isBanned || item instanceof ItemBoat;
        isBanned = isBanned || item instanceof ItemBow;
        isBanned = isBanned || item instanceof ItemBucket;
        isBanned = isBanned || item instanceof ItemBucketMilk;
        isBanned = isBanned || item instanceof ItemDoor;
        isBanned = isBanned || item instanceof ItemWritableBook;
        isBanned = isBanned || item instanceof ItemEgg;
        isBanned = isBanned || item instanceof ItemEnderEye;
        isBanned = isBanned || item instanceof ItemEnderPearl;
        isBanned = isBanned || item instanceof ItemExpBottle;
        isBanned = isBanned || item instanceof ItemFireball;
        isBanned = isBanned || item instanceof ItemFirework;
        isBanned = isBanned || item instanceof ItemFireworkCharge;
        isBanned = isBanned || item instanceof ItemFishingRod;
        isBanned = isBanned || item instanceof ItemFlintAndSteel;
        isBanned = isBanned || item instanceof ItemFood;
        isBanned = isBanned || item instanceof ItemGlassBottle;
        isBanned = isBanned || item instanceof ItemHangingEntity;
        isBanned = isBanned || item instanceof ItemHoe;
        isBanned = isBanned || item instanceof ItemLead;
        isBanned = isBanned || item instanceof ItemMapBase;
        isBanned = isBanned || item instanceof ItemMinecart;
        isBanned = isBanned || item instanceof ItemMonsterPlacer;
        isBanned = isBanned || item instanceof ItemPotion;
        isBanned = isBanned || item instanceof ItemRecord;
        isBanned = isBanned || item instanceof ItemRedstone;
        isBanned = isBanned || item instanceof ItemSaddle;
        isBanned = isBanned || item instanceof ItemSeeds;
        isBanned = isBanned || item instanceof ItemSign;
        isBanned = isBanned || item instanceof ItemSkull;
        isBanned = isBanned || item instanceof ItemSnowball;
        isBanned = isBanned || item instanceof ItemSword;
        isBanned = isBanned || item instanceof ItemWritableBook;

        return isBanned;

    }
}
