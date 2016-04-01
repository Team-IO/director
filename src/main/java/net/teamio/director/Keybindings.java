package net.teamio.director;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;

public class Keybindings {
	public static KeyBinding toggleHud = new KeyBinding(CreeperDirector.KEYS_K_TOGGLE_GUI, Keyboard.KEY_MULTIPLY, CreeperDirector.KEYS_CATEGORY);
	public static KeyBinding saveKeyframe = new KeyBinding(CreeperDirector.KEYS_K_SAVE_KEYFRAME, Keyboard.KEY_ADD, CreeperDirector.KEYS_CATEGORY);
	public static KeyBinding play = new KeyBinding(CreeperDirector.KEYS_K_PLAY, Keyboard.KEY_NUMPADENTER, CreeperDirector.KEYS_CATEGORY); 
}
