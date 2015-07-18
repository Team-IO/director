package net.teamio.director.cut;

import net.teamio.director.act.ActManager;
import net.teamio.director.gui.SceneEditor;

public class CutManager {
	public static Scene currentScene;
	
	public static SceneEditor gui;
	
	static {
		currentScene = new Scene();
	}

	public static void toggleGui() {
		if(gui == null || gui.isDisposed()) {
			gui = new SceneEditor();
			gui.setAdapter(ActManager.instance);
			gui.scene = currentScene;
			gui.invokeSetVisible();
			gui.redraw();
		} else {
			gui.dispose();
			gui = null;
		}
	}
	
	private static void redrawIfNeeded() {
		if(gui != null && !gui.isDisposed()) {
			gui.redraw();
		}
	}
	
	public static void recordKeyframe() {
		currentScene.movement.add(ActManager.instance.recordKeyframe());
		redrawIfNeeded();
	}
}
