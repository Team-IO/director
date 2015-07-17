package net.teamio.director.cut;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.Vec3;

public class Keyframe {
	public Vec3 position;
	public float yaw;
	public float pitch;
	
	public Keyframe(EntityClientPlayerMP player) {
		position = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
		yaw = player.rotationYaw;
		pitch = player.rotationPitch;
	}
}
