package net.teamio.director.cut;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

import net.teamio.director.cut.Keyframe.Interpolation;
import net.teamio.director.cut.Keyframe.Timing;

public class Scene {
	public float movementSpeed = 15;
	public float rotationSpeed = 2;
	public float acceletaion = 0.01f;
	public final List<Keyframe> movement;
	
	public Scene() {
		movement = new ArrayList<Keyframe>();
	}
	
	public void recalculateTimings() {
		if(movementSpeed <= 0) {
			movementSpeed = 1;
		}
		if(rotationSpeed <= 0) {
			rotationSpeed = 1;
		}
		if(acceletaion <= 0) {
			acceletaion = 0.01f;
		}
		
//		float moveX
		
		// For now, we ignore the first frame, as that will currently always be a teleport.
		for(int i = 1; i < movement.size(); i++) {
			Keyframe current = movement.get(i);
			Keyframe last = movement.get(i-1);
			
			if(current.interpolationMovement == Interpolation.Linear) {
				if(current.timingMovement == Timing.Dynamic) {
					// Calculate distance
					Vector3d dest = new Vector3d(current.posX, current.posY, current.posZ);
					Vector3d source = new Vector3d(last.posX, last.posY, last.posZ);
					dest.sub(source);
					double length = dest.length();
					
					// Use movement speed to determine time
					double time = length / movementSpeed;
					current.time = (int)Math.ceil((float)time);
				} else if(current.timingMovement == Timing.Fixed) {
					//TODO here: calculate breaking of movement speed constraints
				}
				//TODO here: calculate breaking of rotation speed constraints
				//TODO: Adjust timing if rotation is dynamic timed (use max of both numbers)
			} else if(current.interpolationMovement == Interpolation.Smooth) {
				/*
				 * Notes:
				 * 
				 * Keep track of movement vector after every frame (even on linear interpolation (calculate!))
				 * only change movement vector by acceleration factor (limit length of the changed amount before applying)
				 * notify constraint break, when at the end there has to be a considerable slowdown to meet the keyframe (sub-tick duration, probably cause flickering/stuttering)
				 * 
				 * !! Check if a "wait" frame appears after the current one, as we have to slow down to zero then.
				 */
			} else if(current.interpolationMovement == Interpolation.Teleport) {
				//TODO: calculate time based on rotation?
			}
		}
	}
}
