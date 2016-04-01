package net.teamio.director;

public class CreepyDirector {
	private CreepyDirector() {
		//Util Class
	}
	
	public static final String MOD_ID = "creepy_director";
	public static final String MOD_NAME = "Creepy Director";
	public static final String MOD_VERSION = "@VERSION@";
	public static final String MOD_AUTHOR1 = "founderio";
	public static final String MOD_DESCRIPTION = "Creepy Director";
	public static final String MOD_CREDITS = "";
	public static final String MOD_LOGO_PATH = "";
	
	public static final String GUI_FACTORY_CLASS = "net.teamio.director.gui.GuiFactory";
	public static final String SERVER_PROXY_CLASS = "net.teamio.director.ServerProxy";
	public static final String CLIENT_PROXY_CLASS = "net.teamio.director.ClientProxy";
	
	public static final String KEYS_CATEGORY = "keys.teamio.director.category";
	public static final String KEYS_K_PLAY = "keys.teamio.director.play";
	public static final String KEYS_K_PAUSE = "keys.teamio.director.pause";
	public static final String KEYS_K_STEP_FORWARD = "keys.teamio.director.step_forward";
	public static final String KEYS_K_STEP_BACK = "keys.teamio.director.step_backward";
	public static final String KEYS_K_SAVE_KEYFRAME= "keys.teamio.director.save_keyframe";
	public static final String KEYS_K_TOGGLE_HUD = "keys.teamio.director.toggle_hud";
	public static final String KEYS_K_TOGGLE_GUI = "keys.teamio.director.toggle_gui";
}
