package net.teamio.director.act;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.teamio.director.cut.Keyframe;
import net.teamio.director.cut.Scene;

public class ActManager {
	public static Scene playingScene;
	
	public static boolean isPlaying = false;
	
	public static float tick = 0;
	
	public static void replay(Scene scene) {
		isPlaying = true;
		playingScene = scene;
		tick = 0;
	}
	
	public static void tick() {
		if(!isPlaying) {
			return;
		}
		tick += 1;
		// TODO: actually interpolate here, & respect settings in the keyframes
		int frameId = (int)(tick / 30);
		if(frameId >= playingScene.movement.size()) {
			isPlaying = false;
			return;
		}

		Keyframe currentKeyframe = playingScene.movement.get(frameId);
		Keyframe lastKeyframe;
		if(frameId > 0) {
			lastKeyframe = playingScene.movement.get(frameId - 1);
		} else {
			lastKeyframe = currentKeyframe;
		}
		
		System.out.println(currentKeyframe.position);
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		
		if(player == null) {
			isPlaying = false;
			return;
//			Mouse.setGrabbed(false);
//			Mouse.setGrabbed(true);
		}
		
		if(!player.capabilities.isFlying && player.capabilities.allowFlying) {
			player.capabilities.isFlying = true;
		}
		
		if(player.capabilities.isFlying) {
			float factor = (tick % 30) / 30f;
			
			double x = (currentKeyframe.position.xCoord - lastKeyframe.position.xCoord) * 1/30f;// + lastKeyframe.position.xCoord;
			double y = (currentKeyframe.position.yCoord - lastKeyframe.position.yCoord) * 1/30f;// + lastKeyframe.position.yCoord;
			double z = (currentKeyframe.position.zCoord - lastKeyframe.position.zCoord) * 1/30f;// + lastKeyframe.position.zCoord;
			
			float yaw = 	(currentKeyframe.yaw - lastKeyframe.yaw) * 1/30f;
			float pitch = 	(currentKeyframe.pitch - lastKeyframe.pitch) * 1/30f;
			
//				 player.oldPosX = player.posX;
//				 player.oldPosY = player.posY;
//				 player.oldPosZ = player.posZ;
//
//				 player.oldRotationYaw = player.rotationYaw;
//				 player.oldRotationPitch = player.rotationPitch;
//			
			
			if(frameId == 0) {
				player.setPositionAndRotation(
						currentKeyframe.position.xCoord,
						currentKeyframe.position.yCoord,
						currentKeyframe.position.zCoord,
                        currentKeyframe.yaw,
						currentKeyframe.pitch);
			}
			//else {
				player.motionX = x;
				player.motionY = y;
				player.motionZ = z;
				
				player.rotationYaw += yaw;
				player.rotationPitch += pitch;
//				player.setPositionAndRotation2(
//						x, y, z,
//						yaw, pitch, 10);
//			}
			//player.sendMotionUpdates();
		}
	}
}
