package net.teamio.director;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CreepyDirector.MOD_ID, name = CreepyDirector.MOD_NAME, version = CreepyDirector.MOD_VERSION, guiFactory = CreepyDirector.GUI_FACTORY_CLASS)
public class CreepyDirectorMain {

	@Instance(CreepyDirector.MOD_ID)
	public static CreepyDirectorMain instance;

	@SidedProxy(clientSide = CreepyDirector.CLIENT_PROXY_CLASS, serverSide= CreepyDirector.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModMetadata meta = event.getModMetadata();
		meta.authorList.add(CreepyDirector.MOD_AUTHOR1);
		// meta.authorList.add(CreepyDirector.MOD_AUTHOR2);
		meta.description = CreepyDirector.MOD_DESCRIPTION;
		meta.logoFile = CreepyDirector.MOD_LOGO_PATH;
		meta.autogenerated = false;

		FMLCommonHandler.instance().bus().register(new Config());
		
		Config.init(event.getSuggestedConfigurationFile());
		
		proxy.registerKeyBindings();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {

		FMLCommonHandler.instance().bus().register(new KeyHandler());
		
		proxy.registerClientTick();
	}
}
