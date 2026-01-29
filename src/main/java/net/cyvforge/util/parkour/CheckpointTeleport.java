package net.cyvforge.util.parkour;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.Set;

import static net.minecraft.command.CommandBase.getEntity;
import static net.minecraft.command.CommandBase.parseCoordinate;

/**Class for handling singleplayer checkpoint methods**/
public class CheckpointTeleport {
    public static void tp(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Entity entity = getEntity(server, sender, args[0]);

        if (entity.world != null) {
            int i = 4096;
            Vec3d vec3d = sender.getPositionVector();
            int j = 1;
            CommandBase.CoordinateArg commandbase$coordinatearg = parseCoordinate(vec3d.x, args[j++], true);
            CommandBase.CoordinateArg commandbase$coordinatearg1 = parseCoordinate(vec3d.y, args[j++], -4096, 4096, false);
            CommandBase.CoordinateArg commandbase$coordinatearg2 = parseCoordinate(vec3d.z, args[j++], true);
            Entity entity1 = sender.getCommandSenderEntity() == null ? entity : sender.getCommandSenderEntity();
            CommandBase.CoordinateArg commandbase$coordinatearg3 = parseCoordinate(args.length > j ? (double) entity1.rotationYaw : (double) entity.rotationYaw, args.length > j ? args[j] : "~", false);
            ++j;
            CommandBase.CoordinateArg commandbase$coordinatearg4 = parseCoordinate(args.length > j ? (double) entity1.rotationPitch : (double) entity.rotationPitch, args.length > j ? args[j] : "~", false);
            doTeleport(entity, commandbase$coordinatearg, commandbase$coordinatearg1, commandbase$coordinatearg2, commandbase$coordinatearg3, commandbase$coordinatearg4);

        }
    }

    private static void doTeleport(Entity teleportingEntity, CommandBase.CoordinateArg argX, CommandBase.CoordinateArg argY, CommandBase.CoordinateArg argZ, CommandBase.CoordinateArg argYaw, CommandBase.CoordinateArg argPitch)
    {
        if (teleportingEntity instanceof EntityPlayerMP)
        {
            Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);
            float f = (float)argYaw.getAmount();

            if (argYaw.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
            }
            else
            {
                f = MathHelper.wrapDegrees(f);
            }

            float f1 = (float)argPitch.getAmount();

            if (argPitch.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
            }
            else
            {
                f1 = MathHelper.wrapDegrees(f1);
            }

            teleportingEntity.dismountRidingEntity();
            ((EntityPlayerMP)teleportingEntity).connection.setPlayerLocation(argX.getResult(), argY.getResult(), argZ.getResult(), f, f1, set);
            teleportingEntity.setRotationYawHead(f);
        }
        else
        {
            float f2 = (float)MathHelper.wrapDegrees(argYaw.getResult());
            float f3 = (float)MathHelper.wrapDegrees(argPitch.getResult());
            f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
            teleportingEntity.setLocationAndAngles(argX.getResult(), argY.getResult(), argZ.getResult(), f2, f3);
            teleportingEntity.setRotationYawHead(f2);
        }

        if (!(teleportingEntity instanceof EntityLivingBase) || !((EntityLivingBase)teleportingEntity).isElytraFlying())
        {
            teleportingEntity.motionY = 0.0D;
            teleportingEntity.onGround = true;
        }
    }
}
