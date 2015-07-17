package net.teamio.director;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(Keybindings.toggleHud);
		ClientRegistry.registerKeyBinding(Keybindings.saveKeyframe);
		ClientRegistry.registerKeyBinding(Keybindings.play);
	}

	@Override
	public void registerClientTick() {
		// Receive event for Client Ticks
		FMLCommonHandler.instance().bus().register(new TickHandler());
	}

}
