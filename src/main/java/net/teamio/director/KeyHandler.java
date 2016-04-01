package net.teamio.director;

import net.teamio.director.act.ActManager;
import net.teamio.director.cut.CutManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyHandler {

	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
		if(Keybindings.toggleHud.isPressed()) {
			System.out.println("Toggle");
			//Minecraft.getMinecraft().thePlayer.posY += 1;
//			Minecraft.getMinecraft().thePlayer.moveEntity(0, 20, 0);
			CutManager.toggleGui();
		} else if(Keybindings.saveKeyframe.isPressed()) {
			CutManager.recordKeyframe();
		} else if(Keybindings.play.isPressed()) {
			ActManager.instance.replay(CutManager.currentScene);
		}
	}
}
