package net.teamio.director;

import net.minecraft.client.Minecraft;
import net.teamio.director.act.ActManager;
import net.teamio.director.cut.CutManager;
import net.teamio.director.cut.Keyframe;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyHandler {

	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
		if(Keybindings.toggleHud.isPressed()) {
			System.out.println("Toggle");
			//Minecraft.getMinecraft().thePlayer.posY += 1;
			Minecraft.getMinecraft().thePlayer.moveEntity(0, 20, 0);
		} else if(Keybindings.saveKeyframe.isPressed()) {
			CutManager.currentScene.movement.add(new Keyframe(Minecraft.getMinecraft().thePlayer));
		} else if(Keybindings.play.isPressed()) {
			ActManager.replay(CutManager.currentScene);
		}
	}
}
