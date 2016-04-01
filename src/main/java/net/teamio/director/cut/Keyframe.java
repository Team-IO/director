package net.teamio.director.cut;


public class Keyframe {
	public double posX;
	public double posY;
	public double posZ;
	
	public float yaw;
	public float pitch;
	
	public Interpolation interpolationMovement;
	public Timing timingMovement;
	public Interpolation interpolationRotation;
	public Timing timingRotation;
	public int time;
	
	public static enum Interpolation {
		Teleport,
		Linear,
		Smooth
	}
	
	public static enum Timing {
		Fixed,
		Dynamic
	}
	
	public Keyframe() {
		// 5 seconds
		this.time = 100;
		
		this.interpolationMovement = Interpolation.Linear;
		this.interpolationRotation = Interpolation.Linear;
		this.timingMovement = Timing.Dynamic;
		this.timingRotation = Timing.Fixed;
	}

	public Keyframe(Keyframe other) {
		this.posX = other.posX;
		this.posY = other.posY;
		this.posZ = other.posZ;
		
		this.yaw = other.yaw;
		this.pitch = other.pitch;
		
		this.time = other.time;
		
		this.interpolationMovement = other.interpolationMovement;
		this.interpolationRotation = other.interpolationRotation;
		this.timingMovement = other.timingMovement;
		this.timingRotation = other.timingRotation;
	}

	@Override
	public String toString() {
		return "Keyframe [(" + posX + ", " + posY + ", " + posZ
				+ ") (" + yaw + ", " + pitch + ")]";
	}
	
	
}
