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
		
			
			player.setPositionAndRotation(
					currentKeyframe.position.xCoord, currentKeyframe.position.yCoord, currentKeyframe.position.zCoord,
					currentKeyframe.yaw, currentKeyframe.pitch);
			//player.sendMotionUpdates();
		}
	}
}
