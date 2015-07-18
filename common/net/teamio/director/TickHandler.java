package net.teamio.director;

import net.teamio.director.act.ActManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler {
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
	    if (event.phase == TickEvent.Phase.END) {
	    	ActManager.instance.tick();
	    }
	}
}
