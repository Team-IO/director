package net.teamio.director.act;

import net.teamio.director.cut.Keyframe;
import net.teamio.director.cut.Scene;

public interface IMinecraftAdapter {
	Keyframe recordKeyframe();
	void replay(Scene scene);
}
