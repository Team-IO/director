package net.teamio.director;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {

	public static Configuration config;

	public static void init(File configFile) {

		if (config == null) {
			config = new Configuration(configFile);
			loadConfig();
		}

	}

	private static void loadConfig() {
		if (config.hasChanged()) {
			config.save();
		}
	}
	
	
	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(CreepyDirector.MOD_ID))
		{
			loadConfig();
		}
	}

}
