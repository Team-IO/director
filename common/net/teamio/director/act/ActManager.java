package net.teamio.director.act;

import javax.vecmath.Vector3d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.Vec3;
import net.teamio.director.cut.Keyframe;
import net.teamio.director.cut.Scene;

public class ActManager implements IMinecraftAdapter {
	public static final ActManager instance = new ActManager();
	
	private ActManager() {
		// Singleton
	}
	
	public Scene playingScene;
	
	public boolean isPlaying = false;
	
	public float tick = 0;
	public float unprocessedTick = 0;

	int currentKeyframeID = -1;
	public Keyframe currentKeyframe;
	public Keyframe previousKeyframe;
	
	private double playerBeginPositionX;
	private double playerBeginPositionY;
	private double playerBeginPositionZ;
	
	@Override
	public void replay(Scene scene) {
		isPlaying = true;
		playingScene = scene;
		tick = 0;
		unprocessedTick = 0;
		currentKeyframeID = -1;
		currentKeyframe = null;
		previousKeyframe = null;

		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		
		if(player == null) {
			isPlaying = false;
			return;
		}
		
		playerBeginPositionX = player.posX;
		playerBeginPositionY = player.posY;
		playerBeginPositionZ = player.posZ;
		
	}
	
	private void nextKeyframe() {
		currentKeyframeID++;
		if(currentKeyframeID >= playingScene.movement.size()) {
			isPlaying = false;
		} else {
			previousKeyframe = currentKeyframe;
			currentKeyframe = playingScene.movement.get(currentKeyframeID);
		}
	}
	
	public void tick() {
		if(!isPlaying) {
			return;
		}
		tick++;
		unprocessedTick++;
		
		if(currentKeyframe == null) {
			nextKeyframe();
		}
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		
		if(player == null) {
			isPlaying = false;
			return;
//			Mouse.setGrabbed(false);
//			Mouse.setGrabbed(true);
		}
		
		checkFlying();
		
		if(player.capabilities.isFlying) {
			

			
			switch(currentKeyframe.interpolationMovement) {
			case Teleport:
				if(unprocessedTick >= currentKeyframe.time) {
					player.setPositionAndRotation(
							currentKeyframe.posX,
							currentKeyframe.posY,
							currentKeyframe.posZ,
	                        currentKeyframe.yaw,
							currentKeyframe.pitch);
					//TODO: respect rotation changing!
					unprocessedTick -= currentKeyframe.time;
					nextKeyframe();
				}
				break;
			case Linear:

				handleLinearMovement(player);
				//TODO: Rotation
				
				break;
			case Smooth:
				handleSmoothMovement(player);
				//TODO: Rotation
				
				break;
			}
			
			
//			float factor = (tick % 30) / 30f;
//			
//			double x = (currentKeyframe.posX - lastKeyframe.posX) * 1/30f;// + lastKeyframe.position.xCoord;
//			double y = (currentKeyframe.posY - lastKeyframe.posY) * 1/30f;// + lastKeyframe.position.yCoord;
//			double z = (currentKeyframe.posZ - lastKeyframe.posZ) * 1/30f;// + lastKeyframe.position.zCoord;
//			
//			float yaw = 	(currentKeyframe.yaw - lastKeyframe.yaw) * 1/30f;
//			float pitch = 	(currentKeyframe.pitch - lastKeyframe.pitch) * 1/30f;
			
//				 player.oldPosX = player.posX;
//				 player.oldPosY = player.posY;
//				 player.oldPosZ = player.posZ;
//
//				 player.oldRotationYaw = player.rotationYaw;
//				 player.oldRotationPitch = player.rotationPitch;
//			
			
//			if(frameId == 0) {
//				player.setPositionAndRotation(
//						currentKeyframe.posX,
//						currentKeyframe.posY,
//						currentKeyframe.posZ,
//                        currentKeyframe.yaw,
//						currentKeyframe.pitch);
//			}
//			//else {
//				player.motionX = x;
//				player.motionY = y;
//				player.motionZ = z;
//				
//				player.rotationYaw += yaw;
//				player.rotationPitch += pitch;
//				player.setPositionAndRotation2(
//						x, y, z,
//						yaw, pitch, 10);
//			}
			//player.sendMotionUpdates();
		}
	}

	private void handleLinearMovement(EntityClientPlayerMP player) {
		float factor = unprocessedTick / currentKeyframe.time;
		if(factor > 1) {
			unprocessedTick -= currentKeyframe.time;
			nextKeyframe();
		} else {
			//TODO: player position -> store before launch
			double wasX = previousKeyframe == null ? playerBeginPositionX : previousKeyframe.posX;
			double wasY = previousKeyframe == null ? playerBeginPositionY : previousKeyframe.posY;
			double wasZ = previousKeyframe == null ? playerBeginPositionZ : previousKeyframe.posZ;
			double shouldBeX = (currentKeyframe.posX - wasX) * factor + wasX;
			double shouldBeY = (currentKeyframe.posY - wasY) * factor + wasY;
			double shouldBeZ = (currentKeyframe.posZ - wasZ) * factor + wasZ;
			double requiredOffsetX = shouldBeX - player.posX;
			double requiredOffsetY = shouldBeY - player.posY;
			double requiredOffsetZ = shouldBeZ - player.posZ;
			
			player.motionX = requiredOffsetX;
			player.motionY = requiredOffsetY;
			player.motionZ = requiredOffsetZ;
		}
	}
	
	private void handleSmoothMovement(EntityClientPlayerMP player) {
		//TODO: player position -> store before launch
		double requiredOffsetX = currentKeyframe.posX - player.posX;
		double requiredOffsetY = currentKeyframe.posY - player.posY;
		double requiredOffsetZ = currentKeyframe.posZ - player.posZ;
		
		Vector3d requiredOffset = new Vector3d(requiredOffsetX, requiredOffsetY, requiredOffsetZ);
		double offsetLength = requiredOffset.length();
		
		if(offsetLength < 0.5) {
			unprocessedTick = 0;
			nextKeyframe();
		}
		
		if(offsetLength > playingScene.movementSpeed) {
			requiredOffset.scale(playingScene.movementSpeed / offsetLength);
		}
		Vector3d motion = new Vector3d(player.motionX, player.motionY, player.motionZ);
		
		requiredOffset.sub(motion);
		offsetLength = requiredOffset.length();
		if(offsetLength > playingScene.acceletaion) {
			requiredOffset.scale(playingScene.acceletaion / offsetLength);
		}

		player.motionX += requiredOffset.x;
		player.motionY += requiredOffset.y;
		player.motionZ += requiredOffset.z;
		
	}
	
	private void checkFlying() {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		if(!player.capabilities.isFlying && player.capabilities.allowFlying) {
			player.capabilities.isFlying = true;
		}
	}
	
	@Override
	public Keyframe recordKeyframe() {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc != null && mc.thePlayer != null) {
			Keyframe kf = new Keyframe();
			
			EntityClientPlayerMP player = mc.thePlayer;
			
			kf.posX  = player.posX;
			kf.posY  = player.posY;
			kf.posZ  = player.posZ;
			kf.yaw   = player.rotationYaw;
			kf.pitch = player.rotationPitch;
			
			return kf;
		} else {
			return null;
		}
	}

}
