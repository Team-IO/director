package net.teamio.director.cut;


public class Keyframe {
	public double posX;
	public double posY;
	public double posZ;
	
	public float yaw;
	public float pitch;
	
	public Keyframe() {
		
	}

	@Override
	public String toString() {
		return "Keyframe [(" + posX + ", " + posY + ", " + posZ
				+ ") (" + yaw + ", " + pitch + ")]";
	}
	
	
}
